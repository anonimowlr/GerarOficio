/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import DAO.Conexao;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import pdf.GeraOficio;
import pdf.Oficio;

/**
 *
 * @author f7057419
 */
public final class FrmPrincipal extends javax.swing.JFrame {

    //Variável para escolha da tabela gerador_oficio ou gerador_oficio2
    // static  public  String tabelaGerador_oficio="gerador_oficios";
    static public String tabelaGerador_oficio = "gerador_oficios2";

    public void carregaEndereco(String endereco) {
        this.txtEndereco.setText(endereco);
    }

    /**
     * Creates new form NewJFrame
     *
     * @throws java.io.IOException
     */
    public FrmPrincipal() throws IOException, Exception {
        Integer mes;
       
        //Verifica a versão do programa, não abre se for versão antiga.
        //e abre a pasta do programa, para o usuário copiar 
        buscaVersao("GeradorOficios");

        //Programa não abre direto da rede
        String local = System.getProperty("user.dir");
        System.out.println("LOCAL");
        System.out.println(local);
        
        local = local.substring(0, 1);

       
        if ("G".equals(local)) {
            JOptionPane.showMessageDialog(rootPane, "Favor copiar o programa para seu computador. ");
            System.exit(0);
        }

        String mesExtenso = null;
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        Integer largura;
        Integer altura;
        largura = d.width;
        largura = (largura / 2) - (1366 / 2);
        altura = d.height;
        altura = (altura / 2) - (768 / 2);
   
        initComponents();
        
        JFormattedTextField formattedText = new JFormattedTextField( new DefaultFormatter());
         txtAOF.add(formattedText);
        
        MaskFormatter maskCorreio = new MaskFormatter("####/########");
        MaskFormatter maskTxtDataOficio = new MaskFormatter("##/##/####");
        maskTxtDataOficio.setPlaceholderCharacter('_');
        maskTxtDataOficio.install(jFormattedTextFieldPrazo);
        maskCorreio.install((JFormattedTextField) txtCorreio);
        MaskFormatter maskAOF = new MaskFormatter("####*#########");
        maskAOF.install((JFormattedTextField) txtAOF);
        
        jFormattedTextFieldPrazo.setVisible(false);
        jLabel20.setVisible(false);
        
        //COMBO DE ENDEREÇOS DE RETORNO
        //cmbEnderecoRetorno.addItem("Boa Vista");
        cmbEnderecoRetorno.addItem("Av. São João");
        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
        cmbEnderecoRetorno.addItem("Vila Mariana");
        
        //Modelos adicionados em 201600704 Denise 
        cmbRespostas.addItem("Selecione o modelo:");
        cmbRespostas.addItem("Ações - BB-BI não custodiante");
        cmbRespostas.addItem("Ações - Bloqueio com Custódia");
        cmbRespostas.addItem("Ações - Bloqueio pelo Escriturador Anterior");
        cmbRespostas.addItem("Ações - Bloqueio Subsequente");
        cmbRespostas.addItem("Ações - Bloqueio Várias Pessoas");
        cmbRespostas.addItem("Ações - Bloqueio");
        cmbRespostas.addItem("Ações - Cadastro Solicitação Docs");
        cmbRespostas.addItem("Ações - Conta Judicial Existente");
        cmbRespostas.addItem("Ações - Contrato Linha Telefonica");
        cmbRespostas.addItem("Ações - Custódia");
        cmbRespostas.addItem("Ações - Demais não localizado");
        cmbRespostas.addItem("Ações - Desbloqueio");
        cmbRespostas.addItem("Ações - Grupamento OI");
        cmbRespostas.addItem("Ações - Inventário");
        cmbRespostas.addItem("Ações - Portador PETRO");
        cmbRespostas.addItem("Ações - Rendimento Outros Bancos");
        cmbRespostas.addItem("Ações - Rendimentos Com Bloqueio");
        cmbRespostas.addItem("Ações - Rendimentos Com Posição");
        cmbRespostas.addItem("Ações - Rendimentos Sem Posição");
        cmbRespostas.addItem("Ações - Saldo Data Óbito");
        cmbRespostas.addItem("Ações - Sem Posição");
        cmbRespostas.addItem("Ações - Telebrás");
        cmbRespostas.addItem("Ações - Títulos Endossáveis");
        cmbRespostas.addItem("Ações - Transferencia BB");
        cmbRespostas.addItem("Ações - Transferencia CEF");
        cmbRespostas.addItem("Ações - Venda");
        
        cmbRespostas.addItem("-------------------------------------------------------------");
        
        cmbRespostas.addItem("BLOQUEIO DE VALOR - CONTA SEM SALDO");
        cmbRespostas.addItem("IMPOSSIBILIDADE CLIENTE NÃO CADASTRADO");
        cmbRespostas.addItem("IMPOSSIBILIDADE DE BLOQUEIO");
        cmbRespostas.addItem("MODELO 421 BLOQ, DESBLOQ DE SALDO DE CONTAS");
        cmbRespostas.addItem("MODELO 421 DEPOSITO JUDICIAL");
        cmbRespostas.addItem("OF 15910655 -BLOQ CONTIN - EGT - Rio do Pires - AO JUIZO");
        cmbRespostas.addItem("VALOR BLOQUEIO TOTAL-PARCIAL");
        cmbRespostas.addItem("-------------------------------------------------------------");

        // fim modelos da Rosa - Sandra
        
        // Modelos Atualizados pelo funcionário Diego Thomas 
        cmbRespostas.addItem("Abertura de conta corrente");
        cmbRespostas.addItem("Acessibilidade");
        cmbRespostas.addItem("Aliança do Brasil");
        cmbRespostas.addItem("Alienação fiduciária - Falta dados");
        cmbRespostas.addItem("Alienação fiduciária COM processo");
        cmbRespostas.addItem("Alienação fiduciária SEM processo");
        cmbRespostas.addItem("Alienação fiduciária - Gravame baixado");
        cmbRespostas.addItem("Ativos - Operações cedidas");
        cmbRespostas.addItem("Baixa restrição SERASA e SCPC");
        cmbRespostas.addItem("Banco 24 Horas");
        cmbRespostas.addItem("Boleto - Carteira 18");
        cmbRespostas.addItem("Boleto - Desconto TAA");
        cmbRespostas.addItem("Brasilprev");
        cmbRespostas.addItem("Cartão - Alelo");
        cmbRespostas.addItem("Cartão - Uso de senha pessoal");
        cmbRespostas.addItem("CCF - Exclusão de restrição");
        cmbRespostas.addItem("CDC - Crédito adquirido - 4777");
        cmbRespostas.addItem("CDC - Outras instituições");
        cmbRespostas.addItem("CFTV - Disponibilização de imagens");
        cmbRespostas.addItem("CFTV - Portaria");
        cmbRespostas.addItem("Cheque - Destruição");
        cmbRespostas.addItem("Cheque - Sua remessa");
        cmbRespostas.addItem("Citação recebida indevidamente - Aliança do Brasil");
        cmbRespostas.addItem("CPF - Homônimos");
        cmbRespostas.addItem("Curatela");
        cmbRespostas.addItem("Custo microfilmagem");
        cmbRespostas.addItem("Dados cadastrais 1");
        cmbRespostas.addItem("Dados cadastrais 2");
        cmbRespostas.addItem("Dados cadastrais - Boleto");
        cmbRespostas.addItem("Dados cadastrais - Conta Fácil");
        cmbRespostas.addItem("Dados cadastrais - Ficha cadastral");
        cmbRespostas.addItem("Defensor Público - Inventário extrajudicial");
        cmbRespostas.addItem("Depósito acima de 10 mil - Identificação");
        cmbRespostas.addItem("Depósito envelope");
        cmbRespostas.addItem("Devolução de ofício");
        cmbRespostas.addItem("Dilação de prazo");
        cmbRespostas.addItem("Encerramento escrituração Petro");
        cmbRespostas.addItem("Endereços");
        cmbRespostas.addItem("Envio de extrato");
        cmbRespostas.addItem("Exclusão CCF");
        cmbRespostas.addItem("Expurgo - Cheque TED DOC");
        cmbRespostas.addItem("Expurgo - Conta encerrada");
        cmbRespostas.addItem("FGTS - Falta dados");
        cmbRespostas.addItem("FGTS - Prescrição trintenária");
        cmbRespostas.addItem("FIES");
        cmbRespostas.addItem("Fraude - Cartão");
        cmbRespostas.addItem("Fraude - Dados IP e beneficiário de transferência");
        cmbRespostas.addItem("Fraude - ROI 1");
        cmbRespostas.addItem("Fraude - ROI 2");
        cmbRespostas.addItem("Improbidade administrativa");
        cmbRespostas.addItem("INSS - Renovação de senha");
        cmbRespostas.addItem("Inventário");
        cmbRespostas.addItem("IR - Devolvido à Receita");
        cmbRespostas.addItem("IR - Não resgatado");
        cmbRespostas.addItem("Ofício já respondido");
        cmbRespostas.addItem("Operação de outra insituição");
        cmbRespostas.addItem("Ordem de pagamento");
        cmbRespostas.addItem("Ourocap");
        cmbRespostas.addItem("Parcial");
        cmbRespostas.addItem("PASEP");
        cmbRespostas.addItem("PIS e FGTS - CEF - Falta dados");
        cmbRespostas.addItem("PIS e FGTS - CEF");
        cmbRespostas.addItem("Portaria - Solicitação");
        cmbRespostas.addItem("Protesto preferência - Operação liquidada");
        cmbRespostas.addItem("Quebra de sigilo - Comparecer à agência");
        cmbRespostas.addItem("Quebra de sigilo - Procurador Assistente");
        cmbRespostas.addItem("Quebra de sigilo - Responsável Expediente");
        cmbRespostas.addItem("Quebra de sigilo");
        cmbRespostas.addItem("Reversão INSS - Abaixo de cinco anos");
        cmbRespostas.addItem("Reversão INSS - Estorno");
        cmbRespostas.addItem("Reversão INSS - Prescrição");
        cmbRespostas.addItem("Seguridade");
        cmbRespostas.addItem("Seguro Crédito Protegido");
        cmbRespostas.addItem("Sem cadastro");
        cmbRespostas.addItem("Sem operações");
        cmbRespostas.addItem("Solicitação de matrícula");
        cmbRespostas.addItem("TAA imagens - Desativado");
        cmbRespostas.addItem("TAA imagens - Falta dados");
        cmbRespostas.addItem("Territorialidade");
        cmbRespostas.addItem("Transferência cliente para conta judicial");
        cmbRespostas.addItem("Transferência online");
        cmbRespostas.addItem("URV");
       
        cmbRespostas.addItem("-------------------------------------------------------------");
        cmbRespostas.addItem("Continuado - Bloqueio de Conta Salário");
        cmbRespostas.addItem("Continuado - Bloqueio e Transf Conta Judicial");
        cmbRespostas.addItem("Continuado - Bloqueio e Transf Outro Banco");
        cmbRespostas.addItem("Continuado - Bloqueio e Transf para CC");
        cmbRespostas.addItem("Continuado - Bloqueio sem Rendimentos");
        cmbRespostas.addItem("Continuado - Bloqueio valor Parcial");
        cmbRespostas.addItem("Continuado - Encerramento Prazo");
        cmbRespostas.addItem("Continuado - Encerramento Salário");
        cmbRespostas.addItem("Continuado - FPM Porcentagem");
        cmbRespostas.addItem("Continuado - FPM Valor Fixo");
        cmbRespostas.addItem("Continuado - FPM Variável");
        cmbRespostas.addItem("Continuado - Informação Valor total da execução");
        cmbRespostas.addItem("Continuado - Sem Saldo para Bloqueio");
        cmbRespostas.addItem("Continuado - Sem Saldo Sugestão");
        cmbRespostas.addItem("-------------------------------------------------------------");
        cmbRespostas.addItem("TRABALHISTA - ABERTURA CONTA DJO");
        cmbRespostas.addItem("TRABALHISTA - ABERTURA DE CONTA JUDICIAL");
        cmbRespostas.addItem("TRABALHISTA - ABERTURA DE CONTA POUPANCA MENOR");
        cmbRespostas.addItem("TRABALHISTA - ANEXO NÃO ENCAMINHADO");
        cmbRespostas.addItem("TRABALHISTA - ASSENTAMENTOS PESQUISA");
        cmbRespostas.addItem("TRABALHISTA - BENEFICIÁRIO FALECIDO");
        cmbRespostas.addItem("TRABALHISTA - BRASÍLIA-DF LEVANTAMENTOS EFETUADOS");
        cmbRespostas.addItem("TRABALHISTA - CHEQUE DEVOLVIDO");
        cmbRespostas.addItem("TRABALHISTA - CONGÊNERE");
        cmbRespostas.addItem("TRABALHISTA - CONTA CEF");
        cmbRespostas.addItem("TRABALHISTA - CONTA NÃO LOCALIZADA");
        cmbRespostas.addItem("TRABALHISTA - CONTA NÃO VINCULADA AO PROCESSO E NEM A VARA");
        cmbRespostas.addItem("TRABALHISTA - CONTA VINCULADA A OUTRA VARA"); 
        cmbRespostas.addItem("TRABALHISTA - CONVÊNIO");   
        cmbRespostas.addItem("TRABALHISTA - CPF CNPJ PIS");
        cmbRespostas.addItem("TRABALHISTA - CPF CNPJ NÃO PERTENCE");
        cmbRespostas.addItem("TRABALHISTA - CPF DIVERGENTE");
        cmbRespostas.addItem("TRABALHISTA - DADOS IMPOSTO DE RENDA");
        cmbRespostas.addItem("TRABALHISTA – DATA DE DEPÓSITO DIVERGENTE");
        cmbRespostas.addItem("TRABALHISTA - DESCONSIDERAMOS OFÍCIO");
        cmbRespostas.addItem("TRABALHISTA - DESTINATÁRIO CEF");
        cmbRespostas.addItem("TRABALHISTA – DEVOLUÇÃO DE OFICIO ENCAMINHADO A CEF");
        cmbRespostas.addItem("TRABALHISTA - DOCUMENTO DE TRANSF. INTERBANCARIA");
        cmbRespostas.addItem("TRABALHISTA - DUPLICIDADE");
        cmbRespostas.addItem("TRABALHISTA - ESCLARECIMENTO DEPÓSITO RECURSAL");
        cmbRespostas.addItem("TRABALHISTA - ESTORNO SEM ALÇADA");
        cmbRespostas.addItem("TRABALHISTA - EXTRATO ANALÍTICO");
        cmbRespostas.addItem("TRABALHISTA - EXTRATO UNIFICADO");
        cmbRespostas.addItem("TRABALHISTA – FGTS – DADOS FALTANTES");
        cmbRespostas.addItem("TRABALHISTA - GERENCIADOR FINANCEIRO");
        cmbRespostas.addItem("TRABALHISTA - INSS 1708 2909");
        cmbRespostas.addItem("TRABALHISTA - INSS VIA GRU");
        cmbRespostas.addItem("TRABALHISTA – IR SOLICITAÇÃO DE QUANTIDADE DE MESES");
        cmbRespostas.addItem("TRABALHISTA - JÁ PETICIONADO");
        cmbRespostas.addItem("TRABALHISTA - OFICIAR BENEFICIÁRIO");
        cmbRespostas.addItem("TRABALHISTA – OFICIO DISPONIVEL PARA RETIRADA NO PAB");
        cmbRespostas.addItem("TRABALHISTA - ORIGEM DO AVISO DE CRÉDITO");
        cmbRespostas.addItem("TRABALHISTA - RECOMPOSIÇÃO DA CONTA");
        cmbRespostas.addItem("TRABALHISTA - RECOMPOSIÇÃO");
        cmbRespostas.addItem("TRABALHISTA - RETIFICAÇÃO EFETUADA");
        cmbRespostas.addItem("TRABALHISTA - RODAPÉ - OFÍCIO PADRÃO");
        cmbRespostas.addItem("TRABALHISTA - SALDO DISPONÍVEL");
        cmbRespostas.addItem("TRABALHISTA - SALDO INSUFICIENTE");
        cmbRespostas.addItem("TRABALHISTA - SALDO ZERADO");
        cmbRespostas.addItem("TRABALHISTA – SALDO ZERADO DE PARCELA SOLICITADA");
        cmbRespostas.addItem("TRABALHISTA - SALDO ZERADO OFÍCIO PAGO ANTERIORMENTE");
        cmbRespostas.addItem("TRABALHISTA - SALVADOR BA - NÃO PAGOU I.R.");
        cmbRespostas.addItem("TRABALHISTA - SISCONDJ");
        cmbRespostas.addItem("TRABALHISTA - SOLICITAÇÃO DE P.I.S. F.G.T.S.");
        cmbRespostas.addItem("TRABALHISTA - TED DEVOLVIDA");
        cmbRespostas.addItem("TRABALHISTA - TRANSFERÊNCIA REALIZADA");
        cmbRespostas.addItem("TRABALHISTA - TRANSITO INDEVIDO CEF");
        cmbRespostas.addItem("TRABALHISTA - UNIFICAÇÃO DE CONTAS");
        cmbRespostas.addItem("TRABALHISTA - WEBSERVICE");
        cmbRespostas.addItem("-------------------------------------------------------------");
        cmbRespostas.addItem("DJO - 1113 NSCGJ - MODELO");
        cmbRespostas.addItem("DJO - 2016 Conta Encerrada por MLJ");
        cmbRespostas.addItem("DJO - 2016 Já Cumprido");
        cmbRespostas.addItem("DJO - Abertura de conta");
        cmbRespostas.addItem("DJO - Anexo não acompanhou");
        cmbRespostas.addItem("DJO - Autorização Conta Outra Vara");
        cmbRespostas.addItem("DJO - BACENJUD Não Localizado");
        cmbRespostas.addItem("DJO - Cadastro Alterado");
        cmbRespostas.addItem("DJO - Cancelamento de Precatório RPV");
        cmbRespostas.addItem("DJO - Comprovante de depósito");
        cmbRespostas.addItem("DJO - DEPRE GRU");
        cmbRespostas.addItem("DJO - DEPRE - Informação Execução Fiscal");
        cmbRespostas.addItem("DJO - DEPRE PGR - Transferência");
        cmbRespostas.addItem("DJO - DEPRE PGR - Transferência - Lista Parcelas");
        cmbRespostas.addItem("DJO - Disponível");
        cmbRespostas.addItem("DJO - Esclarecimentos");
        cmbRespostas.addItem("DJO - Modelo Unificação Com observações");
        cmbRespostas.addItem("DJO - Modelo Unificação CONTAS NÃO LOCALIZADAS");
        cmbRespostas.addItem("DJO - Modelo Unificação Simples");
        cmbRespostas.addItem("DJO - Modelo Unificação SOMENTE UMA CONTA");
        cmbRespostas.addItem("DJO - Não Cumprido - Assinatura não Juiz");
        
        
        //EXCLUIDO 16-05-2017 GERENTE LEANDRO // REATIVADO GERENTE LEANDRO 29-05-2017
        cmbRespostas.addItem("DJO - Não Cumprido - Emitir MLJ");
        //cmbRespostas.addItem("DJO - Não Cumprido - FUNPESP");
        cmbRespostas.addItem("DJO - Não Cumprido - Saldo Insuficiente");
        //cmbRespostas.addItem("DJO - Não Cumprido - Tributário");
        cmbRespostas.addItem("DJO - Não Cumprido - Tributos");
        
        cmbRespostas.addItem("DJO - Não Localizada");
        cmbRespostas.addItem("DJO - Ordem de Resgate com Isenção de Tarifa TED");
        cmbRespostas.addItem("DJO - Origem Destino");
        cmbRespostas.addItem("DJO - Saldo 1 Conta");
        cmbRespostas.addItem("DJO - Saldo Lista");
        cmbRespostas.addItem("DJO - Saldo Simples");
        cmbRespostas.addItem("DJO - TED devolvida");
        cmbRespostas.addItem("DJO - Transferência");
        cmbRespostas.addItem("DJO - Transferência e Informação de Saldo Remanescente");
        cmbRespostas.addItem("DJO - Transferência - EXCEÇÃO MLJ");
        cmbRespostas.addItem("DJO - Transferência - FGTS");
        cmbRespostas.addItem("DJO - Valor disponível em espécie");
        //Combo Autor
        // cmbAutor.addItem("");
        cmbAutor.addItem("Autor (a)                  : ");
        cmbAutor.addItem("Requerente             : ");
        cmbAutor.addItem("Exequente               : ");
        cmbAutor.addItem("Reclamante             : ");
        cmbAutor.addItem("Embargante             : ");
        cmbAutor.addItem("Promovente             : ");
        cmbAutor.addItem("Interessado             : ");
        cmbAutor.addItem("Inventariante            : ");
        cmbAutor.addItem("Classe                     : ");
        cmbAutor.addItem("Assunto                   : ");

        //Combo Reu
        //   cmbReu.addItem("");
        cmbReu.addItem("Réu                         : ");
        cmbReu.addItem("Executado (a)          : ");
        cmbReu.addItem("Requerido (a)          : ");
        cmbReu.addItem("Reclamado (a)        : ");
        cmbReu.addItem("Inventariado (a)       : ");
        cmbReu.addItem("Embargado (a)        : ");
        cmbReu.addItem("Envolvido (a)           : ");
        cmbReu.addItem("Promovido (a)         : ");

        //Combo Envolvido
        //   cmbEnvolvido.addItem("");
        cmbEnvolvido.addItem("Envolvido (a)           : ");
        cmbEnvolvido.addItem("Inventariante           : ");
        cmbEnvolvido.addItem("Falecido (a)            : ");
        cmbEnvolvido.addItem("Vitima                      : ");
        cmbEnvolvido.addItem("Repres. Legal         : ");
        cmbEnvolvido.addItem("Classe                     : ");
        cmbEnvolvido.addItem("Assunto                   : ");

        //Combo Sigilo
        cmbSigilo.addItem("");
        cmbSigilo.addItem("CONFIDENCIAL");
        cmbSigilo.addItem("SIGILOSO");
        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
        cmbSigilo.addItem("PRIORIDADE IDOSO");
        cmbSigilo.addItem("RÉU PRESO");
        
        //Combo Conteudo
        cmbConteudo.addItem("");
        //  cmbConteudo.addItem("AÇÔES");
        cmbConteudo.addItem("AÇÕES CVM");
        cmbConteudo.addItem("AÇÕES PORTAL");
        cmbConteudo.addItem("CONTINUADO");
        cmbConteudo.addItem("DILAÇÃO");
        cmbConteudo.addItem("PARCIAL");
        
        setBounds(largura, altura, 727, 850);
        Color corDoformulario = new Color(240, 240, 240);
        getContentPane().setBackground(corDoformulario);

        LocalDate hoje = new LocalDate();//hoje

        //tratamento mês por extenso
        mes = hoje.getMonthOfYear();

        switch (mes) {
            case 1:
                mesExtenso = "Janeiro";
                break;
            case 2:
                mesExtenso = "Fevereiro";
                break;
            case 3:
                mesExtenso = "Março";
                break;
            case 4:
                mesExtenso = "Abril";
                break;
            case 5:
                mesExtenso = "Maio";
                break;
            case 6:
                mesExtenso = "Junho";
                break;
            case 7:
                mesExtenso = "Julho";
                break;
            case 8:
                mesExtenso = "Agosto";
                break;
            case 9:
                mesExtenso = "Setembro";
                break;
            case 10:
                mesExtenso = "Outubro";
                break;
            case 11:
                mesExtenso = "Novembro";
                break;
            case 12:
                mesExtenso = "Dezembro";
                break;
        }
        txtData.setText("Curitiba, " + hoje.getDayOfMonth() + " de " + mesExtenso + " de " + hoje.getYear() + ".");
        
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage("Logo.png"));
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Imagens\\LogoRetangular.png"));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:/temp/LogoRetangular.png"));
        
        PreencheCmbGerente();
        

    }
   
    private void buscaVersao(String Programa) throws SQLException, IOException {//Busca o código do contratado para inserir no banco

        String versao = null;

        try (com.mysql.jdbc.Connection cnenv = (com.mysql.jdbc.Connection) new Conexao().conectar()) {

            String sqlBuscaVesao = "Select * from tbl_VersaoProgramas where tbl_VersaoProgramas.NOMEDOPROGRAMA= " + "'" + Programa + "'";

            java.sql.Statement stm = cnenv.createStatement();
            try {
                ResultSet rs = stm.executeQuery(sqlBuscaVesao);
                //cn.close();
                if (rs.next()) {
                    //Processar, do jeito que você já fez                    
                    versao = rs.getString("VERSAO");
                    //cn.close();                    
                } else {

                }
            } catch (SQLException e) {
            }
        }
        
      
  //if ("19.6".equals(versao)||("19.8".equals(versao))) {
        if ("19.9".equals(versao)) {
        } else {
            JOptionPane.showMessageDialog(this, "Versão desatualizada. "
                    + "Favor copiar a nova versão do Gerador de Oficios para sua área de trabalho!!! TKS");
            
                     File e = new File("G:\\Publica\\PROJETOS\\Gerador de Oficios\\LogoRetangular.png");
                     
                if(!e.exists()){
                     try {
                        Runtime.getRuntime().exec("explorer G:\\Publica\\PROJETOS\\Gerador de Oficios\\PROGRAMA_GERADOR");
                    } catch (IOException ex) {
                        Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        Runtime.getRuntime().exec("G:\\Publica\\PROJETOS\\Gerador de Oficios\\PROGRAMA_GERADOR");
                    }
                }else{

                    try {
                        Runtime.getRuntime().exec("explorer L:\\Publica\\Projetos\\Gerador de Oficios\\VersaoAtual");
                    } catch (IOException ex) {
                        Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        Runtime.getRuntime().exec("L:\\Publica\\Projetos\\Gerador de Oficios\\VersaoAtual");
                    }
        }
            System.exit(0);
        }

    }

    public void copyFile(File in, File out) throws Exception {
        FileChannel destinationChannel;
        try (FileChannel sourceChannel = new FileInputStream(in).getChannel()) {
            destinationChannel = new FileOutputStream(out).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        }
        destinationChannel.close();
    }

    public void fecharMeiaNoite() {
        new Thread() {
            @Override
            public void run() {
                DateTime dateTime1 = new DateTime();
                //     System.out.println(dateTime1.getMinuteOfDay());
                int dateTime3 = dateTime1.getDayOfMonth();
                dateTime3 = dateTime3 + 1;
                System.out.println(dateTime3);

                int dateTime4 = 0;
                while (dateTime3 != dateTime4) {
                    dateTime4 = dateTime1.getDayOfMonth();
                }
                System.exit(0);
            }
        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel10 = new javax.swing.JLabel();
        txtData = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAbc = new javax.swing.JTextField();
        btnOficio = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtCorpo = new javax.swing.JTextPane();
        btnLimpar = new javax.swing.JToggleButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtEndereco = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEnvolvido = new javax.swing.JTextField();
        txtOficioN = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        txtReu = new javax.swing.JTextField();
        txtDestinatário = new javax.swing.JTextField();
        txtInquerito = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLinhas = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtOficio = new javax.swing.JTextField();
        btnAbrirAOF = new javax.swing.JButton();
        btnAbrirOficioCenop = new javax.swing.JButton();
        btnAbrirCorreio = new javax.swing.JButton();
        txtProcesso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonProcesso = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtCorreio = new javax.swing.JFormattedTextField();
        txtAOF = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        cmbRespostas = new javax.swing.JComboBox();
        cmbConteudo = new javax.swing.JComboBox();
        cmbAutor = new javax.swing.JComboBox();
        cmbReu = new javax.swing.JComboBox();
        cmbEnvolvido = new javax.swing.JComboBox();
        cmbSigilo = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jRadioButtonEmail = new javax.swing.JRadioButton();
        jRadioButtonNaoEmail = new javax.swing.JRadioButton();
        jRadioButton3via = new javax.swing.JRadioButton();
        jLabel20 = new javax.swing.JLabel();
        cmbGerente = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        cmbEnderecoRetorno = new javax.swing.JComboBox<>();
        jCheckBoxComAnexo = new javax.swing.JCheckBox();
        jFormattedTextFieldPrazo = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("    Gerador de Ofícios");
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/bb_logo1.png"))); // NOI18N
        jLabel10.setText("Gerador de Ofícios");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(0, -10, 720, 70);

        txtData.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        txtData.setForeground(new java.awt.Color(0, 96, 168));
        txtData.setText("Curitiba, 22 de xxxxx de xxxx");
        txtData.setToolTipText("");
        getContentPane().add(txtData);
        txtData.setBounds(10, 70, 310, 20);
        getContentPane().add(jSeparator4);
        jSeparator4.setBounds(10, 280, 697, 0);

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel15.setText("_______________________________________________________________________________________");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(10, 690, 710, 15);

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel16.setText("CENOP- Serviços - Curitiba/PR");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(10, 700, 270, 17);

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel18.setText("End. retorno:");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(390, 630, 85, 17);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel5.setText("Iniciais:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(510, 270, 60, 15);

        txtAbc.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtAbc.setForeground(new java.awt.Color(51, 51, 255));
        txtAbc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAbc.setText("ABC");
        txtAbc.setToolTipText("");
        txtAbc.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtAbc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbcActionPerformed(evt);
            }
        });
        getContentPane().add(txtAbc);
        txtAbc.setBounds(570, 270, 50, 18);

        btnOficio.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnOficio.setText("OFÍCIO");
        btnOficio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOficioActionPerformed(evt);
            }
        });
        getContentPane().add(btnOficio);
        btnOficio.setBounds(620, 660, 90, 30);

        txtCorpo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCorpo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(txtCorpo);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(10, 300, 700, 230);

        btnLimpar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnLimpar.setForeground(new java.awt.Color(255, 51, 0));
        btnLimpar.setText("LIMPAR");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpar);
        btnLimpar.setBounds(420, 660, 90, 30);

        txtEndereco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtEndereco.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jScrollPane4.setViewportView(txtEndereco);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(10, 570, 360, 120);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Inq. Pol. Nº :");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 240, 90, 15);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel4.setText("Ofício Nº : ");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(430, 70, 70, 20);

        txtEnvolvido.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtEnvolvido.setToolTipText("");
        txtEnvolvido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtEnvolvido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnvolvidoActionPerformed(evt);
            }
        });
        getContentPane().add(txtEnvolvido);
        txtEnvolvido.setBounds(510, 160, 200, 20);

        txtOficioN.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtOficioN.setToolTipText("");
        txtOficioN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(txtOficioN);
        txtOficioN.setBounds(510, 70, 200, 20);

        txtAutor.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtAutor.setToolTipText("");
        txtAutor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutorActionPerformed(evt);
            }
        });
        getContentPane().add(txtAutor);
        txtAutor.setBounds(510, 100, 200, 20);

        txtReu.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtReu.setToolTipText("");
        txtReu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtReu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReuActionPerformed(evt);
            }
        });
        getContentPane().add(txtReu);
        txtReu.setBounds(510, 130, 200, 20);

        txtDestinatário.setEditable(false);
        txtDestinatário.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtDestinatário.setToolTipText("");
        txtDestinatário.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDestinatárioActionPerformed(evt);
            }
        });
        getContentPane().add(txtDestinatário);
        txtDestinatário.setBounds(10, 620, 220, 20);

        txtInquerito.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtInquerito.setToolTipText("");
        txtInquerito.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtInquerito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInqueritoActionPerformed(evt);
            }
        });
        getContentPane().add(txtInquerito);
        txtInquerito.setBounds(120, 240, 200, 20);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel9.setText("linhas:");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(630, 270, 50, 15);

        txtLinhas.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtLinhas.setText("0");
        txtLinhas.setToolTipText("");
        txtLinhas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtLinhas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLinhasActionPerformed(evt);
            }
        });
        getContentPane().add(txtLinhas);
        txtLinhas.setBounds(680, 270, 30, 20);

        jPanel1.setBackground(new java.awt.Color(0, 96, 168));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 240, 0));
        jLabel14.setText("CORREIO : ");
        jLabel14.setToolTipText("");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 70, 17));

        txtOficio.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtOficio.setToolTipText("");
        txtOficio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(txtOficio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 160, 20));

        btnAbrirAOF.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnAbrirAOF.setForeground(new java.awt.Color(0, 0, 255));
        btnAbrirAOF.setText("v");
        btnAbrirAOF.setToolTipText("");
        btnAbrirAOF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirAOFActionPerformed(evt);
            }
        });
        jPanel1.add(btnAbrirAOF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 40, 20));

        btnAbrirOficioCenop.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnAbrirOficioCenop.setForeground(new java.awt.Color(0, 0, 255));
        btnAbrirOficioCenop.setText("v");
        btnAbrirOficioCenop.setToolTipText("");
        btnAbrirOficioCenop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirOficioCenopActionPerformed(evt);
            }
        });
        jPanel1.add(btnAbrirOficioCenop, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 40, 20));

        btnAbrirCorreio.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnAbrirCorreio.setForeground(new java.awt.Color(0, 0, 255));
        btnAbrirCorreio.setText("v");
        btnAbrirCorreio.setToolTipText("");
        btnAbrirCorreio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirCorreioActionPerformed(evt);
            }
        });
        jPanel1.add(btnAbrirCorreio, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 40, 20));

        txtProcesso.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        txtProcesso.setToolTipText("");
        txtProcesso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(txtProcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 170, 20));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 244, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("PROCESSO Nº :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 100, -1));

        jButtonProcesso.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jButtonProcesso.setForeground(new java.awt.Color(0, 0, 255));
        jButtonProcesso.setText("v");
        jButtonProcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProcessoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonProcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 40, 20));

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 240, 0));
        jLabel22.setText("OF. CENOP N.º : ");
        jLabel22.setToolTipText("");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, 20));

        jLabel23.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 240, 0));
        jLabel23.setText("AOF : ");
        jLabel23.setToolTipText("");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 40, 17));

        txtCorreio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCorreio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreioKeyPressed(evt);
            }
        });
        jPanel1.add(txtCorreio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 130, -1));

        txtAOF.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtAOF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAOFActionPerformed(evt);
            }
        });
        jPanel1.add(txtAOF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 120, -1));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 100, 350, 130);

        jLabel1.setText("                          ");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(330, 710, 90, 20);

        btnSalvar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(0, 0, 204));
        btnSalvar.setText("SALVAR");
        btnSalvar.setToolTipText("");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalvar);
        btnSalvar.setBounds(520, 660, 90, 30);

        cmbRespostas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbRespostas.setToolTipText("");
        cmbRespostas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbRespostasItemStateChanged(evt);
            }
        });
        cmbRespostas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRespostasActionPerformed(evt);
            }
        });
        getContentPane().add(cmbRespostas);
        cmbRespostas.setBounds(80, 270, 410, 25);

        cmbConteudo.setEditable(true);
        cmbConteudo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbConteudo.setToolTipText("");
        cmbConteudo.setBorder(null);
        cmbConteudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConteudoActionPerformed(evt);
            }
        });
        getContentPane().add(cmbConteudo);
        cmbConteudo.setBounds(590, 190, 120, 20);

        cmbAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAutorActionPerformed(evt);
            }
        });
        getContentPane().add(cmbAutor);
        cmbAutor.setBounds(370, 100, 130, 20);

        getContentPane().add(cmbReu);
        cmbReu.setBounds(370, 130, 130, 20);

        getContentPane().add(cmbEnvolvido);
        cmbEnvolvido.setBounds(370, 160, 130, 20);

        cmbSigilo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSigiloActionPerformed(evt);
            }
        });
        getContentPane().add(cmbSigilo);
        cmbSigilo.setBounds(370, 190, 130, 20);

        jButton1.setText("Busca Endereço");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(90, 540, 140, 23);

        jLabel17.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 255));
        jLabel17.setText("Endereço:");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(10, 540, 80, 20);

        txtEmail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(txtEmail);
        txtEmail.setBounds(480, 540, 230, 30);

        buttonGroup1.add(jRadioButtonEmail);
        jRadioButtonEmail.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jRadioButtonEmail.setForeground(new java.awt.Color(0, 102, 255));
        jRadioButtonEmail.setText("Email:");
        jRadioButtonEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonEmailActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButtonEmail);
        jRadioButtonEmail.setBounds(400, 540, 75, 27);

        buttonGroup1.add(jRadioButtonNaoEmail);
        jRadioButtonNaoEmail.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
        jRadioButtonNaoEmail.setSelected(true);
        jRadioButtonNaoEmail.setText("* Não possui");
        jRadioButtonNaoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNaoEmailActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButtonNaoEmail);
        jRadioButtonNaoEmail.setBounds(480, 570, 120, 23);

        jRadioButton3via.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
        jRadioButton3via.setForeground(new java.awt.Color(0, 0, 255));
        jRadioButton3via.setText("3 vias");
        jRadioButton3via.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3viaActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton3via);
        jRadioButton3via.setBounds(640, 630, 70, 23);

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel20.setText("Prazo:");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(540, 220, 50, 17);

        cmbGerente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGerenteActionPerformed(evt);
            }
        });
        getContentPane().add(cmbGerente);
        cmbGerente.setBounds(480, 600, 230, 20);

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel21.setText("Assinatura:");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(400, 600, 80, 17);

        cmbEnderecoRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnderecoRetornoActionPerformed(evt);
            }
        });
        getContentPane().add(cmbEnderecoRetorno);
        cmbEnderecoRetorno.setBounds(480, 630, 150, 20);

        jCheckBoxComAnexo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCheckBoxComAnexo.setText("Com Anexo");
        jCheckBoxComAnexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxComAnexoActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBoxComAnexo);
        jCheckBoxComAnexo.setBounds(610, 570, 100, 23);

        jFormattedTextFieldPrazo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jFormattedTextFieldPrazo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        getContentPane().add(jFormattedTextFieldPrazo);
        jFormattedTextFieldPrazo.setBounds(590, 220, 80, 20);

        jLabel24.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 255));
        jLabel24.setText("Modelo:");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(10, 270, 70, 26);

        jLabel25.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel25.setText("Conteúdo:");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(520, 190, 70, 17);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAbcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbcActionPerformed

    private void btnOficioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOficioActionPerformed

        Oficio oficio = new Oficio();
        try {
            oficio.setLinhas(txtLinhas.getText());
        } catch (Exception e) {
            oficio.setLinhas("");
        }
        try {
            oficio.setOficio(txtOficio.getText());
        } catch (Exception e) {
            oficio.setOficio("");
        }
        try {
            oficio.setAof(txtAOF.getText());
        } catch (Exception e) {
            oficio.setAof("");
        }
        try {
            oficio.setOficio(txtOficio.getText());
        } catch (Exception e) {
            oficio.setOficio("");
        }
        try {
            oficio.setProcesso(txtProcesso.getText());
        } catch (Exception e) {
            oficio.setProcesso("");
        }
        try {
            oficio.setCorpo(txtCorpo.getText());
        } catch (Exception e) {
            oficio.setCorpo("");
        }

        // COLOCADO AQUI PARA PEGAR O TITULO DO DESTINATARIO    
        String DestinatarioCorpo;
        DestinatarioCorpo = oficio.getCorpo().substring(0, 45).trim();
        
        String DestinatarioCorpo1[] = DestinatarioCorpo.split(",");
        
         try {
            oficio.setDestinario("Ao " + DestinatarioCorpo1[0]);
        } catch (Exception e) {
            oficio.setDestinario("");
        }
    
        try {
            oficio.setEndereco(txtEndereco.getText());
        } catch (Exception e) {
            oficio.setEndereco("");
        }
        try {
            oficio.setInquerito(txtInquerito.getText());
        } catch (Exception e) {
            oficio.setInquerito("");
        }
        try {
            oficio.setOficion(txtOficioN.getText());
        } catch (Exception e) {
            oficio.setOficion("");
        }
        try {
            if (!"".equals(txtAutor.getText())) {
                oficio.setAutor(cmbAutor.getSelectedItem().toString() + txtAutor.getText());
            } else {
                oficio.setAutor("");
            }

        } catch (Exception e) {
            oficio.setAutor("");
        }
        try {
            if (!"".equals(txtReu.getText())) {
                oficio.setReu(cmbReu.getSelectedItem().toString() + txtReu.getText());
            } else {
                oficio.setReu("");
            }
        } catch (Exception e) {
            oficio.setReu("");
        }
        try {
            if (!"".equals(txtEnvolvido.getText())) {
                oficio.setEnvolvido(cmbEnvolvido.getSelectedItem().toString() + txtEnvolvido.getText());
            } else {
                oficio.setEnvolvido("");
            }

        } catch (Exception e) {
            oficio.setEnvolvido("");
        }
        try {
            oficio.setAbc(txtAbc.getText().toUpperCase());
        } catch (Exception e) {
            oficio.setAbc("");
        }
        
        try {
            oficio.setCorreio(txtCorreio.getText());
        } catch (Exception e) {
            oficio.setCorreio("");
        }
        
        try {
            oficio.setData(txtData.getText());
        } catch (Exception e) {
            oficio.setData("");
        }

        try {
            String Conteudo = cmbConteudo.getSelectedItem().toString();
            oficio.setConteudo(Conteudo);
        } catch (Exception e) {
            oficio.setConteudo("");
        }
        
        try {
            String enderecoRetorno = cmbEnderecoRetorno.getSelectedItem().toString().trim();
            oficio.setEnderecoRetorno(enderecoRetorno);
        } catch (Exception e) {
            oficio.setEnderecoRetorno("");
        }
        
        if(jCheckBoxComAnexo.isSelected()){
        oficio.setAnexo(true);
        }else{
        oficio.setAnexo(false);    
        }
        
        try {
            String sigiloso = cmbSigilo.getSelectedItem().toString();
            oficio.setSigiloso(sigiloso);
        } catch (Exception e) {
            oficio.setSigiloso("");
        }
         try {
            oficio.setEmail(txtEmail.getText());
        } catch (Exception e) {
            
            oficio.setEmail("");
        }
         
         if(("".equals(txtEmail.getText())) && (jRadioButtonEmail.isSelected())){
            JOptionPane.showMessageDialog(txtEmail, "Favor digitar o email!");
            return;
         }
         
         if ("    /        ".equalsIgnoreCase(txtCorreio.getText())){
             oficio.setCorreio("");
         }
         
         try {
            String gerente = cmbGerente.getSelectedItem().toString();
            oficio.setGerente(gerente);
        } catch (Exception e) {
            oficio.setGerente("");
        }   
         if("".equalsIgnoreCase(oficio.getGerente().toString())){
            JOptionPane.showMessageDialog(null, "Escolher o Gerente!");
            return;
         }
        
         if ("SEM ASSINATURA".equals(cmbGerente.getSelectedItem().toString())){
             oficio.setGerenteSetor("SEM ASSINATURA");
             oficio.setTipoGer("SEM ASSINATURA");
         }else{
             Object[] d = AssinaturaGerente(oficio.getGerente());
             oficio.setGerenteSetor((String)d[0]);
             oficio.setTipoGer((String)d[1]);
         }
          System.out.println("GERENTE_ASSINATURA 2" + oficio.getGerenteSetor()); 
          System.out.println("GERENTE_TIPO 2" + oficio.getTipoGer());
          
        GeraOficio GO = new GeraOficio();
       
        if ("ABC".equals(txtAbc.getText()) || "".equals(txtAbc.getText())) {
            JOptionPane.showMessageDialog(txtAbc, "Favor digitar as iniciais do seu nome");
        } else {
            if ("".equals(txtCorpo.getText())) {
                JOptionPane.showMessageDialog(txtCorpo, "Favor digitar a resposta");
            
                  
                } else {
                    if ("".equals(txtEndereco.getText())) {
                        JOptionPane.showMessageDialog(txtEndereco, "Favor digitar o endereço");

                    } else {
    
                        try {
                            GO.GerarPagUm(oficio);
                            //  GO.colocaLogo();
                        } catch (IOException | DocumentException | InterruptedException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        try {
                            GO.GerarPagDois(oficio);
                            //  GO.colocaLogo();
                        } catch (IOException | DocumentException | InterruptedException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         try {
                            GO.GerarPagDoisB(oficio);
                            //  GO.colocaLogo();
                        } catch (IOException | DocumentException | InterruptedException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         try {
                            GO.GerarPagTres(oficio);
                            //  GO.colocaLogo();
                        } catch (IOException | DocumentException | InterruptedException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // Salvar na rede
                        try {
                            PdfReader pdfComLogo = new PdfReader("c:\\temp\\OficioComLogo.pdf");
                            //--   PdfReader pdfComLogo = new PdfReader("OficioComLogo.pdf");
                            int number_of_pages = pdfComLogo.getNumberOfPages();
                            //  PdfStamper stamp = new PdfStamper(pdfComLogo, new FileOutputStream("c:\\temp\\OficioComLogo.pdf"));
                            LocalDate hoje = new LocalDate();//hoje
                            System.out.println(hoje + "dentro");
                            PdfStamper stamp;
                                 //if (!"aaa".equals(oficio.getAbc())) {//Usar aaa nas inicias para não gravar na pasta--testar o programa                         

                                 //stamp = new PdfStamper(pdfComLogo, new FileOutputStream("c:\\temp\\Oficio_" + oficio.getOficio() + "_" + hoje + "_" + System.getProperty("user.name").toUpperCase() + ".pdf"));
                                /*   try {
                             //stamp = new PdfStamper(pdfComLogo, new FileOutputStream("G:\\Publica\\INFORMACOES A TERCEIROS\\OFÍCIO\\OFICIO_PROG\\Oficio_"+Oficio+"_"+hoje+"_"+Abc+".pdf"));
                             stamp = new PdfStamper(pdfComLogo, new FileOutputStream("G:\\FUNCI\\Publica\\INFORMACOES A TERCEIROS\\OFÍCIO\\OFICIO_PROG\\Oficio_" + oficio.getOficio() + "_" + hoje + "_" + System.getProperty("user.name").toUpperCase().replaceAll("/", "").replaceAll("-", "") + ".pdf"));

                             } catch (DocumentException | IOException e) {
                             stamp = new PdfStamper(pdfComLogo, new FileOutputStream("G:\\Publica\\INFORMACOES A TERCEIROS\\OFÍCIO\\OFICIO_PROG\\Oficio_" + oficio.getOficio() + "_" + hoje + "_" + System.getProperty("user.name").toUpperCase().replaceAll("/", "").replaceAll("-", "") + ".pdf"));
                             //   stamp = new PdfStamper(pdfComLogo, new FileOutputStream("G:\\FUNCI\\Publica\\INFORMACOES A TERCEIROS\\OFÍCIO\\OFICIO_PROG\\Oficio_"+Oficio+"_"+hoje+"_"+Abc+".pdf"));
                             }
                             stamp.close();
                             pdfComLogo.close();*/
                            // }
                        } catch (IOException i1) {
                        }

                        GeraOficio Ga = new GeraOficio();
                        try {
                            Ga.GerarAR(oficio);
                        } catch (DocumentException | IOException e) {
                        }
                        //Criar arquivo com oficio e ar juntos
                        ArrayList<InputStream> list = new ArrayList<>();
                        if (jRadioButton3via.isSelected()){
                             try {
                                list.add(new FileInputStream(new File("c:\\temp\\OficioComLogoUm.pdf")));
                                list.add(new FileInputStream(new File("c:\\temp\\OficioComLogoDoisB.pdf")));
                                list.add(new FileInputStream(new File("c:\\temp\\OficioComLogoTres.pdf")));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }else{
                            try {
                                list.add(new FileInputStream(new File("c:\\temp\\OficioComLogoUm.pdf")));
                                
                               
                                if (jRadioButtonNaoEmail.isSelected()){
                                list.add(new FileInputStream(new File("c:\\temp\\OficioComLogoDois.pdf")));    
                                list.add(new FileInputStream(new File("c:\\temp\\ARF.pdf")));
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                  
                        }

                        String caminho = "c:\\temp\\OficioJunto.pdf";
                        try {
                            OutputStream out = new FileOutputStream(new File(caminho));
                            doMerge(list, out);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (DocumentException | IOException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            // Abre o PDF
                            Process k = Runtime.getRuntime().exec("cmd.exe /C c:\\temp\\OficioJunto.pdf");


                            try {
                                //Apagar os arquivos
                               // 
                                File f = new File("c:\\temp\\OficioComLogoUm.pdf");
                                File f1 = new File("c:\\temp\\ARF.pdf");
                                File f2 = new File("c:\\temp\\OficioComLogoDois.pdf");
                                File f3 = new File("c:\\temp\\OficioComLogoDoisB.pdf");
                                File f4 = new File("c:\\temp\\OficioComLogoTres.pdf");
                                File f5 = new File("c:\\temp\\OficioSemLogo.pdf");                                                     
                            if( f.delete())
                            if( f1.delete())
                            if( f2.delete())
                            if( f3.delete())
                            if( f4.delete())
                            if( f5.delete())                
                                    System.out.println("Arquivo Deletado Com sucesso");
                            } catch (Exception e) {
                            }



                        } catch (IOException ex) {
                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }

                             //SALVAR no BANCO 
                        //CARREGA AS VARIÁVEIS
                        String oficioCenop = txtOficio.getText();
                        String aof = txtAOF.getText();
                        String processo = txtProcesso.getText();
                        String inquerito = txtInquerito.getText();
                        String Oficio = txtOficioN.getText();
                        String autor = txtAutor.getText();
                        String reu = txtReu.getText();
                        String envolvido = txtEnvolvido.getText();
                        String abc = txtAbc.getText();
                        String LINHAS = txtLinhas.getText();
                        String resposta = txtCorpo.getText();
                        String tratamento = txtDestinatário.getText();
                        String endereco = txtEndereco.getText();
                        String CMBAUTOR = cmbAutor.getSelectedItem().toString();
                        String CMBREU = cmbReu.getSelectedItem().toString();
                        String CMBENVOLVIDO = cmbEnvolvido.getSelectedItem().toString();
                        String CMBCONTEUDO = cmbConteudo.getSelectedItem().toString();
                        String email = txtEmail.getText();
                        String CMBENDERECORET = cmbEnderecoRetorno.getSelectedItem().toString();
                        String CMBGERENTE = cmbGerente.getSelectedItem().toString();
                            
                        if (aof.equalsIgnoreCase("    /         ")){
                            aof="";
                        }
                        
                        
                        System.out.println("GERENTE COMBO " + CMBGERENTE);
                        System.out.println("EMAIL " + txtEmail.getText());

                        String chave = System.getProperty("user.name").toUpperCase(); //pega a chave logada no sistema
                        //String sigiloso = cmbSigilo.getSelectedItem().toString();
                        String sigiloso = "";
                        try {
                            sigiloso = cmbSigilo.getSelectedItem().toString();
                        } catch (Exception e) {
                        }
                        String correio = txtCorreio.getText();

                        //INSERE NO BANCO OU ATUALIZA
                        if ("".equals(aof)) {
                            JOptionPane.showMessageDialog(txtAOF, "Favor digitar o número da AOF");
                        }else{
                                if ("".equals(txtOficio.getText())) {
                                    JOptionPane.showMessageDialog(txtOficio, "Favor digitar o número do OFICIO CENOP");

                                } else {

                                    if ("ABC".equals(txtAbc.getText()) || "".equals(txtAbc.getText())) {
                                        JOptionPane.showMessageDialog(txtAbc, "Favor digitar as iniciais do seu nome");
                                    } else {

                                        try {
                                            InsereOficio(oficioCenop, aof, processo, inquerito, Oficio, autor, reu, envolvido, abc, resposta, tratamento, endereco, CMBAUTOR, CMBREU, CMBENVOLVIDO, CMBCONTEUDO, LINHAS, chave, sigiloso, correio, email, CMBGERENTE, CMBENDERECORET);

                                            JOptionPane.showMessageDialog(this, "Gravado com sucesso.");
                                        } catch (SQLException ex) {
                                             JOptionPane.showMessageDialog(this, "Oficio não gravado no banco de dados.");
                                            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }

                                }
                    }        
                        //FIM SALVAR
                    }//endereco
                 
            }//resposta
        }
        //--  }
    }//GEN-LAST:event_btnOficioActionPerformed

    public static void doMerge(ArrayList<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        outputStream.flush();
        document.close();
        outputStream.close();
    }


    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "Deseja Limpar Todos os Dados?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // yes option
            txtOficio.setText("");
            txtAbc.setText("ABC");
            txtAutor.setText("");
            txtAOF.setText("");
            txtProcesso.setText("");
            txtReu.setText("");
            txtDestinatário.setText("");
            txtOficioN.setText("");
            txtEnvolvido.setText("");
            txtCorpo.setText("");
            txtDestinatário.setText("");
            txtEndereco.setText("");
            txtInquerito.setText("");
            txtLinhas.setText("0");
            txtCorreio.setText("");
            txtEmail.setText("");
            
            //Combo Conteudo
            cmbConteudo.removeAllItems();
            cmbConteudo.addItem("");
            cmbConteudo.addItem("AÇÕES CVM");
            cmbConteudo.addItem("AÇÕES PORTAL");
            cmbConteudo.addItem("DILAÇÂO");
            cmbConteudo.addItem("PARCIAL");
            cmbConteudo.addItem("CONTINUADO");

            //Combo Autor
            cmbAutor.removeAllItems();
            cmbAutor.addItem("Autor (a)                  : ");
            cmbAutor.addItem("Requerente             : ");
            cmbAutor.addItem("Exequente               : ");
            cmbAutor.addItem("Reclamante             : ");
            cmbAutor.addItem("Embargante             : ");
            cmbAutor.addItem("Promovente             : ");
            cmbAutor.addItem("Inventariante            : ");
            cmbAutor.addItem("Interessado             : ");
            //Combo Reu
            cmbReu.removeAllItems();
            cmbReu.addItem("Réu                         : ");
            cmbReu.addItem("Executado (a)          : ");
            cmbReu.addItem("Requerido (a)          : ");
            cmbReu.addItem("Reclamado (a)        : ");
            cmbReu.addItem("Inventariado (a)       : ");
            cmbReu.addItem("Embargado (a)        : ");
            cmbReu.addItem("Envolvido (a)           : ");
            cmbReu.addItem("Promovido (a)         : ");
            //Combo Envolvido
            cmbEnvolvido.removeAllItems();
            cmbEnvolvido.addItem("Envolvido (a)           : ");
            cmbEnvolvido.addItem("Inventariante           : ");
            cmbEnvolvido.addItem("Falecido (a)            : ");
            cmbEnvolvido.addItem("Vitima                      : ");
            cmbEnvolvido.addItem("Classe                     : ");
            cmbEnvolvido.addItem("Assunto                   : ");

            cmbSigilo.removeAllItems();
            // cmbSigilo.addItem(rs.getString("SIGILOSO"));
            cmbSigilo.addItem("");
            cmbSigilo.addItem("CONFIDENCIAL");
            cmbSigilo.addItem("SIGILOSO");
            cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
            cmbSigilo.addItem("PRIORIDADE IDOSO");
            cmbSigilo.addItem("RÉU PRESO");
            
            cmbGerente.removeAllItems();
            PreencheCmbGerente();
            
            cmbEnderecoRetorno.removeAllItems(); 
            cmbEnderecoRetorno.addItem("Av. São João");
            //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
            cmbEnderecoRetorno.addItem("Vila Mariana");
            //checkbox de anexo email 
            jCheckBoxComAnexo.setSelected(false);
        } else {
            // no option
        }


    }//GEN-LAST:event_btnLimparActionPerformed

    private void txtEnvolvidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnvolvidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnvolvidoActionPerformed

    private void txtAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutorActionPerformed

    private void txtDestinatárioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDestinatárioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDestinatárioActionPerformed

    private void txtInqueritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInqueritoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInqueritoActionPerformed

    private void txtReuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReuActionPerformed

    private void txtLinhasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLinhasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLinhasActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        
        //CARREGA AS VARIÁVEIS
        String oficioCenop = txtOficio.getText();
        String aof = txtAOF.getText();
        String processo = txtProcesso.getText();
        String inquerito = txtInquerito.getText();
        String oficio = txtOficioN.getText();
        String autor = txtAutor.getText();
        String reu = txtReu.getText();
        String envolvido = txtEnvolvido.getText();
        String abc = txtAbc.getText();
        String LINHAS = txtLinhas.getText();
        String resposta = txtCorpo.getText();
        String tratamento = txtDestinatário.getText();
        String endereco = txtEndereco.getText();
        String CMBGERENTE = cmbGerente.getSelectedItem().toString();
        String email = txtEmail.getText();
        String CMBAUTOR = cmbAutor.getSelectedItem().toString();
        String CMBREU = cmbReu.getSelectedItem().toString();
        String CMBENVOLVIDO = cmbEnvolvido.getSelectedItem().toString();
        String CMBCONTEUDO = cmbConteudo.getSelectedItem().toString();
        String sigiloso = cmbSigilo.getSelectedItem().toString();
        String chave = System.getProperty("user.name").toUpperCase(); //pega a chave logada no sistema
        
        String CMBENDERECORET= "";
        try{
        CMBENDERECORET = cmbEnderecoRetorno.getSelectedItem().toString();
        } catch (Exception e) {
        cmbEnderecoRetorno.removeAllItems();
        cmbEnderecoRetorno.addItem("Av. São João");
        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
        cmbEnderecoRetorno.addItem("Vila Mariana");
        }
        
        try {
            sigiloso = cmbSigilo.getSelectedItem().toString();
        } catch (Exception e) {
        }
        
        String correio = "";
        
        try{
        correio = txtCorreio.getText();
        } catch (Exception e) {
        }
        
        if (correio.equalsIgnoreCase("    /        ")){
            correio = "";
        }

        //INSERE NO BANCO OU ATUALIZA
        if (("".equals(txtAOF.getText())) || ("".equals(txtOficio.getText()))) {
            JOptionPane.showMessageDialog(txtAOF, "Favor digitar o número da AOF ou do OFICIO CENOP(GSV)");

        } else {

            if ("ABC".equals(txtAbc.getText()) || "".equals(txtAbc.getText())) {
                JOptionPane.showMessageDialog(txtAbc, "Favor digitar as iniciais do seu nome");
            } else {

                try {
                    InsereOficio(oficioCenop, aof, processo, inquerito, oficio, autor, reu, envolvido, abc, resposta, tratamento, endereco, CMBAUTOR, CMBREU, CMBENVOLVIDO, CMBCONTEUDO, LINHAS, chave, sigiloso, correio, email, CMBGERENTE, CMBENDERECORET);
                    JOptionPane.showMessageDialog(this, "Gravado com sucesso.");
                } catch (SQLException ex) {
                    Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Erro ao Gravar.");
                }
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnAbrirAOFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirAOFActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtAOF.getText())) {
            JOptionPane.showMessageDialog(txtAOF, "Favor digitar o número da AOF");
        } else {

            String aof = txtAOF.getText();
            String aofBd = ("'" + aof + "'");

            try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
              
                String sqlBuscaDados = "SELECT * FROM " + tabelaGerador_oficio + " WHERE " + tabelaGerador_oficio + ".AOF=" + aofBd + " limit 1;";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);

                    if (rs.next()) {
                        txtOficio.setText(rs.getString("OFICIO_CENOP"));
                        txtProcesso.setText(rs.getString("PROCESSO"));
                        txtInquerito.setText(rs.getString("INQUERITO"));
                        txtOficioN.setText(rs.getString("OFICIO"));
                        txtAutor.setText(rs.getString("AUTOR"));
                        txtReu.setText(rs.getString("REU"));
                        txtEnvolvido.setText(rs.getString("ENVOLVIDO"));
                        txtAbc.setText(rs.getString("ABC"));
                        txtCorpo.setText(rs.getString("RESPOSTA"));
                        txtDestinatário.setText(rs.getString("TRATAMENTO"));
                        txtEndereco.setText(rs.getString("ENDERECO"));
                        txtLinhas.setText(rs.getString("LINHAS"));
                        txtCorreio.setText(rs.getString("CORREIO"));
                        
                        txtEmail.setText(rs.getString("EMAIL"));
                        
                        if (!txtEmail.getText().equalsIgnoreCase("")){
                            jRadioButtonEmail.setSelected(true);
                        }else{
                            jRadioButtonNaoEmail.setSelected(true);
                        }
                        
                        cmbAutor.removeAllItems();
                        cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");
                        cmbAutor.addItem("Classe                     : ");
                        cmbAutor.addItem("Assunto                   : ");

                        cmbReu.removeAllItems();
                        cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");

                        cmbConteudo.removeAllItems();
                        cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        // cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        cmbGerente.addItem(rs.getString("GERENTE"));
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        jCheckBoxComAnexo.setSelected(false);

                    } else {
                        txtOficio.setText("");
                        txtProcesso.setText("");
                        txtInquerito.setText("");
                        txtOficioN.setText("");
                        txtAutor.setText("");
                        txtReu.setText("");
                        txtEnvolvido.setText("");
                        txtAbc.setText("");
                        txtCorpo.setText(" AOF NÃO ENCONTRADA!");
                        txtDestinatário.setText("");
                        txtEndereco.setText("");
                        txtEmail.setText("");
                        txtLinhas.setText("0");

                        cmbAutor.removeAllItems();
                        // cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        // cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        // cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        // cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");

                        cmbConteudo.removeAllItems();
                        // cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        //  cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        
                        jCheckBoxComAnexo.setSelected(false);   
                    }

                } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }

           
        }
    }//GEN-LAST:event_btnAbrirAOFActionPerformed

    private void cmbRespostasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbRespostasItemStateChanged
        // TODO add your handling code here:

        String escolha = cmbRespostas.getSelectedItem().toString();

        if (null != escolha) {
            switch (escolha) {

                //modelos denise 20160704
                //-------------------------------------------------------------------------------------------
                case "Ações - BB-BI não custodiante":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão de XX/XX/XXXX, da B3.\n"
                            + "\n"
                            + "Acionista: XXXXXXXX - CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Bloqueio em X Grau\n"
                            + "\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + "XX               XX.XXX                             XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Cumpre esclarecer que sobre tais ativos pesa bloqueio em 1° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX, em 2° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Ressaltamos, que as posições acionárias acima, poderão sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Acrescentamos, na qualidade de escriturador, que XXXXXXXXX - CPF XXX.XXX.XXX-XX, possui XX ações XX de emissão da empresa XXXXXXX, CNPJXXXX e o BB- BI não foi contratado como agente de custódia.\n"
                            + "\n"
                            + "A respeito dessas ações, importa esclarecer que o saldo pode sofrer alteração em consequência de:\n"
                            + "\n"
                            + "a) negociação em curso no depositário central, uma vez que o prazo para liquidação é de 5 dias;\n"
                            + "b) eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos, cancelamentos de valores mobiliários em circulação ou distribuição de frutos.\n"
                            + "\n"
                            + "Salientamos, que nos termos da legislação em vigor, especialmente nos artigos 23 e seguintes da Lei 12.810/13, os artigos 1º, parágrafo 1º inciso IV e 35 da Instrução CVM 541/13 e também Ofício Circular 049/2015-DP, somente à Central Depositária de Ativos (B3) compete constituir com validade e eficácia jurídica plena os bloqueios determinados pelas autoridades judiciárias e/ou administrativas. Desta forma, caso seja do interesse de Vossa Excelência, sugerimos oficiar diretamente a B3 para o bloqueio das ações em bolsa de valores.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Bloqueio com Custódia":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão de XX/XX/XXXX, da B3.\n"
                            + "\n"
                            + "Acionista: XXXXXXXX - CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Bloqueio em X Grau\n"
                            + "\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + "XX               XX.XXX                             XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Cumpre esclarecer que sobre tais ativos pesa bloqueio em 1° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX, em 2° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Ressaltamos, que as posições acionárias acima, poderão sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Acrescentamos, que XXXXXXXXX - CPF XXX.XXX.XXX-XX, XXXXXXXXX – CPF XXX.XXX.XXX-XX e XXXXXXXXX - CPF XXX.XXX.XXX-XX, possuem ações em bolsa de valores. Segue abaixo a posição acionária. A respeito dessas ações, importa esclarecer que o saldo pode sofrer alteração em consequência de:\n"
                            + "\n"
                            + "a) negociação em curso no depositário central, uma vez que o prazo para liquidação é de 5 dias;\n"
                            + "b) eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos, cancelamentos de valores mobiliários em circulação ou distribuição de frutos.\n"
                            + "\n"
                            + "Salientamos, que nos termos da legislação em vigor, especialmente nos artigos 23 e seguintes da Lei 12.810/13, os artigos 1º, parágrafo 1º inciso IV e 35 da Instrução CVM 541/13 e também Ofício Circular 049/2015-DP, somente à Central Depositária de Ativos (B3) compete constituir com validade e eficácia jurídica plena os bloqueios determinados pelas autoridades judiciárias e/ou administrativas. Desta forma, caso seja do interesse de Vossa Excelência, sugerimos oficiar diretamente a B3 para o bloqueio das ações em bolsa de valores.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Ações - Bloqueio pelo Escriturador Anterior":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que as XX ações tipo XX e XX ações tipo XX de emissão da empresa XXXXXXX, em nome de XXXXXXXXX, CNPJ XX.XXX.XXX/XXXX-XX, encontram-se com bloqueio judicial anterior a data de contratação do serviço de escrituração de valores mobiliários do Banco do Brasil S.A.. \n"
                            + "\n"
                            + "Acrescentamos, que em consulta a empresa XXXXXXX, recebemos a informação que o bloqueio foi originado no Banco XXXXXXX, que poderá prestar maiores esclarecimentos.\n"
                            + "\n"
                            + "Esclarecemos, que a venda/transferência das ações depende de ordem de desbloqueio, que deverá ser originária do processo que gerou o bloqueio. \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"  
                            + "\n"
                            + "Respeitosamente,");
                    break;
                        case "Ações - Bloqueio Subsequente":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio em XX grau das ações em nome de XXXXXXXXXXXXXX, CNPJ XX.XXX.XXX/XXXX-XX, conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX. \n" 
                            + "\n"
                            + "Empresa: XXXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + "XX               XX.XXX                             XXXXXXX                      R$ X,XX\n"
                            + "XX               XX.XXX                             XXXXXXX                      R$ X,XX\n"
                            + "\n" 
                            + "Ressaltamos que a posição acionária poderá sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n" 
                            + "Cumpre esclarecer, que sobre tais ativos pesam bloqueios em 1º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX, em 2º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX, em 3º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX e em 4º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX.\n"
                            + "\n" 
                            + "Acrescentamos, que não consta em nossos registros qualquer posição acionária para os demais executados.\n"
                            + "\n" 
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n" 
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n" 
                            + "Respeitosamente,");
                    break;
                     case "Ações - Bloqueio Várias Pessoas":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio das ações conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX. \n"
                            + "\n"
                            + "Acionista: XXXXXXXXXXXXXXXXXX – CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Bloqueio em Xº Grau \n"
                            + "\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + "XX               XX.XXX                             XXXXXXX                      R$ X,XX\n"
                            + "XX               XX.XXX                             XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Cumpre esclarecer, que sobre tais ativos pesam bloqueios em 1º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX, em 2º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX, em 3º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX e em 4º grau referente ao processo XXXXXXXXXXXXXXX da XXª Vara XXXXXXX.\n"
                            + "\n"
                            + "Acionista: XXXXXXXXXXXXXXXXXX – CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "\n"
                            + "Bloqueio em Xº Grau \n"
                            + "\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Acionista: XXXXXXXXXXXXXXXXXX – CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "\n"
                            + "Bloqueio em Xº Grau \n"
                            + "\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Ressaltamos que as posições acionárias acima, poderão sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                            case "Ações - Bloqueio":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio em Xº grau das ações em nome de XXXXXXXXXXX, CNPJ XX.XXX.XXX/XXXX-XX, conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX. \n"
                            + "\n"
                            + "Empresa: XXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Ressaltamos que a posição acionária poderá sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Ações - Cadastro Solicitação Docs":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas Empresas, a posição acionária atualizada em nome de XXXXXXXX – CPF XXX.XXX.XXX-XX. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n"
                            + "Empresa: XXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título           Saldo Bloq. BB                 Tp. Bloqueio           Valor Unitário\n"
                            + "XX                      XX                                 XXXXXX                 R$ XX,XX\n"
                            + "\n"
                            + "Ressaltamos, que o saldo pode sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos, cancelamentos de valores imobiliários em circulação ou distribuição de frutos.\n"
                            + "\n"
                            + "Acrescentamos, que para dar início ao processo de venda das XX ações XX OU XX, de emissão da XXXXXXXXXX, é imprescindível alguns documentos e informações no cadastro do investidor. Assim, objetivando o cumprimento da ordem de V. Ex.ª, sem olvidar as regras atinentes às negociações com valores mobiliários, solicitamos informar a agência XXXX-X - XXXXXXXX (ENDEREÇO DA AG.) os dados abaixo listados de XXXXXXXXXX - CPF XXX.XXX.XXX-XX/CNPJ XX.XXX.XXX/XXXX-XX, disposto no artigo 3º § 1º da Instrução CVM nº 301, de 16/04/1999:\n"
                            + "\n"
                            + "a) Data de nascimento, naturalidade, nacionalidade, estado civil, filiação e nome do cônjuge;\n"
                            + "b) número do documento de identificação (RG ou CNH), órgão expedidor e estado emissor do documento de identificação, data de expedição;\n"
                            + "c) endereço completo (logradouro, complemento, bairro, cidade, unidade da federação e CEP)  e telefone; e\n"
                            + "d) ocupação profissional; \n"
                            + " (se pessoa jurídica)\n"
                            + "a) Denominação ou razão social;\n"
                            + "b) nomes dos controladores, administradores, e procuradores;\n"
                            + "c) número de identificação no registro empresarial (NIRE) e no cadastro Nacional de Pessoa Jurídica  (CNPJ);\n"
                            + "d) endereço completo (logradouro, complemento, bairro, cidade, unidade da federação e CEP) e número de telefone;\n"
                            + "e) atividade Principal desenvolvida;\n"
                            + "f) informações acerca da situação patrimonial e financeira respectiva; e\n"
                            + "g) denominação ou razão social de pessoas jurídicas controladoras, controladas ou coligadas.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Ações - Conta Judicial Existente":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, esclarecemos na qualidade de escrituradores das ações de diversas empresas, que os rendimentos das ações não ocorrem mensalmente. O pagamento dos lucros provenientes das ações, podem ser na forma de dividendos, juros sobre capital próprio ou bonificações, sendo determinados pela Assembléia Geral de Acionistas dependendo do lucro líquido do exercício, o qual o Banco do Brasil não possui ingerência sobre as datas e valores, o que impossibilita o acompanhamento para depósito dos rendimentos.\n"
                            + "\n"
                            + "A última distribuição de dividendos e juros sobre capital próprio, realizado pela empresa XXXXXXXXX CNPJ: XX.XXX.XXX/XXXX-XX, em nome de XXXXXXXXXXX, CPF: XXX.XXX.XXX-XX, ocorreu em XX/XX/XXXX e os valores disponibilizados foram creditados na conta corrente XX.XXX-X, da agência XXXX-X, em nome da falecida, não havendo atualmente valores disponíveis.\n"
                            + "\n"
                            + "Declaramos que as informações contidas neste ofício são confidenciais e estão sendo encaminhadas à autoridade competente e requisitante com o devido amparo da Lei Complementar nº105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                case "Ações - Contrato Linha Telefonica":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n" 
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, esclarecemos que apenas a OI S.A 76.535.764/0001-43 (CASO NÃO SEJA DA OI AS UTILIZAR – empresa detentora das linhas telefônicas mencionadas em Vosso ofício), poderá prestar as informações solicitadas. O Banco do Brasil, na qualidade de escriturador contratado pela empresa, não possui informações pertinentes ao registro de dados dos contratos de participação financeira emitidos pelas companhias telefônicas, tais como datas de assinaturas dos contratos, valores integralizados, quantidade de ações emitidas e data da emissão das ações. \n"
                            + "\n" 
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n" 
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n" 
                            + "\n"
                            + "Respeitosamente,");
                    break;
                 case "Ações - Custódia":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na qualidade de custodiante contratado que XXXXXXXXX -  CPF XXX.XXX.XXX-XX, XXXXXXXXX - CPF XXX.XXX.XXX-XX e XXXXXXXXX - CPF XXX.XXX.XXX-XX, possuem ações em bolsa de valores. Segue abaixo a posição acionária:\n"
                            + "\n"
                            + "Título                             Quantidade \n"
                            + "XXXXX                              XXX\n" 
                            + "XXXXX                              XXX\n" 
                            + "XXXXX                              XXX\n" 
                            + "XXXXX                              XXX\n"
                            + "\n"
                            + "A respeito dessas ações, importa esclarecer que o saldo pode sofrer alteração em consequência de:\n"
                            + "\n"
                            + "a) negociação em curso no depositário central, uma vez que o prazo para liquidação é de 5 dias;\n"
                            + "b) eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos, cancelamentos de valores mobiliários em circulação ou distribuição de frutos.\n"
                            + "\n"
                            + "Salientamos, que nos termos da legislação em vigor, especialmente nos artigos 23 e seguintes da Lei 12.810/13, os artigos 1º, parágrafo 1º inciso IV e 35 da Instrução CVM 541/13 e também Ofício Circular 049/2015-DP, somente à Central Depositária de Ativos (B3) compete constituir com validade e eficácia jurídica plena os bloqueios determinados pelas autoridades judiciárias e/ou administrativas. Desta forma, caso seja do interesse de Vossa Excelência, sugerimos oficiar diretamente a B3 para o bloqueio das ações em bolsa de valores.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                
                case "Ações - Demais não localizado":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, na condição de administrador das ações de diversas empresas, a posição acionária em nome de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Empresa: XXXXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título		Saldo XXXX BB		Valor Unitário\n"
                            + "XX		          XX	                            R$ XX,XX\n"
                            + "\n"
                            + "Ressaltamos que a posição acionária acima, poderá sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Informamos ainda, que não constam em nossos registros qualquer posição acionária para os demais executados.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Desbloqueio":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que a ordem judicial foi cumprida, com o desbloqueio de XX ações XX da empresa XXXXX, CNPJ XXXXXX, em nome de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                case "Ações - Grupamento OI":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administradores das ações de diversas empresas, a posição acionária em nome de  XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Empresa: XXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título           Saldo XXXX BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Cumpre esclarecer, que a Empresa Oi S.A., na Assembleia Geral Extraordinária de 18/11/2014, aprovou a racionalização da base acionária, por meio de grupamento, na proporção de 10 (dez) ações para 1 (uma) ação. Os saldos de ações que ficaram com montantes inferiores a 1 ação, passaram a representar frações. As frações de ações não complementadas pelos acionistas foram levadas a leilão na Bolsa de Valores e os valores apurados disponibilizados em nome do acionista. \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Inventário":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio conforme abaixo, das ações existentes em nome de XXXXXXXXX – CPF XXX.XXX.XXX-XX. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n" 
                            + "Empresa: XXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                Valor Unitário\n"
                            + " XX                       XX                                 Espólio                        R$ X,XX\n"
                            + " XX                       XX                                 Espólio                        R$ X,XX\n"
                            + "\n"
                            + "Ressaltamos que a posição acionária poderá sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "A fim de evitar perda de direitos, uma vez que o art. 287, da lei 6404, prevê a prescrição após 3 anos da liberação dos valores referentes as frações/rendimentos distribuídos pelas empresas, transferimos para a conta judicial XXXXXXXXX da agência XXXX-X, os valores a receber, em nome do inventariado, distribuídos pela empresa XXXXXX, conforme comprovante anexo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                    
                case "Ações - Portador PETRO":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que não constam em nossos registros qualquer posição acionária em nome de XXXXXXXXXXX – CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Conforme informado pela PETROBRAS, a empresa não possui registro dos detentores das ações preferenciais ao portador, sendo necessário que o demandante, de posse dos certificados originais das ações preferenciais ao portador, apresente-os juntamente com seus documentos pessoais (identidade, CPF e comprovantes de residência e rendimentos) em qualquer agência do Banco do Brasil S.A. (Instituição que está administrando o Sistema de Ações Escriturais da Petrobras) para a conversão em nominativas.\n"
                            + "\n"
                            + "Caso o demandante não possua os certificados originais, orientamos dirigir-se à Instituição Financeira onde foram adquiridas e subcustodiadas as referidas ações preferenciais ao portador, identificadas nos campos nº 33 e 36 da cópia do contrato de promessa de compra e venda, para que outros esclarecimentos possam ser prestados.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Rendimento Outros Bancos":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n"
                            + "Acionista: XXXXXXXX - CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Bloqueio em X Grau\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + "  XX                   XX.XXX                         XXXXXXX                        R$ X,XX\n"
                            + "\n"
                            + "Cumpre esclarecer que sobre tais ativos pesa bloqueio em 1° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX, em 2° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Ressaltamos que a posição acionária acima, poderá sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Acrescentamos, que constam R$ XX,XX (XXXXXXXXXXXXX) referentes a frações e rendimentos distribuídos pela XXXXXXXX, que conforme art. 287, da lei 6404, prescrevem após 3 anos de sua liberação pelas empresas. Esclarecemos que os valores podem ser depositados em conta judicial vinculado ao processo, sendo necessário que esse juízo nos informe o banco depositário, agência, conta e código da receita.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Rendimentos Com Bloqueio":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que efetuamos o bloqueio conforme abaixo. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n"
                            + "Acionista: XXXXXXXX - CPF XXX.XXX.XXX-XX\n"
                            + "Empresa: XXXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Bloqueio em X Grau\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + "  XX                   XX.XXX                         XXXXXXX                        R$ X,XX\n"
                            + "\n"
                            + "Cumpre esclarecer que sobre tais ativos pesa bloqueio em 1° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX, em 2° Grau, determinado pela XXª Vara XXXXXXXX - XX, referente ao processo XXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Ressaltamos que as posições acionárias acima, poderão sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Acrescentamos, que constam R$ XX,XX (XXXXXXXXXXXXXXXXXXX) referentes a frações e rendimentos distribuídos pela XXXXXXXXXX, que conforme art. 287, da lei 6404, prescrevem após 3 anos de sua liberação pelas empresas. Esclarecemos, que os valores podem ser depositados em conta judicial vinculado ao processo, mediante autorização desse juízo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Rendimentos Com Posição":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, a posição acionária em nome de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n"
                            + "Empresa: XXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio                 Valor Unitário\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + " XX                      XX                               XXXXXXX                      R$ X,XX\n"
                            + "\n"
                            + "Acrescentamos, que constam R$ XX,XX (XXXXXXXXXXXXXXXXXXX) referentes a frações e rendimentos distribuídos pela XXXXXXXXXX, que conforme art. 287, da lei 6404, prescrevem após 3 anos de sua liberação pelas empresas. Esclarecemos, que os valores podem ser depositados em conta judicial vinculado ao processo, mediante autorização desse juízo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Ações - Rendimentos Sem Posição":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, na condição de administrador das ações de diversas Empresas, que não constam em nossos registros qualquer posição acionária em nome de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Acrescentamos, que constam R$ XX,XX (XXXXXXXXXXXXXXXXXXX) referentes a frações e rendimentos distribuídos pela XXXXXXXXXX, que conforme art. 287, da lei 6404, prescrevem após 3 anos de sua liberação pelas empresas. Esclarecemos, que os valores podem ser depositados em conta judicial vinculado ao processo, mediante autorização desse juízo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "Ações - Saldo Data Óbito":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, a posição acionária em nome de XXXXXXX – CPF XXX.XXX.XXX-XX, na data do óbito (XX/XX/XXXX), bem como suas respectivas cotações:\n"
                            + "\n"
                            + "Empresa: XXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título              Quantidade de Ações                 Valor Unitário(*)\n"
                            + " XX                       XX                                          R$ X,XX\n"
                            + " XX                       XX                                          R$ X,XX\n"
                            + "\n"
                            + "(*) Refere-se à cotação (Valor Unitário) de fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n"
                            + "Por oportuno, informamos abaixo a posição acionária atual em nome do inventariado. Acrescentamos que as ações estão cotadas com base no fechamento do pregão da B3 de XX/XX/XXXX.\n"
                            + "\n"
                            + "Empresa: XXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX\n"
                            + "Título           Saldo Bloq. BB                  Tp. Bloqueio(*)                 Valor Unitário\n"
                            + " XX                      XX                               XXXXXXX                        R$ X,XX\n"
                            + " XX                      XX                               XXXXXXX                        R$ X,XX\n"
                            + "\n"
                            + "(*) Bloqueio a favor dessa Vara, para processo XXXXXXX.\n"
                            + "\n"
                            + "Ressaltamos que a posição acionária poderá sofrer alteração em função de eventos de racionalização da estrutura de capital do emissor, tais como grupamentos, desdobramentos ou distribuição de frutos.   \n"
                            + "\n"
                            + "Referente a quantidade de ações na data do óbito e a posição atual, cumpre esclarecer que a Empresa Oi S.A. em 18/11/2014, em Assembleia Geral Extraordinária, aprovou a racionalização da base acionária, por meio de grupamento, na proporção de 10 (dez) ações para 1 (uma) ação. Os saldos de ações que ficaram com montantes inferiores a 1 ação, passaram a representar frações. As frações de ações não complementadas pelos acionistas foram levadas a leilão na Bolsa de Valores e os valores apurados disponibilizados em nome do acionista. \n"
                            + "\n"
                            + "Informamos ainda, que os rendimentos das ações acima estão sendo creditados na conta corrente n° XX.XXX-X, na agência XXXX-X, desta Instituição Bancária, de titularidade do \"de cujus\".\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                case "Ações - Sem Posição":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, na condição de administrador das ações de diversas empresas, que não constam em nossos registros qualquer posição acionária em nome de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "Ações - Telebrás":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que o Banco do Brasil S.A. - CNPJ 00.000.000/0001-91 não escritura, nem escriturou ações emitidas pela Empresa Telebrás. \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "Ações - Títulos Endossáveis":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que o acionista XXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, possui ações preferenciais endossáveis emitidas pela Telebrasília (Telecomunicações de Brasília S.A). Em virtude do acionista ter efetuado a retirada dos títulos endossáveis, para liberação do vínculo, é imprescindível que seja efetuada a devolução das cautelas originais em qualquer agência do Banco do Brasil.\n"
                            + "\n"
                            + "Em caso de extravio, faz-se necessário uma Ação Judicial de Anulação e Substituição de Títulos Endossáveis, nos termos do Artigo 38 da Lei 6.404/76,  Artigos 907 e seguintes do CPC – Código de Processo Civil, no qual o Judiciário autoriza as empresas a considerarem nulas as cautelas extraviadas.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "Ações - Transferencia BB":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que informamos que não constam em nossos registros qualquer posição acionária em nome dos requeridos.\n"
                            + "\n"
                            + "Por oportuno, comunicamos que os valores a receber, em nome de XXXXXX, CPF XXX.XXX.XXX-XX referentes as frações/rendimentos distribuídos pelas empresas XXXXXX – CNPJ XX.XXX.XXX/XXXX-XX e XXXXXX - CNPJ XX.XXX.XXX/XXXX-XX, foram transferidos para a conta judicial XXXXXXXXXX - Valor: R$ XXX,XX (Parcela 01 - XXXX) - Valor: R$ X,XX (Parcela 02 - XXXXX), da agência XXXX-X, à disposição desse Juízo, conforme comprovantes anexos.\n"
                            + "\n"
                            + "O valores existentes em nome de XXXXXX, CPF XXXXX referentes as frações/rendimentos distribuídos pela empresa XXXXX, foram transferidos para a conta judicial XXXXXXXXXXX (Valor: R$ XX,XX), da agência XXXX-X, à disposição desse Juízo, conforme comprovante anexo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "Ações - Transferencia CEF":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que transferimos o valor R$ XXX,XX (XXXXXX), referente aos rendimentos distribuídos pela empresa XXXXXX – CNPJ XX.XXX.XXX/XXXX-XX, em nome de XXXXXXXX - CPF XXX.XXX.XXX-XX, para conta judicial XXXXXXX na Caixa Econômica Federal, agência XXXX, (ID: XXXXXXXXXXX), à disposição desse Juízo, conforme comprovante anexo. \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "Ações - Venda":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos na condição de administrador das ações de diversas empresas, que transferimos os valores referentes aos rendimentos e/ou frações de ações da(s) empresa(s) abaixo relacionada(s), em  nome de XXXXXXXXXX - CPF XXX.XXX.XXX-XX, para as contas judiciais na agência XXXX-X – XXXXXXX (XX), conforme comprovante(s) anexo(s).\n"
                            + "\n"
                            + "Empresa	                   Conta Judicial                         Valor (R$)\n"
                            + "XXXXXXXX	                  XXXXXXXXXX                     XXX,XX\n"
                            + "XXXXXXXX	                  XXXXXXXXXX                     X.XXX,XX\n"
                            + "\n"
                            + "Esclarecemos, que o tratamento da venda das ações de emissão de XXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX, XXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX e XXXXXXXXXXX - CNPJ XX.XXX.XXX/XXXX-XX, estão a cargo da ag. XXXX-X (XXXXXXX). \n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                
                    
                case "BLOQUEIO DE VALOR - CONTA SEM SALDO":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao seu Ofício supramencionado, datado de xx/xx/xxxx referente ao processo xxxx/xxx.x.xxx.-x (xxxxxxx-xx.xxxx.x.xx.xxxx), informamos-lhe que não foi possível atender à solicitação de bloqueio de valor , pois não localizamos em nosso banco de dados nenhum cliente com o CPF xxx.xxx.xxx-xx não possui saldo em conta corrente.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos/informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                      
                //-------------------------------------------------------------------------------------------
                case "IMPOSSIBILIDADE CLIENTE NÃO CADASTRADO":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao seu Ofício supramencionado, datado de xx/xx/xxxx referente ao processo xxxx/xxx.x.xxx.-x (xxxxxxx-xx.xxxx.x.xx.xxxx), informamos-lhe que não foi possível atender à solicitação de bloqueio de valor , pois não localizamos em nosso banco de dados nenhum cliente com o CPF xxx.xxx.xxx-xx.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos/informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     
                //-------------------------------------------------------------------------------------------
                case "VALOR BLOQUEIO TOTAL-PARCIAL":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao seu Ofício supramencionado, de xx/xx/xxxx referente ao processo nº. xxxxxxx-xx.xxxx.x.xx.xxxx, informamos-lhe que foi efetuado em xx/xx/xxxx, conforme protocolo nº___________________ o bloqueio do saldo apresentado na conta poupança nº xx.xxx.xxx-x – Agência xxxx-x  em nome de _________________________, no valor de R$ ___,__ (xxxxxxxxxx reais e xxxxxxxxxx e xxxx centavos), colocando-o à disposição desse Juízo.\n"
                            + "\n"
                            + "Com referência aos outros envolvidos, CPF xxxxx.xxxx.xxxx-xx de ___________________ e CPF xxx.xxx.xxx-xx em nome de ____________________, informamos a inexistência de operações ativas nesta Instituição Financeira.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos/informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                   //-------------------------------------------------------------------------------------------------        

                case "MODELO 421 BLOQ, DESBLOQ DE SALDO DE CONTAS":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao seu Ofício nr................., de ....../..../..."
                            + "referente ao processo nr. ....................., informamos-lhe que"
                            + "efetuamos, nesta data, o bloqueio/desbloqueio do saldo apresentado"
                            + "na conta corrente/poupança nr..., em nome de ........., no valor de"
                            + "R$.......(..............................), Agência...,colocando-o à"
                            + "disposição desse Juízo.\n"
                            + "Declaramos que a(s) informação(ões) constante(s) deste documento e de seu(s) eventual(is) anexo(s), requisitados ao Banco do Brasil S.A., está(ão) protegida(s) pelo sigilo bancário, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos/informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente.");
                    break;
                case "MODELO 421 DEPOSITO JUDICIAL":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao seu Ofício nr..................., de ..../..../...,referente ao processo nr. ......................, informamos-lhe que efetuamos em .../../.../, o depósito judicial nr..., à ordem desse   Juízo, por meio da guia nr..., no valor de R$.......................(..........................), na Agência... em nome de .............\n"
                            + "A conta  está  sendo acompanhada pela Agência...(ag. de   relacionamento com o Poder Judiciário local).\n"
                            + "Declaramos que a(s) informação(ões) constante(s) deste documento e de seu(s) eventual(is) anexo(s), requisitados ao Banco do Brasil S.A., está(ão) protegida(s) pelo sigilo bancário, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os  eventuais "
                            + "esclarecimentos/informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "       Respeitosamente.");
                    break;
                case "OF 15910655 -BLOQ CONTIN - EGT - Rio do Pires - AO JUIZO":
                    txtCorpo.setText("Exmo. Sr. Desembargador Eserval Rocha\n"
                            + "\n"
                            + "\n"
                            + "Em atenção à solicitação, por meio do ofício em epígrafe, datado de 12/06/2014, informamos-lhe que foi realizado em 09/01/2015 o bloqueio de R$25.661,09 (vinte e cinco mil e seiscentos e sessenta e um reais e nove centavos) da conta corrente nº 5.576-X da agência 2399-X de titularidade do Município de Rio do Pires.\n"
                            + "Informamos-lhe que o referido valor está à disposição deste Juízo na conta judicial nº 4700118084493, agência 3832-6-Setor Público-Salvador-BA, Banco do Brasil S/A, conforme comprovante anexo.\n"
                            + "Declaramos que a(s) informação(ões) constante(s) deste documento e de seu(s) eventual(is) anexo(s), requisitados ao Banco do Brasil S.A., está(ão) protegida(s) pelo sigilo bancário Lei Complementar nº 105, de 10 de janeiro de 2001, cuja integridade e preservação ora transferimos para essa Autoridade.	\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                //-------------------------------------------------------------------------------------------
                case "IMPOSSIBILIDADE DE BLOQUEIO":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao seu Ofício supramencionado, datado de xx/xx/xxxx referente ao processo xxxx/xxx.x.xxx.-x (xxxxxxx-xx.xxxx.x.xx.xxxx), informamos-lhe que não foi possível atender à solicitação de  bloqueio de valor, visto que o portador do CPF: xxx.xxx.xxx-xx não possui operações nesta Instituição Financeira.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos/informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                   //------------------------

                //FIM MODELOS ROSA/SANDRA
                // INICIO MODELOS DENISE/DENISE         
                case "Abertura de conta corrente":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que para abertura de conta corrente é necessária a presença do cliente ou representante legal em agência de sua preferência, munido de documento de identidade, CPF e comprovante de endereço, bem como o acolhimento de assinaturas em contrato, cartão de autógrafos e gravação de senha, ainda que se trate de conta para recebimento de pensão alimentícia.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                // ---------------   
                case "Acessibilidade":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o atendimento prioritário deve assegurar que as pessoas tenham, efetivamente, melhor e mais rápido atendimento do que aquele que tem atendimento convencional. \n"
                            + "\n"
                            + "Acrescentamos que a prioridade no atendimento não significa necessariamente a criação de guichês exclusivos, mas atendimento específico para clientes com necessidade especial, como facilidade de acesso às dependências, bem como disponibilização de assentos para espera.\n"
                            + "\n"
                            + "O Banco do Brasil assegura o atendimento prioritário para PCDs – Pessoas com Deficiência ou com mobilidade reduzida, temporária ou definitiva, idosos, com idade igual ou superior a sessenta anos, gestantes, lactantes e pessoas acompanhadas por criança de colo, mediante:\n"
                            + "\n"
                            + "• Acesso facilitado às dependências, aos guichês de caixa e aos terminais de autoatendimento;\n"
                            + "\n"
                            + "• Existência de terminal de autoatendimento adaptado nas Salas de Autoatendimento. Não havendo terminal adaptado, o atendimento é realizado em guichê de caixa destinado ao atendimento prioritário ou outro serviço personalizado;\n"
                            + "\n"
                            + "• Mobiliário de recepção e atendimento obrigatoriamente adaptado à altura e condição física de pessoas em cadeira de rodas;\n"
                            + "\n"
                            + "• Assentos de uso preferencial sinalizados, espaços e instalações acessíveis; \n"
                            + "\n"
                            + "• Permissão da entrada e permanência de cão-guia de acompanhamento junto de pessoa portadora de deficiência ou de treinador nos ambientes de atendimento, mediante apresentação da carteira de vacina do animal;\n"
                            + "\n"
                            + "• Sinalização ambiental para orientação das pessoas portadoras de deficiência ou com mobilidade reduzida; \n"
                            + "\n"
                            + "• Divulgação, em lugar visível, do direito de atendimento prioritário das pessoas portadoras de deficiência ou com mobilidade reduzida;\n"
                            + "\n"
                            + "• Sinalização nas dependências com placa aérea sobre o atendimento prioritário;\n"
                            + "\n"
                            + "• Atendimento diferenciado, imediato e personalizado de acordo com a estrutura física de cada agência. \n"
                            + "\n"
                            + "• Atendimento para pessoas com deficiência auditiva, prestado por intérpretes ou pessoas capacitadas em Língua Brasileira de Sinais – Libras: agências devem manter pelo menos dois funcionários capacitados e PAs devem manter um funcionário capacitado. \n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,\n");
                    break;
                    
                    case "Aliança do Brasil":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que quaisquer solicitações, dúvidas e informações pertinentes a seguros deverão ser enviadas à Avenida das Nações Unidas, 14261, 29º andar, Ala A - Vila Gertrudes - CEP 04.794-000 – São Paulo / SP, pois a COMPANHIA DE SEGUROS ALIANCA DO BRASIL, CNPJ 28.196.889/0001-43, possui personalidade jurídica própria, distinta do Banco do Brasil.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Alienação fiduciária - Falta dados":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que para aprofundarmos nossas pesquisas é necessário informar o RENAVAM, CHASSI e ano/modelo do veículo. \n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Alienação fiduciária COM processo":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o veículo de placa XXX-XXXX – chassi XXXXXXXXXXXXXXXXX, encontra-se alienado fiduciariamente a esta instituição financeira como garantia da operação número XXXXXXX – NOME-DA-OPERAÇÃO, em nome de XXXXXXXXXX, CPF/CNPJ XXXXXXXXXXX, contratada em XX.XX.XXXX, no valor de R$ XXXXXX, para pagamento em XX prestações mensais, consecutivas, no valor de R$ XXXXX, sendo que a operação encontra-se inadimplente.\n"
                            + "\n"
                            + "Informamos, ainda, com relação à referida operação, que o Banco do Brasil move uma ação de execução de título extrajudicial contra XXXXXXXXXXXXXXX, processo nº XXXXXXX-XX.XXXX.X.XX.XXXX – Comarca de XXXXXX-UF.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Alienação fiduciária SEM processo":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o veículo de placa XXX-XXXX – chassi XXXXXXXXXXXXXXXXX, encontra-se alienado fiduciariamente a esta instituição financeira como garantia da operação número XXXXXXX – NOME-DA-OPERAÇÃO, em nome de XXXXXXXXXX, CPF/CNPJ XXXXXXXXXXX, contratada em XX.XX.XXXX, no valor de R$ XXXXX, para pagamento em XX prestações mensais, consecutivas, no valor de R$ XXXXXX, sendo a primeira para XX.XX.XXXX.\n"
                            + "\n"
                            + "Acrescentamos que a operação em questão encontra-se adimplente/inadimplente, com saldo devedor de R$ XXXXXXX. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Alienação fiduciária - Gravame baixado":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que, em consulta ao Sistema Nacional de Gravame, verificamos que o veículo de placa XXX-XXXX – chassi XXXXXXXXXXXXXXXXX, não está alienado a esta instituição financeira, uma vez que o gravame que recaía sobre o veículo encontra-se baixado.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Ativos - Operações cedidas":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o veículo de placa XXX-XXXX – chassi XXXXXXXXXXXXXXXXX, encontra-se alienado fiduciariamente a esta instituição financeira como garantia da operação número XXXXXX – NOME-DA-OPERAÇÃO, em nome de XXXXXXXXXX, CPF/CNPJ XXXXXXXXXX, contratada em XX.XX.XXXX, no valor de R$ XXXXXXX. \n"
                            + "\n"
                            + "Acrescentamos que não dispomos de informações sobre o valor atualizado das parcelas e do saldo devedor do contrato de financiamento em questão, que foi cedido à empresa ATIVOS S.A. SECURITIZADORA DE CREDITOS FINANCEIROS, CNPJ 05.437.257/0001-29, com sede em SEPN Quadra 508, Conjunto “C”, 2º andar – Asa Norte - CEP 70740-543 – Brasília/DF, amparado na Resolução nº 2.686 do CMN/Banco Central, de 26 de janeiro de 2000, e no art. 286 e seguintes do Código Civil Brasileiro, motivo pelo qual sugerimos oficiá-la.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Baixa restrição SERASA e SCPC":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a efetuamos a exceção cadastral para o SCPC e o SERASA  em nome de NNNNNNNNNNNNNNNNNNNNNNNNNNNNN, CPF/CNPJ NNNNNNNNNNNNNNN, referente ao contrato nº NNNNNNNN  - nome da operação.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Banco 24 Horas":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a referida transação foi efetuada em Terminal do Banco 24 Horas, que é administrado pela TECBAN TECNOLOGIA BANCARIA, motivo pelo qual sugerimos que o presente ofício seja enviado à Avenida Andrômeda, 2000, Ed. Jacarandá – Alphaville – CEP 06473-900 – Barueri/SP.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Boleto - Carteira 18":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos a data e local de pagamento, tendo em vista que o título em questão não possui registro (carteira 18), sendo o boleto gerado pelo próprio cedente.\n"
                            + "\n"
                            + "Sugerimos oficiar XXXXXXXXXX a fim de obter maiores informações com relação ao recebimento dos títulos requeridos. \n"
                            + "\n"
                            + "Informamos, ainda, que o sacado XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, não possui nenhuma relação negocial com esta instituição financeira, e acrescentamos que XXXXXXXXXX, CPF XXX.XXX.XXX-XX, possui o seguinte endereço constante em nosso cadastro:\n"
                            + "\n"
                            + "Endereço: XXXXXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Boleto - Desconto TAA":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos:\n"
                            + "\n"
                            + "• É possível o pagamento de valor inferior de boleto bancário no terminal de autoatendimento;\n"
                            + "\n"
                            + "• O TAA efetua a leitura do código de barras com os dados informados pelo cedente. Caso já esteja impresso no próprio boleto o desconto, o valor total será já com o desconto concedido. Caso não esteja, pode ser preenchido manualmente pelo cliente na tela do equipamento no campo “desconto/abatimento”, no momento do pagamento.\n"
                            + "\n"
                            + "• Ao efetuar o preenchimento manual, a responsabilidade do valor recolhido é do próprio cliente, uma vez que o terminal não tem como identificar o valor do desconto. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n" 
                            + "Respeitosamente,");
                    break;
                     case "Brasilprev":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, comunicamos que as informações referentes a planos de previdência aberta da Brasilprev são fornecidas pela BRASILPREV SEGUROS E PREVIDÊNCIAS S.A, situada à Rua Alexandre Dumas, 1671 – Chácara Santo Antônio - CEP 04717-004 - SÃO PAULO/SP, motivo pelo qual sugerimos oficiá-la.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Cartão - Alelo":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o Banco do Brasil não possui ingerência sobre os cartões de benefício emitidos pelas empresas clientes desta instituição financeira, bem como sobre cargas efetuadas e suas respectivas movimentações. Esta relação ocorre entre a Alelo, o funcionário e a empresa, motivo pelo qual sugerimos oficiar a empresa Alelo para maiores esclarecimentos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Cartão - Uso de senha pessoal":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a operação XXX.XXX.XXX, contratada em XX.XX.XXXX, no valor de R$ X.XXX,XX, vinculada à conta corrente X.XXX-X, agência XXXX-X, de titularidade de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, foi efetuada no terminal de autoatendimento número XX.XXX, agência XXXX-X, mediante a utilização de cartão magnético e impostação de senha pessoal, o que configura a ocorrência de quebra de sigilo da senha pelo titular, estando esta instituição financeira isenta, portanto, de qualquer responsabilidade sobre tal movimentação.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "CCF - Exclusão de restrição":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a exclusão do Cadastro de Emitentes de Cheques sem Fundos (CCF) em nome de XXXXXXXX, CPF: XXX.XXX.XXX-XX, deve ser realizada pelo BACEN ou banco de origem do cheque, neste caso o XXXXXX, motivo pelo qual sugerimos oficiá-lo. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "CDC - Crédito adquirido - 4777":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que em nome de XXXXXXXX, CPF XXX.XXX.XXX-XX, consta NOME-DA-OPERAÇÃO contratado nesta instituição financeira em XX.XX.XXXX, que encontra-se adimplente. \n"
                            + "\n"
                            + "Informamos, ainda, que a cliente possui outros empréstimos consignados contratados em outras instituições financeiras e que quaisquer outras informações permanecem exclusivamente a cargo da instituição que efetuou o contrato de empréstimo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "CDC - Outras instituições":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que referente à solicitação de informações acerca de contratos de empréstimo junto aos bancos XXXXX e XXXXX, com desconto em folha da cliente XXXXXXX, CPF XXX.XXX.XXX-XX, conta corrente X.XXX-X, agência XXXX-X, esclarecemos que não possuímos meios de fornecer as informações solicitadas, visto que não temos acesso aos contratos efetuados por outras instituições financeiras. \n"
                            + "\n"
                            + "Sugerimos que o questionamento seja efetuado diretamente àquelas instituições. Informamos, ainda, que o Banco do Brasil recebe o crédito líquido da fonte pagadora.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "CFTV - Disponibilização de imagens":
                    txtCorpo.setText("Senhor(a) Delegado(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Senhoria, por meio do ofício em epígrafe, encaminhamos, em anexo, mídia digital contendo as imagens gravadas por nosso sistema de CFTV – Circuito Fechado de TV, instalado na INFORMAR-DEPENDENCIA em INFORMAR-CIDADE/UF, onde estão registrados os momentos em que ocorreu o fato que essa autoridade visa apurar pelo Inquérito Policial e/ou Boletim de Ocorrência.\n"
                            + "\n"
                            + "A mídia ora fornecida exibe imagens de todos quantos acorreram àquela dependência bancária, merecendo, todavia, preservar as imagens alusivas a clientes e usuários alheios ao delito, consoante preceito ínsito no art. 5.º, inciso X, da Constituição Federal. Desta forma, transferimos a Vossa Senhoria a incumbência de manter invioláveis as imagens das pessoas alheias ao crime sob referência, mas veiculadas na gravação ora lhe disponibilizada.\n"
                            + "\n"
                            + "Permita-nos, ainda, deixar evidenciado que ao fazer a presente entrega o Banco do Brasil S/A não está autorizando o uso das imagens fornecidas para qualquer outra finalidade que não seja a estritamente necessária à condução das investigações que deram ensejo à sua honrosa solicitação.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "CFTV - Portaria":
                    txtCorpo.setText("Senhor(a) Delegado(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Senhoria, por meio do ofício em epígrafe, informamos a impossibilidade de atendimento, pois as imagens solicitadas não constam mais em nosso banco de dados. \n"
                            + "\n"
                            + "Esclarecemos que a Portaria nº. 3233/2012 DG/DPF exige a guarda das imagens captadas pela agência pelo prazo de 30 dias.\n"
                            + "\n"
                            + "Cabe ressaltar que esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça. \n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Cheque - Destruição":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que segue, em anexo, a cópia do cheque nº XXXXXX, no valor de R$ XXXXXXX, vinculado à conta X.XXX-X, agência XXXX-X, de titularidade de XXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Informamos, ainda, a impossibilidade de envio do cheque original, pois, conforme Circular Bacen nº 3532, a instituição financeira acolhedora deve guardar o cheque em papel apenas até a sua liquidação final.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Cheque - Sua remessa":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, abaixo, os dados referentes ao acolhimento do cheque de número XXXXXX, vinculado à conta corrente XXXXX-X, agência XXXX-X, de titularidade de XXXXXXXXXX, CPF XXX.XXX.XXX-XX:\n"
                            + "\n"
                            + "Banco: XXX – XXXXXXXXXXX\n"
                            + "Data: XX.XX.XXXX\n"
                            + "Agência acolhedora: XXXX\n"
                            + "Agência de depósito: XXXX\n"
                            + "Conta de depósito: XXXXX\n"
                            + "Valor: R$ XXXXXX\n"
                            + "\n"
                            + "Acrescentamos que não foi possível identificar a titularidade da conta na qual houve o depósito do cheque mencionado, tendo em vista tratar-se de cliente de outro banco, motivo pelo qual sugerimos oficiá-lo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Citação recebida indevidamente - Aliança do Brasil":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o mesmo deverá ser enviado à Rua Manuel da Nóbrega, 1280, 9º andar – Paraiso – CEP 04001-004 – São Paulo/SP, pois a COMPANHIA DE SEGUROS ALIANCA DO BRASIL, CNPJ 28.196.889/0001-43, possui personalidade jurídica própria, distinta do Banco do Brasil.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "CPF - Homônimos":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que para aprofundarmos nossa pesquisa é necessário o CPF de XXXXXXXXXX ou agência e conta, tendo em vista a quantidade de homônimos em nosso banco de dados.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Curatela":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a curadora nomeada deverá comparecer à agência de relacionamento da curatelada munida dos respectivos documentos de identificação originais e Termo de Nomeação, com extensão dos poderes atribuídos, nos termos do art. 1.754 do Código Civil.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Custo microfilmagem":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, solicitamos, para viabilizar a pesquisa dos cheques, verificar a possibilidade de restringir o período e/ou o valor, tendo em vista que localizamos XXXX cheques (conforme relação em anexo) e que o custo de cada cópia de microfilme é de R$ 7,15, o que totaliza o valor de R$ XXXXXXX.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Dados cadastrais 1":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos os dados cadastrais de XXXXXXXXX, CPF XXX.XXX.XXX-XX, que constam em nosso banco de dados:\n"
                            + "\n"
                            + "\n"
                            + "Dt. Nascimento  : XX.XX.XXXX\n"
                            + "Identificação      : XX.XXX.XXX.XXX-X \n"
                            + "Tipo de Doc.      : XXXXXX\n"
                            + "Órgão Emis./UF : XXXX XX                 \n"
                            + "Naturalidade      : XXXXX-XX\n"
                            + "Nome do Pai      : XXXXXXXXXXXX\n"
                            + "Nome da Mãe    : XXXXXXXXXXXXXX\n"
                            + "Telefone             : (XX) XXXX-XXXX\n"
                            + "\n"
                            + "Endereços:                                       \n"
                            + "\n"
                            + "- Residencial: XXXXXXXXXXXX\n"
                            + "Última atualização em XX.XX.XXXX\n"
                            + "\n"
                            + "- Comercial: XXXXXXXXXXXXX\n"
                            + "Última atualização em XX.XX.XXXX\n"
                            + "\n"
                            + "Declaramos que a integridade e preservação das informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão sendo transferidos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Dados cadastrais 2":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos os dados cadastrais de XXXXXXXXX, CPF XXX.XXX.XXX-XX, que constam em nosso banco de dados:\n"
                            + "\n"
                            + "Data de Nascimento: XX.XX.XXXX | Naturalidade: XXXXXXX | Identificação: XX.XXX.XXX.XXX-X | Tipo de Documento: XXXXXX | Órgão Emissor/UF: XXXX | Data de Emissão: XX.XX.XXXX | Nome do Pai: XXXXXXXXXXXX | Nome da Mãe: XXXXXXXXXXXXXX | Telefone: (XX) XXXX-XXXX\n"
                            + "\n"
                            + "Endereço Residencial: XXXXXXXXXXXX (última atualização em XX.XX.XXXX)\n"
                            + "\n"
                            + "Endereço Comercial: XXXXXXXXXXXXX (última atualização em XX.XX.XXXX)\n"
                            + "\n"
                            + "Declaramos que a integridade e preservação das informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão sendo transferidos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Dados cadastrais - Boleto":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, abaixo, os dados referentes ao boleto em questão:\n"
                            + "\n"
                            + "Linha digitável: \n"
                            + "Nosso número: \n"
                            + "Valor: \n"
                            + "Data de recebimento: \n"
                            + "\n"
                            + "Beneficiário: \n"
                            + "CNPJ: \n"
                            + "\n"
                            + "Endereço: \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Dados cadastrais - Conta Fácil":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, encaminhamos, em anexo, a ficha cadastral de XXXXXXXXXX, CPF XXXXXXXXXXX, titular da conta XXXXX-X, agência XXXX-X, contendo as informações que constam em nosso banco de dados.\n"
                            + "\n"
                            + "Por oportuno, cumpre esclarecer que a conta mencionada foi aberta na modalidade “Conta Fácil”, pautada pela Circular Bacen nº. 3680, de 04/11/2013, que é destinada a não correntistas desta instituição financeira, com a finalidade de acessar serviços bancários de forma simplificada, sem a necessidade da apresentação física ou digital de documentos pessoais, assinatura em contratos, cartão de autógrafos e comando de deferimento da conta por funcionário do banco. \n"
                            + "\n"
                            + "Declaramos que a integridade e preservação das informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão sendo transferidos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Dados cadastrais - Ficha cadastral":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, encaminhamos, em anexo, a ficha cadastral de XXXXXXXXXX, CPF XXXXXXXXXXX, titular da conta XXXXX-X, agência XXXX-X, contendo as informações que constam em nosso banco de dados.\n"
                            + "\n"
                            + "Declaramos que a integridade e preservação das informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão sendo transferidos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Defensor Público - Inventário extrajudicial":
                    txtCorpo.setText("Excelentíssimo(a) Defensor(a) Público(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a impossibilidade no atendimento de vossa requisição, visto que é necessária a apresentação de cópia da procuração outorgada pelos interessados, bem como cópia da nomeação dos mesmos como inventariantes.\n"
                            + "\n"
                            + "Acrescentamos que se tratando de inventário extrajudicial, é possível obter tais informações diretamente na agência, mediante apresentação de certidão ou declaração original do tabelião competente pelos herdeiros.\n"
                            + "\n"
                            + "Tais informações estão protegidas pelo sigilo bancário e sua disponibilização depende de prévia ordem judicial de quebra, consoante Art. 5º, XII da Constituição da República, bem como Art. 1º § 4º da Lei Complementar 105/2001.\n"
                            + "\n"
                            + "O Banco do Brasil manifesta seu respeito e disposição em contribuir com os poderes constituídos, mas não se exime de cumprir as determinações legais, uma vez que a quebra do sigilo fora das hipóteses, prevista na referida lei, constitui crime sujeito a pena de um a quatro anos, conforme disposto em seu Art. 10.\n"
                            + "\n"
                            + "Cabe ressaltar que esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça. Assim, de forma a atender à solicitação e respeitar a legislação pátria vigente, em especial a Lei Complementar nº 105/2001, aguardamos o encaminhamento da respectiva ordem ou decisão judicial que ordenou a quebra do sigilo, para que possamos atender a presente solicitação.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Depósito acima de 10 mil - Identificação":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a obrigatoriedade de identificação de depósitos somente se aplica a valores acima de R$ 10.000,00, em cumprimento à Circular Bacen 3.461/09, motivo que nos impossibilita de atender vossa solicitação.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Depósito envelope":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que consta, na conta XXX.XXX-X, agência XXXX-X, de titularidade de XXXXXXXX, o depósito do envelope XXXXXXXXXXX, em XX.XX.XXXX, no valor de R$ XXXXX. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Devolução de ofício":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Por meio desta devolvemos ao juízo o ofício anexo, expedido nos autos do processo em epígrafe, datado de XX.XX.XXXX, uma vez que possivelmente foi enviado por equívoco ao Banco do Brasil S/A quando, aparentemente, se pretendia enviá-lo ao XXXXXXXXXX.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Dilação de prazo":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, solicitamos a dilação do prazo concedido para mais 60 (sessenta) dias, visto tratar-se de informações as quais deveremos demandar outros setores do banco, o que requer um prazo maior, além do já concedido, para a realização das pesquisas.\n"
                            + "\n"
                            + "Assim, diante de nossa solicitação, requeremos a confirmação da concessão do prazo pela mesma via.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para quaisquer outros esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Encerramento escrituração Petro":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, na condição de administrador das ações de diversas empresas, que em 30/08/2018 ocorreu o fim do contrato de escrituração das ações da Petrobras- Petróleo Brasileiro S.A, CNPJ 33.000.167/0001-01, junto a esta Instituição Financeira.\n"
                            + "\n"
                            + "Por oportuno, informamos que, o Banco Bradesco S.A passou a prestar os serviços de escrituração das ações de emissão da Petrobrás. Motivo pelo qual sugerimos que a mesma seja oficiada.\n"
                            + "\n"
                            + "Declaramos que as informações constantes neste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Endereços":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos, abaixo, os endereços de XXXXXXXX, CPF XXX.XXX.XXX-XX, que constam em nossos sistemas: \n"
                            + "\n"
                            + "• Residencial – dados atualizados em XX.XX.XXXX \n"
                            + "XXXXXXXXXXXX\n"
                            + "\n"
                            + "• Comercial – dados atualizados em XX.XX.XXXX\n"
                            + "XXXXXXXXXXXX\n"
                            + "\n"
                            + "Declaramos que a integridade e preservação das informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão sendo transferidos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Envio de extrato":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, encaminhamos, em anexo, os extratos bancários da conta XXXXX-X, agência XXXX-X, de titularidade de XXXXXXXXXX, CPF XXX.XXX.XXX-XX, referentes ao período de XX.XXXX a XX.XXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Exclusão CCF":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a efetuamos a exclusão do Cadastro de Emitentes de Cheques sem Fundos (CCF), do cheque nº XXXXXX em nome de XXXXXXXXXXXXXXXXXXXX, CPF/CNPJ XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Expurgo - Cheque TED DOC":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"  
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a impossibilidade de fornecer os dados referentes ao(s) beneficiário(s) da(s) transação(ões) indicada(s) por este juízo.\n"
                            + "\n"
                            + "Esclarecemos que tais informações não constam mais em nosso banco de dados, uma vez que, conforme a Circular Bacen 3290, de 05/09/2005, Art. 5º, os arquivos eletrônicos referentes à liquidação de cheques, transferências online ou interbancárias TED/DOC devem ser armazenados por dez anos, tendo sido, portanto, ultrapassado o período de pesquisa estabelecido em vosso ofício.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"  
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                   
                     case "Expurgo - Conta encerrada":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"  
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a conta XX.XXX-X, da agência XXXX-X, de titularidade de XXXXXXXXXX, foi encerrada em XX.XX.XXXX.\n"
                            + "\n"
                            + "Esclarecemos que, conforme Circular Bacen nº 3461, de 24 de julho de 2009, Art. 11º, Inciso III, o prazo de guarda de documentos cadastrais pelas instituições financeiras é de 5 (cinco) anos contados a partir da data de encerramento, motivo pelo qual os documentos da referida conta foram expurgados.\n"
                            + "\n"  
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "FGTS - Falta dados":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos, na base de dados desta instituição financeira, valores a título de FGTS em nome de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Para que possamos aprofundar nossa pesquisa, solicitamos informar: \n"
                            + "• Número e série da CTPS – Carteira de Trabalho;\n"
                            + "• Número da conta do trabalhador (agência e conta);\n"
                            + "• Número do PIS/PASEP do trabalhador;\n"
                            + "• Números das contas dos empregadores (agências e contas);\n"
                            + "• Números dos CNPJ'S dos empregadores.\n"
                            + "\n"
                            + "Permita-nos lembrar, a respeito do mencionado na lei nº 8036/90 art. 23 parágrafo 5º e no decreto nº 99684/90 art. 55, sobre o privilégio do FGTS a prescrição trintenária.\n"
                            + "\n"
                            + "Esclarecemos ainda, caso Vossa Excelência necessite uma posição mais atualizada da conta referente ao titular mencionado, que a partir de 10/04/1992, a administração dos recursos do Fundo de Garantia por Tempo de Serviço foi transferida para a Caixa Econômica Federal – CEF.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "FGTS - Prescrição trintenária":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos os extratos das contas de FGTS de XXXXX, inscrito no CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Acrescentamos que, de acordo com a lei nº 8036/90, art.23 parágrafo 5º e o decreto nº 99684/90, art. 55, há a prescrição trintenária do FGTS.\n"
                            + "\n"
                            + "Informamos ainda que desde abril/1992, o Banco do Brasil S/A não é mais depositário de qualquer recurso relacionado ao FGTS posto que, naquela época, todas as contas e seus respectivos saldos, até então mantidos nesta instituição, foram transferidos para integral administração da Caixa Econômica Federal.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "FIES":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o FIES – Fundo de Financiamento Estudantil é um programa do MEC – Ministério da Educação, sendo o FDNE – Fundo Nacional de Desenvolvimento da Educação o agente operador. O Banco do Brasil atua apenas como o agente financeiro na qualidade de mandatário do FNDE, efetuando a contratação, arrecadação e cobrança das operações do FIES.\n"
                            + "\n"
                            + "Os recursos são exclusivamente oriundos de títulos públicos emitidos pelo FNDE, repassados diretamente para as universidades sem interveniência do Banco do Brasil. \n"
                            + "\n"
                            + "VER O QUE É SOLICITADO, CASO SEJA DESNECESSÁRIO RETIRAR OS PARAGRAFOS ABAIXO:\n"
                            + "\n"
                            + "Informamos ainda, que a taxa de juros de 3,4% a.a é determinada conforme condições fixadas pela Resolução do Conselho Monetário Nacional nº 3.842 de 10/03/2010.\n"
                            + "\n"
                            + "Conforme o cronograma de amortização, em seu campo “Fases de Financiamento – item 1 – Utilização”, consta: “o estudante financiado fica obrigado a pagar trimestralmente os juros incidentes sobre o financiamento, limitados a R$ 50,00 (cinquenta reais)”.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Fraude - Cartão":
                    txtCorpo.setText("Senhora(a) Delegado(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Senhoria, por meio do ofício em epígrafe, informamos que não identificamos transações contestadas na fatura de cartão de crédito de XXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Para que possamos aprofundar nossas pesquisas solicitamos que nos informe as datas e valores das compras para que possamos aprofundar nossas pesquisas. \n"
                            + "\n"
                            + "Antecipadamente, informamos que o IP envolvido nas transações de cartão de crédito não está disponível em nosso sistema, pois esta instituição financeira é apenas o agente arrecadador dos pagamentos. Tal informação poderá ser apurada junto à administradora do cartão, no caso XXXXXXXXX.\n"
                            + "\n"
                            + "Cabe ressaltar que esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça. Assim, respeitando a legislação pátria vigente, em especial a Lei Complementar nº 105/2001, aguardamos o encaminhamento de ordem judicial que autorize o fornecimento de cópias de documentos ou outras informações referentes ao processo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para maiores esclarecimentos porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Fraude - Dados IP e beneficiário de transferência":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que as transações ocorridas em XX.XXX.XXXX, na conta XX.XXX-X, agência XXXX-X, de titularidade de XXXXXXXXXX, CPF XXX.XXX.XXX-XX, foram efetuadas através do servidor XXXXXXXXXXXX, IP XXX.XXX.XX, localizado em XXXXX-UF.\n"
                            + "\n"
                            + "Seguem, abaixo, os dados detalhados das transações:\n"
                            + "\n"
                            + "• TED de R$ XXXXXX em XX.XX.XXXX\n"
                            + "• Banco Favorecido: XXXXXXX, agência XXXX-X em XXXXX/UF\n"
                            + "• Conta Corrente XX.XXX-X\n"
                            + "• Nome: XXXXXX\n"
                            + "• CPF: XXX.XXX.XXX-XX\n"
                            + "• Agendada em XX.XX.XXXX às XXhXXmin\n"
                            + "• Convênio: XXXXXXX, valor de R$ X.XXX,XX\n"
                            + "• Agendado em XX.XX.XXXX às XXhXXmin \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Fraude - ROI 1":
                    txtCorpo.setText("Senhor(a) Delegado(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Senhoria, por meio do ofício em epígrafe, informamos que XXXXXXXXX, CPF XXX.XXX.XXX-XX, contestou transações realizadas em sua conta corrente ocorridas entre XX.XX.XXXX e XX.XX.XXXX, as quais totalizaram o valor de R$ XXXXXX.\n"
                            + "\n"
                            + "Esclarecemos que, após a análise da contestação, o processo interno de apuração das ocorrências foi concluído com ônus para o Banco do Brasil, sendo o cliente ressarcido no valor total, através de crédito na conta X.XXX-X, agência XXXX-X, em XX.XX.XXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Fraude - ROI 2":
                    txtCorpo.setText("Senhor(a) Delegado(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Senhoria, por meio do ofício em epígrafe, informamos que, após apuração interna, a conta XX.XXXX-X, agência XXXX-X, de titularidade de XXXXXXXXXXXXXX, CPF nº XXX.XXX.XXX-XX, foi ressarcida no valor de R$ XXXXX no dia XX.XX.XXXX.\n"
                            + "\n"
                            + "Acrescentamos que não foi possível recuperar as imagens de CFTV, conforme a legislação vigente, Portaria 387/2006 DG/DPF, tais imagens ficam disponíveis apenas pelo período de 30 dias.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Improbidade administrativa":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que foram tomadas as providências internas de anotações cadastrais referentes a improbidade administrativa em nome de XXXXXXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, na qual consta a proibição de contratar com o Poder Público ou receber benefícios/incentivos fiscais/creditícios, direta ou indiretamente, ainda que por intermédio de pessoa jurídica da qual seja sócio majoritário, pelo período de XXXXX anos, com baixa em XX.XX.XXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "INSS - Renovação de senha":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não dispomos das informações de renovação de senha do benefício n° XXX.XXX.XXX-X, de titularidade de XXXXXXX, CPF XXX.XXX.XXX-XX, pois o período de guarda das informações é de 5 (cinco) anos. \n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Inventário":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos os saldos existentes, nesta data, na(s) conta(s) de XXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Agência/Conta:\n"
                            + "XXXX-X / XX.XXX-X – Conta Corrente – Saldo de........................R$ XXXXXX\n"
                            + "XXXX-X / XXX.XXX.XXX-X – Conta Poupança – Saldo de........................R$ XXXXXX\n"
                            + "\n"
                            + "Por oportuno, informamos a existência de débito em nome do(a) falecido(a), a atualizar, no valor de R$ XXXXXXX, referente a XXXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "IR - Devolvido à Receita":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que consta, em nosso sistema, para XXXXXXXX, CPF XXX.XXX.XXX-XX, o registro de restituições devolvidas à Receita Federal referente ao(s) exercício(s) XXXX - lote XX, motivo pelo qual sugerimos oficiá-la para maiores informações.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "IR - Não resgatado":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que XXXXXXXX, CPF XXX.XXX.XXX-XX, possui o(s) valor(es) abaixo referente(s) à restituição de imposto de renda:\n"
                            + "\n"
                            + "Exercício/Lote\n"
                            + "XXXX/XX – Não resgatada...............................................R$ XXXXXX\n"
                            + "\n"
                            + "Informamos, ainda, que consta em nossos registros a devolução da restituição referente ao exercício de XXXX - lote XX à Receita Federal, motivo pelo qual sugerimos oficiá-la para maiores informações.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Ofício já respondido":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o ofício n° XXXXXXXX, foi respondido em XXXXXXXXX, conforme cópia em anexo. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Operação de outra insituição":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a operação foi efetuada em outra instituição financeira, neste caso, no(a) XXXXXXX, motivo pelo qual sugerimos oficiá-lo(a).\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Ordem de pagamento":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que localizamos ordem de pagamento enviada pelo XXXXXXXX, em XX.XX.XXXX, no valor de R$ XXXXX, para XXXXXXXXXX, CPF XXX.XXX.XXX-XX, pago no caixa da agência XXXX-X, em XX.XX.XXXX, conforme o comprovante anexo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Ourocap":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que quaisquer solicitações, dúvidas e informações pertinentes a Título de Capitalização OUROCAP são fornecidas pela BRASILCAP CAPITALIZAÇÃO S/A, situada à RUA SENADOR DANTAS, 105, 09º E 10° ANDARES, DEPARTAMENTO JURÍDICO – CENTRO – CEP 20031-201 – RIO DE JANEIRO/RJ, motivo pelo qual sugerimos oficiá-la. \n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Parcial":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, encaminhamos, em anexo, XXXXXXXXXXXXXXXXXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Os demais documentos, assim que localizados, serão encaminhados em novo ofício em complemento a esse.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"       
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "PASEP":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que consta o saldo de R$ XXXXXX para o PASEP X.XXX.XXX.XXX-X, de titularidade de XXXXXXXXX, CPF XXX.XXX.XXX-XX. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "PIS e FGTS - CEF - Falta dados":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos a inscrição PASEP em nosso sistema com os dados constantes no ofício.\n"
                            + "\n"
                            + "Para aprofundarmos nossa pesquisa, solicitamos informar o CPF de XXXXXXXXX. Acrescentamos que a Caixa Econômica Federal é a atual administradora do PIS/FGTS, motivo pelo qual sugerimos que a mesma seja oficiada para a pesquisa de existência de PIS/FGTS em nome do requerido.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "PIS e FGTS - CEF":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos inscrição de PASEP para XXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Acrescentamos que a Caixa Econômica Federal é a atual administradora do PIS/FGTS, motivo pelo qual sugerimos que a mesma seja oficiada para a pesquisa de existência de PIS/FGTS em nome do requerido.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                        
                    case "Portaria - Solicitação":
                    txtCorpo.setText("Senhor(a) Escrivão(ã), \n"
                            + "\n"
                            + "\n"
                            + "Em atenção à vossa solicitação, através do ofício expedido nos autos do processo em epígrafe, informamos que necessitamos da cópia da referida portaria XX/XXXX para atendimento da solicitação. Cabe ressaltar que esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça. \n"
                            + "\n"
                            + "Assim, respeitando a legislação pátria vigente, em especial a Lei Complementar nº 105/2001, aguardamos o encaminhamento de ordem judicial que autorize o fornecimento de cópias de documentos ou outras informações referentes ao processo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Protesto preferência - Operação liquidada":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que a operação de crédito nº XXXXXXXXX, que originou a penhora no bem de matrícula/chassi XXXXXX, em nome de XXXXXXXXXX, inscrito(a) no CPF/CNPJ XXXXXXXXXXX, está liquidada nesta instituição financeira desde XX.XX.XXXX.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Quebra de sigilo - Comparecer à agência":
                    txtCorpo.setText("Senhor(a) Técnico(a) Judiciário(a), \n"
                            + "\n"
                            + "\n"
                            + "Em relação ao Ofício em epígrafe, informamos que tais informações estão protegidas pelo sigilo bancário e que sua disponibilização depende de prévia ordem judicial de quebra, consoante Art. 5º, XII da Constituição da República bem como Art. 1° § 4° da Lei Complementar 105/2001.\n"
                            + "\n"
                            + "Caso seja do interesse do cliente, o mesmo poderá comparecer à sua agência de relacionamento e solicitar a documentação pessoalmente, mediante identificação. \n"
                            + "\n"
                            + "O Banco do Brasil manifesta seu respeito e disposição em contribuir com os poderes constituídos, mas não se exime de cumprir as determinações legais, uma vez que a quebra do sigilo fora das hipóteses, prevista na referida lei, constitui crime sujeito à pena de um a quatro anos, conforme disposto em seu Art. 10.\n"
                            + "\n"
                            + "Cabe ressaltar que esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça. Assim, de forma a atender à solicitação e respeitar a legislação pátria vigente, em especial a Lei Complementar nº 105/2001, aguardamos o encaminhamento da respectiva ordem ou decisão judicial que ordenou a quebra do sigilo, para que possamos atender a presente solicitação.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    case "Quebra de sigilo - Procurador Assistente":
                    txtCorpo.setText("Senhor(a) Procurador(a) Assistente, \n"
                            + "\n"
                            + "\n"
                            + "Em relação ao ofício em epígrafe, informamos a impossibilidade de atender à solicitação acerca do fornecimento de informações quanto as movimentações em conta corrente em nome de XXXXXXXXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, em face de o fato resultar em quebra ilegal de sigilo bancário, assim como disposto no artigo 5º, X e XII da Constituição Federal, e sujeita os administradores desta instituição financeira às penalidades previstas na Lei de Sigilo Bancário, Lei Complementar 105/2001, especificamente, no artigo 10.\n"
                            + "\n"
                            + "Diante de tal fato, com o intento de prestar os melhores serviços a essa respeitosa instituição, informamos que, mediante determinação de uma das autoridades previstas na lei acima aludida, os documentos estarão ao inteiro dispor de vossa senhoria, em cópia.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Quebra de sigilo - Responsável Expediente":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em relação ao ofício em epígrafe, informamos que tais informações estão protegidas pelo sigilo bancário e que sua disponibilização depende de prévia ordem judicial de quebra, consoante Art. 5º, XII da Constituição da República bem como Art. 1° § 4° da Lei Complementar 105/2001.\n"
                            + "\n"
                            + "O Banco do Brasil manifesta seu respeito e disposição em contribuir com os poderes constituídos, mas não se exime de cumprir as determinações legais, uma vez que a quebra do sigilo fora das hipóteses, prevista na referida lei, constitui crime sujeito à pena de um a quatro anos, conforme disposto em seu Art. 10.\n"
                            + "\n"
                            + "Caso entenda que as informações sejam necessárias, solicitamos encaminhar ao banco ofício assinado por autoridade judicial ou a decisão judicial que ordenou a quebra do sigilo, para que possamos atender a presente solicitação.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Quebra de sigilo":
                    txtCorpo.setText("Senhor(a) Técnico(a) Judiciário(a), \n"
                            + "\n"
                            + "\n"
                            + "Em relação ao ofício em epígrafe, informamos que tais informações estão protegidas pelo sigilo bancário e que sua disponibilização depende de prévia ordem judicial de quebra, consoante Art. 5º, XII da Constituição da República bem como Art. 1° § 4° da Lei Complementar 105/2001.\n"
                            + "\n"
                            + "O Banco do Brasil manifesta seu respeito e disposição em contribuir com os poderes constituídos, mas não se exime de cumprir as determinações legais, uma vez que a quebra do sigilo fora das hipóteses, prevista na referida lei, constitui crime sujeito a pena de um a quatro anos, conforme disposto em seu Art. 10.\n"
                            + "\n"
                            + "Cabe ressaltar que esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça. \n"
                            + "\n"
                            + "Assim, de forma a atender à solicitação e respeitar a legislação pátria vigente, em especial a Lei Complementar nº 105/2001, aguardamos o encaminhamento da respectiva ordem ou decisão judicial que ordenou a quebra do sigilo, para que possamos atender a presente solicitação.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Reversão INSS - Abaixo de cinco anos":
                    txtCorpo.setText("Senhor(a) Chefe,\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício em epígrafe, informamos não ser possível a liquidação da GPS/GRU, no valor de R$ XXXXXX, referente à devolução de crédito indevido em conta corrente/poupança do beneficiário XXXXXXXXXX, falecido, benefício número XXXXXXXXXX, tendo em vista que não houve realização de “Prova de Vida” pós-óbito realizada pelo Banco do Brasil S.A., além do disposto no Art. 3º, da Resolução nº 3695 do Banco Central do Brasil, de 26/03/2009.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Reversão INSS - Estorno":
                    txtCorpo.setText("Senhor(a) Chefe,\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício em epígrafe, informamos a efetivação do estorno no valor de R$ XXXXXX, da conta X.XXX-X, agência XXXX-X, conforme comprovante anexo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Reversão INSS - Prescrição":
                    txtCorpo.setText("Senhor(a) Chefe, \n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício em epígrafe, informamos não ser possível a liquidação da GPS/GRU, no valor de R$ XXXXX, referente à devolução de crédito indevido em conta corrente/poupança do beneficiário XXXXXXXXXX, falecido, benefício número XXXXXXXXXX, tendo em vista o prazo prescricional de 5 (cinco) anos em consonância ao disposto nos seguintes dispositivos:\n"
                            + "\n"
                            + "a) Lei 8.213/91 - art. 103 - parágrafo único;\n"
                            + "b) Código Tributário Nacional - art. 174;\n"
                            + "c) Código Tributário Nacional - art. 168;\n"
                            + "d) Lei nº 12.529/11 - art. 46;\n"
                            + "e) Decreto nº 20.910/32 - art. 1º;\n"
                            + "f)  Lei nº 9.873/99 - art. 1º;\n"
                            + "g) Lei nº 8.112/90 - art. 142;\n"
                            + "h) Lei nº 8.429/92 - art. 23;\n"
                            + "i)  Lei nº 6.838/80 - art. 1º e Lei nº 8.906/94 - art. 43;\n"
                            + "j)  Lei nº 4.717/65 - art. 21.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Seguridade":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que consta/constava (VERIFICAR-SE-A-PROPOSTA-AINDA-ESTÁ-VIGENTE a proposta nº XXXXXXXX, referente à apólice de seguro de vida nº XX.XXX, segurado XXXXXXXXXX, CPF XXX.XXX.XXX-XX.\n"
                            + "\n"
                            + "Acrescentamos que a análise de pagamento do sinistro caberá à seguradora e o comunicado deverá ser realizado pelo beneficiário, através da central de atendimento, no telefone 0800 729 7000.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Seguro Crédito Protegido":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que XXXXXX, CPF XXX.XXX.XXX-XX, possui seguro(s) na modalidade SEGURO CRÉDITO PROTEGIDO nº XXXXX. \n"
                            + "\n"
                            + "Para entendimento, este tipo de seguro pessoal tem como objetivo garantir, até o limite do capital segurado, a quitação ou amortização das dívidas assumidas pelo segurado, pessoa física, oriundas de operações de crédito, financiamento ou arrendamento mercantil com o estipulante. Sugerimos o registro de sinistro, que poderá ser realizado pelo beneficiário, através da central de atendimento, no telefone 0800-729-7000.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "Sem cadastro":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos XXXXXXX, inscrito(a) no CPF XXXXXXXXX, em nosso banco de dados.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Sem operações":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que XXXXXXXXXXXX, inscrito(a) no CPF XXX.XXX.XXX-XX, não possui conta ativa ou qualquer aplicação nesta instituição financeira.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                    case "Solicitação de matrícula":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Reportamo-nos aos termos contidos no expediente sob referência, mediante a qual Vossa Excelência comunica a esta instituição que foi a leilão e/ou penhorado o(s) imóvel(is) de Matrícula(s) XXXXXXXXXXX do Registro de Imóveis de XXXXXXXXXXX.\n"
                            + "\n"
                            + "Em atenção ao respeitoso ofício, cumpre-nos informar que apenas com as informações contidas em vosso expediente e após pesquisas junto aos nossos setores competentes, não logramos êxito em localizar registros acerca da hipoteca objeto da matrícula acima descrita.\n"
                            + "\n"
                            + "Desta forma, para o fim de ser dado o correto atendimento e as medidas judiciais cabíveis ao r. ofício em epígrafe, solicitamos a Vossa Excelência o especial obsequio de fornecer a esta Instituição o que se segue:\n"
                            + "\n"
                            + "a) Cópia completa com todas as páginas da matrícula do aludido imóvel vinculado aos Autos, contendo os demais dados e atinentes, assim como anotação de restrição financeira (hipoteca) sobre o mesmo, a favor desta Instituição.\n"
                            + "\n"
                            + "Sendo o que nos cumpre, aproveitamos a oportunidade para apresentar a Vossa Excelência as nossas cordiais saudações.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                     case "TAA imagens - Desativado":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não foi possível recuperar as fotos dos saques realizados na conta corrente de titularidade de XXXXXXXXXX, CPF XXX.XXX.XXX-XX, devido à indisponibilidade dos terminais de autoatendimento onde os saques ocorreram. Estes foram substituídos em virtude da obsolescência do equipamento e as informações neles contidas foram descartadas.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "TAA imagens - Falta dados":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que há a possibilidade do fornecimento das imagens registradas nos caixas eletrônicos, porém, para que possamos efetuar as buscas solicitadas, é necessário o fornecimento de maiores informações, tais como a agência e a conta, o CPF/CNPJ dos envolvidos, o horário e a data da ocorrência.\n"
                            + "\n"
                            + "Informamos, ainda, que caso os terminais tenham sofrido avarias ou sido desativados, não será possível a recuperação das imagens.\n"
                            + "\n"
                            + "Esta instituição financeira tem interesse em contribuir, sempre que possível, com todos os órgãos que exercem funções essenciais à justiça e nos colocamos à disposição para eventuais esclarecimentos e informações porventura necessários, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Territorialidade":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, esclarecemos que a prestação de informações referentes às operações/contas correntes, mantidas em agências localizadas em países estrangeiros, está sujeita às normas jurídicas locais dos países envolvidos.\n"
                            + "\n"
                            + "Assim, por força do princípio da territorialidade, preceituado no nosso ordenamento jurídico no artigo 1º da Constituição Federal, bem como no artigo 9º da Lei de Introdução ao Código Civil e artigos 164 e 165 do Código Bustamante, internalizado no ordenamento pelo Decreto nº 18.871/29, o banco só pode prestar informações sobre operações/contas correntes existentes no território nacional.\n"
                            + "\n"
                            + "Ressaltamos, ainda, que as agências, entre si, não têm acesso comum ao cadastro dos clientes.\n"
                            + "\n"
                            + "O requerimento de informações bancárias de contas localizadas em outro país deverá seguir os trâmites descritos na Portaria Interministerial nº 501 MRE/MJ, de 21.03.2013, disponibilizada no sítio do Ministério da Justiça.  \n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Transferência cliente para conta judicial":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que o valor total de R$ XXXXXX foi transferido para a conta judicial XXXXXXX-X, agência XXXX-X, do banco XXXXXXXXXX, em favor de XXXXXXXX.\n"
                            + "\n"
                            + "A transferência entre as referidas contas foi executada, via TED, em XX.XX.XXXX, sob ID de nº XXXXXXXXXXXXX. Encaminhamos, em anexo, a cópia do comprovante.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "Transferência online":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, segue as informações das transações solicitadas:\n"
                            + "\n"
                            + "XX.XX.XXX – transferência online – R$ XXXXXXX\n"
                            + "Agencia: XXXX-X – Conta: XXXXXXX-X\n"
                            + "Titular: XXXXXXXXXX\n"
                            + "CPF/CNPJ: XXX.XXX.XXX-XX\n"
                            + "\n"
                            + "Informamos que não dispomos dos comprovantes de tais movimentações, por se tratarem de transferências online, realizadas através de internet banking.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                     case "URV":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a), \n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não localizamos, no período de XXXXXXXX, pagamento de proventos da Prefeitura XXXXXXXX, CNPJ XXXXXXXXX nesta instituição financeira.\n"
                            + "\n"
                            + "Informamos, ainda, que o Banco do Brasil realiza transações apenas em moeda corrente. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas instituições financeiras, cuja integridade e preservação ora transferimos para essa autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                         
                    case "Continuado - Bloqueio de Conta Salário":
                    txtCorpo.setText("Meritíssimo Juíz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos na conta salário 4.500.00X.XXX-X, da agência X.XXX-X, de titularidade de XXXXXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX :\n"
                            + "\n"
                            + "- Valor: X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "\n"
                            + "Ressaltamos, que a conta é recebedora de proventos, motivo pelo qual aguardamos confirmação da manutenção do bloqueio total ou nova orientação quanto ao tipo de bloqueio (percentual mensal ou valor fixo) para transferência dos valores para conta judicial na XXXXXXXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das Operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                    case "Continuado - Bloqueio e Transf Conta Judicial":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos na conta corrente X.XXX-X, da agência XXXX-X, de titularidade de XXXXXXXXXXX, CPF XXX.XXX.XXX-XX: \n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: R$ X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos que o referido valor foi transferido para a conta judicial XXXXXXXXXXX, da agência XXXX-X, estando a disposição desse juízo, conforme comprovante anexo.         \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                    case "Continuado - Bloqueio e Transf Outro Banco":
                    txtCorpo.setText("Meritíssimo Juíz,\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos na conta corrente XX.XXX-X, da agência X.XXX-X, de titularidade de XXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX:\n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos que o referido valor foi transferido para o Banco XXXXXXX., agência XXXX-X, conta XXXXX-X, de titularidade de XXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, conforme Vossa solicitação. Segue comprovante anexo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "Continuado - Bloqueio e Transf para CC":
                    txtCorpo.setText("Meritíssima Juiza,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos da conta corrente XXX.XXX-X, da agência XXXX-X, de titularidade de XXXXXXXXX, CPF XXX.XXX.XXX-XX:\n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos, que o referido valor foi transferido para a conta corrente XX.XXX-X, da agência XXXX-X, de titularidade de XXXXXXXXX, CPF XXX.XXX.XXX-XX, conforme Vossa solicitação.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "Continuado - Bloqueio sem Rendimentos":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos na conta corrente X.XXX-X, da agência XXXX-X, de titularidade de XXXXXXXXXX, CPF XXX.XXX.XXX-XX, estando à disposição desse juízo:\n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "\n"
                            + "Por oportuno, para que os valores passem a ser remunerados, colocamos a disposição de Vossa Excelência a possibilidade de transferência para uma conta judicial vinculada ao processo, mediante autorização desse juízo.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "Continuado - Bloqueio valor Parcial":
                    txtCorpo.setText("Meritíssimo Juiz ,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não foi atingido o percentual de XX% referente aos proventos de XXXXXXXXX, portador do CPF  XXX.XXX.XXX-XX, titular da conta corrente XX.XXX-X, da agência X.XXX-X.\n"
                            + "\n"
                            + "Acrescentamos, que em XX/XX/XXXX, bloqueamos o valor de R$ X.XXX,XX (XXXXXXXXXXXXXXXX) através do Protocolo XXXXXXXXXXX, estando à disposição deste juízo.\n"
                            + "\n"
                            + "Esclarecemos que para efetuarmos o bloqueio de valor, é necessário que o cliente possua saldo livre e disponível.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                 case "Continuado - Encerramento Prazo":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos na conta corrente XX.XXX-XX, da agência X.XXX-X (XXXXXXXXXXXX/XX), de titularidade de XXXXXXXXXXX, CPF/CNPJ XXXXXXXX: \n"
                            + "\n"
                            + "- Valor: R$ XX,XX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos que os referidos valores foram transferidos para a conta judicial XXXXXXXXX, da agência X.XXX-X (XXXXX/XX), estando a disposição desse juízo, conforme comprovantes anexos.\n"
                            + "\n"
                            + "Informamos ainda, que decorrido o prazo solicitado de XX dias, encerramos os bloqueios a partir desta data.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                 case "Continuado - Encerramento Salário":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, referente à solicitação de transferência de XX% do salário LÍQUIDO OU BRUTO em nome de XXXXXXXXXX, CPF XXX.XXX.XXX-XX, informamos que não foi possível o cumprimento, tendo em vista que não houve crédito referente a proventos nos meses de XXXX  a XXXXX de 20XX.\n"
                            + "\n"
                            + "Acrescentamos, que estamos encerrando o acompanhamento e os bloqueios na  conta de titularidade da executada, uma vez que os proventos deixaram de ser depositados nesta Instituição Financeira. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;      
                case "Continuado - FPM Porcentagem":
                    txtCorpo.setText("Meritíssimo(a) Juíz(a),\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, referente à retenção de XX% sobre a cota do FPM do MUNICIPIO DE XXXXXXXX (XX), CNPJ XX.XXX.XXX/XXXX-XX, informamos que transferimos da conta corrente XX.XXX-X, da agência XXXX-X (XXXXX/XX) para a conta judicial XXXXXXXXXXXXX, da agência XXXX-X (XXXX/XX), estando à disposição desse juízo:\n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: R$ X.XXX,XX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos que segue anexo o comprovante do depósito.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Continuado - FPM Valor Fixo":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, referente à retenção de valores da conta do FPM do MUNICÍPIO DE XXXXXXX - XX, CNPJ XX.XXX.XXX/XXXX-XX, informamos que transferimos da conta corrente X.XXX-X, da agência XXXX-X, para a conta judicial XXXXXXXXXXX, da agência XXXX-X , estando à disposição desse juízo:\n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: R$ X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos, que encaminhamos anexo o comprovante do depósito. \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Continuado - FPM Variável":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, referente à retenção de valores da conta do FPM do Município de XXXXXXX / XX, CNPJ XX.XXX.XXX/XXXX-XX, informamos que transferimos da conta corrente X.XXX-X, da agência XXXX-X para a conta judicial XXXXXXXXX, da agência XXXX-X (XXXX/XXX), estando à disposição desse juízo, conforme comprovante anexo:\n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Salientamos que a retenção foi calculada sobre o repasse bruto do mês anterior (XXXXXXXXX/XXXX).\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Continuado - Informação Valor total da execução":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que bloqueamos na conta corrente X.XXX-X, da agência XXXX-X, de titularidade de XXXXXXXXXXX, CPF XXX.XXX.XXX-XX: \n"
                            + "\n"
                            + "- Parcela: XXª\n"
                            + "- Valor: R$ X.XXX,XX\n"
                            + "- Protocolo: XXXXXXXXX\n"
                            + "- Data: XX/XX/XXXX\n"
                            + "- Parcela da conta judicial: XXª\n"
                            + "\n"
                            + "Acrescentamos que o referido valor foi transferido para a conta judicial XXXXXXXXXXX, da agência XXXX-X, estando a disposição desse juízo, conforme comprovante anexo.         \n"
                            + "\n"
                            + "Para que sejam cumpridos os bloqueios conforme determinação, solicitamos nos informar o valor total da execução, mencionando nosso número “AOF XXXX/XXXXX/Continuado em Vosso ofício.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Continuado - Sem Saldo para Bloqueio":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, referente à solicitação de transferência de XX% do salário liquido mensal em nome de XXXXXXXXXXXXXX, CPF  XXX.XXX.XXX-XX, informamos que não foi possível o cumprimento devido a insuficiência de saldo. \n"
                            + "\n"
                            + "Esclarecemos que para efetuarmos o bloqueio de valor é necessário que o cliente possua saldo livre e disponível.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "Continuado - Sem Saldo Sugestão":
                    txtCorpo.setText("Meritíssimo Juiz,\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, referente à solicitação de transferência de XX% do salário liquido mensal em nome de XXXXXXXXXXXXXX, portador do CPF  XXX.XXX.XXX-XX, informamos que não foi possível o cumprimento devido a insuficiência de saldo. \n"
                            + "\n"
                            + "Esclarecemos que para efetuarmos o bloqueio de valor é necessário que o cliente possua saldo livre e disponível.  A movimentação de contas e/ou aplicações se dá por diversas maneiras, tais como Terminais de Auto Atendimento (das 6h às 23h), internet (24h por dia), entre outras, impossibilitando qualquer controle ou monitoramento.\n"
                            + "\n"
                            + "Para garantir a não movimentação dos recursos pelo cliente, sugerimos solicitar o bloqueio dos valores diretamente em Folha de Pagamento junto ao empregador XXXXXXXXXXXXX, CNPJ XX.XXX.XXX/XXXX-XX.\n"
                            + "\n"
                            + "Caso o bloqueio passe a ser efetuado pelo empregador, solicitamos nos  informar, citando nosso número AOFXXXX/XXXXXX para o encerramento  dos bloqueios por parte do Banco.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "TRABALHISTA - ABERTURA CONTA DJO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que a conta judicial foi aberta sob nº XXXXXXXXXXXX, na agência 5905-6 Poder Judiciário/SP, nos autos do processo em epígrafe.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                    
                  case "TRABALHISTA - ABERTURA CONTA JUDICIAL":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos à  V. Exa. que realizamos o  Pré-Cadastramento de Depósito Judicial  conforme abaixo:\n"
                            + "\n"
                            + "ID nº XXXXXXXXXXXXXXXXXX- NOME DO TITULAR\n"
                            + "\n"
                            + "Procedimento para efetivação do depósito:\n"
                            + "\n"
                            + "Acessar o site do Banco do Brasil na opção buscar,  localizar a informação \"Emissão de Guia/ID Depósito Judicial\".\n"
                            + "\n"
                            + "- Assinalar  o \"tipo de Justiça\";\n"
                            + "- Em pré cadastramento, selecionar \"Nova parcela do primeiro depósito\";\n"
                            + "- Informar o nº do ID indicado acima;\n"
                            + "\n"
                            + "Preencher os campos obrigatórios,  gerar boleto, com um novo nº de ID, que deverá ser utilizado para a efetivação do depósito.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;         
                    
                 case "TRABALHISTA - ABERTURA DE CONTA POUPANCA MENOR":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, solicitamos, por especial obséquio, que seja determinado ao representante legal do menor, que compareça à nossa congênere de prefixo 4393-1 – Tribunal Regional do Trabalho – São Paulo/SP, sita à Av. Marquês de São Vicente, 235 – Térreo – Barra Funda – São Paulo/SP, CEP 01139-001, com todos os documentos necessários, para que possamos efetuar a abertura da conta poupança.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - ANEXO NÃO ENCAMINHADO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº 0031/2016 de 11/01/2016, protocolado em 18/01/2016, para que possamos cumprir suas determinações, solicitamos, por especial obséquio, fornecer os dados ainda faltantes, conforme abaixo:\n"
                            + "\n"
                            + "–	Anexo mencionado no ofício supra não encaminhado.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;   
                case "TRABALHISTA - ASSENTAMENTOS PESQUISA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que em pesquisa efetuada em nossos assentamentos, não localizamos o aviso de crédito mencionado no ofício supra, vinculado aos autos do processo em epígrafe, com os dados informados.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                case "TRABALHISTA - BENEFICIÁRIO FALECIDO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do alvará expedido nos autos do processo em epígrafe, informamos a V.Exa. que não foi possível cumprimos vossa R. Determinação Judicial de transferência, em razão do beneficiário indicado ser falecido. Solicitamos a Vossa Excelência nos indicar o nome e o cpf de novo beneficiário para que possamos cumprir o alvará enviado. \n"
                            + "\n"
                            + "Solicitamos ainda a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,"); 
                    break;
                 case "TRABALHISTA - BRASÍLIA-DF LEVANTAMENTOS EFETUADOS":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de xx/xx/xxxx, protocolado em xx/xx/xxxx, informamos que o(s) levantamento(s) foi(ram) efetuado(s), em cumprimento à determinação desse Juízo, estando os valores à disposição do beneficiário indicado no documento de levantamento, para pagamento em espécie em qualquer agência localizada na UF de jurisdição do tribunal.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,"); 
                    break;    
                case "TRABALHISTA - CHEQUE DEVOLVIDO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "CANCELAMENTO DE CRÉDITO - Encaminhamento de cheque devolvido: O cheque abaixo relacionado, acolhido para quitação da Guia de Depósito igualmente referida a seguir, foi devolvido pelo Serviço de Compensação por motivo XX (XXXXXX XXXXXX XXXXXX XXXXXX XXXXXX) cujo crédito, tornamos sem efeito:\n"
                            + "\n"
                            + "CHEQUE	           - XXXXXXXXXXXXX   \n"
                            + "BANCO   	           - XXXXXX\n"
                            + "AGÊNCIA	           - XXXXXX\n"
                            + "VALOR   	           - XXXXXX\n"
                            + "CONTA JUDICIAL - XXXXXXXXXXXXX\n"
                            + "GD	           - XXXXXX\n"
                            + "\n"
                            + "OBS: AVISO DE CRÉDITO JÁ ENCAMINHADO, FAVOR DESCONSIDERAR.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - CONGÊNERE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Informamos que foi remetido em 29/01/2016 o ofício nº 0028/2016 de 28/01/2016, protocolado em 29/01/2016, para a nossa congênere de prefixo 1981-X – CENOP Serviços São Paulo/SP, sita à Rua Boa vista, 254 – 14º andar – Centro – São Paulo/SP, CEP 01014-907, para atender à determinação ali contida, oficiando diretamente a esse Juízo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;        
                case "TRABALHISTA - CONTA CEF":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que:\n"
                            + "\n"
                            + "-	Número da conta judicial informada pertence ao Banco 104 - Caixa Econômica Federal e, portanto, desconsideraremos as determinações contidas no ofício.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;   
                    
                case "TRABALHISTA - CONTA NÃO LOCALIZADA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, informamos que, em pesquisa efetuada em nossos assentamentos, não foram localizados créditos para o processo supra, com os dados informados. \n"
                            + "\n"
                            + "Para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer os dados ainda faltantes, conforme abaixo:\n"
                            + "\n"
                            + "–	Cópia dos avisos de crédito e/ou das guias de depósitos autenticadas.\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;      
                case "TRABALHISTA - CONTA VINCULADA A OUTRA VARA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, esclarecemos que os depósitos efetuados na conta judicial nº XXXXXXXXXXXXXXXX, no valor total de R$ XXXXXXXXXX, pertencem a XXª Vara do Trabalho de XXXXXXXXXX, nos autos do processo nº XXXXXXXXXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Diante do exposto, solicitamos, por especial obséquio, que esse Juízo oficie a respectiva vara detentora do depósito em questão, para que essas encaminhem ofício autorizando a(s) transferência(s) solicitada(s) no requerimento supra.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                 case "TRABALHISTA - CONTA NÃO VINCULADA AO PROCESSO E NEM OUTRA VARA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº 0348/2017 de 15/02/2017, solicitamos por especial obséquio, que esse Juízo retifique as informações contidas em vosso ofício, quanto ao número do processo e a conta judicial, visto que a conta 0700127961929 não está vinculado ao processo informado no ofício expedido (00378006220055020016).\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;      
                  case "TRABALHISTA - CONVÊNIO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(íza).\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, informamos respeitosamente a esse R. Juízo que não foi possível efetuar a transferência de valores solicitados, visto que o CNPJ da empresa beneficiária do crédito está vinculado ao Convênio de Resgate Centralizado junto a esta Instituição Financeira. \n"
                            + "\n"
                            + "Esclarecemos que o convênio é um contrato firmado com o Banco do Brasil através do qual, todos os valores oriundos de resgates de Depósitos Judiciais são creditados em conta bancária previamente cadastrada. Desta forma, não é possível realizar as transferências para a conta indicada por V. Excelência.\n"
                            + "\n"
                            + "Diante do exposto, solicitamos autorização desse R. Juízo para efetuar a transferência dos valores para conta bancária vinculada ao convênio supracitado.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;      
                case "TRABALHISTA - CPF CNPJ PIS":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer o dado ainda faltante, conforme abaixo:\n"
                            + "\n"
                            + "- 	Contribuinte			Número de XXXXXXX.\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;        
                 case "TRABALHISTA - CPF CNPJ NÃO PERTENCE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir as determinações desse Juízo solicitamos, por especial obséquio, fornecer os dados, conforme abaixo:\n"
                            + "\n"
                            + "-	Contribuinte	Número de CPF, visto que o número informado, XXXXXXXXXXX, não pertence à XXXXXXXXXXXX, segundo a base de dados da Receita Federal.\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos \n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - CPF DIVERGENTE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer o dado, conforme abaixo:\n"
                            + "\n"
                            + "-	Beneficiário	Número de CPF, visto que o número informado, XXXXXXXXXXXXXXX, não pertence a XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX segundo a base de dados da Receita Federal.\n" 
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "TRABALHISTA - DADOS IMPOSTO DE RENDA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer os dados ainda faltantes, conforme abaixo:\n"
                            + "\n"
                            + "–	Valor do Total dos Rendimentos Tributáveis (Caso este valor seja referente a acordo realizado entre as partes, sem depósito judicial, este deverá ser informado como valor dos rendimentos tributáveis)  \n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                case "TRABALHISTA – DATA DE DEPÓSITO DIVERGENTE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer os dados ainda faltantes, conforme abaixo:\n"
                            + "\n"
                            + "–	Cópia dos avisos de crédito e/ou das guias de depósitos autenticadas (em razão da divergências de datas).\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                  case "TRABALHISTA - DESCONSIDERAMOS OFÍCIO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que desconsideramos o contido no ofício SPREC nº XXX/XXXX de XX/XX/XXXX, e que aguardamos novas determinações desse Juízo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                case "TRABALHISTA - DESTINATÁRIO CEF":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Informamos que desconsideramos o contido no ofício nº 1083/2015 de 10/12/2015, tendo em vista que seu destinatário é o Banco Caixa Econômica Federal.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "TRABALHISTA – DEVOLUÇÃO DE OFICIO ENCAMINHADO A CEF":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, esclarecemos a V.Exa. que em consulta ao PAB, o mesmo informa que não houve registro de entrada do alvará XX/XXXX no Posto de Atendimento Bancário.\n"
                            + "\n"
                            + "Contudo, notamos que a cópia do alvará enviada no anexo do ofício XXX/XXXX (folha XXX) determina que seu cumprimento seja efetuado pela Caixa Econômica Federal. Sugerimos a Vossa Excelência que oficie o Banco Caixa Econômica Federal para que informe se o ofício foi recebido por eles.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                case "TRABALHISTA - DOCUMENTO DE TRANSF. INTERBANCARIA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que, para que possamos dar continuidade em nossas pesquisas, é necessário que esse Juízo oficie o Banco XXXXXXXXXXXX, determinando que o mesmo forneça a cópia do Documento de Transferência Interbancária, no valor mencionado no ofício supra, tendo em vista não termos localizado o referido crédito. \n"
                            + "\n"
                            + "Esclarecemos que o documento anexo ao ofício supra se refere à informação do Banco Central de que encaminhou a determinação para que a transferência de valor seja efetuada. Porém, não comprova que a mesma foi cumprida.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;           
                case "TRABALHISTA - DUPLICIDADE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que em XX/XX/XXXX foi efetuado o levantamento do ofício nºXXXX de XX/XX/XXXX no valor de R$ X.XXX,XX (R$ X.XXX,XX + correções), conforme comprovante anexo.\n"
                            + "\n"
                            + "2.	Ocorreu que o Banco do Brasil recebeu dois originais do ofício supracitado e, em virtude desse recebimento, efetuou um novo levantamento em XX/XX/XXXX, no valor de R$ X.XXX,38 (R$ X.XXX,XX + correções) conforme cópias anexas, gerando uma duplicidade.\n"
                            + "\n"
                            + "3.	Esclarecemos que, uma vez efetuados os levantamentos a título de recolhimentos ao INSS, não podemos cancelá-los, visto que não temos alçada para realizar tal operação.\n"
                            + "\n"
                            + "4.	Informamos ainda, que se encontra a disposição, na agência 5905-6 Poder Judiciário (SP), o saldo remanescente no valor de R$ X.XXX,XX (R$ XX.XXX,XX – valor original), depositado nos autos do processo supra, com direito a rendimentos desde XX/XX/XXXX, na conta de depósito judicial nº XXXXXXXXXXXXX.\n"
                            + "\n"
                            + "5.	Diante do exposto e visto que o saldo não comporta a transferência solicitada no ofício supra, a título de recolhimento de IR, no valor de R$ XX.XXX,XX, solicitamos por especial obséquio, o auxílio desse MM. Juízo, no sentido de oficiar o INSS, para que proceda à devolução do valor creditado em duplicidade, quer seja, R$ X.XXXX,XX, para que se possa restituir a conta judicial nº XXXXXXXXX à disposição dos autos do processo em epígrafe.\n"
                            + "\n"
                            + "6.	Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;       
                 case "TRABALHISTA - ESCLARECIMENTO DEPOSITO RECURSAL":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que, em pesquisa efetuada em nossos assentamentos, não foram localizadas contas judiciais vinculadas ao processo supra.\n"
                            + "\n"
                            + "Esclarecemos que os depósitos recursais são geridos somente pela Caixa Econômica Federal, desde abril/1992, quando todas as Instituições Financeiras transferiram os saldos existentes àquela instituição. Atualmente a rede bancária é apenas o órgão arrecadador do recurso, para repasse à Caixa.\n"
                            + "\n"
                            + "Assim, os esclarecimentos relativos a Depósitos Recursais deverão ser direcionados somente a Caixa Econômica Federal, independentemente do banco arrecadador.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;      
                case "TRABALHISTA - ESTORNO SEM ALÇADA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que foi realizado o levantamento dos valores a título de recolhimento XXXXXXXXXXXXXX, na data de XX/XX/XXXX, conforme comprovante anexo.\n"
                            + "\n"
                            + "Esclarecemos que, uma vez efetivado o recolhimento, não podemos cancelar a transferência, visto que não temos alçada para realizar sua restituição.\n"
                            + "\n"
                            + "Diante do exposto, solicitamos por especial obséquio, que esse MM. Juízo oficie a XXXXXXXXXXXXXXX, determinando a devolução do valor, para que possamos recompor a conta judicial nº XXXXXXXXXXXXXXXXX, pertencente aos autos do processo em epígrafe.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;          
                case "TRABALHISTA - EXTRATO ANALÍTICO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, no qual, encaminhamos anexo o extrato analítico, emitido em XX/XX/XXXX, da(s) movimentação(ões) na(s) conta(s) judicial(is) pertencente aos autos do processo em epígrafe.\n"
                            + "\n"
                            + "Em caso de utilização das informações aqui prestadas, para emissão de alvará ou ofício, solicitamos, por especial obséquio, anexar cópia deste extrato ao documento emitido.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                 case "TRABALHISTA - EXTRATO UNIFICADO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX  encaminhamos anexo extrato unificado emitido em XX/XX/XXXX, com os depósitos judiciais que estão à disposição desse Juízo na agência 5905-6 Poder Judiciário, nos autos do processo em epígrafe.\n"
                            + "\n"
                            + "Em caso de utilização das informações aqui prestadas, para emissão de alvará ou ofício, solicitamos, por especial obséquio, anexar cópia deste extrato ao documento emitido.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                 case "TRABALHISTA – FGTS – DADOS FALTANTES":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer os dados ainda faltantes, conforme abaixo:\n"
                            + "\n"
                            + "Reclamada\n"
                            + "\n"
                            + "- Número do CNPJ\n"
                            + "- Endereço\n"
                            + "\n"
                            + "Reclamante:\n"
                            + "\n"
                            + "- Número do PIS\n"
                            + "- Número da Carteira de Trabalho e Número de Série\n"
                            + "- Data de Admissão\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                 case "TRABALHISTA - GERENCIADOR FINANCEIRO":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V.Exa. que não cumprimos com o determinado, em razão de que, o ofício  não está com a assinatura do(a) MM. Sr.(a) Juíz(a). \n"
                            + "\n"
                            + "Esclarecemos ainda que a consulta a Saldo e extratos esta disponivel aos Magistrados e Serventuários da Justiça autorizados, através do Portal BB ou Via Auto atendimento setor público (AASP), com o devido cadastramento e assinatura do termo de Adesão junto à agência centralizadora da Comarca, 3832 Setor Publico Salvador.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;      
                case "TRABALHISTA - INSS 1708 2909":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + " Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, solicitamos a gentileza de retificar ou ratificar o código informado para recolhimento do INSS, bem como fornecer o dado necessário ao cumprimento de sua determinação:\n"
                            + "\n"
                            + " CÓDIGO PARA RECOLHIMENTO INSS	DADO A SER FORNECIDO\n"
                            +"       2909	                                            CNPJ do Réu/Contribuinte\n" 
                            +"       1708	                                            PIS/Pasep ou NIT\n" 
                            +"       2801	                                            CEI da Empresa \n"
                            + "\n" 
                            + "Lembramos que cada código de recolhimento exige o dado correspondente, conforme tabela acima. Em caso de dúvida, poderá ser consultado tabela informativa no site da Receita Federal.\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para eventuais esclarecimentos ou informações necessárias.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                 case "TRABALHISTA - INSS VIA GRU":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº 139/2017 de 10/03/2017, protocolado em 13/03/2017, informamos a impossibilidade de efetuar o levantamento, a título de INSS, com o CNPJ do próprio Instituto.  Esclarecemos que tal levantamento deve ser feito através de GRU.\n"
                            + "\n"
                            + "Para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer os dados ainda faltantes, conforme abaixo:\n"
                            + "\n"
                            + "– Encaminhar GRU preenchida.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                    case "TRABALHISTA – IR SOLICITAÇÃO DE QUANTIDADE DE MESES":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer o dado ainda faltante, conforme abaixo:\n"
                            + "\n"
                            + "- Quantidade de meses a que se referem os rendimentos\n"
                            + "\n"
                            + "Solicitamos a gentileza de anexar à sua resposta, uma cópia do ofício supra e anexos.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;       
                    
                case "TRABALHISTA - JÁ PETICIONADO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que a resposta referente ao ofício XXXX/XXXX encontra-se peticionada via SISDOC através do protocolo XXXXXXXX desde XX/XX/XXXX.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;       
                case "TRABALHISTA - OFICIAR BENEFICIÁRIO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que em XX/XX/XXXX foi efetuado o levantamento do alvará nº XX/XXXX de XX/XX/XXXX, e o crédito foi direcionado para XXXXXXXXXXXXXXXXXXXXXXX, CNPJ XX.XXX.XXXX/XXXX-XX, conforme comprovante de fls. XXX.\n"
                            + "\n"
                            + "2.	Ocorreu que, em virtude de exacerbado volume de levantamentos diários a serem efetuados, e por um lapso, não se atentou às solicitações de devolução do alvará supra, efetuadas por esse Juízo, e houve o levantamento do mesmo.\n"
                            + "\n"
                            + "3.	Entretanto, ressaltamos que o Banco do Brasil não possui alçada para efetuar o estorno ou o cancelamento desta transferência, uma vez efetivada.\n"
                            + "\n"
                            + "4.	Diante do exposto, solicitamos por especial obséquio, o auxílio desse MM. Juízo, no sentido de oficiar o beneficiário, XXXXXXXXXXXXXXX, CNPJ XX.XXXX.XXX/XXXX-XX, para que proceda à devolução do valor indevidamente liberado, para que se possa restituir a conta judicial nº XXXXXXXXXXXX à disposição dos autos do processo em epígrafe.\n"
                            + "\n"
                            + "5.	Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "TRABALHISTA – OFICIO DISPONIVEL PARA RETIRADA NO PAB":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V.Exa. que o alvará de número XXXX/XXXX está a disposição da XXª Vara de Trabalho de São Paulo Capital podendo ser retirado em nosso Posto de Atendimento Bancário (PAB).\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                case "TRABALHISTA - ORIGEM DO AVISO DE CRÉDITO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº XXXX/XXXX de XX/XX/XXXX, protocolado em XX/XX/XXXX, esclarecemos que o aviso de crédito anexo ao ofício supra de fls. XXX, refere-se à transferência oriunda do processo nº XXXXXXXXXXXXX da XXª Vara do Trabalho de São Paulo/SP, colocando o valor informado à disposição desse Juízo, nos autos do processo em epígrafe.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                case "TRABALHISTA - RECOMPOSIÇÃO DA CONTA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que procedemos à recomposição da conta judicial pertencente a esse Juízo, e que o valor abaixo discriminado encontra-se à disposição, na agência 5905-6 Poder Judiciário/SP, nos autos do processo em epígrafe:\n"
                            + "\n"
                            + "-	Conta Judicial	: XXXXXXXXXXXXX\n"
                            + "-	Valor                      : R$ XXXXXX \n"
                            + "-	Data da Aplicação	: XXXXXXXXX\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                 case "TRABALHISTA - RECOMPOSIÇÃO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Encaminhamos anexo o extrato analítico, emitido em XX/XX/XXXX, das movimentações na conta judicial nº XXXXXXXXXXXXXX pertencente aos autos do processo em epígrafe.\n"
                            + "\n"
                            + "2.	Informamos que em XX/XX/XXXX foi efetuado o levantamento do alvará PJe-JT de XX/XX/XXXX, conforme comprovante anexo.\n"
                            + "\n"
                            + "3.	Ocorreu que, em virtude de exacerbado volume de levantamentos diários a serem efetuados, e por um lapso, não se atentou e houve o levantamento do alvará supra no valor total da conta judicial, R$ XX.XXX,XX (R$ XX.XXX,XX + correções), sendo que o correto seria de R$ XX.XXX,XX (R$ XX.XXX,XX + correções), e em virtude desse recebimento, não se atentou e o levantamento foi efetuado a maior, no valor de R$ X.XXX,XX, conforme cópias anexas.\n"
                            + "\n"
                            + "4.	Esclarecemos que, uma vez efetuado o levantamento a título de crédito em conta, não podemos cancelá-lo, visto que não temos alçada para realizar tal operação.\n"
                            + "\n"
                            + "5.	Diante do exposto, solicitamos por especial obséquio, o auxílio desse MM. Juízo, no sentido de oficiar a beneficiária XXXXXXXXXXXXXXXXXXX, CPF XXX.XXX.XXX-XX, para que proceda à devolução do valor creditado à maior indevidamente, quer seja, R$ X.XXX,XX (70% do valor), para que se possa restituir a conta judicial nº XXXXXXXXXXX à disposição dos autos do processo em epígrafe.\n"
                            + "\n"
                            + "-	Conta Judicial	: XXXXXXXXXXXXX (parcela 02)\n"
                            + "-	Valor			: R$ X.XXX,XX\n"
                            + "-	Data da Aplicação	: XX/XX/XXXX\n"
                            + "\n"
                            + "6.	Em caso de utilização das informações, aqui prestadas, para emissão de Alvará(s) e/ou Ofício(s), favor anexar a cópia deste e do extrato ao documento emitido.\n"
                            + "\n"
                            + "7. 	Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                case "TRABALHISTA - RETIFICAÇÃO EFETUADA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício PJe-JT de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que efetuamos a retificação solicitada por esse Juízo. \n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                 case "TRABALHISTA - RODAPÉ - OFÍCIO PADRÃO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício PJe-JT de xx/xx/xxxx, informamos que a transferência solicitada por esse Juízo não foi efetuada.\n"
                            + "\n"
                            + "Esclarecemos que transferências de valores devem ser solicitadas através de ofício padrão individualizado para cada recolhimento, conforme modelo anexo no Ato GP/CR 01/2014, especificando os códigos de recolhimento, contas judiciais, CPF/CNPJ das partes, datas de depósito e valores (com juros/correção).\n"
                            + "\n"
                            + "Informamos ainda, que para que possamos cumprir a determinação desse Juízo, solicitamos, por especial obséquio, fornecer o dado ainda faltante, conforme abaixo:\n"
                            + "\n"
                            + "–	Valor a ser transferido.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                    
                case "TRABALHISTA - SALDO DISPONÍVEL":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado  em XX/XX/XXXX, informamos que o valor abaixo discriminado encontra-se à disposição desse Juízo, na agência 5905-6 Poder Judiciário/SP, nos autos do processo em epígrafe, conforme segue:\n"
                            + "\n"
                            + "–	Conta Judicial		 : xxxxxxxxxxxxx\n"
                            + "–	Valor			: xxxxxxxxxxxxx\n"
                            + "–	Data da Aplicação	         : xxxxxxxxxxxxxx\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - SALDO INSUFICIENTE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que se encontra à disposição, na agência 5905-6 Poder Judiciário (SP), o saldo remanescente no valor de R$ XX.XXX,XX, (valor original R$ XX.XXX,XX) depositado nos autos do processo supra, com direito a rendimentos desde XX/XX/XXXX, na conta de depósito judicial nº XXXXXXXXXX.\n"
                            + "\n"
                            + "Diante do exposto e visto que o saldo não comporta a transferência solicitada no ofício supra, aguardamos novas determinações desse Juízo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - SALDO ZERADO":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que a conta de depósito judicial nº XXXXXXXXXXXXX, nos valores originais de R$ XXXXXXXXXX  referente ao processo supra, encontra-se sem saldo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                case "TRABALHISTA – SALDO ZERADO DE PARCELA SOLICITADA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que a parcela nº X da conta de depósito judicial nº XXXXXXXXXXXXX, nos valores originais de R$ XXXXXX, referente ao processo supra, encontra-se sem saldo na data de XX/XX/XXXX.\n"
                            + "\n"
                            + "Diante do exposto e visto que o saldo não comporta a transferência solicitada no ofício supra, aguardamos novas determinações desse Juízo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;     
                case "TRABALHISTA - SALDO ZERADO OFÍCIO PAGO ANTERIORMENTE":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao alvará PJe-JT de xx/xx/xxxx, informamos que a parcela nº xx da conta de depósito judicial nº xxxxxxxxxxxxxx, indicada em vosso ofício, encontra-se sem saldo.\n"
                            + "\n"
                            + "Esclarecemos, que foi efetuado um levantamento a título de crédito em conta no valor de R$ xxxxxxxx (R$ xxxxx + correções) em cumprimento à determinação desse Juízo contida no alvará PJe-JT nº xxxxxxxx, conforme comprovante anexo.\n"
                            + "\n"
                            + "Diante do exposto, aguardamos novas determinações desse Juízo.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - SALVADOR BA - NÃO PAGOU I.R.":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos à  V. Exa. que realizamos a(s) transferência(s) conforme o determinado. Segue(m) comprovante(s) anexo(s).\n"
                            + "\n"
                            + "Esclarecemos ainda a impossibilidade de efetuar o crédito do valor de R$ xxxxxxx referente ao I.R - RRA em favor do credor uma vez que não foi informado os dados de agência e conta necessários para a realização da operação.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;
                    
                case "TRABALHISTA - SISCONDJ":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra, informamos que, em caso de eventuais problemas em relação ao SISCONDJ, é necessário abrir uma ocorrência junto ao suporte técnico, informando as contas não disponíveis ou não localizadas no sistema.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                case "TRABALHISTA - SOLICITAÇÃO DE P.I.S. F.G.T.S.":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº XXXX/XXXX de XX/XX/XXXX, protocolado em XX/XX/XXXX, para que possamos cumprir a determinação desse Juízo, a título de recolhimento ao FGTS, solicitamos, por especial obséquio, fornecer o dado, conforme abaixo:\n"
                            + "\n"
                            + "-	Contribuinte			 Número de PIS.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                    
                case "TRABALHISTA - TED DEVOLVIDA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício supra de XX/XX/XXXX, protocolado em XX/XX/XXXX, informamos que a TED, enviada ao Banco XXXXXXXX em XX/XX/XXXX, no valor de R$ XXXXXX (R$ XXXXX + correções), foi devolvida pela alínea XX – XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.\n"
                            + "\n"
                            + "Informamos ainda, que o valor foi reaplicado e que se encontra à disposição desse Juízo, na agência 5905-6 Poder Judiciário (SP), nos autos do processo em epígrafe:\n"
                            + "\n"
                            + "–	Conta Judicial		: XXXXXXXXX\n"
                            + "–	Valor			: R$XXXXXXX\n"
                            + "–	Data da Aplicação	: XX/XX/XXXX\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;    
                 case "TRABALHISTA - TRANSFERÊNCIA REALIZADA":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº 2143/2015 de 11/12/2015, protocolado em 15/12/2015, informamos que o(s) levantamento(s) foi(ram) efetuado(s), em cumprimento à determinação desse Juízo, conforme comprovante(s) anexo(s).\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;         
                case "TRABALHISTA - TRÂNSITO INDEVIDO CEF":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Informamos que desconsideramos o contido no ofício supra de XX/XX/XXXX, tendo em vista que o destinatário do ofício supra é o Banco Caixa Econômica Federal.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos e informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;  
                case "TRABALHISTA - UNIFICAÇÃO DE CONTAS":
                    txtCorpo.setText("Excelentíssimo(a) Senhor(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício nº xxxxxxxx de xx/xx/xxxx, informamos que foram resgatados os depósitos judiciais, relacionados no extrato anexo e, com as devidas correções, foram unificados em novo depósito judicial nº xxxxxxxxxxxxxxxxx, no valor de R$ xxxxxxxxx com direito a rendimentos desde xx/xx/xxxx.\n"
                            + "\n"
                            + "Colocamo-nos à disposição de Vossa Excelência para os eventuais esclarecimentos ou informações porventura necessários.\n"
                            + "\n"
                            + "\n"
                            + "Respeitosamente,");
                    break;      
                case "TRABALHISTA - WEBSERVICE":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao ofício referenciado, informamos a V. Exa. que não foi possível cumprir com a vossa determinação enviada via canal WEBSERVICE à esta instituição financeira, haja vista que aquele canal é exclusivo para o envio de intimações e citações onde o Banco do Brasil é uma das partes.\n"
                            + "\n"
                            + "Os ofícios de DJO encaminhados naquele canal não são passíveis de atendimento devido à impossibilidade de conferência da assinatura do(a) MM. Sr.(a) Juíz(a), impossibilitando ainda confirmar a veracidade do referido documento, além de não permitir respondermos as demandas eletronicamente.\n"
                            + "\n"
                            + "Tendo em vista o comprometimento desta Instituição Financeira em atender aos Magistrados mas também, em mitigar riscos das operações envolvendo valores à ordem deste R. Juízo, solicitamos respeitosamente que nos seja enviado um novo ofício no formato documento físico, com a assinatura de V.Exa. ou assinatura eletrônica.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;          
                    
                case "DJO - 1113 NSCGJ - MODELO":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que não foi possível cumprir vossa R. Determinação Judicial, pois não consta em V. Ofício, o valor a ser transferido em moeda nacional, como é determinado no § 1º do Art. 1.113 das Normas de Serviço da Corregedoria Geral da Justiça.\n"
                            + "\n"
                            + "Outrossim, informamos que o saldo atualizado da conta judicial até a presente data é R$ 119.946,61.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;        
                case "DJO - 2016 Conta Encerrada por MLJ":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V.Exa que a conta judicial …  foi encerrada em 00/00/2015, por meio do Mandado de Levantamento Judicial nº .../2015.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                case "DJO - 2016 Já Cumprido":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V.Sa que já cumprimos com o determinado, conforme nossa resposta nº … /2016 (cópia anexa).\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;    
                    
                case "DJO - Abertura de conta":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, vinculado aos autos em epígrafe, solicitamos que nos informe qual o tipo de conta deverá ser aberta.\n"
                            + "\n"
                            + "Para a abertura de uma conta judicial – Justiça Estadual, nesta Instituição, se faz necessário acessar o site do Portal de Custas do TJ - SP, https://portaldecustas.tjsp.jus.br/portaltjsp/login.jsp .\n"
                            + "\n"
                            + "Após o cadastramento dos dados necessários, será gerado o número do ID (Identificador de Depósito). O nº da conta judicial será gerado pelo sistema, a partir da efetivação da transferência.\n"
                            + "\n"
                            + "Já uma conta corrente, pela resolução 2025 do Banco Central do Brasil, há obrigatoriedade de apresentação da documentação completa: CPF, RG e comprovante de residência (conta de água, luz ou telefone), cópia e original, bem como a presença do(a) beneficiário(a), ou do representante do(a) menor, no ato da abertura da conta na agência escolhida, para assinar a ficha de abertura e cartão de assinatura.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break; 
                  case "DJO - Anexo não acompanhou":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, vinculado aos autos em epígrafe, informamos que o(s) demonstrativo(s) mencionado(s) não acompanharam o V. ofício.\n"
                            + "\n"
                            + "Diante do exposto, solicitamos que nos envie a referida peça para que possamos pesquisar mais detalhadamente e cumprir com o determinado.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");   
                    break;
                case "DJO - Autorização Conta Outra Vara":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V.Exa. que a(s) conta(s) judicial(is) nº XXXXXXXXX, encontra(m)-se à disposição do E. Juízo da XXXXXXXXXXXXXX.  Portanto, qualquer movimentação na(s) referida(s) conta(s) judicial(is), somente poderá ser feita mediante autorização daquele(s) E. Juízo(s).\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                case "DJO - BACENJUD Não Localizado":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a esse R. Juízo que não logramos localizar a conta judicial referente ao valor de R$ XXXX,XX, ID XXXXXXX, em nome das partes e processo acima citados, à disposição desse E. Juízo.\n"
                            + "\n"
                            + "Diante do exposto, solicitamos a V. Exa. Oficiar o Banco onde foi efetuado o bloqueio, para maiores esclarecimentos quanto à transferência do valor supra, e, se possível nos enviar a cópia da respectiva transferência (TED), com sua respectiva autenticação mecânica, bem como necessitamos o nº do CPF das partes, para uma pesquisa mais detalhada.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;    
                    
                case "DJO - Cadastro Alterado":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V. Exa. que, procedemos com a alteração do cadastro da(s) conta(s) judicial(is) nº XXXXXXXXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break; 
                case "DJO - Cancelamento de Precatório RPV":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, vinculado aos autos em epígrafe, informamos que conforme determina a Lei 13.463/2017, em seu Art. 2º, os Precatórios e RPVs (Requisições de Pequeno Valor) federais expedidos e cujos valores não tenham sido levantados há mais de dois anos, deverão ser cancelados e terão seus valores devolvidos para a União, independente da exigência ou não de alvará para resgate. E uma vez efetivado o recolhimento em favor da União, não temos alçada para solicitar a devolução dos valores.\n"
                            + "\n"
                            + "Diante do exposto, por especial obséquio, oficie à União, determinando a devolução dos valores aos autos do processo em epígrafe, para posterior crédito na conta judicial n° XXXXXX, junto ao Banco do Brasil.\n"
                            + "\n"
                            + "Segue comprovante anexo da devolução dos valores.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");   
                    break; 
                case "DJO - Comprovante de depósito":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, informamos que o valor abaixo discriminado encontra-se à disposição desse Juízo, na agência 5905-6 Poder Judiciário/SP, nos autos do processo em epígrafe, conforme segue:\n"
                            + "\n"
                            + "–	Conta Judicial            : xxxxxxxxxxxxx\n"
                            + "–	Valor                          : xxxxxxxxxxxxx\n"
                            + "–	Data da Aplicação      : xxxxxxxxxxxxx\n"
                            + "\n"
                            + "Outrossim, informamos que conforme Comunicado Conjunto nº 474/2017:\n"
                            + "\n"
                            + "\"4. A partir de 01/03/2017 em todas as Unidades Judicias do Estado os depósitos judiciais deverão ser efetuados através do módulo “Depósitos Judiciais”, sejam depósitos novos ou em continuação.\n"
                            + "4.1 O acesso ao módulo “Depósitos Judiciais” será disponibilizado a todas as Unidades Judicias do Estado, inclusive com possibilidade de verificação de saldo de depósitos efetuados a partir de 01/03/2017.\n"
                            + "4.2 O Banco do Brasil não mais enviará comprovantes dos novos depósitos em formato físico. Caberá a cada Unidade Judicial o gerenciamento dos depósitos no módulo respectivo, inclusive aquelas Unidades que não integram o Projeto Piloto.\"\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais  anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");   
                    break;     
                case "DJO - DEPRE GRU":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em cumprimento ao epigrafado, informamos à V. Exa. que transferimos em xx/xx/xxxx, o(s) valor(es) de R$ xxxx,xx para o Tribunal Regional Federal da 3ª Região, através da Guia de Recolhimento da União, código nº xxxxx-x, conforme comprovante(s) em anexo.\n"
                            + "\n"
                            + "O(s) valor(es) acima corresponde(m) aos solicitados em Vosso oficio, acrescidos de juros e correção monetária a partir  das datas mencionadas, tendo sido sacado(s) da conta judicial nº XXXXXXXXXXX.        \n"
                            + "\n"
                            + "No ensejo, renovamos nossos protestos de elevada estima e apreço.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;    
                case "DJO - DEPRE - Informação Execução Fiscal":
                    txtCorpo.setText("Meritíssimo(a) Desembargador(a),\n"
                            + "\n"
                            + "\n"
                            + "Em cumprimento ao epigrafado, informamos a V. Exa. que, determinado pelo Setor de Execuções Contra a Fazenda Pública – Foro da Fazenda Pública/Acidentes, transferimos os valores relacionados abaixo, juntamente com a respectiva conta de crédito e parcela criada para cada depósito.\n"
                            + "\n"
                            + "Valor Depositado 	Conta Creditada	 Nº da Parcela Criada\n"
                            + "R$ XX,XX                           XXXXXXXXXXXX             XXXX\n"
                            + "\n" 
                            + "\n"
                            + "Respeitosamente,");
                    break; 
                case "DJO - DEPRE PGR - Transferência":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em cumprimento ao epigrafado, informamos a V. Exa. que transferimos em xx/xx/xxxx, o montante de R$ xxxxxxx,xx para a conta judicial nº XXXXXXXXXX, parcela XX, cadastrada em nome de TRIBUNAL REGIONAL DO TRABALHO x PREFEITURA MUNICIPAL DE XXXXXXXX, vinculada ao processo nº XXXXXXXXXXXX à disposição do CAMPINAS EC 62 – COMARCA DE EC 62/2009 – PRECATORIOS.\n"
                            + "\n"
                            + "O valor acima corresponde ao solicitado em V.Ofício, acrescidos de juros e correção monetária desde as datas informadas, tendo sido sacado da conta judicial nº XXXXXXXXXXX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "No ensejo, renovamos nossos protestos de elevada estima e apreço.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break; 
                case "DJO - DEPRE PGR - Transferência - Lista Parcelas":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em cumprimento ao epigrafado, informamos a V. Exa. que transferimos em XX/XX/XXXX, os montantes abaixo relacionados, para a conta judicial nº XXXXXXXXXXX, cadastrada em nome de  TXXX - ORDEM CRONOLÓGICA  x PREFEITURA MUNICÍPAL DE  GUARUJÁ,  vinculada ao processo  XXXXXXXXXX, à disposição do  TRIB XXXXX - COMARCA XXXXXX \n"
                            + "\n"
                            + "R$ XX.XXX,XX, parcela XX\n"
                            + "R$   X.XXXX,XX, parcela XX\n"
                            + "\n"
                            + "Os valores acima correspondem ao solicitado em V. Ofício, acrescido de juros e correção monetária desde a data do depósito, tendo sido sacados da conta judicial nº XXXXXXXXXXX.\n"
                            + "\n"
                            + "No ensejo, renovamos nossos protestos de elevada estima e apreço.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;         
                       
                case "DJO - Disponível":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, informamos a esse R. Juízo que a ordem judicial em referência foi cumprida.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                 case "DJO - Esclarecimentos":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V. Exa. que seguem, em anexo, extratos pormenorizados das contas judiciais nº XXXXXXXXX, bem como, enviamos cópias dos respectivos MLJ, conforme solicitado.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;   
                case "DJO - Modelo Unificação Com observações":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao acima epigrafado, informamos a V. Exa. que foram resgatados os depósitos judiciais cadastrados nestes autos em nome das referidas partes e, com as devidas correções, foram unificados na conta judicial nº 000000000000, Parcela 01, no valor total de R$ 000000,00 com direito a rendimentos desde de XX.XX.20XX. Informamos, no entanto, que:\n"
                            + "\n"
                            + "•\n"
                            + "•\n"
                            + "•\n"
                            + "\n"
                            + "Salientamos que deverá constar o número deste, se o mesmo servir para emissão de Mandado de Levantamento Judicial.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;   
                case "DJO - Modelo Unificação CONTAS NÃO LOCALIZADAS":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao acima epigrafado, informamos a V. Exa. que não foi possível cumprir vossa determinação de unificação, pois não logramos localizar nenhuma conta judicial ainda com saldo à disposição desse R. Juízo, em nome das referidas partes. Desta forma, solicitamos cópia legível da(s) guia(s) de depósito judicial, para que possamos pesquisar mais detalhadamente e atender Vosso ofício.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;   
                case "DJO - Modelo Unificação Simples":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao acima epigrafado, informamos a V. Exa. que foram resgatados os depósitos judiciais cadastrados nestes autos em nome das referidas partes e, com as devidas correções, foram unificados na conta judicial nº 000000000000, Parcela 01, no valor total de R$ 000000,00 com direito a rendimentos desde de XX.XX.20XX \n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;   
                case "DJO - Modelo Unificação SOMENTE UMA CONTA":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atenção ao acima epigrafado, informamos a V. Exa. que não foi possível cumprir vossa R. Determinação de unificação, pois logramos localizar somente um depósito judicial ainda com saldo, qual seja, XXXXXXXXXXX, com saldo atualizado até a presente data de R$ 0000,00. No caso de haver mais contas judiciais a serem unificadas, solicitamos cópia legível da(s) guia(s) de depósito judicial, para que possamos atender Vosso ofício.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisi-tados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;       
                case "DJO - Não Cumprido - Assinatura não Juiz":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a esse R. Juízo que não foi possível cumprir com o determinado, em razão de que, o ofício de transferência não está com a assinatura do(a) MM. Sr.(a) Juíz(a) legível, onde é obrigatória a referida assinatura, em consonância com o PROVIMENTO Nº 24, de 29/09/2003, da E. Corregedoria Geral de Justiça.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                 case "DJO - Não Cumprido - Emitir MLJ":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, solicitamos respeitosamente a esse R. Juízo a emissão de Mandado de Levantamento Judicial para cumprimento por parte desta Instituição Financeira na condição de Banco depositário, seguindo o disposto no comunicado da E. Corregedoria Geral da Justiça nº 501/2016, e por força do artigo 1.112 das NSCGJs – Normas de Serviço da Corregedoria Geral da Justiça.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                     
                 case "DJO - Não Cumprido - Saldo Insuficiente":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a esse R. Juízo que não foi possível cumprir com o determinado no Ofício supra, em razão de não haver saldo suficiente na conta judicial nº XXXXXXXX, a qual possui saldo atualizado até a presente data de R$ 00,00.\n"
                            + "\n"
                            + "Salientamos ainda, que deverá constar o número deste ofício caso o mesmo seja utilizado para emissão de Mandado de Levantamento Judicial.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;   

                 case "DJO - Não Cumprido - Tributos":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, informamos não ser possível cumprir vossa R. Ordem Judicial, pois não identificamos os dados que esta Instituição Financeira deva fazer uso no que tange ao recolhimento de valores direcionado ao(a) Órgão/Autarquia mencionado(a).\n"
                            + "\n"
                            + "Tendo em vista o comprometimento desta Instituição Financeira de mitigar riscos das operações envolvendo valores à ordem deste R. Juízo, solicitamos respeitosamente que seja enviado novo ofício com o respectivo documento de arrecadação do tributo preenchido ou que em seu teor apresente todas as informações necessárias ao respectivo recolhimento, solicitado de forma expressa, a saber: \n"
                            + "\n"
                            + "1) Documento de arrecadação da ser utilizado (DARF, GPS, GRU,etc.)	\n"
                            + "1.1) Na hipótese de ser utilizada GRU (Guia de Recolhimento da União): \n"
                            + "- Nº da Unidade Gestora de Arrecadação / Código de recolhimento\n"
                            + "- Nº de referência / Competência (MM/AAAA)\n"
                            + "- Nome/Razão Social/Órgão e CPF/CNPJ do contribuinte\n"
                            + "- Valor do Recolhimento\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;             
                    
                case "DJO - Não Localizada":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício expedido nos autos do processo em epígrafe, informamos respeitosamente a V. Exa. que não logramos localizar conta(s) judicial(is) vinculada(s) ao processo supra, a partir das informações fornecidas em vosso ofício.\n"
                            + "\n"
                            + "Diante do exposto, para subsidiar novas pesquisas nos sistemas desta Instituição Financeira, solicitamos que nos envie o nº da conta judicial e/ou a(s) cópia(s) da(s) guia(s) de depósito(s) judicial(is) ou em caso de ter sido enviada por TED (Transferência Eletrônica Disponível) necessitamos o número do ID – Identificador de Depósito, e se possível a cópia da respectiva transferência – TED com a autenticação mecânica.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                case "DJO - Ordem de Resgate com Isenção de Tarifa TED":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício em referência, informamos respeitosamente a esse R. Juízo que, ao utilizar o Sistema Interbancário com a finalidade de envio de valores para contas em outras Instituições Financeiras, há custo sobre a utilização do mesmo por parte do Banco do Brasil, que é a tarifa cobrada, não considerando a perspectiva processual da natureza dos valores.\n"
                            + "\n"
                            + "Diante do exposto, encontra-se impossibilitado o cumprimento integral de vossa R. Determinação Judicial. Como alternativa, o Banco pode disponibilizar tais valores para levantamento em espécie diretamente em uma agência, sem a cobrança de qualquer tarifa sobre tal procedimento, alternativa esta que se apresenta como única forma de levantamento nos casos envolvendo valores abaixo da tarifa vigente de transferência.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;    
                case "DJO - Origem Destino":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que, para cumprirmos com a transferência determinada, faz-se necessário o envio dos dados abaixo indicados: \n"
                            + "\n"
                            + "(  )  Número da Conta Judicial de onde o valor deverá ser sacado;\n"
                            + "(  )  Número da Conta Judicial de Destino;\n" 
                            + "(  )  Agência e Conta Corrente ou Poupança do beneficiário;\n"
                            + "(  )  Nome e CPF/ CNPJ do Titular;\n"
                            + "(  )  Nome do Órgão e Comarca;\n"
                            + "(  )  Código de Recolhimento da Receita;\n" 
                            + "(  )  Número do processo destino;\n"
                            + "(  )  Valor a ser apurado.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                case "DJO - Saldo 1 Conta":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que localizamos o depósito de R$ 00,00 em XX/XX/XXXX na conta judicial nº XXXXXXXXXX, cadastrada em nome de _________ X _________, processo nº XXXXXXX e à disposição do E. Juízo da Xª Vara Cível – Foro XXXXX, com saldo atualizado até a presente data de R$ 00,00.\n"
                            + "\n"
                            + "Salientamos ainda, que deverá constar o número deste ofício caso o mesmo seja utilizado para emissão de Mandado de Levantamento Judicial.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;
                case "DJO - Saldo Lista":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos a V. Exa. que foram localizadas a(s) conta(s) judicial(is), com saldos atualizados até a presente data:\n"
                            + "\n"
                            + "• Nº 000000000 - R$ 00,00\n"
                            + "• Nº 000000000 - R$ 00,00\n"
                            + "• Nº 000000000 - R$ 00,00\n"
                            + "• Nº 000000000 - R$ 00,00\n"
                            + "\n"
                            + "Salientamos ainda, que deverá constar o número deste ofício caso o mesmo seja utilizado para emissão de Mandado de Levantamento Judicial.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;    
                    
                case "DJO - Saldo Simples":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que foram localizadas a(s) conta(s) judicial(is) nº XXXXXXXXXXX, com saldo atualizado até a presente data de R$ 00,00.\n"
                            + "\n"
                            + "Salientamos ainda, que deverá constar o número deste ofício caso o mesmo seja utilizado para emissão de Mandado de Levantamento Judicial.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;      
                case "DJO - Transferência":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos à  V. Exa. que realizamos a(s) transferência(s) conforme o determinado. Segue(m) comprovante(s) anexo(s).\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;    
                 case "DJO - Transferência e Informação de Saldo Remanescente":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos à  V. Exa. que realizamos a(s) transferência(s) conforme o determinado. Segue(m) comprovante(s) anexo(s).\n"
                            + "\n"
                            + "Salientamos que a referida conta judicial apresenta saldo atualizado até a presente data de R$ 0000,00.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;  
                case "DJO - Transferência - EXCEÇÃO MLJ":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos que realizamos a(s) transferência(s) conforme o determinado. Segue(m) comprovante(s) anexo(s).\n"
                            + "\n"
                            + "Outrossim, informamos também que a(s) transferência(s) acima descrita(s) fora(m) feita(s) em caráter de exceção, pois de acordo com o Comunicado da E. Corregedoria Geral da Justiça nº 501/2016 e por força do artigo 1.112 das NSCGJs – Normas de Serviço da Corregedoria Geral da Justiça, este procedimento deve ser solicitado para cumprimento desta instituição financeira através da emissão de Mandado De Levantamento Judicial. Portanto, respeitosamente, solicitamos que as demais solicitações de levantamento determinadas por Vossa Excelência sejam encaminhadas através desse instrumento.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;     
                 case "DJO - Transferência - FGTS":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Em atendimento à requisição de Vossa Excelência, por meio do ofício expedido nos autos do processo em epígrafe, informamos à  V. Exa. que em DD/MM/AAAA, realizamos a(s) transferência(s) de R$ X,XX  para conta de FGTS na instituição financeira Caixa Econômica Federal, através da Guia de Reposição de FGTS (GRP), via STR (Sistema de Transferência de Reservas)nº  xxxxxxxxxxxx.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");
                    break;  
                case "DJO - TED devolvida":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, vinculado aos autos em epígrafe, informamos a esse R. Juízo que a transferência solicitada a favor XXXXXXXX foi efetuada em XX/XX/XXXX, porém devolvida pelo Banco XXX – XXXXXXX, em razão de \"XXXXXXXXXXXXXXXXX\". Portanto, solicitamos que nos sejam enviados os dados corretos, para que possamos cumprir com o determinado.\n"
                            + "\n"
                            + "Outrossim informamos que o valor resgatado de R$ XXXX (já descontada a tarifa interbancária) da conta judicial nº XXXXXXXXXXXXX, já foi reaplicado na mesma conta, parcela X.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");   
                    break;    
                case "DJO - Valor disponível em espécie":
                    txtCorpo.setText("Meritíssimo(a) Juiz(a),\n"
                            + "\n"
                            + "\n"
                            + "Pelo presente e em atenção ao ofício supracitado, vinculado aos autos em epígrafe, informamos a esse R. Juízo que o valor a ser restituído é inferior ao valor da tarifa interbancária. Diante do exposto, informamos que o referido valor está disponível para resgate podendo ser sacado em qualquer agência do Banco do Brasil S/A do estado de São Paulo, pelo prazo de 30 dias a partir da data do resgate efetuado em XX/XX/20XX.\n"
                            + "\n"
                            + "Declaramos que as informações constantes deste documento e de seus eventuais anexos, requisitados ao Banco do Brasil S.A., estão protegidos pela Lei Complementar Nº 105, de 10 de janeiro de 2001, que dispõe sobre o sigilo das operações e serviços prestados pelas Instituições Financeiras, cuja integridade e preservação ora transferimos para essa Autoridade.\n"
                            + "\n"
                            + "Colocamo-nos à disposição para eventuais esclarecimentos, aproveitando o ensejo para enviar protestos de elevada estima e consideração.\n"
                            + "\n"
                            + "\n"
                            + "Atenciosamente,");   
                    break;        
                  
            }
        }
    }//GEN-LAST:event_cmbRespostasItemStateChanged

    private void cmbRespostasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRespostasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRespostasActionPerformed

    private void cmbAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAutorActionPerformed

    private void btnAbrirOficioCenopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirOficioCenopActionPerformed
        // TODO add your handling code here:

        if ("".equals(txtOficio.getText())) {
            JOptionPane.showMessageDialog(txtOficio, "Favor digitar o número do Oficio Cenop");
        } else {

            String OficioCenop = txtOficio.getText();
            String OficioCenopBD = ("'" + OficioCenop + "'");

            //  Connection conexao;
            //  try {
            //      conexao = DriverManager.getConnection(stringDeConexao, usuariobd, senhabd);
            //       String sqlBuscaDados = "SELECT * FROM gerador_oficios WHERE gerador_oficios.OficioCenop=" + OficioCenopBD + "";
            //       java.sql.Statement stm = conexao.createStatement();
            try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
                // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
                String sqlBuscaDados = "SELECT * FROM " + tabelaGerador_oficio + " WHERE " + tabelaGerador_oficio + ".OFICIO_CENOP=" + OficioCenopBD + " limit 1;";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);

                    if (rs.next()) {
                        txtAOF.setText(rs.getString("AOF"));
                        txtOficio.setText(rs.getString("OFICIO_CENOP"));
                        txtProcesso.setText(rs.getString("PROCESSO"));
                        txtInquerito.setText(rs.getString("INQUERITO"));
                        txtOficioN.setText(rs.getString("OFICIO"));
                        txtAutor.setText(rs.getString("AUTOR"));
                        txtReu.setText(rs.getString("REU"));
                        txtEnvolvido.setText(rs.getString("ENVOLVIDO"));
                        txtAbc.setText(rs.getString("ABC"));
                        txtCorpo.setText(rs.getString("RESPOSTA"));
                        txtDestinatário.setText(rs.getString("TRATAMENTO"));
                        txtEndereco.setText(rs.getString("ENDERECO"));
                        txtLinhas.setText(rs.getString("LINHAS"));
                        txtCorreio.setText(rs.getString("CORREIO"));
                        txtEmail.setText(rs.getString("EMAIL"));
                         
                        if (!txtEmail.getText().equalsIgnoreCase("")){
                            jRadioButtonEmail.setSelected(true);
                        }else{
                            jRadioButtonNaoEmail.setSelected(true);
                        }
                        
                        System.out.println("IDENTIFICACAO -" + rs.getString("ID"));

                        cmbAutor.removeAllItems();
                        cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Repres. Legal         : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");
                         
                        System.out.println(rs.getString("SIGILOSO"));
                        System.out.println(rs.getString("CMBCONTEUDO"));
                        
                        cmbSigilo.removeAllItems();
                        cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");
                        

                        cmbConteudo.removeAllItems();
                        cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        //cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        cmbGerente.addItem(rs.getString("GERENTE"));
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        
                        jCheckBoxComAnexo.setSelected(false);

                    } else {
                        
                        System.out.println(rs.getString("SIGILOSO"));
                        System.out.println(rs.getString("CMBCONTEUDO"));
                        
                        txtAOF.setText("");
                        txtOficio.setText("");
                        txtProcesso.setText("");
                        txtInquerito.setText("");
                        txtOficioN.setText("");
                        txtAutor.setText("");
                        txtReu.setText("");
                        txtEnvolvido.setText("");
                        txtAbc.setText("");
                        txtCorpo.setText("OFICIO CENOP NÃO ENCONTRADO");
                        txtDestinatário.setText("");
                        txtEndereco.setText("");
                        txtLinhas.setText("0");
                        txtEmail.setText("");

                        cmbAutor.removeAllItems();
                        // cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        // cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        // cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        // cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("CONFIDENCIAL");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");

                        cmbConteudo.removeAllItems();
                        // cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        // cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        
                        jCheckBoxComAnexo.setSelected(false);
                    }

                } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_btnAbrirOficioCenopActionPerformed

    private void btnAbrirCorreioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirCorreioActionPerformed
        // TODO add your handling code here:

        if ("".equals(txtCorreio.getText())) {
            JOptionPane.showMessageDialog(txtCorreio, "Favor digitar o número do Correio");
        } else {

            String correio = txtCorreio.getText();
            String correioBD = ("'" + correio + "'");

            //  Connection conexao;
            //  try {
            //      conexao = DriverManager.getConnection(stringDeConexao, usuariobd, senhabd);
            //       String sqlBuscaDados = "SELECT * FROM gerador_oficios WHERE gerador_oficios.OficioCenop=" + OficioCenopBD + "";
            //       java.sql.Statement stm = conexao.createStatement();
            try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
                // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
                String sqlBuscaDados = "SELECT * FROM " + tabelaGerador_oficio + " WHERE " + tabelaGerador_oficio + ".CORREIO=" + correioBD + " limit 1;";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);

                    if (rs.next()) {
                        txtAOF.setText(rs.getString("AOF"));
                        txtOficio.setText(rs.getString("OFICIO_CENOP"));
                        txtProcesso.setText(rs.getString("PROCESSO"));
                        txtInquerito.setText(rs.getString("INQUERITO"));
                        txtOficioN.setText(rs.getString("OFICIO"));
                        txtAutor.setText(rs.getString("AUTOR"));
                        txtReu.setText(rs.getString("REU"));
                        txtEnvolvido.setText(rs.getString("ENVOLVIDO"));
                        txtAbc.setText(rs.getString("ABC"));
                        txtCorpo.setText(rs.getString("RESPOSTA"));
                        txtDestinatário.setText(rs.getString("TRATAMENTO"));
                        txtEndereco.setText(rs.getString("ENDERECO"));
                        txtLinhas.setText(rs.getString("LINHAS"));
                        txtOficioN.setText(rs.getString("OFICIO"));
                        txtEmail.setText(rs.getString("EMAIL"));
                            if (!txtEmail.getText().equalsIgnoreCase("")){
                            jRadioButtonEmail.setSelected(true);
                        }else{
                            jRadioButtonNaoEmail.setSelected(true);
                        }
                        cmbAutor.removeAllItems();
                        cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Repres. Legal         : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("CONFIDENCIAL");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");

                        cmbConteudo.removeAllItems();
                        cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        //cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        cmbGerente.addItem(rs.getString("GERENTE"));
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        
                        jCheckBoxComAnexo.setSelected(false);
                        

                    } else {
                        txtAOF.setText("");
                        txtOficio.setText("");
                        txtProcesso.setText("");
                        txtInquerito.setText("");
                        txtOficioN.setText("");
                        txtAutor.setText("");
                        txtReu.setText("");
                        txtEnvolvido.setText("");
                        txtAbc.setText("");
                        txtCorpo.setText("OFICIO CENOP NÃO ENCONTRADO");
                        txtDestinatário.setText("");
                        txtEndereco.setText("");
                        txtLinhas.setText("0");
                        txtEmail.setText("");

                        cmbAutor.removeAllItems();
                        // cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        // cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        // cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        // cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("CONFIDENCIAL");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");

                        cmbConteudo.removeAllItems();
                        // cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        // cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        
                        jCheckBoxComAnexo.setSelected(false);
                    }

                } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAbrirCorreioActionPerformed

   
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            new FrmBuscaEndereco(this).setVisible(true);
        } catch (Exception ex) {
            
         //   JOptionPane.showMessageDialog(txtCorpo, ex);
            
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbConteudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConteudoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbConteudoActionPerformed

    private void cmbSigiloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSigiloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSigiloActionPerformed

    private void jButtonProcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProcessoActionPerformed
        // TODO add your handling code here:
         if ("".equals(txtProcesso.getText())) {
            JOptionPane.showMessageDialog(txtOficio, "Favor digitar o número do Processo");
        } else {

            String OficioProcesso = txtProcesso.getText();
            String OficioCenopBD = ("'" + OficioProcesso + "'");

            //  Connection conexao;
            //  try {
            //      conexao = DriverManager.getConnection(stringDeConexao, usuariobd, senhabd);
            //       String sqlBuscaDados = "SELECT * FROM gerador_oficios WHERE gerador_oficios.OficioCenop=" + OficioCenopBD + "";
            //       java.sql.Statement stm = conexao.createStatement();
            try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
                // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
                String sqlBuscaDados = "SELECT * FROM " + tabelaGerador_oficio + " WHERE " + tabelaGerador_oficio + ".PROCESSO=" + OficioCenopBD + ";";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);

                    if (rs.next()) {
                        txtAOF.setText(rs.getString("AOF"));
                        txtOficio.setText(rs.getString("OFICIO_CENOP"));
                        txtProcesso.setText(rs.getString("PROCESSO"));
                        txtInquerito.setText(rs.getString("INQUERITO"));
                        txtOficioN.setText(rs.getString("OFICIO"));
                        txtAutor.setText(rs.getString("AUTOR"));
                        txtReu.setText(rs.getString("REU"));
                        txtEnvolvido.setText(rs.getString("ENVOLVIDO"));
                        txtAbc.setText(rs.getString("ABC"));
                        txtCorpo.setText(rs.getString("RESPOSTA"));
                        txtDestinatário.setText(rs.getString("TRATAMENTO"));
                        txtEndereco.setText(rs.getString("ENDERECO"));
                        txtLinhas.setText(rs.getString("LINHAS"));
                        txtCorreio.setText(rs.getString("CORREIO"));
                        txtEmail.setText(rs.getString("EMAIL"));
                        
                            if (!txtEmail.getText().equalsIgnoreCase("")){
                            jRadioButtonEmail.setSelected(true);
                             }else{
                            jRadioButtonNaoEmail.setSelected(true);
                            }
                        cmbAutor.removeAllItems();
                        cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Repres. Legal         : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("CONFIDENCIAL");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");    

                        cmbConteudo.removeAllItems();
                        cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        //cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        cmbGerente.addItem(rs.getString("GERENTE"));
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");
                        
                        jCheckBoxComAnexo.setSelected(false);

                    } else {
                        txtAOF.setText("");
                        txtOficio.setText("");
                        txtProcesso.setText("");
                        txtInquerito.setText("");
                        txtOficioN.setText("");
                        txtAutor.setText("");
                        txtReu.setText("");
                        txtEnvolvido.setText("");
                        txtAbc.setText("");
                        txtCorpo.setText("PROCESSO NÃO ENCONTRADO");
                        txtDestinatário.setText("");
                        txtEndereco.setText("");
                        txtLinhas.setText("0");
                        txtEmail.setText("");

                        cmbAutor.removeAllItems();
                        // cmbAutor.addItem(rs.getString("CMBAUTOR"));
                        cmbAutor.addItem("");
                        cmbAutor.addItem("Autor (a)                  : ");
                        cmbAutor.addItem("Requerente             : ");
                        cmbAutor.addItem("Exequente               : ");
                        cmbAutor.addItem("Reclamante             : ");
                        cmbAutor.addItem("Embargante             : ");
                        cmbAutor.addItem("Promovente             : ");
                        cmbAutor.addItem("Inventariante            : ");
                        cmbAutor.addItem("Interessado             : ");

                        cmbReu.removeAllItems();
                        // cmbReu.addItem(rs.getString("CMBREU"));
                        cmbReu.addItem("");
                        cmbReu.addItem("Réu                         : ");
                        cmbReu.addItem("Executado (a)          : ");
                        cmbReu.addItem("Requerido (a)          : ");
                        cmbReu.addItem("Reclamado (a)        : ");
                        cmbReu.addItem("Inventariado (a)       : ");
                        cmbReu.addItem("Embargado (a)        : ");
                        cmbReu.addItem("Envolvido (a)           : ");
                        cmbReu.addItem("Promovido (a)         : ");

                        cmbEnvolvido.removeAllItems();
                        // cmbEnvolvido.addItem(rs.getString("CMBENVOLVIDO"));
                        cmbEnvolvido.addItem("");
                        cmbEnvolvido.addItem("Envolvido (a)           : ");
                        cmbEnvolvido.addItem("Inventariante           : ");
                        cmbEnvolvido.addItem("Falecido (a)            : ");
                        cmbEnvolvido.addItem("Vitima                      : ");
                        cmbEnvolvido.addItem("Classe                     : ");
                        cmbEnvolvido.addItem("Assunto                   : ");

                        cmbSigilo.removeAllItems();
                        // cmbSigilo.addItem(rs.getString("SIGILOSO"));
                        cmbSigilo.addItem("");
                        cmbSigilo.addItem("CONFIDENCIAL");
                        cmbSigilo.addItem("SIGILOSO");
                        cmbSigilo.addItem("SEGREDO DE JUSTIÇA");
                        cmbSigilo.addItem("PRIORIDADE IDOSO");
                        cmbSigilo.addItem("RÉU PRESO");
                        

                        cmbConteudo.removeAllItems();
                        // cmbConteudo.addItem(rs.getString("CMBCONTEUDO"));
                        cmbConteudo.addItem("");
                        // cmbConteudo.addItem("AÇÔES");
                        cmbConteudo.addItem("AÇÕES CVM");
                        cmbConteudo.addItem("AÇÕES PORTAL");
                        cmbConteudo.addItem("DILAÇÂO");
                        cmbConteudo.addItem("PARCIAL");
                        cmbConteudo.addItem("CONTINUADO");
                        
                        cmbGerente.removeAllItems();
                        PreencheCmbGerente();
                        
                        cmbEnderecoRetorno.removeAllItems();
                        cmbEnderecoRetorno.addItem(rs.getString("ENDERECO_RET"));
                        cmbEnderecoRetorno.addItem("Av. São João");
                        //cmbEnderecoRetorno.addItem("Vila Mariana - Central");
                        cmbEnderecoRetorno.addItem("Vila Mariana");        
                        
                        jCheckBoxComAnexo.setSelected(false);
                        

                    }

                } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButtonProcessoActionPerformed

    private void jRadioButtonEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonEmailActionPerformed
        if (jRadioButtonEmail.isSelected()) {
            txtEmail.setEnabled(true);   
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonEmailActionPerformed

    private void jRadioButtonNaoEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNaoEmailActionPerformed
        if (jRadioButtonNaoEmail.isSelected()) {
            txtEmail.setText("");
            txtEmail.setEnabled(false); 
            jCheckBoxComAnexo.setSelected(false);
            
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonNaoEmailActionPerformed

    private void jRadioButton3viaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3viaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3viaActionPerformed

    private void cmbGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGerenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbGerenteActionPerformed

    private void cmbEnderecoRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnderecoRetornoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEnderecoRetornoActionPerformed

    private void txtCorreioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreioKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == evt.VK_ENTER){   
           txtCorreio.requestFocus();
           } 
    }//GEN-LAST:event_txtCorreioKeyPressed

    private void txtAOFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAOFActionPerformed
        // TODO add your handling code here:
       
            //txtAOF.setCaretPosition(0);
       
       
        
    }//GEN-LAST:event_txtAOFActionPerformed

    private void jCheckBoxComAnexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxComAnexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxComAnexoActionPerformed

    private void InsereOficio(String oficioCenop, String aof, String processo, String inquerito, String oficio, String autor,
            String reu, String envolvido, String abc, String resposta, String tratamento, String endereco, String CMBAUTOR, String CMBREU, String CMBENVOLVIDO, String CMBCONTEUDO, String Linhas, String Chave, String sigiloso, String correio, String email, String CMBGERENTE, String CMBENDERECORET) throws SQLException {

        if ((!"".equals(aof))) {
            String aofBd = ("'" + aof + "'");
            
            try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {

                String sqlBuscaDados = "SELECT * FROM " + tabelaGerador_oficio + " WHERE " + tabelaGerador_oficio + ".AOF=" + aofBd + ";";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);

                    if (rs.next()) {

                        //com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar();
                        String sqlUp = "UPDATE " + tabelaGerador_oficio + " "
                                + "SET OFICIO_CENOP=?,PROCESSO=?,INQUERITO=?,OFICIO=?,AUTOR=?,"
                                + "REU=?,ENVOLVIDO=?,ABC=?,RESPOSTA=?,TRATAMENTO=?,ENDERECO=?,CMBAUTOR=?,CMBREU=?,"
                                + "CMBENVOLVIDO=?,CMBCONTEUDO=?,LINHAS=?,SIGILOSO=?,CORREIO=?,DATA=now(),EMAIL=?,GERENTE=?,ENDERECO_RET=? "
                                + "WHERE AOF=?";
                        
                        PreparedStatement comando = cn.prepareStatement(sqlUp);
                        comando.setString(1, oficioCenop);
                        comando.setString(2, processo);
                        comando.setString(3, inquerito);
                        comando.setString(4, oficio);
                        comando.setString(5, autor);
                        comando.setString(6, reu);
                        comando.setString(7, envolvido);
                        comando.setString(8, abc);
                        comando.setString(9, resposta);
                        comando.setString(10, tratamento);
                        comando.setString(11, endereco);
                        comando.setString(12, CMBAUTOR);
                        comando.setString(13, CMBREU);
                        comando.setString(14, CMBENVOLVIDO);
                        comando.setString(15, CMBCONTEUDO);
                        comando.setString(16, Linhas);
                        comando.setString(17, sigiloso);
                        comando.setString(18, correio);
                        comando.setString(19, email);
                        comando.setString(20, CMBGERENTE);
                        comando.setString(21, CMBENDERECORET);
                        comando.setString(22, aof);
                        System.out.println("Executando comando...");
                        comando.execute();
                        //  JOptionPane.showMessageDialog(this, "Gravado com sucesso.");
                        System.out.println("SQL UPDATE " + sqlUp);
                        System.out.println("Fechando conexão...");

                    } else {
                         String sql = "INSERT INTO " + tabelaGerador_oficio + " (OFICIO_CENOP,AOF,PROCESSO,INQUERITO,OFICIO,AUTOR,"
                                + "REU,ENVOLVIDO,ABC,RESPOSTA,TRATAMENTO,ENDERECO,CHAVE,CMBAUTOR,CMBREU,CMBENVOLVIDO,CMBCONTEUDO,LINHAS,SIGILOSO,CORREIO,DATA,EMAIL,GERENTE,ENDERECO_RET) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?)";

                        PreparedStatement comando = cn.prepareStatement(sql);
                        comando.setString(1, oficioCenop);
                        comando.setString(2, aof);
                        comando.setString(3, processo);
                        comando.setString(4, inquerito);
                        comando.setString(5, oficio);
                        comando.setString(6, autor);
                        comando.setString(7, reu);
                        comando.setString(8, envolvido);
                        comando.setString(9, abc);
                        comando.setString(10, resposta);
                        comando.setString(11, tratamento);
                        comando.setString(12, endereco);
                        comando.setString(13, Chave);
                        comando.setString(14, CMBAUTOR);
                        comando.setString(15, CMBREU);
                        comando.setString(16, CMBENVOLVIDO);
                        comando.setString(17, CMBCONTEUDO);
                        comando.setString(18, Linhas);
                        comando.setString(19, sigiloso);
                        comando.setString(20, correio);
                        comando.setString(21, email);
                        comando.setString(22, CMBGERENTE);
                        comando.setString(23, CMBENDERECORET);
                        //   System.out.println("Executando comando...");
                        comando.execute();
                        

                    }

                } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }

        } else {

            String oficiocenopBD = ("'" + oficioCenop + "';");
            try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {

                String sqlBuscaDados = "SELECT * FROM " + tabelaGerador_oficio + " WHERE " + tabelaGerador_oficio + ".oficio_cenop=" + oficiocenopBD + "";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);

                    if (rs.next()) {
                        //   com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar();
                        String sqlUp = "UPDATE " + tabelaGerador_oficio + " "
                                + "SET AOF=?,PROCESSO=?,INQUERITO=?,OFICIO=?,AUTOR=?,"
                                + "REU=?,ENVOLVIDO=?,ABC=?,RESPOSTA=?,TRATAMENTO=?,ENDERECO=?,CMBAUTOR=?,CMBREU=?,CMBENVOLVIDO=?,CMBCONTEUDO=?,LINHAS=?,SIGILOSO=?,CORREIO=? ,DATA=now(),EMAIL=?, GERENTE=?, ENDERECO_RET=? "
                                + "WHERE OFICIO_CENOP=? ";
                        
                        PreparedStatement comando = cn.prepareStatement(sqlUp);
                        comando.setString(1, aof);
                        comando.setString(2, processo);
                        comando.setString(3, inquerito);
                        comando.setString(4, oficio);
                        comando.setString(5, autor);
                        comando.setString(6, reu);
                        comando.setString(7, envolvido);
                        comando.setString(8, abc);
                        comando.setString(9, resposta);
                        comando.setString(10, tratamento);
                        comando.setString(11, endereco);
                        comando.setString(12, CMBAUTOR);
                        comando.setString(13, CMBREU);
                        comando.setString(14, CMBENVOLVIDO);
                        comando.setString(15, CMBCONTEUDO);
                        comando.setString(16, Linhas);
                        comando.setString(17, sigiloso);
                        comando.setString(18, correio);
                        comando.setString(19, email);
                        comando.setString(20, CMBGERENTE);
                        comando.setString(21, CMBENDERECORET);
                        comando.setString(22, oficioCenop);
                        
                        System.out.println("Executando comando...");
                        System.out.println("SQL up2 " + sqlUp);
                        comando.execute();
                        
                        //  JOptionPane.showMessageDialog(this, "Gravado com sucesso.");
                        System.out.println("Fechando conexão...");

                    } else {
                        String sql = "INSERT INTO " + tabelaGerador_oficio + "(OFICIO_CENOP,AOF,PROCESSO,INQUERITO,OFICIO,AUTOR,"
                                + "REU,ENVOLVIDO,ABC,RESPOSTA,TRATAMENTO,ENDERECO,CHAVE,CMBAUTOR,CMBREU,CMBENVOLVIDO,CMBCONTEUDO,LINHAS,SIGILOSO,CORREIO,DATA,EMAIL,GERENTE,ENDERECO_RET) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?)";

                        PreparedStatement comando = cn.prepareStatement(sql);
                        comando.setString(1, oficioCenop);
                        comando.setString(2, aof);
                        comando.setString(3, processo);
                        comando.setString(4, inquerito);
                        comando.setString(5, oficio);
                        comando.setString(6, autor);
                        comando.setString(7, reu);
                        comando.setString(8, envolvido);
                        comando.setString(9, abc);
                        comando.setString(10, resposta);
                        comando.setString(11, tratamento);
                        comando.setString(12, endereco);
                        comando.setString(13, Chave);
                        comando.setString(14, CMBAUTOR);
                        comando.setString(15, CMBREU);
                        comando.setString(16, CMBENVOLVIDO);
                        comando.setString(17, CMBCONTEUDO);
                        comando.setString(18, Linhas);
                        comando.setString(19, sigiloso);
                        comando.setString(20, correio);
                        comando.setString(21, email);
                        comando.setString(22, CMBGERENTE);
                        comando.setString(23, CMBENDERECORET);
                        //   System.out.println("Executando comando...");
                        comando.execute();
                        System.out.println("SQL ELSE2 insert " + sql);
                    }

                } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new FrmPrincipal().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirAOF;
    private javax.swing.JButton btnAbrirCorreio;
    private javax.swing.JButton btnAbrirOficioCenop;
    private javax.swing.JToggleButton btnLimpar;
    private javax.swing.JButton btnOficio;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbAutor;
    private javax.swing.JComboBox cmbConteudo;
    private javax.swing.JComboBox<String> cmbEnderecoRetorno;
    private javax.swing.JComboBox cmbEnvolvido;
    private javax.swing.JComboBox cmbGerente;
    private javax.swing.JComboBox cmbRespostas;
    private javax.swing.JComboBox cmbReu;
    private javax.swing.JComboBox cmbSigilo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonProcesso;
    private javax.swing.JCheckBox jCheckBoxComAnexo;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrazo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton3via;
    private javax.swing.JRadioButton jRadioButtonEmail;
    private javax.swing.JRadioButton jRadioButtonNaoEmail;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JFormattedTextField txtAOF;
    private javax.swing.JTextField txtAbc;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextPane txtCorpo;
    private javax.swing.JFormattedTextField txtCorreio;
    private javax.swing.JLabel txtData;
    private javax.swing.JTextField txtDestinatário;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextPane txtEndereco;
    private javax.swing.JTextField txtEnvolvido;
    private javax.swing.JTextField txtInquerito;
    private javax.swing.JTextField txtLinhas;
    private javax.swing.JTextField txtOficio;
    private javax.swing.JTextField txtOficioN;
    private javax.swing.JTextField txtProcesso;
    private javax.swing.JTextField txtReu;
    // End of variables declaration//GEN-END:variables

    private void EXIT_ON_CLOSE(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    private void PreencheCmbGerente(){
         try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
                
                String sqlBuscaDados = "SELECT * FROM gerador_oficios_gerentes  WHERE  STATUS='ATIVO' order by GER_GRUPO;";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);
                    cmbGerente.addItem("SEM ASSINATURA");
                    while(rs.next()){
                    cmbGerente.addItem(rs.getString("GER_GRUPO"));
                    }
               } catch (SQLException e) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }    
}
     private String GerenteSetor(String gerenteGrupo){
         String GerenteSetor1 = null; 
         try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
                // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
                String sqlBuscaDados = "SELECT * FROM gerador_oficios_gerentes  WHERE GER_GRUPO='" + gerenteGrupo + "' AND STATUS='ATIVO';";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {
                    ResultSet rs = stm.executeQuery(sqlBuscaDados);
                    if(rs.next()){
                   GerenteSetor1 =  rs.getString("GER_SETOR");
                    //oficio. rs.getString("GER_GRUPO"));
                    }
                } catch (SQLException e) {
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }   
         System.out.println("GERENTESETOR " + GerenteSetor1);
         return GerenteSetor1;
}
     
 private Object[] AssinaturaGerente(String Gerente1) {
  Object[] o = new Object[2];
  
   try (com.mysql.jdbc.Connection cn = (com.mysql.jdbc.Connection) new Conexao().conectar()) {
                // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
                String sqlBuscaDados = "SELECT * FROM gerador_oficios_gerentes  WHERE GER_GRUPO='" + Gerente1 + "' AND STATUS='ATIVO' limit 1;";

                System.out.println(sqlBuscaDados);
                java.sql.Statement stm = cn.createStatement();

                try {

                    ResultSet rs = stm.executeQuery(sqlBuscaDados);
                    if(rs.next()){
                    o[0] = rs.getString("GER_SETOR");
                    o[1] = rs.getString("TIPO");
                    //oficio. rs.getString("GER_GRUPO"));
                    }
                } catch (SQLException e) {
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);

            }         
         return o;
}
 
}