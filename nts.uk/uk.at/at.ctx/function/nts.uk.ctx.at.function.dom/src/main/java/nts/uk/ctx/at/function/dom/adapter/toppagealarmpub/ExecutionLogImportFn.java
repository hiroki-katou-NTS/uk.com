package nts.uk.ctx.at.function.dom.adapter.toppagealarmpub;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionLogImportFn {
	/** 会社ID */
	private String companyId;
	
	/** エラーの有無 
	 *  エラーなし : 0
	 *  エラーあり : 1 
	 * */
	private int existenceError;
	
	/** 実行内容 */
	private AlarmCategoryFn executionContent;
	
	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;
	
	/** 管理社員ID */
	private List<String> managerId;
	
	/** 中止フラグ
	 * 未読 : 0 
	 * 了解  :1
	 */
	private Optional<Integer> isCancelled;
	
	/** 対象社員ID */
	private List<ExecutionLogErrorDetailFn> targerEmployee;
}
