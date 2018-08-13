package nts.uk.ctx.at.shared.dom.specialholiday.event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayName;

/**
 * 特別休暇情報が変更された
 * 
 * @author tanlv
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
public class SpecialHolidayEvent extends DomainEvent {
	/** 有効とする */
	private boolean isEffective;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/** 特別休暇名称 */
	private SpecialHolidayName specialHolidayName;
}
