package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import java.util.List;
import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityByHoliday;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityByShiftMaster;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityByTimeZone;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailability;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityMemo;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY")
@AllArgsConstructor
public class KscdtAvailability extends ContractCompanyUkJpaEntity{
	
	public static final Function<NtsResultRecord, KscdtAvailability> mapper = s ->  
			new JpaEntityMapper<>(KscdtAvailability.class).toEntity(s);
	
	@EmbeddedId
	public KscdtAvailabilityPk pk;
	
	@Column(name = "MEMO")
	public String memo;
	
	@Column(name = "SPECIFIED_METHOD")
	public int method;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KscdtAvailability fromDomain(WorkAvailabilityOfOneDay expectation) {
		return new KscdtAvailability(
				new KscdtAvailabilityPk(expectation.getEmployeeId(), expectation.getExpectingDate()), 
				expectation.getMemo().v(),
				expectation.getWorkExpectation().getAssignmentMethod().value);
	}
	
	public WorkAvailabilityOfOneDay toDomain(List<ShiftMasterCode> shiftMasterCodeList,
			List<TimeSpanForCalc> timeZoneList) {
		
		AssignmentMethod asignmentMethod = AssignmentMethod.of(this.method);
		WorkAvailability workExpectation;
		switch (asignmentMethod) {
			case SHIFT:
				workExpectation = new WorkAvailabilityByShiftMaster(shiftMasterCodeList);
				break;
			case TIME_ZONE:
				workExpectation = new WorkAvailabilityByTimeZone(timeZoneList);
				break;
			case HOLIDAY:
			default:
				workExpectation = new WorkAvailabilityByHoliday();
				break;
		}
		
		return new WorkAvailabilityOfOneDay(
				this.pk.employeeID,
				this.pk.expectingDate, 
				new WorkAvailabilityMemo(this.memo), 
				workExpectation);
				
	}

}
