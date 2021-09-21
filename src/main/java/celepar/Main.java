package celepar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Main {

	static String connectionUrl = "jdbc:jtds:sqlserver://10.15.60.80:1433/sia;databaseName=sia;user=sflprod;password=prodsfl";
	static final Integer ALTERAR = 0;
	static final Integer INCLUIR = 1;
	static Integer estado = ALTERAR;
	JTextArea areaDescrOcorr = new JTextArea();
	JFormattedTextField ordemExibicao = new JFormattedTextField();
	JFormattedTextField codCadInf = new JFormattedTextField();
	JFormattedTextField artigo = new JFormattedTextField();
	JFormattedTextField itemParagrafoArtigo = new JFormattedTextField();
	JComboBox<TipoOcorrDetalhamento> tipoOcorrDetalhamento = new JComboBox<TipoOcorrDetalhamento>();
	JComboBox<TipoOcorr> grupoOcor = new JComboBox<TipoOcorr>();

	JTextArea areaDescrEnq = new JTextArea();

	public void janelaCfg() {
		JFrame f = new JFrame();// creating instance of JFrame
		f.setSize(400, 690);// 400 width and 500 height
		f.setLayout(new BorderLayout());// using no layout managers
		
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize(); 
		Dimension dw = f.getSize(); 
		f.setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
		
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(f.getContentPane());
		f.setTitle("Enquadramentos");
		f.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		areaDescrEnq.setBounds(10, 410, 360, 200);

		f.add(areaDescrEnq);

		tipoOcorrDetalhamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings({ "rawtypes", "unused" })
				JComboBox cb = (JComboBox) e.getSource();
				@SuppressWarnings("unused")
				TipoOcorrDetalhamento itemSelected = (TipoOcorrDetalhamento) cb.getSelectedItem();
				if (itemSelected != null) {
					areaDescrOcorr.setText(itemSelected.getDescrOcorrencia());
					areaDescrOcorr.setLineWrap(true);
					ordemExibicao.setText(String.valueOf(itemSelected.getOrdemExibicao()));
					itemParagrafoArtigo.setText(itemSelected.getItemParagArtigo());
					areaDescrEnq.setLineWrap(true);
					areaDescrEnq.setText(itemSelected.getDescEnq());
					if (itemSelected.getDescEnq() != null && itemSelected.getDescEnq().length() > 0)
						areaDescrEnq.setCaretPosition(areaDescrEnq.getDocument().getLength() - 1);

					if (itemSelected.getCodCadInf() != null)
						codCadInf.setText(String.valueOf(itemSelected.getCodCadInf()));
					else
						codCadInf.setText("");

					if (itemSelected.getArtigo() != null)
						artigo.setText(String.valueOf(itemSelected.getArtigo()));
					else
						artigo.setText("");

				}

			}
		});

		grupoOcor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings({ "rawtypes", "unused" })
					JComboBox cb = (JComboBox) e.getSource();
					@SuppressWarnings("unused")
					TipoOcorr itemSelected = (TipoOcorr) cb.getSelectedItem();
					Connection con = DriverManager.getConnection(connectionUrl);
					// Statement stmt = con.createStatement();
					tipoOcorrDetalhamento.removeAllItems();
					String sql = "SELECT tpOcor.codGrupoOcor\r\n" + "	,tpOcor.codOcorrencia\r\n"
							+ "	,tpOcor.ordemExibicao\r\n" + "	,tpOcor.descrOcorrencia\r\n"
							+ "	,tpOcor.codCadInf\r\n" + "	,enq.artigo\r\n" + "	,enq.itemParagArtigo\r\n"
							+ "	,enq.DescrEnquad\r\n" + "FROM TB_TipoOcorrencia tpOcor\r\n"
							+ "left JOIN TB_Enquadramento enq ON enq.CodGrupoOcor = tpOcor.CodGrupoOcor\r\n"
							+ "	AND enq.CodOcorrencia = tpOcor.CodOcorrencia\r\n"
							+ "WHERE tpOcor.Ativo = 'S' AND tpOcor.CodOcorrencia <> 0 \r\n"
							+ "	AND tpOcor.CodGrupoOcor = ? ORDER BY  tpOcor.ordemExibicao\r\n";

					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setInt(1, itemSelected.getCodGrupoOcor());
					stmt.execute();
					ResultSet rs = stmt.getResultSet();
					while (rs.next()) {
						TipoOcorrDetalhamento item = new TipoOcorrDetalhamento(rs.getInt(3) + " - " + rs.getString(4)) {
							public String toString() {
								return this.getDescrOcorrVis();
							}
							
						};
						item.setDescrOcorrVis(rs.getInt(3) + " - " + rs.getString(4));
						item.setCodGrupoOcor(rs.getInt(1));
						item.setDescrOcorrencia(rs.getString(4));
						if (rs.getString(8) != null)
							item.setDescEnq(rs.getString(8));
						item.setCodOcorrencia(rs.getInt(2));
						if (rs.getString(6) != null)
							item.setArtigo(rs.getInt(6));
						if (rs.getString(7) != null)
							item.setItemParagArtigo(rs.getString(7));
						item.setOrdemExibicao(rs.getInt(3));
						if (rs.getString(5) != null)
							item.setCodCadInf(rs.getInt(5));

						tipoOcorrDetalhamento.addItem(item);
					}
					con.close();

				} catch (Exception e1) {
					System.out.println();
				}
			}
		});
		try {
			Connection con = DriverManager.getConnection(connectionUrl);
			Statement stmt = con.createStatement();
			String sql = "SELECT CodGrupoOcor,  DescrOcorrencia\r\n" + "FROM TB_TipoOcorrencia\r\n"
					+ "WHERE Ativo = 'S'\r\n" + "and CodOcorrencia = 0 order by DescrOcorrencia";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TipoOcorr item = new TipoOcorr(rs.getString(2)) {
					public String toString() {
						return this.getDescrOcorrencia();
					}
				};
				item.setCodGrupoOcor(rs.getInt(1));
				item.setDescrOcorrencia(rs.getString(2));
				grupoOcor.addItem(item);
			}
			con.close();
		} catch (Exception e) {
			areaDescrOcorr.setText(e.getMessage());

		}
		grupoOcor.setBounds(10, 10, 360, 40);
		f.add(grupoOcor);
		tipoOcorrDetalhamento.setBounds(10, 50, 360, 40);
		f.add(tipoOcorrDetalhamento);
		areaDescrOcorr.setBounds(10, 90, 360, 200);
		f.add(areaDescrOcorr);
		ordemExibicao.setBounds(10, 290, 30, 40);
		f.add(ordemExibicao);
		codCadInf.setBounds(10, 330, 30, 40);
		f.add(codCadInf);
		artigo.setBounds(10, 370, 30, 40);
		f.add(artigo);
		itemParagrafoArtigo.setBounds(40, 370, 80, 40);
		f.add(itemParagrafoArtigo);

		JButton novo = new JButton("+");// creating instance of JButton
		novo.setBounds(100, 610, 100, 40);// x axis, y axis, width, height
		novo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("+")) {
					tipoOcorrDetalhamento.removeAllItems();
					artigo.setText("");
					areaDescrOcorr.setText("");
					ordemExibicao.setText("");
					codCadInf.setText("");
					artigo.setText("");
					itemParagrafoArtigo.setText("");
					areaDescrEnq.setText("");
					estado = INCLUIR;
				}

			}
		});
		f.add(novo);

		JButton b = new JButton("Salvar");// creating instance of JButton
		b.setBounds(200, 610, 100, 40);// x axis, y axis, width, height
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Salvar")) {
					if (estado.equals(ALTERAR)) {

						try {

							String codCadInfo = codCadInf.getText().equals("") ? "NULL" : codCadInf.getText();
							String ipa = itemParagrafoArtigo.getText().equals("") ? "NULL"
									: "'" + itemParagrafoArtigo.getText() + "'";
							Connection con = DriverManager.getConnection(connectionUrl);
							TipoOcorrDetalhamento itemSelected = (TipoOcorrDetalhamento) tipoOcorrDetalhamento
									.getSelectedItem();
							String sql =

									"EXEC dbo.stp_alterar_tipoOcorrenciaComConsEnq " + itemSelected.getCodGrupoOcor()
											+ "	," + itemSelected.getCodOcorrencia() + ",'" + areaDescrOcorr.getText()
											+ "'," + codCadInfo + "," + ordemExibicao.getText() + "," + artigo.getText()
											+ "," + ipa;

							Statement stmt = con.createStatement();
							stmt.execute(sql);

							con.close();
						} catch (Exception e1) {
							areaDescrOcorr.setText(e1.getMessage());
							return;
						}

						for (ActionListener al : grupoOcor.getActionListeners()) {
							al.actionPerformed(new ActionEvent(grupoOcor, -1, ""));
							break;
						}

					} else if (estado.equals(INCLUIR)) {

						try {

							String codCadInfo = codCadInf.getText().equals("") ? "NULL" : codCadInf.getText();
							String ipa = itemParagrafoArtigo.getText().equals("") ? "NULL"
									: "'" + itemParagrafoArtigo.getText() + "'";
							Connection con = DriverManager.getConnection(connectionUrl);
							TipoOcorr itemSelected = (TipoOcorr) grupoOcor.getSelectedItem();
							String sql =

									"EXEC dbo.stp_inclui_tipoOcorrenciaComConsEnq " + itemSelected.getCodGrupoOcor()
											+ "	,'" + areaDescrOcorr.getText() + "'" + "	," + codCadInfo + "	,"
											+ ordemExibicao.getText() + "	," + artigo.getText() + "	," + ipa;

							Statement stmt = con.createStatement();
							stmt.execute(sql);

							con.close();
						} catch (Exception e1) {
							areaDescrOcorr.setText(e1.getMessage());
							return;
						}

						for (ActionListener al : grupoOcor.getActionListeners()) {
							al.actionPerformed(new ActionEvent(grupoOcor, -1, ""));
							break;
						}

						estado = ALTERAR;
					}

				}

			}
		});

		f.add(b);// adding button in JFrame

		
		f.setVisible(true);// making the frame visible

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

			}
		});

	}

	public static void main(String[] args) throws IOException {
		  if(args.length == 0) {
		       Runtime.getRuntime().exec("java -jar " + (new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getAbsolutePath() + " cmd");
		    } else {
		    	new Main().janelaCfg();
		    }
		
		//new Main().janelaCfg();
		

	}

}
