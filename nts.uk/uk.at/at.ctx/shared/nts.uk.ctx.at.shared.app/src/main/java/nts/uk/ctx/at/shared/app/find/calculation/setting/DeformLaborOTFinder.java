package nts.uk.ctx.at.shared.app.find.calculation.setting;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.setting.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.calculation.setting.DeformLaborOTRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author yennh
 *
 */
@Stateless
public class DeformLaborOTFinder {
	@Inject
	private DeformLaborOTRepository repository;

	/**
	 * Find all DeformLaborOT
	 * 
	 * @return
	 */
	public List<DeformLaborOT> findAllDeformLaborOT() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId);
	}
}
