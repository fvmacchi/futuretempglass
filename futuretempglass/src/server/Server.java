package server;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import server.handlers.OrderHandler;
import storage.database.DBInventoryLibrary;
import storage.database.DBItemLibrary;
import storage.database.DBOrderLibrary;
import storage.database.DBProductionStepsLibrary;
import ui.views.Window;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.sun.net.httpserver.HttpServer;

import core.Application;

class Server extends Window implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int PORT = 8080;

	private static boolean stop = false;

	private JButton stopServerButton;

	private JTextArea textArea;
	
	private HttpServer server;

	private void addHandlers()
	{
		server.createContext(OrderHandler.getContext(), new OrderHandler());
	}
	
	private void addLibraries()
	{
		Application.inventoryLibrary = new DBInventoryLibrary();
		Application.itemLibrary = new DBItemLibrary();
		Application.orderLibrary = new DBOrderLibrary();
		Application.productionStepsLibrary = new DBProductionStepsLibrary();
	}
	
	public static String getLocalIp()
	{
		String ip = null;
		try
		{
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			while (interfaces.hasMoreElements())
			{
				NetworkInterface iface = interfaces.nextElement();
				// filters out 127.0.0.1 and inactive interfaces
				if(iface.isLoopback() || !iface.isUp())
					continue;

				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				while (addresses.hasMoreElements())
				{
					InetAddress addr = addresses.nextElement();
					ip = addr.getHostAddress();
					System.out.println(iface.getDisplayName() + " " + ip);
				}
			}
		}
		catch(SocketException e)
		{
			throw new RuntimeException(e);
		}
		return ip;
	}

	public Server() throws Exception
	{
		super(null);
		setTitle(getLocalIp());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		textArea = new JTextArea();
		getContentPane().add(textArea, "2, 2, fill, fill");

		stopServerButton = new JButton("Stop Server");
		stopServerButton.addMouseListener(this);
		getContentPane().add(stopServerButton, "2, 4");
		pack();
		setVisible(true);

		addLibraries();
		server = HttpServer.create(new InetSocketAddress(PORT), 0);
		addHandlers();
		server.start();
		while (!stop)
		{
			Thread.sleep(1000);
		}
		server.stop(0);
		dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource().equals(stopServerButton))
		{
			stop = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception
	{/*
		Application.itemLibrary = new ServerItemLibrary();
		Application.productionStepsLibrary = new ServerProductionStepsLibrary();
*/
		new Server();

		return;
	}
}