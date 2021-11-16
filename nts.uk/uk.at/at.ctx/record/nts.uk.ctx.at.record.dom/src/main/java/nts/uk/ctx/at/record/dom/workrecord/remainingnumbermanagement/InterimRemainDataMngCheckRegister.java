package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RemainErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RemainInputParam;

public interface InterimRemainDataMngCheckRegister {
	/**
	 * 登録時の残数チェック
	 * @param inputParam
	 * @return
	 */
	EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam);
	
	InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput);
	
	/**
	 * 残数エーラの取得する
	 */
	public RemainErrors getError(RemainInputParam inputParam, InterimEachData remainData, Optional<RemainNumberClassification> remainNumberClassification) ;
	
}
