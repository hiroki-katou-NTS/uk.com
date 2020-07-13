package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;

/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class StampTypeDto {
	
	/** 勤務種類を半休に変更する */
	private Boolean changeHalfDay;

	/** 外出区分 */
	private Integer goOutArt;

	/** 所定時刻セット区分 */
	private Integer setPreClockArt;

	/** 時刻変更区分 */
	private Integer changeClockArt;

	/** 計算区分変更対象 */
	private Integer changeCalArt;

	public static StampTypeDto fromDomain(StampType stampType) {

		return new StampTypeDto(stampType.isChangeHalfDay(),
				stampType.getGoOutArt().isPresent() ? stampType.getGoOutArt().get().value : null,
				stampType.getSetPreClockArt().value, stampType.getChangeClockArt().value,
				stampType.getChangeCalArt().value);
	}
}
