package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.shr.com.context.AppContexts;

/**
 * 特別休暇処理
 * @author shuichi_ishida
 */
public class SpecialHolidayProcess {

	/**
	 * 特別休暇処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public static AtomTask specialHolidayProcess(Require require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId, List<DailyInterimRemainMngData> interimRemainMngMap) {

		String companyId = AppContexts.user().companyId();

		List<InterimSpecialHolidayMng> interimSpecialData = getSpecialRemain(interimRemainMngMap);

		/** ドメインモデル「特別休暇」を取得する */
		val specialHolidays = require.specialHoliday(companyId);

		List<AtomTask> atomTask =  specialHolidays.stream().map(specialHoliday -> {

			/** 特別休暇残数計算 */
			InPeriodOfSpecialLeaveResultInfor output = calculateRemainSpecial(require, cacheCarrier, companyId, period,
					empId, specialHoliday.getSpecialHolidayCode().v(), interimSpecialData);

			/** 特別休暇残数更新 */
			return AtomTask.of(updateRemainSpecialHoliday(require, output, empId, period.getPeriod(),
														specialHoliday.getSpecialHolidayCode().v(), specialHoliday.getAutoGrant().value))
					/** 特別休暇暫定データ削除 */
							.then(deleteTempSpecialHolidayManagement(
									require, empId, specialHoliday.getSpecialHolidayCode().v(), period.getPeriod()));
		}).collect(Collectors.toList());

		return AtomTask.bundle(atomTask);
	}

	/**
	 * 特別休暇残数更新
	 * @param output 特別休暇の集計結果
	 * @param empId 社員ID
	 * @param period 期間
	 * @param specialLeaveCode 特別休暇コード
	 * @param autoGrant 自動付与区分
	 */
	private static AtomTask updateRemainSpecialHoliday(RequireM2 require, InPeriodOfSpecialLeaveResultInfor output,
			String empId, DatePeriod period, int specialLeaveCode, int autoGrant) {

		List<AtomTask> atomTask = new ArrayList<>();

		Map<GeneralDate, String> existDataMap = new HashMap<>();

		/** 当月以降の特別休暇付与残数データを削除 */
		atomTask.add(deleteAfterCurrentMonth(require, empId, period, specialLeaveCode, autoGrant));

		/** ドメインモデル「特別休暇付与残数データ」を取得 */
		val specialGrantRemainData = require.specialLeaveGrantRemainingData(empId, specialLeaveCode);

		specialGrantRemainData.stream().forEach(c -> {

			/** TODO: ドメインモデル「特別休暇付与残数履歴データ」を追加する */
		});

		return AtomTask.bundle(atomTask)
				/** 特別休暇付与残数データ更新処理 */
				.then(update(require, output.getAsOfStartNextDayOfPeriodEnd(), empId, specialLeaveCode, existDataMap, specialGrantRemainData));
	}

	/** 特別休暇付与残数データ更新処理 */
	private static AtomTask update(RequireM2 require, SpecialLeaveInfo speLeaInfo, String empId,
			int specialLeaveCode, Map<GeneralDate, String> existDataMap, List<SpecialLeaveGrantRemainingData> speLeaGrantRemainDatas) {

		/** パラメータ「特別休暇情報．付与残数データ」を取得する */
		val atomTasks = speLeaInfo.getGrantRemainingDataList().stream().map(detail -> {

			/** 残数がマイナスの場合の補正処理 */
			detail.getDetails().correctRemainNumbers();

			/** ドメインモデル「特別休暇付与残数データ」を取得する */
			return speLeaGrantRemainDatas.stream().filter(c -> c.getGrantDate().equals(detail.getGrantDate()))
					.findFirst()
					.map(c -> {

						/** ドメインモデル「特別休暇付与残数データ」を更新する */
						c.setExpirationStatus(detail.getExpirationStatus());
						c.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
						return AtomTask.of(() -> require.updateSpecialLeaveGrantRemainingData(c));
					})
					.orElseGet(() -> {

						/** ドメインモデル「特別休暇付与残数データ」を追加する */
						val data = SpecialLeaveGrantRemainingData.of(detail, specialLeaveCode);
						data.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
						return AtomTask.of(() -> require.addSpecialLeaveGrantRemainingData(data));
					});

		}).collect(Collectors.toList());

		return AtomTask.bundle(atomTasks);
	}

	/** 当月以降の特別休暇付与残数データを削除 */
	private static AtomTask deleteAfterCurrentMonth(RequireM3 require, String empId, DatePeriod period, int specialLeaveCode, int autoGrant) {

		/** パラメータ「自動付与区分」をチェックする */
		if (autoGrant == 0) {
			return AtomTask.none();
		}

		/** ドメインモデル「特別休暇付与残数データ」を削除 */
		return AtomTask.of(() -> require.deleteSpecialLeaveGrantRemainAfter(empId, specialLeaveCode, period.start()))
				/** TODO: ドメインモデル「特別休暇付与残数履歴データ」を削除 */
				;
	}

	private static List<InterimSpecialHolidayMng> getSpecialRemain(List<DailyInterimRemainMngData> interimRemainMngMap) {

		/** 暫定残数データを特別休暇に絞り込む */
		List<InterimSpecialHolidayMng> interimSpecialData = interimRemainMngMap.stream()
				.filter(c -> !c.getRecAbsData().isEmpty() && !c.getSpecialHolidayData().isEmpty())
				.map(c -> {

					/**特別休暇暫定データに、親ドメインの情報を更新する。　※暫定データの作成処理がまだ対応中のため、親ドメインと子ドメインが別々になっているので。 */

					return c.getSpecialHolidayData();
				}).flatMap(List::stream).collect(Collectors.toList());
		return interimSpecialData;
	}

	/**
	 * 特別休暇残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param specialLeaveCode 特別休暇コード
	 * @param interimMng 暫定残数管理データリスト
	 * @param interimSpecialData 特別休暇暫定データリスト
	 * @return 特別休暇の集計結果
	 */
	private static InPeriodOfSpecialLeaveResultInfor calculateRemainSpecial(RequireM1 require, CacheCarrier cacheCarrier, String cid,
			AggrPeriodEachActualClosure period, String empId, int specialLeaveCode, List<InterimSpecialHolidayMng> interimSpecialData) {

		/** 「期間内の特別休暇残を集計する」を実行する */
		ComplileInPeriodOfSpecialLeaveParam param = new ComplileInPeriodOfSpecialLeaveParam(
				cid, empId, period.getPeriod(), true, period.getPeriod().end(), specialLeaveCode, true, true, interimSpecialData,Optional.of(period.getPeriod()));

		return SpecialLeaveManagementService.complileInPeriodOfSpecialLeave(require, cacheCarrier, param);
	}
	
	
	/**
	 * 特別休暇暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param specialLeaveCode
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempSpecialHolidayManagement(
			RequireM4 require, String employeeId, int specialLeaveCode,  DatePeriod period){
		
		return AtomTask.of(() -> require.deleteTempSpecialSidPeriod(employeeId, specialLeaveCode, period));
	}

	public static interface RequireM1 extends SpecialLeaveManagementService.RequireM5 {

	}

	public static interface Require extends RequireM1, RequireM2, RequireM4 {

		List<SpecialHoliday> specialHoliday(String companyId);
	}

	public static interface RequireM3 {

		void deleteSpecialLeaveGrantRemainAfter(String sid, int specialCode, GeneralDate targetDate);
	}

	public static interface RequireM2 extends RequireM3 {

		List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String employeeId, int specialCode);

		void updateSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data);

		void addSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data);
	}
	
	public static interface RequireM4{
		void deleteTempSpecialSidPeriod(String sid, int specialCode, DatePeriod period);
	}
}
