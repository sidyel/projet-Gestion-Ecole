package config;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import entitee.Personne;

public class PdfUtil {

	public static void createPdf(String filePath, Personne personne, String logoPath) throws Exception {
	    // Crée le PDF
	    PdfWriter writer = new PdfWriter(filePath);
	    PdfDocument pdfDoc = new PdfDocument(writer);
	    Document document = new Document(pdfDoc);

	    // Ajouter le logo
	    Image logo = new Image(ImageDataFactory.create(logoPath));
	    logo.setWidth(100); // Vous pouvez ajuster la largeur selon vos besoins
	    logo.setHeight(100); // Vous pouvez ajuster la hauteur selon vos besoins
	    document.add(logo);

	    // Ajouter un en-tête décoré
	    String nomEcole = "Kame-Senryu"; // Remplacez par le nom réel de votre école
	    Paragraph header = new Paragraph(nomEcole)
	            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)) // Utilisation de StandardFonts
	            .setFontSize(24) // Taille de police plus grande pour l'en-tête
	            .setBold()
	            .setFontColor(ColorConstants.BLUE) // Couleur de l'en-tête
	            .setMarginBottom(20); // Espacement sous l'en-tête

	    document.add(header);

	    // Ajouter un titre pour la confirmation
	    document.add(new Paragraph("Confirmation d'inscription du personnel")
	            .setFontSize(18) // Taille de police pour le titre
	            .setBold()
	            .setMarginBottom(10)); // Espacement sous le titre

	    // Ajouter des informations sur la personne
	    document.add(new Paragraph("Nom: " + personne.getNom()));
	    document.add(new Paragraph("Prénom: " + personne.getPrenom()));
	    document.add(new Paragraph("Email: " + personne.getEmail()));

	    // Ferme le document
	    document.close();
	}
}
