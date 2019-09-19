package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembership;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.shr.com.company.CompanyInfor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuredNameChangedNotiExportData {

    String empId;

    CompanyInfor companyInfor;

    SocialInsuranceOffice socialInsuranceOffice;

    WelfPenNumInformation welfPenNumInformation;

    HealthCarePortInfor healthCarePortInfor;

    EmpBasicPenNumInfor empBasicPenNumInfor;

    WelfarePenTypeInfor welfarePenTypeInfor;

    EmPensionFundPartiPeriodInfor emPensionFundPartiPeriodInfor;

    EmpNameChangeNotiInfor empNameChangeNotiInfor;

    FundMembership fundMembership;

    HealInsurNumberInfor healInsurNumberInfor;

    EmpHealthInsurBenefits empHealthInsurBenefits;


}
