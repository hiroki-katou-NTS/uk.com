package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday;


import java.util.List;
import java.util.Optional;


import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.GetRemainingNumberPublicHolidayService.RequireM1;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayCarryForwardInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayCarryForwardInformationOutput;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayDigestionInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayErrors;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
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
	private  Finally<LeaveRemainingDayNumber> numberCarriedForward;
	
	public AggregatePublicHolidayWork(YearMonth yearMonth,
			DatePeriod period,
			PublicHolidayMonthSetting publicHolidayMonthSetting,
			GeneralDate ymd, 
			Finally<LeaveRemainingDayNumber> numberCarriedForward){
		
		this.yearMonth = yearMonth;
		this.period = period;
		this.publicHolidayMonthSetting = publicHolidayMonthSetting;
		this.ymd = ymd;
		this.numberCarriedForward = numberCarriedForward;
	}
	
	/**
	 * 公休消化情報を作成する
	 * @param publicHolidayCarryForwardData List<公休繰越データ>
	 * @param tempPublicHolidayManagement List<暫定公休管理データ>
	 * @return PublicHolidayDigestionInformation 公休消化情報
	 */
	public PublicHolidayDigestionInformation createDigestionInformation (
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData,
			List<TempPublicHolidayManagement> tempPublicHolidayManagement
			){
			
		//公休消化情報を返す
		return new PublicHolidayDigestionInformation(
				new LeaveGrantDayNumber(this.publicHolidayMonthSetting.getInLegalHoliday().v()),
				new LeaveRemainingDayNumber(publicHolidayCarryForwardData.stream()
						.mapToDouble(c -> c.numberCarriedForward.v())
						.sum()),
				new LeaveUsedDayNumber(tempPublicHolidayManagement.stream()
						.filter(c -> this.period.contains(c.getYmd()))
						.mapToDouble(c -> c.getPublicHolidayUseNumber().v())
						.sum()));
	}
		
	/**
	 * 翌月繰越数を求める
	 * @param publicHolidayCarryForwardData List<公休繰越データ>
	 * @param numberOfAcquisitions　取得数
	 */
	public void calculateCarriedForward(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData,
			LeaveUsedDayNumber numberOfAcquisitions			
			){
		//当月の繰越数を求める
		//翌月繰越数
		Finally<LeaveRemainingDayNumber> numberCarriedForward = Finally.of(new LeaveRemainingDayNumber(
				this.publicHolidayMonthSetting.getInLegalHoliday().v() - numberOfAcquisitions.v()));
		//取りすぎ数
		LeaveRemainingDayNumber overAcquisition = new LeaveRemainingDayNumber(
				numberOfAcquisitions.v() - this.publicHolidayMonthSetting.getInLegalHoliday().v());

		//相殺処理を行うか
		if(numberCarriedForward.get().v() != 0 && publicHolidayCarryForwardData.size() != 0){
			//繰越データと相殺処理
			numberCarriedForward = Finally.of(offsetCarriedForward(
					publicHolidayCarryForwardData,overAcquisition.v(),numberCarriedForward.get().v()));
		}
		
		
		this.numberCarriedForward = numberCarriedForward;
	}
	
	/**
	 * 繰越データと相殺処理
	 * @param publicHolidayCarryForwardData
	 * @param overAcquisition　当月の取りすぎ日数
	 * @param numberNotAcquired　当月の取れてない日数
	 * @return 当月取れていない日数
	 */
	private LeaveRemainingDayNumber offsetCarriedForward(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData,
			double overAcquisition,
			double numberNotAcquired
			){
		//計算用当月の取れてない日数
		double calculationNumberNotAcquired = numberNotAcquired;
		
		if(overAcquisition == 0.0){
			return new LeaveRemainingDayNumber(calculationNumberNotAcquired);
		}
		
		for(PublicHolidayCarryForwardData carryForwardData:publicHolidayCarryForwardData){
			
			//繰り越しされてきた取りすぎ日数
			double carryForwardOver =  carryForwardData.numberCarriedForward.v() * -1.0;
			//繰越されてきた取れていない日数
			double carryForwardNotAcquired = carryForwardData.numberCarriedForward.v();
	
			//当月の取りすぎ日数 > 0
			if(overAcquisition > 0.0){
				//繰り越されてきた取れてない日数 > 0
				if(carryForwardNotAcquired > 0.0){
					//繰り越されてきた取れてない日数と相殺
					calculationNumberNotAcquired += carryForwardData.offsetUnacquiredDays(carryForwardNotAcquired);
				}
			//当月の取りすぎ日数 < 0	
			}else if(overAcquisition < 0.0){
				//繰り越されてきた取りすぎ日数 > 0
				if(carryForwardOver > 0.0 ){
					//繰り越されてきた取りすぎ日数と相殺
					calculationNumberNotAcquired -= carryForwardData.offsetUnacquiredDays(carryForwardNotAcquired);
				}
			}
		}
		
		return new LeaveRemainingDayNumber(calculationNumberNotAcquired);
	}
	
	/**
	 * 公休取得数エラーチェック
	 * @return 公休エラー
	 */
	public Optional<PublicHolidayErrors> errorCheck(){
		if(this.numberCarriedForward.isPresent()){
			if(this.numberCarriedForward.get().v() > 0.0){
				return Optional.of(new PublicHolidayErrors(true));
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 公休繰越情報を作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param publicHolidayCarryForwardData　List<公休繰越データ>
	 * @param Require
	 * @return PublicHolidayCarryForwardInformationOutput 公休繰越情報OUTPUT
	 */
	public PublicHolidayCarryForwardInformationOutput calculateCarriedForwardInformation(
			String companyId,
			String employeeId,
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData, 
			 RequireM1 require){
		
		//集計期間の繰越データ作成
		Optional<PublicHolidayCarryForwardData> CarryForwardData = 
				createPublicHolidayCarryForwardData(companyId, employeeId, require);
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
	 * @param companyId 会社ID
	 * @param employeeId　社員ID
	 * @param require
	 * @return　PublicHolidayCarryForwardData　公休繰越データ
	 */
	private Optional<PublicHolidayCarryForwardData> createPublicHolidayCarryForwardData(
			String companyId, String employeeId, RequireM1 require){
		
		
		PublicHolidaySetting publicHolidaySetting = require.publicHolidaySetting(companyId);
		
		if(!this.numberCarriedForward.isPresent()){
			return Optional.empty(); 
		}
		
		//公休設定．公休がマイナス時に繰越する = true　and 翌月繰越数 < 0
		if(publicHolidaySetting.iscarryOverNumberOfPublicHoliday() && this.numberCarriedForward.get().v() < 0.0){
			return Optional.empty(); 
		}
		//翌月繰越数 != 0
		if(!(this.numberCarriedForward.get().v() != 0.0)){
			return Optional.empty();
		}
		//処理期間の繰越データを作成
		return Optional.of(new PublicHolidayCarryForwardData(
				employeeId,
				this.yearMonth,
				this.ymd,
				this.numberCarriedForward.get(),
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
		
		//List<公休繰越データ>を絞り込み
		//絞り込みしたList<公休繰越データ>の合計を未消化数に入れる
		unused = publicHolidayCarryForwardData.stream()
		.filter(x->x.ymd.beforeOrEquals(this.period.end()) && x.numberCarriedForward.v() > 0)
		.mapToDouble(x->x.numberCarriedForward.v())
		.sum();
		
		//未消化数を返す
		return new LeaveRemainingDayNumber(unused);
	}
	
	/**
	 * 繰越データを削除する
	 * @param publicHolidayCarryForwardData 公休繰越データ
	 * @return
	 */
	private List<PublicHolidayCarryForwardData> deletepublicHolidayCarryForwardData(
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData){
		
		 publicHolidayCarryForwardData.removeIf(x -> x.deleteDecision(this.period.end()));
				
		 return publicHolidayCarryForwardData;
	}
}
