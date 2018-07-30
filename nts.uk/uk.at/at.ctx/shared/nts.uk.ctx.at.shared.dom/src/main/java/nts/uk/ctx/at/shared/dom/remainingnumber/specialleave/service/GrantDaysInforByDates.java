package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GrantDaysInforByDates {
	/**
	 * 期間外次回付与日
	 */
	private GeneralDate grantDate;
	/**
	 * 付与日数一覧：List＜年月日、付与日数、エラーフラグ＞
	 */
	private List<GrantDaysInfor> lstGrantDaysInfor;
}
