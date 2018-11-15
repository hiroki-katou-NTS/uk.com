package nts.uk.ctx.pr.core.app.command.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;

@Value
public class AddSalaryClsInfoCommand {
    private String companyId;
    private String salaryClsCode;
    private String salaryClsName;
    private String memo;

    public SalaryClsInfo toDomain() {
        return SalaryClsInfo.createFromDataType(this.companyId, this.salaryClsCode, this.salaryClsName, this.memo);
    }
}
