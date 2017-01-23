package nts.uk.ctx.pr.core.app.find.rule.employment.unitprice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;

@Stateless
public class UnitPriceHistoryFinder {

	@Inject
	private UnitPriceHistoryRepository repository;

	public Optional<UnitPriceHistoryDto> find(String id) {
		return repository.findById(id).map(domain -> UnitPriceHistoryDto.fromDomain(domain));
	}

	public List<UnitPriceHistoryDto> findAll(String companyCode) {
		return repository.findAll(companyCode).stream().map(domain -> UnitPriceHistoryDto.fromDomain(domain))
				.collect(Collectors.toList());
	}
}
