package find.person.info.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.bs.person.dom.person.info.category.HistoryTypes;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoCategoryFinder {

	@Inject
	IInternationalization internationalization;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	public List<PerInfoCtgFullDto> getAllPerInfoCtg() {
		return perInfoCtgRepositoty
				.getAllPerInfoCategory(AppContexts.user().companyId(), AppContexts.user().contractCode()).stream()
				.map(p -> {
					return new PerInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
							p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
							p.getCategoryType().value, p.getIsFixed().value);
				}).collect(Collectors.toList());
	};

	public PerInfoCtgFullDto getPerInfoCtg(String perInfoCategoryId) {
		return perInfoCtgRepositoty.getPerInfoCategory(perInfoCategoryId, AppContexts.user().contractCode()).map(p -> {
			return new PerInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(), p.getCategoryName().v(),
					p.getPersonEmployeeType().value, p.getIsAbolition().value, p.getCategoryType().value,
					p.getIsFixed().value);
		}).orElse(null);
	};

	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompany() {
		List<PerInfoCtgShowDto> categoryList = perInfoCtgRepositoty
				.getAllPerInfoCategory(AppContexts.user().companyId(), AppContexts.user().contractCode()).stream()
				.map(p -> {
					return new PerInfoCtgShowDto(p.getPersonInfoCategoryId(), p.getCategoryName().v(), p.getCategoryType().value);
				}).collect(Collectors.toList());
		
		List<EnumConstant> historyTypes = EnumAdaptor.convertToValueNameList(HistoryTypes.class, internationalization);
		return new PerInfoCtgDataEnumDto(historyTypes, categoryList);
	};
}
