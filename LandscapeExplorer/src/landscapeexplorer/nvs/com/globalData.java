package landscapeexplorer.nvs.com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;

public class globalData {

	public LandscapeExplorerFrame mainFrame;
	public MainMenu menu = new MainMenu(this);
	public JDesktopPane desktop;
	
	public HashMap<String,JInternalFrame> frames=new HashMap<String,JInternalFrame>(); 
	public int counter;
	public String lang = "";
	public Hashtable<String, String> msgText = new Hashtable<String, String>();
	public ImageIcon nvsIcon;
	public ImageIcon appIcon;
	public String curSongFileName;
	public String mainPath;

	public Hashtable<String, BufferedImage> pictures = new Hashtable<String, BufferedImage>();
	public Hashtable<String, String> commonParams = new Hashtable<String, String>();

	
	public ExecutorService executorService = Executors.newFixedThreadPool(50);

	public JFileChooser fchooser = new JFileChooser();

	

	public globalData() {
		init();
	}

	public void init() {

		lang = "RU";
		translate_init();
		this.nvsIcon = new ImageIcon(getClass().getResource(resPrefix("nvs.png")));
		this.appIcon = new ImageIcon(getClass().getResource(resPrefix("application.png")));

		mainPath = System.getProperty("user.dir");

		Properties params = new Properties();

		try {

			params.load(new FileInputStream(this.mainPath + File.separator + "common.properties"));

			Enumeration e = params.propertyNames();

			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = params.getProperty(key);
				this.commonParams.put(key, value);

			}

			File secParamFile = new File (this.mainPath + File.separator + "secret.properties");
			
			if (secParamFile.exists()) {
				Properties secParams = new Properties();
				secParams.load(new FileInputStream(secParamFile));

				e = secParams.propertyNames();

				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = secParams.getProperty(key);
					this.commonParams.put(key, value);

				}
			}
			
		} catch (Exception e) {

			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));

		}

	}

	public void loadPicture(String picName) {

		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(resPrefix(picName + ".png")));
			pictures.put(picName, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String resPrefix(String resName) {
		String out = "";

		String protocol = this.getClass().getResource("").getProtocol();
		if (Objects.equals(protocol, "jar")) {
			out = "/resources/" + resName;
		} else if (Objects.equals(protocol, "file")) {
			out = "/" + resName;
		}

		return out;
	}

	public void translate_init() {

		addToTrans("Music editor", "Musikeditor", "Музыкальный редактор");
		addToTrans("Notes", "Anmerkungen", "Ноты");
		addToTrans("Common view", "gemeinsame Ansicht", "Общий вид");

		addToTrans("File", "Datei", "Файл");
		addToTrans("New", "neue", "Новый");
		addToTrans("exit", "Ausgang", "выход");
		addToTrans("import", "importieren", "импортировать");

		addToTrans("Current Takt", "Aktuelles Häkchen", "Текущий такт");

		addToTrans("Drums part", "Trommelteil", "Барабанная партия");
		addToTrans("drums", "Trommel", "барабаны");
		addToTrans("Drums picture", "Schlagzeug Bild", "Барабанная установка");
		addToTrans("Chords part", "Akkordteil", "Аккорды");

		addToTrans("delete note", "löschen Musiknote", "удалить ноту");
		addToTrans("play current beat", "abspielen den aktuellen Takt", "воспроизвести текущий такт");

		addToTrans("size", "Größe", "размер");
		addToTrans("tempo", "Tempo", "темп");
		addToTrans("takt number", "Taktnummer", "номер такта");

		addToTrans("save", "speichern", "сохранить");
		addToTrans("save as", "speichern als", "сохранить как");
		addToTrans("open", "öffnen", "открыть");

		addToTrans("play from the beginning", "von Anfang an spielen", "играть с начала");
		addToTrans("insert a chord", "einfügen einen Akkord", "вставить аккорд");
		addToTrans("Select beat at chords timeline", "Wählen Sie den Beat auf der Akkord-Timeline",
				"Выберите долю на шкале времени аккордов");
		addToTrans("composition", "Komposition", "композиция");

		addToTrans("insert new song part", "neuen Songteil einfügen", "добавить новую часть");
		addToTrans("insert new song part", "neuen Songteil einfügen", "добавить новую часть");

		addToTrans("rename", "umbenennen", "периеменовать");
		addToTrans("delete", "löschen", "удалить");

		addToTrans("move up", "nach oben bewegen", "переместить вверх");
		addToTrans("move down", "nach unten bewegen", "переместить вниз");

		addToTrans("new", "neue", "новый");

		addToTrans("name", "Name", "имя");
		addToTrans("add beat", "Beat hinzufügen", "добавить такт");
		addToTrans("clear sequence", "klare Reihenfolge", "очистить последовательность");
		addToTrans("sequence", "Reihenfolge", "sequence");

	}

	protected void addToTrans(String enWord, String deWord, String ruWord) {

		msgText.put(enWord + "_DE", deWord);
		msgText.put(enWord + "_RU", ruWord);
	}

	public String translate(String word) {
		String out = "";

		if (this.lang.equals("EN")) {
			return word;
		}
		;

		String key = word + "_" + this.lang;

		if (msgText.containsKey(key)) {

			out = msgText.get(key);

		} else {

			out = word;

		}

		return out;
	}

	public String parseVar(String sourceText, String varName) {
		String out = "";
		String preparedText = sourceText.replace(";;", ";");

		try {

			String[] variables = preparedText.split(";");

			for (int i = 0; i < variables.length; i++) {

				String[] parts = variables[i].split("=");

				if (parts[0].trim().equals(varName)) {
					out = parts[1];
				}

			}

		} catch (Exception e) {

			out = "err";

		}
		return out;
	}

public void repainAllFrames() {
	
}

}
