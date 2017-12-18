package nts.uk.ctx.at.shared.app.find.workingcondition;

import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

public class WorkingConditionDto extends PeregDomainDto{
	//期間
	@PeregItem("IS00117")
	private String period;
	
	//開始日
	@PeregItem("IS00118")
	private GeneralDate startDate;
	
	//終了日
	@PeregItem("IS00119")
	private GeneralDate endDate;
	
	//スケ管理区分
	@PeregItem("IS00120")
	private int scheduleManagementAtr;
	
//	//スケ作成区分
//	@PeregItem("IS00121")
//	private 
//	
//	@PeregItem("IS00122")
//	
//	@PeregItem("IS00123")
}
