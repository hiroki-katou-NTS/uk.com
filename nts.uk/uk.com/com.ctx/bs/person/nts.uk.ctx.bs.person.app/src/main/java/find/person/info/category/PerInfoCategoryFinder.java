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

	public List<PerInfoCtgNewLayoutDto> getAllPerInfoCtgNewLayout() {
		return perInfoCtgRepositoty.getAllPerInfoCategory(AppContexts.user().companyId()).stream().map(p -> {
			return new PerInfoCtgNewLayoutDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
					p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
					p.getCategoryType().value, p.getIsFixed().value);
		}).collect(Collectors.toList());
	};

	public PerInfoCtgNewLayoutDto getPerInfoCtgNewLayout(String perInfoCategoryId) {
		return perInfoCtgRepositoty.getPerInfoCategory(perInfoCategoryId).map(p -> {
			return new PerInfoCtgNewLayoutDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
					p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
					p.getCategoryType().value, p.getIsFixed().value);
		}).orElse(null);
	};

}
