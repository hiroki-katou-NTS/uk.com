package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 休暇残数情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class RemainVacationInfo {
	
	/**
	 * 年休管理区分
	 */
	private boolean yearManage;
	
	/**
	 * 代休管理区分
	 */
	private boolean subHdManage;
	
	/**
	 * 振休管理区分
	 */
	private boolean subVacaManage;
	
	/**
	 * 積休管理区分
	 */
	private boolean retentionManage;
	
	/**
	 * 年休残数
	 */
	private Double yearRemain;
	
	/**
	 * 代休残数
	 */
	private Double subHdRemain;
	
	/**
	 * 振休残数
	 */
	private Double subVacaRemain;
	
	/**
	 * 積休残数
	 */
	private Double stockRemain;
}
