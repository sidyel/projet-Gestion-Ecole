package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import entitee.BusTransport;
import entitee.BusTransportHome;
import entitee.Personnel;
import entitee.PersonnelHome;
import metier.Operation;

@ManagedBean
public class BusTransportBean {

	BusTransport busTransport = new BusTransport();
	Personnel personnel = new Personnel();
	List<Personnel> personnelList;
    private int chauffeurId;
    

	
	@PostConstruct
    private void init() {
        try {
        	
        	personnelList=operation.listeChauffeur("CHAUFFEUR");
            
           
            System.out.println("Cours loaded: " + personnelList.size());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public BusTransport getBusTransport() {
		return busTransport;
	}

	public void setBusTransport(BusTransport busTransport) {
		this.busTransport = busTransport;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	Operation operation = new Operation();
	BusTransportHome busTransportHome = new BusTransportHome();
	PersonnelHome personnelHome = new PersonnelHome();

	public List<Personnel> getPersonnelList() {
		return personnelList;
	}

	public void setPersonnelList(List<Personnel> personnelList) {
		this.personnelList = personnelList;
	}
	
	public String enregistrerBus()
	{
		personnel=personnelHome.findById(chauffeurId);
		busTransport.setPersonnel(personnel);
		busTransportHome.persist(busTransport);
		
		return null;
	}

	public int getChauffeurId() {
		return chauffeurId;
	}

	public void setChauffeurId(int chauffeurId) {
		this.chauffeurId = chauffeurId;
	}
	

}
