package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class YearServicePerDto {
	private int specialHolidayCode;
	private String yearServiceCode;
	private String yearServiceName;
	private int provision;
	private int yearServiceCls;
	private List<YearServicePerSetDto> yearServicePerSets;
}
