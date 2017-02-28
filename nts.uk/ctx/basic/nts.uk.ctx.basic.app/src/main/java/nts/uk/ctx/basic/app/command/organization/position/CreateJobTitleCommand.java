package nts.uk.ctx.basic.app.command.organization.position;


import java.time.LocalDate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.Position;

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


public Position toDomain(String newPos){
		return Position.createFromJavaType(this.jobName,GeneralDate.localDate(this.endDate),  this.jobCode,this.jobOutCode,GeneralDate.localDate(this.startDate),this.historyID,this.companyCode, this.memo,this.hiterarchyOrderCode,this.presenceCheckScopeSet);
}
}
