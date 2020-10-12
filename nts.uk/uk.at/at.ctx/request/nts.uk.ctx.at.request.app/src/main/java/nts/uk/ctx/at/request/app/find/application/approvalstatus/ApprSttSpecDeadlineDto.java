package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDto;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttSpecDeadlineDto {
	
	// 選択された就業締め日
	private List<ClosureDto> closureList;
	
	// 期間（開始日～終了日）
	private String startDate;
	private String endDate;
	
	// 雇用コード（リスト）
	private List<String> listEmployeeCD;
}
