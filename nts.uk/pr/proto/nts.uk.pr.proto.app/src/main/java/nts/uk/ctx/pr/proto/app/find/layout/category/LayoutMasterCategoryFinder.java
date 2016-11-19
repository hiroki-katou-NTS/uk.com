package nts.uk.ctx.pr.proto.app.find.layout.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;

@RequestScoped
public class LayoutMasterCategoryFinder {
	@Inject
	private LayoutMasterCategoryRepository repository;
	
	public List<LayoutMasterCategoryDto> getCategories(String companyCd,
			String layoutCd,
			int startYm){
		return this.repository.getCategories(companyCd, layoutCd, startYm).stream()
				.map(c -> LayoutMasterCategoryDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
