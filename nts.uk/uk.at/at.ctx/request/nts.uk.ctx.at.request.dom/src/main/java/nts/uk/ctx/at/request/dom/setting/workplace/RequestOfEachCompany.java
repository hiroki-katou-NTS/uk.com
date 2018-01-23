package nts.uk.ctx.at.request.dom.setting.workplace;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 会社別申請承認設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class RequestOfEachCompany extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 選択
	 */
	private SelectionFlg selectionFlg;
	
	/**
	 * 申請承認機能設定
	 */
	private List<ApprovalFunctionSetting> listApprovalFunctionSetting;
	
}
