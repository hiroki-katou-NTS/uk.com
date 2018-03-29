package nts.uk.ctx.at.request.infra.repository.holidayinstruct;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.infra.entity.holidayworkinstruct.KrqdtHolidayInstruct;

@Stateless
public class JpaHolidayInstructRepository extends JpaRepository implements HolidayInstructRepository {
	private static final String FIND_ALL;
	private static final String FIND_FOR_TARGET_PERSON;
	private static final String FIND_ALL_BY_TARGET_PERSON;
	
	static{
		StringBuilder query = new StringBuilder();
		query.append("Select o from KrqdtHolidayInstruct o");
		FIND_ALL = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE o.krqdtHolidayInstructPK.instructDate = :instructDate");
		query.append(" AND o.krqdtHolidayInstructPK.targetPerson = :targetPerson");
		FIND_FOR_TARGET_PERSON = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" AND o.krqdtHolidayInstructPK.targetPerson = :targetPerson");
		query.append(" ORDER BY o.krqdtHolidayInstructPK.instructDate ASC");
		FIND_ALL_BY_TARGET_PERSON = query.toString();
	}
	@Override
	public HolidayInstruct getHolidayWorkInstruct(GeneralDate instructDate, String targetPerson) {
		Optional<HolidayInstruct> overTimeInstructs = this.queryProxy().query(FIND_FOR_TARGET_PERSON,KrqdtHolidayInstruct.class)
				.setParameter("instructDate", instructDate.date())
				.setParameter("targetPerson", targetPerson).getSingle(c -> convertToDomain(c));
				if(overTimeInstructs.isPresent()){
					return overTimeInstructs.get();
				}
				return null;
			}
	private HolidayInstruct convertToDomain(KrqdtHolidayInstruct krqdtHolidayWorkInstruct){
		 return HolidayInstruct.createSimpleFromJavaType(
				 krqdtHolidayWorkInstruct.getWorkContent(),
				 krqdtHolidayWorkInstruct.getInputDate(),
				 krqdtHolidayWorkInstruct.getKrqdtHolidayInstructPK().getTargetPerson(),
				 krqdtHolidayWorkInstruct.getKrqdtHolidayInstructPK().getInstructDate(),
				 krqdtHolidayWorkInstruct.getInstructor(),
				 krqdtHolidayWorkInstruct.getHolidayInstructReason(),
				 krqdtHolidayWorkInstruct.getHolidayWorkHour(),
				 krqdtHolidayWorkInstruct.getStartClock(),
				 krqdtHolidayWorkInstruct.getEndClock());
		
		
	}
	/**
	 * For request list No.231
	 */
	@Override
	public List<HolidayInstruct> getAllHolidayInstructBySId(String sId) {
		List<HolidayInstruct> holidayInstruct = this.queryProxy().query(FIND_ALL_BY_TARGET_PERSON, KrqdtHolidayInstruct.class)
				.setParameter("targetPerson", sId).getList(c -> convertToDomain(c));
		
		if(holidayInstruct != null){
			if(holidayInstruct.size() > 0){
				return holidayInstruct;
			}
		}
		
		return null;
	}
}
