package nts.uk.screen.com.app.cmf.cmf001.f.get;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;

/**
 * CSVベースレイアウト
 */
@Value
public class CsvBasedLayoutDto {

	int domainId;
	List<CsvBasedLayoutItemDto> items;
	
	public static CsvBasedLayoutDto create(DomainImportSetting setting) {
		
		val items = setting.getAssembly().getMapping().getMappings().stream()
				.map(m -> CsvBasedLayoutItemDto.create(m))
				.collect(toList());
		
		return new CsvBasedLayoutDto(setting.getDomainId().value, items);
	}
}
