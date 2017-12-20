package nts.uk.ctx.pereg.dom.person.setting.init.item.savedata;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveData;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

@Getter
public class SaveDate extends SaveData {

	private GeneralDate value;

	public SaveDate(GeneralDate value) {
		super();
		this.saveType = SaveDataType.DATE;
		this.value = value;
	}

	public static SaveDate createFromJavaType(GeneralDate value) {
		return new SaveDate(value);
	}

}
