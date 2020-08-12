package nts.uk.ctx.at.request.app.find.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartAppStampParam {
	
	private String companyId;
		
	private String date;
	
	private AppDispInfoStartupDto appDispInfoStartupDto;
	
	private Boolean recoderFlag;
}
