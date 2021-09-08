package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;

/**
 * @author anhdt
 *
 */
@Data
public class StampSettingDto {
	private boolean buttonEmphasisArt;
	private Integer historyDisplayMethod;
	private Integer correctionInterval;
	private String textColor;
	private Integer resultDisplayTime;
	private List<StampPageLayoutDto> pageLayouts = new ArrayList<>();
	public StampSettingDto (Optional<StampSettingPerson> oDomain) {
		if(oDomain.isPresent()) {
			StampSettingPerson domain = oDomain.get();
			this.buttonEmphasisArt = domain.isButtonEmphasisArt();
			
			DisplaySettingsStampScreen screenSet =  domain.getStampingScreenSet();
			this.historyDisplayMethod = domain.getHistoryDisplayMethod().value;
			this.correctionInterval = screenSet.getCorrectionInterval().v();
			
			this.textColor = screenSet.getSettingDateTimeColor().getTextColor().v();
			this.resultDisplayTime = screenSet.getResultDisplayTime().v();
			
			List<StampPageLayout> layouts = domain.getLstStampPageLayout();
			layouts.sort((l1, l2) -> l1.getPageNo().v().compareTo(l2.getPageNo().v()));
			for(StampPageLayout layout : layouts) {
				this.pageLayouts.add(new StampPageLayoutDto(layout));
			}
		}
	}
	public StampSettingDto(StampSetCommunal domain) {
		
		this.buttonEmphasisArt = domain.isEmployeeAuthcUseArt();
		
		DisplaySettingsStampScreen screenSet =  domain.getDisplaySetStampScreen();
		this.correctionInterval = screenSet.getCorrectionInterval().v();
		
		SettingDateTimeColorOfStampScreen colorSetting =  screenSet.getSettingDateTimeColor();
		this.textColor = colorSetting.getTextColor().v();
		this.resultDisplayTime = screenSet.getResultDisplayTime().v();
		
		List<StampPageLayout> layouts = domain.getLstStampPageLayout();
		layouts.sort((l1, l2) -> l1.getPageNo().v().compareTo(l2.getPageNo().v()));
		for(StampPageLayout layout : layouts) {
			this.pageLayouts.add(new StampPageLayoutDto(layout));
		}
	}
}
