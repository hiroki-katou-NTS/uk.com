package nts.uk.cnv.app.cnv.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.app.cnv.dto.GetErpColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.GetUkColumnsResultDto;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.cnv.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.cnv.tabledesign.ErpTableDesign;
import nts.uk.cnv.dom.cnv.tabledesign.ErpTableDesignRepository;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledefinetype.UkDataType;

@Stateless
public class TableInfoService {
	@Inject
	public ErpTableDesignRepository erpTableDesignRepository;
	@Inject
	public SnapshotRepository ssRepo;
	@Inject
	public ConversionTableRepository conversionTableRepository;

	public List<String> getErpTables() {
		return erpTableDesignRepository.getAllTableList();
	}

	public List<GetUkColumnsResultDto> getUkColumns(String category, String tableName, int recordNo) {
		val schemaSs = ssRepo.getSchemaLatest().orElseThrow(
				() -> new BusinessException(new RawErrorMessage("最新スキーマが見つかりません")));
		TableDesign td = ssRepo.getTableByName(schemaSs.getSnapshotId(), tableName).orElseThrow(
				() -> new BusinessException(new RawErrorMessage("テーブルがありません")));

		UkDataType dataType = new UkDataType();

		List<OneColumnConversion> conversionTable = conversionTableRepository.find(category, td.getName().v(), recordNo);

		return td.getColumns().stream()
				.map(col -> {
					boolean exists = conversionTable.stream()
						.anyMatch(ct -> ct.getTargetColumn().equals(col.getName()));
					return new GetUkColumnsResultDto(
						col.getId(),
						col.getName(),
						dataType.dataType(col.getType().getDataType(), col.getType().getLength(), col.getType().getScale()),
						exists
					);
				})
				.collect(Collectors.toList());
	}

	public List<GetErpColumnsResultDto> getErpColumns(String tableName) {
		ErpTableDesign td = erpTableDesignRepository.find(tableName)
				.orElseThrow(RuntimeException::new);

		return td.getColumns().stream()
				.map(col -> new GetErpColumnsResultDto(
						col.getId(),
						col.getName(),
						col.getType()
					))
				.collect(Collectors.toList());
	}

}
