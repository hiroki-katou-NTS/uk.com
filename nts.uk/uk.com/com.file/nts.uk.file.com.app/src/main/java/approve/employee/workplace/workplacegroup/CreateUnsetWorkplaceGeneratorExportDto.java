package approve.employee.workplace.workplacegroup;

import java.nio.file.Path;

import lombok.Value;
@Value
public class CreateUnsetWorkplaceGeneratorExportDto {
	private Path path;
	private String fileName;
}
