package nts.uk.ctx.at.schedule.infra.repository.shift.management.workavailability;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.Value;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailability;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityPk;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityShift;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect.KscdtAvailabilityTs;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkAvailabilityOfOneDayRepository extends JpaRepository implements WorkAvailabilityOfOneDayRepository{

	private static final String QUERY_SHIFT_EXPECTATION_ONE_DAY =
			"SELECT * FROM KSCDT_AVAILABILITY_SHIFT" + 
			" WHERE SID = @employeeID" + 
			" AND YMD = @expectingDate";
	
	private static final String QUERY_TS_EXPECTATION_ONE_DAY =
			"SELECT * FROM KSCDT_AVAILABILITY_TS" + 
			" WHERE SID = @employeeID" + 
			" AND YMD = @expectingDate";
	
	private static final String QUERY_EXPECTATION_PERIOD =
			"SELECT * FROM KSCDT_AVAILABILITY" + 
			" WHERE SID = @employeeID" + 
			" AND YMD between @startDate and @endDate" + 
			" ORDER BY YMD ASC";
	
	private static final String QUERY_SHIFT_EXPECTATION_PERIOD =
			"SELECT * FROM KSCDT_AVAILABILITY_SHIFT" + 
			" WHERE SID = @employeeID" + 
			" AND YMD between @startDate and @endDate" +
			" ORDER BY YMD ASC";
	
	private static final String QUERY_TS_EXPECTATION_PERIOD =
			"SELECT * FROM KSCDT_AVAILABILITY_TS" + 
			" WHERE SID = @employeeID" + 
			" AND YMD between @startDate and @endDate" + 
			" ORDER BY YMD ASC";
	
	@Override
	public Optional<WorkAvailabilityOfOneDay> get(String employeeID, GeneralDate expectingDate) {
		Optional<KscdtAvailability> availability = this.queryProxy()
				.find(new KscdtAvailabilityPk(employeeID, expectingDate), KscdtAvailability.class);
		
		if (!availability.isPresent()) {
			return Optional.empty();
		}
		
		List<ShiftMasterCode> shiftMasterCodeList = 
				new NtsStatement(QUERY_SHIFT_EXPECTATION_ONE_DAY, this.jdbcProxy())
						.paramString("employeeID", employeeID)
						.paramDate("expectingDate", expectingDate)
						.getList(s -> new ShiftMasterCode(s.getString("SHIFT_MASTER_CD")));
		
		List<TimeSpanForCalc> timeZoneList = 
				new NtsStatement(QUERY_TS_EXPECTATION_ONE_DAY, this.jdbcProxy())
				.paramString("employeeID", employeeID)
				.paramDate("expectingDate", expectingDate)
				.getList(t -> new TimeSpanForCalc(
						new TimeWithDayAttr(t.getInt("START_CLOCK")), 
						new TimeWithDayAttr(t.getInt("END_CLOCK"))));
		
		return Optional.of(availability.get().toDomain(shiftMasterCodeList, timeZoneList));
		
	}

	@Override
	public List<WorkAvailabilityOfOneDay> getList(String employeeID, DatePeriod period) {
		
		List<KscdtAvailability> availabilityList = new NtsStatement(QUERY_EXPECTATION_PERIOD, this.jdbcProxy())
												.paramString("employeeID", employeeID)
												.paramDate("startDate", period.start())
												.paramDate("endDate", period.end())
												.getList( KscdtAvailability.mapper );
		
		List<KscdtAvailabilityShift> availabilityShiftList = new NtsStatement(QUERY_SHIFT_EXPECTATION_PERIOD, this.jdbcProxy())
													.paramString("employeeID", employeeID)
													.paramDate("startDate", period.start())
													.paramDate("endDate", period.end())
													.getList( KscdtAvailabilityShift.mapper );
		
		List<KscdtAvailabilityTs> availabilityTsList = new NtsStatement(QUERY_TS_EXPECTATION_PERIOD, this.jdbcProxy())
													.paramString("employeeID", employeeID)
													.paramDate("startDate", period.start())
													.paramDate("endDate", period.end())
													.getList( KscdtAvailabilityTs.mapper );
													
		return availabilityList.stream().map( a -> {
			
			List<ShiftMasterCode> shiftMasterCodeList = availabilityShiftList.stream()
														.filter( s -> s.pk.expectingDate == a.pk.expectingDate)
														.map( s -> new ShiftMasterCode(s.pk.shiftMasterCode))
														.collect(Collectors.toList());
			
			List<TimeSpanForCalc> timeZoneList = availabilityTsList.stream() 
													.filter(t -> t.pk.expectingDate == a.pk.expectingDate)
													.map( t -> new TimeSpanForCalc(
															new TimeWithDayAttr(t.pk.startClock), 
															new TimeWithDayAttr(t.pk.endClock)))
													.collect(Collectors.toList());
			
			return a.toDomain(shiftMasterCodeList, timeZoneList);
			
		}).collect(Collectors.toList());
	}

	@Override
	public void add(WorkAvailabilityOfOneDay expectation) {
		Entities entities = toEntities(expectation);
		
		this.commandProxy().insert(entities.getAvailability());
		this.commandProxy().insertAll(entities.getAvailabilityShiftList());
		this.commandProxy().insertAll(entities.getAvailabilityTSList());
	}

	@Override
	public void update(WorkAvailabilityOfOneDay expectation) {
		Entities entities = toEntities(expectation);
		
		this.commandProxy().update(entities.getAvailability());
		this.commandProxy().updateAll(entities.getAvailabilityShiftList());
		this.commandProxy().updateAll(entities.getAvailabilityTSList());
		
	}

	@Override
	public boolean exists(String employeeID, GeneralDate expectingDate) {
		
		return this.queryProxy()
				.find(new KscdtAvailabilityPk(employeeID, expectingDate), KscdtAvailability.class)
				.isPresent();
		
	}
	
	private Entities toEntities(WorkAvailabilityOfOneDay expectation) {
		KscdtAvailability availability = KscdtAvailability.fromDomain(expectation); 
		List<KscdtAvailabilityShift> availabilityShiftList = KscdtAvailabilityShift.fromDomain(expectation);
		List<KscdtAvailabilityTs> availabilityTSList = KscdtAvailabilityTs.fromDomain(expectation);
		
		return new Entities(availability, availabilityShiftList, availabilityTSList);
	}
	
	@Value
	static class Entities {
		
		KscdtAvailability availability;
		
		List<KscdtAvailabilityShift> availabilityShiftList;
		
		List<KscdtAvailabilityTs> availabilityTSList;
	}

}
