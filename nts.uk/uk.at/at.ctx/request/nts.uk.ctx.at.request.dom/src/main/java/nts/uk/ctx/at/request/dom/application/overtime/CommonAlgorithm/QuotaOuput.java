package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;
// 利用する残業枠

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
@AllArgsConstructor
@NoArgsConstructor
@Data
// 利用する残業枠
public class QuotaOuput {
	// フレックス時間表示区分
	private Boolean flexTimeClf;
	// 残業枠一覧
	private List<OvertimeWorkFrame> overTimeQuotaList;
}
