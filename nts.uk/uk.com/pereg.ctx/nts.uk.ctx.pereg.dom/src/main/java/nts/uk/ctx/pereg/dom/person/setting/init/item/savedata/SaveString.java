package nts.uk.ctx.pereg.dom.person.setting.init.item.savedata;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveData;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;
import nts.uk.ctx.pereg.dom.person.setting.init.item.StringValue;

@Getter
public class SaveString extends SaveData {

	private StringValue value;

	public SaveString(StringValue value) {
		super();
		this.saveType = SaveDataType.STRING;
		this.value = value;
	}

	public static SaveString createFromJavaType(String value) {
		return new SaveString(new StringValue(value));
	}

}
