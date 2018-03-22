package nts.uk.ctx.at.shared.pub.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.event.DomainEvent;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class SphdHolidayEventPub extends DomainEvent {
	/** 譛牙柑縺ｨ縺吶ｋ */
	private boolean isEffective;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 特別休暇名称 */
	private String specialHolidayName;

}
