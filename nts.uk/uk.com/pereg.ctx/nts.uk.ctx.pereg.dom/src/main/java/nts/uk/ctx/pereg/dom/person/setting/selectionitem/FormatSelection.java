package nts.uk.ctx.pereg.dom.person.setting.selectionitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author tuannv
 *
 */
@AllArgsConstructor
@Getter
public class FormatSelection {
	private SelectionCodeLength selectionCode;
	private SelectionCodeCharacter selectionCodeCharacter;
	private SelectionNameLength selectionName;
	private SelectionExternalCDLength selectionExternalCode;

	// 選択肢の形式ドメイン
	public static FormatSelection createFromJavaType(int selectionCd, int characterTypeAtr, int selectionName,
			int selectionExtCd) {

		// 選択肢の形式パラメーター帰還
		return new FormatSelection(new SelectionCodeLength(selectionCd),
				EnumAdaptor.valueOf(characterTypeAtr, SelectionCodeCharacter.class),
				new SelectionNameLength(selectionName), new SelectionExternalCDLength(selectionExtCd));

	}
}
