package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.AgreMaxTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.AgreementTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.MonthlyAggregationErrorInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;

@Data
@Builder
public class AgreementTimeOfManagePeriodDto {
	/** 社員ID */
	private String employeeId;
	/** 月度 */
	private Integer yearMonth;
	/** 年度 */
	private Integer year;
	/** 36協定時間 */
	private AgreementTimeOfMonthlyDto agreementTime;
	/** 36協定上限時間 */
	private AgreMaxTimeOfMonthlyDto agreementMaxTime;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfoDto> errorInfos;
	
	public static AgreementTimeOfManagePeriodDto from(AgreementTimeOfManagePeriod domain){
		AgreementTimeOfManagePeriodDto dto = AgreementTimeOfManagePeriodDto.builder().build();
		if (domain != null) {
			dto = AgreementTimeOfManagePeriodDto.builder()
					.employeeId(domain.getEmployeeId())
					.yearMonth(domain.getYearMonth().v())
					.year(domain.getYear().v())
					.agreementTime(AgreementTimeOfMonthlyDto.from(domain.getAgreementTime().getAgreementTime()))
					.agreementMaxTime(AgreMaxTimeOfMonthlyDto.from(domain.getAgreementMaxTime().getAgreementTime()))
					.errorInfos(ConvertHelper.mapTo(domain.getErrorInfos(), c -> new MonthlyAggregationErrorInfoDto(c.getResourceId(), c.getMessage().v())))
					.build();
		}

		return dto;
	}
}
