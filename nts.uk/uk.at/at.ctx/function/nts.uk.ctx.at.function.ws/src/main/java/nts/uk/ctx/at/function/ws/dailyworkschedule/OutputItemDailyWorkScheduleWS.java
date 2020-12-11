/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.dailyworkschedule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleCommand;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleDeleteHandler;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleSaveHandler;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.DataInforReturnDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.DataReturnDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputItemDailyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputItemDailyWorkScheduleFinder;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputStandardSettingOfDailyWorkScheduleDto;

/**
 * The Class OutputItemDailyWorkScheduleWS.
 * @author NWS-HoangDD
 */
@Path("at/function/dailyworkschedule")
@Produces(MediaType.APPLICATION_JSON)
public class OutputItemDailyWorkScheduleWS extends WebService{
	
	/** The output item daily work schedule finder. */
	@Inject
	private OutputItemDailyWorkScheduleFinder outputItemDailyWorkScheduleFinder; 
	
	/** The output item daily work schedule save handler. */
	@Inject
	private OutputItemDailyWorkScheduleSaveHandler outputItemDailyWorkScheduleSaveHandler;
	
	/** The output item daily work schedule delete handler. */
	@Inject
	private OutputItemDailyWorkScheduleDeleteHandler outputItemDailyWorkScheduleDeleteHandler;
	
	/**
	 * Find.
	 *
	 * @return the output item daily work schedule dto
	 */
	@Path("find")
	@POST
	public Map<String, Object> find(RequestStartScreenCDto dto) {
		return this.outputItemDailyWorkScheduleFinder.startScreenC(Optional.of(dto.getCode()), dto.getSelectTionType());
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void save(OutputItemDailyWorkScheduleCommand command){
		this.outputItemDailyWorkScheduleSaveHandler.handle(command);
	}
	
	/**
	 * Delete.
	 *
	 * @param code the code
	 */
	@Path("delete/{layoutId}/{selectionType}")
	@POST
	public void delete(@PathParam("layoutId") String layoutId, @PathParam("selectionType") Integer selectionType) {
		this.outputItemDailyWorkScheduleDeleteHandler.delete(layoutId, selectionType);
	}
	
	/**
	 * Find copy.
	 *
	 * @return the list
	 */
	@Path("findCopy")
	@POST
	public List<DataInforReturnDto> findCopy() {
		return this.outputItemDailyWorkScheduleFinder.getFormatDailyPerformance();
	}
	
	/**
	 * Execute copy.
	 *
	 * @param codeCopy the code copy
	 * @param codeSourceSerivce the code source serivce
	 * @return the list
	 */
	@Path("executeCopy/{codeCopy}/{codeSourceSerivce}/{selectionType}/{fontSize}")
	@POST
	public DataReturnDto executeCopy(@PathParam("codeCopy") String codeCopy
			, @PathParam("codeSourceSerivce") String codeSourceSerivce
			, @PathParam("selectionType") Integer selectionType
			, @PathParam("fontSize") Integer fontSize) {
		return this.outputItemDailyWorkScheduleFinder.executeCopy(codeCopy, codeSourceSerivce, selectionType, fontSize);
	}
	
	@Path("findByCode/{code}/{selectionType}")
	@POST
	public OutputItemDailyWorkScheduleDto findByCode(@PathParam("code") String code, @PathParam("selectionType") Integer selectionType) {
		return this.outputItemDailyWorkScheduleFinder.findByCode(code, selectionType);
	}

	/**
	 * Find stand setting by company id.
	 *
	 * @param companyId the company id
	 * @return the output standard setting of daily work schedule dto
	 */
	@Path("findStandardSetting/{companyId}")
	@POST
	public OutputStandardSettingOfDailyWorkScheduleDto findStandSettingByCompanyId(@PathParam("companyId") String companyId) {
		return this.outputItemDailyWorkScheduleFinder.getStandardSetting(companyId);
	}
	
	/**
	 * Find free setting by company id.
	 *
	 * @param companyId the company id
	 * @return the output standard setting of daily work schedule dto
	 */
	@Path("findFreeSetting/{companyId}/{employeeId}")
	@POST
	public OutputStandardSettingOfDailyWorkScheduleDto findFreeSettingByCompanyId(@PathParam("companyId") String companyId) {
		return this.outputItemDailyWorkScheduleFinder.getStandardSetting(companyId);
	}
}
