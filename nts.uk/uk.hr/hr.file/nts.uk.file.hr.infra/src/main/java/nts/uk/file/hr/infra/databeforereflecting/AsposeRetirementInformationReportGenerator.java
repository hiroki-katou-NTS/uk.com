package nts.uk.file.hr.infra.databeforereflecting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.hr.app.databeforereflecting.DataBeforeReflectingPerInfoGenerator;
import nts.uk.screen.hr.app.databeforereflecting.find.DataBeforeReflectResultDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeRetirementInformationReportGenerator extends AsposeCellsReportGenerator
		implements DataBeforeReflectingPerInfoGenerator {

	@Override
	public void generate(FileGeneratorContext generatorContext, DataBeforeReflectResultDto retiDto) {
		// TODO Auto-generated method stub

	}

}
