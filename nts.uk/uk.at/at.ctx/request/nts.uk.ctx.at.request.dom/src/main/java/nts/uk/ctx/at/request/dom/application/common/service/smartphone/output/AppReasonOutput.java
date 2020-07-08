package nts.uk.ctx.at.request.dom.application.common.service.smartphone.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppReasonOutput {
	
	/**
	 * 定型理由の表示区分
	 */
	private DisplayAtr displayStandardReason;
	
	/**
	 * 申請理由の表示区分
	 */
	private DisplayAtr displayAppReason;
	
	/**
	 * 定型理由項目<List> 
	 */
	private List<ReasonTypeItem> reasonTypeItemLst;
	
}
