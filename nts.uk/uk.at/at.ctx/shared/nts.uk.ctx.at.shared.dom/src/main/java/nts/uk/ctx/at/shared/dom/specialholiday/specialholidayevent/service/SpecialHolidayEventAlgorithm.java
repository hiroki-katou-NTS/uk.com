package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;

/**
 * 事象に対する特別休暇
 * @author hoatt
 *
 */
public interface SpecialHolidayEventAlgorithm {
	/**
	 * 指定する勤務種類が事象に応じた特別休暇かチェックする
	 * @param companyId - 会社ID
	 * @param workTypeCD - 勤務種類コード
	 * @return
	 */
	public CheckWkTypeSpecHdEventOutput checkWkTypeSpecHdForEvent(String companyId, String workTypeCD);
	/**
	 * 指定する特休枠の上限日数を取得する
 	 * @param companyId - 会社ID
 	 * @param specHdFrame - 特休枠
 	 * @param specHdEvent - ドメインモデル「事象に対する特別休暇」
 	 * @param relationCD - 続柄コード(optional)
	 * @return
	 */
	public MaxDaySpecHdOutput getMaxDaySpecHd(String companyId, Integer specHdFrame, 
			SpecialHolidayEvent specHdEvent, Optional<String> relationCD);
	/**
	 * 指定する特休枠の続柄に対する上限日数を取得する
	 * @param companyId - 会社ID
	 * @param specHdFrame - 特休枠
	 * @return
	 */
	public List<DateSpecHdRelationOutput> getMaxDaySpecHdByRelaFrameNo(String companyId, Integer specHdFrame);
}
