package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice.Info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.LateOrEarlyInfoDto;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyInfo;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class LateEarlyDateChangeFinderDto {

	private AppDispInfoWithDateDto appDispInfoWithDateOutput;
	
	private List<LateOrEarlyInfoDto> lateOrEarlyInfoLst;
	
	private String errorInfo;
	
	public static LateEarlyDateChangeFinderDto fromDomain(LateEarlyDateChangeOutput dto) {
		List<LateOrEarlyInfoDto> earlyInfos = new ArrayList<LateOrEarlyInfoDto>();
		
		for(LateOrEarlyInfo info : dto.getLateOrEarlyInfoLst()) {
			earlyInfos.add(LateOrEarlyInfoDto.convertDto(info));
		}
		
		return new LateEarlyDateChangeFinderDto(AppDispInfoWithDateDto.fromDomain(dto.getAppDispInfoWithDateOutput()), 
				earlyInfos, 
				dto.getErrorInfo().isPresent() ? dto.getErrorInfo().get() : null);
	}
}
