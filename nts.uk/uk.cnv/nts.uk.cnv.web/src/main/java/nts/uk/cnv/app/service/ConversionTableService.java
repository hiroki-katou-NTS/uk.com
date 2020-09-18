package nts.uk.cnv.app.service;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.app.dto.GetCategoryTablesDto;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;

@Stateless
public class ConversionTableService {

	@Inject
	ConversionCategoryTableRepository repository;

	@Inject
	ConversionSourcesRepository conversionSourceRepo;

	public GetCategoryTablesDto getCategoryTables(String category) {
		return new GetCategoryTablesDto(
				repository.get(category).stream()
					.map(ct -> ct.getTablename())
					.collect(Collectors.toList())
			);
	}

}
