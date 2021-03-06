package Serie53;

import Connexions.ConnectionFichiers;
import IPane.IO;
import MesExceptions.AbandonException;
import MesExceptions.MenuPrecedentException;
import MesExceptions.RetourException;
import MesInterfaces.InterfaceGestion;
import Utils.DateUser;

public class GestionTableDesCommandes53 implements InterfaceGestion<TableDesCommandes53>{
	
	IO es = new IO();
	private ConnectionFichiers<TableDesCommandes53> fichin;
	
	public GestionTableDesCommandes53(String nomPhy) {
		fichin = new ConnectionFichiers<TableDesCommandes53>(nomPhy);
	}
	
	public TableDesCommandes53 recuperer() {
		
		TableDesCommandes53 tabCde = fichin.lire();
		if(tabCde == null)
			tabCde = new TableDesCommandes53();
		return tabCde;
	}
	
	public void sauvegarder(TableDesCommandes53 tabCde) {
		
		fichin.ecrire(tabCde, false);
	}
	
	public int menuChoix(String msg, int min, int max) throws AbandonException,RetourException {
		try {
			return (Integer) es.saisie(msg, min, max);
			}
			catch (MenuPrecedentException mp) {
				throw new RetourException();
			}
	}

	public void menuGeneral(Object ...obj) throws AbandonException {

		int choix;
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		TableArticles53 tabArt = (TableArticles53)obj[1];
		TableClient53 tabClient = (TableClient53)obj[2];
		do {
			try {
			String numCde = prochainNumero(tabCde);
			String msg = "\n --------------- MENU DE GESTION DES COMMANDES --------------- \n"
					+ "\t Creer la commande nº"+numCde+"          ............... 1\n"
					+ "\t supprimer une commande         ............... 2\n"
					+ "\t afficher une commande          ............... 3\n"
					+ "\t afficher toutes les commandes  ............... 4\n"
					+ "\t modifier une commande          ............... 5\n"
					+ "\t finir                          ............... 0\n" + "votre choix ?\n\n";
			choix = menuChoix(msg, 0, 5);
			switch (choix) {
			case 1:
				creer(tabCde, tabArt, numCde, tabClient);
				sauvegarder(tabCde);
				break;
			case 2:
				supprimer(tabCde);
				sauvegarder(tabCde);
				break;
			case 3:
				afficherUneCde(tabCde);
				break;
			case 4:
				affiche(tabCde);
				break;
			case 5:
				modifier(tabCde, tabArt);
				sauvegarder(tabCde);
				break;
			case 0:
				break;}
		} catch (MenuPrecedentException mp) {
			choix = -1;
		} catch (RetourException rt) {
			choix = 0;}
		} while (choix != 0);
	}
	
	public void creer(Object ...obj) throws AbandonException,MenuPrecedentException {
	
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		TableArticles53 tabArt = (TableArticles53)obj[1];
		TableClient53 tabClient = (TableClient53)obj[3];
		String numCde = (String) obj[2];
		String numclient = es.saisie(tabClient.cle()+ " saisir le client qui commande \n");
		numclient = numclient.toUpperCase();
		Client53 cl = tabClient.retourner(numclient);
		if (cl != null)
		{
			TableLigneDeCommande53<String> cde = new TableLigneDeCommande53<String>();
			cde.setNumcommande(numCde);
			cde.setNumclient(cl.getNumclient());
			GestionTableLigneDeCommande53 gtldc = new GestionTableLigneDeCommande53();
			gtldc.menuGeneral(cde, tabArt);
			if(cde.taille() != 0)
			{
				tabCde.ajouter(cde);
				cl.setChiffre_affaire(cl.getChiffre_affaire()+(cde.getValeurHT()*1.196f*100)/100.0f);
				cl.setNb_cde(cl.getNb_cde() + 1);
			}
				
		}
		else
			es.affiche("Client inexistant !\n");
	}
	
	public String prochainNumero(TableDesCommandes53 tabCde) {
		
		int numero = 1;
		DateUser date = new DateUser();
		String cle = ""+date.getAnnee() + date.getMois()+ date.getJour();
		String ret;
		do{
			ret = cle + numero;
			numero++;		
		}while(tabCde.retourner(ret) != null);
		return ret;
	}
	
	public void supprimer(Object ...obj) throws AbandonException,MenuPrecedentException {
		
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		if(tabCde.tailleFacture() != 0)
		{
			String num = es.saisie(tabCde.cleFacture()+"quelle commande voulez vous supprimer ?\n");
			TableLigneDeCommande53<String> cde = tabCde.retourner(num);
			if (cde != null)
			{
				if(cde.isEtatfacture())
				{
					DateUser verif = new DateUser();
					if (verif.estSuperieur(cde.getDatefacture().ajouterNombreDeJour(7)))
						tabCde.supprimer(num);
					else
						es.affiche("Vous ne pouvez pas supprimer une commande facturee de moins de 7 jours !");
				}
				else
					es.affiche("Commande non facturee !");
			}
			else
				es.affiche("Commande introuvable !");
		}
		else
			es.affiche("Aucune comande deja facturee en cours !");
	}
	
	public void afficherUneCde(Object ...obj) throws AbandonException,MenuPrecedentException {
		
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		if(tabCde.taille() != 0)
		{
			String num = es.saisie(tabCde.cle()+"quelle commande voulez vous visualiser ?\n");
			TableLigneDeCommande53<String> cde = tabCde.retourner(num);
			if ( cde != null) {
				GestionTableLigneDeCommande53 gtldc1 = new GestionTableLigneDeCommande53();
				gtldc1.affiche(cde);
			}
			else
				es.affiche("Commande introuvable !");
		}
		else
			es.affiche("Aucune comande en cours !");
	}
	
	public void affiche(Object ...obj) {
		
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		if(tabCde.taille() != 0)
		{
			es.affiche("Liste des commandes \n\n"+tabCde.toString());
		}
		else
			es.affiche("Aucune comande en cours !");
	}
	
	/*public void facturer(TableDesCommandes52 tabCde,TableArticles52 tabArt) throws AbandonException,MenuPrecedentException {
		
		if(tabCde.taille() != 0)
		{
			String num = es.saisie(tabCde.cle()+"quelle commande voulez vous facturer ?");
			TableLigneDeCommande52<String> cde = tabCde.retourner(num);
			if (cde != null) {
				GestionTableLigneDeCommande52 gtldc2 = new GestionTableLigneDeCommande52();
				gtldc2.facturer(cde, tabArt);
			}
			else
				es.affiche("Commande introuvable !");
		}
		else
			es.affiche("Aucune comande en cours !");
	}*/
	
	public void modifier(Object ...obj) throws AbandonException,MenuPrecedentException {
		
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		TableArticles53 tabArt = (TableArticles53)obj[1];
		if(tabCde.taille() != 0)
		{
			String num = es.saisie(tabCde.cleNonFacture()+"quelle commande voulez vous modifier ?");
			TableLigneDeCommande53<String> cde = tabCde.retourner(num);
			if (cde != null) {
				if(cde.isEtatfacture() == false)
				{
					GestionTableLigneDeCommande53 gtldc3 = new GestionTableLigneDeCommande53();
					gtldc3.menuGeneral(cde,tabArt);
					if (cde.taille() == 0)
						tabCde.supprimer(num);
				}
				else
					es.affiche("Impossible de modifier une commande deja facturee !");
			}
			else
				es.affiche("Commande introuvable !");
		}
		else
			es.affiche("Aucune comande en cours !");
	}
	
	public void autoPurge(TableDesCommandes53 tabCde, int n)
	{
		if (tabCde.taille() != 0)
			tabCde.autoPurge(n, 1);
	}
	
	public void purgeOld(TableDesCommandes53 tabCde, int n)
	{
		if (n < 7)
			n = 7;
		if (tabCde.taille() != 0 && tabCde.plusExist(n))
		{
			boolean ok = es.saisieOuiNon("Voulez vous supprimer les anciennes commandes deja facturees de plus de "+n+" jours ? \n"
					+ "ATTENTION ACTION IRREVERSIBLE !!!!!!!!!!!");
			if (ok)
				tabCde.autoPurge(n, 2);
		}
	}
}
