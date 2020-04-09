package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
@Getter
@Setter
@AllArgsConstructor
public class AppEmploymentSetting extends AggregateRoot{
	
	private String companyID;
	
	private String employmentCode;
	
	private List<WorkTypeObjAppHoliday> listWTOAH;
	
}
