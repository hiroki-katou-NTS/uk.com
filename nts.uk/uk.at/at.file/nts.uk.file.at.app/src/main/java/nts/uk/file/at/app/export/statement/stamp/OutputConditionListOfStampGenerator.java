package nts.uk.file.at.app.export.statement.stamp;

import nts.arc.layer.infra.file.export.FileGeneratorContext;


public interface OutputConditionListOfStampGenerator {
	/**
	 * Lấy data in của Stamp 
	 * @param fileGeneratorContext
	 * @param query
	 */
	StampGeneratorExportDto  generate(FileGeneratorContext fileGeneratorContext, OutputConditionListOfStampQuery query);

}
