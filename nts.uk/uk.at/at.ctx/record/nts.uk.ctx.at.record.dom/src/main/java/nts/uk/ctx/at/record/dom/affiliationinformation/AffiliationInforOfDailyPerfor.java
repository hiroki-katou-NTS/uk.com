package nts.uk.ctx.at.record.dom.affiliationinformation;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 
 * @author nampt
 * 日別実績の所属情報 - root
 *
 */
@Getter
public class AffiliationInforOfDailyPerfor extends AggregateRoot {
	
	private EmploymentCode employmentCode;
	
	private String employeeId;
	
	private String jobTitleID;
	
	private String wplID;
	
	private GeneralDate ymd;
	
	private ClassificationCode clsCode;
	
	private BonusPaySettingCode bonusPaySettingCode;

}
