package nts.uk.ctx.workflow.dom.resultrecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 就業実績確認状態
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootConfirm {
	
	/**
	 * 承認ルート中間データID
	 */
	private String rootID;
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 対象者
	 */
	private String employeeID;
	
	/**
	 * 対象日
	 */
	private GeneralDate recordDate;
	
	/**
	 * ルート種類
	 */
	private RecordRootType rootType;
	
	/**
	 * 承認済フェーズ
	 */
	private List<AppPhaseConfirm> listAppPhase;
	
}
