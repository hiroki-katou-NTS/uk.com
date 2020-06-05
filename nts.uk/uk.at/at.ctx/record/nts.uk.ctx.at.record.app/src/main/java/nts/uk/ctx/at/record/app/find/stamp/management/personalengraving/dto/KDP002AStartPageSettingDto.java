package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;

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

	private List<EmpStampCardDto> stampCards = new ArrayList<>();

	public KDP002AStartPageSettingDto(KDP002AStartPageOutput settings) {
		this.stampSetting = new StampSettingDto( Optional.ofNullable(settings.getStampSetting()));
		this.stampResultDisplay = new StampResultDisplayDto(Optional.ofNullable(settings.getStampResultDisplay()));
		this.timeCard = new TimeCardDto(settings.getTimeCard());
		this.stampToSuppress = new StampToSuppressDto(settings.getStampToSuppress());
		
		for (EmployeeStampInfo stampInfo : settings.getStampDataOfEmployees()) {
			StampDataOfEmployeesDto stampData = new StampDataOfEmployeesDto(stampInfo);
			this.stampDataOfEmployees.addAll(stampData.getStampRecords());
		}
	}

}
