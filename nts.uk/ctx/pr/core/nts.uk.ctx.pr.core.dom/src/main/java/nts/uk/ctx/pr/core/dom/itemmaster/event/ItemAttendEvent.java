package nts.uk.ctx.pr.core.dom.itemmaster.event;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;

@Getter
public class ItemAttendEvent extends DomainEvent {
	private String companyCode;
	private List<String> itemCodes;
	private AvePayAtr avePayAtr;
	
	public ItemAttendEvent(String companyCode, List<String> itemCodes, AvePayAtr avePayAtr) {
		super();
		this.companyCode = companyCode;
		this.itemCodes = itemCodes;
		this.avePayAtr = avePayAtr;
	}
	
}
