package nts.uk.ctx.at.request.infra.repository.overtimeinstruct;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.infra.entity.overtimeinstruct.KrqdtOvertimeInstruct;

@Stateless
public class OvertimeInstructImpl extends JpaRepository implements OvertimeInstructRepository {
	private static final String FIND_ALL;
	private static final String FIND_FOR_TARGET_PERSON;
	
	static{
		StringBuilder query = new StringBuilder();
		query.append("Select o from KrqdtOvertimeInstruct o");
		FIND_ALL = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE o.inputDate = :inputDate");
		query.append(" AND o.instructDate = :instructDate");
		query.append(" AND o.targetPerson = :targetPerson");
		FIND_FOR_TARGET_PERSON = query.toString();
		
	}

	@Override
	public List<OverTimeInstruct> getOvertimeInstruct(GeneralDate inputDate, GeneralDate instructDate, String targetPerson) {
		
		return this.queryProxy().query(FIND_FOR_TARGET_PERSON,KrqdtOvertimeInstruct.class)
		.setParameter("inputDate", inputDate)
		.setParameter("instructDate", instructDate)
		.setParameter("targetPerson", targetPerson).getList(c -> convertToDomain(c));
		
	}
	private OverTimeInstruct convertToDomain(KrqdtOvertimeInstruct krqdtOvertimeInstruct){
		 return OverTimeInstruct.createSimpleFromJavaType(krqdtOvertimeInstruct.getKrqdtOvertimeInstructPK().getCid(),
				krqdtOvertimeInstruct.getKrqdtOvertimeInstructPK().getAppId(),
				krqdtOvertimeInstruct.getWorkContent(),
				krqdtOvertimeInstruct.getInputDate(),
				krqdtOvertimeInstruct.getTargetPerson(),
				krqdtOvertimeInstruct.getInstructDate(),
				krqdtOvertimeInstruct.getInstructor(),
				krqdtOvertimeInstruct.getOvertimeInstructReason(),
				krqdtOvertimeInstruct.getOvertimeHour(),
				krqdtOvertimeInstruct.getStartClock(),
				krqdtOvertimeInstruct.getEndClock());
		
		
	}

}
