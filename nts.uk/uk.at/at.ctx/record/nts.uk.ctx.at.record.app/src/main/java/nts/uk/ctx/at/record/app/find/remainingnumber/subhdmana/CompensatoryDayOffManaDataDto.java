package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;

/**
 * @author sang.nv
 *
 */
@Value
public class CompensatoryDayOffManaDataDto {

	// 社員ID
	private String sID;

	// 代休日
	private GeneralDate dayOffDate;

	// 必要日数
	private Double requireDays;

	// 必要時間数
	private Integer requiredTimes;

	// 未相殺日数
	private Double remainDays;

	// 未相殺時間数
	private Integer remainTimes;

	public static CompensatoryDayOffManaDataDto createFromDomain(CompensatoryDayOffManaData domain) {
		return new CompensatoryDayOffManaDataDto(domain.getSID(), domain.getDayOffDate().getDayoffDate().get(),
				domain.getRequireDays().v(), domain.getRequiredTimes().v(), domain.getRemainDays().v(),
				domain.getRemainTimes().v());
	}
}
