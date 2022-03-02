package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;

@Data
@AllArgsConstructor
public class EmploymentChoiceDto {
	private List<EmploymentAndLinkedMonthSetting> employmentListWithSpecifiedCompany;
	
	private List<EmploymentAndLinkedMonthSetting> employmentListWithSpecifiedPaymentDate;
}
