package nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;

@Data
@AllArgsConstructor
public class SalaryClsInfo extends AggregateRoot {

    private CompanyId companyId;
    private SalaryClsCode salaryClsCode;
    private SalaryClsName salaryClsName;
    private Memo memo;

    public SalaryClsInfo(CompanyId companyId, SalaryClsCode salaryClsCode) {
        this.companyId = companyId;
        this.salaryClsCode = salaryClsCode;
    }

    @Override
    public void validate() {
        super.validate();

    }

    public static SalaryClsInfo createFromDataType(String companyId, String salaryClsCode,
                                                   String salaryClsName, String memo) {
        return new SalaryClsInfo(new CompanyId(companyId),
                                  new SalaryClsCode(salaryClsCode),
                                  new SalaryClsName(salaryClsName),
                                  new Memo(memo));
    }

    public static SalaryClsInfo createFromDataType(String companyId, String salaryClsCode) {
        return new SalaryClsInfo(new CompanyId(companyId),
                new SalaryClsCode(salaryClsCode));
    }
}
