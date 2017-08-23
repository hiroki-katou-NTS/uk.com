package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.Memo;

@AllArgsConstructor
@Getter
public class SpecialHoliday extends AggregateRoot {

	/*会社ID*/
	private String companyId;

	/*特別休暇コード*/
	private SpecialHolidayCode specialHolidayCode;

	/*特別休暇名称*/
	private SpecialHolidayName specialHolidayName;

	/*定期付与*/
	private GrantPeriodicCls grantPeriodicCls;

	/*メモ*/
	private Memo memo;
	
	public static SpecialHoliday createFromJavaType(String companyId,
			String specialHolidayCode,
			String specialHolidayName,
			int grantPeriodicCls,
			String memo){
					return new SpecialHoliday(companyId, 
					new SpecialHolidayCode(specialHolidayCode),
					new SpecialHolidayName(specialHolidayName),
					EnumAdaptor.valueOf(grantPeriodicCls, GrantPeriodicCls.class),
					new Memo(memo));
	}
}
