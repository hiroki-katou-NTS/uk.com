package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityByTimeZone;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY_TS")
@AllArgsConstructor
@NoArgsConstructor
public class KscdtAvailabilityTs extends ContractCompanyUkJpaEntity {
	
	public static final Function<NtsResultRecord, KscdtAvailabilityTs> mapper = s ->  
			new JpaEntityMapper<>(KscdtAvailabilityTs.class).toEntity(s);

	@EmbeddedId
	public KscdtAvailabilityTsPk pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static List<KscdtAvailabilityTs> fromDomain( WorkAvailabilityOfOneDay expectation) {
		
		if ( expectation.getWorkAvailability().getAssignmentMethod() != AssignmentMethod.TIME_ZONE) {
			return new ArrayList<>();
		}
		
		WorkAvailabilityByTimeZone timeZoneExpectation = (WorkAvailabilityByTimeZone) expectation.getWorkAvailability();
		return timeZoneExpectation.getWorkableTimeZoneList().stream()
			.map(t -> new KscdtAvailabilityTs(
							new KscdtAvailabilityTsPk(
									expectation.getEmployeeId(), 
									expectation.getWorkAvailabilityDate(), 
									t.startValue().intValue(), 
									t.endValue().intValue()) 
							))
			.collect(Collectors.toList());
	}
		
}
