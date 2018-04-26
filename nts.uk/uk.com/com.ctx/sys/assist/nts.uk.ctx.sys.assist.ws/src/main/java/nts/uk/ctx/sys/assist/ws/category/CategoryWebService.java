package nts.uk.ctx.sys.assist.ws.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.category.CategoryFinder;
import nts.uk.ctx.sys.assist.app.find.category.CusCategoryDto;


@Path("ctx/sys/assist/app")
@Produces("application/json")
public class CategoryWebService {
	
	@Inject
	private CategoryFinder categoryFinder;
	
	@POST
	@Path("findCategory/{systemType}")
	public List<CusCategoryDto> find(@PathParam("systemType") int systemType){
		return this.categoryFinder.getCategoryBySystemType(systemType);
	}
	
}
