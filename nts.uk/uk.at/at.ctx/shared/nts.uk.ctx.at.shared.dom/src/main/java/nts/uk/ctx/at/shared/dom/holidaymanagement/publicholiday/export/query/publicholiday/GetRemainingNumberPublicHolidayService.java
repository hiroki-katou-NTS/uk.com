package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.AggrResultOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayCarryForwardInformationOutput;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayDigestionInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayErrors;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * 
 * @author hayata_maekawa
 *	期間内の公休残数を集計する
 *
 */
public class GetRemainingNumberPublicHolidayService {

	/**
	 * 期間内の公休残数を集計する
	 * @param companyId　会社ID
	 * @param employeeId 社員ID
	 * @param YearMonth 年月(List)
	 * @param criteriaDate 基準日
	 * @param performReferenceAtr 実績のみ参照区分(月次モード orその他)
	 * @param isOverWrite 上書きフラグ(Optional)
	 * @param TempPublicHolidayManagement List<上書き用暫定管理データ>(Optional)
	 * @param createAtr 作成元区分(Optional)
	 * @param periodOverWrite 上書き対象期間(Optional)
	 * @param Require
	 */
	
	public AggrResultOfPublicHoliday getPublicHolidayRemNumWithinPeriod(
			String companyId,
			String employeeId,
			List<YearMonth> yearMonth,
			GeneralDate criteriaDate,
			InterimRemainMngMode performReferenceAtr,
			Optional<Boolean> isOverWrite,
			List<TempPublicHolidayManagement> tempPublicHolidayforOverWriteList,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			CacheCarrier cacheCarrier,
			RequireM1 require
			){
		
		//公休設定を取得する
		PublicHolidaySetting publicHolidaySetting = require.publicHolidaySetting(companyId);
		
		//公休設定を確認する
		if(publicHolidaySetting.getIsManagePublicHoliday() != 1){
			return new AggrResultOfPublicHoliday();
		}
		//集計期間WORKを作成
		List<AggregatePublicHolidayWork> aggregatePublicHolidayWork = publicHolidaySetting.createAggregatePeriodWork(
				employeeId, yearMonth,criteriaDate, cacheCarrier, require);
		//繰越データを取得する
		List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData = require.publicHolidayCarryForwardData(employeeId);
		
		//暫定公休管理データを取得する
		List<TempPublicHolidayManagement> tempPublicHolidayManagement = getTempPublicHolidayManagement(
				employeeId, 
				new DatePeriod(aggregatePublicHolidayWork.get(0).getPeriod().start(),
						aggregatePublicHolidayWork.get(aggregatePublicHolidayWork.size()-1).getPeriod().end()),
				isOverWrite,
				tempPublicHolidayforOverWriteList,
				performReferenceAtr,
				createAtr,
				periodOverWrite,
				require);
		
		List<PublicHolidayInformation> publicHolidayInformation = new ArrayList<>();
		
		//公休集計期間WORKでループ
		for(AggregatePublicHolidayWork publicHolidayWork : aggregatePublicHolidayWork){
		
			//公休消化情報を作成する
			PublicHolidayDigestionInformation afterDigestion = 
					publicHolidayWork.createDigestionInformation(
							publicHolidayCarryForwardData, tempPublicHolidayManagement);
			
			//翌月繰越数を求める
			publicHolidayWork.calculateCarriedForward(publicHolidayCarryForwardData,afterDigestion.getNumberOfAcquisitions());
			
			//エラーチェック
			Optional<PublicHolidayErrors> Errors = publicHolidayWork.errorCheck();
			
			//繰越データを作成
			PublicHolidayCarryForwardInformationOutput CarryForwardInformation=
					publicHolidayWork.calculateCarriedForwardInformation(
							companyId,
							employeeId,
							publicHolidayCarryForwardData,
							require);

			
			publicHolidayInformation.add(new PublicHolidayInformation(publicHolidayWork.getYearMonth(),
																		afterDigestion,
																		CarryForwardInformation.getPublicHolidayCarryForwardInformation(),
																		Errors));
			
			publicHolidayCarryForwardData = CarryForwardInformation.getPublicHolidayCarryForwardData();
		}
		return new AggrResultOfPublicHoliday(publicHolidayInformation, publicHolidayCarryForwardData);
		
	}
	
	/**
	 * 暫定公休管理データを取得する
	 * @param employeeId
	 * @param period
	 * @param isOverWrite
	 * @param tempPublicHolidayforOverWriteList
	 * @param performReferenceAtr
	 * @param createAtr
	 * @param periodOverWrite
	 * @param require
	 * @return TempPublicHolidayManagement
	 */
	public List<TempPublicHolidayManagement> getTempPublicHolidayManagement(String employeeId,DatePeriod period,
			Optional<Boolean> isOverWrite,
			List<TempPublicHolidayManagement> tempPublicHolidayforOverWriteList,
			InterimRemainMngMode performReferenceAtr,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			RequireM6 require){
		List<TempPublicHolidayManagement> interimDate = new ArrayList<>();
		
		// 実績のみ参照区分を確認
		if (performReferenceAtr == InterimRemainMngMode.OTHER) {
			// 暫定公休管理データを取得
			interimDate = require.tempPublicHolidayManagement(employeeId , period , RemainType.PUBLICHOLIDAY);
		}
		
		// 上書きフラグを確認
		if (isOverWrite.orElse(false)){
			// ※残数共通処理にする必要あり：一時対応
			// 上書き用暫定残数データで置き換える
			//	ドメインモデル「暫定公休管理データ」．作成元区分 = パラメータ「作成元区分」
			//	パラメータ「上書き対象期間．開始日」 <= ドメインモデル「暫定公休管理データ」．年月日 <= パラメータ「上書き対象期間．終了日」
			List<TempPublicHolidayManagement> noOverwriteRemains =
					interimDate
					.stream()
					.filter(c -> !periodOverWrite.get().contains(c.getYmd()))
					.collect(Collectors.toList()); //上書き用の暫定管理データから上書対象でない暫定データを退避
			noOverwriteRemains.addAll(tempPublicHolidayforOverWriteList);
			return noOverwriteRemains;
		}
		
		return interimDate;
	}
	
	
	public static interface RequireM1 extends RequireM5, RequireM6,RequireM7, RequireM8{
		//公休設定を取得する（会社ID）
		PublicHolidaySetting publicHolidaySetting(String companyId);
	}
	
	public static interface RequireM2 {
		//締めを取得する（会社ID、締めID）
		Optional<Closure> closure(String companyId, int closureId);
	}
	
	public static interface RequireM3 extends RequireM2 {
		//雇用に紐づく就業締めを取得する（会社ID、社員ID）
		Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD);
	}

	public static interface RequireM4 extends RequireM3 {
		//所属雇用履歴を取得する（会社ID、社員ID、基準日）
		Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate);
	}
	
	public static interface RequireM5{
		//公休繰越データを取得する（社員ID）
		List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData(String employeeId);
	}
	
	public static interface RequireM6{
		//暫定公休管理データ（社員ID、期間、残数種類）
		List<TempPublicHolidayManagement> tempPublicHolidayManagement(String employeeId, DatePeriod ymd, RemainType remainType);
	}
	
	public static interface RequireM7 extends PublicHolidaySetting.RequireM1, RequireM8{
	}
	public static interface RequireM8 extends PublicHolidaySetting.RequireM2{
	}

}
