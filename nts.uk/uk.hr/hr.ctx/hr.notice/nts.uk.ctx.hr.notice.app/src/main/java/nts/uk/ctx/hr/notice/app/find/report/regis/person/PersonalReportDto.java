package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalReportDto{
	
	 Integer reportID;
	 String sendBackComment;
	 String rootSateId;
	 Integer regStatus; 
	 Integer aprStatus;
	 PersonalReportClassificationDto clsDto;

}
