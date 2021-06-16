package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayCarryForwardInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayCarryForwardInformationOutput;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayDigestionInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayErrors;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHplidayManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;

/**
 * 公休集計期間WORK
 * @author hayata_maekawa
 *
 */
@Getter
@Setter
public class AggregatePublicHolidayWork {

	/**
	 * 年月
	 */
	private YearMonth yearMonth; 
	
	/**
	 * 期間
	 */
	private DatePeriod period;
	
	/**
	 * 公休日数
	 */
	private PublicHolidayMonthSetting publicHolidayMonthSetting;
	/**
	 * 期限日
	 */
	private GeneralDate ymd;
	
	/**
	 * 翌月繰越数
	 */
	private LeaveRemainingDayNumber numberCarriedForward;
	
	public AggregatePublicHolidayWork(YearMonth yearMonth,
			DatePeriod period,
			PublicHolidayMonthSetting publicHolidayMonthSetting,
			GeneralDate ymd, 
			LeaveRemainingDayNumber numberCarriedForward){
		
		this.yearMonth = yearMonth;
		this.period = period;
		this.publicHolidayMonthSetting = publicHolidayMonthSetting;
		this.ymd = ymd;
		this.numberCarriedForward = numberCarriedForward;
	}
	
	/**
	 * 公休消化情報を作成する
	 * @param publicHolidayCarryForwardData
	 * @param tempPublicHplidayManagement
	 * @return
	 */
	public Pair<PublicHolidayDigestionInformation, Optional<PublicHolidayErrors>> createDigestionInformation (
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData,
			List<TempPublicHplidayManagement> tempPublicHplidayManagement
			){
		
		//集計開始時点の公休情報を作成する
		PublicHolidayDigestionInformation Information = createStartDigestionInformation(publicHolidayCarryForwardData);
		
		//期間中の暫定データに絞り込む
		List<TempPublicHplidayManagement> narrowDownList = tempPublicHplidayManagement.stream()
																.filter(c -> this.period.contains(c.getYmd()))
																.collect(Collectors.toList()); 
		
		//公休取得数を求める
		Information.setNumberOfAcquisitions(totalTempPublicHpliday(narrowDownList));
																
		//繰越数を求める
		this.numberCarriedForward = calculateCarriedForward(publicHolidayCarryForwardData,Information.getNumberOfAcquisitions());
		
		//エラーチェック
		Optional<PublicHolidayErrors> Errors =errorCheck();
		
		return Pair.of(Information, Errors);
	}
	
	/**
	 * 集計開始時点の公休情報を作成する
	 * @param carryForwardData
	 * @return
	 */
	private PublicHolidayDigestionInformation createStartDigestionInformation(
			List<PublicHolidayCarryForwardData> carryForwardData){
		
		return new PublicHolidayDigestionInformation(
				new LeaveGrantDayNumber(this.publicHolidayMonthSetting.getInLegalHoliday().v()),
				new LeaveRemainingDayNumber(carryForwardData.stream().mapToDouble(c -> c.numberCarriedForward.v()).sum()),
				new LeaveUsedDayNumber(0.0));
	}
	
	/**
	 * 公休取得数を求める
	 * @param tempPublicHplidayManagement　暫定公休管理データ
	 * @return LeaveUsedDayNumber　休暇使用日数
	 */
	private LeaveUsedDayNumber totalTempPublicHpliday(List<TempPublicHplidayManagement> tempPublicHplidayManagement){
		return new LeaveUsedDayNumber(tempPublicHplidayManagement
				.stream().mapToDouble(c -> c.getPublicHplidayUseNumber().v()).sum());
	}
	
	/**
	 * 翌月繰越数を求める
	 * @param publicHolidayCarryForwardData 繰越データリスト
	 * @param numberOfAcquisitions　取得数
	 * @return
	 */
	private LeaveRemainingDayNumber calculateCarriedForward(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData,
			LeaveUsedDayNumber numberOfAcquisitions			
			){
		//当月の繰越数を求める
		//翌月繰越数
		LeaveRemainingDayNumber numberCarriedForward = new LeaveRemainingDayNumber(
				this.publicHolidayMonthSetting.getInLegalHoliday().v() - numberOfAcquisitions.v());
		//取りすぎ数
		LeaveRemainingDayNumber overAcquisition = new LeaveRemainingDayNumber(
				numberOfAcquisitions.v() - this.publicHolidayMonthSetting.getInLegalHoliday().v());

		//相殺処理を行うか
		if(numberCarriedForward.v() != 0 && publicHolidayCarryForwardData.size() != 0){
			//繰越データと相殺処理
			numberCarriedForward = offsetCarriedForward(
					publicHolidayCarryForwardData,overAcquisition.v(),numberCarriedForward.v());
		}
		
		
		return numberCarriedForward;
	}
	
	/**
	 * 繰越データと相殺処理
	 * @param publicHolidayCarryForwardData
	 * @param overAcquisition
	 * @param numberNotAcquired
	 * @return 当月取れていない日数
	 */
	private LeaveRemainingDayNumber offsetCarriedForward(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData,
			double overAcquisition,
			double numberNotAcquired
			){
		
		//繰り越しされてきた取りすぎ日数
		double carryForwardOver;
		//繰越されてきた取れていない日数
		double carryForwardNotAcquired;
		
		for(PublicHolidayCarryForwardData carryForwardData:publicHolidayCarryForwardData){
			
			//公休繰越データの繰越数を変数に置き換え
			carryForwardOver =  carryForwardData.numberCarriedForward.v() * -1.0;
			carryForwardNotAcquired = carryForwardData.numberCarriedForward.v();
			
			//当月の取りすぎ日数 > 0
			if(overAcquisition > 0.0){
				//繰り越されてきた取れてない日数 > 0
				if(carryForwardNotAcquired > 0.0){
					//繰り越されてきた取れてない日数と相殺
					double x = Math.abs(carryForwardData.numberCarriedForward.v()/carryForwardNotAcquired);
					if (x > 1) {
						numberNotAcquired += carryForwardNotAcquired;
					}else{
						numberNotAcquired += carryForwardData.numberCarriedForward.v();
					}
				}
			//当月の取りすぎ日数 < 0	
			}else if(overAcquisition < 0.0){
				//繰り越されてきた取りすぎ日数 > 0
				if(carryForwardOver > 0.0 ){
					//繰り越されてきた取りすぎ日数と相殺
					double x = Math.abs(carryForwardData.numberCarriedForward.v()/carryForwardNotAcquired);
					if (x > 1) {
						numberNotAcquired -= carryForwardNotAcquired;
					}else{
						numberNotAcquired -= carryForwardData.numberCarriedForward.v();
					}
				}
				
			}else{
				break;
			}
			
		}
		
		return new LeaveRemainingDayNumber(numberNotAcquired);
	}
	
	/**
	 * 公休取得数エラーチェック
	 * @return 公休エラー
	 */
	private Optional<PublicHolidayErrors> errorCheck(){
		if(this.numberCarriedForward.v() > 0.0){
			return Optional.of(new PublicHolidayErrors(true));
		}
		return Optional.empty();
	}
	
	/**
	 * 公休繰越情報を作成する
	 * @param employeeId
	 * @param publicHolidayCarryForwardData
	 * @param publicHolidaySetting 公休
	 * @return 公休繰越情報
	 */
	public PublicHolidayCarryForwardInformationOutput calculateCarriedForwardInformation(
			String employeeId,
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData, 
			PublicHolidaySetting publicHolidaySetting){
		
		//集計期間の繰越データ作成
		Optional<PublicHolidayCarryForwardData> CarryForwardData = 
				(createPublicHolidayCarryForwardData(employeeId, publicHolidaySetting));
		if(CarryForwardData.isPresent()){
			publicHolidayCarryForwardData.add(CarryForwardData.get());
		}
		
		//集計期間終了時点での未消化を算出
		LeaveRemainingDayNumber unused = calculateUnused(publicHolidayCarryForwardData);
		
		//翌月に繰り越さない繰越データを削除する
		List<PublicHolidayCarryForwardData> deleteForwardData = deletepublicHolidayCarryForwardData(publicHolidayCarryForwardData);
		
		//翌月繰越数を公休繰越情報に設定
		LeaveRemainingDayNumber unusedNumber = new LeaveRemainingDayNumber(
				deleteForwardData.stream().mapToDouble(x -> x.numberCarriedForward.v()).sum());
		
		return new PublicHolidayCarryForwardInformationOutput(
				new PublicHolidayCarryForwardInformation(unusedNumber,unused),deleteForwardData) ;
	}
	
	/**
	 * 集計期間の繰越データ作成
	 * @param employeeId　社員ID
	 * @param publicHolidaySetting　公休設定
	 * @return　PublicHolidayCarryForwardData　公休繰越データ
	 */
	private Optional<PublicHolidayCarryForwardData> createPublicHolidayCarryForwardData(
			String employeeId, PublicHolidaySetting publicHolidaySetting){
		//公休設定．公休がマイナス時に繰越する = true　and 翌月繰越数 < 0
		if(publicHolidaySetting.iscarryOverNumberOfPublicHoliday() && this.numberCarriedForward.v() < 0.0){
			return Optional.empty(); 
		}
		//翌月繰越数 != 0
		if(!(this.numberCarriedForward.v() != 0.0)){
			return Optional.empty();
		}
		//処理期間の繰越データを作成
		return Optional.of(new PublicHolidayCarryForwardData(
				employeeId,
				this.yearMonth,
				this.ymd,
				this.numberCarriedForward,
				GrantRemainRegisterType.MONTH_CLOSE));
	}
	
	/**
	 * 集計期間終了時点での未消化を算出
	 * @param publicHolidayCarryForwardData　公休繰越データ
	 * @return LeaveRemainingDayNumber 未消化数
	 */
	private LeaveRemainingDayNumber calculateUnused(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData){
		
		//未消化数=0
		double unused = 0.0;
		
		//繰越データリストの件数でループ
		for(PublicHolidayCarryForwardData carryForwardData : publicHolidayCarryForwardData){
			//繰越データ．期限日 <= 期間．終了日
			if(carryForwardData.ymd.beforeOrEquals(this.period.end())){
				if(carryForwardData.numberCarriedForward.v() > 0){
					unused += carryForwardData.numberCarriedForward.v();
				}
				
			}
		}
		return new LeaveRemainingDayNumber(unused);
	}
	
	/**
	 * 繰越データを削除する
	 * @param publicHolidayCarryForwardData 公休繰越データ
	 * @return
	 */
	private List<PublicHolidayCarryForwardData> deletepublicHolidayCarryForwardData(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData){
		
		 publicHolidayCarryForwardData.removeIf(x -> x.numberCarriedForward.v() == 0.0 || 
				x.ymd.beforeOrEquals(this.period.end()));
				
		 return publicHolidayCarryForwardData;
	}
}
