package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInLossInfoDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfoDto;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;

@Value
@Data
@AllArgsConstructor
public class LossInfoDto {

    private HealthInLossInfoDto healthInsLossInfo;

    private WelfPenInsLossIfDto welfPenInsLossIf;

    private EmpBasicPenNumInforDto empBasicPenNumInfor;

    private MultiEmpWorkInfoDto multiEmpWorkInfo ;

    private int screenMode;

}
