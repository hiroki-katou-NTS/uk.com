package nts.uk.ctx.at.shared.dom.specialholiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantSingle;
import nts.uk.shr.com.primitive.Memo;

@AllArgsConstructor
@Getter
public class SpecialHoliday extends AggregateRoot {

	/** 会社ID **/
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 特別休暇名称 */
	private SpecialHolidayName specialHolidayName;

	/* 定期付与 */
	private GrantMethod grantMethod;

	/* メモ */
	private Memo memo;
	
	/**/
	private List<String> workTypeList;
	
	/**/
	private GrantRegular grantRegular;
	
	/**/
	private GrantPeriodic grantPeriodic;
	
	/**/
	private SphdLimit sphdLimit;
	
	/**/
	private SubCondition subCondition;
	
	/**/
	private GrantSingle grantSingle;

	@Override
	public void validate() {
		super.validate(); 
		
		this.validateWorkType();
		
		if (this.grantMethod == GrantMethod.ManageRemainNumber) {
			this.grantSingle.validate();
		} else {
			this.grantRegular.validate();
			this.grantPeriodic.validate();
			this.sphdLimit.validate();
			this.subCondition.validate();
		}
	}
	
	/**
	 * Check Work Type
	 */
	public void validateWorkType(){
		if(CollectionUtil.isEmpty(workTypeList)){
			throw new BusinessException("Msg_12");
		}
	}
	
	public static SpecialHoliday createFromJavaType(String companyId, String specialHolidayCode,
			String specialHolidayName, int grantPeriodicCls, String memo, List<String> workTypeList, 
			GrantRegular grantRegular,
			GrantPeriodic grantPeriodic,
			SphdLimit sphdLimit,
			SubCondition subCondition,
			GrantSingle grantSingle) {
		return new SpecialHoliday(companyId, new SpecialHolidayCode(specialHolidayCode),
				new SpecialHolidayName(specialHolidayName),
				EnumAdaptor.valueOf(grantPeriodicCls, GrantMethod.class),
				new Memo(memo),
				workTypeList,
				grantRegular,
				grantPeriodic,
				sphdLimit,
				subCondition,
				grantSingle);
	}
}
