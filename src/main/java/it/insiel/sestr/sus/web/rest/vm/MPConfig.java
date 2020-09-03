package it.insiel.sestr.sus.web.rest.vm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component 
@ConfigurationProperties("modellatoreprocessi") //pesca dal file application.yml tutte le propertyes che iniziano con gestuser
public class MPConfig 
{
	private String srvUrl;
	private String serviceServizio;
	private String serviceAttivita;
	
	public String getSrvUrl() {
		return srvUrl;
	}
	public void setSrvUrl(String srvUrl) {
		this.srvUrl = srvUrl;
	}
	public String getServiceServizio() {
		return serviceServizio;
	}
	public void setServiceServizio(String serviceServizio) {
		this.serviceServizio = serviceServizio;
	}
	public String getServiceAttivita() {
		return serviceAttivita;
	}
	public void setServiceAttivita(String serviceAttivita) {
		this.serviceAttivita = serviceAttivita;
	}
	
}
