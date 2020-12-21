package nts.uk.screen.at.app.kmk004.h;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.kmk004.g.YearMonthPeriodDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;

/**
 * 
 * @author sonnlb
 *
 */

@Data
@NoArgsConstructor
public class SelectFlexYearByWorkPlaceDto {

	YearMonthPeriodDto yearMonthPeriod;

	List<DisplayMonthlyWorkingDto> timeSetComs;
}
