package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInLossInfoDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfoDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInforDto;

@Value
@Data
@AllArgsConstructor
public class LossInfoDto {

    private HealthInLossInfoDto healthInsLossInfo;

    private WelfPenInsLossIfDto welfPenInsLossIf;

    private EmpBasicPenNumInforDto empBasicPenNumInfor;

    private MultiEmpWorkInfoDto multiEmpWorkInfo ;

    private SocialInsurAcquisiInforDto socialInsurAcquisiInfor;

    private int screenMode;

}
