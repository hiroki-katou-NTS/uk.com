package nts.uk.ctx.at.shared.dom.worktype.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorktypeServiceImpl.
 */
@Stateless
public class WorktypeServiceImpl implements WorktypeService{
	
	/** The repository. */
	@Inject WorkTypeRepository repository;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktype.service.WorktypeService#getAttendanceHolidayAtr(java.lang.String)
	 */
	@Override
	public AttendanceHolidayAtr getAttendanceHolidayAtr(String worktypeCode) {
		
		// call service find by work type code
		Optional<WorkType> optionalWorktype = this.repository.findByPK(AppContexts.user().companyId(), worktypeCode);
		
		// check exist data 
		if (!optionalWorktype.isPresent()) {
			throw new RuntimeException("NOT FOUND WORK TYPE");
		}
		
		WorkType worktype = optionalWorktype.get();
		
		// check 勤務区分 is 1日 (OneDay)
		if (worktype.getDailyWork().getWorkTypeUnit().value == WorkTypeUnit.OneDay.value) {

			return AttendanceHolidayAtr.HOLIDAY;
			// FULL_TIME
		} else
		// check 勤務区分 is 午前と午後 (午前と午後)
		{

		}
		return null;
	}

}
