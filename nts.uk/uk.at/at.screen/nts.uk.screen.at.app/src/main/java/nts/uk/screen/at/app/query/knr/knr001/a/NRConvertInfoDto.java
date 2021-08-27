package nts.uk.screen.at.app.query.knr.knr001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRConvertInfo;

@AllArgsConstructor
@Data
public class NRConvertInfoDto {

	// 外出打刻の変換
	private OutPlaceConvertDto outPlaceConvert;
	
	// 出退勤を入退門に変換
	private int entranceExit;
	
	public static NRConvertInfoDto toDto(NRConvertInfo domain) {
		
		return new NRConvertInfoDto(OutPlaceConvertDto.toDto(domain.getOutPlaceConvert()), domain.getEntranceExit().value);
	}
}
