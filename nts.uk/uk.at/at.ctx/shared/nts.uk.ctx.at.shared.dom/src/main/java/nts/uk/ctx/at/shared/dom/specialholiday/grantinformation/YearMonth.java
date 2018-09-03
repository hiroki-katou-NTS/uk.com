package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearMonth {
	private int year;
	private int month;
	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof YearMonth))
            return false;

        YearMonth mdc = (YearMonth) obj;
        return mdc.year == this.year && mdc.month == this.month;
    }
}
