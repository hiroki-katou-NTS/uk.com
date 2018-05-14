package nts.uk.ctx.at.record.app.find.monthlyclosureupdate.exportcsv;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.app.find.monthlyclosureupdate.MonthlyClosureErrorInforDto;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class MontlyClosureUpdateExportDto {
	private String executionDt;
	private List<MonthlyClosureErrorInforDto> data;
}
