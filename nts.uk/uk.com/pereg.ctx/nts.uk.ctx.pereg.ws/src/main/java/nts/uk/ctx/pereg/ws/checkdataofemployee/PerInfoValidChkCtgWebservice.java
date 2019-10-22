package nts.uk.ctx.pereg.ws.checkdataofemployee;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.process.checkdata.CategoryResult;
import nts.uk.ctx.pereg.app.command.process.checkdata.CheckAllCtgProcess;
import nts.uk.ctx.pereg.app.command.process.checkdata.CheckDataEmpCommandHandler;
import nts.uk.ctx.pereg.app.command.process.checkdata.CheckDataFromUI;
import nts.uk.ctx.pereg.app.command.process.checkdata.DataEmployeeToCheck;
import nts.uk.ctx.pereg.app.find.person.setting.checkdata.CharacteristicDto;
import nts.uk.ctx.pereg.app.find.person.setting.checkdata.PerInfoValidChkCtgDto;
import nts.uk.ctx.pereg.app.find.person.setting.checkdata.PerInfoValidChkCtgFinder;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Path("ctx/pereg/check/category")
@Produces("application/json")
public class PerInfoValidChkCtgWebservice {
	@Inject
	private PerInfoValidChkCtgFinder perInfoFinder;
	
	@Inject
	private CheckDataEmpCommandHandler process;
	
	@Inject CheckAllCtgProcess checkHasCtg;
	
	@POST
	@Path("checkHasCtg")
	public CategoryResult checkHasCtg(CheckDataFromUI query) {
		return checkHasCtg.getAllCategory(query);
	}
	
	@POST
	@Path("process")
	public AsyncTaskInfo executeAggr(CheckDataFromUI param) {
		return this.process.handle(param);
	}
	
	
	@POST
	@Path("findCharacteristics")
	public CharacteristicDto getCharacteristics() {
		return new CharacteristicDto(AppContexts.user().companyId(), AppContexts.user().userId());
	}
	
	@POST
	@Path("findAll")
	public List<PerInfoValidChkCtgDto> getAllCategoryBySetId() {
		return this.perInfoFinder.getListPerInfoValid();
	}
	
	@POST
	@Path("findAllByCatId/{personInfoCategoryId}")
	public Optional<PerInfoValidChkCtgDto> getAllCategoryBySetId(@PathParam("personInfoCategoryId") String personInfoCategoryId) {
		return null;
		//return this.perInfoFinder.getPerInfoValid(personInfoCategoryId);
	}
	
	@POST
	@Path("getError")
	public List<DataEmployeeToCheck> getError(CheckDataFromUI query) {
		return null;
	}
	
	@POST
	@Path("getSystemDate")
	public SystemDateDto getSystemDate() {
		SystemDateDto result = new SystemDateDto(GeneralDate.today());
		return result;
	}
}
