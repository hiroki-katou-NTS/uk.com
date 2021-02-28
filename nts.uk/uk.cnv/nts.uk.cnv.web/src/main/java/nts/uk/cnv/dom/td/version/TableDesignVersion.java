package nts.uk.cnv.dom.td.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class TableDesignVersion implements Cloneable {
	private String feature;
	private GeneralDate date;

	@Override
	public TableDesignVersion clone() {
		return new TableDesignVersion(
				this.feature,
				GeneralDate.ymd(this.date.year(), this.date.month(), this.date.day()));
	}
}
