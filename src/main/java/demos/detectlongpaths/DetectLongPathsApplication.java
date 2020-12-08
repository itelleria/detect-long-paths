package demos.detectlongpaths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.util.StringUtils.hasText;

@SpringBootApplication
public class DetectLongPathsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DetectLongPathsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int limit = getLimit(args);
		String directory = getDirectoryToScan(args);

		FileExplorer fileExplorer = new FileExplorer(limit);
		var fileNames = fileExplorer.findFilesWithLongName(directory);

		fileNames.stream().forEach(System.out::println);
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
