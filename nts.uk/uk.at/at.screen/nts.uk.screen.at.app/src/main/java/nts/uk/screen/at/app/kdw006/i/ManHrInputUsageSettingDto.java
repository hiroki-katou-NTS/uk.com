package nts.uk.screen.at.app.kdw006.i;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.usagesetting.ManHrInputUsageSetting;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManHrInputUsageSettingDto {

	/** 使用区分 */
	private int usrAtr;
	
	/** 設備入力を利用する*/
	private int equipmentUseAtr;

	public static ManHrInputUsageSettingDto fromDomain(ManHrInputUsageSetting domain) {
		return new ManHrInputUsageSettingDto(domain.getUsrAtr().value, domain.getEquipmentUseAtr().value);
	}

}
