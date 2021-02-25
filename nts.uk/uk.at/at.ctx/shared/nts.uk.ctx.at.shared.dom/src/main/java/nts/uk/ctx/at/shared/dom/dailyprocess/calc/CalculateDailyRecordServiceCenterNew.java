package nts.uk.ctx.at.shared.dom.dailyprocess.calc;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
/**
 * Interface này để chuyển method này từ record sang shared
 * @author phongtq
 *
 */
public interface CalculateDailyRecordServiceCenterNew {
	//勤務予定情報を計算する - Chuyển từ record sang shared
			//計算(会社共通のマスタを渡せる場合)
			public List<IntegrationOfDaily> calculatePassCompanySetting(CalculateOption calcOption, List<IntegrationOfDaily> integrationOfDaily,ExecutionType reCalcAtr);
			
			//計算(スケジュールからの窓口)
			public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,List<IntegrationOfDaily> integrationOfDaily);
}
