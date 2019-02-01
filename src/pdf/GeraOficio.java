package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GeraOficio {

    static String Oficio = "";
    static String AOF = "";
    static String Data;
  //  static String data;
    static String Processo;
    static String Inquerito;
    static String OficioN;
    static String Autor;
    static String Reu;
    static String Envolvido;   

    static String Corpo;
    static String Destinatário;
    static String Endereco;
    static String Abc;
    static String Conteudo;
    static String Sigiloso;
    //static String GSV;
    static String Correio;
    static String Email;
    static String Gerente;
    static String GerenteSetor;
    static String TipoGer;
    static String EnderecoRetorno;
    static boolean Anexo;
    /* public GeraOficio(Oficio of) {      
            
     }*/
    public void GerarPagUm(Oficio of) throws FileNotFoundException, IOException, DocumentException, InterruptedException {
        Oficio = of.getOficio();
        AOF = of.getAof();
     //   Data = of.getAof();
        Processo = of.getProcesso();
        Inquerito = of.getInquerito();
        OficioN = of.getOficion();
        Autor = of.getAutor();
        Reu = of.getReu();
        Envolvido = of.getEnvolvido();
        Corpo = of.getCorpo();
        Destinatário = of.getDestinario();
        Endereco = of.getEndereco();
        Abc = of.getAbc();
        Data = of.getData();
        Sigiloso = of.getSigiloso();
        Correio = of.getCorreio();
        String ABC = of.getAbc().toUpperCase();
        Conteudo="1ª Via ENVIO "+of.getConteudo();
        Email= of.getEmail();
        Gerente = of.getGerente();
        GerenteSetor = of.getGerenteSetor();
        TipoGer = of.getTipoGer();
        EnderecoRetorno = of.getEnderecoRetorno();
        Anexo = of.isAnexo();
     
        Document documento = null;
        OutputStream os = null;
        

        try {
            //FONTES        
            FontFactory.register("c:\\windows\\fonts\\arial.ttf");
            FontFactory.register("c:\\windows\\fonts\\arialbd.ttf");

            //Arial 8   
            Font Arial8 = new Font(FontFactory.getFont("arial"));
            Arial8.setSize(8);
            Font Arial8N = new Font(FontFactory.getFont("arial"));
            Arial8.setSize(8);
            Arial8N.setColor(BaseColor.BLACK);    
            Arial8N.setStyle("bold");
            
            //Arial 10   
            Font Arial10 = new Font(FontFactory.getFont("arial"));
            Arial10.setSize(10);
            Font Arial10N = new Font(FontFactory.getFont("arial"));
            Arial10N.setSize(10);
            Arial10N.setStyle("bold");

            //Arial 12   
            Font Arial12 = new Font(FontFactory.getFont("arial"));
            Arial12.setSize(12);
            Font Arial12N = new Font(FontFactory.getFont("arial"));
            Arial12N.setSize(12);
            Arial12N.setColor(BaseColor.BLACK);
            Arial12N.setStyle("bold");

            //Arial 14   
            Font Arial14 = new Font(FontFactory.getFont("arial"));
            Arial14.setSize(14);
            Font Arial14N = new Font(FontFactory.getFont("arial"));
            Arial14N.setSize(14);
            Arial14N.setStyle("bold");

            //Arial 16   
            Font Arial16 = new Font(FontFactory.getFont("arial"));
            Arial16.setSize(16);
            Font Arial16N = new Font(FontFactory.getFont("arial"));
            Arial16N.setSize(16);
            Arial16N.setStyle("bold");

            documento = new Document(PageSize.A4, 72, 36, 80, 50);            
            
            PdfWriter writer = PdfWriter.getInstance(documento,               
                      new FileOutputStream("c:\\temp\\OficioSemLogo.pdf"));

            //CABEÇALHO E RODAPÉ
            //  Rectangle rct = new Rectangle(36, 54, 559, 788);
            Rectangle rct = new Rectangle(50, 50, 180, 800);
            //Definimos un nombre y un tamaño para el PageBox los nombres posibles son: “crop”, “trim”, “art” and “bleed”.
            writer.setBoxSize("art", rct);
            HeaderFooter event = new HeaderFooter();
            writer.setPageEvent(event);

            //abre o documento
            documento.open();
            Paragraph LinhaBranca = new Paragraph("                                             ");
            documento.add(LinhaBranca);

            //Insere Processo no PDF            
            Paragraph processo = new Paragraph("Processo Nº            : " + of.getProcesso(), Arial12N);
            processo.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Processo)) {//Se vazio não grava
                documento.add(processo);
            }

            //////////////////////////
            //Insere INQUERITO no PDF            
            Paragraph inquerito = new Paragraph("Inquerito Policial Nº : " + of.getInquerito(), Arial12N);
            inquerito.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Inquerito)) {//Se vazio não grava
                documento.add(inquerito);
            }

            //////////////////////////
            //Insere OFICION no PDF            
            Paragraph oficion = new Paragraph("Ofício Nº                  : " + of.getOficion(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(OficioN)) {//Se vazio não grava
                documento.add(oficion);
            }

            //////////////////////////
            //Insere AUTOR no PDF            
            Paragraph autor = new Paragraph(of.getAutor(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Autor)) {//Se vazio não grava
                documento.add(autor);
            }
            //////////////////////////
            //Insere RÉU no PDF            
            Paragraph reu = new Paragraph( of.getReu(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Reu)) {//Se vazio não grava
                documento.add(reu);
            }

            //////////////////////////
            //Insere ENVOLVIDO no PDF            
            Paragraph envolvido = new Paragraph( of.getEnvolvido(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Envolvido)) {//Se vazio não grava
                documento.add(envolvido);
            }
            // insere SIGILOSO no PDF 
            Paragraph sigiloso = new Paragraph(of.getSigiloso(), Arial14N);
            sigiloso.setAlignment(Element.ALIGN_RIGHT);
            if (!"".equals(Sigiloso)) {
                documento.add(sigiloso);
            }
            //////////////////////////  
            documento.add(LinhaBranca);
            documento.add(LinhaBranca);
            //////////////////////////////////

           Paragraph p7 = new Paragraph(of.getCorpo().replaceAll("\t", "\b               "), Arial12);
           
          // Paragraph p7 = new Paragraph(of.getCorpo(), Arial12);
           p7.setTabSettings(new TabSettings(56f));
           p7.setLeading(13);
           p7.setAlignment(Element.ALIGN_JUSTIFIED);
           documento.add(p7);
            
        /*   
        Paragraph p = new Paragraph(of.getCorpo());
        p = new Paragraph();
        p.setTabSettings(new TabSettings(56f));
        p.add(Chunk.TABBING);
        p.add(new Chunk(of.getCorpo()));
        documento.add(p);
        */
              //INSERE O CORPO DO PDF
        // Paragraph Corpo = new Paragraph(tabulacaoDoTexto(of.getCorpo()), Arial12);
        // Corpo.setAlignment(Element.ALIGN_JUSTIFIED);
        // Corpo.setSpacingBefore(50);
        // Corpo.setSpacingAfter(50);
        // documento.add(Corpo);
        
            Paragraph p8 = new Paragraph("BANCO DO BRASIL S.A.", Arial14N);
            p8.setAlignment(Element.ALIGN_CENTER);
            documento.add(p8);
            Paragraph p9 = new Paragraph("CENOP SERVIÇOS CURITIBA/PR", Arial12);
            p9.setAlignment(Element.ALIGN_CENTER);
            documento.add(p9);
            
            documento.add(LinhaBranca);
         
            // ASSINTURAS
            if (!"SEM ASSINATURA".equals(Gerente)){
                //SO COM UMA ASSINATURA DE GERENTE 
                if("DJO2".equals(TipoGer)){
                    Paragraph p10 = new Paragraph(GerenteSetor);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo");
                    p10.setAlignment(Element.ALIGN_LEFT);   
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    
                }else if("GER_GRUPO".equals(TipoGer)){    
                      
                    Paragraph p10 = new Paragraph(Gerente);
                    //p10.setAlignment(Element.ALIGN_LEFT);
                    p10.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo",Arial10N );                                                                 
                    p11.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    
                }else{    
               //Paragraph p10 = new Paragraph("Nilciane C. Mieko Rangel                                                                                               Marcia Ricci",Arial10N);
                //INSERE A LINHA COM GERENTE DE SETOR E GERENTE DE GRUPO
                Paragraph p10 = new Paragraph(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente) + Gerente ,Arial10N);
                p10.setAlignment(Element.ALIGN_LEFT);
                documento.add(LinhaBranca);
                documento.add(p10);
                int tipo1 = 1;
                tipo1= GerenteSetor.length();
                System.out.println("quant setor " + GerenteSetor.length());

                    // ASSINATURA DO SETOR E ASSINATURA GRUPO
                    if("NORMAL".equals(TipoGer)){
                    //Paragraph p11 = new Paragraph("Gerente de Setor                                                                                                            Gerente de grupo",Arial10 );
                    Paragraph p11 = new Paragraph("Gerente de Setor" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Gerente de Grupo" ,Arial10N );                                                                 

                    documento.add(p11);
                    documento.add(LinhaBranca);
                    //CARGO DO GRUPO E DO FUNCIONARIO
                    }else if("DJO".equals(TipoGer)){
                    Paragraph p11 = new Paragraph("Gerente de Grupo" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Funcionário(a)" ,Arial10N );                                                                 

                    documento.add(p11);
                    documento.add(LinhaBranca);
                  
                    }  
                }
            }
            // ASSINTURAS FIM
                
            int linhas;            
            
            linhas = Integer.parseInt(of.getLinhas());            
            
            for (int i = 0; i < linhas; i++) {
                documento.add(LinhaBranca);
            }
            
            //INSERE O DESTINATARIO DO PDF
            Paragraph destinatario = new Paragraph(of.getDestinario(), Arial10 );
            destinatario.setAlignment(Element.ALIGN_LEFT);
            
            //INSERE O ENDERECO DO PDF
            Paragraph endereco = new Paragraph(of.getEndereco(), Arial10);
            endereco.setAlignment(Element.ALIGN_LEFT);
            documento.add(endereco);
            
            
            if(!"".equalsIgnoreCase(of.getEmail())){
                if (of.isAnexo()){    
                   //true
                    Chunk chunk1 = new Chunk(of.getEmail(), Arial10);
                   Chunk chunk2 = new Chunk(" (Com Anexo)", Arial10N);
                   
                   Paragraph email = new Paragraph();
                   email.add(chunk1);email.add(chunk2);
                   email.setAlignment(Element.ALIGN_LEFT);
                   documento.add(email);
                   
                }else {
                    //false
                        Paragraph email2 = new Paragraph(of.getEmail(), Arial10);
                        email2.setAlignment(Element.ALIGN_LEFT);
                        documento.add(email2);
                        }
            }
     
        } finally {
            if (documento != null) {
                //fechamento do documento
                documento.close();
            }
            if (os != null) {
                //fechamento da stream de saída
                os.close();
            }              
        }

        //INSERE O LOGO DO BANCO--CÓDIGO DE COLOCAR MARCA D AGUA       
        try {
              PdfReader Read_PDF_To_Watermark = new PdfReader("c:\\temp\\OficioSemLogo.pdf");
            //--   PdfReader Read_PDF_To_Watermark = new PdfReader("OficioSemLogo.pdf");
            int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
              PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("c:\\temp\\OficioComLogoUm.pdf"));
            //--         PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("OficioComLogo.pdf"));  
            int i = 0;
            
            Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");           
            //Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");           
            
            //--Image watermark_image = Image.getInstance("LogoRetangular.png");
            watermark_image.setAbsolutePosition(72, 775);
            PdfContentByte add_watermark;
            while (i < number_of_pages) {
                i++;
                add_watermark = stamp.getUnderContent(i);
                add_watermark.addImage(watermark_image);
            }           
           stamp.close();       
           Read_PDF_To_Watermark.close();           
        } catch (DocumentException | IOException i1) {                       
        }          
    }
    
        public void GerarPagDois(Oficio of) throws FileNotFoundException, IOException, DocumentException, InterruptedException {
        Oficio = of.getOficio();
        AOF = of.getAof();
        Data = of.getAof();
        Processo = of.getProcesso();
        Inquerito = of.getInquerito();
        OficioN = of.getOficion();
        Autor = of.getAutor();
        Reu = of.getReu();
        Envolvido = of.getEnvolvido();
        Corpo = of.getCorpo();
        Destinatário = of.getDestinario();
        Endereco = of.getEndereco();
        Abc = of.getAbc();
        Data = of.getData();
        Conteudo="2ª Via ARQUIVO "+of.getConteudo();
        Correio = of.getCorreio();
        Email= of.getEmail();
        Gerente = of.getGerente();
        GerenteSetor = of.getGerenteSetor();
        TipoGer = of.getTipoGer();
        EnderecoRetorno = of.getEnderecoRetorno();
        Anexo = of.isAnexo();
        
        
        String ABC=of.getAbc().toUpperCase();
        Document documento = null;
        OutputStream os = null;
        
        try {
            //FONTES        
            FontFactory.register("c:\\windows\\fonts\\arial.ttf");
            FontFactory.register("c:\\windows\\fonts\\arialbd.ttf");
               
             Font Arial9 = new Font(FontFactory.getFont("arial"));
            Arial9.setSize(9);
            Font Arial9N = new Font(FontFactory.getFont("arial"));
            Arial9N.setSize(9);
            Arial9N.setStyle("bold");
            
            //Arial 8   
            Font Arial8 = new Font(FontFactory.getFont("arial"));
            Arial8.setSize(8);

            //Arial 10   
            Font Arial10 = new Font(FontFactory.getFont("arial"));
            Arial10.setSize(10);
            Font Arial10N = new Font(FontFactory.getFont("arial"));
            Arial10N.setSize(10);
            Arial10N.setStyle("bold");

            //Arial 12   
            Font Arial12 = new Font(FontFactory.getFont("arial"));
            Arial12.setSize(12);
            Font Arial12N = new Font(FontFactory.getFont("arial"));
            Arial12N.setSize(12);
            Arial12N.setColor(BaseColor.BLACK);
            Arial12N.setStyle("bold");

            //Arial 14   
            Font Arial14 = new Font(FontFactory.getFont("arial"));
            Arial14.setSize(14);
            Font Arial14N = new Font(FontFactory.getFont("arial"));
            Arial14N.setSize(14);
            Arial14N.setStyle("bold");

            //Arial 16   
            Font Arial16 = new Font(FontFactory.getFont("arial"));
            Arial16.setSize(16);
            Font Arial16N = new Font(FontFactory.getFont("arial"));
            Arial16N.setSize(16);
            Arial16N.setStyle("bold");

            documento = new Document(PageSize.A4, 72, 36, 80, 50);            
            
            PdfWriter writer = PdfWriter.getInstance(documento,               
                      new FileOutputStream("c:\\temp\\OficioSemLogo.pdf"));
            //--            new FileOutputStream("OficioSemLogo.pdf"));

            //CABEÇALHO E RODAPÉ
            //  Rectangle rct = new Rectangle(36, 54, 559, 788);
            Rectangle rct = new Rectangle(50, 50, 180, 800);
            //Definimos un nombre y un tamaño para el PageBox los nombres posibles son: “crop”, “trim”, “art” and “bleed”.
            writer.setBoxSize("art", rct);
            HeaderFooter event = new HeaderFooter();
            writer.setPageEvent(event);

            //abre o documento
            documento.open();

            Paragraph LinhaBranca = new Paragraph("                                             ");

             //documento.add(LinhaBranca);
            documento.add(LinhaBranca);

            //Insere Processo no PDF            
            Paragraph processo = new Paragraph("Processo Nº            : " + of.getProcesso(), Arial12N);
            processo.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Processo)) {//Se vazio não grava
                documento.add(processo);
            }

            //////////////////////////
            //Insere INQUERITO no PDF            
            Paragraph inquerito = new Paragraph("Inquerito Policial Nº : " + of.getInquerito(), Arial12N);
            inquerito.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Inquerito)) {//Se vazio não grava
                documento.add(inquerito);
            }

            //////////////////////////
            //Insere OFICION no PDF            
            Paragraph oficion = new Paragraph("Ofício Nº                  : " + of.getOficion(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(OficioN)) {//Se vazio não grava
                documento.add(oficion);
            }

            //////////////////////////
            //Insere AUTOR no PDF            
            Paragraph autor = new Paragraph(of.getAutor(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Autor)) {//Se vazio não grava
                documento.add(autor);
            }
            //////////////////////////
            //Insere RÉU no PDF            
            Paragraph reu = new Paragraph( of.getReu(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Reu)) {//Se vazio não grava
                documento.add(reu);
            }

            //////////////////////////
            //Insere ENVOLVIDO no PDF            
            Paragraph envolvido = new Paragraph( of.getEnvolvido(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Envolvido)) {//Se vazio não grava
                documento.add(envolvido);
            }
             // insere SIGILOSO no PDF 
            Paragraph sigiloso = new Paragraph( of.getSigiloso(), Arial14N);
            oficion.setAlignment(Element.ALIGN_RIGHT);
            if (!"".equals(Sigiloso)) {
                documento.add(sigiloso);
            }
            documento.add(LinhaBranca);
            documento.add(LinhaBranca);
            
           /* 
            String[] dadosJavaSplit = of.getCorpo().split("\\\n");
            
            for (String t : dadosJavaSplit) {                
                Paragraph p7 = new Paragraph(tabulacaoDoTexto(t), Arial12);
                p7.setSpacingBefore(00);
                p7.setSpacingAfter(5);
                p7.setTabSettings(new TabSettings(56f));
                p7.setLeading(13);
                p7.setAlignment(Element.ALIGN_JUSTIFIED);
                documento.add(p7);
            }
            */
            
            //String textoCorpo =of.getCorpo().replaceAll("\t", "             ");            
            //Phrase P7=new Phrase(3,textoCorpo, Arial12);
            //P7.setLeading(13); 
            Paragraph p7 = new Paragraph(of.getCorpo().replaceAll("\t", "\b               "), Arial12);
           
            // Paragraph p7 = new Paragraph(of.getCorpo(), Arial12);
            p7.setTabSettings(new TabSettings(56f));
            p7.setLeading(13);
            p7.setAlignment(Element.ALIGN_JUSTIFIED);
            documento.add(p7);
            
            /////////////////////////////////
            Paragraph p8 = new Paragraph("BANCO DO BRASIL S.A.", Arial14N);
            p8.setAlignment(Element.ALIGN_CENTER);
            documento.add(p8);
            Paragraph p9 = new Paragraph("CENOP SERVIÇOS SÃO PAULO/SP", Arial12);
            p9.setAlignment(Element.ALIGN_CENTER);
            documento.add(p9);
            documento.add(LinhaBranca);
            
             if (!"SEM ASSINATURA".equals(Gerente)){
                 
                if("DJO2".equals(TipoGer)){
                    Paragraph p10 = new Paragraph(GerenteSetor);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo");
                    p10.setAlignment(Element.ALIGN_LEFT);   
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    
                }else if("GER_GRUPO".equals(TipoGer)){    
                      
                    Paragraph p10 = new Paragraph(Gerente);
                    //p10.setAlignment(Element.ALIGN_LEFT);
                    p10.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo",Arial10N );                                                                 
                    p11.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    
                }else{  
                 
                 
                    //COLOCA O PARAGRAFO DO NOME DO GERENTE DE SETOR, EDITA O ESPAÇO ATÉ O GERENTE DE GRUPO
                    Paragraph p10 = new Paragraph(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente) + Gerente ,Arial10N);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    documento.add(LinhaBranca);
                    documento.add(p10);

                    int tipo1 = 1;
                    tipo1= GerenteSetor.length();
                    System.out.println("quant setor " + GerenteSetor.length());


                    if("NORMAL".equals(TipoGer)){
                    //Paragraph p11 = new Paragraph("Gerente de Setor                                                                                                            Gerente de grupo",Arial10 );
                    Paragraph p11 = new Paragraph("Gerente de Setor" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Gerente de Grupo" ,Arial10N );                                                                 
                    documento.add(p11);
                    documento.add(LinhaBranca);    

                    }else if("DJO".equals(TipoGer)){
                    Paragraph p11 = new Paragraph("Gerente de Grupo" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Funcionário(a)" ,Arial10N );                                                                 
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    }
                }  
             }
            int linhas;        
            linhas = Integer.parseInt(of.getLinhas());            
            
            for (int i = 0; i < linhas; i++) {
                documento.add(LinhaBranca);
            }
            
            //INSERE O DESTINATARIO DO PDF
            Paragraph destinatario = new Paragraph(of.getDestinario(), Arial10);
            destinatario.setAlignment(Element.ALIGN_LEFT);
            //INSERE O ENDERECO DO PDF
            Paragraph endereco = new Paragraph(of.getEndereco(), Arial10);
            endereco.setAlignment(Element.ALIGN_LEFT);
            documento.add(endereco);
           
           if(!"".equalsIgnoreCase(of.getEmail())){
                if (of.isAnexo()){
                    //true
                   Chunk chunk1 = new Chunk(of.getEmail(), Arial10);
                   Chunk chunk2 = new Chunk(" (Com Anexo)", Arial10N);
                   
                   Paragraph email = new Paragraph();
                   email.add(chunk1);email.add(chunk2);
                   email.setAlignment(Element.ALIGN_LEFT);
                   documento.add(email);
                    
                    
                    }else {
                    // false
                        Paragraph email2 = new Paragraph(of.getEmail(), Arial10);
                        email2.setAlignment(Element.ALIGN_LEFT);
                        documento.add(email2);
                        }
                
            }
           
        } finally {
            if (documento != null) {
                //fechamento do documento
                documento.close();
            }
            if (os != null) {
                //fechamento da stream de saída
                os.close();
            }              
        }

//INSERE O LOGO DO BANCO--CÓDIGO DE COLOCAR MARCA D AGUA       
        try {
              PdfReader Read_PDF_To_Watermark = new PdfReader("c:\\temp\\OficioSemLogo.pdf");
                //-- PdfReader Read_PDF_To_Watermark = new PdfReader("OficioSemLogo.pdf");
            int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
              PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("c:\\temp\\OficioComLogoDois.pdf"));
                //-- PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("OficioComLogo.pdf"));  
            int i = 0;
            Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");  
                //Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");           

            //--Image watermark_image = Image.getInstance("LogoRetangular.png");
            watermark_image.setAbsolutePosition(72, 775);
            PdfContentByte add_watermark;
            while (i < number_of_pages) {
                i++;
                add_watermark = stamp.getUnderContent(i);
                add_watermark.addImage(watermark_image);
            }           
           stamp.close();       
           Read_PDF_To_Watermark.close();           
        } catch (DocumentException | IOException i1) {                       
        }
    }
    
   public void GerarPagTres(Oficio of) throws FileNotFoundException, IOException, DocumentException, InterruptedException {
       
        Oficio = of.getOficio();
        AOF = of.getAof();
        Data = of.getAof();
        Processo = of.getProcesso();
        Inquerito = of.getInquerito();
        OficioN = of.getOficion();
        Autor = of.getAutor();
        Reu = of.getReu();
        Envolvido = of.getEnvolvido();
        Corpo = of.getCorpo();
        Destinatário = of.getDestinario();
        Endereco = of.getEndereco();
        Abc = of.getAbc();
        Data = of.getData();
        Conteudo ="3ª Via ARQUIVO " + of.getConteudo();
        Correio = of.getCorreio();
        Email= of.getEmail();  
        Gerente = of.getGerente();
        GerenteSetor = of.getGerenteSetor();
        String ABC = of.getAbc().toUpperCase();
        TipoGer = of.getTipoGer();
        EnderecoRetorno = of.getEnderecoRetorno();
        Anexo = of.isAnexo();

        Document documento = null;
        OutputStream os = null;
        
        try {
            //FONTES        
            FontFactory.register("c:\\windows\\fonts\\arial.ttf");
            FontFactory.register("c:\\windows\\fonts\\arialbd.ttf");

            //Arial 8   
            Font Arial8 = new Font(FontFactory.getFont("arial"));
            Arial8.setSize(8);
            
            Font Arial9 = new Font(FontFactory.getFont("arial"));
            Arial9.setSize(9);
            Font Arial9N = new Font(FontFactory.getFont("arial"));
            Arial9N.setSize(9);
            Arial9N.setStyle("bold");
            
            //Arial 10   
            Font Arial10 = new Font(FontFactory.getFont("arial"));
            Arial10.setSize(10);
            Font Arial10N = new Font(FontFactory.getFont("arial"));
            Arial10N.setSize(10);
            Arial10N.setStyle("bold");

            //Arial 12   
            Font Arial12 = new Font(FontFactory.getFont("arial"));
            Arial12.setSize(12);
            Font Arial12N = new Font(FontFactory.getFont("arial"));
            Arial12N.setSize(12);
            Arial12N.setColor(BaseColor.BLACK);
            Arial12N.setStyle("bold");

            //Arial 14   
            Font Arial14 = new Font(FontFactory.getFont("arial"));
            Arial14.setSize(14);
            Font Arial14N = new Font(FontFactory.getFont("arial"));
            Arial14N.setSize(14);
            Arial14N.setStyle("bold");

            //Arial 16   
            Font Arial16 = new Font(FontFactory.getFont("arial"));
            Arial16.setSize(16);
            Font Arial16N = new Font(FontFactory.getFont("arial"));
            Arial16N.setSize(16);
            Arial16N.setStyle("bold");

            documento = new Document(PageSize.A4, 72, 36, 80, 50);            
            
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("c:\\temp\\OficioSemLogo.pdf"));
            
            //CABEÇALHO E RODAPÉ
            //Rectangle rct = new Rectangle(36, 54, 559, 788);
            Rectangle rct = new Rectangle(50, 50, 180, 800);
            //Definimos un nombre y un tamaño para el PageBox los nombres posibles son: “crop”, “trim”, “art” and “bleed”.
            writer.setBoxSize("art", rct);
            HeaderFooter event = new HeaderFooter();
            writer.setPageEvent(event);

            //abre o documento
            documento.open();

            Paragraph LinhaBranca = new Paragraph("                                             ");
            documento.add(LinhaBranca);

            //Insere Processo no PDF            
            Paragraph processo = new Paragraph("Processo Nº            : " + of.getProcesso(), Arial12N);
            processo.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Processo)) {//Se vazio não grava
                documento.add(processo);
            }

            //////////////////////////
            //Insere INQUERITO no PDF            
            Paragraph inquerito = new Paragraph("Inquerito Policial Nº : " + of.getInquerito(), Arial12N);
            inquerito.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Inquerito)) {//Se vazio não grava
                documento.add(inquerito);
            }

            //////////////////////////
            //Insere OFICION no PDF            
            Paragraph oficion = new Paragraph("Ofício Nº                  : " + of.getOficion(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(OficioN)) {//Se vazio não grava
                documento.add(oficion);
            }

            //////////////////////////
            //Insere AUTOR no PDF            
            Paragraph autor = new Paragraph(of.getAutor(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Autor)) {//Se vazio não grava
                documento.add(autor);
            }
            //////////////////////////
            //Insere RÉU no PDF            
            Paragraph reu = new Paragraph( of.getReu(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Reu)) {//Se vazio não grava
                documento.add(reu);
            }

            //////////////////////////
            //Insere ENVOLVIDO no PDF            
            Paragraph envolvido = new Paragraph( of.getEnvolvido(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Envolvido)) {//Se vazio não grava
                documento.add(envolvido);
            }
             // insere SIGILOSO no PDF 
            Paragraph sigiloso = new Paragraph( of.getSigiloso(), Arial14N);
            oficion.setAlignment(Element.ALIGN_RIGHT);
            if (!"".equals(Sigiloso)) {
                documento.add(sigiloso);
            }
            
            //////////////////////////  
            documento.add(LinhaBranca);
            documento.add(LinhaBranca);
            
            Paragraph p7 = new Paragraph(of.getCorpo(), Arial12);
            p7.setTabSettings(new TabSettings(56f));
            p7.setLeading(13);
            p7.setAlignment(Element.ALIGN_JUSTIFIED);
            documento.add(p7);
            
            /////////////////////////////////
            Paragraph p8 = new Paragraph("BANCO DO BRASIL S.A.", Arial14N);
            p8.setAlignment(Element.ALIGN_CENTER);
            documento.add(p8);
            Paragraph p9 = new Paragraph("CENOP SERVIÇOS SÃO PAULO/SP", Arial12);
            p9.setAlignment(Element.ALIGN_CENTER);
            documento.add(p9);
            
            documento.add(LinhaBranca);
            
            
             if (!"SEM ASSINATURA".equals(Gerente)){
                 
                if("DJO2".equals(TipoGer)){
                    Paragraph p10 = new Paragraph(GerenteSetor);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo");
                    p10.setAlignment(Element.ALIGN_LEFT);   
                    documento.add(p11);
                    documento.add(LinhaBranca);
                }else if("GER_GRUPO".equals(TipoGer)){    
                      
                    Paragraph p10 = new Paragraph(Gerente);
                    //p10.setAlignment(Element.ALIGN_LEFT);
                    p10.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo",Arial10N );                                                                 
                    p11.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p11);
                    documento.add(LinhaBranca);  
                    
                }else{   
                 
                      //COLOCA O PARAGRAFO DO NOME DO GERENTE DE SETOR, EDITA O ESPAÇO ATÉ O GERENTE DE GRUPO
                    Paragraph p10 = new Paragraph(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente) + Gerente ,Arial10N);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    documento.add(LinhaBranca);
                    documento.add(p10);
                    int tipo1 = 1;

                    tipo1= GerenteSetor.length();
                    System.out.println("quant setor " + GerenteSetor.length());


                    if("NORMAL".equals(TipoGer)){
                    //Paragraph p11 = new Paragraph("Gerente de Setor                                                                                                            Gerente de grupo",Arial10 );
                    Paragraph p11 = new Paragraph("Gerente de Setor" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Gerente de Grupo" ,Arial10N );                                                                 
                    documento.add(p11);
                    documento.add(LinhaBranca);

                    }else if("DJO".equals(TipoGer)){
                    Paragraph p11 = new Paragraph("Gerente de Grupo" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Funcionário(a)" ,Arial10N );                                                                 
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    }
                }    
             }
            
            int linhas;        
            linhas = Integer.parseInt(of.getLinhas());            
            
            for (int i = 0; i < linhas; i++) {
                documento.add(LinhaBranca);
            }  
            
            //documento.add(LinhaBranca);
            //INSERE O DESTINATARIO DO PDF
            Paragraph destinatario = new Paragraph(of.getDestinario(), Arial10);
            destinatario.setAlignment(Element.ALIGN_LEFT);
            Paragraph endereco = new Paragraph(of.getEndereco(), Arial10);
            endereco.setAlignment(Element.ALIGN_LEFT);
            documento.add(endereco);
           
            if(!"".equalsIgnoreCase(of.getEmail())){
                if (of.isAnexo()){
                    //true
                   Chunk chunk1 = new Chunk(of.getEmail(), Arial10);
                   Chunk chunk2 = new Chunk(" (Com Anexo)", Arial10N);
                   
                   Paragraph email = new Paragraph();
                   email.add(chunk1);email.add(chunk2);
                   email.setAlignment(Element.ALIGN_LEFT);
                   documento.add(email);   
                    
                    
                    }else {
                    // false
                        Paragraph email2 = new Paragraph(of.getEmail(), Arial10);
                        email2.setAlignment(Element.ALIGN_LEFT);
                        documento.add(email2);
                        }   
            } 
        } finally {
            if (documento != null) {
                //fechamento do documento
                documento.close();
            }
            if (os != null) {
                //fechamento da stream de saída
                os.close();
            }              
        }

//INSERE O LOGO DO BANCO--CÓDIGO DE COLOCAR MARCA D AGUA       
        try {
              PdfReader Read_PDF_To_Watermark = new PdfReader("c:\\temp\\OficioSemLogo.pdf");
            //--   PdfReader Read_PDF_To_Watermark = new PdfReader("OficioSemLogo.pdf");
            int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
              PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("c:\\temp\\OficioComLogoTres.pdf"));
            //--PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("OficioComLogo.pdf"));  
            int i = 0;
            Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");  
            //Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");           
            
            //--Image watermark_image = Image.getInstance("LogoRetangular.png");
            watermark_image.setAbsolutePosition(72, 775);
            PdfContentByte add_watermark;
            while (i < number_of_pages) {
                i++;
                add_watermark = stamp.getUnderContent(i);
                add_watermark.addImage(watermark_image);
            }           
           stamp.close();       
           Read_PDF_To_Watermark.close();           
        } catch (DocumentException | IOException i1) {                       
        }
    }
    

 public void GerarPagDoisB(Oficio of) throws FileNotFoundException, IOException, DocumentException, InterruptedException {
       
        Oficio = of.getOficio();
        AOF = of.getAof();
        Data = of.getAof();
        Processo = of.getProcesso();
        Inquerito = of.getInquerito();
        OficioN = of.getOficion();
        Autor = of.getAutor();
        Reu = of.getReu();
        Envolvido = of.getEnvolvido();
        Corpo = of.getCorpo();
        Destinatário = of.getDestinario();
        Endereco = of.getEndereco();
        Abc = of.getAbc();
        Data = of.getData();
        Conteudo ="2ª Via PROTOCOLO " + of.getConteudo();
        Correio = of.getCorreio();
        Email= of.getEmail();  
        Gerente = of.getGerente();
        GerenteSetor = of.getGerenteSetor();
        String ABC = of.getAbc().toUpperCase();
        TipoGer = of.getTipoGer();
        EnderecoRetorno = of.getEnderecoRetorno();
        Anexo = of.isAnexo();
      

        Document documento = null;
        OutputStream os = null;
        
        try {
            //FONTES        
            FontFactory.register("c:\\windows\\fonts\\arial.ttf");
            FontFactory.register("c:\\windows\\fonts\\arialbd.ttf");

            //Arial 8   
            Font Arial8 = new Font(FontFactory.getFont("arial"));
            Arial8.setSize(8);

            //Arial 10   
            Font Arial10 = new Font(FontFactory.getFont("arial"));
            Arial10.setSize(10);
            Font Arial10N = new Font(FontFactory.getFont("arial"));
            Arial10N.setSize(10);
            Arial10N.setStyle("bold");

            //Arial 12   
            Font Arial12 = new Font(FontFactory.getFont("arial"));
            Arial12.setSize(12);
            Font Arial12N = new Font(FontFactory.getFont("arial"));
            Arial12N.setSize(12);
            Arial12N.setColor(BaseColor.BLACK);
            Arial12N.setStyle("bold");

            //Arial 14   
            Font Arial14 = new Font(FontFactory.getFont("arial"));
            Arial14.setSize(14);
            Font Arial14N = new Font(FontFactory.getFont("arial"));
            Arial14N.setSize(14);
            Arial14N.setStyle("bold");

            //Arial 16   
            Font Arial16 = new Font(FontFactory.getFont("arial"));
            Arial16.setSize(16);
            Font Arial16N = new Font(FontFactory.getFont("arial"));
            Arial16N.setSize(16);
            Arial16N.setStyle("bold");

            documento = new Document(PageSize.A4, 72, 36, 80, 50);            
            
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("c:\\temp\\OficioSemLogo.pdf"));
             //--            new FileOutputStream("OficioSemLogo.pdf"));

            //CABEÇALHO E RODAPÉ
            //  Rectangle rct = new Rectangle(36, 54, 559, 788);
            Rectangle rct = new Rectangle(50, 50, 180, 800);
            //Definimos un nombre y un tamaño para el PageBox los nombres posibles son: “crop”, “trim”, “art” and “bleed”.
            writer.setBoxSize("art", rct);
            HeaderFooter event = new HeaderFooter();
            writer.setPageEvent(event);

            //abre o documento
            documento.open();
            Paragraph LinhaBranca = new Paragraph("                                             ");
            documento.add(LinhaBranca);

            //Insere Processo no PDF            
            Paragraph processo = new Paragraph("Processo Nº            : " + of.getProcesso(), Arial12N);
            processo.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Processo)) {//Se vazio não grava
                documento.add(processo);
            }

            //////////////////////////
            //Insere INQUERITO no PDF            
            Paragraph inquerito = new Paragraph("Inquerito Policial Nº : " + of.getInquerito(), Arial12N);
            inquerito.setAlignment(Element.ALIGN_LEFT);

            if (!"".equals(Inquerito)) {//Se vazio não grava
                documento.add(inquerito);
            }

            //////////////////////////
            //Insere OFICION no PDF            
            Paragraph oficion = new Paragraph("Ofício Nº                  : " + of.getOficion(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(OficioN)) {//Se vazio não grava
                documento.add(oficion);
            }

            //////////////////////////
            //Insere AUTOR no PDF            
            Paragraph autor = new Paragraph(of.getAutor(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Autor)) {//Se vazio não grava
                documento.add(autor);
            }
            //////////////////////////
            //Insere RÉU no PDF            
            Paragraph reu = new Paragraph( of.getReu(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Reu)) {//Se vazio não grava
                documento.add(reu);
            }

            //////////////////////////
            //Insere ENVOLVIDO no PDF            
            Paragraph envolvido = new Paragraph( of.getEnvolvido(), Arial12N);
            oficion.setAlignment(Element.ALIGN_LEFT);
            if (!"".equals(Envolvido)) {//Se vazio não grava
                documento.add(envolvido);
            }
             // insere SIGILOSO no PDF 
            Paragraph sigiloso = new Paragraph( of.getSigiloso(), Arial14N);
            oficion.setAlignment(Element.ALIGN_RIGHT);
            if (!"".equals(Sigiloso)) {
                documento.add(sigiloso);
            }
            
            //////////////////////////  
            documento.add(LinhaBranca);
            documento.add(LinhaBranca);  
            Paragraph p7 = new Paragraph(of.getCorpo(), Arial12);
            p7.setTabSettings(new TabSettings(56f));
            p7.setLeading(13);
            p7.setAlignment(Element.ALIGN_JUSTIFIED);
            documento.add(p7);
            
            /////////////////////////////////
            Paragraph p8 = new Paragraph("BANCO DO BRASIL S.A.", Arial14N);
            p8.setAlignment(Element.ALIGN_CENTER);
            documento.add(p8);
            Paragraph p9 = new Paragraph("CENOP SERVIÇOS SÃO PAULO/SP", Arial12);
            p9.setAlignment(Element.ALIGN_CENTER);
            documento.add(p9);
            documento.add(LinhaBranca);
            
            
             if (!"SEM ASSINATURA".equals(Gerente)){
                 
                 if("DJO2".equals(TipoGer)){
                    Paragraph p10 = new Paragraph(GerenteSetor);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo");
                    p10.setAlignment(Element.ALIGN_LEFT);   
                    documento.add(p11);
                    documento.add(LinhaBranca);
                 }else if("GER_GRUPO".equals(TipoGer)){    
                      
                    Paragraph p10 = new Paragraph(Gerente);
                    //p10.setAlignment(Element.ALIGN_LEFT);
                    p10.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p10);
                    Paragraph p11 = new Paragraph("Gerente de Grupo",Arial10N );                                                                 
                    p11.setAlignment(Element.ALIGN_CENTER);
                    documento.add(p11);
                    documento.add(LinhaBranca);   
                    
                 }else{   
                      //COLOCA O PARAGRAFO DO NOME DO GERENTE DE SETOR, EDITA O ESPAÇO ATÉ O GERENTE DE GRUPO
                    Paragraph p10 = new Paragraph(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente) + Gerente ,Arial10N);
                    p10.setAlignment(Element.ALIGN_LEFT);
                    documento.add(LinhaBranca);
                    documento.add(p10);

                    int tipo1 = 1;
                    tipo1= GerenteSetor.length();
                    System.out.println("quant setor " + GerenteSetor.length());
                    
                    if("NORMAL".equals(TipoGer)){
                    //Paragraph p11 = new Paragraph("Gerente de Setor                                                                                                            Gerente de grupo",Arial10 );
                    Paragraph p11 = new Paragraph("Gerente de Setor" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Gerente de Grupo" ,Arial10N );                                                                 
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    //
                    }else if("DJO".equals(TipoGer)){
                    Paragraph p11 = new Paragraph("Gerente de Grupo" + EditaEspaco2(GerenteSetor + EditaEspaco1(GerenteSetor + Gerente),tipo1) + "Funcionário(a)" ,Arial10N );                                                                 
                    documento.add(p11);
                    documento.add(LinhaBranca);
                    }
                 }    
            }
            int linhas;
            linhas = Integer.parseInt(of.getLinhas());            
            
            for (int i = 0; i < linhas; i++) {
                documento.add(LinhaBranca);
            }
            
            //documento.add(LinhaBranca);
            //INSERE O DESTINATARIO DO PDF
            Paragraph destinatario = new Paragraph(of.getDestinario(), Arial10);
            destinatario.setAlignment(Element.ALIGN_LEFT);
        
            //INSERE O ENDERECO DO PDF
            Paragraph endereco = new Paragraph(of.getEndereco(), Arial10);
            endereco.setAlignment(Element.ALIGN_LEFT);
            documento.add(endereco);
           
           if(!"".equalsIgnoreCase(of.getEmail())){
                if (of.isAnexo()){
                    //true
                   Chunk chunk1 = new Chunk(of.getEmail(), Arial10);
                   Chunk chunk2 = new Chunk(" (Com Anexo)", Arial10N);
                   
                   Paragraph email = new Paragraph();
                   email.add(chunk1);email.add(chunk2);
                   email.setAlignment(Element.ALIGN_LEFT);
                   documento.add(email);
                }else {
                    // false
                        Paragraph email2 = new Paragraph(of.getEmail(), Arial10);
                        email2.setAlignment(Element.ALIGN_LEFT);
                        documento.add(email2);
                } 
            }
            
        } finally {
            if (documento != null) {
                //fechamento do documento
                documento.close();
            }
            if (os != null) {
                //fechamento da stream de saída
                os.close();
            }              
        }

        //INSERE O LOGO DO BANCO--CÓDIGO DE COLOCAR MARCA D AGUA       
        try {
              PdfReader Read_PDF_To_Watermark = new PdfReader("c:\\temp\\OficioSemLogo.pdf");
                //--   PdfReader Read_PDF_To_Watermark = new PdfReader("OficioSemLogo.pdf");
            int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
              PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("c:\\temp\\OficioComLogoDoisB.pdf"));
                //--         PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("OficioComLogo.pdf"));  
            int i = 0;
            //Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png"); 
            Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");  
            
            //--Image watermark_image = Image.getInstance("LogoRetangular.png");
            watermark_image.setAbsolutePosition(72, 775);
            PdfContentByte add_watermark;
            while (i < number_of_pages) {
                i++;
                add_watermark = stamp.getUnderContent(i);
                add_watermark.addImage(watermark_image);
            }           
           stamp.close();       
           Read_PDF_To_Watermark.close();           
        } catch (DocumentException | IOException i1) {                       
        }          
        
  
    }
    
    
//    public void colocaLogo() throws IOException, DocumentException {
//                  //INSERE O LOGO DO BANCO--CÓDIGO DE COLOCAR MARCA D AGUA       
//        try {
//            //   PdfReader Read_PDF_To_Watermark = new PdfReader("c:\\temp\\OficioSemLogo.pdf");
//            PdfReader Read_PDF_To_Watermark = new PdfReader("OficioSemLogo.pdf");
//            int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
//            //  PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("c:\\temp\\OficioComLogo.pdf"));
//            PdfStamper stamp = new PdfStamper(Read_PDF_To_Watermark, new FileOutputStream("OficioComLogo.pdf"));  
//            int i = 0;
//            //Image watermark_image = Image.getInstance("c:\\temp\\LogoRetangular.png");
//            Image watermark_image = Image.getInstance("LogoRetangular.png");
//            watermark_image.setAbsolutePosition(72, 775);
//            PdfContentByte add_watermark;
//            while (i < number_of_pages) {
//                i++;
//                add_watermark = stamp.getUnderContent(i);
//                add_watermark.addImage(watermark_image);
//            }
//           
//           stamp.close();          
//           
//        } catch (DocumentException | IOException i1) {
//                       
//        }       
//    }


    public void GerarAR(Oficio of) throws FileNotFoundException, IOException, DocumentException {
         EnderecoRetorno = of.getEnderecoRetorno();
        // TODO code application logic here
        //FONTES        
        FontFactory.register("c:\\windows\\fonts\\arial.ttf");
        FontFactory.register("c:\\windows\\fonts\\arialbd.ttf");

        //Arial 6   
        Font Arial6 = new Font(FontFactory.getFont("arial"));
        Arial6.setSize(6);

        //Arial 7   
        Font Arial7 = new Font(FontFactory.getFont("arial"));
        Arial7.setSize(7);
        

        //Arial 8   
        Font Arial8 = new Font(FontFactory.getFont("arial"));
        Arial8.setSize(8);
        
        Font Arial8N = new Font(FontFactory.getFont("arial"));
        Arial8N.setSize(8);
        Arial8N.setStyle("bold");

        //Arial 10   
        Font Arial10 = new Font(FontFactory.getFont("arial"));
        Arial10.setSize(10);

        Font Arial10N = new Font(FontFactory.getFont("arial"));
        Arial10N.setSize(10);
        Arial10N.setStyle("bold");

        //Arial 12   
        Font Arial12 = new Font(FontFactory.getFont("arial"));
        Arial12.setSize(12);

        Font Arial12N = new Font(FontFactory.getFont("arial"));
        Arial12N.setSize(12);
        Arial12N.setColor(BaseColor.BLACK);
        Arial12N.setStyle("bold");

        //Arial 14   
        Font Arial14 = new Font(FontFactory.getFont("arial"));
        Arial14.setSize(14);

        Font Arial14N = new Font(FontFactory.getFont("arial"));
        Arial14N.setSize(14);
        Arial14N.setStyle("bold");

        //Arial 16   
        Font Arial16 = new Font(FontFactory.getFont("arial"));
        Arial16.setSize(16);

        Font Arial16N = new Font(FontFactory.getFont("arial"));
        Arial16N.setSize(16);
        Arial16N.setStyle("bold");

        // Create output PDF
        
      
        // Document document = new Document(PageSize.A4);
         Document document = new Document(PageSize.A4, 80, 20, 80, 50);
        
        PdfWriter writer = PdfWriter.getInstance(document,
             //--   new FileOutputStream("ARF.pdf"));
                
                new FileOutputStream("c:\\temp\\ARF.pdf"));

        document.open();
        PdfContentByte cb = writer.getDirectContent();

         // Load existing PDF
        //--  PdfReader reader = new PdfReader("AR.pdf");
        
        PdfReader reader = new PdfReader("c:\\temp\\ARM.pdf");        
      
         
        PdfImportedPage page = writer.getImportedPage(reader, 1);

        document.newPage();
        cb.addTemplate(page, 0, -35);

        Paragraph linhaBranca = new Paragraph("                                  ");
        // Setando o alinhamento p/ o centro
        linhaBranca.setAlignment(Paragraph.ALIGN_LEFT);
        
        String chave = System.getProperty("user.name").toUpperCase();

        
        
        //Se Oficio não tiver AOF gravar número do oficio CENOP
        Paragraph p ;//= new Paragraph();
        
        //endereço no começo
        if (!"".equals(of.getAof())) {
             p = new Paragraph("N.º AOF : " + of.getAof()+ "  GSV N.º : " + of.getOficio(),Arial8N);
             p.setAlignment(Paragraph.ALIGN_LEFT);            
        }else{
              p = new Paragraph("GSV N.º : " + of.getOficio(), Arial8N);
              p.setAlignment(Paragraph.ALIGN_LEFT);            
        }
        

        Paragraph pdestinatario = new Paragraph( of.getEndereco(), Arial7);
        // Setando o alinhamento p/ o centro
        pdestinatario.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph p1 = new Paragraph("ENDEREÇO PARA DEVOLUÇÃO DO AR ", Arial8N);
        // Setando o alinhamento p/ o centro
        p1.setAlignment(Paragraph.ALIGN_LEFT);
        //cmbEnderecoRetorno.addItem("Av. São João");
        Paragraph p2;
         //ENDEREÇOS DE RETORNO
        if ("Boa Vista".equalsIgnoreCase(EnderecoRetorno)) {
            p2 = new Paragraph("Banco do Brasil S.A.\n"
                    + "CENOP SERVIÇOS/SP – Central de Ofícios\n"
                    + "Rua Boa Vista, 254 – 14º andar\n"
                    + "CEP: 01014-907 - São Paulo/SP", Arial7);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
         
        }else if ("Av. São João".equalsIgnoreCase(EnderecoRetorno)) {
                    
            p2 = new Paragraph("Banco do Brasil S.A.\n"
                    + "CENOP SERVIÇOS/SP - Central de Ofícios - Av. São João, N°32, 18° andar, Centro\n"
                    + "CEP: 01036-900 - São Paulo/SP", Arial7);
            p2.setAlignment(Paragraph.ALIGN_LEFT);        
        }else if ("Vila Mariana".equalsIgnoreCase(EnderecoRetorno)) {
                    
            p2 = new Paragraph("Banco do Brasil S.A.\n"
                    + "CENOP SERVIÇOS/SP – DJO - Av. Doutor Altino Arantes, 1297 Térreo\n"
                    + "CEP: 04042035 - São Paulo/SP", Arial7);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
        }else if ("Vila Mariana - Central".equalsIgnoreCase(EnderecoRetorno)) {            
            p2 = new Paragraph("Banco do Brasil S.A.\n"
                    + "CENOP SERVIÇOS/SP – Central de Ofícios - Av. Doutor Altino Arantes, 1297 Térreo\n"
                    + "CEP: 04042035 - São Paulo/SP", Arial7);
            p2.setAlignment(Paragraph.ALIGN_LEFT);       
        }else{
            p2 = new Paragraph("Banco do Brasil S.A.\n"
                    + "CENOP SERVIÇOS/SP - Central de Ofícios - Av. São João, N°32, 18° andar, Centro\n"
                    + "CEP: 01036-900 - São Paulo/SP", Arial7);
            p2.setAlignment(Paragraph.ALIGN_LEFT);        
        }
             
        
     
        Paragraph p3 = new Paragraph("                                                                                                              "+chave, Arial6);
        // Setando o alinhamento p/ o centro
        // p2.setTabSettings(new TabSettings(56f));        
        // p2.setLeading(13);
        p3.setAlignment(Paragraph.ALIGN_CENTER);
        
       
        String endereco;
        endereco = of.getEndereco();
        endereco = tabulacaoDoTexto(endereco);

        Paragraph pdendereco = new Paragraph("" + endereco, Arial8);
        // Setando o alinhamento p/ o centro
        pdendereco.setTabSettings(new TabSettings(56f));         
        pdendereco.setLeading(13);
        pdendereco.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(p3);
        document.add(p);
        document.add(pdestinatario);
        document.add(linhaBranca);
        document.add(p1);
        document.add(p2);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(linhaBranca);
        document.add(p);
        document.add(pdestinatario);
        document.close();          
     
    //        Process k = Runtime.getRuntime().exec("cmd.exe /C c:\\temp\\ARF.pdf");        
    }
    private String tabulacaoDoTexto(String line) {
        String[] tokens = line.split("\t");
        String correctionString = "";
        
      try {
             
        for (int i = 0; i < tokens.length - 1; i++) {
            correctionString += tokens[i];

            if (tokens[i].trim().equals("")) {
                correctionString += emptyString(8);
            } else {
                correctionString += emptyString(8 - (tokens[i].length() % 8));
            }
        }
        correctionString += tokens[tokens.length - 1];
        System.out.println(correctionString);
            
        } catch (Exception e) {
        }
       
        
        
        return correctionString;
        
        
        
        
    }

    private String emptyString(int size) {
        String format = "";
        for (int i = 0; i < size; i++) {
            format += " ";
        }
        return format;
    }

    static class HeaderFooter extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            //FONTES
            FontFactory.register("c:\\windows\\fonts\\arial.ttf");
            FontFactory.register("c:\\windows\\fonts\\arialbd.ttf");

            //Arial 6
            Font Arial6 = new Font(FontFactory.getFont("arial"));
            Arial6.setSize(6);

            //Arial 8
            Font Arial8 = new Font(FontFactory.getFont("arial"));
            Arial8.setSize(8);

            Font Arial8N = new Font(FontFactory.getFont("arial"));
            Arial8N.setSize(8);
            Arial8N.setStyle("bold");
            
            Font Arial9 = new Font(FontFactory.getFont("arial"));
            Arial9.setSize(9);
            
            Font Arial9N = new Font(FontFactory.getFont("arial"));
            Arial9N.setSize(9);
            Arial9N.setStyle("bold");

            //Arial 10
            Font Arial10 = new Font(FontFactory.getFont("arial"));
            Arial10.setSize(10);

            Font Arial10N = new Font(FontFactory.getFont("arial"));
            Arial10N.setSize(10);
            Arial10N.setStyle("bold");

            //Arial 12
            Font Arial12 = new Font(FontFactory.getFont("arial"));
            Arial12.setSize(12);

            Font Arial12N = new Font(FontFactory.getFont("arial"));
            Arial12N.setSize(12);
            Arial12N.setColor(BaseColor.BLACK);
            Arial12N.setStyle("bold");

            //Arial 14
            Font Arial14 = new Font(FontFactory.getFont("arial"));
            Arial14.setSize(14);

            Font Arial14N = new Font(FontFactory.getFont("arial"));
            Arial14N.setSize(14);
            Arial14N.setStyle("bold");

            //Arial 16
            Font Arial16 = new Font(FontFactory.getFont("arial"));
            Arial16.setSize(16);

            Font Arial16N = new Font(FontFactory.getFont("arial"));
            Arial16N.setSize(16);
            Arial16N.setStyle("bold");

            Rectangle rect = writer.getBoxSize("art");

            //Cabeçalho
                         
//
//            if ("".equals(AOF)) {
////                ColumnText.showTextAligned(writer.getDirectContent(),
////                Element.ALIGN_RIGHT, new Phrase(Data, Arial10),
////                rect.getRight() + 370, rect.getTop() - 15, 0);
//                
//                ColumnText.showTextAligned(writer.getDirectContent(),
//                        Element.ALIGN_RIGHT, new Phrase("CORREIO :" + Correio, Arial10N),
//                        rect.getRight() + 370, rect.getTop() - 15, 0);
//                ColumnText.showTextAligned(writer.getDirectContent(),
//                        Element.ALIGN_RIGHT, new Phrase(Data, Arial10),
//                        rect.getRight() + 370, rect.getTop() - 30, 0);
//                
//            } else {
//                ColumnText.showTextAligned(writer.getDirectContent(),
//                        Element.ALIGN_RIGHT, new Phrase("AOF :" + AOF, Arial10N),
//                        rect.getRight() + 370, rect.getTop() - 15, 0);
//                ColumnText.showTextAligned(writer.getDirectContent(),
//                        Element.ALIGN_RIGHT, new Phrase(Data, Arial10),
//                        rect.getRight() + 370, rect.getTop() - 30, 0);
//            }
            
            
            
            

                
             if("".equals(Correio)){ 
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("OFICIO CENOP SJ N.º : " + Oficio, Arial10N),
                    rect.getRight() + 370, rect.getTop(), 0);     
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("AOF : " + AOF, Arial10N),
                    rect.getRight() + 370, rect.getTop() - 15, 0);
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase(Data, Arial10),
                    rect.getRight() + 370, rect.getTop() - 30, 0);        
             }else{ 
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("OFICIO CENOP SJ N.º : " + Oficio, Arial9N),
                    rect.getRight() + 370, rect.getTop(), 0);     
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("AOF : " + AOF, Arial9N),
                    rect.getRight() + 370, rect.getTop() - 10, 0);
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("CORREIO : " + Correio, Arial9N),
                    rect.getRight() + 370, rect.getTop() - 20, 0);
                    ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase(Data, Arial9N),
                    rect.getRight() + 370, rect.getTop() - 33, 0);
             }
                 
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("  ", Arial12),
                    rect.getRight() + 200, rect.getTop() - 45, 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("  ", Arial12),
                    rect.getRight() + 200, rect.getTop() - 60, 0);
            //Rodapé            
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("______________________________________________"
                            + "________________________________", Arial10N),
                    (rect.getLeft() + rect.getRight()) + 65, rect.getBottom() - 0, 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("CENOP SERVIÇOS/SP - CENTRAL DE OFÍCIOS", Arial8N),
                    //Element.ALIGN_CENTER, new Phrase("CENOP SERVIÇOS Judiciais SÃO PAULO/SP", Arial8N),
                    (rect.getLeft() + rect.getRight()) + 70, rect.getBottom() - 15, 0);
            //ColumnText.showTextAligned(writer.getDirectContent(),
              //      Element.ALIGN_CENTER, new Phrase("Rua Boa Vista, 254, 14º andar - Centro - CEP 01014-000 - São Paulo-SP", Arial8),
                //    (rect.getLeft() + rect.getRight()) + 80, rect.getBottom() - 28, 0);
            
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(Conteudo, Arial10),
                    (rect.getLeft() + rect.getRight()) -140, rect.getBottom() - 38, 0);
            
            
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(Abc, Arial6),
                    (rect.getLeft() + rect.getRight()) +300, rect.getBottom() - 38, 0);    
        }
    }
    
    
     public String EditaEspaco1(String texto) {
        String texto1 = "";
        
        if(texto.length()>70){
        
                while (texto1.length() < 30 ) {
                texto1 = texto1 + " ";
                }
        }else if ( texto.length()> 60){
         
                while (texto1.length() < 40 ) {
                texto1 = texto1 + " ";
                }      
        }else if ( texto.length()> 50){
                while (texto1.length() < 50 ) {
                texto1 = texto1 + " ";
                 }
         }else if ( texto.length()> 45){
                while (texto1.length() < 60 ) {
                texto1 = texto1 + " ";
                 }             
        }else if ( texto.length()> 40){
                 while (texto1.length() < 70 ) {
                texto1 = texto1 + " ";
             }
       
         }else if ( texto.length()> 35){
                 while (texto1.length() < 90 ) {
                texto1 = texto1 + " ";
             }                
          }else if ( texto.length()> 30){
                 while (texto1.length() < 95 ) {
                texto1 = texto1 + " ";
             }   
                 
        }
        return texto1;
    } 


 public String EditaEspaco2(String text, int tipo) {
     int numero = 0;
     
     numero = text.length() + tipo;
     String texto2 = "";

     
     if (tipo==30){   
     numero = text.length() + 5;
     
     }else if (tipo==18){
     numero = text.length()-6 ;
     
     
     }else if (tipo==22){
     numero = text.length()-1 ;
     
     }else if (tipo==23){
     numero = text.length()+ 1;
            
     }else if (tipo==26){
     numero = text.length()+3 ;
     }
     
     while (texto2.length() < numero ) {
                texto2 = texto2 + " ";
     }
     return texto2;
}
}
