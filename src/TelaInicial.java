import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import Model.Funcionario;
import Repository.FuncionarioRepository;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaInicial extends JFrame implements ActionListener {
    JFrame frame = new JFrame("Cadastro de Funcionário");
    JPanel painel = new JPanel();
    JLabel lTitulo = new JLabel();
    JLabel lNome = new JLabel();
    JLabel lCpf = new JLabel();
    JTextArea tNome = new JTextArea();
    JTextArea tCpf = new JTextArea();
    JButton bSalvar = new JButton();
    JButton bExcluir = new JButton();
    JButton bConsultar = new JButton();
    Funcionario funcionario = new Funcionario();
    FuncionarioRepository repository = new FuncionarioRepository();
    JTable tabela = new JTable();
    DefaultTableModel model = new DefaultTableModel();

    public void telaCadastro() {
        frame.setSize(450, 350);
        painel.setSize(450, 350);
        painel.setLayout(null);

        lTitulo.setText("Cadastro de funcionários");
        lTitulo.setBounds(150, 20, 150, 20);

        lNome.setText("Nome:");
        lNome.setBounds(5, 55, 50, 30);
        tNome.setBounds(60, 60, 150, 20);

        lCpf.setText("CPF:");
        lCpf.setBounds(250, 55, 50, 30);
        tCpf.setBounds(290, 60, 120, 20);

        bSalvar.setText("Salvar");
        bSalvar.setBounds(70, 120, 80, 30);
        bSalvar.addActionListener(this);

        bConsultar.setText("Consultar");
        bConsultar.setBounds(160, 120, 100, 30);
        bConsultar.addActionListener(new consultaAction());

        bExcluir.setText("Excluir");
        bExcluir.setBounds(270, 120, 100, 30);
        bExcluir.addActionListener(new excluirAction() {

        });

        tabela.setBounds(65, 200, 310, 150);

        frame.setVisible(true);
        frame.add(painel);
        painel.add(lTitulo);
        painel.add(lNome);
        painel.add(lCpf);
        painel.add(tNome);
        painel.add(tCpf);
        painel.add(bSalvar);
        painel.add(bConsultar);
        painel.add(bExcluir);
        painel.add(tabela);
    }

    @Override // Sobrescrita - método original sobrescrito
    public void actionPerformed(ActionEvent e) {
        funcionario.setNome(tNome.getText());
        funcionario.setCpf(tCpf.getText());
        repository.cadastrar(funcionario);
        tNome.setText(null);
        tCpf.setText(null);
        new consultaAction().actionPerformed(e);
    }

    public class consultaAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).setRowCount(0);
                ((DefaultTableModel) tabela.getModel()).setColumnCount(0);
            }

            ArrayList<Funcionario> funcionarios = new ArrayList<>();
            try {
                funcionarios = repository.consultar();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            model.addColumn("Id");
            model.addColumn("Nome");
            model.addColumn("CPF");
            model.addRow(new Object[] { "ID", "Nome", "CPF" });

            for (int i = 0; i < funcionarios.size(); i++) {
                funcionario = funcionarios.get(i);
                model.addRow(new Object[] { funcionario.getId(), funcionario.getNome(), funcionario.getCpf() });
            }
            tabela.setModel(model);
        }
    }

    public class excluirAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int linhaSelecionada = tabela.getSelectedRow();

            try {
                repository.excluir((int) tabela.getValueAt(tabela.getSelectedRow(), 0));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            model.removeRow(linhaSelecionada);
        }
    }

}