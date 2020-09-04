package nts.uk.ctx.at.schedule.ac.request.reflect;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.pub.application.RequestSettingPub;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.RequestSettingAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCAppReflectionSetting;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCApplyTimeSchedulePriority;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCClassifyScheAchieveAtr;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCPriorityTimeReflectAtr;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;

public class RequestSettingAdapterImpl implements RequestSettingAdapter {

	@Inject
	private RequestSettingPub requestSettingPub;

	@Override
	public Optional<SCAppReflectionSetting> getAppReflectionSetting(String companyId, ApplicationTypeShare appType) {
		return requestSettingPub.getAppReflectionSetting(companyId)
				.map(x -> new SCAppReflectionSetting(x.getScheReflectFlg(),
						EnumAdaptor.valueOf(x.getPriorityTimeReflectFlag().value, SCPriorityTimeReflectAtr.class),
						x.getAttendentTimeReflectFlg(),
						EnumAdaptor.valueOf(x.getClassScheAchi().value, SCClassifyScheAchieveAtr.class),
						EnumAdaptor.valueOf(x.getReflecTimeofSche().value, SCApplyTimeSchedulePriority.class)));
	}

}
