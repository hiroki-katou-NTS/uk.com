package nts.uk.ctx.at.schedule.infra.repository.shift.management.workexpect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.ShiftExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.TimeZoneExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationMemo;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailability;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityShift;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityShiftPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityTs;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityTsPk;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkExpectationOfOneDayRepository extends JpaRepository implements WorkExpectationOfOneDayRepository{

	private static final String QUERY_SHIFT_EXPECTATION_ONE_DAY =
			"SELECT s FROM KSCDT_AVAILABILITY_SHIFT s" + 
			" WHERE s.pk.employeeID = :employeeID" + 
			" AND s.pk.expectingDate = :expectingDate";
	
	private static final String QUERY_TS_EXPECTATION_ONE_DAY =
			"SELECT t FROM KSCDT_AVAILABILITY_TS t" + 
			" WHERE t.pk.employeeID = :employeeID" + 
			" AND t.pk.expectingDate = :expectingDate";
	
	private static final String QUERY_EXPECTATION_PERIOD =
			"SELECT s FROM KSCDT_AVAILABILITY s" + 
			" WHERE s.pk.employeeID = :employeeID" + 
			" AND s.pk.expectingDate >= :startDate" +
			" AND s.pk.expectingDate <= :endDate";
	
	private static final String QUERY_SHIFT_EXPECTATION_PERIOD =
			"SELECT s FROM KSCDT_AVAILABILITY_SHIFT s" + 
			" WHERE s.pk.employeeID = :employeeID" + 
			" AND s.pk.expectingDate >= :startDate" +
			" AND s.pk.expectingDate <= :endDate";
	
	private static final String QUERY_TS_EXPECTATION_PERIOD =
			"SELECT t FROM KSCDT_AVAILABILITY_TS t" + 
			" WHERE t.pk.employeeID = :employeeID" + 
			" AND s.pk.expectingDate >= :startDate" +
			" AND s.pk.expectingDate <= :endDate";
	
	@Override
	public Optional<WorkExpectationOfOneDay> get(String employeeID, GeneralDate expectingDate) {
		Optional<KscdtAvailability> availability = this.queryProxy()
				.find(new KscdtAvailabilityPk(employeeID, expectingDate), KscdtAvailability.class);
		
		if (!availability.isPresent()) {
			return Optional.empty();
		}
		
		List<ShiftMasterCode> shiftMasterCodeList = this.queryProxy()
				.query(QUERY_SHIFT_EXPECTATION_ONE_DAY, KscdtAvailabilityShift.class)
				.setParameter("employeeID", employeeID)
				.setParameter("expectingDate", expectingDate)
				.getList()
				.stream()
				.map( s -> new ShiftMasterCode(s.pk.shiftMasterCode))
				.collect(Collectors.toList());
		
		List<TimeSpanForCalc> timeZoneList = this.queryProxy()
				.query(QUERY_TS_EXPECTATION_ONE_DAY, KscdtAvailabilityTs.class)
				.setParameter("employeeID", employeeID)
				.setParameter("expectingDate", expectingDate)
				.getList()
				.stream()
				.map( t -> new TimeSpanForCalc(new TimeWithDayAttr(t.pk.startClock), new TimeWithDayAttr(t.pk.endClock)))
				.collect(Collectors.toList());
		
		return Optional.of(WorkExpectationOfOneDay.create(
				employeeID, 
				expectingDate, 
				new WorkExpectationMemo(availability.get().memo), 
				EnumAdaptor.valueOf(availability.get().method, AssignmentMethod.class), 
				shiftMasterCodeList, 
				timeZoneList));
		
	}

	@Override
	public List<WorkExpectationOfOneDay> getList(String employeeID, DatePeriod period) {
		
		List<KscdtAvailability> availabilityList = this.queryProxy()
				.query(QUERY_EXPECTATION_PERIOD, KscdtAvailability.class)
				.setParameter("employeeID", employeeID)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList();
		
		Map<GeneralDate, List<KscdtAvailabilityShift>> availabilityShiftMap = this.queryProxy()
				.query(QUERY_SHIFT_EXPECTATION_PERIOD, KscdtAvailabilityShift.class)
				.setParameter("employeeID", employeeID)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList()
				.stream()
				.collect(Collectors.groupingBy(p -> p.pk.expectingDate));
		
		Map<GeneralDate, List<KscdtAvailabilityTs>> availabilityTsMap = this.queryProxy()
				.query(QUERY_TS_EXPECTATION_PERIOD, KscdtAvailabilityTs.class)
				.setParameter("employeeID", employeeID)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList()
				.stream()
				.collect(Collectors.groupingBy(p -> p.pk.expectingDate));
		
		return availabilityList.stream().map( a -> {
			
			AssignmentMethod assignmentMethod = EnumAdaptor.valueOf(a.method, AssignmentMethod.class);
			
			List<ShiftMasterCode> shiftMasterCodeList = new ArrayList<>();
			List<TimeSpanForCalc> timeZoneList = new ArrayList<>();
			
			switch (assignmentMethod) {
				case SHIFT:
					shiftMasterCodeList = availabilityShiftMap
												.get(a.pk.expectingDate)
												.stream()
												.map( s -> new ShiftMasterCode(s.pk.shiftMasterCode))
												.collect(Collectors.toList());
					break;
		
				case TIME_ZONE:
					timeZoneList = availabilityTsMap
												.get(a.pk.expectingDate)
												.stream()
												.map( t -> new TimeSpanForCalc(
														new TimeWithDayAttr(t.pk.startClock), 
														new TimeWithDayAttr(t.pk.endClock)))
												.collect(Collectors.toList());
					break;
				case HOLIDAY:
				default:
					break;
			}
			
			return WorkExpectationOfOneDay.create(
					employeeID, 
					a.pk.expectingDate, 
					new WorkExpectationMemo(a.memo), 
					assignmentMethod, 
					shiftMasterCodeList, 
					timeZoneList);
			
		}).collect(Collectors.toList());
	}

	@Override
	public void add(WorkExpectationOfOneDay expectation) {
		Entities entities = toEntities(expectation);
		
		this.commandProxy().insert(entities.getAvailability());
		this.commandProxy().insertAll(entities.getAvailabilityShiftList());
		this.commandProxy().insertAll(entities.getAvailabilityTSList());
	}

	@Override
	public void update(WorkExpectationOfOneDay expectation) {
		Entities entities = toEntities(expectation);
		
		this.commandProxy().update(entities.getAvailability());
		this.commandProxy().updateAll(entities.getAvailabilityShiftList());
		this.commandProxy().updateAll(entities.getAvailabilityTSList());
		
	}

	@Override
	public boolean exists(String employeeID, GeneralDate expectingDate) {
		return this.queryProxy().find(new KscdtAvailabilityPk(employeeID, expectingDate), KscdtAvailability.class).isPresent();
		
	}
	
	private Entities toEntities(WorkExpectationOfOneDay expectation) {
		KscdtAvailability availability = new KscdtAvailability(
				new KscdtAvailabilityPk(expectation.getEmployeeId(), expectation.getExpectingDate()), 
				expectation.getMemo().v(),
				expectation.getWorkExpectation().getAssignmentMethod().value);
		
		switch (expectation.getWorkExpectation().getAssignmentMethod()) {
			case SHIFT:
				ShiftExpectation shiftExpectation =  (ShiftExpectation) expectation.getWorkExpectation();
				List<KscdtAvailabilityShift> availabilityShiftList = shiftExpectation.getWorkableShiftCodeList().stream()
																		.map(s -> new KscdtAvailabilityShift(
																					AppContexts.user().companyId(),
																					new KscdtAvailabilityShiftPk(
																							expectation.getEmployeeId(), 
																							expectation.getExpectingDate(), s.v()) 
																					))
																		.collect(Collectors.toList());
				return new Entities(availability, availabilityShiftList, new ArrayList<>());
			case TIME_ZONE:
				TimeZoneExpectation timeZoneExpectation = (TimeZoneExpectation) expectation.getWorkExpectation();
				List<KscdtAvailabilityTs> availabilityTSList = timeZoneExpectation.getWorkableTimeZoneList().stream()
																		.map(t -> new KscdtAvailabilityTs(
																						AppContexts.user().companyId(),
																						new KscdtAvailabilityTsPk(
																								expectation.getEmployeeId(), 
																								expectation.getExpectingDate(), 
																								t.startValue().intValue(), 
																								t.endValue().intValue()) 
																						))
																		.collect(Collectors.toList());
				return new Entities(availability, new ArrayList<>(), availabilityTSList);
			case HOLIDAY:
			default:
				return new Entities(availability, new ArrayList<>(), new ArrayList<>());
		}
	}
	
	@Value
	class Entities {
		KscdtAvailability availability;
		
		List<KscdtAvailabilityShift> availabilityShiftList;
		
		List<KscdtAvailabilityTs> availabilityTSList;
	}

}
