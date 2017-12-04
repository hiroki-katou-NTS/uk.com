package nts.uk.ctx.bs.person.dom.person.setting.init.item.savedata;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveData;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.IntValue;

@Getter
public class SaveInt extends SaveData {

	private IntValue value;

	public SaveInt(IntValue value) {
		super();
		this.saveType = SaveDataType.NUMBERIC;
		this.value = value;
	}

	public static SaveInt createFromJavaType(BigDecimal value) {

		return new SaveInt(new IntValue(value));
	}

}
