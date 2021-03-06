package Serie61;

import Connexions.ConnectionFichiers;
import IFrame.FrameGestionTableDesFactures61;
import IPane.IO;
import MesExceptions.AbandonException;
import MesExceptions.MenuPrecedentException;
import MesExceptions.RetourException;
import MesInterfaces.InterfaceGestion;
import Utils.DateUser;

public class GestionTableFactures61 {

	IO es = new IO();
	private ConnectionFichiers<TableFactures61> fichin;

	public GestionTableFactures61(String nomPhy) {
		fichin = new ConnectionFichiers<TableFactures61>(nomPhy);
	}

	public TableFactures61 recuperer() {

		TableFactures61 tabFact = fichin.lire();
		if (tabFact == null)
			tabFact = new TableFactures61();
		return tabFact;
	}

	public void sauvegarder(TableFactures61 tabFact) {

		fichin.ecrire(tabFact, false);
	}

	public int menuChoix(String msg, int min, int max) throws AbandonException, RetourException {
		try {
			return (Integer) es.saisie(msg, min, max);
		} catch (MenuPrecedentException mp) {
			throw new RetourException();
		}
	}

	public void menuGeneral(Object... obj) {
		int choix;
		TableFactures61 tabFact = (TableFactures61) obj[0];
		TableDesCommandes61 tabCde = (TableDesCommandes61) obj[1];
		TableArticles61 tabArt = (TableArticles61) obj[2];
		new FrameGestionTableDesFactures61(tabArt, tabCde, tabFact);
		/*do {
			try {
				String msg = "\n --------------- MENU DE GESTION DES FACTURES--------------- \n"
						+ "\t Creer une facture             ............... 1\n"
						+ "\t supprimer une facture         ............... 2\n"
						+ "\t afficher une facture          ............... 3\n"
						+ "\t afficher toutes les factures  ............... 4\n"
						//+ "\t modifier une facture          ............... 5\n"
						+ "\t finir                         ............... 0\n" + "votre choix ?\n\n";
				choix = menuChoix(msg, 0, 4);
				switch (choix) {
				case 1:
					creer(tabFact, tabCde, tabArt);
					sauvegarder(tabFact);
					break;
				case 2:
					supprimer(tabFact);
					sauvegarder(tabFact);
					break;
				case 3:
					afficherUneFact(tabFact);
					break;
				case 4:
					affiche(tabFact);
					break;
				//case 5:
				//	modifier(tabFact);
				//	sauvegarder(tabFact);
				//	break;
				case 0:
					break;
				}
			} catch (MenuPrecedentException mp) {
				choix = -1;
			} catch (RetourException rt) {
				choix = 0;
			}
		} while (choix != 0);*/
	}

	/*public void afficherUneFact(TableFactures61 tabFact) throws AbandonException, MenuPrecedentException {
		if (tabFact.taille() != 0) {
			String num = es.saisie(tabFact.cle() + "quelle facture voulez vous visualiser ?\n");
			Facture61 fact = tabFact.retourner(num);
			if (fact != null) {
				es.affiche(fact.toString());
			} else
				es.affiche("Facture introuvable !");
		} else
			es.affiche("Aucune Facture en cours !");
	}

	public void affiche(Object... obj) {
		TableFactures61 tabFact = (TableFactures61) obj[0];
		if (tabFact.taille() != 0)
			es.affiche(tabFact.toString());
		else
			es.affiche("Aucune Facture en cours !");
	}

	public void creer(Object... obj) throws AbandonException, MenuPrecedentException {
		TableFactures61 tabFact = (TableFactures61) obj[0];
		TableDesCommandes61 tabCde = (TableDesCommandes61) obj[1];
		TableArticles61 tabArt = (TableArticles61) obj[2];
		if (tabCde.tailleNonFacture() != 0) {
			String num = es.saisie(tabCde.cleNonFacture() + "quelle commande voulez vous facturer ?\n");
			TableLigneDeCommande61<String> cde = tabCde.retourner(num);
			if (cde != null) {
				Facture61 fact = tabFact.retourner(cde.getNumcommande());
				if (fact == null) {
					fact = new Facture61();
					fact.setNumcde(cde.getNumcommande());
					cde.setDatefacture(new DateUser());
					fact.setDateFacture(cde.getDatefacture());
					cde.setEtatfacture(true);
					fact.setTxt(cde.facturer(tabArt));
					tabFact.ajouter(fact);
				} else
					es.affiche("Commande deja facturee !");
			}
			else
				es.affiche("Commande introuvable !");
		} else
			es.affiche("Aucune commande non facturee en cours !");
	}

	public void supprimer(Object... obj) throws AbandonException, MenuPrecedentException 
	{
		TableFactures61 tabFact = (TableFactures61) obj[0];
		String num = es.saisie(tabFact.cle() + "quelle facture voulez vous supprimer ?\n");
		Facture61 fact = tabFact.retourner(num);
		if (fact != null) {
			DateUser verif = new DateUser();
			if (verif.estSuperieur(fact.getDateFacture().ajouterNombreDeJour(7)))
				tabFact.supprimer(num);
			else
				es.affiche("Vous ne pouvez pas effacer une facture de moins de 7 jours !");
		} 
		else
			es.affiche("Facture introuvable !");
	}

	public void modifier(Object... obj) throws AbandonException, MenuPrecedentException 
	{
		TableFactures61 tabFact = (TableFactures61) obj[0];
		String num = es.saisie(tabFact.cle() + "quelle facture voulez vous modifier ?\n");
		Facture61 fact = tabFact.retourner(num);
		if (fact != null) {
			
		}
		else
			es.affiche("Facture introuvable !");
	}
	*/
	public void purgeOld(TableFactures61 tabFact, int n)
	{
		if (n < 7)
			n = 7;
		if (tabFact.taille() != 0 && tabFact.plusExist(n))
		{
			boolean ok = es.saisieOuiNon("Voulez vous supprimer les anciennes factures de plus de "+n+" jours ? \n"
					+ "ATTENTION ACTION IRREVERSIBLE !!!!!!!!!!!");
			if (ok)
				tabFact.autoPurge(n);
		}
	}
}
