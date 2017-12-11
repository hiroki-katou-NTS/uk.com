package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.FormatSelection;

@Value
public class FormatSelectionDto {
	private int selectionCode;
	private int selectionCodeCharacter;
	private int selectionName;
	private int selectionExternalCode;

	public static FormatSelectionDto fromDomain(FormatSelection domain) {
		return new FormatSelectionDto(domain.getSelectionCode().v(), domain.getSelectionCodeCharacter().value,
				domain.getSelectionName().v(), domain.getSelectionExternalCode().v());
	}
}
