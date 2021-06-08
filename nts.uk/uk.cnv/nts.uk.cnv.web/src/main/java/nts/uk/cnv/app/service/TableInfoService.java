package nts.uk.cnv.app.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.app.dto.GetErpColumnsResultDto;
import nts.uk.cnv.core.dom.tabledesign.ErpTableDesign;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;

@Stateless
public class TableInfoService {
	@Inject
	public ErpTableDesignRepository erpTableDesignRepository;
	@Inject
	public ConversionTableRepository conversionTableRepository;

	public List<String> getErpTables() {
		return erpTableDesignRepository.getAllTableList();
	}

	public List<String> getUkColumns(String category, String tableName, int recordNo) {
		return conversionTableRepository.find(category, tableName, recordNo).stream()
			.map(oneColCnv -> oneColCnv.getTargetColumn())
			.collect(Collectors.toList());
	}

	public List<GetErpColumnsResultDto> getErpColumns(String tableName) {
		ErpTableDesign td = erpTableDesignRepository.find(tableName)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("ERPのテーブルが見つかりません")));

		return td.getColumns().stream()
				.map(col -> new GetErpColumnsResultDto(
						col.getId(),
						col.getName(),
						col.getType()
					))
				.collect(Collectors.toList());
	}

}
