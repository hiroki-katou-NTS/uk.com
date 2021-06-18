package nts.uk.ctx.at.request.pubimp.application;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.pub.application.RequestSettingPub;
import nts.uk.ctx.at.request.pub.application.export.AppReflectionSettingExport;

@Stateless
public class RequestSettingPubImpl implements RequestSettingPub {

//	@Inject
//	private RequestSettingRepository repo;

	@Override
	public Optional<AppReflectionSettingExport> getAppReflectionSetting(String cid) {
		return Optional.empty();
//		return repo.getAppReflectionSetting(cid)
//				.map(x -> new AppReflectionSettingExport(x.getScheReflectFlg(),
//						EnumAdaptor.valueOf(x.getPriorityTimeReflectFlag().value, PriorityTimeReflectAtrExport.class),
//						x.getAttendentTimeReflectFlg(),
//						EnumAdaptor.valueOf(x.getClassScheAchi().value, ClassifyScheAchieveAtrExport.class),
//						EnumAdaptor.valueOf(x.getReflecTimeofSche().value, ApplyTimeSchedulePriorityExport.class)));
	}

}
