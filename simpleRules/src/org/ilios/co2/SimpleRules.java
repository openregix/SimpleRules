package org.ilios.co2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class SimpleRules {
	
    public static void main(String[] args) {
        try {
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

            kbuilder.add( ResourceFactory.newClassPathResource( "Regles-Fiscales.drl",
                                                                        SimpleRules.class ),
                                  ResourceType.DRL );
            KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
            kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
            Vector<Vehicle> stock =  new Vector<Vehicle>();;
            loadFacts(stock);
                 
            Co2UI ui = new Co2UI( stock,
                                  new EvalTaxationCallback( kbase ) );
            ui.createAndShowGUI();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        
    }
     private static void loadFacts(Vector<Vehicle> stock) {
         
         
         stock.add( new Vehicle( "TWINGO Access 1.2 60 eco2",
                 130, 5.6 , "essence" ) );
         stock.add( new Vehicle( "TWINGO Authentique 1.2 LEV 16v 75 eco2",
                 119, 5.1 , "essence" ) );
         stock.add( new Vehicle( "TWINGO Authentique dCi 65 eco2",
                 113, 4.3 , "essence" ) );
         stock.add( new Vehicle( "TWINGO Dynamique 1.2 16v 75 eco2",
         		135, 5.7  , "essence" ) );
         stock.add( new Vehicle( "TWINGO Dynamique TCe 100 eco2",
                 138, 5.9 , "essence" ) );
         stock.add( new Vehicle( "TWINGO Dynamique dCi 85 eco2",
                 104, 4.0 , " diesel" ) );
         stock.add( new Vehicle( "TWINGO Renault Sport 1.6 16v 133",
                 165, 7.0 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO CAMPUS Authentique 3 portes 1.2 60 Eco2",
                 140, 5.9 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO CAMPUS Authentique 5p 1.2 16V 75 eco2",
         		137, 5.9 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO CAMPUS Authentique Dynamique 5p dCi 65 eco2",
         		115, 4.4 , "diesel" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO CAMPUS Campus.Com 5p dCi 85 eco2",
         		111, 4.2 , "diesel" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Authentique 3p 1.2 16v 75 eco2",
         		139, 5.9 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Authentique 3p dCi 70 eco2",
         		139, 5.9 , "diesel" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Expression 3p 1.2 16v 75 eco2",
         		139, 5.9 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Expression 3p 1.2 16v 75 Ethanol eco2",
         		139, 5.9 , "ethanol" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Dynamique 3p dCi 85 eco2",
         		115, 4.3 , "diesel" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Exception 3p Tce 100 eco2",
         		137, 5.8 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Exception 3p Tce 100 eco2",
         		137, 5.8 , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Exception 3p 1.6 16V 128",
         		160,  6.9  , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Exception 3p 1.6 16V 110 BVA",
         		179,  7.5  , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO Exception 3p dCi 105 eco2",
         		123,  4.6  , "diesel" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO GT 5p 1.6 16v 128",
         		160,  6.9  , "essence" ) );
         stock.add( new Vehicle( "NOUVELLE CLIO GT Renault Sport S rie limit e Cup 2.0 16v",
         		195,  8.3  , "essence" ) );
        
     }
     
     public static class Co2UI extends JPanel {

		private static final long serialVersionUID = 1L;

		private JTextArea        output;

        private TableModel       tableModel;

        private EvalTaxationCallback callback;

         public Co2UI(Vector<Vehicle> items,
                          EvalTaxationCallback callback) {
            super( new BorderLayout() );
            this.callback = callback;

            //Create main vertical split panel
            JSplitPane splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
            add( splitPane,
                 BorderLayout.CENTER );

            //create top half of split panel and add to parent
            JPanel topHalf = new JPanel();
            topHalf.setLayout( new BoxLayout( topHalf,
                                              BoxLayout.X_AXIS ) );
            topHalf.setBorder( BorderFactory.createEmptyBorder( 5,
                                                                5,
                                                                0,
                                                                5 ) );
            topHalf.setMinimumSize( new Dimension( 400,
                                                   50 ) );
            topHalf.setPreferredSize( new Dimension( 650,
                                                     250 ) );
            splitPane.add( topHalf );

            //create bottom top half of split panel and add to parent
            JPanel bottomHalf = new JPanel( new BorderLayout() );
            bottomHalf.setMinimumSize( new Dimension( 400,
                                                      50 ) );
            bottomHalf.setPreferredSize( new Dimension( 650,
                                                        300 ) );
            splitPane.add( bottomHalf );

            //Container that list container that shows available store items
            JPanel listContainer = new JPanel( new GridLayout( 1,
                                                               1 ) );
            listContainer.setBorder( BorderFactory.createTitledBorder( "Gamme" ) );
            topHalf.add( listContainer );

            //Create JList for items, add to scroll pane and then add to parent
            // container
            JList list = new JList( items );
            ListSelectionModel listSelectionModel = list.getSelectionModel();
            listSelectionModel.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
            
            //handler adds item to eval taxation
            list.addMouseListener( new ListSelectionHandler() );
            JScrollPane listPane = new JScrollPane( list );
            listContainer.add( listPane );

            JPanel vehicleContainer = new JPanel( new GridLayout( 1,
                                                                1 ) );
            vehicleContainer.setBorder( BorderFactory.createTitledBorder( "V hicules    valuer" ) );
            topHalf.add( vehicleContainer );

            //Container that displays table showing selected items
            tableModel = new TableModel();
            JTable table = new JTable( tableModel );
            
            //handler removes items
            table.addMouseListener( new TableSelectionHandler() );
            ListSelectionModel tableSelectionModel = table.getSelectionModel();
            tableSelectionModel.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
            TableColumnModel tableColumnModel = table.getColumnModel();
            
            //notice we have a custom renderer for each column as both columns
            // point to the same underlying object
            tableColumnModel.getColumn( 0 ).setCellRenderer( new NameRenderer() );
            tableColumnModel.getColumn( 1 ).setCellRenderer( new Co2Renderer() );
            tableColumnModel.getColumn( 2 ).setCellRenderer( new ConsoRenderer() );
            tableColumnModel.getColumn( 3 ).setCellRenderer( new ClasseRenderer() );
            tableColumnModel.getColumn( 4 ).setCellRenderer( new BonusRenderer() );
            tableColumnModel.getColumn( 1 ).setMaxWidth( 50 );
            tableColumnModel.getColumn( 2 ).setMaxWidth( 50 );
            tableColumnModel.getColumn( 3 ).setMaxWidth( 50 );
            tableColumnModel.getColumn( 4 ).setMaxWidth( 50 );
            
            JScrollPane tablePane = new JScrollPane( table );
            tablePane.setPreferredSize( new Dimension( 250,
                                                       100 ) );
            vehicleContainer.add( tablePane );

            //Create panel for Eval button and add to bottomHalf parent
            JPanel checkoutPane = new JPanel();
            JButton button = new JButton( "Fiscalit " );
            button.setVerticalTextPosition( AbstractButton.CENTER );
            button.setHorizontalTextPosition( AbstractButton.LEADING );
            
            //attach handler to assert items into working memory
            button.addMouseListener( new evalButtonHandler() );
            button.setActionCommand( "checkout" );
            checkoutPane.add( button );
            bottomHalf.add( checkoutPane,
                            BorderLayout.NORTH );

            button = new JButton( "RAZ" );
            button.setVerticalTextPosition( AbstractButton.CENTER );
            button.setHorizontalTextPosition( AbstractButton.TRAILING );
            
            //attach handler to assert items into working memory
            button.addMouseListener( new ResetButtonHandler() );
            button.setActionCommand( "reset" );
            checkoutPane.add( button );
            bottomHalf.add( checkoutPane,
                            BorderLayout.NORTH );

            output = new JTextArea( 1,
                                    10 );
            output.setEditable( false );
            JScrollPane outputPane = new JScrollPane( output,
                                                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
            bottomHalf.add( outputPane,
                            BorderLayout.CENTER );

            this.callback.setOutput( this.output );
        }

        /**
         * Create and show the GUI
         *  
         */
        public void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame( "Taxation Demo" );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

            setOpaque( true );
            frame.setContentPane( this );

            //Display the window.
            frame.pack();
            frame.setVisible( true );
        }

        /**
         * Adds the selected item to the table
         */
        private class ListSelectionHandler extends MouseAdapter {
            public void mouseReleased(MouseEvent e) {
                JList jlist = (JList) e.getSource();
                tableModel.addItem( (Vehicle) jlist.getSelectedValue() );
            }
        }

        /**
         * Modifiy the selected item from the table
         */
        private class TableSelectionHandler extends MouseAdapter {
            public void mouseReleased(MouseEvent e) {
                JTable jtable = (JTable) e.getSource();
                TableModel tableModel = (TableModel) jtable.getModel();
                tableModel.removeItem( jtable.getSelectedRow() );
                System.out.println("mouseReleased in TableSelectionHandler");
            }
        }

        /**
         * Calls the referenced callback, passing a the jrame and selected items.
         *  
         */
        private class evalButtonHandler extends MouseAdapter {
            public void mouseReleased(MouseEvent e) {
                JButton button = (JButton) e.getComponent();
                output.setText( null );
                output.append("### Evaluation de la fiscalit  ###\n");
                callback.eval( (JFrame) button.getTopLevelAncestor(),
                                   tableModel.getItems() );
                tableModel.refresh();
            }
        }

        private class ResetButtonHandler extends MouseAdapter {
            public void mouseReleased(MouseEvent e) {
                output.setText( null );
                callback.reload();
                tableModel.clear();
                
                System.out.println( "------ Reset ------" );
            }
        }

      
        private class NameRenderer extends DefaultTableCellRenderer {

			private static final long serialVersionUID = 1L;

			public NameRenderer() {
                super();
            }

            public void setValue(Object object) {
                Vehicle item = (Vehicle) object;
                setText( item.getName() );
            }
        }

     
        private class Co2Renderer extends DefaultTableCellRenderer {

			private static final long serialVersionUID = 1L;

			public Co2Renderer() {
                super();
            }

            public void setValue(Object object) {
                Vehicle item = (Vehicle) object;
                setText( Double.toString( item.getCo2() ) );
            }
        }
        
        private class ClasseRenderer extends DefaultTableCellRenderer {

			private static final long serialVersionUID = 1L;

			public ClasseRenderer() {
                super();
            }

            public void setValue(Object object) {
                Vehicle item = (Vehicle) object;
                setText( item.getClasse() );
            }
        } 
        
    
        private class ConsoRenderer extends DefaultTableCellRenderer {

			private static final long serialVersionUID = 1L;

			public ConsoRenderer() {
                super();
            }

            public void setValue(Object object) {
                Vehicle item = (Vehicle) object;
                setText( Double.toString( item.getConso() ) );
                
            }
        }
        
        private class BonusRenderer extends DefaultTableCellRenderer {

			private static final long serialVersionUID = 1L;

			public BonusRenderer() {
                super();
            }

            public void setValue(Object object) {
                Vehicle item = (Vehicle) object;
                setText( Double.toString( item.getBonus() ) );
                
            }
        }
    }


    private static class TableModel extends AbstractTableModel {

    	private static final long serialVersionUID = 1L;

		private String[]  columnNames = {	"Mod le", 
											"Co2 (g/km)", 
											"Conso (l/km)", 
											"Classe", 
											"Bonus"};

        private ArrayList<Vehicle> items;

        public TableModel() {
            super();
            items = new ArrayList<Vehicle>();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return items.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row,
                                 int col) {
            return items.get( row );
        }

        public Class<?> getColumnClass(int c) {
            return Vehicle.class;
        }

        public void addItem(Vehicle item) {
            items.add( item );
            fireTableRowsInserted( items.size(),
                                   items.size() );
        }

        public void removeItem(int row) {
            items.remove( row );
            fireTableRowsDeleted( row,
                                  row );
        }

        public List<Vehicle> getItems() {
            return items;
        }

        public void clear() {
            int lastRow = items.size();
            items.clear();
            fireTableRowsDeleted( 0,
                                  lastRow );
        }
        
        public void refresh() {
        	int lastRow = items.size();
        	fireTableRowsUpdated(0,
                                  lastRow);
        }
    }


    public static class EvalTaxationCallback {
        KnowledgeBase kbase;
        JTextArea     output;

        public EvalTaxationCallback(KnowledgeBase kbase) {
            this.kbase = kbase;
        }

        public void setOutput(JTextArea output) {
            this.output = output;
        }

        public String eval(JFrame frame, List<Vehicle> items) {
            Gamme gamme = new Gamme();
            
            for ( Vehicle v: items ) {
                gamme.addItem(new Taxation( v, "Fr" )) ;
              
            }
            
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
           
            ksession.setGlobal( "frame",
                                frame );
            
            ksession.setGlobal( "textArea",
                               this.output );                          
                               
            ksession.insert( gamme );
            ksession.fireAllRules();
            return "Eval Done";
            
        }
        
        public String reload () {
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

            kbuilder.add( ResourceFactory.newClassPathResource( "Regles-Fiscales.drl",  
            														SimpleRules.class ),
                                  ResourceType.DRL );
            kbase = KnowledgeBaseFactory.newKnowledgeBase();
            kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
            
        	return "Reload Done";
        }
    }

    public static class Gamme {
        private List<Taxation>          items;

        private static String newline         = System.getProperty( "line.separator" );

        public Gamme() {
            this.items = new ArrayList<Taxation>();
        }

        public void addItem(Taxation item) {
            this.items.add( item );
        }

        public List<Taxation> getItems() {
            return this.items;
        }

        public String toString() {
            StringBuffer buf = new StringBuffer();

            buf.append( "ShoppingCart:" + newline );

            Iterator<Taxation> itemIter = getItems().iterator();

            while ( itemIter.hasNext() ) {
                buf.append( "\t" + itemIter.next() + newline );
            }

             return buf.toString();
        }
    }

    public static class Taxation {

        private Vehicle vehicle;
        private String country;

        public Taxation(	Vehicle vehicle, 
                        	String country) {
            super();
            this.vehicle = vehicle;
            this.country = country;
        }
        

        public Vehicle getVehicle() {
            return vehicle;
        }

        public int hashCode() {
            final int PRIME = 31;
            int result = 1;
            result = PRIME * result + ((country == null) ? 0 : country.hashCode());
            result = PRIME * result + ((vehicle == null) ? 0 : vehicle.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if ( this == obj ) return true;
            if ( obj == null ) return false;
            if ( getClass() != obj.getClass() ) return false;
            final Taxation other = (Taxation) obj;
            
             if ( country == null ) {
            
                if ( other.country != null ) return false;
                
            } else if ( !country.equals( other.country ) ) return false;
            
            if ( vehicle == null ) {
                if ( other.vehicle != null ) return false;
            } else if ( !vehicle.equals( other.vehicle ) ) return false;
            return true;
        }


		public String getCountry() {
			return country;
		}

    }

    public static class Vehicle {
        private String name;
        private double co2;
		public void setCo2(double co2) {
			this.co2 = co2;
		}

		private double conso;
		private String classe;
		private double bonus;
		private String carburant;
		
        public Vehicle(String name,
                       double co2,
                       double conso,
                       String carburant) {
            this.name = name;
            this.co2 = co2;
            this.conso = conso;
            this.carburant = carburant;
        }

        public String getName() {
            return this.name;
        }

        public double getCo2() {
            return this.co2;
        }
        
        public double getConso() {
            return this.conso;
        }

        public String toString() {
            return name ;
        }

        public int hashCode() {
            final int PRIME = 31;
            int result = 1;
            result = PRIME * result + ((name == null) ? 0 : name.hashCode());
            long temp;
            temp = Double.doubleToLongBits( co2 );
            result = PRIME * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        public boolean equals(Object obj) {
            if ( this == obj ) return true;
            if ( obj == null ) return false;
            if ( getClass() != obj.getClass() ) return false;
            final Vehicle other = (Vehicle) obj;
            if ( name == null ) {
                if ( other.name != null ) return false;
            } else if ( !name.equals( other.name ) ) return false;
            if ( Double.doubleToLongBits( co2 ) != Double.doubleToLongBits( other.co2 ) ) return false;
            return true;
        }

		public void setClasse(String classe) {
			this.classe = classe;
		}

		public String getClasse() {
			return classe;
		}

		public void setBonus(double bonus) {
			this.bonus = bonus;
		}

		public double getBonus() {
			return bonus;
		}

		public void setCarburant(String carburant) {
			this.carburant = carburant;
		}

		public String getCarburant() {
			return carburant;
		}

    }

}
