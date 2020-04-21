package approve.employee.workplace.workplacegroup;

import nts.arc.layer.infra.file.export.FileGeneratorContext;


public interface CreateUnsetWorkplaceGenerator {
	/**
	 * Lấy data in của Stamp 
	 * @param fileGeneratorContext
	 * @param query
	 */
	CreateUnsetWorkplaceGeneratorExportDto  generate(FileGeneratorContext fileGeneratorContext, OutputExportKSM007 query);

}
