package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
/**
 * 
 * @author Trung Tran
 *
 */
@Getter
public class AddBusinessWokrTypeOfHistoryCommand {
	// 開始日
	@PeregItem("IS00255")
	private GeneralDate startDate;

	// 終了日
	@PeregItem("IS00256")
	private GeneralDate endDate;

	// 勤務種別CD
	@PeregItem("IS00257")
	private String businessTypeCode;

	@PeregEmployeeId
	private String employeeId;
}
