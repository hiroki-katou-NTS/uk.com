package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.LeaveCategory;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         NRLの変換情報
 */
@AllArgsConstructor
@Getter
public class NRConvertInfo implements StampInfoConversion {

	// 外出打刻の変換
	private final OutPlaceConvert outPlaceConvert;

	// 出退勤を入退門に変換
	private final NotUseAtr entranceExit;

	// [1] NRから変換する
	@Override
	public Optional<ChangeClockAtr> convertFromNR(LeaveCategory leavCategory) {
		return leavCategory.toChangeClockAtr(entranceExit == NotUseAtr.USE);
	}

	@Override
	public Optional<GoingOutReason> getOutReasonWhenReplace() {
		if (this.outPlaceConvert.getReplace() == NotUseAtr.USE) {
			return this.outPlaceConvert.getGoOutReason();
		}
		
		return Optional.empty();
	}
}
