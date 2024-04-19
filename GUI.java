import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Queue;

enum UpdateTypes {
    Banco,
    Levantados,
    Barbeiros
}

public class GUI extends JFrame {

    
    private JTable TableBarbeiros;
    private JTable TableLevantados;
    private JTable TableBanco;

    private JLabel LabelLevantados;
    private JLabel LabelBanco;

    GUI() {
        this._build();

        DefaultTableModel tbbarbeiros = new DefaultTableModel();
        TableBarbeiros = new JTable( tbbarbeiros );
        tbbarbeiros.addColumn("Nome");
        tbbarbeiros.addColumn("Fazendo");
        tbbarbeiros.addColumn("Cliente");

        tbbarbeiros.addRow(new Object[]{ "Barbeiro 1", "Desconhecido", "Sem cliente" });
        tbbarbeiros.addRow(new Object[]{ "Barbeiro 2", "Desconhecido", "Sem cliente" });
        tbbarbeiros.addRow(new Object[]{ "Barbeiro 3", "Desconhecido", "Sem cliente" });

        DefaultTableModel tbclient = new DefaultTableModel();
        tbclient.addColumn("Posição");
        tbclient.addColumn("Nome");
       
        DefaultTableModel tbclient2 = new DefaultTableModel();
        tbclient2.addColumn("Posição");
        tbclient2.addColumn("Nome");

        TableBanco = new JTable(tbclient);
        TableLevantados = new JTable(tbclient2);

        TableBanco.getColumnModel().getColumn(0).setMaxWidth(80);
        TableLevantados.getColumnModel().getColumn(0).setMaxWidth(80);

        TableBanco.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        TableLevantados.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JScrollPane scroll1 = new JScrollPane(TableLevantados);
        JScrollPane scroll2 = new JScrollPane(TableBanco);
        JScrollPane scroll3 = new JScrollPane(TableBarbeiros);


        JPanel painel1 = new JPanel();
        JPanel painel2 = new JPanel();
        JPanel painel3 = new JPanel();

        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS));
        painel2.setLayout(new BoxLayout(painel2, BoxLayout.Y_AXIS));
        painel3.setLayout(new BoxLayout(painel3, BoxLayout.Y_AXIS));

        painel1.setSize(100, 1000);
        JPanel painel4 = new JPanel( new GridLayout (0, 3) );

        LabelLevantados = new JLabel("Levantados (0)");
        LabelBanco= new JLabel("Banco (0)");

        painel1.add(LabelLevantados);
        painel1.add(scroll1);

        painel2.add( LabelBanco );
        painel2.add(scroll2);

        painel3.add(new JLabel("Barbeiros"));
        painel3.add(scroll3);

        painel4.add( painel1 );
        painel4.add( painel2 );
        painel4.add( painel3 );

        getContentPane().add(painel4, BorderLayout.CENTER);

        setVisible(true);
    }

    private void _build() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize( 800 , 600);
        setTitle("Baita interface gráfica ");
        setResizable(false);  
    }

    private void setClientesRows ( DefaultTableModel model, Queue<Cliente> cl ) {
        model.setRowCount(0);

        int i = 0;
        for (Cliente c : cl) {
            model.addRow(new Object[] {
                    i++, "Cliente " + c.getNome()
            });
        }      
    }
    public void SetLevatados(Queue<Cliente> cl) {
        DefaultTableModel model = (DefaultTableModel) TableLevantados.getModel();

        LabelLevantados.setText("Levatados (" + cl.size() + ")");

        setClientesRows(model, cl);
    };

    public  void SetBanco( Queue<Cliente> cl ) {
        DefaultTableModel model = (DefaultTableModel) TableBanco.getModel();

        LabelBanco.setText("Banco (" + cl.size() + ")");
        
        setClientesRows(model, cl);
    }

    public void SetBarbeiros( ArrayList<Barbeiro> bs  ){
        DefaultTableModel model = (DefaultTableModel) TableBarbeiros.getModel();
        
        
        int i = 0;
        for (Barbeiro b : bs) {
            model.setValueAt(b.getStateName(), i, 1);
            model.setValueAt(b.getCurrentClienteName(), i++, 2);
        }
    };
}
