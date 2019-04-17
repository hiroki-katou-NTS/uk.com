package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionStandardDto;

import java.util.List;

public interface SalaryHealthFileGenerator {
    void generate(FileGeneratorContext fileContext, SalaryHealthExportData data,List<CusWelfarePensionStandardDto> list,String socialInsuranceCode, String socialInsuranceName);
}
