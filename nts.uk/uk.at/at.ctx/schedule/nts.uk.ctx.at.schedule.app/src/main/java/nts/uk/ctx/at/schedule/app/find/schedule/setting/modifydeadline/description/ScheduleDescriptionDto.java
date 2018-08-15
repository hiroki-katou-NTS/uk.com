package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description;

import java.util.List;

import lombok.Data;
@Data
public class ScheduleDescriptionDto {
	private List<ScheduleAuthorityDto> authority;
	
	private List<ScheduleCommonDto> common;
	
	private List<ScheduleDateDto> date;
	
	private List<ScheduleShiftDto> shift;
	
	private List<ScheduleWorkplaceDto> workplace;

	public ScheduleDescriptionDto(List<ScheduleAuthorityDto> authority, List<ScheduleCommonDto> common,
			List<ScheduleDateDto> date, List<ScheduleShiftDto> shift, List<ScheduleWorkplaceDto> workplace) {
		super();
		this.authority = authority;
		this.common = common;
		this.date = date;
		this.shift = shift;
		this.workplace = workplace;
	}	
	

}
