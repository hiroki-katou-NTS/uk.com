package nts.uk.file.at.app.export.statement.stamp;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface IExportQRCodeToExcel {
	
	void export(FileGeneratorContext generatorContext, GetExtractedEmployeeCardNoInput input);
	
}
