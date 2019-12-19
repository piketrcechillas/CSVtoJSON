public class Configurations {

	private char separator;
	private String csvPath;
	private String mappingPath;
	private String namespace;
	private String encoding;
	
	public Configurations(char separator, String csvPath, String mappingPath, String namespace, String encoding) {
		this.separator = separator;
		this.csvPath = csvPath;
		this.mappingPath = mappingPath;
		this.namespace = namespace;
		this.encoding = encoding;
	}

	public char getSeparator() {
		return separator;
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	public String getCSVPath() {
		return csvPath;
	}

	public void setCSVPath(String csvPath) {
		this.csvPath = csvPath;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingUri(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	
}