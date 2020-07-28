package nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Getter
@Setter
@AllArgsConstructor
public class PageInitDto {

	private String appId;

	private AppDispInfoStartupDto infoStartup;
}
