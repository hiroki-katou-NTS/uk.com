package nts.uk.ctx.at.request.app.find.application;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppListInfoDto;

@Data
public class AppScreenQuery {
	public int appListAtr;
	public AppListInfoDto lstApp;
}
