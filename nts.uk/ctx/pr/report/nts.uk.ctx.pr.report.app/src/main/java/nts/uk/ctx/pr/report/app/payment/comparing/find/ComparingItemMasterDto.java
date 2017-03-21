package nts.uk.ctx.pr.report.app.payment.comparing.find;

import lombok.Value;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingItemMaster;

@Value
public class ComparingItemMasterDto {
	private String itemCode;
	private String itemName;

	public static ComparingItemMasterDto fromDomain(ComparingItemMaster domain) {
		return new ComparingItemMasterDto(domain.getItemCode().v(), domain.getItemName().v());
	}

}