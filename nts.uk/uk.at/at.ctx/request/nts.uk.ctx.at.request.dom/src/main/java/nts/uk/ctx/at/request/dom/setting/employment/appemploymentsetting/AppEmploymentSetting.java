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
//雇用別申請承認設定
public class AppEmploymentSetting extends AggregateRoot{
	
//	会社ID
	private String companyID;
//	雇用区分コード
	private String employmentCode;
//	申請別対象勤務種類
	private List<WorkTypeObjAppHoliday> listWTOAH;
	
}
