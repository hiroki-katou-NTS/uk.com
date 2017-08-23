package nts.uk.screen.at.infra.schedule.basicschedule;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaBasicScheduleScreenRepository extends JpaRepository implements BasicScheduleScreenRepository {

	private static final String SEL = "SELECT c FROM KscdtBasicSchedule c ";
	private static final String SEL_BY_LIST_SID_AND_DATE = SEL
			+ "WHERE c.kscdpBSchedulePK.sId IN :sId AND c.kscdpBSchedulePK.date >= :startDate AND c.kscdpBSchedulePK.date <= :endDate";

	private static BasicScheduleScreenDto toDto(KscdtBasicSchedule entity) {
		return new BasicScheduleScreenDto(entity.kscdpBSchedulePK.sId, entity.kscdpBSchedulePK.date, entity.workTypeCode,
				entity.workTimeCode);
	}

	@Override
	public List<BasicScheduleScreenDto> getByListSidAndDate(List<String> sId, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SEL_BY_LIST_SID_AND_DATE, KscdtBasicSchedule.class).setParameter("sId", sId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(x -> toDto(x));
	}
}
