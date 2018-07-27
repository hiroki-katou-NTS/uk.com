package nts.uk.ctx.at.request.dom.application.remainingnumer;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface RemainingNumberGenerator {
	
	void generate(FileGeneratorContext generatorContext, List<ExcelInforCommand> excelInforCommand);
}
