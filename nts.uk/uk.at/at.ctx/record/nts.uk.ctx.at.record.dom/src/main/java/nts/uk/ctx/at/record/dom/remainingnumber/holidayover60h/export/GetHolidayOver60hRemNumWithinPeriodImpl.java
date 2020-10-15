package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.holidayover60h.HolidayOver60h;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;

@Stateless
public class GetHolidayOver60hRemNumWithinPeriodImpl implements GetHolidayOver60hRemNumWithinPeriod {

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
	public AggrResultOfHolidayOver60h algorithm(
			GetHolidayOver60hRemNumWithinPeriod.RequireM1 require, 
			CacheCarrier cacheCarrier,
			String companyId, 
			String employeeId, 
			DatePeriod aggrPeriod, 
			InterimRemainMngMode mode,
			GeneralDate criteriaDate, 
			Optional<Boolean> isOverWriteOpt, 
			Optional<List<TmpHolidayOver60hMng>> forOverWriteList,
			Optional<AggrResultOfHolidayOver60h> prevHolidayOver60h) {
		
		AggrResultOfHolidayOver60h result = this.createSampleData();
		return result;
		
	}
	
	/**
	 * Require
	 * @author masaaki_jinno
	 *
	 */
	public static class GetHolidayOver60hRemNumWithinPeriodRequireM1 implements GetHolidayOver60hRemNumWithinPeriod.RequireM1 {
		public GetHolidayOver60hRemNumWithinPeriodRequireM1(){
		}
	}

	
	/**
	 * サンプルデータ作成
	 * @return
	 */
	private AggrResultOfHolidayOver60h createSampleData(){
		
		AggrResultOfHolidayOver60h result = new AggrResultOfHolidayOver60h();
		
		// 使用回数
		result.setUsedTimes(new UsedTimes(3));
		
		//　期間終了日時点
		HolidayOver60hInfo holidayOver60hInfo_1 = createHolidayOver60hInfo_1();
		result.setAsOfPeriodEnd(holidayOver60hInfo_1);
		
		//　期間終了日の翌日時点
		HolidayOver60hInfo holidayOver60hInfo_2 = createHolidayOver60hInfo_2();
		result.setAsOfStartNextDayOfPeriodEnd(holidayOver60hInfo_2);
		
		//　消滅時点
		HolidayOver60hInfo holidayOver60hInfo_3 = createHolidayOver60hInfo_3();
		ArrayList<HolidayOver60hInfo> list = new ArrayList<HolidayOver60hInfo>();
		list.add(holidayOver60hInfo_3);
		result.setLapsed(holidayOver60hInfo_3);
		
		return result;
	}
	
	private HolidayOver60hInfo createHolidayOver60hInfo_1(){
		
		// ６０H超休情報 
		HolidayOver60hInfo holidayOver60hInfo = new HolidayOver60hInfo();
		
		// 年月日
		holidayOver60hInfo.setYmd(GeneralDate.ymd(2020, 8, 31));
		
		// 残数
		HolidayOver60hRemainingNumber ｈolidayOver60hRemainingNumber
			 = new HolidayOver60hRemainingNumber();
		
		// 60H超休 マイナスあり
		{
			HolidayOver60h holidayOver60h = new HolidayOver60h();
			holidayOver60h.setUsedTime(new AnnualLeaveUsedTime(180));
			holidayOver60h.setRemainingTime(new AnnualLeaveRemainingTime(120));
			
			ｈolidayOver60hRemainingNumber.setHolidayOver60hWithMinus(holidayOver60h);
		}
		// 60H超休 マイナスなし
		{
			HolidayOver60h holidayOver60h = new HolidayOver60h();
			holidayOver60h.setUsedTime(new AnnualLeaveUsedTime(180));
			holidayOver60h.setRemainingTime(new AnnualLeaveRemainingTime(120));
			
			ｈolidayOver60hRemainingNumber.setHolidayOver60hNoMinus(holidayOver60h);
		}
		
		//　繰り越し時間
		AnnualLeaveRemainingTime carryForwardTimes = new AnnualLeaveRemainingTime(300);
		ｈolidayOver60hRemainingNumber.setCarryForwardTimes(carryForwardTimes);
		
		/** 未消化数 */
		AnnualLeaveRemainingTime holidayOver60hUndigestNumber
			= new AnnualLeaveRemainingTime(60);
		ｈolidayOver60hRemainingNumber.setHolidayOver60hUndigestNumber(holidayOver60hUndigestNumber);
		
		// 残数をセット
		holidayOver60hInfo.setRemainingNumber(ｈolidayOver60hRemainingNumber);
		
		/** 付与残数データ */
		ArrayList<HolidayOver60hGrantRemaining> grantRemainingList
			= new ArrayList<HolidayOver60hGrantRemaining>();
		
		{
			// 1件目
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 5, 15));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 8, 21));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
			
				// 明細
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(300)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(240)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(60)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 7, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 9, 30));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
		
				// 明細２件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(360)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(60)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(300)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 8, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 8, 31));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
	
				// 明細３件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(240)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(0)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(240)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 1, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 2, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
	
				// 明細３件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(240)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(0)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(240)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 9, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 9, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
	
				// 明細３件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(240)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(0)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(240)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			holidayOver60hInfo.setGrantRemainingList(grantRemainingList);
		}
		
		return holidayOver60hInfo;
	}
	
	private HolidayOver60hInfo createHolidayOver60hInfo_2(){
		
		// ６０H超休情報 
		HolidayOver60hInfo holidayOver60hInfo = new HolidayOver60hInfo();
		
		// 年月日
		holidayOver60hInfo.setYmd(GeneralDate.ymd(2020, 9, 1));
		
		// 残数
		HolidayOver60hRemainingNumber ｈolidayOver60hRemainingNumber
			 = new HolidayOver60hRemainingNumber();
		
		// 60H超休 マイナスあり
		{
			HolidayOver60h holidayOver60h = new HolidayOver60h();
			holidayOver60h.setUsedTime(new AnnualLeaveUsedTime(180));
			holidayOver60h.setRemainingTime(new AnnualLeaveRemainingTime(600));
			
			ｈolidayOver60hRemainingNumber.setHolidayOver60hWithMinus(holidayOver60h);
		}
		// 60H超休 マイナスなし
		{
			HolidayOver60h holidayOver60h = new HolidayOver60h();
			holidayOver60h.setUsedTime(new AnnualLeaveUsedTime(180));
			holidayOver60h.setRemainingTime(new AnnualLeaveRemainingTime(600));
			
			ｈolidayOver60hRemainingNumber.setHolidayOver60hNoMinus(holidayOver60h);
		}
		
		//　繰り越し時間
		AnnualLeaveRemainingTime carryForwardTimes = new AnnualLeaveRemainingTime(300);
		ｈolidayOver60hRemainingNumber.setCarryForwardTimes(carryForwardTimes);
		
		/** 未消化数 */
		AnnualLeaveRemainingTime holidayOver60hUndigestNumber
			= new AnnualLeaveRemainingTime(60);
		ｈolidayOver60hRemainingNumber.setHolidayOver60hUndigestNumber(holidayOver60hUndigestNumber);
		
		// 残数をセット
		holidayOver60hInfo.setRemainingNumber(ｈolidayOver60hRemainingNumber);
		
		/** 付与残数データ */
		ArrayList<HolidayOver60hGrantRemaining> grantRemainingList
			= new ArrayList<HolidayOver60hGrantRemaining>();
		
		{
			// 1件目
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 5, 15));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 8, 15));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
			
				// 明細
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(300)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(240)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(60)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			// 2件目
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 7, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 10, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
		
				// 明細２件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(360)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(60)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(180)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			// 3件目
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 8, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 11, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
	
				// 明細３件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(240)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(0)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(240)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			// 4件目
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 9, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 12, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
	
				// 明細３件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(300)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(0)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(300)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			holidayOver60hInfo.setGrantRemainingList(grantRemainingList);
		}
		
		return holidayOver60hInfo;
	}

	private HolidayOver60hInfo createHolidayOver60hInfo_3(){
		
		// ６０H超休情報 
		HolidayOver60hInfo holidayOver60hInfo = new HolidayOver60hInfo();
		
		// 年月日
		holidayOver60hInfo.setYmd(GeneralDate.ymd(2020, 8, 15));
		
		// 残数
		HolidayOver60hRemainingNumber ｈolidayOver60hRemainingNumber
			 = new HolidayOver60hRemainingNumber();
		
		// 60H超休 マイナスあり
		{
			HolidayOver60h holidayOver60h = new HolidayOver60h();
			holidayOver60h.setUsedTime(new AnnualLeaveUsedTime(60));
			holidayOver60h.setRemainingTime(new AnnualLeaveRemainingTime(480));
			
			ｈolidayOver60hRemainingNumber.setHolidayOver60hWithMinus(holidayOver60h);
		}
		// 60H超休 マイナスなし
		{
			HolidayOver60h holidayOver60h = new HolidayOver60h();
			holidayOver60h.setUsedTime(new AnnualLeaveUsedTime(60));
			holidayOver60h.setRemainingTime(new AnnualLeaveRemainingTime(480));
			
			ｈolidayOver60hRemainingNumber.setHolidayOver60hNoMinus(holidayOver60h);
		}
		
		//　繰り越し時間
		AnnualLeaveRemainingTime carryForwardTimes = new AnnualLeaveRemainingTime(300);
		ｈolidayOver60hRemainingNumber.setCarryForwardTimes(carryForwardTimes);
		
		/** 未消化数 */
		AnnualLeaveRemainingTime holidayOver60hUndigestNumber
			= new AnnualLeaveRemainingTime(0);
		ｈolidayOver60hRemainingNumber.setHolidayOver60hUndigestNumber(holidayOver60hUndigestNumber);
		
		// 残数をセット
		holidayOver60hInfo.setRemainingNumber(ｈolidayOver60hRemainingNumber);
		
		/** 付与残数データ */
		ArrayList<HolidayOver60hGrantRemaining> grantRemainingList
			= new ArrayList<HolidayOver60hGrantRemaining>();
		
		{
			// 1件目
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 5, 15));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 8, 15));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
			
				// 明細
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(300)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(240)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(60)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 7, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 10, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
		
				// 明細２件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(360)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(180)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(180)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			{
				HolidayOver60hGrantRemaining holidayOver60hGrantRemaining
					= new HolidayOver60hGrantRemaining();
				holidayOver60hGrantRemaining.setEmployeeId("ca294040-910f-4a42-8d90-2bd02772697c");
				holidayOver60hGrantRemaining.setGrantDate(GeneralDate.ymd(2020, 8, 1));
				holidayOver60hGrantRemaining.setDeadline(GeneralDate.ymd(2020, 11, 1));
				holidayOver60hGrantRemaining.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
				holidayOver60hGrantRemaining.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
	
				// 明細３件目
				LeaveNumberInfo leaveNumberInfo = new LeaveNumberInfo();
				//　明細付与数時間
				leaveNumberInfo.getGrantNumber().setMinutes(Optional.of(new LeaveGrantTime(240)));
				//　明細使用時間
				leaveNumberInfo.getUsedNumber().setMinutes(Optional.of(new LeaveUsedTime(0)));
				//　明細残数時間
				leaveNumberInfo.getRemainingNumber().setMinutes(Optional.of(new LeaveRemainingTime(240)));
				// 追加
				holidayOver60hGrantRemaining.setDetails(leaveNumberInfo);
				// 追加
				grantRemainingList.add(holidayOver60hGrantRemaining);
			}
			holidayOver60hInfo.setGrantRemainingList(grantRemainingList);
		}
		
		return holidayOver60hInfo;
	}
}


	