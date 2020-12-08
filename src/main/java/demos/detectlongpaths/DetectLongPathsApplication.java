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
		int pathLimit = getPathLimit(args);
		int filenameLimit = getFilenameLimit(args);
		String directory = getDirectoryToScan(args);

		FileExplorer fileExplorer = new FileExplorer(pathLimit, filenameLimit);
		var scanResult = fileExplorer.findFilesWithLongName(directory);

		LOGGER.info("{} files detected with long name:", scanResult.getLongNameFileList().size());

		scanResult.getLongNameFileList().stream()
				.forEach(f -> LOGGER.info(f));

		LOGGER.info("{} files detected with long paths:", scanResult.getLongPathFileList().size());

		scanResult.getLongPathFileList().stream()
				.forEach(f -> LOGGER.info(f));

	}

	private int getPathLimit(String[] args) {
		if (!hasText(args[0])) {
			throw new IllegalArgumentException();
		}

		return Integer.parseInt(args[0]);
	}

	private int getFilenameLimit(String[] args) {
		if (!hasText(args[1])) {
			throw new IllegalArgumentException();
		}

		return Integer.parseInt(args[1]);
	}

	private String getDirectoryToScan(String[] args) {

		if (!hasText(args[2])) {
			return ".";
		}

		return args[2];
	}

}
