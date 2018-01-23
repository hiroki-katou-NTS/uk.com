package nts.uk.ctx.at.request.dom.setting.workplace;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 職場別申請承認設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class RequestOfEachWorkplace extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 職場ID
	 */
	private String workPlaceID;
	
	/**
	 * 選択
	 */
	private SelectionFlg selectionFlg;
	
	/**
	 * 申請承認機能設定
	 */
	private List<ApprovalFunctionSetting> listApprovalFunctionSetting;
	
}
