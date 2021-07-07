package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
public class KDP002AStartPageOutput {

	private StampSettingPerson stampSetting;
	private StampResultDisplay stampResultDisplay;
	private StampToSuppress stampToSuppress;
	private TimeCard timeCard;
	private List<EmployeeStampInfo> stampDataOfEmployees = new ArrayList<>();
	private int useATR;

	public KDP002AStartPageOutput(Optional<StampSettingPerson> settingPerson,
			Optional<StampResultDisplay> stampRsDisplay,
			TimeCard timeCard,
			List<EmployeeStampInfo> employeeStampDatas,
			StampToSuppress stampToSuppress, 
			Integer useArt) {
		this.stampSetting = settingPerson.isPresent() ? settingPerson.get() : null;
		this.stampResultDisplay = stampRsDisplay.isPresent() ? stampRsDisplay.get() : null;
		this.stampToSuppress = stampToSuppress;
		this.timeCard = timeCard;
		this.stampDataOfEmployees = employeeStampDatas;
		this.useATR = useArt;
	}

}
