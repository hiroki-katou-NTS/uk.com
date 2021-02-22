package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeOfManagePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementTimeOfManagePeriodDto {
	// 36協定対象時間
	public AgreementTimeOfMonthlyDto agreementTime;
	// 社員ID
	public String sid;
	// 状態
	public Integer status;
	// 内訳
	public AgreementTimeBreakdownDto agreementTimeBreakDown;
	// 年月
	public String yearMonth;
	// 法定上限対象時間
	public AgreementTimeOfMonthlyDto legalMaxTime;
	
	
	public static AgreementTimeOfManagePeriodDto fromDomain(AgreementTimeOfManagePeriod param) {
		int year = param.getYearMonth().year();
		int month = param.getYearMonth().month();
		return new AgreementTimeOfManagePeriodDto(
				AgreementTimeOfMonthlyDto.fromDomain(param.getAgreementTime()),
				param.getSid(),
				param.getStatus().value,
				AgreementTimeBreakdownDto.fromDomain(param.getAgreementTimeBreakDown()),
				GeneralDate.ymd(year, month, 1).toString("yyyy/MM"),
				AgreementTimeOfMonthlyDto.fromDomain(param.getLegalMaxTime()));
	}
}
