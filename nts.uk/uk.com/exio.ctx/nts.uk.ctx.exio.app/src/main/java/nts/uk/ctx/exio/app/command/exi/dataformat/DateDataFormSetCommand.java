package nts.uk.ctx.exio.app.command.exi.dataformat;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.editvalue.dataformat.DateDataFormSet;
import nts.uk.ctx.exio.dom.input.editvalue.dataformat.ItemType;

@Value
public class DateDataFormSetCommand {

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 固定値の値
	 */
	private GeneralDate valueOfFixedValue;

	/**
	 * 形式選択
	 */
	private int formatSelection;

	public DateDataFormSet toDomain() {
		return new DateDataFormSet(ItemType.DATE.value, this.fixedValue, this.formatSelection,
				this.valueOfFixedValue);
	}
}
