package nts.uk.ctx.at.request.app.find.application.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampOutputDto;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeDateParam {
	
	private String companyId;
	
	private AppStampOutputDto appStampOutputDto;
	
	private List<String> date;
	
	private AppDispInfoStartupDto appDispInfoStartupDto;
}
