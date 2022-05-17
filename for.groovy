import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.*;
    
    def Message processData(Message message) {
        
        def body = message.getBody(String)
        def jsonParser = new JsonSlurper()
        def jsonObject = jsonParser.parseText(body)
        
        def json
        def array = []
    
    for ( int i = 0; i < jsonObject.d.results.size(); i++){
        json = JsonOutput.toJson(
            firstName : jsonObject.d.results[i].firstName,
            title : jsonObject.d.results[i].title,
            email : jsonObject.d.results[i].email,
            location : jsonObject.d.results[i].location
            // phoneNumber : jsonObject.d.results[i].empInfo.personNav.phoneNav.results[0].phoneNumber
        )
            def json2 = jsonParser.parseText(json)
    for (int j = 0; j < jsonObject.d.results[i].empInfo.personNav.phoneNav.results.size(); j++){
            def ph = j + 1 
            json2."phoneNumber $ph" = jsonObject.d.results[i].empInfo.personNav.phoneNav.results[j].phoneNumber
    }
        json = JsonOutput.toJson(json2)
        array.push(json)
    }
    
    message.setBody(JsonOutput.prettyPrint(array.toString()))
    message.setBody(array.toString())
    message.setHeader("Content-Type", "application/json")
    


    return message;
    }
 