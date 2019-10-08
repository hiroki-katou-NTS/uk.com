package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurOutOrder;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembership;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuredNameChangedNotiExportData {

    private String empId;

    private int socialInsurOutOrder;

    private CompanyInfor companyInfor;

    private EmployeeInfoEx employeeInfo;

    private SocialInsuranceOffice socialInsuranceOffice;

    private WelfPenNumInformation welfPenNumInformation;

    private HealthCarePortInfor healthCarePortInfor;

    private EmpBasicPenNumInfor empBasicPenNumInfor;

    private WelfarePenTypeInfor welfarePenTypeInfor;

    private EmPensionFundPartiPeriodInfor emPensionFundPartiPeriodInfor;

    private EmpNameChangeNotiInfor empNameChangeNotiInfor;

    private FundMembership fundMembership;

    private HealInsurNumberInfor healInsurNumberInfor;

    private EmpHealthInsurBenefits empHealthInsurBenefits;

    private boolean processSate;

    private String employeeName;

    private GeneralDate submitDate;

}
