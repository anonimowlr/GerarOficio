/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import DAO.Conexao;
import com.mysql.jdbc.Connection;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author f7057419
 */
public final class FrmBuscaEndereco extends javax.swing.JFrame {
    
    private FrmPrincipal frmprincipal;
    
    public FrmBuscaEndereco(FrmPrincipal frm){
        
         initComponents();
         
         //Para o form ficar no meio da tela    
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        Integer largura;
        Integer altura;

        largura = d.width;
        largura = (largura / 2) - (660 / 2);
        altura = d.height;
        altura = (altura / 2) - (620 / 2);
         
        
        
        setBounds(largura, altura, 660, 620);
         
        
        listarUfs();
        this.frmprincipal = frm;
        this.rbConferido.setEnabled(false);
    }
    /**
     * Creates new form jFrmPrincipal
     */
    public FrmBuscaEndereco() {
//        initComponents();
//        listarUfs();
//      //    listarOrgaos("SANTOS");     
    }
    
   
        public void teste(){
            JFormattedTextField txtCEP2 = new JFormattedTextField(Mascara("#####-###")); 
        }
                
        
        
        
    
    private String carregaFormDjo(String djo) throws SQLException {
        String conferido = null;
        String  endereco = null;
        try (Connection cn = (Connection) new Conexao().conectar()) {
            // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
          String sqlBuscaDados = "SELECT * FROM endereco_orgaos WHERE endereco_orgaos.NDJO="+djo+"";         
          
          
            System.out.println(sqlBuscaDados);          
            java.sql.Statement stm = cn.createStatement();          
            
            try {
              ResultSet rs = stm.executeQuery(sqlBuscaDados);    
                  while (rs.next()) {
                
            System.out.println(rs.getString("conferido"));  
            
            endereco=rs.getString("bacen")+"&"+
                    rs.getString("tribunal")+"&"+
                     rs.getString("comarca")+"&"+
                     rs.getString("orgao")+"&"+
                     rs.getString("rua")+"&"+
                     rs.getString("cep")+"&"+
                     rs.getString("bairro")+"&"+
                     rs.getString("cidade")+"&"+
                     rs.getString("uf")+"&"+
                     rs.getString("conferido");      
        }
            } catch (SQLException e) {
            }finally{
                cn.close();
            }
               
        return endereco;
    }
       }
    
        private String carregaFormBacen(String Bacen) throws SQLException {
        String conferido = null;
        String  endereco = null;
        try (Connection cn = (Connection) new Conexao().conectar()) {
            // String sqlBuscaEvtc = "Select hc_honor.ID_H  from hc_honor where hc_honor.DATA_EVT= " + dataHonorariosBd + " and  hc_honor.NPJ = " + npjHonorariosBD + "";
          String sqlBuscaDados = "SELECT * FROM endereco_orgaos WHERE endereco_orgaos.bacen="+Bacen+"";
          
          
          
            System.out.println(sqlBuscaDados);          
            java.sql.Statement stm = cn.createStatement();
           
            
            try {
              ResultSet rs = stm.executeQuery(sqlBuscaDados);    
                  while (rs.next()) {
                
            System.out.println(rs.getString("conferido"));  
            
            endereco=rs.getString("ndjo")+"&"+
                     rs.getString("tribunal")+"&"+
                     rs.getString("comarca")+"&"+
                     rs.getString("orgao")+"&"+
                     rs.getString("rua")+"&"+
                     rs.getString("cep")+"&"+
                     rs.getString("bairro")+"&"+
                     rs.getString("cidade")+"&"+
                     rs.getString("uf")+"&"+
                     rs.getString("conferido");      
        }
            } catch (SQLException e) {
            }finally{
                cn.close();
            }
               
        return endereco;
    }
       }
    
     private void atualizaDados() throws SQLException {
          
        
     
        /*/ String frmGrandesConcessionariasa = txtGrandesConcessionarias1.getText().toString();
        String frmEstadualSEFAZa = txtSefaz.getText().toString();
        String frmEstadualorgaosEstaduaisa = txtOrgaosEstaduais.getText().toString();
        String frmPMSPa = txtPmsp.getText().toString();
        String frmFGTSa = txtFgts.getText().toString();
        String frmDistribuicaoa = txtDistribuicao.getText().toString();
        String frmRepassea = txtRepasse.getText().toString();
        String frmProaca = txtProac.getText().toString();
        String frmRestituicaoIPVAa = txtRestituicaoIpva.getText().toString();*/
         
        String bacen = txtBacen.getText().trim();
        //Integer bacenN=Integer.parseInt(bacen);
        
        bacen="'"+bacen+"'";
       
        
        String djo = txtDjo.getText().trim(); 
        //Integer djoN=Integer.parseInt(djo);
        djo="'"+djo+"'";        
        String tribunal =txtTribunal.getText().trim(); 
        tribunal="'"+tribunal+"'";
        String comarca =txtComarca.getText().trim(); 
        comarca="'"+comarca+"'";
        String vara =txtVara.getText().trim(); 
        vara="'"+vara+"'";
        String rua =txtRua.getText().trim(); 
        rua="'"+rua+"'";
        String cep =txtCep.getText().trim(); 
        cep="'"+cep+"'";
         System.out.println(cep);
        String bairro =txtBairro.getText().trim(); 
        bairro="'"+bairro+"'";
        String cidade =txtCidade.getText().trim(); 
        cidade="'"+ cidade+"'";
        String uf =txtUf.getText().trim(); 
        uf="'"+uf+"'";
       String conferido;
      //   if (rbConferido.isSelected()) {
          conferido="'1'";
     //    }else{
     //       conferido="'0'"; 
    //     }
             
 
        
  
         
         
            
        try {
            try (java.sql.Connection cn = (java.sql.Connection) new Conexao().conectar()) {
                String sqlAtualiza = "UPDATE endereco_orgaos set endereco_orgaos.tribunal ="+tribunal +""
                        + ", endereco_orgaos.comarca ="+comarca+""
                        + ", endereco_orgaos.orgao ="+vara+""
                        + ", endereco_orgaos.rua ="+rua+""
                        + ", endereco_orgaos.cep ="+cep+""
                        + ", endereco_orgaos.bairro ="+bairro+""
                        + ", endereco_orgaos.cidade ="+cidade+""
                        + ", endereco_orgaos.bacen ="+bacen+""
                        + ", endereco_orgaos.uf ="+uf+""
                        + ", endereco_orgaos.comarca ="+comarca+""
                        + ", endereco_orgaos.conferido ="+conferido+""
                        + "    WHERE endereco_orgaos.ndjo="+djo;
                
                
// + frmGrandesConcessionariasa + ",Sefaz=" + frmEstadualSEFAZa + ",OrgaosEstaduais=" + frmEstadualorgaosEstaduaisa + ",Pmsp=" + frmPMSPa + ",Fgts=" + frmFGTSa + ",Distribuicao=" + frmDistribuicaoa + ",Proac=" + frmProaca + ",Repasse=" + frmRepassea + ",RestituicaoIpva=" + frmRestituicaoIPVAa + ",dtRef=" +"'"+frmAnoa+"-"+frmMesa+"-"+"01"+"'" +      " where MesAno= " + frmMesa + frmAnoa + " ;";
                
                
                
                System.out.println(sqlAtualiza);
                
                
//            int n = JOptionPane.showConfirmDialog(null,
//                    "Dados já gravados para o mês e ano informamos. Deseja Atualizar?",
//                    "Arrecadação",
//                    JOptionPane.YES_NO_OPTION);
                
                //     if (n == 0) {

                Statement stm = cn.createStatement();

                int rs = stm.executeUpdate(sqlAtualiza);
                
                if (rs != 0) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Dados atualizados com sucesso!",
                            "Arrecadação",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(rootPane,
                            "Falha",
                            "Arrecadação",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                }
                cn.close();
                //  }
            }
// + frmGrandesConcessionariasa + ",Sefaz=" + frmEstadualSEFAZa + ",OrgaosEstaduais=" + frmEstadualorgaosEstaduaisa + ",Pmsp=" + frmPMSPa + ",Fgts=" + frmFGTSa + ",Distribuicao=" + frmDistribuicaoa + ",Proac=" + frmProaca + ",Repasse=" + frmRepassea + ",RestituicaoIpva=" + frmRestituicaoIPVAa + ",dtRef=" +"'"+frmAnoa+"-"+frmMesa+"-"+"01"+"'" +      " where MesAno= " + frmMesa + frmAnoa + " ;";
        }catch (SQLException | HeadlessException e){
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTribunal = new javax.swing.JTextField();
        txtComarca = new javax.swing.JTextField();
        txtVara = new javax.swing.JTextField();
        txtRua = new javax.swing.JTextField();
        txtBairro = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        txtUf = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rbConferido = new javax.swing.JRadioButton();
        btnLimpar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDjo = new javax.swing.JTextField();
        txtBacen = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnBuscar1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxOrgao = new javax.swing.JComboBox();
        jComboBoxComarca = new javax.swing.JComboBox();
        jComboBoxUF = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtCep = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButtonBuscaCEP = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Busca Endereços");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        txtTribunal.setBackground(new java.awt.Color(102, 102, 102));
        txtTribunal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTribunal.setForeground(new java.awt.Color(255, 255, 255));
        txtTribunal.setText(" ");
        txtTribunal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtTribunal);
        txtTribunal.setBounds(170, 250, 380, 30);

        txtComarca.setBackground(new java.awt.Color(102, 102, 102));
        txtComarca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtComarca.setForeground(new java.awt.Color(255, 255, 255));
        txtComarca.setText(" ");
        txtComarca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtComarca);
        txtComarca.setBounds(170, 290, 380, 30);

        txtVara.setBackground(new java.awt.Color(102, 102, 102));
        txtVara.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtVara.setForeground(new java.awt.Color(255, 255, 255));
        txtVara.setText(" ");
        txtVara.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtVara);
        txtVara.setBounds(170, 330, 380, 30);

        txtRua.setBackground(new java.awt.Color(102, 102, 102));
        txtRua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRua.setForeground(new java.awt.Color(255, 255, 255));
        txtRua.setText(" ");
        txtRua.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtRua);
        txtRua.setBounds(170, 370, 380, 30);

        txtBairro.setBackground(new java.awt.Color(102, 102, 102));
        txtBairro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBairro.setForeground(new java.awt.Color(255, 255, 255));
        txtBairro.setText(" ");
        txtBairro.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtBairro);
        txtBairro.setBounds(170, 410, 380, 30);

        txtCidade.setBackground(new java.awt.Color(102, 102, 102));
        txtCidade.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCidade.setForeground(new java.awt.Color(255, 255, 255));
        txtCidade.setText(" ");
        txtCidade.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txtCidade);
        txtCidade.setBounds(170, 450, 380, 30);

        txtUf.setBackground(new java.awt.Color(102, 102, 102));
        txtUf.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUf.setForeground(new java.awt.Color(255, 255, 255));
        txtUf.setText(" ");
        txtUf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUfActionPerformed(evt);
            }
        });
        jPanel1.add(txtUf);
        txtUf.setBounds(560, 450, 59, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tribunal    :");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(30, 250, 110, 22);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Comarca  :");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 290, 100, 22);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Orgão       :");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(30, 330, 110, 22);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Rua           :");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(30, 370, 110, 20);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Bairro       :");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(30, 410, 100, 22);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Email       :");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(30, 500, 100, 22);

        rbConferido.setBackground(new java.awt.Color(102, 102, 102));
        rbConferido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbConferido.setForeground(new java.awt.Color(255, 255, 255));
        rbConferido.setText("Conferido");
        jPanel1.add(rbConferido);
        rbConferido.setBounds(230, 540, 90, 30);

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpar.setForeground(new java.awt.Color(255, 0, 0));
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpar);
        btnLimpar.setBounds(550, 540, 80, 30);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 0));
        jButton1.setText("Conferir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(330, 540, 90, 30);

        jPanel2.setBackground(new java.awt.Color(126, 124, 124));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Bacen");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 50, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("DJO");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 40, -1));

        txtDjo.setBackground(new java.awt.Color(102, 102, 102));
        txtDjo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDjo.setForeground(new java.awt.Color(255, 255, 255));
        txtDjo.setText("  0");
        txtDjo.setBorder(null);
        txtDjo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDjoActionPerformed(evt);
            }
        });
        jPanel2.add(txtDjo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 70, 30));

        txtBacen.setBackground(new java.awt.Color(102, 102, 102));
        txtBacen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBacen.setForeground(new java.awt.Color(255, 255, 255));
        txtBacen.setText("  0");
        txtBacen.setBorder(null);
        txtBacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBacenActionPerformed(evt);
            }
        });
        jPanel2.add(txtBacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 70, 30));

        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(51, 0, 204));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 80, 30));

        btnBuscar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscar1.setForeground(new java.awt.Color(0, 153, 51));
        btnBuscar1.setText("Buscar");
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 80, 30));

        jPanel1.add(jPanel2);
        jPanel2.setBounds(350, 70, 280, 110);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxOrgao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ESCOLHA" }));
        jComboBoxOrgao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxOrgaoActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxOrgao, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 220, 30));

        jComboBoxComarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ESCOLHA" }));
        jComboBoxComarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxComarcaActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxComarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 220, 30));

        jComboBoxUF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ESCOLHA" }));
        jComboBoxUF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxUFMouseClicked(evt);
            }
        });
        jComboBoxUF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxUFActionPerformed(evt);
            }
        });
        jComboBoxUF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxUFPropertyChange(evt);
            }
        });
        jPanel3.add(jComboBoxUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 220, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Orgão");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 60, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("UF");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 40, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Comarca");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, -1));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(20, 40, 320, 140);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Busca Endereço");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(350, 20, 260, 40);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("Enviar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(440, 540, 80, 30);

        txtEmail.setBackground(new java.awt.Color(102, 102, 102));
        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setText(" ");
        txtEmail.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmail);
        txtEmail.setBounds(170, 490, 380, 30);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Cidade     :");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(30, 450, 100, 22);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCep.setBackground(new java.awt.Color(102, 102, 102));
        txtCep.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCep.setForeground(new java.awt.Color(255, 255, 255));
        txtCep.setText(" ");
        txtCep.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(txtCep, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 210, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Cep           : ");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jButtonBuscaCEP.setText("Buscar");
        jButtonBuscaCEP.setEnabled(false);
        jButtonBuscaCEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscaCEPActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonBuscaCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        jPanel1.add(jPanel4);
        jPanel4.setBounds(20, 190, 500, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/fundo.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(3, 4, 340, 590);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxOrgaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOrgaoActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        // TODO add your handling code here:
        
        // TODO add your handling code here:        
        try {
           String dados=String.valueOf(jComboBoxOrgao.getSelectedItem().toString());
           if (!dados.equalsIgnoreCase("ESCOLHA")) {
               
                String uf=jComboBoxUF.getSelectedItem().toString() ;
                String comarca=jComboBoxComarca.getSelectedItem().toString() ;
                String vara=jComboBoxOrgao.getSelectedItem().toString() ;
                String conferido = null;

                 try {
                Connection cn = (Connection) new Conexao().conectar();
                String sqlBuscaDados = "SELECT * FROM bdjudicial.endereco_orgaos WHERE UF='"+uf+"' AND COMARCA='"+comarca+"' AND ORGAO='"+vara+"';";       

                java.sql.Statement stm = cn.createStatement(); 
                ResultSet rs = stm.executeQuery(sqlBuscaDados); 

                while (rs.next()) {      
                    System.out.println(rs.getString("NDJO"));
                    txtDjo.setText("  "+(rs.getString("NDJO")));
                    txtBacen.setText("  "+(rs.getString("BACEN")));
                    txtTribunal.setText("  "+(rs.getString("TRIBUNAL")));
                    txtComarca.setText("  "+(rs.getString("COMARCA")));
                    txtVara.setText("  "+(rs.getString("ORGAO")));
                    txtRua.setText("  "+(rs.getString("RUA")));
                    txtCep.setText("  "+(rs.getString("CEP")));
                    txtBairro.setText("  "+(rs.getString("BAIRRO")));
                    txtCidade.setText("  "+(rs.getString("CIDADE")));
                    txtUf.setText("  "+(rs.getString("UF")));
                    System.out.println(rs.getString("UF"));
                    conferido=((rs.getString("CONFERIDO")));
                    
                    if ("0".equals(conferido)) {
                        rbConferido.setSelected(false);
                     //   JOptionPane.showMessageDialog(rootPane, "Favor completar o endereço e gravar.");
                                                
                    }else{
                        rbConferido.setSelected(true);
                    }     
        }             
    } catch (Exception e) {
    }  
               
               
                      
        }else{
//            jComboBoxOrgao.removeAllItems();
//            jComboBoxOrgao.addItem("Escolha");   
             
        }
        } catch (Exception e) {
            System.out.println(e);
        }       
        
        
        
        
        
//        
//        String comarca=String.valueOf(jComboBoxOrgao.getSelectedItem().toString());
//        if (!comarca.equalsIgnoreCase("ESCOLHA")) {
//            
//        
//        
//        
//                String uf=jComboBoxUF.getSelectedItem().toString() ;
//                String comarca=jComboBoxUF.getSelectedItem().toString() ;
//                String vara=jComboBoxUF.getSelectedItem().toString() ;
//
//                 try {
//                Connection cn = (Connection) new Conexao().conectar();
//                String sqlBuscaDados = "SELECT * FROM bdjudicial.endereco_orgaos WHERE UF='"+uf+"' AND COMARCA='"+comarca+"' AND ORGAO='"+vara+"';";       
//
//                java.sql.Statement stm = cn.createStatement(); 
//                ResultSet rs = stm.executeQuery(sqlBuscaDados); 
//
//                while (rs.next()) {      
//
//                    txtDjo.setText((rs.getString("NDJO")));
//                    txtBacen.setText((rs.getString("BACEN")));
////                    txtTribunal.setText("  " +tribunal);
////                    txtComarca.setText("  " +comarca);
////                    txtVara.setText("  " +vara);
////                    txtRua.setText("  " +rua);
////                    txtCep.setText("  " +cep);
////                    txtBairro.setText("  " +bairro);
////                    txtCidade.setText("  " +cidade);
////                    txtUf.setText("  " +uf);
////                    if ("0".equals(conferido)) {
////                        rbConferido.setSelected(false);
////                    }else{
////                        rbConferido.setSelected(true);
////        }
//         
//        }             
//    } catch (Exception e) {
//        e.printStackTrace();
//    }  
//         
//         
//    }     
         
    }//GEN-LAST:event_jComboBoxOrgaoActionPerformed

    private void jComboBoxUFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxUFActionPerformed
        // TODO add your handling code here:        
                String dados=String.valueOf(jComboBoxUF.getSelectedItem().toString());
        try {
             if (!dados.equalsIgnoreCase("ESCOLHA")) {
            jComboBoxComarca.removeAllItems();
        //    jComboBoxComarca.addItem("ESCOLHA");
            listarComarcas(dados);          
        }else{
            jComboBoxComarca.removeAllItems();
           jComboBoxComarca.addItem("ESCOLHA"); 
             
        }
        } catch (Exception e) {
        }
        
        
    }//GEN-LAST:event_jComboBoxUFActionPerformed

    private void jComboBoxComarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxComarcaActionPerformed
        // TODO add your handling code here:        
        try {
            String comarca=String.valueOf(jComboBoxComarca.getSelectedItem().toString());
            String uf=String.valueOf(jComboBoxUF.getSelectedItem().toString());
        if (!comarca.equalsIgnoreCase("ESCOLHA")) {
         //   jComboBoxOrgao.removeAllItems();
         //   jComboBoxOrgao.addItem("Escolha");
            listarOrgaos(uf,comarca);          
        }else{
            jComboBoxOrgao.removeAllItems();
            jComboBoxOrgao.addItem("ESCOLHA"); 
            
        }
        } catch (Exception e) {
            System.out.println(e);
        }       
    }//GEN-LAST:event_jComboBoxComarcaActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
       limpaDados();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
//             if (!rbConferido.isSelected()) {
//            JOptionPane.showMessageDialog(rootPane, "Favor Conferir o Endereço.");            
//        }else{
   //     atualizaDados();
//             }
             int resposta;
             resposta = JOptionPane.showConfirmDialog(null, "Os dados foram verificados? \n Confima a gravação?");
             
             if (resposta == JOptionPane.YES_OPTION) {
             // verifica se o usuário clicou no botão YES
                 atualizaDados();
                // JOptionPane.showMessageDialog(null, "Dados gravados com sucesso.");
                 rbConferido.setSelected(true);
             } else {
                 JOptionPane.showMessageDialog(null, "Favor Conferir os dados.");
             }
             
             
    //    } catch (SQLException ex) {
             } catch (Exception e) {
         //  Logger.getLogger(JFMPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        
       
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtDjoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDjoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDjoActionPerformed

    private void txtBacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBacenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBacenActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        String djo=txtDjo.getText().trim();
        String bacen;
        String dadosForm = null;
        String tribunal ;
        String comarca ;
        String vara ;
        String rua;
        String cep ;
        String bairro ;
        String cidade;
        String uf ;
        String conferido;
        Integer djoN;

        djoN=Integer.parseInt(djo);

        djo="'"+djoN+"'";
        try {
            dadosForm=  carregaFormDjo(djo);
        } catch (SQLException ex) {
          //  Logger.getLogger(FrmBuscaEndereco.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (dadosForm==null) {
            JOptionPane.showMessageDialog(rootPane, "Favor digitar um número de DJO válido. ");
        }else{
            String d[]=dadosForm.split("&");
            bacen = d[0];
            tribunal = d[1];
            comarca = d[2];
            vara = d[3];
            rua = d[4];
            cep = d[5];
            bairro = d[6];
            cidade = d[7];
            uf = d[8];
            conferido = d[9];

            txtBacen.setText(" "+bacen);
            txtTribunal.setText("  " +tribunal);
            txtComarca.setText("  " +comarca);
            txtVara.setText("  " +vara);
            txtRua.setText("  " +rua);
            txtCep.setText("  " +cep);
            txtBairro.setText("  " +bairro);
            txtCidade.setText("  " +cidade);
            txtUf.setText("  " +uf);
            if ("0".equals(conferido)) {
                rbConferido.setSelected(false);
            }else{
                rbConferido.setSelected(true);
            }
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String bacen=txtBacen.getText().trim();
        String djo;
        String dadosForm = null;
        String tribunal ;
        String comarca ;
        String vara ;
        String rua;
        String cep ;
        String bairro ;
        String cidade;
        String uf ;
        String conferido;
        Integer bacenN;

        bacenN= Integer.parseInt(bacen);
        bacen="'"+bacenN+"'";
        try {
            dadosForm=  carregaFormBacen(bacen);
        } catch (SQLException ex) {
//            Logger.getLogger(FrmBuscaEndereco.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (dadosForm==null) {
            JOptionPane.showMessageDialog(rootPane, "Favor digitar um número de BACEN válido. ");
        }else{

            String d[]=dadosForm.split("&");
            djo = d[0];
            tribunal = d[1];
            comarca = d[2];
            vara = d[3];
            rua = d[4];
            cep = d[5];
            bairro = d[6];
            cidade = d[7];
            uf = d[8];
            conferido = d[9];

            //txtBacen.setText("a ");
            txtDjo.setText("  " +djo);
            txtTribunal.setText("  " +tribunal);
            txtComarca.setText("  " +comarca);
            txtVara.setText("  " +vara);
            txtRua.setText("  " +rua);
            txtCep.setText("  " +cep);
            txtBairro.setText("  " +bairro);
            txtCidade.setText("  " +cidade);
            txtUf.setText("  " +uf);
            if ("0".equals(conferido)) {
                rbConferido.setSelected(false);
            }else{
                rbConferido.setSelected(true);
            }
    }//GEN-LAST:event_btnBuscar1ActionPerformed
    }
    

 
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 
        String teste;
        teste=rbConferido.getText();        
        System.out.println(rbConferido.getText());        
        
        if (!rbConferido.isSelected()) {
            JOptionPane.showMessageDialog(rootPane, "Favor Conferir o Endereço.");            
        }else{
            
//            
//            Oficio oficio = new Oficio();
// 
// oficio.setEndereco(txtTribunal.getText() +"\n" +txtComarca.getText() +"\n" +txtVara.getText()+
//         "\n" +txtRua.getText() +"\n" +txtBairro.getText()+"\n" +txtCep.getText() 
// +"\n" +txtCidade.getText()+" " +txtUf.getText()); 
//        System.out.println(oficio.getEndereco());            
//   
        
        //Envia os dados para o from princiopal
        frmprincipal.carregaEndereco(txtTribunal.getText() +"\n" +txtComarca.getText() +"\n" +txtVara.getText()+
         "\n" +txtRua.getText() +"\n" +txtBairro.getText()+"\n" +txtCep.getText() 
 +"\n" +txtCidade.getText()+" " +txtUf.getText()); 
    
        
        this.hide();
        }  
        
        
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBoxUFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxUFMouseClicked
        // TODO add your handling code here:
      
        
        
        
    }//GEN-LAST:event_jComboBoxUFMouseClicked

    private void jComboBoxUFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBoxUFPropertyChange
        // TODO add your handling code here:
  
       
    }//GEN-LAST:event_jComboBoxUFPropertyChange

    private void txtUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUfActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void jButtonBuscaCEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaCEPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscaCEPActionPerformed
    
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
            java.util.logging.Logger.getLogger(FrmBuscaEndereco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaEndereco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaEndereco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBuscaEndereco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmBuscaEndereco().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonBuscaCEP;
    private javax.swing.JComboBox jComboBoxComarca;
    private javax.swing.JComboBox jComboBoxOrgao;
    private javax.swing.JComboBox jComboBoxUF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton rbConferido;
    private javax.swing.JTextField txtBacen;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtComarca;
    private javax.swing.JTextField txtDjo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtRua;
    private javax.swing.JTextField txtTribunal;
    private javax.swing.JTextField txtUf;
    private javax.swing.JTextField txtVara;
    // End of variables declaration//GEN-END:variables

public void listarUfs() {   
    try {
        Connection cn = (Connection) new Conexao().conectar();
        String sqlBuscaDados = "SELECT DISTINCT UF FROM bdjudicial.endereco_orgaos where UF <> \"\" order by UF;";       
        
        java.sql.Statement stm = cn.createStatement(); 
        ResultSet rs = stm.executeQuery(sqlBuscaDados); 
        
         while (rs.next()) {                
         //  System.out.println(rs.getString("UF")); 
            jComboBoxUF.addItem(rs.getString("UF")); 
        }             
    } catch (Exception e) {
        
    }
}

public void listarComarcas(String uf) { 
      try {
        Connection cn = (Connection) new Conexao().conectar();
        String sqlBuscaDados = "SELECT DISTINCT COMARCA FROM bdjudicial.endereco_orgaos where UF='"+uf+"' AND UF <> \"\"   order by COMARCA;"; 
        
       
        
java.sql.Statement stm = cn.createStatement(); 
        ResultSet rs = stm.executeQuery(sqlBuscaDados); 
        
         while (rs.next()) {               
         //  System.out.println(rs.getString("UF"));            
                 jComboBoxComarca.addItem(rs.getString("COMARCA"));        
        }             
    } catch (Exception e) {
    } 
}

public void listarOrgaos(String uf,String comarca) { 
      try {
        Connection cn = (Connection) new Conexao().conectar();
        String sqlBuscaDados = "SELECT DISTINCT ORGAO FROM bdjudicial.endereco_orgaos where COMARCA='"+comarca+"' AND COMARCA <> \"\" AND UF='"+uf+"' AND UF <> \"\" order by ORGAO;";    
       
        
        java.sql.Statement stm = cn.createStatement(); 
        ResultSet rs = stm.executeQuery(sqlBuscaDados); 
        
         while (rs.next()) {               
        //   System.out.println(rs.getString("ORGAO"));            
                 jComboBoxOrgao.addItem(rs.getString("ORGAO"));        
        }             
    } catch (Exception e) {
        e.printStackTrace();
    } 
}


private void limpaDados(){
        txtDjo.setText("  0");
        txtBacen.setText("  0");
        txtTribunal.setText("");
        txtComarca.setText("");
        txtVara.setText("");
        txtRua.setText("");
        txtCep.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtUf.setText("");
        rbConferido.setSelected(false);
        jComboBoxUF.setSelectedItem("ESCOLHA");
        jComboBoxComarca.setSelectedItem("ESCOLHA");
        jComboBoxOrgao.setSelectedItem("ESCOLHA");
}


public MaskFormatter Mascara(String Mascara){
        MaskFormatter F_Mascara = new MaskFormatter();
        try{
            F_Mascara.setMask(Mascara); //Atribui a mascara
            F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento 
            F_Mascara.setValidCharacters("0123456789");
        }
        catch (Exception excecao) {
        excecao.printStackTrace();
        } 
        return F_Mascara;
 }



}
