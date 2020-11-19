package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

@Value
public class FullIpAddress implements DomainValue {

	private PartialIpAddress ipAddress1;
	
	private PartialIpAddress ipAddress2;
	
	private PartialIpAddress ipAddress3;
	
	private PartialIpAddress ipAddress4;
	
	public String getFullIpAddress() {
		return this.ipAddress1 + "." + this.ipAddress2 + "." + this.ipAddress3 + "." + this.ipAddress4;
	}
}
