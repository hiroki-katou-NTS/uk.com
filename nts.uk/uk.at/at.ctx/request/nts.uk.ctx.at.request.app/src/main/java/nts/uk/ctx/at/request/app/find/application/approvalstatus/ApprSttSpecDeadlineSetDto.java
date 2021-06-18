package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttSpecDeadlineSetDto {
	
	// 期間.開始日　＝　締め期間開始年月日
	// 期間.終了日　＝　締め期間終了年月日
	private String startDate;
	private String endDate;
	
	// 雇用コード（リスト）
	private List<String> listEmploymentCD;
}
