package nts.uk.ctx.exio.app.input.find.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.exio.app.input.find.setting.assembly.ExternalImportLayoutDto;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

@Value
public class ExternalImportSettingDto {
	
	/** 受入設定コード */
	private String code;
	
	/** 受入設定名称 */
	private String name;
	
	/** 受入グループID */
	private int group;
	
	/** 受入モード */
	private int mode;
	
	/** CSVの項目名取得行 */
	private int itemNameRow;
	
	/** CSVの取込開始行 */
	private int importStartRow;
	
	/** レイアウト */
	private List<ExternalImportLayoutDto> layouts;
	
	public static ExternalImportSettingDto fromDomain(Require require, ExternalImportSetting domain) {
		
		return new ExternalImportSettingDto(
				domain.getCode().toString(), 
				domain.getName().toString(), 
				domain.getExternalImportGroupId().value, 
				domain.getImportingMode().value, 
				domain.getAssembly().getCsvFileInfo().getItemNameRowNumber().hashCode(), 
				domain.getAssembly().getCsvFileInfo().getImportStartRowNumber().hashCode(), 
				domain.getAssembly().getMapping().getMappings().stream()
				.map(m -> ExternalImportLayoutDto.fromDomain(require, domain.getExternalImportGroupId(), m))
				.collect(Collectors.toList()));
	}
	
	public static interface Require extends ExternalImportLayoutDto.Require{
	}
}
