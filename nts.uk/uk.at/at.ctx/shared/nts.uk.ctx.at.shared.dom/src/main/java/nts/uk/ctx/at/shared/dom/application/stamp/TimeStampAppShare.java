package nts.uk.ctx.at.shared.dom.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         打刻申請時刻
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeStampAppShare {
	// 反映先
	private DestinationTimeAppShare destinationTimeApp;
	// 時刻
	private TimeWithDayAttr timeOfDay;
	// 勤務場所
	private Optional<WorkLocationCD> workLocationCd;
	// 外出理由
	private Optional<GoingOutReason> appStampGoOutAtr;

}
