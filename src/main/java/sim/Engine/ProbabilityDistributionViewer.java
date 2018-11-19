package sim.Engine;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.jfree.ui.RefineryUtilities;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ProbabilityDistributionViewer {

    private JFrame frame;
    private JTextField param1;
    private JTextField param2;
    private JTextField numOfSamples;
    private JTextField numOfFile;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ProbabilityDistributionViewer window = new ProbabilityDistributionViewer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ProbabilityDistributionViewer() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblSelectDistribution = new JLabel("Select Distribution");
        lblSelectDistribution.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelectDistribution.setBounds(29, 11, 114, 22);
        frame.getContentPane().add(lblSelectDistribution);

        /*JComboBox distro = new JComboBox();
        distro.setModel( new DefaultComboBoxModel(Distribution.values() ) );
        distro.setBounds(208, 11, 86, 20);
        frame.getContentPane().add(distro);*/

        JLabel lblSelecttParam = new JLabel("Select 1st Param");
        lblSelecttParam.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelecttParam.setBounds(29, 44, 114, 22);
        frame.getContentPane().add(lblSelecttParam);

        param1 = new JTextField();
        param1.setText("1");
        param1.setBounds(208, 42, 86, 20);
        frame.getContentPane().add(param1);
        param1.setColumns(10);

        JLabel lblSelectndParam = new JLabel("Select 2nd Param");
        lblSelectndParam.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelectndParam.setBounds(29, 78, 114, 22);
        frame.getContentPane().add(lblSelectndParam);

        param2 = new JTextField();
        param2.setText("1");
        param2.setColumns(10);
        param2.setBounds(208, 76, 86, 20);
        frame.getContentPane().add(param2);

        JLabel lblSelectNumberOf = new JLabel("Select number of samples");
        lblSelectNumberOf.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelectNumberOf.setBounds(29, 112, 155, 22);
        frame.getContentPane().add(lblSelectNumberOf);

        numOfSamples = new JTextField();
        numOfSamples.setText("1000");
        numOfSamples.setColumns(10);
        numOfSamples.setBounds(208, 110, 86, 20);
        frame.getContentPane().add(numOfSamples);

        JLabel lblSelectNumberOf_1 = new JLabel("Select number of file");
        lblSelectNumberOf_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelectNumberOf_1.setBounds(29, 144, 155, 22);
        frame.getContentPane().add(lblSelectNumberOf_1);

        numOfFile = new JTextField();
        numOfFile.setText("100");
        numOfFile.setColumns(10);
        numOfFile.setBounds(208, 142, 86, 20);
        frame.getContentPane().add(numOfFile);

        JButton draw = new JButton("Draw");
        draw.addActionListener( (e) -> {
            try {

                //Distribution distribution = (Distribution) distro.getSelectedItem();

                Double p1 = Double.parseDouble( param1.getText() );
                Double p2 = Double.parseDouble( param2.getText() );

                Integer numFile = Integer.parseInt( numOfFile.getText() );
                Integer numSample = Integer.parseInt( numOfSamples.getText() );

                if( numFile < 0 )
                    throw new Exception( "Number of file can't be less then 0" );
                else if( numSample < 0 )
                    throw new Exception( "Number of sample can't be less then 0" );
                else if( p1 < 0 )
                    throw new Exception( "Parameter 1 can't be less then 0" );
                else if( p2 < 0 )
                    throw new Exception( "Parameter 2 can't be less then 0" );


                SampleValueHistogram chart = new SampleValueHistogram( "Download frequency" );
                chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
                frame.setVisible( false );

            }
            catch( NumberFormatException ex ) {

                JOptionPane.showMessageDialog(frame, "Please enter valid number in textbox", "Number format error", JOptionPane.ERROR_MESSAGE, null );
            }
            catch( Exception ex ) {

                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null );
            }
        });

        draw.setBounds(205, 173, 91, 23);
        frame.getContentPane().add(draw);
        RefineryUtilities.centerFrameOnScreen(frame);
    }
}

