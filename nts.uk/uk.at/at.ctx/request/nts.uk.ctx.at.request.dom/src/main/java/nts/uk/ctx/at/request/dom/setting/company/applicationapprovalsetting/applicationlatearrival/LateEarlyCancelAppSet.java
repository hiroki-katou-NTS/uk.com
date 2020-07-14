package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * 遅刻早退取消申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class LateEarlyCancelAppSet {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 取り消す設定
	 */
	private CancelAtr cancelAtr;
	
}
