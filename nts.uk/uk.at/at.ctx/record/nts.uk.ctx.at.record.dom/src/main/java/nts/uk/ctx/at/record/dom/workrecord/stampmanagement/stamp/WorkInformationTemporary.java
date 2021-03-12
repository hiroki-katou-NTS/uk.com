package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * 勤務先情報Temporary									
 * @author laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkInformationTemporary {

	// 職場ID
	@Getter
	private  Optional<String> workplaceID;

	// 場所コード
	@Getter
	private  Optional<WorkLocationCD> workLocationCD;
}
