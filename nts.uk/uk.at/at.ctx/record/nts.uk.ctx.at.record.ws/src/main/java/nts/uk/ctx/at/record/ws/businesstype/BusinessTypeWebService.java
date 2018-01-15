package nts.uk.ctx.at.record.ws.businesstype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.businesstype.AddBusinessTypeNameCommand;
import nts.uk.ctx.at.record.app.command.businesstype.AddBusinessTypeNameCommandHandler;
import nts.uk.ctx.at.record.app.command.businesstype.DeleteBusinessTypeNameCommand;
import nts.uk.ctx.at.record.app.command.businesstype.DeleteBusinessTypeNameCommandHandler;
import nts.uk.ctx.at.record.app.command.businesstype.UpdateBusinessTypeNameCommand;
import nts.uk.ctx.at.record.app.command.businesstype.UpdateBusinessTypeNameCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypesFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDto;

@Path("at/record/businesstype")
@Produces("application/json")
public class BusinessTypeWebService extends WebService {
	@Inject
	private BusinessTypesFinder findAll;
	@Inject
	private AddBusinessTypeNameCommandHandler add;
	@Inject
	private UpdateBusinessTypeNameCommandHandler update;
	@Inject
	private DeleteBusinessTypeNameCommandHandler delete;
	/**
	 * get all data to list
	 * @return
	 */
	@POST
	@Path("findAll")
	public List<BusinessTypeDto> getAll(){
		return this.findAll.findAll();
	}
	@POST
	@Path("findByCode")
	public BusinessTypeDto getBusinessType(String businessTypeCode){
		return this.findAll.findByCode(businessTypeCode);
	}
	/**
	 * insert new business type name
	 * @param command
	 */
	@POST
	@Path("add")
	public void addBusinessTypeName(AddBusinessTypeNameCommand command){
		this.add.handle(command);
	}
	/**
	 * update a business type name
	 * @param command
	 */
	@POST
	@Path("update")
	public void updateBusinessTypeName(UpdateBusinessTypeNameCommand command){
		this.update.handle(command);
	}
	@POST
	@Path("delete")
	public void deleteBusinessTypeName(DeleteBusinessTypeNameCommand command){
		this.delete.handle(command);
	}
}
