package nts.uk.ctx.basic.app.command.organization.position;



import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.Position;

@Getter
public class CreatePositionCommand {
private String companyCode;
private String historyID;
private GeneralDate startDate;
private GeneralDate endDate;
private String jobCode;
private String jobName;
private String jobOutCode;
private String memo;
private String hiterarchyOrderCode;
private int presenceCheckScopeSet;
private boolean checkCopy;


public Position toDomain(String newPos){
		return Position.createFromJavaType(this.jobName,this.endDate,  this.jobCode,this.jobOutCode,this.startDate,this.historyID,this.companyCode, this.memo,this.hiterarchyOrderCode,this.presenceCheckScopeSet);
}
}
