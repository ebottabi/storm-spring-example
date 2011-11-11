package mykidong.storm.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonMashaller {
	
	/**
	 * log4j to log.
	 */
	private static Logger log = LoggerFactory
			.getLogger(JsonMashaller.class);
	
	public static Object unmarshal(String str, Class clazz)
	{
		ObjectMapper mapper = new ObjectMapper(); 
		Object obj = null;
		try {
			obj = mapper.readValue(str, clazz);			
			
		} catch (JsonParseException e) {
			log.error("Error: " + e);
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			log.error("Error: " + e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("Error: " + e);
			throw new RuntimeException(e);
		}
		return obj;
	}
}
