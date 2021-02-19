package nts.uk.ctx.at.record.app.find.worktypeselection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.BusinessType;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * @author anhvd
 *
 */
@Stateless
public class WorkTypeSelectionFinder {

	@Inject
	private BusinessTypesRepository businessTypeRepository;

	public List<WorkTypeSelectionDto> getListWorkTypeSelection() {
		String companyId = AppContexts.user().companyId();
		List<WorkTypeSelectionDto> workTypeSelectionList = businessTypeRepository.findAll(companyId).stream()
				.map(item -> {
					return new WorkTypeSelectionDto(item.getBusinessTypeCode().v(), item.getBusinessTypeName().v());
				}).collect(Collectors.toList());
		return workTypeSelectionList;

	}
	
	public List<String> getWorkTypeNamesByCodes(List<String> codes) {
		String companyId = AppContexts.user().companyId();
		List<String> names = new ArrayList<>();
		if (codes == null || codes.isEmpty()) return names;
		for (String c : codes) {
			Optional<BusinessType> opt = businessTypeRepository.findByCode(companyId, c);
			if (opt.isPresent()) {
				String name = opt.get().getBusinessTypeName().v();
				names.add(name);
			}
		}
		return names;
	}
	
	public List<String> getWorkTypeNamesByCodesHasDefault(List<String> codes) {
		String companyId = AppContexts.user().companyId();
		List<String> names = new ArrayList<>();
		String codeNoName = I18NText.getText("KAL003_120");
		if (codes == null || codes.isEmpty()) return names;
		for (String c : codes) {
			Optional<BusinessType> opt = businessTypeRepository.findByCode(companyId, c);
			if (opt.isPresent()) {
				String name = opt.get().getBusinessTypeName().v();
				names.add(name);
			}else {
				names.add(c + " " + codeNoName);
			}
		}
		return names;
	}
}
