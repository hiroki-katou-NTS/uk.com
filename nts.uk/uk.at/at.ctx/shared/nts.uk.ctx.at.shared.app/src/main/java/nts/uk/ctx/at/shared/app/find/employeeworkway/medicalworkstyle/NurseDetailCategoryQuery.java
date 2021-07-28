package nts.uk.ctx.at.shared.app.find.employeeworkway.medicalworkstyle;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 * 看護区分の明細を取得する
 */
@Stateless
public class NurseDetailCategoryQuery {
	
	@Inject
	private NurseClassificationRepository nurseClassificationRepository;

	public NurseDetailCategoryDto getDetailNurseCategory(String nurseCode) {
		String companyId = AppContexts.user().companyId();
		return nurseClassificationRepository.getSpecifiNurseCategory(companyId, nurseCode)
				.map(x -> new NurseDetailCategoryDto(x.getNurseClassifiCode().v(), x.getNurseClassifiName().v(),
						x.getLicense().value, x.isOfficeWorker()))
				.orElse(null);
	}
}
