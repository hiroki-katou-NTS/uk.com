package nts.uk.ctx.at.schedule.dom.scheduleitemmanagement;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class ScheItemOrder extends AggregateRoot {
	/* 会社ID */
    public String companyId;
    
    /*予定項目ID*/
    public String scheduleItemId;
    
    /* 順番 */
    public int dispOrder;

	public ScheItemOrder(String companyId, String scheduleItemId, int dispOrder) {
		
		this.companyId = companyId;
		this.scheduleItemId = scheduleItemId;
		this.dispOrder = dispOrder;
	}
    
    
}
