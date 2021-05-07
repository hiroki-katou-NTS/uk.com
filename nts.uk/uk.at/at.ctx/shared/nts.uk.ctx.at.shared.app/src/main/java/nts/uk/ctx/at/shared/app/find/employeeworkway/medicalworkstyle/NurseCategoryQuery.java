package nts.uk.ctx.at.shared.app.find.employeeworkway.medicalworkstyle;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX 
 * 看護区分の一覧を取得する
 */
@Stateless
public class NurseCategoryQuery {

	@Inject
	private NurseClassificationRepository nurseClassificationRepository;

	public List<NurseCategoryDto> getListNurseCategory() {
		String companyId = AppContexts.user().companyId();
		return nurseClassificationRepository.getListCompanyNurseCategory(companyId).stream()
				.map(x -> new NurseCategoryDto(x.getNurseClassifiCode().v(), x.getNurseClassifiName().v()))
				.collect(Collectors.toList());
	}

}
