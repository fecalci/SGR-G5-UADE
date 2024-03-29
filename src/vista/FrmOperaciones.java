package vista;

import api.Verificaciones;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import static javax.swing.JOptionPane.showMessageDialog;

public class FrmOperaciones extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlTitulo;
    private JTabbedPane pnlTabPanelOps;
    private JPanel pnlCHP;
    private JButton JBCHP;
    private JPanel pnlCHT;
    private JPanel pnlCCC;
    private JPanel pnlPB;
    private JPanel pnlTC;
    private JPanel pnlPST;
    private JComboBox comboBoxCDCPST;
    private JComboBox comboBEPST;
    private JLabel JLITPST;
    private JLabel JLTasaPST;
    private JLabel JLTDAPST;
    private JLabel JLCDCPST;
    private JSpinner spinnerITPST;
    private JLabel JLTitulo;
    private JLabel JLLogo;
    private JLabel JLEncabezadoPST;
    private JLabel JLEncabezadoTC;
    private JLabel JLBEPST;
    private JTextField TFFDAPST;
    private JLabel JLSistemaPST;
    private JComboBox comboSistemaPST;
    private JTextField TFNDTTC;
    private JTextField TFNombreTC;
    private JTextField TFFVTC;
    private JTextField TFCDSTC;
    private JTextField TFNDPPB;
    private JTextField TFFDVPB;
    private JTextField TFCDFPB;
    private JButton JBPB;
    private JComboBox comboBEPB;
    private JTextField TFITCCC;
    private JTextField TFFDVCCC;
    private JSpinner spinnerITCCC;
    private JTextField TFBECHT;
    private JTextField TFFDVCHT;
    private JTextField TFCDFCHT;
    private JButton JBCHT;
    private JComboBox comboCHT;
    private JTextField TFTDDCHP;
    private JTextField TFFDVCHP;
    private JTextField TFCDFCHP;
    private JComboBox comboBECHP;
    private JSpinner spinnerTDDCHP;
    private JSpinner spinnerTDDPST;
    private JButton JBPST;
    private JLabel JLCSPST;
    private JTextField TFCSPST;
    private JComboBox comboBECHT;
    private JTextField TFNDCCHT;
    private JTextField TFNDCCHP;
    private JButton JBCCC;
    private JButton JBTC;
    private Verificaciones verif = new impl.Verificaciones();



    public FrmOperaciones(Window owner, String Title) {
        super(owner, Title);

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch( Exception ex ) {
            System.err.println("Failed to initialize LaF");
        }

        // Define el canvas según swing.
        this.setContentPane(this.pnlPrincipal);
        // Tamaño de la pantalla
        this.setSize(1000, 600);
        // No permite volver a la pantalla anterior hasta cerrar esta
        this.setModal(true);
        this.setResizable(false);
        // Establezco el comportamiento al cerrar la pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Inicia la pantalla centrada
        this.setLocationRelativeTo(null);


        //Action Listener de JButton Cheques Personales
        JBCHP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagCHP = true;
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHP
                Object BECHP = new Object();
                BECHP = comboBECHP.getSelectedItem();

                //Toma el String Numero de Cheque desde el JText Field TFNCCHP y lo transforma en un entero
                String NCCHP;
                int NCCHPint;
                NCCHP = TFNDCCHP.getText();
                if (NCCHP.isEmpty()) {
                    showMessageDialog(null, "El campo Numero de Cheque es mandatorio, por favor ingrese el dato solicitado");
                    DatosCorrectosFlagCHP = false;
                }
                else {
                    if (verif.esnumerico(NCCHP)) {
                        NCCHPint = Integer.parseInt(NCCHP);
                    } else {
                        showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                        DatosCorrectosFlagCHP = false;
                    }
                }

                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHP y lo transforma en un date
                String FDVCHP = "";
                FDVCHP = TFFDVCHP.getText();
                if (verif.fechavalida(FDVCHP)==true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(FDVCHP, formatter);
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(localDate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                        DatosCorrectosFlagCHP = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagCHP = false;
                    }
                }
                else{
                    showMessageDialog(null,"La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagCHP = false;
                }

                //Toma el String CUIT firmante desde el JText Field TFCDFCHT
                String CDFCHP = "";
                CDFCHP = TFCDFCHP.getText();
                if (verif.CUITValido(CDFCHP)){
                }
                else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHP = false;
                }

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDCHP;
                TDDCHP = spinnerTDDCHP.getValue();
                int TDDCHPint;
                TDDCHPint= (Integer) TDDCHP;
                if(TDDCHPint <= 0){
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagCHP = false;
                }
                if(TDDCHPint >=100){
                    showMessageDialog(null,"El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                    DatosCorrectosFlagCHP = false;
                }
                if (DatosCorrectosFlagCHP==true){

                }
            }
        });
        JBCHT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagCHT = true;
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHT
                Object BECHT = new Object();
                BECHT = comboBECHT.getSelectedItem();

                //Toma el String Numero de Cheque desde el JText Field TFNCCHT y lo transforma en un entero
                String NCCHT;
                int NCCHTint;
                NCCHT = TFNDCCHT.getText();
                if (NCCHT.isEmpty()) {
                    showMessageDialog(null, "El campo Numero de Cheque es mandatorio, por favor ingrese el dato solicitado");
                    DatosCorrectosFlagCHT = false;
                } else {
                    if (verif.esnumerico(NCCHT)) {
                        NCCHTint = Integer.parseInt(NCCHT);
                    } else {
                        showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                        DatosCorrectosFlagCHT = false;
                    }
                }
                //Toma el String Fecha de Vencimiento desde el JText Field FDVCHT y lo transforma en un date
                String FDVCHT = "";
                FDVCHT = TFFDVCHT.getText();
                if (verif.fechavalida(FDVCHT) == true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(FDVCHT, formatter);
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(localDate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "El cheque se encuentra vencido");
                        DatosCorrectosFlagCHT = false;
                    }
                    if (comparacionfecha == "Hoy") {
                        showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                        DatosCorrectosFlagCHT = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagCHT = false;
                }
                //Toma el String CUIT firmante desde el JText Field TFCDFCHT
                String CDFCHT = "";
                CDFCHT = TFCDFCHT.getText();
                if (verif.CUITValido(CDFCHT)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagCHT = false;
                }
                if (DatosCorrectosFlagCHT==true){

                }
            }
        });
        JBPB.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       boolean DatosCorrectosFlagPB = true;
                                       //Toma el objeto Banco Emisor desde el Combo Box comboBEPB
                                       Object BEPB = new Object();
                                       BEPB = comboBEPB.getSelectedItem();

                                       //Toma el String Numero de Cheque desde el JText Field TFNDPPB y lo transforma en un entero
                                       String NDPPB;
                                       int NDPPBint;
                                       NDPPB = TFNDPPB.getText();
                                       if (NDPPB.isEmpty()) {
                                           showMessageDialog(null, "El campo Numero de Cheque es mandatorio, por favor ingrese el dato solicitado");
                                           DatosCorrectosFlagPB = false;
                                       } else {
                                           if (verif.esnumerico(NDPPB)) {
                                               NDPPBint = Integer.parseInt(NDPPB);
                                           } else {
                                               showMessageDialog(null, "El campo Numero de Cheque solo admite numeros, no se admiten otros caracteres");
                                               DatosCorrectosFlagPB = false;
                                           }
                                       }
                                       //Toma el String Fecha de Vencimiento desde el JText Field FDVPB y lo transforma en un date
                                       String FDVPB = "";
                                       FDVPB = TFFDVPB.getText();
                                       if (verif.fechavalida(FDVPB) == true) {
                                           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                           LocalDate localDate = LocalDate.parse(FDVPB, formatter);
                                           //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                                           // canje o vender un cheque ya vencido.
                                           String comparacionfecha = verif.fechavshoy(localDate);
                                           if (comparacionfecha == "Menor") {
                                               showMessageDialog(null, "El cheque se encuentra vencido");
                                               DatosCorrectosFlagPB = false;
                                           }
                                           if (comparacionfecha == "Hoy") {
                                               showMessageDialog(null, "La fecha de vencimiento del cheque es hoy");
                                               DatosCorrectosFlagPB = false;
                                           }
                                       } else {
                                           showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                                           DatosCorrectosFlagPB = false;
                                       }
                                       //Toma el String CUIT firmante desde el JText Field TFCDFPB
                                       String CDFPB = "";
                                       CDFPB = TFCDFPB.getText();
                                       if (verif.CUITValido(CDFPB)) {
                                       } else {
                                           showMessageDialog(null, "El CUIT ingresado es invalido");
                                           DatosCorrectosFlagPB = false;
                                       }
                                       if (DatosCorrectosFlagPB == true) {

                                       }
                                   }
                               });





        // El actionListener de Prestamos
        JBPST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean DatosCorrectosFlagPST = true;
                //Toma el objeto Banco Emisor desde el Combo Box comboBECHP
                Object BEPST = new Object();
                BEPST = comboBEPST.getSelectedItem();

                //Toma el Entero Tasa desde el JSpinner spinnerTDDCHP
                Object TDDPST;
                TDDPST = spinnerTDDPST.getValue();
                int TDDPSTint;
                TDDPSTint = (Integer) TDDPST;
                if (TDDPSTint <= 0) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento menor o igual a 0");
                    DatosCorrectosFlagPST = false;
                }
                if (TDDPSTint >= 100) {
                    showMessageDialog(null, "El cheque no puede ser vendido con una tasa de descuento superior al 99%");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el importe total
                Object ITPST;
                ITPST = spinnerITPST.getValue();
                int ITFloatPST;
                ITFloatPST = (Integer) ITPST;
                if (ITFloatPST <= 0) {
                    showMessageDialog(null, "El importe total del prestamo no puede ser igual o menor que 0");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el String Fecha de Acreeditación desde el JText Field FDFAPST y lo transforma en un date
                String FDAPST = "";
                FDAPST = TFFDAPST.getText();
                if (verif.fechavalida(FDAPST) == true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(FDAPST, formatter);
                    //Compara la fecha ingresada con la fecha actual ya que no tendria sentido vender un cheque el dia de su
                    // canje o vender un cheque ya vencido.
                    String comparacionfecha = verif.fechavshoy(localDate);
                    if (comparacionfecha == "Menor") {
                        showMessageDialog(null, "La fecha de acreditación no es valida");
                        DatosCorrectosFlagPST = false;
                    }
                } else {
                    showMessageDialog(null, "La fecha ingresada no cumple con el formato solicitado");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el String CUIT Socio desde el JText Field TFCSPST
                String CSPST = "";
                CSPST = TFCSPST.getText();
                if (verif.CUITValido(CSPST)) {
                } else {
                    showMessageDialog(null, "El CUIT ingresado es invalido");
                    DatosCorrectosFlagPST = false;
                }

                //Toma el objeto Sistema desde el Combo Box comboSistemaPST
                Object Sistema_PST = new Object();
                Sistema_PST = comboSistemaPST.getSelectedItem();

                //Toma el objeto Cantidad de cuotas desde el Combo Box comboSistemaPST
                Object cantidadCuotas = new Object();
                cantidadCuotas = comboBoxCDCPST.getSelectedItem();
            }
        });
    }
}
