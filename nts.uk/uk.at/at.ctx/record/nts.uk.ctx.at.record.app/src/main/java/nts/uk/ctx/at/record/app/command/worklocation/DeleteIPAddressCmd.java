package nts.uk.ctx.at.record.app.command.worklocation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.net.Ipv4Address;

@Getter
@Setter
@NoArgsConstructor
public class DeleteIPAddressCmd {
	private String workLocationCode;

	private int net1;

	private int net2;

	private int host1;

	private int host2;

	public DeleteIPAddressCmd(String workLocationCode, int net1, int net2, int host1, int host2) {
		super();
		this.workLocationCode = workLocationCode;
		this.net1 = net1;
		this.net2 = net2;
		this.host1 = host1;
		this.host2 = host2;
	}
	
	public  Ipv4Address toDomain() {
		return Ipv4Address.parse(this.net1 + "." + this.net2 + "." + this.host1 + "." + this.host2);
	}

}
