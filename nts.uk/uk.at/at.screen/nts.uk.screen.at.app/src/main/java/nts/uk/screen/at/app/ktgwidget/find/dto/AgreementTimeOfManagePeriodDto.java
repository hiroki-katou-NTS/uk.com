package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.AgreementTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.MonthlyAggregationErrorInfoDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;

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
//	private AgreMaxTimeOfMonthlyDto agreementMaxTime;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfoDto> errorInfos;
	
	public static AgreementTimeOfManagePeriodDto from(AgreementTimeOfManagePeriod domain){
		AgreementTimeOfManagePeriodDto dto = AgreementTimeOfManagePeriodDto.builder().build();
		if (domain != null) {
			dto = AgreementTimeOfManagePeriodDto.builder()
					.employeeId(domain.getSid())
					.yearMonth(domain.getYm().v())
					.agreementTime(AgreementTimeOfMonthlyDto.from(domain.getAgreementTime()))
//					.agreementMaxTime(AgreMaxTimeOfMonthlyDto.from(domain.getLegalMaxTime().getAgreementTime()))
//					.errorInfos(ConvertHelper.mapTo(domain.getErrorInfos(), c -> new MonthlyAggregationErrorInfoDto(c.getResourceId(), c.getMessage().v())))
					.build();
		}

		return dto;
	}
}
