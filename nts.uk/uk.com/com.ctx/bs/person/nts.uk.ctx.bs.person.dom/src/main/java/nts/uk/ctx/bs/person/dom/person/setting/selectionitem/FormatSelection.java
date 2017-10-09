package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@Getter
public class FormatSelection {
	private SelectionCode selectionCode;
	private SelectionCodeCharacter selectionCodeCharacter;
	private SelectionName selectionName;
	private SelectionExternalCode selectionExternalCode;

	public static FormatSelection createFromJavaType(int selectionCd, int characterTypeAtr, int selectionName,
			int selectionExtCd) {
		return new FormatSelection(new SelectionCode(selectionCd),
				EnumAdaptor.valueOf(characterTypeAtr, SelectionCodeCharacter.class),
				new SelectionName(selectionName),
				new SelectionExternalCode(selectionExtCd));

	}
}
