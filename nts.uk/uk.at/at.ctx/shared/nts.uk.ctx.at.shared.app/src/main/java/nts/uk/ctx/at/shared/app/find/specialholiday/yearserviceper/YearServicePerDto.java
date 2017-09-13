package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;
import java.util.List;

import lombok.Data;
import lombok.Value;
/**
 * 
 * @author yennth
 *
 */
@Data
@Value
public class YearServicePerDto {
	private String specialHolidayCode;
	private String yearServiceCode;
	private String yearServiceName;
	private Integer yearServiceCls;
	private List<YearServicePerSetDto> yearServicePerSets;
}
