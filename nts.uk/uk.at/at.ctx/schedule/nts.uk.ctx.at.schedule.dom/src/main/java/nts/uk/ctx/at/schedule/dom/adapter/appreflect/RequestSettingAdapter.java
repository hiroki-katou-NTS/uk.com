package nts.uk.ctx.at.schedule.dom.adapter.appreflect;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;

public interface RequestSettingAdapter {
	Optional<SCAppReflectionSetting> getAppReflectionSetting(String companyId, ApplicationTypeShare appType);
}
