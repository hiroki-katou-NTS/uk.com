package nts.uk.ctx.at.shared.app.command.specialholidaynew;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.shr.com.context.AppContexts;

@Value
public class SpecialHolidayCommand {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 特別休暇名称 */
	private String specialHolidayName;
	
	/** メモ */
	private String memo;

	public SpecialHoliday toDomain() {
		String companyId = AppContexts.user().companyId();
		
//		List<GrantCondition> grantConditionList = this.grantConditions.stream().map(x-> {
//			return new GrantCondition(companyId, new YearHolidayCode(yearHolidayCode), x.getConditionNo(), x.getConditionValue() != null ? new ConditionValue(x.getConditionValue()) : null, 
//					EnumAdaptor.valueOf(x.getUseConditionAtr(), UseConditionAtr.class), false);
//		}).collect(Collectors.toList());
		
		return  SpecialHoliday.createFromJavaType(companyId, specialHolidayCode, specialHolidayName, memo);
	}
}
