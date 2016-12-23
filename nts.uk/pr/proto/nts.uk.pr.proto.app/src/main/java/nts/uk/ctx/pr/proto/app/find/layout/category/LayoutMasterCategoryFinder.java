package nts.uk.ctx.pr.proto.app.find.layout.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.app.find.layout.detail.LayoutMasterDetailDto;
import nts.uk.ctx.pr.proto.app.find.layout.line.LayoutMasterLineDto;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LayoutMasterCategoryFinder {
	@Inject
	private LayoutMasterCategoryRepository cateRepo;
	@Inject
	private LayoutMasterLineRepository lineRepo;
	@Inject
	private LayoutMasterDetailRepository detailRepo;
	
	public List<LayoutMasterCategoryDto> getCategories(String companyCd,
			String layoutCd,
			int startYm){
		return this.cateRepo.getCategories(companyCd, layoutCd, startYm).stream()
				.map(c -> LayoutMasterCategoryDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * get LayoutMasterCategory with lines and details data by LayoutCode
	 * @param layoutCd
	 * @param startYm
	 * @return
	 */
	public List<LayoutMasterCategoryDto> getCategoriesFullData(String layoutCd,
			int startYm){
		String companyCd = AppContexts.user().companyCode();
		List<LayoutMasterCategoryDto> categories = getCategories(layoutCd, startYm, companyCd); 
		List<LayoutMasterLineDto> lines = getLines(layoutCd, startYm, companyCd); 
		List<LayoutMasterDetailDto> details = getDetails(layoutCd, startYm, companyCd); 
		List<LayoutMasterDetailDto> detail9Items = new ArrayList<>(); 
		
		categories.sort((c1, c2) -> c1.getCtgPos() - c2.getCtgPos());
		for (LayoutMasterCategoryDto category : categories) {
			List<LayoutMasterLineDto> linesOfCategory = lines.stream().filter(l -> l.getCategoryAtr() == category.getCategoryAtr()).collect(Collectors.toList());
			linesOfCategory.sort((c1, c2) -> c1.getLinePosition() - c2.getLinePosition());
			for (LayoutMasterLineDto line : linesOfCategory){
				detail9Items = new ArrayList<>(); 
				for (int i = 1; i <= 9; i++) {
					int posColumn = i;
					LayoutMasterDetailDto item = details.stream().filter(d -> d.getAutoLineId().equals(line.getAutoLineId())
							&& d.getCategoryAtr() == line.getCategoryAtr()
							&& d.getItemPosColumn() == posColumn).findFirst()
							.orElse(
									new LayoutMasterDetailDto(layoutCd, startYm, category.getEndYm(), category.getCategoryAtr(), "itemTemp-" + String.valueOf(i), line.getAutoLineId(), i, "+", 0, "", 0, 0, 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0));
					detail9Items.add(item);
				}
				
				line.getDetails().addAll(detail9Items);
			}
			category.getLines().addAll(linesOfCategory);
		}
		
		return categories;
	}


	private List<LayoutMasterDetailDto> getDetails(String layoutCd, int startYm, String companyCd) {
		return this.detailRepo.getDetails(companyCd, layoutCd, startYm).stream()
				.map(c -> LayoutMasterDetailDto.fromDomain(c))
				.collect(Collectors.toList());
	}


	private List<LayoutMasterLineDto> getLines(String layoutCd, int startYm, String companyCd) {
		return this.lineRepo.getLines(companyCd, layoutCd, startYm).stream()
				.map(c -> LayoutMasterLineDto.fromDomain(c))
				.collect(Collectors.toList());
	}


	private List<LayoutMasterCategoryDto> getCategories(String layoutCd, int startYm, String companyCd) {
		return this.cateRepo.getCategories(companyCd, layoutCd, startYm).stream()
				.map(c -> LayoutMasterCategoryDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
