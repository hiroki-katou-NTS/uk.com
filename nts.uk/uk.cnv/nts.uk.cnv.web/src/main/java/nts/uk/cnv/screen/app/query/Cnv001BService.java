package nts.uk.cnv.screen.app.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.cnv.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.td.tabledesign.UkTableDesignRepository;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadDataDto;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadParamDto;

@Stateless
public class Cnv001BService {

	@Inject
	UkTableDesignRepository ukTableDesignRepository;

	@Inject
	ConversionCategoryTableRepository repository;

	public Cnv001BLoadDataDto loadData(Cnv001BLoadParamDto param) {

		List<String> conversionTableCategories = repository.get(param.getCategory()).stream()
				.map(cate -> cate.getTablename())
				.collect(Collectors.toList());

		List<String> tables = ukTableDesignRepository.getAllTableList(param.getFeature(), param.getDateTime()).stream()
				.map(tb -> tb.getTableName())
				.collect(Collectors.toList());

		tables.removeAll(conversionTableCategories);

		return new Cnv001BLoadDataDto(conversionTableCategories, tables);
	}
}
