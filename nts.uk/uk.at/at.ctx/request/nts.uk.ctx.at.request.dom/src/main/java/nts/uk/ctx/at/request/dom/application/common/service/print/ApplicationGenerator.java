package nts.uk.ctx.at.request.dom.application.common.service.print;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

public interface ApplicationGenerator {
	
	void generate(FileGeneratorContext generatorContext, PrintContentOfApp printContentOfApp, ApplicationType appType);
	
}
