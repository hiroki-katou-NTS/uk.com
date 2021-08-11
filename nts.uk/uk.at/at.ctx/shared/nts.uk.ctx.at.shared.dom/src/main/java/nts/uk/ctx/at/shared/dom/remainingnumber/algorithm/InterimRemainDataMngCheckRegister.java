package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Map;

import nts.arc.time.GeneralDate;

public interface InterimRemainDataMngCheckRegister {
	/**
	 * 登録時の残数チェック
	 * @param inputParam
	 * @return
	 */
	EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam);
	
	InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput);
}
