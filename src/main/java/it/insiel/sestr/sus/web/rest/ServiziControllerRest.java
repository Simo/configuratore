package it.insiel.sestr.sus.web.rest;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.insiel.sestr.sus.api.model.AttivitaType;
import it.insiel.sestr.sus.api.model.ServizioType;
import it.insiel.sestr.sus.service.FornitoreServizi;
import it.insiel.sestr.sus.web.rest.vm.MPConfig;

@RestController
@RequestMapping("/api/servizi")
public class ServiziControllerRest 
{
	private static final Logger logger = LoggerFactory.getLogger(ServiziControllerRest.class);
	
	@Autowired
	private MPConfig Config;
	
	@Autowired
	private FornitoreServizi fornitoreServizi;
	
	@GetMapping(value = "/servizio",
	        	produces = { "application/json", "application/problem+json" })
	ResponseEntity<?> servizioGet() throws Exception 
	{
		logger.info("****** Chiamiamo il web-service Modellatore dei Servizi: servizioGet *******");
		
		String codiceServizio = "21103";//pozzi

		String urlServiceServizio = Config.getSrvUrl() + Config.getServiceServizio() + codiceServizio;
		
		HashMap<String, String> variabiliServizio = new HashMap<String, String>();
		variabiliServizio.put("ruolo", "richiedente");
		
		ServizioType servizio = fornitoreServizi.servizioResponce(urlServiceServizio, variabiliServizio);
		
		List<AttivitaType> attivitaList = servizio.getAttivita();
		
		AttivitaType attivitaTemp = attivitaList.get(0);
		
		String idAttivita = attivitaTemp.getId();
		
		logger.info("****** Chiamiamo il web-service Modellatore dei Servizi: attivitaGet *******");
		
		String urlServiceAttivita = Config.getSrvUrl() + Config.getServiceAttivita() + idAttivita;
		
		HashMap<String, String> variabiliAttivita = new HashMap<String, String>();
		variabiliAttivita.put("restituisci_configurazione", "true");
		
		AttivitaType attivita = fornitoreServizi.attivitaResponce(urlServiceAttivita, variabiliAttivita);

		byte[] configurazione = attivita.getConfigurazione();
		
		String entita_contestualizzata = new String(configurazione);
		
		logger.debug(entita_contestualizzata);
		
		//aggiungo le ulteriori proprietà dimensione e posizioneinPagina per ogni tag attributo semplice
		//pulisco i lang="IT" o i lang="EN"
		HashMap<String, String> completamentoXml = new HashMap<String, String>();
		HashMap<String, String> puliziaXml = new HashMap<String, String>();
		
		completamentoXml.put("dimensione", "full");
		completamentoXml.put("posizioneinPagina", "last");
		
		puliziaXml.put("lang=\"IT\"", "");
		puliziaXml.put("lang=\"EN\"", "");
		
		entita_contestualizzata = fornitoreServizi.completa_pulisciXML(entita_contestualizzata, completamentoXml, "attributoSemplice", puliziaXml);
		
		logger.debug(entita_contestualizzata);
		
		String json = fornitoreServizi.XMLtoJSONTransformer(entita_contestualizzata);
		
		logger.debug(json);
		
		return new ResponseEntity<>(json, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/attivita",
        	produces = { "application/json", "application/problem+json" })
	ResponseEntity<?> attivitaGet() throws Exception 
	{
		
		String idAttivita = "21107";//Richiesta dati qualità dell'acqua
	
		logger.info("****** Chiamiamo il web-service Modellatore dei Servizi: attivitaGet *******");
		
		String urlServiceAttivita = Config.getSrvUrl() + Config.getServiceAttivita() + idAttivita;
		
		HashMap<String, String> variabiliAttivita = new HashMap<String, String>();
		variabiliAttivita.put("restituisci_configurazione", "true");
		
		AttivitaType attivita = fornitoreServizi.attivitaResponce(urlServiceAttivita, variabiliAttivita);
	
		byte[] configurazione = attivita.getConfigurazione();
		
		String entita_contestualizzata = new String(configurazione);
		
		logger.debug(entita_contestualizzata);
		
		//aggiungo le ulteriori proprietà dimensione e posizioneinPagina per ogni tag attributo semplice
		//pulisco i lang="IT" o i lang="EN"
		HashMap<String, String> completamentoXml = new HashMap<String, String>();
		HashMap<String, String> puliziaXml = new HashMap<String, String>();
		
		completamentoXml.put("dimensione", "full");
		completamentoXml.put("posizioneinPagina", "last");
		
		puliziaXml.put("lang=\"IT\"", "");
		puliziaXml.put("lang=\"EN\"", "");
		
		entita_contestualizzata = fornitoreServizi.completa_pulisciXML(entita_contestualizzata, completamentoXml, "attributoSemplice", puliziaXml);
		
		logger.debug(entita_contestualizzata);
		
		String json = fornitoreServizi.XMLtoJSONTransformer(entita_contestualizzata);
		
		logger.debug(json);
		
		return new ResponseEntity<>(json, HttpStatus.OK);
		
	}

	@GetMapping(value = "/pozzi",
        produces = { "application/json", "application/problem+json" })
    ResponseEntity<?> getMock() throws Exception {

	    String json = "{\"entita\":{\"nomeQualificato\":\"RISORSE_IDRICHE::Pozzo\",\"nomeContestualizzato\":\"Pozzo ad uso domestico\",\"attributoSemplice\":[{\"nomeQualificato\":\"RISORSE_IDRICHE::Pozzo.DataFineCostruzione\",\"nomeContestualizzato\":\"Anno di costruzione\",\"formato\":\"DATE\",\"configurazioneLogica\":{\"opzioniVisualizzazione\":\"WRITE\",\"obbligatorio\":\"true\",\"cardinalitaMin\":\"1\",\"cardinalitaMax\":\"1\"},\"dimensione\":\"full\",\"posizioneinPagina\":\"last\"},{\"nomeQualificato\":\"RISORSE_IDRICHE::Pozzo.Diametro\",\"nomeContestualizzato\":\"Diametro presunto\",\"formato\":\"NUMBER\",\"configurazioneLogica\":{\"opzioniVisualizzazione\":\"WRITE\",\"obbligatorio\":\"true\",\"cardinalitaMin\":\"1\",\"cardinalitaMax\":\"1\"},\"dimensione\":\"full\",\"posizioneinPagina\":\"last\"},{\"nomeQualificato\":\"RISORSE_IDRICHE::Pozzo.Profondità\",\"nomeContestualizzato\":\"Profondità presunta\",\"formato\":\"NUMBER\",\"configurazioneLogica\":{\"opzioniVisualizzazione\":\"WRITE\",\"obbligatorio\":\"true\",\"cardinalitaMin\":\"1\",\"cardinalitaMax\":\"1\"},\"dimensione\":\"full\",\"posizioneinPagina\":\"last\"}],\"relazione\":{\"nomeRelazione\":\"GEOREFERENZIATO_DA\",\"entita\":{\"nomeQualificato\":\"OGGETTI_GEOMETRICI::Punto\",\"nomeContestualizzato\":\"Coordinate geografiche\",\"attributoSemplice\":[{\"nomeQualificato\":\"OGGETTI_GEOMETRICI::Geometria.Geometria\",\"nomeContestualizzato\":\"Geometria\",\"formato\":\"STRING\",\"configurazioneLogica\":{\"opzioniVisualizzazione\":\"WRITE\",\"obbligatorio\":\"true\",\"cardinalitaMin\":\"1\",\"cardinalitaMax\":\"1\"},\"dimensione\":\"full\",\"posizioneinPagina\":\"last\"},{\"nomeQualificato\":\"OGGETTI_GEOMETRICI::Geometria.Codice\",\"nomeContestualizzato\":\"Codice\",\"formato\":\"STRING\",\"configurazioneLogica\":{\"opzioniVisualizzazione\":\"WRITE\",\"obbligatorio\":\"true\",\"cardinalitaMin\":\"1\",\"cardinalitaMax\":\"1\"},\"dimensione\":\"full\",\"posizioneinPagina\":\"last\"},{\"nomeQualificato\":\"OGGETTI_GEOMETRICI::Geometria.TipoSerializzazione\",\"nomeContestualizzato\":\"Tipo serializzazione\",\"formato\":\"STRING\",\"configurazioneLogica\":{\"opzioniVisualizzazione\":\"WRITE\",\"obbligatorio\":\"true\",\"cardinalitaMin\":\"1\",\"cardinalitaMax\":\"1\"},\"dimensione\":\"full\",\"posizioneinPagina\":\"last\"}]}}}}";

	    logger.debug(json);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
	
}
