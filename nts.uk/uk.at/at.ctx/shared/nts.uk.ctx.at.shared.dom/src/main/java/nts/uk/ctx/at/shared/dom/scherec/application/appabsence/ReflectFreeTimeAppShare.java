package nts.uk.ctx.at.shared.dom.scherec.application.appabsence;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author thanhnx
 * 
 *         休暇申請反映情報(反映用)
 *
 */
@Data
public class ReflectFreeTimeAppShare {

	// 時間帯(勤務NO付き)
	private List<TimeZoneWithWorkNo> workingHours;

	// 時間消化申請
	private Optional<TimeDigestApplicationShare> timeDegestion;

	// 勤務情報
	private WorkInformation workInfo;

	// するしない区分
	private NotUseAtr workChangeUse;

	public ReflectFreeTimeAppShare(List<TimeZoneWithWorkNo> workingHours,
			Optional<TimeDigestApplicationShare> timeDegestion, WorkInformation workInfo, NotUseAtr workChangeUse) {
		this.workingHours = workingHours;
		this.timeDegestion = timeDegestion;
		this.workInfo = workInfo;
		this.workChangeUse = workChangeUse;
	}
}
