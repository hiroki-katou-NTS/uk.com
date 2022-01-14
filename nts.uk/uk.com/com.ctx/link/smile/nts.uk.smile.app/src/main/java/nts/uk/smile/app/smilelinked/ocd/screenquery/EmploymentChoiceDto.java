package nts.uk.smile.app.smilelinked.ocd.screenquery;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;

@Data
@AllArgsConstructor
public class EmploymentChoiceDto {
	private List<EmploymentAndLinkedMonthSetting> employmentListWithSpecifiedCompany;
	
	private List<EmploymentAndLinkedMonthSetting> employmentListWithSpecifiedPaymentDate;
}
