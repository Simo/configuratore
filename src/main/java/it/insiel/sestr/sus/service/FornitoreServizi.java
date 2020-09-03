package it.insiel.sestr.sus.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.insiel.sestr.sus.api.model.AttivitaType;
import it.insiel.sestr.sus.api.model.ServizioType;
import it.insiel.sestr.sus.service.mapper.DuplicateToArrayJsonNodeDeserializer;

@Service
public class FornitoreServizi 
{
	public ServizioType servizioResponce(String urlServizio, HashMap<String, String> variabili)
	{
		URI urlServiceServizio = null;
		
		try 
		{
			if(variabili != null && variabili.size() != 0)
			{
				urlServizio += "?"; 
				
				HashMap<String,String> map = variabili;
                Set<String> keySet = map.keySet();
                for(String key:keySet)
                {
                	urlServizio += key;
                    String value = map.get(key);  
                    urlServizio = urlServizio + "=" + value + "&";
                }
                
                urlServizio = urlServizio.substring(0, urlServizio.length()-1);
			}
	
			urlServiceServizio = new URI(urlServizio);
		} 
		catch (URISyntaxException e) 
		{
			 
			e.printStackTrace();
		}
		
		//si interfaccia con il servizio
		RestTemplate restTemplateServizio = new RestTemplate();
		
		ServizioType servizio = null;
		
		try 
		{
			servizio = restTemplateServizio.getForObject(urlServiceServizio, ServizioType.class);
		} 
		catch (Exception e) 
		{
			String ErrMsg = String.format("Connessione al servizio del modellatore processi non riuscita!!");			
		}
		
		return servizio;
	}
	
	public AttivitaType attivitaResponce(String urlServizio, HashMap<String, String> variabili)
	{
		URI urlAttivitaServizio = null;
		
		try 
		{
			if(variabili != null && variabili.size() != 0)
			{
				urlServizio += "?"; 
				
				HashMap<String,String> map = variabili;
                Set<String> keySet = map.keySet();
                for(String key:keySet)
                {
                	urlServizio += key;
                    String value = map.get(key);  
                    urlServizio = urlServizio + "=" + value + "&";
                }
                
                urlServizio = urlServizio.substring(0, urlServizio.length()-1);
			}
	
			urlAttivitaServizio = new URI(urlServizio);
		} 
		catch (URISyntaxException e) 
		{
			 
			e.printStackTrace();
		}
		
		//si interfaccia con il servizio
		RestTemplate restTemplateServizio = new RestTemplate();
		
		AttivitaType attivita = null;
		
		try 
		{
			attivita = restTemplateServizio.getForObject(urlAttivitaServizio, AttivitaType.class);
		} 
		catch (Exception e) 
		{
			String ErrMsg = String.format("Connessione al servizio del modellatore processi non riuscita!!");			
		}
		
		return attivita;
	}
	
	public String completa_pulisciXML(String xmlIniziale, HashMap<String, String> completamentoXml, String elementoDaCompletare, HashMap<String, String> puliziaXml) 
	{
		String xmlCompleto = xmlIniziale;
		
		//completo l'XML con gli elementi TAG supplementari
		if(completamentoXml != null && completamentoXml.size() != 0)
		{
			String tagCompletamento = ""; 
			
			HashMap<String,String> map = completamentoXml;
            Set<String> keySet = map.keySet();
            for(String key:keySet)
            {
            	tagCompletamento += "<" + key + ">";
                String value = map.get(key);  
                tagCompletamento += value;
                tagCompletamento += "</" + key + ">";
            }
            
            String elementoDaCompletareTag = "</" + elementoDaCompletare + ">";
            tagCompletamento += elementoDaCompletareTag;
            
            xmlCompleto = xmlCompleto.replace(elementoDaCompletareTag, tagCompletamento);
		}
		
		//pulisco l'XML
		if(puliziaXml != null && puliziaXml.size() != 0)
		{
			HashMap<String,String> map = puliziaXml;
            Set<String> keySet = map.keySet();
            for(String key:keySet)
            {
            	String value = map.get(key);  
            	xmlCompleto = xmlCompleto.replace(key, value);
            }
		}
		
		return xmlCompleto;
	}
	
	public String XMLtoJSONTransformer(String xml) throws IOException
	{
		String json = "";
		
		XmlMapper xmlMapper = new XmlMapper();
		//questa configurazione serve a risolvere i problemi di deserializzazione di nodi xml con lo stesso nome allo stesso livello
		xmlMapper.registerModule(new SimpleModule().addDeserializer(
				JsonNode.class,
				new DuplicateToArrayJsonNodeDeserializer()));
		
		JsonNode node = xmlMapper.readTree(xml.getBytes());

		ObjectMapper jsonMapper = new ObjectMapper();
		json = jsonMapper.writeValueAsString(node);
		
		return json;
	}
}
