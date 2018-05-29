package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.format.SelectionCodeCharacterType;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.format.SelectionCodeLength;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.format.SelectionExternalCDLength;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.format.SelectionNameLength;

/**
 * 
 * @author tuannv
 *
 */
@Getter
public class FormatSelection {
	
	private SelectionCodeCharacterType characterType;
	
	private SelectionCodeLength codeLength;
	
	private SelectionNameLength nameLength;
	
	private SelectionExternalCDLength externalCodeLength;
	
	public FormatSelection(SelectionCodeCharacterType codeType, SelectionCodeLength codeLength,
			SelectionNameLength nameLength, SelectionExternalCDLength externalCodeLength) {
		super();
		this.characterType = codeType;
		this.codeLength = codeLength;
		this.nameLength = nameLength;
		this.externalCodeLength = externalCodeLength;
	}

	public static FormatSelection createFromJavaType( int characterTypeAtr, int codeLength,int nameLength,
			int extCodeLength) {
		return new FormatSelection(EnumAdaptor.valueOf(characterTypeAtr, SelectionCodeCharacterType.class),
				new SelectionCodeLength(codeLength), new SelectionNameLength(nameLength),
				new SelectionExternalCDLength(extCodeLength));

	}
}
