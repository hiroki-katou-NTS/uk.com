package nts.uk.ctx.at.request.dom.application.appabsence.service.output;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.DateSpecHdRelationOutput;

/**
 * 特別休暇表示情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecAbsenceDispInfo {
	
	/**
	 * 事象に応じた特休フラグ
	 */
	private boolean specHdForEventFlag;
	
	/**
	 * 事象に対する特別休暇
	 */
	private Optional<SpecialHolidayEvent> specHdEvent;
	
	/**
	 * 特休枠NO
	 */
	private Optional<Integer> frameNo;
	
	/**
	 * 上限日数
	 */
	private Optional<Integer> maxDay;
	
	/**
	 * 喪主加算日数
	 */
	private Optional<Integer> dayOfRela;
	
	/**
	 * 続柄毎の上限日数リスト
	 */
	private Optional<List<DateSpecHdRelationOutput>> dateSpecHdRelationLst;
	
}
