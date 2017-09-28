package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import lombok.Data;

@Data
public class DeleteHoriTotalCategoryCommand {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
}
