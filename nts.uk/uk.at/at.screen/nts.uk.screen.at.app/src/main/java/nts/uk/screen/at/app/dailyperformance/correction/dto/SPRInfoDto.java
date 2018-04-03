package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class SPRInfoDto {
	 GeneralDate dateSpr;
     boolean canEdit;
     String employeeId;
     //退勤打刻
     String liveTime;
     //出勤打刻
     String goOut;
}
