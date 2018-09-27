package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 特別休暇の集計結果
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InPeriodOfSpecialLeave {
	/**
	 * 特別休暇の付与明細
	 */
	private List<SpecialLeaveGrantDetails> lstSpeLeaveGrantDetails;
	/**
	 * 特別休暇の残数
	 */
	private RemainDaysOfSpecialHoliday remainDays;
	/**
	 * 特別休暇期間外の使用
	 */
	private List<UseDaysOfPeriodSpeHoliday> useOutPeriod;
	/**
	 * 特別休暇エラー
	 */
	private List<SpecialLeaveError> lstError;
}
