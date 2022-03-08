package nts.uk.screen.com.app.cmf.cmf001.f.get;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;

/**
 * CSVベースレイアウト
 */
@Value
public class CsvBasedLayoutDto {

	int domainId;
	List<CsvBasedLayoutItemDto> items;
	
	public static CsvBasedLayoutDto create(RequireCreate require, DomainImportSetting setting) {
		return new CsvBasedLayoutDto(setting.getDomainId().value, items(require, setting));
	}
	
	private static List<CsvBasedLayoutItemDto> items(RequireCreate require, DomainImportSetting setting) {
		
		return setting.getAssembly().getMapping().getMappings().stream()
				.map(m -> CsvBasedLayoutItemDto.create(m, require.getImportableItem(setting.getDomainId(), m.getItemNo())))
				.collect(toList());
	}
	
	public interface RequireCreate {
		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);
	}
}
