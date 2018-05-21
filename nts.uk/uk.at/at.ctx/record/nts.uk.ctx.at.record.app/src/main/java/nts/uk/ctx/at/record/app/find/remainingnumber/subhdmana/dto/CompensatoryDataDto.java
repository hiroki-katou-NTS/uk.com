package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
public class CompensatoryDataDto {
	// ID
	private String comDayOffID;

	// 社員ID
	private String sID;

	private String cID;

	// 代休日
	private GeneralDate dayOffDate;

	// 必要日数
	private double requireDays;

	// 必要時間数
	private int requiredTimes;

	// 未相殺日数
	private double remainDays;

	// 未相殺時間数
	private int remainTimes;

	public static CompensatoryDataDto convertToDto(CompensatoryDayOffManaData compensatoryData) {
		return new CompensatoryDataDto(compensatoryData.getCID(), compensatoryData.getSID(), compensatoryData.getCID(),
				compensatoryData.getDayOffDate().getDayoffDate().get(), compensatoryData.getRequireDays().v(),
				compensatoryData.getRequiredTimes().v(), compensatoryData.getRemainDays().v(),
				compensatoryData.getRemainTimes().v());
	}
}
