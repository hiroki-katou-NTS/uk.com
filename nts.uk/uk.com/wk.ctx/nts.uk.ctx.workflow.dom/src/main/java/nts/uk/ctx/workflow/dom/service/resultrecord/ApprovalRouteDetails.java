package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
/**
 * 承認ルートの詳細
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalRouteDetails {
	
	/**
	 * 承認ルート中間データ
	 */
	private AppRootInstance appRootInstance;
	
	/**
	 * 承認者
	 */
	private String employeeID;
	
	/**
	 * 代行される人
	 */
	private Optional<String> agentID;
	
	/**
	 * 代行開始日
	 */
	private Optional<GeneralDate> startDate;
	
	/**
	 * 代行終了日
	 */
	private Optional<GeneralDate> endDate;
	
}
