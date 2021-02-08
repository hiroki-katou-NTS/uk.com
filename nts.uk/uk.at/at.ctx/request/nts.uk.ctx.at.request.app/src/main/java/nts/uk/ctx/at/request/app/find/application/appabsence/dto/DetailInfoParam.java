package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailInfoParam {
	private String companyId;
	private String appId;
	private AppDispInfoStartupCmd appDispInfoStartup;
}
