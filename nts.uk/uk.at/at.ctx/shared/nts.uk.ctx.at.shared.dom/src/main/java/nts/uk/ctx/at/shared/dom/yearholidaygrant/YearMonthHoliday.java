package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YearMonthHoliday {
	private int year;
	private int month;
	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof YearMonthHoliday))
            return false;

        YearMonthHoliday mdc = (YearMonthHoliday) obj;
        return mdc.year == this.year && mdc.month == this.month;
    }
}
