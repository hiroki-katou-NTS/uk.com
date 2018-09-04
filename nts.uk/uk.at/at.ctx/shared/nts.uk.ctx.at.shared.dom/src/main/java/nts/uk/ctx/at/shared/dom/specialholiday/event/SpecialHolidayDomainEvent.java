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
	private boolean isUse;
	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	/** 特別休暇名称 */
	private SpecialHolidayName specialHolidayName;
	/** 付与するタイミングの種類 */
	private TypeTime typeTime;

	public SpecialHolidayDomainEvent(boolean isUse, int specialHolidayCode, String specialHolidayName, int typeTime) {
		super();
		this.isUse = isUse;
		this.specialHolidayCode = new SpecialHolidayCode(specialHolidayCode);
		this.specialHolidayName = new SpecialHolidayName(specialHolidayName);
		this.typeTime = EnumAdaptor.valueOf(typeTime, TypeTime.class);
	}

	public static SpecialHolidayDomainEvent createFromDomain(boolean isEffective,SpecialHoliday domain) {
		return new SpecialHolidayDomainEvent(isEffective, domain.getSpecialHolidayCode().v(),
				domain.getSpecialHolidayName().v(), domain.getGrantRegular().getTypeTime().value);
	}
}
