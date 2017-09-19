package find.person.setting.selection;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

@Value
public class PerInfoSelectionItemDto {
	private String selectionItemId;
	private String selectionItemName;
	private String Memo;
	private int selectionItemClassification;
	private String contractCode;
	private String integrationCode;
	private FormatSelectionDto formatSelection;

	
	public static PerInfoSelectionItemDto fromDomain(PerInfoSelectionItem domain) {
		
		return new PerInfoSelectionItemDto(domain.getSelectionItemId(),
				domain.getSelectionItemName().v(),
				domain.getMemo().v(),
				domain.getSelectionItemClassification().value,
				domain.getContractCode(),
				domain.getIntegrationCode().v(),
				FormatSelectionDto.fromDomain(domain.getFormatSelection()));
		
	}
}
