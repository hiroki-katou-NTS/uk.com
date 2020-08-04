package nts.uk.ctx.at.request.app.command.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppRecordImageDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampOutputDto;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterOrUpdateAppStampParam {
	
	private ApplicationDto applicationDto;
	
	private AppStampDto appStampDto;
	
	private AppRecordImageDto appRecordImageDto;
	
	private AppStampOutputDto appStampOutputDto;
	
	private Boolean recoderFlag;
}
