package nts.uk.screen.at.infra.worktype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkType;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypeSet;
import nts.uk.screen.at.app.worktype.WorkTypeDto;
import nts.uk.screen.at.app.worktype.WorkTypeQueryRepository;
import nts.uk.screen.at.app.worktype.WorkTypeSetDto;

@Stateless
public class JpaWorkTypeQueryRepository extends JpaRepository implements WorkTypeQueryRepository {
	
	private static final String SELECT_FROM_WORKTYPE = "SELECT c FROM KshmtWorkType c";

	private static final String SELECT_ALL_WORKTYPE = SELECT_FROM_WORKTYPE
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId";

	@Override
	public List<WorkTypeDto> findAllWorkType(String companyId) {		
		return this.queryProxy().query(SELECT_ALL_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(x -> toApp(x));
	}
	
	private WorkTypeDto toApp(KshmtWorkType entity){
		
		
		Integer dispOrder = entity.kshmtWorkTypeOrder != null ? entity.kshmtWorkTypeOrder.dispOrder : null;
		
	    WorkTypeDto workType = new WorkTypeDto(
	    		entity.kshmtWorkTypePK.workTypeCode, 
	    		entity.name, 
	    		entity.abbreviationName,
	    		entity.symbolicName,
	    		entity.deprecateAtr,
	    		entity.memo,
	    		entity.worktypeAtr,
	    		entity.oneDayAtr,
	    		entity.morningAtr,
	    		entity.afternoonAtr,
	    		entity.calculatorMethod,
	    		dispOrder);
	    if (entity.worktypeSetList != null) {
	    	workType.setWorkTypeSet(entity.worktypeSetList.stream().map(x -> toAppWorkTypeSet(x)).collect(Collectors.toList()));
	    }
		return workType;
	}
	
	private WorkTypeSetDto toAppWorkTypeSet(KshmtWorkTypeSet entity){
		val app = new WorkTypeSetDto(
				entity.kshmtWorkTypeSetPK.workTypeCode, 
				entity.kshmtWorkTypeSetPK.workAtr, 
				entity.digestPublicHd, 
				entity.hodidayAtr, 
				entity.countHoliday, 
				entity.closeAtr, 
				entity.sumAbsenseNo, 
				entity.sumSpHolidayNo, 
				entity.timeLeaveWork, 
				entity.attendanceTime, 
				entity.genSubHoliday,
				entity.dayNightTimeAsk);
		return app;
	}
	
}
