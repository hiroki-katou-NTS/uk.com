package nts.uk.ctx.at.shared.infra.repository.worktimeset_old;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktimeset_old.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSetRepository;
import nts.uk.ctx.at.shared.infra.entity.worktimeset_old.KwtdtWorkTimeDay;
import nts.uk.ctx.at.shared.infra.entity.worktimeset_old.KwtspWorkTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktimeset_old.KwtstWorkTimeSet_old;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaWorkTimeSetRepository extends JpaRepository implements WorkTimeSetRepository{
	
	private final String findWorkTimeSetByCompanyID = "SELECT DISTINCT a FROM KwtstWorkTimeSet_old a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID";
	
	private final String findWorkTimeSetByList = "SELECT DISTINCT a FROM KwtstWorkTimeSet_old a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.siftCD IN :siftCDs";
	public static final int FIRST_ITEM = 0;
	public static final int SECOND_ITEM = 1;
	public static final int TRUE_NUMBER = 1;
	public static final int TIME_ZONE_SIZE_1 = 1;
	public static final int TIME_ZONE_SIZE_2 = 2;
	
	@Override
	public List<WorkTimeSet> findByCompanyID(String companyID) {
		return this.queryProxy().query(findWorkTimeSetByCompanyID, KwtstWorkTimeSet_old.class).setParameter("companyID", companyID).getList(x -> convertToDomainWorkTimeSet(x));
	}
	
	@Override
	public Optional<WorkTimeSet> findByCode(String companyID, String siftCD) {
		return this.queryProxy().find(new KwtspWorkTimeSetPK(companyID, siftCD), KwtstWorkTimeSet_old.class)
				.map(x -> convertToDomainWorkTimeSet(x));
	}

	@Override
	public List<WorkTimeSet> findByCodeList(String companyID, List<String> siftCDs) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		// query code 500 each, because limit record request by database 
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		// query code remain or when code list size < 500  
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		return result;
	}

	@Override
	public List<WorkTimeSet> findByStart(String companyID, List<String> siftCDs, int startClock) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		// query code 500 each, because limit record request by database
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		// query code remain or when code list size < 500 
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		// filter all WorkTimeSet result by startAtr and startClock, when atlest one WorkTimeDay is right, WorkTimeSet is counted
		List<WorkTimeSet> result1 = result.stream().filter(x -> {
			Boolean rs1 = false;
			Boolean rs2 = false;
			if (x.getPrescribedTimezoneSetting().getTimezone().size() >= TIME_ZONE_SIZE_1) {
				Timezone timezone1 = x.getPrescribedTimezoneSetting().getTimezone().get(FIRST_ITEM);
				if (timezone1 != null) {
					if (timezone1.getStart().equals(new TimeWithDayAttr(startClock))) {
						rs1 = true;
					}
				}
			}
			if (x.getPrescribedTimezoneSetting().getTimezone().size() >= TIME_ZONE_SIZE_2) {
				Timezone timezone2 = x.getPrescribedTimezoneSetting().getTimezone().get(SECOND_ITEM);
				if (timezone2 != null) {
					if (timezone2.getStart().equals(new TimeWithDayAttr(startClock))) {
						rs2 = true;
					}
				}
			}
			return rs1||rs2;
		}).collect(Collectors.toList());
		return result1;
	}

	@Override
	public List<WorkTimeSet> findByEnd(String companyID, List<String> siftCDs, int endClock) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		// query code 500 each, because limit record request by database
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		// query code remain or when code list size < 500 
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		// filter all WorkTimeSet result by endAtr and endClock, when atlest one WorkTimeDay is right, WorkTimeSet is counted
		List<WorkTimeSet> result1 = result.stream().filter(x -> {
			Boolean rs1 = false;
			Boolean rs2 = false;
			if (x.getPrescribedTimezoneSetting().getTimezone().size() >= TIME_ZONE_SIZE_1) {
				Timezone timezone1 = x.getPrescribedTimezoneSetting().getTimezone().get(FIRST_ITEM);
				if (timezone1 != null) {
					if (timezone1.getEnd().equals(new TimeWithDayAttr(endClock))) {
						rs1 = true;
					}
				}
			}
			if (x.getPrescribedTimezoneSetting().getTimezone().size() >= TIME_ZONE_SIZE_2) {
				Timezone timezone2 = x.getPrescribedTimezoneSetting().getTimezone().get(SECOND_ITEM);
				if (timezone2 != null) {
					if (timezone2.getEnd().equals(new TimeWithDayAttr(endClock))) {
						rs2 = true;
					}
				}
			}
			return rs1||rs2;
		}).collect(Collectors.toList());
		return result1;
	}

	@Override
	public List<WorkTimeSet> findByStartAndEnd(String companyID, List<String> siftCDs, int startClock, int endClock) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		// query code 500 each, because limit record request by database
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		// query code remain or when code list size < 500 
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet_old.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		// filter all WorkTimeSet result by startAtr, startClock, endAtr end startClock, when atlest one WorkTimeDay is right, WorkTimeSet is counted
		List<WorkTimeSet> result1 = result.stream().filter(x -> {
			Boolean rs1 = false;
			Boolean rs2 = false;
			if (x.getPrescribedTimezoneSetting().getTimezone().size() >= TIME_ZONE_SIZE_1) {
				Timezone timezone1 = x.getPrescribedTimezoneSetting().getTimezone().get(FIRST_ITEM);
				if (timezone1.getStart().equals(new TimeWithDayAttr(startClock))
						&& timezone1.getEnd().equals(new TimeWithDayAttr(endClock))) {
					rs1 = true;
				}
			}
			if (x.getPrescribedTimezoneSetting().getTimezone().size() >= TIME_ZONE_SIZE_2) {
				Timezone timezone2 = x.getPrescribedTimezoneSetting().getTimezone().get(SECOND_ITEM);
				if (timezone2.getStart().equals(new TimeWithDayAttr(startClock))
						&& timezone2.getEnd().equals(new TimeWithDayAttr(endClock))) {
					rs2 = true;
				}
			}
			return rs1||rs2;
		}).collect(Collectors.toList());
		return result1;
	}
	
	/**
	 * convert Work Time Set entity object to Work Time Set domain object
	 * @param kwtstWorkTimeSet Work Time Set entity object
	 * @return Work Time Set domain object
	 */
	private WorkTimeSet convertToDomainWorkTimeSet(KwtstWorkTimeSet_old kwtstWorkTimeSet) {
		if (kwtstWorkTimeSet == null) {
			return null;
		}
		TimeWithDayAttr morningEndTime = new TimeWithDayAttr(kwtstWorkTimeSet.morningEndTime);
		TimeWithDayAttr afternoonStartTime = new TimeWithDayAttr(kwtstWorkTimeSet.afternoonStartTime);
		List<Timezone> timezones = kwtstWorkTimeSet.kwtdtWorkTimeDay.stream().map(x -> convertToDomainTimezone(x))
				.collect(Collectors.toList());
		//TODO: set kwtstWorkTimeSet.additionSetID for PredetermineTime additionSetID
		return new WorkTimeSet(kwtstWorkTimeSet.kwtspWorkTimeSetPK.companyID, new AttendanceTime(kwtstWorkTimeSet.rangeTimeDay),
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.siftCD, null,
				kwtstWorkTimeSet.nightShiftAtr == TRUE_NUMBER ? true : false,
				new PrescribedTimezoneSetting(morningEndTime, afternoonStartTime, timezones),
				new TimeWithDayAttr(kwtstWorkTimeSet.startDateClock), kwtstWorkTimeSet.predetermineAtr == TRUE_NUMBER ? true : false);
	}
	
	/**
	 * convert Work Time Day entity object to Work Time Day domain object.
	 *
	 * @param kwtdtWorkTimeDay Work Time Day entity object
	 * @return Work Time Day domain object
	 */
	private Timezone convertToDomainTimezone(KwtdtWorkTimeDay kwtdtWorkTimeDay) {
		return new Timezone(EnumAdaptor.valueOf(kwtdtWorkTimeDay.useAtr, UseSetting.class),
				kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.timeNumberCnt, new TimeWithDayAttr(kwtdtWorkTimeDay.start),
				new TimeWithDayAttr(kwtdtWorkTimeDay.end));
	}
}
