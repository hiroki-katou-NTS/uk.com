package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.management.ColorSetting;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.stamp.management.StampingScreenSet;

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
	private String backGroundColor;
	private Integer resultDisplayTime;
	private List<StampPageLayoutDto> pageLayouts = new ArrayList<>();
	public StampSettingDto (Optional<StampSettingPerson> oDomain) {
		if(oDomain.isPresent()) {
			StampSettingPerson domain = oDomain.get();
			this.buttonEmphasisArt = domain.isButtonEmphasisArt();
			
			StampingScreenSet screenSet =  domain.getStampingScreenSet();
			this.historyDisplayMethod = screenSet.getHistoryDisplayMethod().value;
			this.correctionInterval = screenSet.getCorrectionInterval().v();
			
			ColorSetting colorSetting =  screenSet.getColorSetting();
			this.textColor = colorSetting.getTextColor().v();
			this.backGroundColor = colorSetting.getBackGroundColor().v();
			this.resultDisplayTime = screenSet.getResultDisplayTime().v();
			
			for(StampPageLayout layout : domain.getLstStampPageLayout()) {
				this.pageLayouts.add(new StampPageLayoutDto(layout));
			}
		}
		
		
	}
}
