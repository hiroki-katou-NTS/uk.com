package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ExcessErrorContent;

import java.util.List;
import java.util.Optional;

/**
 * 申請作成結果
 * @author khai.dh
 */
@Getter
@AllArgsConstructor
public class AppCreationResult {
	//社員ID
	private final String empId;

	// 永続化処理
	private final Optional<AtomTask> atomTask;

	// List<超過エラー内容>
	private final List<ExcessErrorContent> errorInfo;
}
