package nts.uk.file.at.app.export.worktype;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReport;

public interface WorkTypeGenerator {
	
	/**
	 * Generate
	 * @param fileContext
	 * @param reportData
	 */
	void generate(FileGeneratorContext fileContext, WorkTypeReport reportData);

}
