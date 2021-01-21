package nts.uk.ctx.at.request.app.find.application;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppListInfoCmd;

@Data
public class AppScreenQuery {
	public int appListAtr;
	public AppListInfoCmd lstApp;
	public String programName;
}
