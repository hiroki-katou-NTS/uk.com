package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * 勤務先情報Temporary									
 * @author laitv
 *
 */
@AllArgsConstructor
@Getter
public class WorkInformationTemporary {

	// 職場ID
	@Getter
	private final Optional<String> workplaceID;

	// 場所コード
	@Getter
	private final Optional<WorkLocationCD> workLocationCD;
}
