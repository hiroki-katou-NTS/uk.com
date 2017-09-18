package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;
import java.util.List;

import lombok.Data;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.YearServiceIdCls;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.primitives.YearServiceCode;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.primitives.YearServiceName;
/**
 * 
 * @author yennth
 *
 */
@Data
@Value
public class YearServicePerDto {
	private String specialHolidayCode;
	private YearServiceCode yearServiceCode;
	private YearServiceName yearServiceName;
	private YearServiceIdCls yearServiceCls;
	private List<YearServicePerSetDto> yearServicePerSets;
}
