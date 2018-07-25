package nts.uk.ctx.sys.assist.ws.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.command.category.CusCategoryCommand;
import nts.uk.ctx.sys.assist.app.find.category.CategoryDto;
import nts.uk.ctx.sys.assist.app.find.category.CategoryFinder;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;


@Path("ctx/sys/assist/app")
@Produces("application/json")
public class CategoryWebService extends WebService {
	
	@Inject
	private CategoryFinder categoryFinder;
	
	@Inject
	private I18NResourcesForUK i18n;
	
	@POST
	@Path("findCategory/{systemType}")
	public List<CategoryDto> find(@PathParam("systemType") int systemType) {
		return this.categoryFinder.getCategoryBySystemType(systemType);
	}
	
	@POST
	@Path("findCategoryByCodeOrName")
	public List<CategoryDto> getCategoryByCodeOrName(CusCategoryCommand cusCategoryCommand) {
		if(cusCategoryCommand.getKeySearch() != null) {
			return this.categoryFinder.getCategoryByCodeOrName(cusCategoryCommand.getSystemType(), cusCategoryCommand.getKeySearch(), cusCategoryCommand.getCategoriesIgnore());
		} else {
			return this.categoryFinder.getCategoryByCodeOrName(cusCategoryCommand.getSystemType(), "", cusCategoryCommand.getCategoriesIgnore());
		}
	}
	
	@POST
	@Path("getSysType")
	public List<EnumConstant> getEnumValueOutputFormat(){
		return EnumAdaptor.convertToValueNameList(SystemType.class, i18n);
	}
	
}
