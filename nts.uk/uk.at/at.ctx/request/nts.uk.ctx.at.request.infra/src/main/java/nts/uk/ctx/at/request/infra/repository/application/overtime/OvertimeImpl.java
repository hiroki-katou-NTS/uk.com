package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertime;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimePK;

@Stateless
public class OvertimeImpl extends JpaRepository implements OvertimeRepository {
	private static final String FIND_ALL = "SELECT e FROM KrqdtAppOvertime e";

	private static final String FIND_BY_APPID;
	static {
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtAppOvertimePK.cid = :companyID");
		query.append(" AND e.krqdtAppOvertimePK.appId = :appID");
		FIND_BY_APPID = query.toString();
	}

	@Override
	public Optional<AppOverTime> getAppOvertime(String companyID, String appID) {

		return this.queryProxy().query(FIND_BY_APPID, KrqdtAppOvertime.class).setParameter("companyID", companyID)
				.setParameter("appID", appID).getSingle(e -> convertToDomain(e));
	}
	
	@Override
	public void Add(AppOverTime domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	private KrqdtAppOvertime toEntity(AppOverTime domain) {
		return new KrqdtAppOvertime(new KrqdtAppOvertimePK(domain.getCompanyID(), domain.getAppID()),
				domain.getOverTimeAtr().value, domain.getWorkTypeCode().v(), domain.getSiftCode().v(),
				domain.getWorkClockFrom1(), domain.getWorkClockTo1(), domain.getWorkClockFrom2(),
				domain.getWorkClockTo2(), domain.getDivergenceReasonID(), domain.getFlexExessTime(),
				domain.getOverTimeShiftNight());
	}

	private AppOverTime convertToDomain(KrqdtAppOvertime entity) {
		return AppOverTime.createSimpleFromJavaType(entity.getKrqdtAppOvertimePK().getCid(),
				entity.getKrqdtAppOvertimePK().getAppId(), entity.getOvertimeAtr(), entity.getWorkTypeCode(),
				entity.getSiftCode(), entity.getWorkClockFrom1(), entity.getWorkClockTo1(), entity.getWorkClockFrom2(),
				entity.getWorkClockTo2(), entity.getDivergenceReasonId(), entity.getFlexExcessTime(),
				entity.getOvertimeShiftNight());
	}

}
