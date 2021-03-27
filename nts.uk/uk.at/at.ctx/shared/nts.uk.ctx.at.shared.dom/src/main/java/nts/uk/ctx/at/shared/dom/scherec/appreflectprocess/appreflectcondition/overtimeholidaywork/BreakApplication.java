package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectionOfBreak;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 
 * 休憩の申請反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BreakApplication {
    /**
     * 休憩を反映する
     */
    private NotUseAtr breakReflectAtr;
    
    /**
     * @author thanh_nx
     *
     *         休憩の申請反映
     */
	public void process(List<TimeZoneWithWorkNo> breakTimeOp, DailyRecordOfApplication dailyApp) {

		// [休憩を反映する]をチェック
		if (this.getBreakReflectAtr() == NotUseAtr.NOT_USE) {
			return;
		}

		// 休憩の反映
		ReflectionOfBreak.process(breakTimeOp, dailyApp);
	}
}
