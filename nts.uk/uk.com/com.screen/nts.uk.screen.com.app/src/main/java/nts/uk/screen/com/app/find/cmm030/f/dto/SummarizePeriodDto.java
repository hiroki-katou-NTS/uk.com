package nts.uk.screen.com.app.find.cmm030.f.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class SummarizePeriodDto {

	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
	
	/**
	 * 運用モード
	 */
	private Integer operationMode;
	
	/**
	 * 重なるフラグ
	 */
	private boolean isOverlap;
	
	/**
	 * 承認ID<List>
	 */
	private List<String> approvalIds;
}
