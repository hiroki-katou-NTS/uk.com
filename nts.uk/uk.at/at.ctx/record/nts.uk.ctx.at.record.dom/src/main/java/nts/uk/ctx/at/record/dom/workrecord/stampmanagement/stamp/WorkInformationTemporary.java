package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * 勤務先情報Temporary									
 * @author laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkInformationTemporary {

	// 職場ID
	private  Optional<String> workplaceID;

	// 場所コード
	private  Optional<WorkLocationCD> workLocationCD;
}
