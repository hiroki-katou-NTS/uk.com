package nts.uk.ctx.exio.app.input.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

@Value
public class ExternalImportSettingListItemDto {
	
	/** 受入設定コード */
	private String code;
	
	/** 受入設定名称 */
	private String name;
	
	public static List<ExternalImportSettingListItemDto> fromDomain(List<ExternalImportSetting> domains) {
		return domains.stream()
				.map(dom -> new ExternalImportSettingListItemDto(dom.getCode().toString(), dom.getName().toString()))
				.collect(Collectors.toList());
	}
}
