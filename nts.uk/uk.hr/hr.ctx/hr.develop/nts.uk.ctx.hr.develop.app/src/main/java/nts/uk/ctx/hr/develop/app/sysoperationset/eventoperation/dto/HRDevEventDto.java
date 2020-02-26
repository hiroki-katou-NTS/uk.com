package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevEvent;

@Data
@AllArgsConstructor
public class HRDevEventDto {
	// イベントID
	private int eventId;
	// イベント名
	private String eventName;
	// 備考
	private String memo;
	// 利用できる
	private int availableEvent;
	
	public static HRDevEventDto fromDomain(HRDevEvent domain){
		return new HRDevEventDto(domain.getEventId().value,
									domain.getEventName().v(),
									domain.getMemo().v(), domain.getAvailableEvent().value);
	}
}
