package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComparisonValue;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ConfirmedStatus;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.EmployeeCode;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.EmployeeName;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ReasonDifference;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.RegistrationStatus;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ValueDifference;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.CategoryAtr;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ItemCode;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ItemName;
@Setter
@Getter
public class DetailDifferentialExtend  extends DetailDifferential{
	private DepartmentCode departmentCode;
	public DetailDifferentialExtend(String companyCode, EmployeeCode employeeCode, EmployeeName employeeName,
			ItemCode itemCode, ItemName itemName, CategoryAtr categoryAtr, ComparisonValue comparisonValue1,
			ComparisonValue comparisonValue2, ValueDifference valueDifference, ReasonDifference reasonDifference,
			RegistrationStatus registrationStatus1, RegistrationStatus registrationStatus2,
			ConfirmedStatus confirmedStatus, DepartmentCode departmentCode) {
		super(companyCode, employeeCode, employeeName, itemCode, itemName, categoryAtr, comparisonValue1, comparisonValue2,
				valueDifference, reasonDifference, registrationStatus1, registrationStatus2, confirmedStatus);
		this.departmentCode = departmentCode;
		// TODO Auto-generated constructor stub
	}

}
