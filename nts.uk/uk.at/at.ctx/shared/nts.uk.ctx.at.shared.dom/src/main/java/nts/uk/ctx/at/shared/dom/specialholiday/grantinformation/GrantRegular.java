package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 付与情報
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantRegular extends DomainObject {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	
	/** 付与するタイミングの種類 */
	private TypeTime typeTime;
	
	/** 付与基準日 */
	private GrantDate grantDate;
	
	/** 取得できなかった端数は消滅する */
	private boolean allowDisappear;
	
	/** 取得できなかった端数は消滅する */
	private GrantTime grantTime;
	
	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * Create from Java Type
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param typeTime
	 * @param grantDate
	 * @param allowDisappear
	 * @param grantTime
	 * @return
	 */
	public static GrantRegular createFromJavaType(String companyId, int specialHolidayCode, int typeTime, int grantDate, boolean allowDisappear, GrantTime grantTime) {
		return new GrantRegular(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(typeTime, TypeTime.class),
				EnumAdaptor.valueOf(grantDate, GrantDate.class),
				allowDisappear,
				grantTime);
	}
}
