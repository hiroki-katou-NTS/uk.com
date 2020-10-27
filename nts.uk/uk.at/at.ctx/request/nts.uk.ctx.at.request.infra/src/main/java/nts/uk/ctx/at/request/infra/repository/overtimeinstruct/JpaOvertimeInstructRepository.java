package nts.uk.ctx.at.request.infra.repository.overtimeinstruct;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.infra.entity.overtimeinstruct.KrqdtInstructOvertime;

@Stateless
public class JpaOvertimeInstructRepository extends JpaRepository implements OvertimeInstructRepository {
	private static final String FIND_ALL;
	private static final String FIND_FOR_TARGET_PERSON;
	private static final String FIND_ALL_BY_TARGET_PERSON;
	
	static{
		StringBuilder query = new StringBuilder();
		query.append("Select o from KrqdtInstructOvertime o");
		FIND_ALL = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE o.krqdtInstructOvertimePK.instructDate = :instructDate");
		query.append(" AND o.krqdtInstructOvertimePK.targetPerson = :targetPerson");
		FIND_FOR_TARGET_PERSON = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE o.krqdtInstructOvertimePK.targetPerson = :targetPerson");
		query.append(" ORDER BY o.krqdtInstructOvertimePK.instructDate ASC");
		FIND_ALL_BY_TARGET_PERSON = query.toString();
		
	}

	@Override
	public OverTimeInstruct getOvertimeInstruct(GeneralDate instructDate, String targetPerson) {
		
		List<OverTimeInstruct> overTimeInstructs = this.queryProxy().query(FIND_FOR_TARGET_PERSON,KrqdtInstructOvertime.class)
		.setParameter("instructDate", instructDate.date())
		.setParameter("targetPerson", targetPerson).getList(c -> convertToDomain(c));
		if(overTimeInstructs != null){
			if(overTimeInstructs.size() > 0){
				return overTimeInstructs.get(0);
			}
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private OverTimeInstruct convertToDomain(KrqdtInstructOvertime krqdtInstructOvertime){
		 return OverTimeInstruct.createSimpleFromJavaType(krqdtInstructOvertime.getKrqdtInstructOvertimePK().getCid(),
				krqdtInstructOvertime.getWorkContent(),
				krqdtInstructOvertime.getInputDate(),
				krqdtInstructOvertime.getKrqdtInstructOvertimePK().getTargetPerson(),
				krqdtInstructOvertime.getKrqdtInstructOvertimePK().getInstructDate(),
				krqdtInstructOvertime.getInstructor(),
				krqdtInstructOvertime.getOvertimeInstructReason(),
				krqdtInstructOvertime.getOvertimeHour(),
				krqdtInstructOvertime.getStartClock(),
				krqdtInstructOvertime.getEndClock());
		
		
	}
	
	/**
	 * For request list 230
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<OverTimeInstruct> getAllOverTimeInstructBySId(String sId, GeneralDate strDate, GeneralDate endDate) {
		List<OverTimeInstruct> overTimeInstructs = this.queryProxy().query(FIND_ALL_BY_TARGET_PERSON, KrqdtInstructOvertime.class)
				.setParameter("targetPerson", sId).getList(c -> convertToDomain(c));
		List<OverTimeInstruct> result = new ArrayList<>();
		for (OverTimeInstruct item : overTimeInstructs) {
			if(item.getInstructDate().afterOrEquals(strDate) && item.getInstructDate().beforeOrEquals(endDate)) {
				result.add(item);
			}
		}
		return result;
	}
	
}


