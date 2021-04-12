package nts.uk.ctx.at.record.app.command.worklocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.net.Ipv4Address;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIPAddressCmd {
	private String workLocationCode;

	private int net1;

	private int net2;

	private int host1;

	private int host2;
	
	public  Ipv4Address toDomain() {
		return Ipv4Address.parse(this.net1 + "." + this.net2 + "." + this.host1 + "." + this.host2);
	}

}
