package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataOfEmployees;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;

/**
 * @author anhdt
 *
 */
@Data
public class KDP002AStartPageSettingDto {

	private TimeCardDto timeCard;

	private StampSettingDto stampSetting;
	private StampResultDisplayDto stampResultDisplay;
	
	private List<StampRecordDto> stampDataOfEmployees = new ArrayList<>();
	
	private StampToSuppressDto stampToSuppress;

	public KDP002AStartPageSettingDto(Optional<StampSettingPerson> settingPerson,
			Optional<StampResultDisplay> stampRsDisplay, TimeCard timeCard,
			List<StampDataOfEmployees> employeeStampDatas, StampToSuppress stampToSuppress) {
		
		this.stampSetting = new StampSettingDto(settingPerson);
		this.stampResultDisplay = new StampResultDisplayDto(stampRsDisplay);
		this.timeCard = new TimeCardDto(timeCard);
		this.stampToSuppress = new StampToSuppressDto(stampToSuppress);
		
		for (StampDataOfEmployees stampData : employeeStampDatas) {
			this.stampDataOfEmployees.addAll(new StampDataOfEmployeesDto(stampData).getStampRecords());
		}

	}

}
