package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class TargetPersonImport {
	/** 社員ID */
	private String employeeId;
	
	/** 就業計算と集計実行ログID */
	private String empCalAndSumExecLogId;
	
	/** 状態 */
	private List<ComplStateOfExeContentsImport> state;
}
