package nts.uk.screen.at.app.query.kdp.kdp003.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.net.Ipv4Address;

@AllArgsConstructor
@Data
public class GetIPRequestDto {

	private String beforeParse;
	
	private Ipv4Address afterParse;
}
