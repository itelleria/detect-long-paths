package demos.detectlongpaths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.util.StringUtils.hasText;

@SpringBootApplication
public class DetectLongPathsApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetectLongPathsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DetectLongPathsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int limit = getLimit(args);
		String directory = getDirectoryToScan(args);

		FileExplorer fileExplorer = new FileExplorer(limit);
		var fileNames = fileExplorer.findFilesWithLongName(directory);

		fileNames.stream().forEach(f -> LOGGER.info(f));

		LOGGER.info("Detected files: {}", fileNames.size());

	}

	private int getLimit(String[] args) {
		if (!hasText(args[0])) {
			throw new IllegalArgumentException();
		}

		return Integer.parseInt(args[0]);
	}

	private String getDirectoryToScan(String[] args) {

		if (!hasText(args[1])) {
			return ".";
		}

		return args[1];
	}

}
