package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 子看護の残数
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChildCareNursingRemainOutputPara {
	/**
	 * 管理区分
	 */
	private boolean chkMngAtr;
	/**
	 * 期中付与フラグ
	 */
	private boolean periodGrantFlg;
	/**
	 * 付与前明細: 子の看護介護残日数
	 */
	private double beforeCareLeaveDays;
	/**
	 * 付与前明細: 子の看護介護使用日数
	 */
	private double beforeUseDays;
	/**
	 * 付与後明細
	 */
	private Optional<Double> afterCareLeaveDays;
	/**
	 * 付与後明細
	 */
	private Optional<Double> afterUseDays;
	
}
