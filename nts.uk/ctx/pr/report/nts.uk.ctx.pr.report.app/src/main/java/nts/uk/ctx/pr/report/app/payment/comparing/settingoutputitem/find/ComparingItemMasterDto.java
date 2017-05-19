package nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.find;

import lombok.Value;

@Value
public class ComparingItemMasterDto {
	private String itemCode;
	private String itemName;
	private int categoryAtr;
	private String categoryAtrName;

	public static ComparingItemMasterDto fromDomain(String itemCode, String itemName, int categoryAtr,
			String categoryAtrName) {
		return new ComparingItemMasterDto(itemCode, itemName, categoryAtr, categoryAtrName);
	}

}