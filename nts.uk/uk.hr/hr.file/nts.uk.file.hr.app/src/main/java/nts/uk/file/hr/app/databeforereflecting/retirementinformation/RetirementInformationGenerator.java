package nts.uk.file.hr.app.databeforereflecting.retirementinformation;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;
import nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find.SearchRetiredResultDto;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;

public interface RetirementInformationGenerator {
	void generate(FileGeneratorContext generatorContext, SearchRetiredResultDto serchReti,
			SearchRetiredEmployeesQuery searchRetiredEmployeesQuery, String companyName, List<ReferEvaluationItem> referEvaluaItems);
}
