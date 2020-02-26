package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class EventOperationDto {
	// イベントID
	private int eventId;
	// イベントを使用する
	private int useEvent;
	// 会社ID
	private BigInteger ccd;
	
	public static EventOperationDto fromDomain(EventOperation domain){
		return new EventOperationDto(domain.getEventId().value, domain.getUseEvent().value, domain.getCcd());
	}
}
