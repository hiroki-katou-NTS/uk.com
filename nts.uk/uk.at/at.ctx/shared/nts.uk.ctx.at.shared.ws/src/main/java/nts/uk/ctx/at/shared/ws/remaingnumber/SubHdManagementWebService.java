/**
 * 
 */
package nts.uk.ctx.at.shared.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.AddSubHdManagementCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.AddSubHdManagementCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.SubstitutionManagementDataFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto.ExtraHolidayManagementDataDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto.SubDataSearchConditionDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto.SubstituteDataManagementDto;

/**
 * @author nam.lh
 *
 */

@Path("at/record/remaingnumber")
@Produces("application/json")
public class SubHdManagementWebService extends WebService{
	@Inject
	private AddSubHdManagementCommandHandler add;
	
	@Inject
	private SubstitutionManagementDataFinder subManagementFinder;
	
	@POST
	@Path("add")
	public List<String> addBusinessTypeName(AddSubHdManagementCommand command){
		return this.add.handle(command);
	}
	
	@POST
	@Path("getSubsitutionData")
	public SubstituteDataManagementDto getSubsitutionData(SubDataSearchConditionDto dto){
		return subManagementFinder.getSubstituteManagementData(dto);
	}
	
	@POST
	@Path("getExtraHolidayData")
	public ExtraHolidayManagementDataDto getExtraHolidayData(SubDataSearchConditionDto dto){
		return subManagementFinder.getExtraHolidayManagementData(dto);
	}
}
