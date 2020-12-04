package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementTimeOfManagePeriodCommand {
	// 36協定対象時間
	public AgreementTimeOfMonthlyCommand agreementTime;
	// 社員ID
	public String sid;
	// 状態
	public Integer status;
	// 内訳
	public AgreementTimeBreakdownCommand agreementTimeBreakDown;
	// 年月
	public String yearMonth;
	// 法定上限対象時間
	public AgreementTimeOfMonthlyCommand legalMaxTime;
	
	public AgreementTimeOfManagePeriod toDomain() {
		
		return AgreementTimeOfManagePeriod.builder()
				.agreementTime(agreementTime.toDomain())
				.sid(sid)
				.status(EnumAdaptor.valueOf(status, AgreementTimeStatusOfMonthly.class))
				.agreementTimeBreakDown(agreementTimeBreakDown.toDomain())
				.yearMonth(new YearMonth(Integer.valueOf(yearMonth.split("/")[0])*100 + Integer.valueOf(yearMonth.split("/")[1])))
				.legalMaxTime(legalMaxTime.toDomain())
				.build();
	}
	
}
