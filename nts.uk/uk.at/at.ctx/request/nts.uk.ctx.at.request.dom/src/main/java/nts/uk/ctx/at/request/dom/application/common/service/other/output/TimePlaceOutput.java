package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.stamp.StampFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.時刻場所
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class TimePlaceOutput {
	
	/**
	 * 場所コード
	 */
	private Optional<WorkLocationCD> opWorkLocationCD;
	
	/**
	 * 外出区分
	 */
	private Optional<GoingOutReason> opGoOutReasonAtr;
	
	/**
	 * 打刻枠No
	 */
	private StampFrameNo frameNo;
	
	/**
	 * 時刻終了
	 */
	private Optional<TimeWithDayAttr> opEndTime;
	
	/**
	 * 時刻開始
	 */
	private Optional<TimeWithDayAttr> opStartTime;
	
}
