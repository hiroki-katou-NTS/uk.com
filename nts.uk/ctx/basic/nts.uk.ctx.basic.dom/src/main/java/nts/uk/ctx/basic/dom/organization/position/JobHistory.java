package nts.uk.ctx.basic.dom.organization.position;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Setter
@Getter
public class JobHistory extends AggregateRoot {
	
	private String historyId;

	private String companyCode;
	
	private GeneralDate endDate;
	
	private GeneralDate startDate;
	
	
	
	
	
	
	public JobHistory( String companyCode,String historyId,GeneralDate endDate,GeneralDate startDate) {

		super();
		
		this.companyCode = companyCode;
		this.historyId = historyId;
		this.endDate = endDate;
		this.startDate = startDate;
	}

	public static JobHistory createFromJavaType( String companyCode,String historyId,GeneralDate endDate,GeneralDate startDate) {
		return new JobHistory(companyCode,historyId,endDate,startDate);

	}

	public void setEndDate(GeneralDate endDate) {
		this.endDate = endDate;
	}

}
