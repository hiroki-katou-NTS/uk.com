package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 基準日に関する情報
public class InfoBaseDateOutput {
	// 勤務種類リスト
	private List<WorkType> worktypes;
	// 残業申請で利用する残業枠
	private QuotaOuput quotaOutput;
}
