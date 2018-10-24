/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.dailyworkschedule;

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
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleCommand;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleDeleteHandler;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleSaveHandler;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.DataInforReturnDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputItemDailyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputItemDailyWorkScheduleFinder;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarksContentChoice;

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
	public Map<String, Object> find(){
		return this.outputItemDailyWorkScheduleFinder.findByCid();
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
	@Path("delete/{code}")
	@POST
	public void delete(@PathParam("code") String code){
		this.outputItemDailyWorkScheduleDeleteHandler.delete(code);
	}
	
	/**
	 * Find copy.
	 *
	 * @return the list
	 */
	@Path("findCopy")
	@POST
	public List<DataInforReturnDto> findCopy(){
		return this.outputItemDailyWorkScheduleFinder.getFormatDailyPerformance();
	}
	
	/**
	 * Execute copy.
	 *
	 * @param codeCopy the code copy
	 * @param codeSourceSerivce the code source serivce
	 * @return the list
	 */
	@Path("executeCopy/{codeCopy}/{codeSourceSerivce}")
	@POST
	public List<DataInforReturnDto> executeCopy(@PathParam("codeCopy") String codeCopy, @PathParam("codeSourceSerivce") String codeSourceSerivce){
		return this.outputItemDailyWorkScheduleFinder.executeCopy(codeCopy, codeSourceSerivce);
	}
	
	/**
	 * Gets the enum name.
	 *
	 * @return the enum name
	 */
	@Path("enumName")
	@POST
	public List<EnumConstant> getEnumName(){
		return EnumAdaptor.convertToValueNameList(NameWorkTypeOrHourZone.class);
	}
	
	/**
	 * Gets the enum remark content choice.
	 *
	 * @return the enum remark content choice
	 */
	@Path("enumRemarkContentChoice")
	@POST
	public List<EnumConstant> getEnumRemarkContentChoice(){
		return EnumAdaptor.convertToValueNameList(RemarksContentChoice.class);
	}
	
	@Path("enumRemarkInputContent")
	@POST
	public List<EnumConstant> getEnumRemarkInputContent(){
		return EnumAdaptor.convertToValueNameList(RemarkInputContent.class);
	}
	
	@Path("findByCode/{code}")
	@POST
	public OutputItemDailyWorkScheduleDto findByCode(@PathParam("code") String code){
		return this.outputItemDailyWorkScheduleFinder.findByCodeId(code);
	}
}
