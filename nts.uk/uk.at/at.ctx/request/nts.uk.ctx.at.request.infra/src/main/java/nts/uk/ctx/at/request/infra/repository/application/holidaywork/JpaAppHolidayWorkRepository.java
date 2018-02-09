package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHolidayWork;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHolidayWorkPK;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtHolidayWorkInput;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtHolidayWorkInputPK;
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
	@Override
	public void Add(AppHolidayWork domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	private KrqdtAppHolidayWork toEntity(AppHolidayWork domain) {
		List<KrqdtHolidayWorkInput> overtimeInputs = domain.getHolidayWorkInputs().stream()
				.map(item -> {
					KrqdtHolidayWorkInputPK pk =  new KrqdtHolidayWorkInputPK(item.getCompanyID(), item.getAppID(),
							item.getAttendanceType().value, item.getFrameNo());
					return new KrqdtHolidayWorkInput(pk, item.getStartTime().v(), item.getEndTime().v(),
							item.getApplicationTime().v());
				})
				.collect(Collectors.toList());

		return new KrqdtAppHolidayWork(new KrqdtAppHolidayWorkPK(domain.getCompanyID(), domain.getAppID()),
				domain.getVersion(), domain.getWorkTypeCode().v(), domain.getWorkTimeCode().v(),
				domain.getWorkClock1().getStartTime().v(),
				domain.getWorkClock1().getEndTime().v(),
				domain.getWorkClock1().getGoAtr().value,
				domain.getWorkClock1().getBackAtr().value,
				domain.getWorkClock2().getStartTime().v(),
				domain.getWorkClock2().getEndTime().v(),
				domain.getWorkClock2().getGoAtr().value,
				domain.getWorkClock2().getBackAtr().value,
				domain.getDivergenceReason(),
				domain.getHolidayShiftNight(), overtimeInputs);
	}
}
