package nts.uk.cnv.screen.app.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.core.dom.TableIdentity;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadDataDto;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadParamDto;

@Stateless
public class Cnv001BService {

	@Inject
	ConversionCategoryTableRepository repository;

	public Cnv001BLoadDataDto loadData(Cnv001BLoadParamDto param) {

		List<TableIdentity> conversionTableCategories = repository.get(param.getCategory()).stream()
				.map(cate -> new TableIdentity(cate.getTable().getTableId(), cate.getTable().getName()))
				.collect(Collectors.toList());

		return new Cnv001BLoadDataDto(conversionTableCategories);
	}
}
