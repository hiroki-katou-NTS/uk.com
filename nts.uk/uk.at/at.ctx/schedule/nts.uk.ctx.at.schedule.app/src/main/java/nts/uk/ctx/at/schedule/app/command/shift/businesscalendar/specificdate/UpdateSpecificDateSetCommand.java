package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

import java.util.List;

import lombok.Value;

@Value
public class UpdateSpecificDateSetCommand {
	private Integer util;
	private String strDate;
	private String endDate;
	private List<Integer> dayofWeek;
	private List<Integer> lstTimeItemId;
	private Integer setUpdate;
	private String workplaceId;
}
