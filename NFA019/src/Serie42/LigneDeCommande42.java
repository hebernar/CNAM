package Serie42;

public class LigneDeCommande42 {

	private int codearticle;
	private int quantitecommande;
	
	public LigneDeCommande42(int codearticle, int quantitecommande) {
		this.codearticle = codearticle;
		this.quantitecommande = quantitecommande;
	}
	
	public LigneDeCommande42() {}
	
	public int getCodearticle() {
		return codearticle;
	}
	public int getQuantitecommande() {
		return quantitecommande;
	}
	public void setCodearticle(int codearticle) {
		this.codearticle = codearticle;
	}
	public void setQuantitecommande(int quantitecommande) {
		this.quantitecommande = quantitecommande;
	}
	
	public String toString()
	{
		return ("code article : "+ codearticle+" quantite commande : "+quantitecommande);
	}
	
	public String facturer(TableArticles42 tabArt) {
		AbstractArticle art = tabArt.retourner(this.getCodearticle());
		return (art.facturer(this.quantitecommande));
	}
	
	public float prix(TableArticles42 tabArt)
	{
		return (tabArt.retourner(this.getCodearticle()).prix(this.getQuantitecommande()));
	}
}
