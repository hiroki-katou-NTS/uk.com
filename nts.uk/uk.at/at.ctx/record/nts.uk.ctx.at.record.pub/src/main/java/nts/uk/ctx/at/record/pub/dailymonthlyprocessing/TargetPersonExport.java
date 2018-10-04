package nts.uk.ctx.at.record.pub.dailymonthlyprocessing;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Setter
@Getter
public class TargetPersonExport {
	/** 社員ID */
	private String employeeId;
	
	/** 就業計算と集計実行ログID */
	private String empCalAndSumExecLogId;
	
	/** 状態 */
	private List<ComplStateOfExeContentsExport> state;
}
