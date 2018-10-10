package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 振休残数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbsDaysRemain {
	/**
	 * 残日数 || 発生日数
	 */
	private double remainDays;
	/**
	 * 未消化日数 || 使用日数
	 */
	private double unDigestedDays;
	/**
	 * True: エラーがあります。、
	 * False：　エラーがない
	 */
	private boolean errors;
}
