package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementActualOutput {
	private List<AgreementTimeOfManagePeriod> lstAgreementTimeOfManagePeriod;
	private Year year;
	private String employeeId;
}
