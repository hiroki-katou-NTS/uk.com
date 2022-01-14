package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.exio.app.find.exi.condset.StdAcceptCondSetDto;

/**
 * 起動のOutput
 *
 */
@Data
@Builder
public class OutputOfStartupDto {
	
	//Smile連携受入設定
	private List<SmileCooperationAcceptanceSettingDto> smileCooperationAcceptanceSettings;
	
	// Map＜条件設定コード、条件設定名称＞
	private List<StdAcceptCondSetDto> stdAcceptCondSetDtos;
}
