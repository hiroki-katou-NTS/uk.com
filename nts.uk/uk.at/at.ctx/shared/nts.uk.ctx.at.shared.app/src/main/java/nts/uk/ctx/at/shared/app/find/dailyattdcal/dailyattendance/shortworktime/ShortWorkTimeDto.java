package nts.uk.ctx.at.shared.app.find.dailyattdcal.dailyattendance.shortworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkTime;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ShortWorkTimeDto {
	/**
	 * 短時間勤務枠NO
	 */
	private int shortWorkTimeFrameNo;
	
	/**
	 * 終了
	 */
	private int end;
	
	/**
	 * 育児介護区分
	 */
	private int childCareAtr;
	
	/**
	 * 開始
	 */
	private int start;
	
	public static ShortWorkTimeDto fromDomain(ShortWorkTime shortWorkTime) {
		return new ShortWorkTimeDto(
				shortWorkTime.getShortWorkTimeFrameNo().v(), 
				shortWorkTime.getEnd().v(), 
				shortWorkTime.getChildCareAtr().value, 
				shortWorkTime.getStart().v());
	}
}
