package nts.uk.ctx.at.request.app.find.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampOutputDto;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeforeRegisterOrUpdateParam {
	
	private String companyId;
	
	private Boolean agentAtr;
	
	private ApplicationDto applicationDto;
	
	private AppStampOutputDto appStampOutputDto;
	
}
