package be.marain.jframes;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import be.marain.classes.Booking;
import be.marain.dao.BookingDAO;
import be.marain.dao.EcoleSkiConnection;
import be.marain.tableModels.BookingTableModel;

public class BookingsJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private BookingDAO bookingDAO = new BookingDAO(EcoleSkiConnection.getInstance());
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingsJFrame frame = new BookingsJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookingsJFrame() {
		List<Booking> bookings = new ArrayList<Booking>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1310, 781);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(5, 5, 1293, 739);
		tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		tablePanel.setLayout(null);
		
		BookingTableModel model = new BookingTableModel(bookings);
		
		JTable table = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(623, 20, 650, 699);
		
		tablePanel.add(scrollPane);
		contentPane.add(tablePanel);
	}

}
