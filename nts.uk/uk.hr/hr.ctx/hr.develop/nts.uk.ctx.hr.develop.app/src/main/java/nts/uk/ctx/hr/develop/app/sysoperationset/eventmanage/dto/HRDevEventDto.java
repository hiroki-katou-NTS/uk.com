package nts.uk.ctx.hr.develop.app.sysoperationset.eventmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.hrdevelopmentevent.HRDevEvent;

@Data
@AllArgsConstructor
public class HRDevEventDto {
	// イベントID
	private int eventId;
	// 備考
	private String memo;
	// 利用できる
	private int availableEvent;
	
	public static HRDevEventDto fromDomain(HRDevEvent domain){
		return new HRDevEventDto(domain.getEventId().value, 
									domain.getMemo().v(), domain.getAvailableEvent().value);
	}
}
