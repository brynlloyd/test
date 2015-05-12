package uk.gov.metoffice.demo.streamapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.codec.digest.DigestUtils;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		final Instant start = Instant.now();
		final int count = hash(args[0], args[1]);
		final Duration duration = Duration.between(start, Instant.now());

		LOGGER.info(String.format("%s lines hashed in %d ms.", count, duration.toMillis()));
	}

	private int hash(final String in, final String out) throws IOException {
		int count = 0;
		try (final FileWriter writer = new FileWriter(out)) {
			try (final BufferedReader reader = new BufferedReader(new FileReader(in))) {
				String line;
				while ((line = reader.readLine()) != null) {
					writer.write(DigestUtils.sha512Hex(line));
					count++;
				}
			}
		}
		return count;
	}

}
