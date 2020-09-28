package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.ShiftExpectation;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY_SHIFT")
@AllArgsConstructor
public class KscdtAvailabilityShift extends ContractUkJpaEntity{
	
	public static final Function<NtsResultRecord, KscdtAvailabilityShift> mapper = s ->  
			new JpaEntityMapper<>(KscdtAvailabilityShift.class).toEntity(s);
	
	@Column(name = "CID")
	public String companyId;
	
	@Getter
	@EmbeddedId
	public KscdtAvailabilityShiftPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static List<KscdtAvailabilityShift> fromDomain(
			WorkExpectationOfOneDay expectation) {
		
		if (expectation.getWorkExpectation().getAssignmentMethod() != AssignmentMethod.SHIFT) {
			return new ArrayList<>();
		}
		
		ShiftExpectation shiftExpectation =  (ShiftExpectation) expectation.getWorkExpectation();
		return shiftExpectation.getWorkableShiftCodeList().stream()
				.map(s -> new KscdtAvailabilityShift(
							AppContexts.user().companyId(),
							new KscdtAvailabilityShiftPk(
									expectation.getEmployeeId(), 
									expectation.getExpectingDate(), s.v()) 
							))
				.collect(Collectors.toList());
	}

}
