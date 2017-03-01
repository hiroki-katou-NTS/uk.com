package nts.uk.ctx.basic.dom.organization.positionhistory;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;

@Getter
public class PositionHistory extends AggregateRoot {
	
	private String historyID;

	private String companyCode;
	
	private GeneralDate endDate;
	
	private GeneralDate startDate;
	
	
	
	
	
	public PositionHistory( String companyCode,String historyID,GeneralDate endDate,GeneralDate startDate) {

		super();
		
		this.companyCode = companyCode;
		this.historyID = historyID;
		this.endDate = endDate;
		this.startDate = startDate;
	}

	public PositionHistory( String companyCode,GeneralDate endDate,GeneralDate startDate) {

		super();
		
		this.companyCode = companyCode;
		this.historyID = IdentifierUtil.randomUniqueId();
		this.endDate = endDate;
		this.startDate = startDate;
	}
	public static PositionHistory createFromJavaType( String companyCode,String historyID,GeneralDate endDate,GeneralDate startDate) {
		return new PositionHistory(companyCode,historyID,endDate,startDate);

	}

}
