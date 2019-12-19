import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.jena.propertytable.lang.CSV2RDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;




public class Converter {
	private static Converter instance;
	
	private Converter(){
		CSV2RDF.init();	
	}
	
	public static Converter getInstance(){
		if(instance == null) 
			instance = new Converter();
		return instance;
	}
	
	public Model convert(Configurations config) throws IOException, URISyntaxException {
		
		
		Model model = ModelFactory.createDefaultModel();
		
		File file = null;
		@SuppressWarnings("unused")
		char separator = config.getSeparator();
		
		try {
			file = File.createTempFile("temp", ".csv");
			
						
			String path = config.getCSVPath();
			Reader readCSV = new FileReader(path);

			CSVReader reader = new CSVReader(readCSV);
			CSVWriter writer = new CSVWriter(new FileWriter(file)); 
			String[] row = null;
		
			while((row = reader.readNext()) != null){
				writer.writeNext(row);
			}
			
			reader.close();
			writer.close();
		} catch (IOException e) {
			System.out.println("Error occured: " + config.toString() + ".");
			System.out.println(">>>" + e.getMessage());
		}
		
		if(file != null) {
			String namespace = config.getNamespace();
			Properties mapping = new Properties();
			try {	
				String mappingPath = config.getMappingPath();
				Reader map = new FileReader(mappingPath);
				mapping.load(map);			
			} catch (IOException e) {
				mapping = null;
				System.out.println("Error occured: " + config.getMappingPath().toString());
				System.out.println(">>>" + e.getMessage());
			}
			
			Model csv = ModelFactory.createModelForGraph(new GraphCSV(namespace, mapping, file.toURI().toString())) ;
			model.add(csv.listStatements());
			
			file.delete();
			
			return model;
		} else {
			System.out.println("File is null");
			return model;
		}
		
	}
}