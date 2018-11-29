package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
