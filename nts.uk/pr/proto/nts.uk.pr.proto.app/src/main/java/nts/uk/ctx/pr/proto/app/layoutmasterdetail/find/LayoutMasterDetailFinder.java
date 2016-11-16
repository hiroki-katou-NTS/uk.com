package nts.uk.ctx.pr.proto.app.layoutmasterdetail.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;

@RequestScoped
public class LayoutMasterDetailFinder {
	@Inject
	private LayoutMasterDetailRepository repository;
	/**
	 * find all layout master details
	 * @param companyCd
	 * @param stmtCd
	 * @param startYm
	 * @param categoryAtr
	 * @return
	 */
	public List<LayoutMasterDetailDto> getDetails(String companyCd,
			String stmtCd,
			int startYm,
			int categoryAtr){
		return this.repository.getDetails(companyCd,
				stmtCd,
				startYm, 
				categoryAtr).stream()
				.map(item ->LayoutMasterDetailDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	/**
	 * find layout master detail
	 * @param companyCd
	 * @param stmtCd
	 * @param startYm
	 * @param categoryAtr
	 * @param itemCd
	 * @return
	 */
	public Optional<LayoutMasterDetailDto> getDetail(String companyCd
			, String stmtCd
			, int startYm
			, int categoryAtr
			, String itemCd){
		return this.repository.getDetail(companyCd, stmtCd, startYm, categoryAtr, itemCd)
				.map(item -> LayoutMasterDetailDto.fromDomain(item));
	}
}
