package find.person.info.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoCategoryFinder {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	public List<PerInfoCategoryDto> getAllPerInfoCtg() {
		return perInfoCtgRepositoty
				.getAllPerInfoCategory(AppContexts.user().companyId(), AppContexts.user().contractCode()).stream()
				.map(p -> {
					return new PerInfoCategoryDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
							p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
							p.getCategoryType().value, p.getIsFixed().value);
				}).collect(Collectors.toList());
	};

	public PerInfoCategoryDto getPerInfoCtg(String perInfoCategoryId) {
		return perInfoCtgRepositoty.getPerInfoCategory(perInfoCategoryId, AppContexts.user().contractCode()).map(p -> {
			return new PerInfoCategoryDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(), p.getCategoryName().v(),
					p.getPersonEmployeeType().value, p.getIsAbolition().value, p.getCategoryType().value,
					p.getIsFixed().value);
		}).orElse(null);
	};

}
