package nts.uk.ctx.sys.shared.pub.toppagealarmpub;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class ExecutionLogImport {
	/** 会社ID */
	private String companyId;
	
	/** エラーの有無 */
	private int existenceError;
	
	/** 実行内容 */
	private int executionContent;
	
	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;
	
	/** 管理社員ID */
	private List<String> managerId;
	
	/** 中止フラグ */
	private Integer isCancelled;
	
	/** 対象社員ID */
	private List<ExecutionLogErrorDetail> targerEmployee;
}
