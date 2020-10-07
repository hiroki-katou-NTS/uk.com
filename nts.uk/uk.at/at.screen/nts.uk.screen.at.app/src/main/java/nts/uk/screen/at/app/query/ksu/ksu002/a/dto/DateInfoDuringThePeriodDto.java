package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DateInfoDuringThePeriodDto {
	
	private boolean isHoliday;

	private boolean isSpecificDay;

	private String optWorkplaceEventName;

	private String optCompanyEventName;

	private List<String> listSpecDayNameWorkplace;

	private List<String> listSpecDayNameCompany;

}
