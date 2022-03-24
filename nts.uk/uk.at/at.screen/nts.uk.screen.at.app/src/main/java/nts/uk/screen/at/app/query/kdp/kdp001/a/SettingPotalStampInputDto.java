package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampResultDisplayDto;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettingPotalStampInputDto {

	/**
	 * ・ポータル打刻の打刻設定
	 */
	private PortalStampSettingsDto portalStampSettings;

	private List<EmpInfoPotalStampDto> empInfos;
	
	private  StampResultDisplayDto stampResultDisplayDto;
	
	private List<EmployeeStampInfoDto> employeeStampInfo;
}
