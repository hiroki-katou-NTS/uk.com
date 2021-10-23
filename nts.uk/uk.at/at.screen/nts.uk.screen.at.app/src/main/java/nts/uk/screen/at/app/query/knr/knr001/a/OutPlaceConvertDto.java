package nts.uk.screen.at.app.query.knr.knr001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;

@AllArgsConstructor
@Data
public class OutPlaceConvertDto {

	// 置換する
	private int replace;
	
	// 外出理由
	private Integer goOutReason;
	
	public static OutPlaceConvertDto toDto(OutPlaceConvert domain) {
		return new OutPlaceConvertDto(
				domain.getReplace().value,
				domain.getGoOutReason().isPresent() ? domain.getGoOutReason().get().value : null);
	}
}
