package nts.uk.screen.at.app.query.kdp.kdp010.a.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplaySettingsStampScreenDto;

@Getter
@NoArgsConstructor
public class SettingsSmartphoneStampDto {
	
	private String cid;
	
	private DisplaySettingsStampScreenDto displaySettingsStampScreen;
	
	private List<StampPageLayoutDto> pageLayoutSettings;
	
	private Integer buttonEmphasisArt;	
	
	@Setter
	private Integer googleMap;
	public void settingsSmartphoneStamp(SettingsSmartphoneStamp domain) {
		this.cid = domain.getCid(); 
		this.displaySettingsStampScreen = DisplaySettingsStampScreenDto.fromDomain(domain.getDisplaySettingsStampScreen()); 
		this.pageLayoutSettings = domain.getPageLayoutSettings().stream().map(c->StampPageLayoutDto.fromDomain(c)).collect(Collectors.toList()); 
		this.buttonEmphasisArt = domain.isButtonEmphasisArt()?1:0;
	}
}
