package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class ApprovalComfirmDto {
	/**
	 * システム選択中の締めIDを取得 
	 * */
	private int selectedClosureId;
	
	/**
	 * list closure
	 */
	private List<ClosuresDto> closuresDto;
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
	
	private int processingYm;
	/**
	 * list employee code
	 */
	private List<String> employeesCode;
}
