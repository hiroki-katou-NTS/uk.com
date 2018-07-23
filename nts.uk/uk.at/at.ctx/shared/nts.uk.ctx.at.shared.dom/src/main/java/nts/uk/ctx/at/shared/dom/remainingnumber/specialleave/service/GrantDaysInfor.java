package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * 付与日数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GrantDaysInfor {
	/**
	 * 年月日
	 */
	private GeneralDate ymd;
	/**
	 * エラーフラグ
	 */
	private Optional<ErrorFlg> errorFlg;
	/**
	 * 付与日数
	 */
	private double grantDays;
}
