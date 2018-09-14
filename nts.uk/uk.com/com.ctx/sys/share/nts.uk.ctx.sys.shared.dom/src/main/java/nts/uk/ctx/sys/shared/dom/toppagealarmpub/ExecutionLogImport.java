package nts.uk.ctx.sys.shared.dom.toppagealarmpub;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.shared.dom.toppagealarm.ExistenceError;
import nts.uk.ctx.sys.shared.dom.toppagealarm.IsCancelled;
import nts.uk.ctx.sys.shared.dom.toppagealarmset.AlarmCategory;

@Data
public class ExecutionLogImport {
	/** 会社ID */
	private String companyId;
	
	/** エラーの有無 */
	private ExistenceError existenceError;
	
	/** 実行内容 */
	private AlarmCategory executionContent;
	
	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;
	
	/** 管理社員ID */
	private List<String> managerId;
	
	/** 中止フラグ */
	private Optional<IsCancelled> isCancelled;
	
	/** 対象社員ID */
	private List<ExecutionLogErrorDetail> targerEmployee;
}
