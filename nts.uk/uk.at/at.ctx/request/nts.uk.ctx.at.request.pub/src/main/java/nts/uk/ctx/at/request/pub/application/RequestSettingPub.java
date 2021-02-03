package nts.uk.ctx.at.request.pub.application;

import java.util.Optional;

import nts.uk.ctx.at.request.pub.application.export.AppReflectionSettingExport;

public interface RequestSettingPub {
	public Optional<AppReflectionSettingExport> getAppReflectionSetting(String cid);
}
