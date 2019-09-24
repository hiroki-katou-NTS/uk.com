package nts.uk.ctx.pereg.app.find.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員表示処理
 * @author lanlt
 *
 */
@Stateless
public class ProcessingDisplayEmployee {
	@Inject 
	private PerInfoCtgByCompanyRepositoty ctgRepo;
	
	public GridEmployeeDto processingDiplayEmployee(CategoryParam param) {
		String cid = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		Optional<PersonInfoCategory> ctgOptional =  this.ctgRepo.getDetailCategoryInfo(cid, param.getCategoryId(), contractCd);
		if(ctgOptional.isPresent()) {
			
		}
		return null;
	}
}
