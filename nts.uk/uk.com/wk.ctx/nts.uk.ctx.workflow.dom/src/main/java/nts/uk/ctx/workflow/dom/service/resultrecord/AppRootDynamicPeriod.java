package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootDynamic;
/**
 * 対象者と期間の中間データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootDynamicPeriod {
	
	/**
	 * 社員ID
	 */
	private String employeeID;
	
	/**
	 * 承認ルート一覧
	 */
	private List<AppRootDynamic> appRootDynamicLst;
	
}
