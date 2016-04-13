package infovis;

import static spark.Spark.get;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class App {
	private static Sql2o sql2o;
	
	public static void main(String[] args) {
		 sql2o = new Sql2o("jdbc:postgresql://localhost:5432/infovis",
		            "jpuente", "jpuente");
	        get("/generos", (req, res) -> {return dataToJson(getGenderValues(sql2o));});
	    }
	
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
    
    public static Gender getGenderValues(Sql2o sql2o) {
    	try (Connection conn = sql2o.open()) {
    	List<Integer> genderValues = conn.createQuery("select count (*) from Persona group by P02")
                .executeAndFetch(Integer.class);
    	return new Gender(genderValues.get(1), genderValues.get(0));
    	}
    }
	
}
