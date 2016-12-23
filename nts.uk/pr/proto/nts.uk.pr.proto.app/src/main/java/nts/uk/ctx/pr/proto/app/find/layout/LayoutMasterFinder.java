package nts.uk.ctx.pr.proto.app.find.layout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;

@Stateless
public class LayoutMasterFinder {
	@Inject
	private LayoutMasterRepository repository;

	/**
	 * finder all layout by company code and layout code
	 * 
	 * @param companyCode
	 * @return
	 */
	public List<LayoutDto> getAllLayout(String companyCode) {
		return this.repository.getLayouts(companyCode).stream().map(layout -> LayoutDto.fromDomain(layout))
				.collect(Collectors.toList());
	}

	/**
	 * finder layout by company code, layout code, start year month
	 * 
	 * @param companyCode
	 * @param stmtCode
	 * @param strYm
	 * @return
	 */
	public Optional<LayoutDto> getLayout(String companyCode, String stmtCode, String historyId) {
		return this.repository.getLayout(companyCode, historyId, stmtCode).map(layout -> LayoutDto.fromDomain(layout));
	}
	/**
	 * find all layouts with max startYM
	 * @param companyCode
	 * @return
	 */
	public List<LayoutDto> getLayoutsWithMaxStartYm(String companyCode)
	{
		return this.repository.getLayoutsWithMaxStartYm(companyCode).stream()
				.map(layout -> LayoutDto.fromDomain(layout))
				.collect(Collectors.toList());
	}

}
