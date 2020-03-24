package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;


public interface OutputConditionListOfStampGenerator {
	/**
	 * 
	 * @param fileGeneratorContext
	 * @param query
	 */
	StampGeneratorExportDto  generate(FileGeneratorContext fileGeneratorContext, OutputConditionListOfStampQuery query);

	
	void generateExcelScreen(FileGeneratorContext generatorContext, List<OutputConditionListOfStampQuery> dataSource);
}
