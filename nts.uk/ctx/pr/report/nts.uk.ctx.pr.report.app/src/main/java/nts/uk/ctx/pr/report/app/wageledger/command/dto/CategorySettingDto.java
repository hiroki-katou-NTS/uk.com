package nts.uk.ctx.pr.report.app.wageledger.command.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySettingDto {
	/** The category. */
	public String category;
	
	/** The payment type. */
	public String paymentType;
	
	/** The output items. */
	public List<SettingItemDto> outputItems;
}
