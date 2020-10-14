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
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.HolidayExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.ShiftExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.TimeZoneExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationMemo;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;
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
	
	public static KscdtAvailability fromDomain(WorkExpectationOfOneDay expectation) {
		return new KscdtAvailability(
				new KscdtAvailabilityPk(expectation.getEmployeeId(), expectation.getExpectingDate()), 
				expectation.getMemo().v(),
				expectation.getWorkExpectation().getAssignmentMethod().value);
	}
	
	public WorkExpectationOfOneDay toDomain(List<ShiftMasterCode> shiftMasterCodeList,
			List<TimeSpanForCalc> timeZoneList) {
		
		AssignmentMethod asignmentMethod = AssignmentMethod.of(this.method);
		WorkExpectation workExpectation;
		switch (asignmentMethod) {
			case SHIFT:
				workExpectation = new ShiftExpectation(shiftMasterCodeList);
				break;
			case TIME_ZONE:
				workExpectation = new TimeZoneExpectation(timeZoneList);
				break;
			case HOLIDAY:
			default:
				workExpectation = new HolidayExpectation();
				break;
		}
		
		return new WorkExpectationOfOneDay(
				this.pk.employeeID,
				this.pk.expectingDate, 
				new WorkExpectationMemo(this.memo), 
				workExpectation);
				
	}

}
