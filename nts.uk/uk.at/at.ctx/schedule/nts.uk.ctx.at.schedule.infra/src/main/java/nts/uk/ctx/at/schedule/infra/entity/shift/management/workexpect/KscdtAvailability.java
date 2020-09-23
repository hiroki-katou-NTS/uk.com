package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCDT_AVAILABILITY")
@AllArgsConstructor
public class KscdtAvailability extends ContractUkJpaEntity{
	
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
	
	public static Function<NtsResultRecord, KscdtAvailability> createKscdtAvailability = s -> 
		new KscdtAvailability(
			new KscdtAvailabilityPk(s.getString("SID"), s.getGeneralDate("YMD")), 
			s.getString("MEMO"), 
			s.getInt("SPECIFIED_METHOD"));
		
	public static KscdtAvailability fromDomain(WorkExpectationOfOneDay expectation) {
		return new KscdtAvailability(
				new KscdtAvailabilityPk(expectation.getEmployeeId(), expectation.getExpectingDate()), 
				expectation.getMemo().v(),
				expectation.getWorkExpectation().getAssignmentMethod().value);
	}

}
