package org.streusa.services;

import com.cloudant.client.api.Database;
import com.sendgrid.*;
import org.streusa.entities.User;

import java.util.List;

public class MailService {

    static SendGrid sg = new SendGrid("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");


    public void sendEmail(String type, String subject, String body) {

        Personalization p = new Personalization();
        Mail mail = new Mail();
        Email from = new Email("infostreusa@gmail.com");
        Content content = new Content("text/plain", body);

        List<User> users = getUsers();
        if(type.equals("User")){
            for(User user : users){
                p.addTo(new Email(user.getEmail()));
            }
        }else if(type.equals("SuperUser")){
            for(User user : users){
                if(user.getRuolo().equals("SuperUser")){
                    p.addTo(new Email(user.getEmail()));
                }
            }
        }else if(type.equals("Administrator")){
            p.addTo(new Email("edocngi@gmail.com"));
        }
        mail.addPersonalization(p);
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);

        try{
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("[INFO] SENDGRID Response: " + response.getStatusCode());

        }catch(Exception e){
            System.out.println("[ERROR] Errore nell'invio mail. Message: "+e.getMessage() );
            e.printStackTrace();
        }

    }

    private List<User> getUsers(){
        Database usersDB = DatabaseService.getDB("Cloudant-streusa20", "users");
        List<User> usersList = null;
        usersList = usersDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", User.class).getDocs();

        return usersList;
    }
}
