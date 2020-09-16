package nts.uk.cnv.screen.app.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.tabledesign.UkTableDesignRepository;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadDataDto;

@Stateless
public class Cnv001BService {

	@Inject
	UkTableDesignRepository ukTableDesignRepository;

	@Inject
	ConversionCategoryTableRepository repository;

	public Cnv001BLoadDataDto loadData(String category) {

		List<String> conversionTableCategories = repository.get(category).stream()
				.map(cate -> cate.getCategoryName())
				.collect(Collectors.toList());

		List<String> tables = ukTableDesignRepository.getAllTableList().stream()
				.map(tb -> tb.getTableName())
				.collect(Collectors.toList());

		tables.removeAll(conversionTableCategories);

		return new Cnv001BLoadDataDto(conversionTableCategories, tables);
	}
}
