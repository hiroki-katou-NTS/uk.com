package nts.uk.ctx.basic.app.command.organization.jobtitle;


import java.time.LocalDate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitle;

@Getter
public class CreateJobTitleCommand {
private String companyCode;
private String historyID;
private LocalDate startDate;
private LocalDate endDate;
private String jobCode;
private String jobName;
private String jobOutCode;
private String memo;
private String hiterarchyOrderCode;
private int presenceCheckScopeSet;
private boolean checkCopy;


public JobTitle toDomain(String newPos){
		return JobTitle.createFromJavaType(this.jobName,GeneralDate.localDate(this.endDate),  this.jobCode,this.jobOutCode,GeneralDate.localDate(this.startDate),this.historyID,this.companyCode, this.memo,this.hiterarchyOrderCode,this.presenceCheckScopeSet);
}
}
