package nts.uk.ctx.pr.proto.app.layout.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;

@RequestScoped
public class LayoutFinder {
	@Inject
	private LayoutMasterRepository repository;

	/**
	 * finder all layout by company code and layout code
	 * 
	 * @param companyCode
	 * @param stmtCode
	 * @return
	 */
	public List<LayoutDto> getAllLayout(String companyCode, String stmtCode) {
		return this.repository.getLayouts(companyCode, stmtCode).stream().map(layout -> LayoutDto.fromDomain(layout))
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
	public Optional<LayoutDto> getLayout(String companyCode, String stmtCode, int strYm) {
		return this.repository.getLayout(companyCode, stmtCode, strYm).map(layout -> LayoutDto.fromDomain(layout));
	}

}
