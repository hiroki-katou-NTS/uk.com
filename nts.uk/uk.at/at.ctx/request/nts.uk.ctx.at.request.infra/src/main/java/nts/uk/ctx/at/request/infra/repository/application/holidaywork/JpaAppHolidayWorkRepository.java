package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHolidayWork;
@Stateless
public class JpaAppHolidayWorkRepository extends JpaRepository implements AppHolidayWorkRepository{
	private static final String FIND_ALL = "SELECT e FROM KrqdtAppHolidayWork e";

	private static final String FIND_BY_APPID;
	static {
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtAppHolidayWorkPk.cid = :companyID");
		query.append(" AND e.krqdtAppHolidayWorkPK.appId = :appID");
		FIND_BY_APPID = query.toString();
	}
	@Override
	public Optional<AppHolidayWork> getAppHolidayWork(String companyID, String appID) {
		
		return this.queryProxy().query(FIND_BY_APPID, KrqdtAppHolidayWork.class)
				.setParameter("companyID", companyID).setParameter("appID", appID).getSingle( e -> convertToDomain(e));
	}
	private AppHolidayWork convertToDomain(KrqdtAppHolidayWork entity) {
		return AppHolidayWork.createSimpleFromJavaType(entity.getKrqdtAppHolidayWorkPK().getCid(),
				entity.getKrqdtAppHolidayWorkPK().getAppId(), entity.getWorkTypeCode(),
				entity.getWorkTimeCode(), entity.getWorkClockStart1(), 
				entity.getWorkClockEnd1(), 
				entity.getWorkClockStart2(), 
				entity.getWorkClockEnd2(),
				entity.getGoAtr1(),
				entity.getBackAtr1(),
				entity.getGoAtr2(),
				entity.getBackAtr2(),
				entity.getDivergenceReason(), 
				entity.getHolidayShiftNight());
	}
}
