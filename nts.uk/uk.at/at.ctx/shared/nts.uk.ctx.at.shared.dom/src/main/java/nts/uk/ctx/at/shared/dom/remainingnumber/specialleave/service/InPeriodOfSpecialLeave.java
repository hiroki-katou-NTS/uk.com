package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InPeriodOfSpecialLeave {
	/**
	 * 特別休暇の付与明細
	 */
	private List<SpecialLeaveGrantDetails> lstSpeLeaveGrantDetails;
	/**
	 * 特別休暇の残数
	 */
	private RemainDaysOfSpecialHoliday remainDays;
}
