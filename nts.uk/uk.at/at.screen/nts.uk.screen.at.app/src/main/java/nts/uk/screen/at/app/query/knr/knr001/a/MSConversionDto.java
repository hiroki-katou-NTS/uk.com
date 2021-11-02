package nts.uk.screen.at.app.query.knr.knr001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MSConversion;

@AllArgsConstructor
@Data
public class MSConversionDto {

	// 打刻分類
	private int stampClassifi;
	
	// 反映先
	private int stampDestination;
	
	public static MSConversionDto toDto(MSConversion domain) {
		return new MSConversionDto(
				domain.getStampClassifi().value,
				domain.getStampDestination().value);
	}
}
