package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import lombok.Data;

/**
 * 年休・積休消化詳細
 * @author phongtq
 *
 */
@Data
public class DetailsAnnuaAccumulatedHoliday {
	/** 使用数 */
	private String numberOfUse;
	
	/** 年休消化状況 */
	private String AnnualHolidayStatus;
	
	/** 消化日 */
	private String digestionDate;
}
