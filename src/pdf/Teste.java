/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;


/**
 *
 * @author f7057419
 */
 import com.itextpdf.text.Document;
 import com.itextpdf.text.DocumentException;
 import com.itextpdf.text.Element;
 import com.itextpdf.text.PageSize;
 import com.itextpdf.text.Paragraph;
 import com.itextpdf.text.Phrase;
 import com.itextpdf.text.Rectangle;
 import com.itextpdf.text.pdf.ColumnText;
 import com.itextpdf.text.pdf.PdfPageEventHelper;
 import com.itextpdf.text.pdf.PdfWriter;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream; 
 import java.io.IOException;
    

public class Teste
{

    //en esta ocacion no vamos a utilizar el constructor
    //por lo que si queremos podemos borrarlo pero siempre
    //es una buena practica de programacion ponerlo.
    public Teste()
    {

    }

    //crearemo nuestro pdf directamente del metodo main
    public static void  main(String[] args) throws IOException, DocumentException
    {
        //creamos una variable de tipo documento
        Document documento = new Document(PageSize.A4, 72, 72, 72, 72);

        //como vamos a trabajar con ficheros/archivos
        //debemos hacerlo dentro de un bloque try
        //para capturar los posibles errores
        try
        {
            //le decimos que nos genere un archivo en PDF
            // pasandole como parametros el contenido de
            //dicho archivo y el nombre de nuestro PDF
            PdfWriter writer = PdfWriter.getInstance(documento,
                    new FileOutputStream("ExemploPDF.pdf"));           
            
          //  Rectangle rct = new Rectangle(36, 54, 559, 788);
            Rectangle rct = new Rectangle(36,54, 559, 788);
            //Definimos un nombre y un tamaño para el PageBox los nombres posibles son: “crop”, “trim”, “art” and “bleed”.
            writer.setBoxSize("art", rct);
            HeaderFooter event = new HeaderFooter();
          
            writer.setPageEvent(event);
                       
            //depois de criar a headerfooter
                   
            //
            //abrimos nuestro documento para poder escribir en él.
            documento.open();
            //diriamos que escribimos un nuevo parrafo en nuestro
            //documento el cual contiene solo una linea de texto.
            for (int i = 0; i < 50; i++) {
              documento.add(new Paragraph("Teste pdf"));  
              documento.add(new Paragraph("Linha" + i));
            }
            
            
            
            
            
            
        }
        //aqui capturamos los errores que pudiera generar
        //nuestro documeto
        catch (FileNotFoundException de)
        {
            //imprimimos los errores
            System.err.println(de.getMessage());
        }

        //finalmente cerramos nuestro documento
        documento.close();
        
        Runtime.getRuntime().exec (new String[]{"cmd.exe", "/c", "start", "ExemploPDF.pdf"});
        
        //mandamos a imprimir que nuestro archivo esta listo
        System.out.println("Archivo generado exitosamente");
     }
    
static class HeaderFooter  extends PdfPageEventHelper  {
    
    

    

    
    

      public void onEndPage (PdfWriter writer, Document document) {
          Rectangle rect = writer.getBoxSize("art");
          ColumnText.showTextAligned(writer.getDirectContent(),
                  Element.ALIGN_CENTER, new Phrase("Linha 1" ),
                  rect.getRight(), rect.getTop(), 0);
          ColumnText.showTextAligned(writer.getDirectContent(),
                  Element.ALIGN_CENTER, new Phrase("Linha 2" ),
                  rect.getRight(), rect.getTop() -15 , 0);
               
          
          
          ColumnText.showTextAligned(writer.getDirectContent(),
                  Element.ALIGN_CENTER, new Phrase("Rodapé" ),
                  (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
            
            
            
        }
    }
    
    
    
    

   

}

