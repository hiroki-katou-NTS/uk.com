package nts.uk.ctx.at.record.pubimp.remainnumber.holidayover60h;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.GetHolidayOver60hRemNumWithinPeriodPub;
import nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export.AggrResultOfHolidayOver60hExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;

public class GetHolidayOver60hRemNumWithinPeriodPubImpl implements GetHolidayOver60hRemNumWithinPeriodPub{

	/**
	 * 期間中の60H超休残数を取得
	 * @param require Require
	 * @param cacheCarrier CacheCarrier
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode 実績のみ参照区分
	 * @param criteriaDate 基準日
	 * @param isOverWriteOpt 上書きフラグ
	 * @param forOverWriteListOpt 上書き用の暫定60H超休管理データ
	 * @param prevAnnualLeaveOpt 前回の60H超休の集計結果
	 * @return 60H超休の集計結果
	 */
	public AggrResultOfHolidayOver60hExport algorithm(
			GetHolidayOver60hRemNumWithinPeriod.RequireM1 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			DatePeriod aggrPeriod,
			InterimRemainMngMode mode,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<List<TmpHolidayOver60hMng>> forOverWriteList,
			Optional<AggrResultOfHolidayOver60hExport> prevHolidayOver60hExport) {

		// Exportクラスをドメインへ変換
		Optional<AggrResultOfHolidayOver60h> prevHolidayOver60h
			= prevHolidayOver60hExport.map(c->AggrResultOfHolidayOver60hExport.toDomain((c)));

		//comment 処理
		GetHolidayOver60hRemNumWithinPeriod getHolidayOver60hRemNumWithinPeriod
			= new GetHolidayOver60hRemNumWithinPeriodImpl();

		AggrResultOfHolidayOver60h holidayOver60h
			= getHolidayOver60hRemNumWithinPeriod.algorithm(
				require,
				cacheCarrier,
				companyId,
				employeeId,
				aggrPeriod,
				mode,
				criteriaDate,
				isOverWrite,
				forOverWriteList,
				prevHolidayOver60h);

		//comment ドメインをExportクラスへ変換
		AggrResultOfHolidayOver60hExport holidayOver60hExport
			= AggrResultOfHolidayOver60hExport.of((holidayOver60h));

		return holidayOver60hExport;
	}

}


