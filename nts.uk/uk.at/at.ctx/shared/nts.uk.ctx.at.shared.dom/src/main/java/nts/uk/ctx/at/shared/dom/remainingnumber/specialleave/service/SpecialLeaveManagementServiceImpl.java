package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class SpecialLeaveManagementServiceImpl implements SpecialLeaveManagementService{
	@Inject
	private SpecialLeaveGrantRepository speLeaveRepo;
	@Inject
	private SpecialLeaveBasicInfoRepository leaveBasicInfoRepo;
	@Inject
	private SpecialHolidayRepository holidayRepo;
	@Inject
	private InterimSpecialHolidayMngExport interimSpeHolidayExport;
	@Override
	public InPeriodOfSpecialLeave complileInPeriodOfSpecialLeave(String cid, String sid, DatePeriod complileDate,
			boolean mode, GeneralDate baseDate, int specialLeaveCode, boolean mngAtr) {
		//管理データを取得する
		//TODO 1
		
		/*//社員の特別休暇情報を取得する
		List<InterimSpecialHolidayMng> lstInterimSpeHoliday = interimSpeHolidayExport.getDataByMode(cid, sid, complileDate, mode);
		//繰越上限日数まで調整する
		//TODO lam tiep khi todo 1 xong
		List<SpecialLeaveGrantRemainingData> adjustCarryoverDays = this.adjustCarryoverDays(null, lstInterimSpeHoliday, null);
		
		//使用数を管理データから引く
		adjustCarryoverDays = this.subtractUseDaysFromMngData(adjustCarryoverDays, lstInterimSpeHoliday);
		//使用数を求める
		Double useDays = this.askUseDays(lstInterimSpeHoliday);*/
		//残数情報をまとめる
		InPeriodOfSpecialLeave outputData = this.sumRemainData(new ArrayList<>(), (double) 0, baseDate); //TODO
		if(mngAtr) {
			//社員の特別休暇情報を取得する
		}
		return outputData;
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> getMngData(String cid, String sid, int specialLeaveCode,
			DatePeriod complileDate) {
		//ドメインモデル「特別休暇付与残数データ」を取得する
		List<SpecialLeaveGrantRemainingData> lstDataSpe = speLeaveRepo.getByPeriodStatus(sid, specialLeaveCode, LeaveExpirationStatus.AVAILABLE,
				complileDate.end(), complileDate.start());
		//社員の特別休暇情報を取得する
		//TODO 
		return null;
	}

	@Override
	public InforSpecialLeaveOfEmployee getInforSpecialLeaveOfEmployee(String cid, String sid, int specialLeaveCode,
			DatePeriod complileDate) {
		InforSpecialLeaveOfEmployee outputData = new InforSpecialLeaveOfEmployee(InforStatus.NOTUSE, 0, false, false);
		//ドメインモデル「特別休暇基本情報」を取得する
		Optional<SpecialLeaveBasicInfo> optBasicInfor = leaveBasicInfoRepo.getBySidLeaveCdUser(sid, specialLeaveCode, UseAtr.USE);
		if(!optBasicInfor.isPresent()) {
			return outputData;
		}
		//ドメインモデル「特別休暇」を取得する
		Optional<SpecialHoliday> optSpecialHoliday = holidayRepo.findByCidHolidayCd(cid, specialLeaveCode);
		if(!optSpecialHoliday.isPresent()) {
			return outputData;
		}
		SpecialHoliday specialHoliday = optSpecialHoliday.get();
		//対応するドメインモデル「定期付与」を取得する
		//TODO 特別休暇 can phai duoc sua
		
		return null;
	}

	@Override
	public List<GeneralDate> askGrantDayOnTable(String sid, DatePeriod dateData, SpecialLeaveBasicInfo specialInfo) {
		//取得している「特別休暇基本情報．適用設定」をチェックする
		//TODO
		
		return null;
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> adjustCarryoverDays(List<SpecialLeaveGrantRemainingData> specialLeaverData,
			List<InterimSpecialHolidayMng> specialHolidayData, Integer upLimiDays) {
		//INPUT．蓄積上限日数をチェックする
		//INPUT．特別休暇付与残数データ一覧に付与予定のデータがあるかチェックする
		if(upLimiDays == null 
				|| upLimiDays <= 0
				|| specialLeaverData.isEmpty()
				|| specialHolidayData.isEmpty()) {
			return specialLeaverData;
		}
		Double interimSpeUseDays = (double) 0;
		Double beforeGrantUseDays = (double) 0;
		Double carryForwardDays =  (double) 0;
		Double grantDays =  (double) 0;
		for (SpecialLeaveGrantRemainingData speLeaverData : specialLeaverData) {
			//指定日までの使用数を求める
			interimSpeUseDays += this.getInterimSpeUseDays(specialHolidayData, speLeaverData.getGrantDate().addDays(-1)); 
			//付与前の残数=付与済の「特別休暇付与残数データ」．「明細」．残数の合計(日数) - 使用数(output)
			beforeGrantUseDays += speLeaverData.getDetails().getRemainingNumber().getDayNumberOfRemain().v() - interimSpeUseDays;
			grantDays += speLeaverData.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		}
		//繰越超えた値 = 付与予定の「特別休暇付与残数データ」．「明細」．付与 + 付与前の残数 -INPUT．蓄積上限日数
		carryForwardDays +=  grantDays + beforeGrantUseDays < 0 ? 0 : beforeGrantUseDays - upLimiDays;
		//繰越上限を超えたかチェックする		
		if(carryForwardDays > 0) {
			Double remainDays = carryForwardDays;
			//古い日付順から、付与済の「特別休暇付与残数データ」．「明細」．残数から繰越超えた値を引く
			for (SpecialLeaveGrantRemainingData speLeaverData : specialLeaverData) {
				Double remainLeaverDays = speLeaverData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
				remainDays -= remainLeaverDays;
				if(remainDays > 0) {
					speLeaverData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain((double) 0));
				} else {
					speLeaverData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(-remainDays));
					break;
				}
			}
		}
		return specialLeaverData;
	}

	@Override
	public Double getInterimSpeUseDays(List<InterimSpecialHolidayMng> specialHolidayData, GeneralDate baseDate) {
		Double outPutData = (double) 0;
		//INPUT.ドメインモデル「特別休暇暫定データ」一覧をループする
		for (InterimSpecialHolidayMng speHolidayData : specialHolidayData) {
			//ループ中のドメインモデル「特別休暇暫定データ」．年月日とINPUT．年月日を比較する
			if(speHolidayData.getYmd().beforeOrEquals(baseDate)) {
				//使用数 += ループ中のドメインモデル「特別休暇暫定データ」．特休使用
				outPutData += speHolidayData.getUseDays().v();
			}
		}
		
		return outPutData;
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> subtractUseDaysFromMngData(
			List<SpecialLeaveGrantRemainingData> specialLeaverData, List<InterimSpecialHolidayMng> specialHolidayData) {
		if(specialLeaverData.isEmpty()
				|| specialHolidayData.isEmpty()) {
			return specialLeaverData;
		}
		List<UseDaysOfPeriodSpeHoliday> lstUseDaysOfPeriod = new ArrayList<UseDaysOfPeriodSpeHoliday>();
		//INPUT．特別休暇暫定データ一覧をループする
		for (InterimSpecialHolidayMng speHolidayData : specialHolidayData) {
			//相殺できるINPUT．特別休暇付与残数データを取得する
			//・「特別休暇付与残数データ」．付与日<= ループ中の「特別休暇暫定データ」．年月日 <= 「特別休暇付与残数データ」．期限日
			List<SpecialLeaveGrantRemainingData> tmp = specialLeaverData.stream().filter(x -> x.getGrantDate().beforeOrEquals(speHolidayData.getYmd())
					&& speHolidayData.getYmd().beforeOrEquals(x.getDeadlineDate())).collect(Collectors.toList());
			if(tmp.isEmpty()) {
				//ループ中の「特別休暇暫定データ」を「特別休暇期間外の使用」に追加する
				UseDaysOfPeriodSpeHoliday useDaysOPeriod = new UseDaysOfPeriodSpeHoliday(speHolidayData.getYmd(), speHolidayData.getUseDays().v(), speHolidayData.getUseTimes().v());
				lstUseDaysOfPeriod.add(useDaysOPeriod); //them nhung ko dung lam gi
			} else {
				Double remainDays = speHolidayData.getUseDays().v();
				//付与日の古い特別休暇付与残数データから特休使用を引く
				//・相殺できるINPUT．特別休暇付与残数データ一覧が一つのみの場合：
				//特休使用を該当管理データから引く。
				//※残数がマイナスになっても。
			
				//・相殺できるINPUT．特別休暇付与残数データ一覧が複数の場合：
				//特休使用を付与日が古い管理データから引く。残数が０になったら、次の管理データから引く。
				int i = 0;
				boolean isEnd = true;
				for (SpecialLeaveGrantRemainingData tmpLstData : tmp) {
					specialLeaverData.remove(tmpLstData);
					//付与日が古い特別休暇付与残数データに特休使用を計上する
					Double useDays = tmpLstData.getDetails().getUsedNumber().getDayNumberOfUse().v() + speHolidayData.getUseDays().v();
					tmpLstData.getDetails().getUsedNumber().setDayNumberOfUse(new DayNumberOfUse(useDays));
					
					remainDays -= tmpLstData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
					i += 1;					
					if(remainDays > 0) {
						tmpLstData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain((double) 0));
					} else if(remainDays < 0 && isEnd){
						tmpLstData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(i == tmp.size() ? remainDays : -remainDays));
						isEnd = false;
					}
					specialLeaverData.add(tmpLstData);
				}				
			}
		}
		//特別休暇付与残数データ一覧を特別休暇パラメータに追加する
		//TODO
		return specialLeaverData;
	}

	@Override
	public Double askUseDays(List<InterimSpecialHolidayMng> specialHolidayData) {
		Double outputData = (double) 0;
		if(specialHolidayData.isEmpty()) {
			return outputData;
		}
		
		//INPUT.ドメインモデル「特別休暇暫定データ」一覧をループする
		for (InterimSpecialHolidayMng speHoliData : specialHolidayData) {
			outputData += speHoliData.getUseDays().v();
		}
		return outputData;
	}

	@Override
	public InPeriodOfSpecialLeave sumRemainData(List<SpecialLeaveGrantDetails> lstSpecialLeaverData,
			Double useDays, GeneralDate baseDate) {
		//付与前明細．残数と付与数=0（初期化）
		Double beforeRemainDays = (double) 0;
		Double beforeGrantDays = (double) 0;
		//付与後明細．残数と付与数=0（初期化）
		Double afterRemainDays = (double) 0;
		Double afterGrantDays = (double) 0;
		for (SpecialLeaveGrantDetails leaveData : lstSpecialLeaverData) {
			//期限切れかチェックする
			if(leaveData.getDeadlineDate().afterOrEquals(baseDate)) {
				//ループ中のパラメータ．特別休暇の付与明細．期限切れ区分=使用可能
				leaveData.setExpirationStatus(LeaveExpirationStatus.AVAILABLE);
				//ループ中のパラメータ．特別休暇の付与明細．データ区分をチェックする
				if(leaveData.getDataAtr() == DataAtr.GRANTED) {
					//付与前明細．残数 += ループ中のパラメータ．特別休暇の付与明細．明細．残数
					beforeRemainDays += leaveData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
					//付与前明細．付与数 += ループ中のパラメータ．特別休暇の付与明細．明細．付与数
					beforeGrantDays += leaveData.getDetails().getGrantNumber().getDayNumberOfGrant().v();
				} else {
					//付与後明細．残数 += ループ中のパラメータ．特別休暇の付与明細．明細．残数
					afterRemainDays += leaveData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
					//付与後明細．付与数 += ループ中のパラメータ．特別休暇の付与明細．明細．付与数
					afterGrantDays += leaveData.getDetails().getGrantNumber().getDayNumberOfGrant().v();
					
				}
			} else {
				//ループ中のパラメータ．特別休暇の付与明細．期限切れ区分=期限切れ
				leaveData.setExpirationStatus(LeaveExpirationStatus.EXPIRED);
			}
		}
		//付与前明細．残数と付与数を特別休暇の残数に入れる
		RemainDaysOfSpecialHoliday outDataRemainDays = new RemainDaysOfSpecialHoliday(useDays, beforeRemainDays, beforeGrantDays, Optional.empty(), Optional.empty());
		if(beforeGrantDays > 0) {
			outDataRemainDays.setAfterGrantDays(Optional.of(afterGrantDays));
			outDataRemainDays.setAfterRemainDays(Optional.of(afterRemainDays));
		}
		return new InPeriodOfSpecialLeave(lstSpecialLeaverData, outDataRemainDays);
	}
	
}
