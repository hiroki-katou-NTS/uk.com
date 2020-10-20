package nts.uk.ctx.at.record.ac.application.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RCAppReflectionSetting;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RCApplyTimeSchedulePriority;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RCClassifyScheAchieveAtr;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RCPriorityTimeReflectAtr;
import nts.uk.ctx.at.record.dom.adapter.application.reflect.RequestSettingAdapter;
import nts.uk.ctx.at.request.pub.application.RequestSettingPub;

@Stateless
public class RequestSettingAdapterImpl implements RequestSettingAdapter {

	@Inject
	private RequestSettingPub requestSettingPub;

	@Override
	public Optional<RCAppReflectionSetting> getAppReflectionSetting(String cid) {
		return requestSettingPub.getAppReflectionSetting(cid)
				.map(x -> new RCAppReflectionSetting(x.getScheReflectFlg(),
						EnumAdaptor.valueOf(x.getPriorityTimeReflectFlag().value, RCPriorityTimeReflectAtr.class),
						x.getAttendentTimeReflectFlg(),
						EnumAdaptor.valueOf(x.getClassScheAchi().value, RCClassifyScheAchieveAtr.class),
						EnumAdaptor.valueOf(x.getReflecTimeofSche().value, RCApplyTimeSchedulePriority.class)));
	}

}
