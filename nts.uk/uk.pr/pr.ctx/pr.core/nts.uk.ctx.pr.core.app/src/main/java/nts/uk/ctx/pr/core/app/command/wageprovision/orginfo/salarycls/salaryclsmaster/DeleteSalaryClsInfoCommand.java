package nts.uk.ctx.pr.core.app.command.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;

@Value
public class DeleteSalaryClsInfoCommand {
    private String companyId;
    private String salaryClsCode;

    public SalaryClsInfo toDomain() {
        return SalaryClsInfo.createFromDataType(this.companyId, this.salaryClsCode);
    }
}
