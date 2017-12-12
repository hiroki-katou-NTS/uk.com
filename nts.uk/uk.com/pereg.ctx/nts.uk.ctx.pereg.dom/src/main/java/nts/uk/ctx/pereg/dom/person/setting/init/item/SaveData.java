package nts.uk.ctx.pereg.dom.person.setting.init.item;

import java.math.BigDecimal;

import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.savedata.SaveDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.savedata.SaveInt;
import nts.uk.ctx.pereg.dom.person.setting.init.item.savedata.SaveString;

public class SaveData extends AggregateRoot {

	protected SaveDataType saveType;

	public static SaveData createDateValue(GeneralDate value) {

		return SaveDate.createFromJavaType(value);

	}

	public static SaveData createStringValue(String value) {

		return SaveString.createFromJavaType(value);
	}

	public static SaveData createIntValue(BigDecimal value) {

		return SaveInt.createFromJavaType(value);

	}

}
