package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityByShiftMaster;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY_SHIFT")
@AllArgsConstructor
public class KscdtAvailabilityShift extends ContractCompanyUkJpaEntity {
	
	public static final Function<NtsResultRecord, KscdtAvailabilityShift> mapper = s ->  
			new JpaEntityMapper<>(KscdtAvailabilityShift.class).toEntity(s);
	
	@Getter
	@EmbeddedId
	public KscdtAvailabilityShiftPk pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static List<KscdtAvailabilityShift> fromDomain(
			WorkAvailabilityOfOneDay expectation) {
		
		if (expectation.getWorkAvailability().getAssignmentMethod() != AssignmentMethod.SHIFT) {
			return new ArrayList<>();
		}
		
		WorkAvailabilityByShiftMaster shiftExpectation =  (WorkAvailabilityByShiftMaster) expectation.getWorkAvailability();
		return shiftExpectation.getWorkableShiftCodeList().stream()
				.map(s -> new KscdtAvailabilityShift(
							new KscdtAvailabilityShiftPk(
									expectation.getEmployeeId(), 
									expectation.getWorkAvailabilityDate(), s.v()) 
							))
				.collect(Collectors.toList());
	}

}
