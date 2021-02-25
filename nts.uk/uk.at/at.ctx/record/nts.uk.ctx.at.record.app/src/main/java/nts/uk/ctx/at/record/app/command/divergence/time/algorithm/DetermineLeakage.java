package nts.uk.ctx.at.record.app.command.divergence.time.algorithm;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;

@Stateless
public class DetermineLeakage {

	//乖離理由入力選択漏れのチェック
	public JudgmentResult checkInputMissing(String userId, GeneralDate processDate, int divergenceTimeNo,
			DiverdenceReasonCode divReasonCode, DivergenceReason divReason, List<String> listDPError) {
		if (!listDPError.isEmpty()) {
			return JudgmentResult.NORMAL;
		}
		
		//乖離理由入力選択のチェック
		return checkDivergenceReasonInput(divReasonCode, divReason);
	}
	
	//乖離理由入力選択のチェック
	public JudgmentResult checkDivergenceReasonInput(DiverdenceReasonCode divReasonCode, DivergenceReason divReason){
		if (divReasonCode != null)
			return JudgmentResult.ERROR;
		if (divReason == null)
			return JudgmentResult.NORMAL;
		return JudgmentResult.ERROR;
	}
}
