package nts.uk.ctx.hr.develop.dom.careermgmt.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

//キャリア管理運用設定

@Getter 
@AllArgsConstructor
public class CareerMgmtSetting extends AggregateRoot{

	private String companyId;
	
	private boolean isCareerPath;
	
	private boolean isCareerPlan;
	
	private boolean isMail;
	
	private boolean isInfomation;
	
	private MaxClassLevel maxClassLevel;
	
	public static CareerMgmtSetting createFromJavaType(String companyId,  boolean isCareerPath, boolean isCareerPlan, boolean isMail, boolean isInfomation, int maxClassLevel) {
		return new CareerMgmtSetting(
				companyId,
				isCareerPath, 
				isCareerPlan,
				isMail,
				isInfomation,
				EnumAdaptor.valueOf(maxClassLevel, MaxClassLevel.class)
				);
	}

}
