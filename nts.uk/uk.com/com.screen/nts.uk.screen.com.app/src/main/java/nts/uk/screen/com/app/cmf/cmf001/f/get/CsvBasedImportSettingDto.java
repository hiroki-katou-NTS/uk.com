package nts.uk.screen.com.app.cmf.cmf001.f.get;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.BaseCsvInfoDto;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

/**
 * CSVベースの受入設定
 */
@Value
public class CsvBasedImportSettingDto {

	String settingCode;
	List<SampleCsvItemDto> sampleCsvItems;
	List<CsvBasedLayoutDto> layouts;
	Map<Integer, List<ImportableItem>> importableItems;
	
	public static CsvBasedImportSettingDto create(
			RequireCreate require,
			ExternalImportSetting setting,
			List<BaseCsvInfoDto> sampleCsvItems) {
		
		return new CsvBasedImportSettingDto(
				setting.getCode().v(),
				csvItems(sampleCsvItems),
				layouts(require, setting),
				importableItems(require, setting));
	}
	
	private static List<SampleCsvItemDto> csvItems(List<BaseCsvInfoDto> sampleCsvItems) {
		
		val list = new ArrayList<SampleCsvItemDto>();
		
		for (int i = 0; i < sampleCsvItems.size(); i++) {
			int columnNo = i + 1;
			val item = sampleCsvItems.get(i);
			list.add(new SampleCsvItemDto(columnNo, item.getName(), item.getSampleData()));
		}
		
		return list;
	}
	
	private static List<CsvBasedLayoutDto> layouts(RequireCreate require, ExternalImportSetting setting) {
		
		return setting.getDomainSettings().stream()
				.map(ds -> CsvBasedLayoutDto.create(ds))
				.collect(toList());
	}
	
	private static Map<Integer, List<ImportableItem>> importableItems(RequireCreate require, ExternalImportSetting setting) {
		
		return setting.getDomainSettings().stream()
				.map(ds -> ds.getDomainId())
				.map(id -> require.getImportableItems(id))
				.collect(toMap(l -> l.get(0).getDomainId().value, l -> l));
	}
	
	public interface RequireCreate {
		
		List<ImportableItem> getImportableItems(ImportingDomainId domainId);
	}
}
