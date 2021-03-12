package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class AddComAndWorkplaceEventCommand {

		public String workPlaceID;
		
		public String targetDate;
		
		public String eventComName;
		
		public String eventWorkplaceName;
		
		public String state;
}
