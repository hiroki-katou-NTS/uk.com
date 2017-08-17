/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.DefaultBasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * The Class WorkTypeWebService.
 */
@Path("at/share/worktype")
@Produces("application/json")
public class WorkTypeWebService extends WebService {

	/** The find. */
	@Inject
	private WorkTypeFinder find;
	
	/** The basic schedule. */
	@Inject 
	private DefaultBasicScheduleService basicSchedule;
	
	private static final List<Integer> workstyleList = Arrays.asList(WorkStyle.AFTERNOON_BREAK.value,
			WorkStyle.MORNING_BREAK.value, WorkStyle.ONE_DAY_REST.value, WorkStyle.ONE_DAY_WORK.value);
	

	/**
	 * Gets the possible work type.
	 *
	 * @param lstPossible the lst possible
	 * @return the possible work type
	 */
	@POST
	@Path("getpossibleworktype")
	public List<WorkTypeDto> getPossibleWorkType(List<String> lstPossible) {
		return this.find.getPossibleWorkType(lstPossible);
	}

	/**
	 * Gets the by C id and display atr.
	 *
	 * @return the by C id and display atr
	 */
	@POST
	@Path("getByCIdAndDisplayAtr")
	public List<WorkTypeDto> getByCIdAndDisplayAtr() {
		return this.find.findByCIdAndDisplayAtr();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<WorkTypeDto> findAll() {
		return this.find.findByCompanyId();
	}
	
	/**
	 * Find by id.
	 *
	 * @param workTypeCode the work type code
	 * @return the work type dto
	 */
	@POST
	@Path("findById/{workTypeCode}")
	public WorkTypeDto findById(@PathParam("workTypeCode") String workTypeCode){
		return this.find.findById(workTypeCode);
	}
	
	@POST
	@Path("findSelectAble/{workStyleLst}")
	public List<String> findSelectable(@PathParam("workStyleLst") List<Integer> workStyleLst) {
		List<String> worktypeCodeList = this.find.findByCompanyId().stream().map(item -> {
			return item.getWorkTypeCode();
		}).collect(Collectors.toList());
		
		// Case: input workstyleList is Null
		if (CollectionUtil.isEmpty(workStyleLst)) {
			return new ArrayList<>();
		}
		// Case: input workstyleList contains full values of enum WorkStyle
		if (workStyleLst.containsAll(workstyleList)) {
			return worktypeCodeList;
		}
		// Other cases
		List<String> codeList = new ArrayList<>();
		worktypeCodeList.stream().forEach(item-> {
			WorkStyle workstyle = this.basicSchedule.checkWorkDay(item);
			if (workstyleList.contains(workstyle)) {
				codeList.add(item);
			}
		});
		return codeList;
		
	}
	
}
