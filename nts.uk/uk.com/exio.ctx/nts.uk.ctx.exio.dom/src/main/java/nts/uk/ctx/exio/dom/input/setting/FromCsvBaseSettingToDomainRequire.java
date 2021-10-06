package nts.uk.ctx.exio.dom.input.setting;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.csvimport.BaseCsvInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;

public interface FromCsvBaseSettingToDomainRequire {
	Optional<BaseCsvInfo> createBaseCsvInfo(String baseCsvFileId, ExternalImportCsvFileInfo fileInfo);

	Map<Integer, List<String>> readBaseCsvWithFirstData(ExternalImportCsvFileInfo fileInfo);
}
