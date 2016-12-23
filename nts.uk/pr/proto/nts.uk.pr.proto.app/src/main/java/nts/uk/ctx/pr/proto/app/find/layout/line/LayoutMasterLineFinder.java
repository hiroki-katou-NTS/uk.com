package nts.uk.ctx.pr.proto.app.find.layout.line;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;

@Stateless
public class LayoutMasterLineFinder {
	@Inject
	private LayoutMasterLineRepository repository;
	
	public List<LayoutMasterLineDto> getLines(
			String companyCd,
			String stmtCd,
			int startYm){
		return this.repository.getLines(companyCd, stmtCd, startYm).stream()
				.map(item -> LayoutMasterLineDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
