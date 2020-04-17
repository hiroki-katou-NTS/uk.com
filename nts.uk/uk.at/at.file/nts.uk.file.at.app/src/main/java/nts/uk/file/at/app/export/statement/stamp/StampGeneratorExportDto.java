package nts.uk.file.at.app.export.statement.stamp;

import java.nio.file.Path;

import lombok.Value;
@Value
public class StampGeneratorExportDto {
	private Path path;
	private String fileName;
}
