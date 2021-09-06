package nts.uk.cnv.screen.app.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;
import nts.uk.cnv.screen.app.query.dto.Cnv001ALoadDataDto;
import nts.uk.cnv.screen.app.query.dto.RecordDto;
import nts.uk.cnv.screen.app.query.dto.SourceDto;;

@Stateless
public class Cnv001AService {

	@Inject
	ConversionSourcesRepository conversionSourceRepo;

	@Inject
	ConversionRecordRepository conversionTableRepo;

	@Inject
	ErpTableDesignRepository erpTableDesignRepo;

	public Cnv001ALoadDataDto loadData(String categoryName, String tableName) {

		List<SourceDto> sources = conversionSourceRepo.getByCategory(categoryName).stream()
				.map(s -> new SourceDto(
						s.getSourceId(),
						s.getCategory(),
						s.getSourceTableName(),
						s.getCondition(),
						s.getMemo(),
						s.getDateColumnName().orElse(""),
						s.getStartDateColumnName().orElse(""),
						s.getEndDateColumnName().orElse(""),
						s.getDateType().orElse("")
						))
				.collect(Collectors.toList());

		List<RecordDto> records = conversionTableRepo.getRecords(categoryName, tableName).stream()
				.map(r -> new RecordDto(
						r.getRecordNo(),
						r.getTableName(),
						r.getExplanation(),
						r.getWhereCondition(),
						r.getSourceId(),
						r.isRemoveDuplicate()
					))
				.collect(Collectors.toList());

		List<String> ukTables = erpTableDesignRepo.getAllTableList();

		return new Cnv001ALoadDataDto(records, sources, ukTables);
	}
}
