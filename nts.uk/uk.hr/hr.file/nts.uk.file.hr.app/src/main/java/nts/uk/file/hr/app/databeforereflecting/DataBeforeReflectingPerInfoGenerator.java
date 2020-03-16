package nts.uk.file.hr.app.databeforereflecting;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.screen.hr.app.databeforereflecting.find.DataBeforeReflectResultDto;

public interface DataBeforeReflectingPerInfoGenerator {
	void generate(FileGeneratorContext generatorContext, DataBeforeReflectResultDto retiDto);
}
