package nts.uk.ctx.sys.auth.ws.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataRequest {
	
	private String userId;
	
	private String roleId;

}
