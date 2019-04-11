package Serie61;

import java.io.Serializable;

import Connexions.ConnectionFichiers;
import IFrame.FrameGestionTableDesClient61;
import IPane.IO;
import MesExceptions.AbandonException;
import MesExceptions.MenuPrecedentException;
import MesExceptions.RetourException;
import MesInterfaces.InterfaceGestion;

public class GestionTableClient61 {

	IO es = new IO();
	private ConnectionFichiers<TableClient61> fichin;
	
	public GestionTableClient61(String nomPhy) {
		fichin = new ConnectionFichiers<TableClient61>(nomPhy);
	}
	
	public TableClient61 recuperer() {
		
		TableClient61 tabClient = fichin.lire();
		if(tabClient == null)
			tabClient = new TableClient61();
		return tabClient;
	}
	
	public void sauvegarder(TableClient61 tabClient) {
		
		fichin.ecrire(tabClient, false);
	}
	
	public int menuChoix(String msg, int min, int max) throws AbandonException,RetourException {
		try {
			return (Integer) es.saisie(msg, min, max);
			}
			catch (MenuPrecedentException mp) {
				throw new RetourException();
			}
	}

	public void menuGeneral(Object ...obj) {

		int choix;
		TableClient61 tabClient = (TableClient61)obj[0];
		TableDesCommandes61 tabCde = (TableDesCommandes61)obj[1];
		new FrameGestionTableDesClient61(tabClient, tabCde);
		/*do {
			try {
			String msg = "\n --------------- MENU DE GESTION DES CLIENTS --------------- \n"
					+ "\t Creer un compte client         ............... 1\n"
					+ "\t supprimer une compte        ............... 2\n"
					+ "\t afficher un compte          ............... 3\n"
					+ "\t afficher tout les comptes  ............... 4\n"
					+ "\t modifier un compte         ............... 5\n"
					+ "\t finir                          ............... 0\n" + "votre choix ?\n\n";
			choix = menuChoix(msg, 0, 5);
			switch (choix) {
			case 1:
				creer(tabClient);
				sauvegarder(tabClient);
				break;
			case 2:
				supprimer(tabClient, tabCde);
				sauvegarder(tabClient);
				break;
			case 3:
				afficherUnClient(tabClient, tabCde);
				break;
			case 4:
				affiche(tabClient);
				break;
			case 5:
				modifier(tabClient);
				sauvegarder(tabClient);
				break;
			case 0:
				break;}
		} catch (MenuPrecedentException mp) {
			choix = -1;
		} catch (RetourException rt) {
			choix = 0;}
		} while (choix != 0);*/
	}
	
	/*public void afficherUnClient(Object ...obj) throws AbandonException,MenuPrecedentException 
	{
		TableClient61 tabClient = (TableClient61)obj[0];
		TableDesCommandes61 tabCde = (TableDesCommandes61)obj[1];
		String numclient = es.saisie(tabClient.cle() + "Saisissez le client a afficher \n");
		numclient = numclient.toUpperCase();
		Client61 cl = tabClient.retourner(numclient);
		if (cl != null)
			es.affiche(cl.toString()+"\n"+tabCde.cdeClient(numclient));	
		else
			es.affiche("Client inexistant !\n");
	}
	
	public void affiche(Object ...obj)
	{
		TableClient61 tabClient = (TableClient61)obj[0];
		if (tabClient.taille() == 0)
			es.affiche("Aucun client dans la base !\n");
		else
			es.affiche(tabClient.toString()+"\n");
	}
	
	public void creer(Object ...obj) throws AbandonException,MenuPrecedentException 
	{
		TableClient61 tabClient = (TableClient61)obj[0];
		String name = es.saisie("Entrez votre nom au format NOM-Prenom\n", true);
		String lieu = es.saisie("Entrez votre lieu d'habitation\n", true);
		int numero = 0;
		String ret = "";
		do {
			ret = name.substring(0, 3) + numero;
			numero++;
		}while (tabClient.retourner(ret) != null);
		Client61 client = new Client61(name, lieu, ret);
		tabClient.ajouter(client);
	}
	
	public void supprimer(Object ...obj) throws AbandonException,MenuPrecedentException 
	{
		TableClient61 tabClient = (TableClient61)obj[0];
		TableDesCommandes61 tabCde = (TableDesCommandes61)obj[1];
		String numclient = es.saisie(tabClient.cle() + "Saisissez le client a supprimer\n");
		numclient = numclient.toUpperCase();
		Client61 cl = tabClient.retourner(numclient);
		if (cl != null)
		{
			tabCde.purgeClient(numclient);
			tabClient.supprimer(numclient);
			es.affiche("Client supprimer !");
		}
		else
			es.affiche("Client inexistant !\n");	
	}
	
	public void modifier(Object ...obj) throws AbandonException,MenuPrecedentException 
	{
		TableClient61 tabClient = (TableClient61)obj[0];
		String numclient = es.saisie(tabClient.cle() + "Saisissez le client a modifier \n");
		numclient = numclient.toUpperCase();
		Client61 cl = tabClient.retourner(numclient);
		if (cl != null)
		{
			String lieu = es.saisie("Nouveau nom du lieu d'habitation\n", true);
			cl.setLieu(lieu);
		}
		else
			es.affiche("Client inexistant !\n");	
	}
	*/
}
