package nts.uk.ctx.basic.app.find.organization.position;


import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class JobHistDto {

	private String historyId;
	private String companyCode;
	private GeneralDate startDate;
	private GeneralDate endDate;
	
	//nts.uk.time.formatDate(data, "yyyy/MM/dd")
	
//	public static JobHistDto fromDomain(JobHistory domain){
//		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		  return new JobHistDto(domain.getCompanyCode(),
//		    domain.getHistoryId(),
//		    domain.getStartDate().localDate().format(formatter), 
//		    domain.getEndDate().localDate().format(formatter));
//		 }
	
}
