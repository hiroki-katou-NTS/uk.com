package nts.uk.screen.com.app.cmf.cmf001.f.get;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;

/**
 * CSVベースレイアウト項目
 */
@Value
public class CsvBasedLayoutItemDto {

	int itemNo;
	boolean isFixedValue;
	Integer csvColumnNo;
	
	public static CsvBasedLayoutItemDto create(ImportingItemMapping mapping) {
		
		return new CsvBasedLayoutItemDto(
				mapping.getItemNo(),
				mapping.isFixedValue(),
				mapping.getCsvColumnNo().orElse(null));
	}
}
