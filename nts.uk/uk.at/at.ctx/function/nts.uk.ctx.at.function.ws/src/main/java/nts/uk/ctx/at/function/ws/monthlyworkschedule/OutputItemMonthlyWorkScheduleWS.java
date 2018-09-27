/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.monthlyworkschedule;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleCommand;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleDeleteHandler;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleSaveHandler;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginFinder;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.DisplayTimeItemDto;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.MonthlyPerformanceDataReturnDto;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.OutputItemMonthlyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.OutputItemMonthlyWorkScheduleFinder;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn;

/**
 * The Class OutputItemMonthlyWorkScheduleWS.
 */
@Path("at/function/monthlyworkschedule")
@Produces(MediaType.APPLICATION_JSON)
public class OutputItemMonthlyWorkScheduleWS extends WebService {

	/** The output item monthly work schedule finder. */
	@Inject
	private OutputItemMonthlyWorkScheduleFinder outputItemMonthlyWorkScheduleFinder;
	
	/** The role whether login finder. */
	@Inject
	private RoleWhetherLoginFinder roleWhetherLoginFinder;

	/** The output item monthly work schedule save handler. */
	@Inject
	private OutputItemMonthlyWorkScheduleSaveHandler outputItemMonthlyWorkScheduleSaveHandler;

	/** The output item monthly work schedule delete handler. */
	@Inject
	private OutputItemMonthlyWorkScheduleDeleteHandler outputItemMonthlyWorkScheduleDeleteHandler;

	
	/**
	 * Gets the current loginer role.
	 *
	 * @return the current loginer role
	 */
	@Path("getCurrentLoginerRole")
	@POST
	public RoleWhetherLoginDto getCurrentLoginerRole() {
		return this.roleWhetherLoginFinder.getCurrentLoginerRole();
	}

	/**
	 * Find.
	 *
	 * @return the map
	 */
	@Path("find")
	@POST
	public Map<String, Object> find() {
		return this.outputItemMonthlyWorkScheduleFinder.findByCid();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findall")
	@POST
	public List<OutputItemMonthlyWorkScheduleDto> findAll() {
		return this.outputItemMonthlyWorkScheduleFinder.findAll();
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@Path("save")
	@POST
	public void save(OutputItemMonthlyWorkScheduleCommand command) {
		this.outputItemMonthlyWorkScheduleSaveHandler.handle(command);
	}

	/**
	 * Delete.
	 *
	 * @param code
	 *            the code
	 */
	@Path("delete/{code}")
	@POST
	public void delete(@PathParam("code") String code) {
		this.outputItemMonthlyWorkScheduleDeleteHandler.delete(code);
	}

	/**
	 * Gets the enum setting print.
	 *
	 * @return the enum setting print
	 */
	@Path("enumSettingPrint")
	@POST
	public List<EnumConstant> getEnumSettingPrint() {
		return EnumAdaptor.convertToValueNameList(PrintSettingRemarksColumn.class);
	}

	/**
	 * Find copy.
	 *
	 * @return the list
	 */
	@Path("findCopy")
	@POST
	public MonthlyPerformanceDataReturnDto findCopy() {
		return this.outputItemMonthlyWorkScheduleFinder.getFormatMonthlyPerformance();
	}

	/**
	 * Execute copy.
	 *
	 * @param codeCopy
	 *            the code copy
	 * @param codeSourceSerivce
	 *            the code source serivce
	 * @param lstCommandCopy
	 *            the lst command copy
	 * @return the list
	 */
	@Path("executeCopy/{codeCopy}/{codeSourceSerivce}")
	@POST
	public List<DisplayTimeItemDto> executeCopy(@PathParam("codeCopy") String codeCopy,
			@PathParam("codeSourceSerivce") String codeSourceSerivce) {
		return this.outputItemMonthlyWorkScheduleFinder.executeCopy(codeCopy, codeSourceSerivce);
	}
	
	/**
	 * Gets the enum remark input content.
	 *
	 * @return the enum remark input content
	 */
	@Path("enumRemarkInputContent")
	@POST
	public List<EnumConstant> getEnumRemarkInputContent(){
		return EnumAdaptor.convertToValueNameList(RemarkInputContent.class);
	}

}
