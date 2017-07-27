package nts.uk.screen.at.infra.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KsuppBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KsuptBasicSchedule;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaBasicScheduleScreenRepository extends JpaRepository implements BasicScheduleScreenRepository {

	private static final String SEL = "SELECT c FROM KsuptBasicSchedule c ";
	private static final String SEL_BY_LIST_SID_AND_DATE = SEL
			+ "WHERE c.ksuppBSchedulePK.sId IN :sId AND c.ksuppBSchedulePK.date >= :startDate AND c.ksuppBSchedulePK.date <= :endDate";

	private static BasicScheduleScreenDto toDto(KsuptBasicSchedule entity) {
		return new BasicScheduleScreenDto(entity.ksuppBSchedulePK.sId, entity.ksuppBSchedulePK.date, entity.workTypeCd,
				entity.workTimeCd);
	}

	@Override
	public List<BasicScheduleScreenDto> getByListSidAndDate(List<String> sId, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SEL_BY_LIST_SID_AND_DATE, KsuptBasicSchedule.class).setParameter("sId", sId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(x -> toDto(x));
	}

	@Override
	public Optional<BasicScheduleScreenDto> getByPK(String sId, GeneralDate date) {
		return this.queryProxy().find(new KsuppBasicSchedulePK(sId, date), KsuptBasicSchedule.class)
				.map(x -> toDto(x));
	}

}
