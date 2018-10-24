package nts.uk.ctx.pr.core.app.find.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;

@Value
public class SalaryClsInfoDto {
    String companyId;
    String salaryClsCode;
    String salaryClsName;
    String memo;

    public static SalaryClsInfoDto of(SalaryClsInfo domain) {
        return new SalaryClsInfoDto(domain.getCompanyId().v(),
                                    domain.getSalaryClsCode().v(),
                                    domain.getSalaryClsName().v(),
                                    domain.getMemo().v());
    }
}
