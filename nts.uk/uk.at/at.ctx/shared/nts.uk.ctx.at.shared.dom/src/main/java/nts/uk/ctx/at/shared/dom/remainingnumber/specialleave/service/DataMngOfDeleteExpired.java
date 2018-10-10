package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataMngOfDeleteExpired {
	/**	未消化数 */
	private double unDigestedDay;
	/**	特別休暇付与残数データ一覧 */
	private List<SpecialLeaveGrantRemainingData> lstGrantData;
	/**
	 * 付与日 と　上限超過消滅日数
	 */
	private Map<GeneralDate, Double> mapGrantDays;
}
