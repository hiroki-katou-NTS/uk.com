package nts.uk.ctx.hr.develop.ws.test.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class ParamCommon {
	
	public String companyId;
	
	public String historyId;
	
	public GeneralDate baseDate;

}
