package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubtractUseDaysFromMngDataOut {
	/**
	 * 特別休暇の集計結果
	 */
	private InPeriodOfSpecialLeave speLeaveResult;
	/**
	 * 
	 */
	private List<SpecialLeaveGrantRemainingData> lstSpeRemainData;
}
