package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

import java.util.List;

import lombok.Value;

@Value
public class UpdateSpecificDateSetCommand {
	private int util;
	private int strDate;
	private int endDate;
	private List<Integer> dayofWeek;
	private List<Integer> lstTimeItemId;
	private int setUpdate;
	private String workplaceId;
}
