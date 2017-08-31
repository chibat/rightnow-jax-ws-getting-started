package app;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import com.rightnow.ws.base.v1_3.ActionEnum;
import com.rightnow.ws.base.v1_3.ID;
import com.rightnow.ws.base.v1_3.NamedID;
import com.rightnow.ws.base.v1_3.RNObject;
import com.rightnow.ws.messages.v1_3.ClientInfoHeader;
import com.rightnow.ws.messages.v1_3.CreateProcessingOptions;
import com.rightnow.ws.messages.v1_3.RNObjectsResult;
import com.rightnow.ws.objects.v1_3.Contact;
import com.rightnow.ws.objects.v1_3.Email;
import com.rightnow.ws.objects.v1_3.EmailList;
import com.rightnow.ws.objects.v1_3.PersonName;
import com.rightnow.ws.wsdl.v1_3.RightNowSyncPort;
import com.rightnow.ws.wsdl.v1_3.RightNowSyncService;

public class SampleClient {

    public static void main(String[] args) throws Exception {
        long id = new SampleClient().createContact();
        System.out.println("New Contact Created with ID: " + id);
    }

    public long createContact() throws Exception {

        RightNowSyncService service = new RightNowSyncService(new URL(Config.WSDL_URL));

        service.setHandlerResolver(new HandlerResolver() {
            @SuppressWarnings("rawtypes")
            @Override
            public List<Handler> getHandlerChain(PortInfo portInfo) {
                List<Handler> handlerList = new ArrayList<>();
                handlerList.add(new RequestHandler());
                return handlerList;
            }
        });

        RightNowSyncPort port = service.getRightNowSyncPort();
        Contact newContact = populateContactInfo();
        // Set the application ID in the client info header.
        ClientInfoHeader clientInfoHeader = new ClientInfoHeader();
        clientInfoHeader.setAppID(Config.APP_ID);
        // Set the create processing options, allow external events and rules to execute
        CreateProcessingOptions createProcessingOptions = new CreateProcessingOptions();
        createProcessingOptions.setSuppressExternalEvents(false);
        createProcessingOptions.setSuppressRules(false);
        List<RNObject> createObjects = new ArrayList<>();
        createObjects.add(newContact);
        // Invoke the create operation on the RightNow server
        RNObjectsResult createResults = port.create(createObjects, createProcessingOptions, clientInfoHeader);
        // We only created a single contact, this will be at index 0 of the results
        List<RNObject> rnObjects = createResults.getRNObjects();
        newContact = (Contact) rnObjects.get(0);
        return newContact.getID().getId();
    }

    private Contact populateContactInfo() {
        Contact newContact = new Contact();
        PersonName personName = new PersonName();
        personName.setFirst("Christopher");
        personName.setLast("Omland");
        newContact.setName(personName);
        EmailList emailList = new EmailList();
        Email email = new Email();
        email.setAction(ActionEnum.ADD);
        email.setAddress("chris.omland2@rightnow.com");
        NamedID addressType = new NamedID();
        ID addressTypeID = new ID();
        addressTypeID.setId(1L);
        addressType.setID(addressTypeID);
        email.setAddressType(addressType);
        email.setInvalid(false);
        emailList.getEmailList().add(email);
        newContact.setEmails(emailList);
        return newContact;
    }
}
