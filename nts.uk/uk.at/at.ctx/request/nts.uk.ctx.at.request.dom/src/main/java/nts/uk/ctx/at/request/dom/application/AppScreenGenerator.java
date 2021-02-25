package nts.uk.ctx.at.request.dom.application;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;

/**
 * @author anhnm
 *
 */
public interface AppScreenGenerator {
	void generate(FileGeneratorContext exportContext, int appListAtr, AppListInfo lstApp, String programName);
}
