package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;

/**
 * @author anhdt
 *
 */
@Data
public class KDP002AStartPageSettingDto {
	
	private StampSettingDto stampSetting;
	private StampResultDisplayDto stampResultDisplay;
	
	public KDP002AStartPageSettingDto(Optional<StampSettingPerson> settingPerson, Optional<StampResultDisplay> stampRsDisplay, TimeCard timeCard) {
		this.stampSetting = new StampSettingDto(settingPerson);
		this.stampResultDisplay = new StampResultDisplayDto(stampRsDisplay);
	}
	
}
