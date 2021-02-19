package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * コアタイム時間帯を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.フレックス勤務設定.コアタイム時間帯を取得する
 * @author kumiko_otake
 *
 */
public class GetTimezoneOfCoreTimeService {

	/**
	 * コアタイム時間帯を取得する
	 * @param require require
	 * @param workTypeCode 勤務種類コード
	 * @param workTimeCode 就業時間帯コード
	 * @return コアタイム時間帯
	 */
	public static Optional<TimeSpanForCalc> get(Require require, WorkTypeCode workTypeCode, WorkTimeCode workTimeCode) {

		// 勤務種類を取得する
		val workType = require.getWorkType(workTypeCode);
		if ( !workType.isPresent() ) {
			return Optional.empty();
		}

		// フレックス勤務設定を取得する
		val flexWrkStg = require.getFlexWorkSetting(workTimeCode);
		if ( !workType.isPresent() ) {
			return Optional.empty();
		}

		// 午前午後区分に応じたコアタイム時間帯を取得する
		val amPmAtr = workType.get().checkWorkDay().toAmPmAtr().get();
		return flexWrkStg.get().getCoreTimeByAmPmForCalc( require, amPmAtr );

	}


	public static interface Require extends WorkSetting.Require {

		/**
		 * 勤務種類を取得する
		 * @param workTypeCode 勤務種類コード
		 * @return 勤務種類
		 */
		Optional<WorkType> getWorkType( WorkTypeCode workTypeCode );

		/**
		 * フレックス勤務設定を取得する
		 * @param workTimeCode 就業時間帯コード
		 * @return フレックス勤務設定
		 */
		Optional<FlexWorkSetting> getFlexWorkSetting( WorkTimeCode workTimeCode );

	}

}
