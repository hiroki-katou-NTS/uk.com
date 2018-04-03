package nts.uk.ctx.at.request.pub.spr.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 残業申請
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppOverTimeSprExport {
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * 反映情報.実績反映状態
	 */
	private Integer stateReflectionReal;
	
	/** 
	 * 残業区分
	 */
	private Integer overTimeAtr;
}
