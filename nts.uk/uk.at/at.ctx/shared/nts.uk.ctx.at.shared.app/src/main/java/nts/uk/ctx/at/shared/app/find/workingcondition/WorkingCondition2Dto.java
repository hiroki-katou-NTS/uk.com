package nts.uk.ctx.at.shared.app.find.workingcondition;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Setter
public class WorkingCondition2Dto extends WorkingConditionDto {	
	
	/**
	 * 期間 - 
	 */
	@PeregItem("IS00780")
	private String period2;

	/**
	 * 開始日
	 */
	@PeregItem("IS00781")
	private GeneralDate startDate2;

	/**
	 * 終了日
	 */
	@PeregItem("IS00782")
	private GeneralDate endDate2;
	
	public WorkingCondition2Dto(String recordId) {
		super(recordId);
		// TODO Auto-generated constructor stub
	}
}
