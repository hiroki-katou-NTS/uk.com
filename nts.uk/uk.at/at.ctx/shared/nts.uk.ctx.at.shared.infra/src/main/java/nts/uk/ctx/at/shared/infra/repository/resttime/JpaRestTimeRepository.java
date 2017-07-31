/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.resttime;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.resttime.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.resttime.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.resttime.HalfDayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.dom.resttime.HolidayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.dom.resttime.RestTimeRepository;
import nts.uk.ctx.at.shared.dom.resttime.TimeTable;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;
import nts.uk.ctx.at.shared.infra.entity.resttime.KwtmtRestTime;
import nts.uk.ctx.at.shared.infra.entity.resttime.KwtmtRestTimePK_;
import nts.uk.ctx.at.shared.infra.entity.resttime.KwtmtRestTime_;

/**
 * The Class JpaRestTimeRepository.
 */
@Stateless
public class JpaRestTimeRepository extends JpaRepository implements RestTimeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.resttime.RestTimeRepository#getResttime(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public FixedWorkSetting getResttime(String companyId, String selectedSiftCode) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KwtmtRestTime> query = builder.createQuery(KwtmtRestTime.class);
		Root<KwtmtRestTime> root = query.from(KwtmtRestTime.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KwtmtRestTime_.kwtmtRestTimePK).get(KwtmtRestTimePK_.cid), companyId));
		predicateList.add(
				builder.equal(root.get(KwtmtRestTime_.kwtmtRestTimePK).get(KwtmtRestTimePK_.siftCd),
						selectedSiftCode));

		query.where(predicateList.toArray(new Predicate[] {}));
		// KwtmtRestTime result = em.createQuery(query).getSingleResult();
		// if (result == null) {
		// return null;
		// }

		// TODO: remove fake data.
		FixedWorkSetting fws = new FixedWorkSetting();
		List<TimeTable> timeTable = new ArrayList<>();
		switch (selectedSiftCode) {
		case "AAA":
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 100,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 200,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 300,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 400,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 500,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 600,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 799,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 800,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 900,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 1000,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			break;

		case "AAD":
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 102,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 220,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 320,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 420,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 520,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 620,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 729,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 820,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 920,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_NextDay, 1020,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			break;
			
		case "AAG":
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 130,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 230,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 330,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 400,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 530,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 600,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 739,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 830,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 930,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_SkipDay, 1030,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			break;
			
		default:
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 103,
					TimeDayAtr.Enum_DayAtr_SkipDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 240,
					TimeDayAtr.Enum_DayAtr_NextDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 340,
					TimeDayAtr.Enum_DayAtr_SkipDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 440, TimeDayAtr.Enum_DayAtr_Day,
					1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 540,
					TimeDayAtr.Enum_DayAtr_NextDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 640,
					TimeDayAtr.Enum_DayAtr_NextDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 749,
					TimeDayAtr.Enum_DayAtr_PreviousDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 840,
					TimeDayAtr.Enum_DayAtr_SkipDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 940,
					TimeDayAtr.Enum_DayAtr_SkipDay, 1000));
			timeTable.add(new TimeTable(TimeDayAtr.Enum_DayAtr_Day, 1040,
					TimeDayAtr.Enum_DayAtr_SkipDay, 1000));
			break;
		}

		fws.setHalfDayAttendanceWorkTimeZone(
				new HalfDayAttendanceWorkTimeZone(new BreakTimeZone(timeTable)));
		fws.setHolidayAttendanceWorkTimeZone(
				new HolidayAttendanceWorkTimeZone(new BreakTimeZone(timeTable)));

		return fws;
	}

}
