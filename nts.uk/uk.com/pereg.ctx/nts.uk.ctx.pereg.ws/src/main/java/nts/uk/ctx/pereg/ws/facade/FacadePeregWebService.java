package nts.uk.ctx.pereg.ws.facade;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.ctx.pereg.app.find.processor.ItemDefFinder;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregDeleteCommand;
import nts.uk.shr.pereg.app.command.PeregInputContainer;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Path("/facade/pereg")
@Produces("application/json")
public class FacadePeregWebService extends WebService {

	@Inject
	private PeregCommandFacade commandFacade;
	
	@Inject
	private ItemDefFinder itemDefFinder;
	
	@POST
	@Path("add")
	public void add(PeregInputContainer inputContainer) {
		this.commandFacade.add(inputContainer);
	}
	
	@POST
	@Path("update")
	public void update(PeregInputContainer inputContainer) {
		this.commandFacade.update(inputContainer);
	}
	
	@POST
	@Path("delete")
	public void delete(PeregDeleteCommand deleteCommand) {
		this.commandFacade.delete(deleteCommand);
	}
	
	@POST
	@Path("register")
	public Object register(PeregInputContainer inputContainer) {
		
		String recordId = null;
		List<ItemsByCategory> addInputs = inputContainer.getInputs().stream().filter(p->StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());
		if (addInputs!= null && !addInputs.isEmpty()){
			recordId= this.commandFacade.add(inputContainer);
		}
		
		List<ItemsByCategory> updateInputs = inputContainer.getInputs().stream().filter(p->!StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());
		if (updateInputs!= null && !updateInputs.isEmpty()){
			// Add item invisible to list
			for (ItemsByCategory itemByCategory : updateInputs) {
				
				PeregQuery query = new PeregQuery(itemByCategory.getCategoryCd(), inputContainer.getEmployeeId(),inputContainer.getPersonId(),null);
				query.setInfoId(itemByCategory.getRecordId());
				List<ItemValue> fullItems = itemDefFinder.getFullListItemDef(query);
				List<String> itemVisible = itemByCategory.getItems().stream().map(ItemValue::itemCode).collect(Collectors.toList());
				
				// List item invisible
				List<ItemValue> itemInvisible = fullItems.stream().filter(i->!itemVisible.contains(i.itemCode()) && i.itemCode().charAt(1) == 'S').collect(Collectors.toList());
				itemInvisible.addAll(itemByCategory.getItems());
				
				itemByCategory.setItems(itemInvisible);
			}
			
			
			PeregInputContainer registerPeregInputContainer = new PeregInputContainer(inputContainer.getPersonId(), inputContainer.getEmployeeId(), updateInputs);
			this.commandFacade.update(registerPeregInputContainer);
		}
		
		return new Object[] { recordId };
	}
}
