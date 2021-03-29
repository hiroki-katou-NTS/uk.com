package nts.uk.ctx.at.request.pubimp.application;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.pub.application.RequestSettingPub;
import nts.uk.ctx.at.request.pub.application.export.AppReflectionSettingExport;
import nts.uk.ctx.at.request.pub.application.export.ApplyTimeSchedulePriorityExport;
import nts.uk.ctx.at.request.pub.application.export.ClassifyScheAchieveAtrExport;
import nts.uk.ctx.at.request.pub.application.export.PriorityTimeReflectAtrExport;

@Stateless
public class RequestSettingPubImpl implements RequestSettingPub {

	@Inject
	private RequestSettingRepository repo;

	@Override
	public Optional<AppReflectionSettingExport> getAppReflectionSetting(String cid) {

		return repo.getAppReflectionSetting(cid)
				.map(x -> new AppReflectionSettingExport(x.getScheReflectFlg(),
						EnumAdaptor.valueOf(x.getPriorityTimeReflectFlag().value, PriorityTimeReflectAtrExport.class),
						x.getAttendentTimeReflectFlg(),
						EnumAdaptor.valueOf(x.getClassScheAchi().value, ClassifyScheAchieveAtrExport.class),
						EnumAdaptor.valueOf(x.getReflecTimeofSche().value, ApplyTimeSchedulePriorityExport.class)));
	}

}
