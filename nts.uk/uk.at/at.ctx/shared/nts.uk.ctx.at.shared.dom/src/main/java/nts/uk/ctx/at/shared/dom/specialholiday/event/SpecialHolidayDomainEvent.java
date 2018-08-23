package nts.uk.ctx.at.shared.dom.specialholiday.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayName;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;

/**
 * 特別休暇情報が変更された
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
public class SpecialHolidayDomainEvent extends DomainEvent {
	
	/** 有効とする */
<<<<<<< HEAD
	private boolean isUse;
=======
	private boolean isEffective;
>>>>>>> pj/at/dev/Team_B/Test_ReleaseUser
	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	/** 特別休暇名称 */
	private SpecialHolidayName specialHolidayName;
	/** 付与するタイミングの種類 */
	private TypeTime typeTime;

<<<<<<< HEAD
	public SpecialHolidayDomainEvent(boolean isUse, int specialHolidayCode, String specialHolidayName, int typeTime) {
		super();
		this.isUse = isUse;
=======
	public SpecialHolidayDomainEvent(boolean isEffective, int specialHolidayCode, String specialHolidayName, int typeTime) {
		super();
		this.isEffective = isEffective;
>>>>>>> pj/at/dev/Team_B/Test_ReleaseUser
		this.specialHolidayCode = new SpecialHolidayCode(specialHolidayCode);
		this.specialHolidayName = new SpecialHolidayName(specialHolidayName);
		this.typeTime = EnumAdaptor.valueOf(typeTime, TypeTime.class);
	}

	public static SpecialHolidayDomainEvent createFromDomain(boolean isSave,SpecialHoliday domain) {
		return new SpecialHolidayDomainEvent(isSave, domain.getSpecialHolidayCode().v(),
				domain.getSpecialHolidayName().v(), domain.getGrantRegular().getTypeTime().value);
	}
}
