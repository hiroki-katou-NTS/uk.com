package nts.uk.ctx.basic.dom.organization.position;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;

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

	public JobHistory( String companyCode,GeneralDate endDate,GeneralDate startDate) {

		super();
		
		this.companyCode = companyCode;
		this.historyId = IdentifierUtil.randomUniqueId();
		this.endDate = endDate;
		this.startDate = startDate;
	}
	public static JobHistory createFromJavaType( String companyCode,String historyId,GeneralDate endDate,GeneralDate startDate) {
		return new JobHistory(companyCode,historyId,endDate,startDate);

	}

}
