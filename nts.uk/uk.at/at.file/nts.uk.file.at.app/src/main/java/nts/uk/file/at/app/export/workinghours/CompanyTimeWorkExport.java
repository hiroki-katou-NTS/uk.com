package nts.uk.file.at.app.export.workinghours;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * 
 * @author chungnt
 *
 */

public interface CompanyTimeWorkExport {
	
	void export(FileGeneratorContext generatorContext, YearsInput input);
}
