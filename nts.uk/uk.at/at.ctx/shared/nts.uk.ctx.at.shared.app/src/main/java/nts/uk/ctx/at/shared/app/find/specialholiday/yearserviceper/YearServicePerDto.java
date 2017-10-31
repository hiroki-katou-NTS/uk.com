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
	private String specialHolidayCode;
	private String yearServiceCode;
	private String yearServiceName;
	private int yearServiceCls;
	private List<YearServicePerSetDto> yearServicePerSets;
}
