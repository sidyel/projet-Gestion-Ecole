package config;

import java.util.List;

import entitee.Classes;
import entitee.ClassesHome;
import entitee.Eleve;
import entitee.EleveHome;
import entitee.Personne;
import entitee.Personnel;
import metier.Operation;

public class test {

	public static void main(String[] args) {
		
		  // TODO Auto-generated method stub Classes c=new Classes(); ClassesHome
			/*
			 * ClassesHome ch=new ClassesHome(); ch.persist(null); Operation operation=new
			 * Operation(); Personne personne=operation.connexion("sidy", "123");
			 * System.out.println(personne.getNom());
			 * 
			 * Operation op=new Operation(); List<Object[]>
			 * t=op.getEmploiDuTempsParClasse(15); for (Object[] ligne : t) {
			 * System.out.println("Date: " + ligne[0]); System.out.println("Heure début: " +
			 * ligne[1]); System.out.println("Heure fin: " + ligne[2]);
			 * System.out.println("Cours: " + ligne[3]); System.out.println("Professeur: " +
			 * ligne[4] + " " + ligne[5]);
			 * System.out.println("---------------------------"); }
			 */
		Eleve e=new Eleve();
		EleveHome eh=new EleveHome();
		eh.persist(e);
	}

}
