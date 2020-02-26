package nts.uk.file.hr.app.databeforereflecting.retirementinformation;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetiDateDto;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredResultDto;

public interface RetirementInformationGenerator {
	void generate(FileGeneratorContext generatorContext, SearchRetiredResultDto serchReti,
			SearchRetiredEmployeesQuery searchRetiredEmployeesQuery, String companyName,
			List<ReferEvaluationItem> referEvaluaItems, RetiDateDto retiDto);
}
