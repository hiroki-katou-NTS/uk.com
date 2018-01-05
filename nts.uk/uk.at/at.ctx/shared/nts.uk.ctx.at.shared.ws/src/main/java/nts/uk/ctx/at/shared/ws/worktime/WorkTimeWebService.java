/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonDeleteCommand;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.DiffTimeWorkSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.DiffTimeWorkSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.FixedWorkSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.FixedWorkSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.worktime.flexset.FlexWorkSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.flexset.FlexWorkSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.worktime.flowset.FlowWorkSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.flowset.FlowWorkSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.worktime.WorkTimeSettingInfoFinder;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.WorkTimeSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime_old.WorkTimeFinder;
import nts.uk.ctx.at.shared.app.find.worktime_old.WorktimeFinderNew;
import nts.uk.ctx.at.shared.app.find.worktime_old.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingCondition;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/shared/worktime")
@Produces("application/json")
public class WorkTimeWebService extends WebService {

	/** The work time set finder. */
	@Inject
	private WorkTimeSettingFinder workTimeSetFinder;

	/** The work time finder. */
	@Inject
	private WorkTimeFinder workTimeFinder;
	
	@Inject
	private WorktimeFinderNew worktimeFinderNew_ver1;

	/** The flow handler. */
	@Inject
	private FixedWorkSettingSaveCommandHandler fixedHandler;

	/** The flow handler. */
	@Inject
	private DiffTimeWorkSettingSaveCommandHandler diffTimeHandler;
	
	/** The flow handler. */
	@Inject
	private FlowWorkSettingSaveCommandHandler flowHandler;
	
	/** The flow handler. */
	@Inject
	private FlexWorkSettingSaveCommandHandler flexHandler;
	
	@Inject
	private WorkTimeSettingInfoFinder workTimeSettingInfoFinder;
	
	/** The work time common delete command handler. */
	@Inject
	private WorkTimeCommonDeleteCommandHandler workTimeCommonDeleteCommandHandler;
	/**
	 * Find by company ID.
	 *
	 * @return the list
	 */
	@POST
	@Path("findByCompanyID")
	public List<WorkTimeDto> findByCompanyID() {
		return this.workTimeFinder.findByCompanyID();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<WorkTimeDto> findAll() {
		return this.workTimeFinder.findAll();
	}

	/**
	 * Find by codes.
	 *
	 * @param codes the codes
	 * @return the list
	 */
	@POST
	@Path("findByCodes")
	public List<WorkTimeDto> findByCodes(List<String> codes) {
		return this.workTimeFinder.findByCodes(codes);
	}

	/**
	 * Find with condition.
	 *
	 * @param condition the condition
	 * @return the list
	 */
	@POST
	@Path("findwithcondition")
	public List<SimpleWorkTimeSettingDto> findWithCondition(WorkTimeSettingCondition condition) {
		return this.workTimeSetFinder.findWithCondition(condition);
	}

	/**
	 * Find by code list.
	 *
	 * @param codelist the codelist
	 * @return the list
	 */
	@POST
	@Path("findByCodeList")
	public List<WorkTimeDto> findByCodeList(List<String> codelist) {
		return this.workTimeFinder.findByCodeList(codelist);
	}

	/**
	 * Find by time.
	 *
	 * @param command the command
	 * @return the list
	 */
	@POST
	@Path("findByTime")
	public List<WorkTimeDto> findByTime(WorkTimeCommandFinder command) {
		return this.workTimeFinder.findByTime(command.codelist, command.startAtr, command.startTime, command.endAtr,
				command.endTime);
	}

	/**
	 * Find by id.
	 *
	 * @param workTimeCode the work time code
	 * @return the work time dto
	 */
	@POST
	@Path("findById/{workTimeCode}")
	public WorkTimeDto findByWorkTimeCode(@PathParam("workTimeCode") String workTimeCode){
		return this.workTimeFinder.findById(workTimeCode);
	}
	// waitting nws
	@POST
	@Path("findByCodeListVer1")
	public List<WorkTimeDto> findByCodeList_Ver1(List<String> codelist) {
		return this.worktimeFinderNew_ver1.findByCodeList(codelist);
	}
	
	@POST
	@Path("findByTimeVer1")
	public List<WorkTimeDto> findByTimeVer1(WorkTimeCommandFinder command) {
		return this.worktimeFinderNew_ver1.findByTime(command.codelist, command.startTime,
				command.endTime);
	}
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("fixedset/save")
	public void save(FixedWorkSettingSaveCommand command){
		this.fixedHandler.handle(command);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("flowset/save")
	public void save(FlowWorkSettingSaveCommand command){
		this.flowHandler.handle(command);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("difftimeset/save")
	public void save(DiffTimeWorkSettingSaveCommand command){
		this.diffTimeHandler.handle(command);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("flexset/save")
	public void save(FlexWorkSettingSaveCommand command){
		this.flexHandler.handle(command);
	}
	
	/**
	 * Find info by work time code.
	 *
	 * @param workTimeCode the work time code
	 * @return the work time setting info dto
	 */
	@POST
	@Path("findInfo/{workTimeCode}")
	public WorkTimeSettingInfoDto findInfoByWorkTimeCode(@PathParam("workTimeCode") String workTimeCode){
		return this.workTimeSettingInfoFinder.find(workTimeCode);
	}
	
	/**
	 * Removes the by work time code.
	 *
	 * @param command the command
	 */
	@POST
	@Path("remove")
	public void removeByWorkTimeCode(WorkTimeCommonDeleteCommand command) {
		this.workTimeCommonDeleteCommandHandler.handle(command);
	}
}

@Data
@NoArgsConstructor
class WorkTimeCommandFinder {
	List<String> codelist;
	Integer startAtr;
	Integer startTime;
	int endAtr;
	Integer endTime;
}
