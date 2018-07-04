package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.SpecialVacationCD;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.DayNumberOfGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
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
	private InterimRemainRepository interimMngRepo;
	@Inject
	private InterimRemainOffMonthProcess interimMonthProcess;
	@Inject
	private InterimSpecialHolidayMngRepository interimSpecialRepos;
	
	@Override
	public InPeriodOfSpecialLeave complileInPeriodOfSpecialLeave(String cid, String sid, DatePeriod complileDate,
			boolean mode, GeneralDate baseDate, int specialLeaveCode, boolean mngAtr) {
		//TODO 管理データを取得する
		ManagaData grantRemainData = this.getMngData(cid, sid, specialLeaveCode, complileDate);		
		//特別休暇暫定データを取得する
		List<InterimSpecialHolidayMng> lstSpecialHolidayData = this.specialHolidayData(cid, sid, complileDate, mode);
		//管理データと暫定データの相殺
		InPeriodOfSpecialLeave output = this.getOffsetDay(cid,
				sid,
				complileDate,
				baseDate,
				grantRemainData.getRemainDatas(),
				lstSpecialHolidayData,
				grantRemainData.getLimitDays().isPresent() ? grantRemainData.getLimitDays().get() : 0);		
		//残数情報をまとめる
		output = this.sumRemainData(output);
		return output;
	}

	@Override
	public ManagaData getMngData(String cid, String sid, int specialLeaveCode,
			DatePeriod complileDate) {
		//ドメインモデル「特別休暇付与残数データ」を取得する
		List<SpecialLeaveGrantRemainingData> lstDataSpeDataBase = speLeaveRepo.getByPeriodStatus(sid, specialLeaveCode, LeaveExpirationStatus.AVAILABLE,
				complileDate.end(), complileDate.start());
		//TODO 社員の特別休暇情報を取得する
		InforSpecialLeaveOfEmployee getSpecialHolidayOfEmp = this.getInforSpecialLeaveOfEmployee(cid, sid, specialLeaveCode, complileDate);
		if(getSpecialHolidayOfEmp.getStatus() != InforStatus.NOTGRANT
				&& getSpecialHolidayOfEmp.getStatus() != InforStatus.NOTUSE
				&& getSpecialHolidayOfEmp.getStatus() != InforStatus.OUTOFSERVICE) {
			//メモリ上「特別休暇付与残数データ」を作る
			List<SpecialLeaveGrantRemainingData> lstDataSpeDataMemory = new ArrayList<>();
			for (SpecialHolidayInfor memoryInfor : getSpecialHolidayOfEmp.getSpeHolidayInfor()) {
				if(memoryInfor.getGrantDaysInfor().getGrantDays() <= 0) {
					continue;
				}
				String mngId = IdentifierUtil.randomUniqueId();
				SpecialLeaveNumberInfo details = new SpecialLeaveNumberInfo();
				SpecialLeaveGrantNumber grantNumber = new SpecialLeaveGrantNumber(new DayNumberOfGrant(memoryInfor.getGrantDaysInfor().getGrantDays()), Optional.empty());
				details.setGrantNumber(grantNumber);
				SpecialLeaveGrantRemainingData grantMemoryData = new SpecialLeaveGrantRemainingData(mngId, 
						cid,
						sid, 
						new SpecialVacationCD(specialLeaveCode),
						memoryInfor.getGrantDaysInfor().getYmd(),
						memoryInfor.getDeadlineDate().isPresent() ? memoryInfor.getDeadlineDate().get() : GeneralDate.max(),
						LeaveExpirationStatus.AVAILABLE,
						GrantRemainRegisterType.MANUAL,
						details);
				lstDataSpeDataMemory.add(grantMemoryData);
			}
			lstDataSpeDataBase = this.adjustGrantData(lstDataSpeDataBase, lstDataSpeDataMemory);
		}
		//「特別休暇付与残数データ」(output)をソートする
		lstDataSpeDataBase = lstDataSpeDataBase.stream().sorted((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()))
				.collect(Collectors.toList());
		ManagaData outputData = new ManagaData(lstDataSpeDataBase, getSpecialHolidayOfEmp.getUpLimiDays());
		return outputData;
	}
	/**
	 * 付与日が同じ管理データを排除する
	 * @param lstDatabase
	 * @param lstMemoryData
	 * @return
	 */
	private List<SpecialLeaveGrantRemainingData> adjustGrantData(List<SpecialLeaveGrantRemainingData> lstDatabase, List<SpecialLeaveGrantRemainingData> lstMemoryData){
		//INPUT．「特別休暇付与残数データ」(old)をOUTPUT．「特別休暇付与残数データ」に追加する
		List<SpecialLeaveGrantRemainingData> lstOutputData = new ArrayList<>(lstDatabase);
		//INPUT．「特別休暇付与残数データ」(new)をループする
		for (SpecialLeaveGrantRemainingData memoryData : lstMemoryData) {
			//INPUT．「特別休暇付与残数データ」(old)の中に、ループ中の「特別休暇付与残数データ」(new)の付与日と一致する項目が存在するかチェックする
			List<SpecialLeaveGrantRemainingData> lstTemp = lstDatabase.stream()
					.filter(x -> x.getGrantDate().equals(memoryData.getGrantDate()))
					.collect(Collectors.toList());
			if(lstTemp.isEmpty()) {
				//ループ中の「特別休暇付与残数データ」(new)をOUTPUT．「特別休暇付与残数データ」に追加する
				lstOutputData.add(memoryData);
			}
		}
		
		return lstOutputData;
	}

	@Override
	public InforSpecialLeaveOfEmployee getInforSpecialLeaveOfEmployee(String cid, String sid, int specialLeaveCode,
			DatePeriod complileDate) {
		InforSpecialLeaveOfEmployee outputData = new InforSpecialLeaveOfEmployee(InforStatus.NOTUSE, Optional.empty(), new ArrayList<>(), false);
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
		//TODO 付与日数情報を取得する
		
		return null;
	}

	@Override
	public InPeriodOfSpecialLeave subtractUseDaysFromMngData(List<SpecialLeaveGrantRemainingData> specialLeaverData,
			List<InterimSpecialHolidayMng> specialHolidayData, OffsetDaysFromInterimDataMng offsetDays, InPeriodOfSpecialLeave inPeriodData, Map<GeneralDate, Double> limitDays) {
		if(specialLeaverData.isEmpty()
				|| specialHolidayData.isEmpty()) {
			return inPeriodData;
		}
		List<UseDaysOfPeriodSpeHoliday> lstUseDays = new ArrayList<>();
		//INPUT．特別休暇暫定データ一覧をループする
		double beforeUseDays = offsetDays.getBeforeUseDays();
		double afterUseDays = offsetDays.getAfterUseDays();
		boolean isRemainBefore = false;
		boolean isRemainAfter = false;
		double beforeUseDaysRemain = beforeUseDays;
		double afterUseDaysRemain = afterUseDays;
		List<SpecialLeaveGrantRemainingData> specialLeaverDataTmp = new ArrayList<>(specialLeaverData);
		
		for (InterimSpecialHolidayMng speHolidayData : specialHolidayData) {
			Optional<InterimRemain> optInterimMng = interimMngRepo.getById(speHolidayData.getSpecialHolidayId());
			if(!optInterimMng.isPresent()) {
				continue;
			}
			InterimRemain interimMng = optInterimMng.get();
			//相殺できるINPUT．特別休暇付与残数データを取得する
			//・「特別休暇付与残数データ」．付与日<= ループ中の「特別休暇暫定データ」．年月日 <= 「特別休暇付与残数データ」．期限日
			List<SpecialLeaveGrantRemainingData> tmpGrantRemainingData = specialLeaverDataTmp.stream().filter(x -> x.getGrantDate().beforeOrEquals(interimMng.getYmd())
					&& interimMng.getYmd().beforeOrEquals(x.getDeadlineDate())).collect(Collectors.toList());
			if(tmpGrantRemainingData.isEmpty()) {
				//ループ中の「特別休暇暫定データ」を「特別休暇期間外の使用」に追加する
				UseDaysOfPeriodSpeHoliday useDaysOPeriod = new UseDaysOfPeriodSpeHoliday(interimMng.getYmd(), speHolidayData.getUseDays().v(), speHolidayData.getUseTimes().v());
				lstUseDays.add(useDaysOPeriod);
			} else {
				int count = 0;
				for (SpecialLeaveGrantRemainingData grantData : tmpGrantRemainingData) {
					count += 1;
					specialLeaverData.remove(grantData);
					Optional<SpecialLeaveGrantRemainingData> grantDataById = speLeaveRepo.getBySpecialId(grantData.getSpecialId());
					double dayNumberOfUse = grantData.getDetails().getUsedNumber().getDayNumberOfUse().v();
					if(grantDataById.isPresent()) {
						//INPUT．使用数付与前をDBから取得した付与日の古い特別休暇付与残数データから引く
						//INPUT．使用数付与前を該当特別休暇付与残数データに特休使用に計上する
						beforeUseDaysRemain -= grantData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
						if(beforeUseDaysRemain >= 0) {
							grantData.getDetails().getUsedNumber().setDayNumberOfUse(new DayNumberOfUse(dayNumberOfUse 
									+ grantData.getDetails().getRemainingNumber().getDayNumberOfRemain().v()));
							grantData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(0.0));							
						} else if(beforeUseDaysRemain < 0 && !isRemainBefore) {	
							grantData.getDetails().getUsedNumber().setDayNumberOfUse(new DayNumberOfUse(dayNumberOfUse - beforeUseDaysRemain));
							grantData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(count == tmpGrantRemainingData.size() ? beforeUseDaysRemain : -beforeUseDaysRemain));
							isRemainBefore = true;
						}
						//
					} else {
						//INPUT．使用数付与後をメモリ上取得した付与日の古い特別休暇付与残数データから引く
						//INPUT．使用数付与後を該当特別休暇付与残数データに特休使用に計上する
						afterUseDaysRemain -= grantData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
						if(afterUseDaysRemain >= 0) {
							grantData.getDetails().getUsedNumber().setDayNumberOfUse(new DayNumberOfUse(dayNumberOfUse 
									+ grantData.getDetails().getRemainingNumber().getDayNumberOfRemain().v()));
							grantData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(0.0));
						} else if(afterUseDaysRemain < 0 && !isRemainAfter) {						
							grantData.getDetails().getUsedNumber().setDayNumberOfUse(new DayNumberOfUse(dayNumberOfUse - afterUseDaysRemain));
							grantData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(count == tmpGrantRemainingData.size() ? beforeUseDaysRemain : -beforeUseDaysRemain));
							isRemainAfter = true;
						}
					}
					
					specialLeaverData.add(grantData);
				}
			}
		}
		List<SpecialLeaveGrantDetails> lstSpecialLeaveGrantDetails = new ArrayList<>();
		//特別休暇付与残数データ一覧を特別休暇パラメータに追加する
		specialLeaverData.forEach(x -> {
			SpecialLeaveGrantDetails grantDetail = new SpecialLeaveGrantDetails();
			grantDetail.setCode(x.getSpecialLeaveCode().v());
			Optional<SpecialLeaveGrantRemainingData> grantDataById = speLeaveRepo.getBySpecialId(x.getSpecialId());
			if(grantDataById.isPresent()) {
				grantDetail.setDataAtr(DataAtr.GRANTED);
			} else {
				grantDetail.setDataAtr(DataAtr.GRANTSCHE);
			}
			
			grantDetail.setExpirationStatus(x.getExpirationStatus());
			grantDetail.setSid(x.getEmployeeId());
			grantDetail.setDeadlineDate(x.getDeadlineDate());
			grantDetail.setGrantDate(x.getGrantDate());
			SpecialLeaveNumberInfoService inforSevice = new SpecialLeaveNumberInfoService();
			
			inforSevice.setRemainDays(x.getDetails().getRemainingNumber().getDayNumberOfRemain().v());
			inforSevice.setUseDays(x.getDetails().getUsedNumber().getDayNumberOfUse().v());
			inforSevice.setGrantDays(x.getDetails().getGrantNumber().getDayNumberOfGrant().v());
			inforSevice.setRemainTimes(x.getDetails().getRemainingNumber().getTimeOfRemain().isPresent() ? Optional.of( x.getDetails().getRemainingNumber().getTimeOfRemain().get().v()) : Optional.empty());
			inforSevice.setUseTimes(x.getDetails().getUsedNumber().getTimeOfUse().isPresent() ? Optional.of(x.getDetails().getUsedNumber().getTimeOfUse().get().v()) : Optional.empty());
			if(limitDays.containsKey(x.getGrantDate())) {
				LimitTimeAndDays limitInfor = new LimitTimeAndDays(limitDays.get(x.getGrantDate()), Optional.empty());
				inforSevice.setLimitDays(Optional.of(limitInfor));
			} else {
				inforSevice.setLimitDays(Optional.empty());	
			}			
			inforSevice.setGrantTimes(x.getDetails().getGrantNumber().getTimeOfGrant().isPresent() ? Optional.of(x.getDetails().getGrantNumber().getTimeOfGrant().get().v()) : Optional.empty());
			grantDetail.setDetails(inforSevice);
			lstSpecialLeaveGrantDetails.add(grantDetail);
		});
		inPeriodData.setLstSpeLeaveGrantDetails(lstSpecialLeaveGrantDetails);
		//付与前明細．使用数=INPUT．使用数付与前、付与後明細．使用数=INPUT．使用数付与後
		inPeriodData.getRemainDays().getGrantDetailBefore().setUseDays(beforeUseDaysRemain);
		SpecialHolidayRemainInfor afterInfor = new SpecialHolidayRemainInfor(0.0, afterUseDaysRemain, 0.0);
		inPeriodData.getRemainDays().setGrantDetailAfter(Optional.of(afterInfor));
		return inPeriodData;
	}

	@Override
	public List<InterimSpecialHolidayMng> specialHolidayData(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		List<InterimSpecialHolidayMng> lstOutput = new ArrayList<>();
		//INPUT．モードをチェックする
		if(mode) {
			//暫定残数管理データを作成する
			Map<GeneralDate, DailyInterimRemainMngData> interimMngData = interimMonthProcess.monthInterimRemainData(cid, sid, dateData);
			interimMngData.forEach((x, y) -> {
				List<InterimSpecialHolidayMng> specialHolidayData = y.getSpecialHolidayData();
				if(!specialHolidayData.isEmpty()) {
					lstOutput.addAll(specialHolidayData);
				}
			});
		} else {
			List<InterimRemain> lstInterimMng = interimMngRepo.getRemainBySidPriod(sid, dateData, RemainType.SPECIAL);
			lstInterimMng.stream().forEach(a -> {
				List<InterimSpecialHolidayMng> lstSpecialData = interimSpecialRepos.findById(a.getRemainManaID());
				if(!lstSpecialData.isEmpty()) {
					lstOutput.addAll(lstSpecialData);
				}
			});
		}
		return lstOutput;
	}

	@Override
	public InPeriodOfSpecialLeave getOffsetDay(String cid, String sid, DatePeriod dateData, GeneralDate baseDate,
			List<SpecialLeaveGrantRemainingData> lstGrantData, List<InterimSpecialHolidayMng> lstInterimData, double accumulationMaxDays) {
		//使用数付与前=0, 使用数付与後=0, 未消化数=0(初期化)
		OffsetDaysFromInterimDataMng outputData = new OffsetDaysFromInterimDataMng(0, 0, 0);
		//INPUT．特別休暇付与残数データ一覧に付与予定のデータがあるかチェックする
		if(lstGrantData.isEmpty()) {
			double useDays = 0;
			//使用数を求める
			for (InterimSpecialHolidayMng interimData : lstInterimData) {
				useDays += interimData.getUseDays().v();
			}
			outputData.setBeforeUseDays(useDays);
		} else {
			//指定期間の使用数を求める
			double useDaysBefore = 0;
			for (SpecialLeaveGrantRemainingData grantData : lstGrantData) {
				DatePeriod datePeriod = new DatePeriod(dateData.start(), grantData.getGrantDate().addDays(-1));
				useDaysBefore += this.useDayFormGrant(lstInterimData, datePeriod);
			}
			//使用数付与前=使用数
			outputData.setBeforeUseDays(useDaysBefore);
			//指定期間の使用数を求める
			double useDaysBeforeAfter = 0;
			for (SpecialLeaveGrantRemainingData grantData : lstGrantData) {
				DatePeriod datePeriod = new DatePeriod(grantData.getGrantDate(), dateData.end());
				useDaysBeforeAfter += this.useDayFormGrant(lstInterimData, datePeriod);
			}
			//使用数付与後=使用数
			outputData.setAfterUseDays(useDaysBeforeAfter);
		}
		//期限切れの管理データを削除する
		DataMngOfDeleteExpired expiredData = this.unDigestedDay(lstGrantData, baseDate);
		//未消化数+=未消化数(output)
		outputData.setUndigested(outputData.getUndigested() + expiredData.getUnDigestedDay());
		//繰越上限日数まで調整する
		List<SpecialLeaveGrantRemainingData> lstDeleteDealine = lstGrantData.stream()
				.filter(x -> x.getExpirationStatus() == LeaveExpirationStatus.AVAILABLE)
				.collect(Collectors.toList());
		DataMngOfDeleteExpired adjustCarryForward = this.adjustCarryForward(lstDeleteDealine, outputData.getBeforeUseDays(), accumulationMaxDays);
		//未消化数+=未消化数
		outputData.setUndigested(outputData.getUndigested() + adjustCarryForward.getUnDigestedDay());
		
		//パラメータ．特別休暇の残数．未消化数=未消化数
		RemainDaysOfSpecialHoliday remainDaysOfSpecialHoliday = new RemainDaysOfSpecialHoliday(null, outputData.getUndigested(), Optional.empty());
		InPeriodOfSpecialLeave inPeriodData = new InPeriodOfSpecialLeave(new ArrayList<>(), remainDaysOfSpecialHoliday, new ArrayList<>());
		//使用数を管理データから引く
		inPeriodData = this.subtractUseDaysFromMngData(lstGrantData, lstInterimData, outputData, inPeriodData, adjustCarryForward.getLimitDays());
		return inPeriodData;
	}
	/**
	 * 指定期間の使用数を求める
	 * @param lstInterimData
	 * @param dateData
	 * @return
	 */
	private double useDayFormGrant(List<InterimSpecialHolidayMng> lstInterimData, DatePeriod dateData) {
		double outputData = 0;
		//ループ中のドメインモデル「特別休暇暫定データ」．年月日とINPUT．開始日、終了日を比較する
		for (InterimSpecialHolidayMng interimMng : lstInterimData) {
			Optional<InterimRemain> optInterimMng = interimMngRepo.getById(interimMng.getSpecialHolidayId());
			if(optInterimMng.isPresent()) {
				InterimRemain interimMngData = optInterimMng.get();
				if(interimMngData.getYmd().beforeOrEquals(dateData.start())
						&& interimMngData.getYmd().afterOrEquals(dateData.end())) {
					outputData += interimMng.getUseDays().v();
				}
			}
		}
		return outputData;
	}
	/**
	 * 期限切れの管理データを削除する
	 * @param lstGrantData
	 * @param baseDate
	 * @return
	 */
	private DataMngOfDeleteExpired unDigestedDay(List<SpecialLeaveGrantRemainingData> lstGrantData, GeneralDate baseDate) {
		double unDisgesteDays = 0;
		List<SpecialLeaveGrantRemainingData> lstTmp = new ArrayList<>(lstGrantData);
		for (SpecialLeaveGrantRemainingData grantData : lstGrantData) {
			if(!grantData.getDeadlineDate().afterOrEquals(baseDate)) {
				unDisgesteDays += grantData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
				lstTmp.remove(grantData);
			}
		}
		return new DataMngOfDeleteExpired(unDisgesteDays, lstTmp, null);
	}
	/**
	 * 繰越上限日数まで調整する
	 * @param lstGrantData
	 * @param beforeUseDays
	 * @param accumulationMaxDays
	 * @param lstSpeLeaveGrantDetails
	 * @return
	 */
	private DataMngOfDeleteExpired adjustCarryForward(List<SpecialLeaveGrantRemainingData> lstGrantData, double beforeUseDays, double accumulationMaxDays) {
		double unDisgesteDays = 0;
		Map<GeneralDate, Double> limitDays = new HashMap<>();
		//INPUT．蓄積上限日数をチェックする
		if(unDisgesteDays <= 0) {
			return new DataMngOfDeleteExpired(unDisgesteDays, lstGrantData, limitDays);
		}
		//INPUT．特別休暇付与残数データ一覧に付与予定のデータがあるかチェックする
		//DB上取得した「特別休暇付与残数データ」は付与済
		//メモリ上の「特別休暇付与残数データ」は付与予定		
		List<SpecialLeaveGrantRemainingData> lstGrantDataMemory = new ArrayList<>();
		List<SpecialLeaveGrantRemainingData> lstGrantDatabase = new ArrayList<>();
		for (SpecialLeaveGrantRemainingData grantData : lstGrantData) {
			Optional<SpecialLeaveGrantRemainingData> grantDataById = speLeaveRepo.getBySpecialId(grantData.getSpecialId());
			if(grantDataById.isPresent()) {
				lstGrantDatabase.add(grantData);
			} else {
				lstGrantDataMemory.add(grantData);
			}
		}
		//INPUT．特別休暇付与残数データ一覧に付与予定のデータがあるかチェックする		
		if(!lstGrantDataMemory.isEmpty()) {
			double grantBeforeDays = 0;
			double remaintDays = 0;
			for (SpecialLeaveGrantRemainingData grantDatabase : lstGrantDatabase) {				
				remaintDays += grantDatabase.getDetails().getRemainingNumber().getDayNumberOfRemain().v();				
			}
			//付与前の残数=付与済の「特別休暇付与残数データ」．「明細」．残数の合計 - INPUT．使用数付与前
			grantBeforeDays = (remaintDays - beforeUseDays) >= 0 ? (remaintDays - beforeUseDays) : 0;
			double overCarryDays = 0;
			double dayNumberOfGrant = 0;
			for (SpecialLeaveGrantRemainingData grantDataMemory : lstGrantDataMemory) {
				dayNumberOfGrant += grantDataMemory.getDetails().getGrantNumber().getDayNumberOfGrant().v();
			}
			//繰越超えた値 = 付与予定の「特別休暇付与残数データ」．「明細」．付与 + 付与前の残数 -INPUT．蓄積上限日数
			overCarryDays = dayNumberOfGrant + grantBeforeDays - accumulationMaxDays;
			//繰越上限を超えたかチェックする
			if(overCarryDays > 0) {//TODO can check ky hon voi xu ly trong vong if nay
				unDisgesteDays = overCarryDays;
				
				List<SpecialLeaveGrantRemainingData> lstGrantDatabaseTmp = new ArrayList<>(lstGrantDatabase);
				for (SpecialLeaveGrantRemainingData speLeaverData : lstGrantDatabaseTmp) {
					double remainLeaverDays = speLeaverData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
					unDisgesteDays -= remainLeaverDays;
					//特別休暇の利用情報 set 上限超過消滅日数．日数					
					limitDays.put(speLeaverData.getGrantDate(), unDisgesteDays);					
					////古い日付順から、付与済の「特別休暇付与残数データ」．「明細」．残数から繰越超えた値を引く
					if(unDisgesteDays > 0) {
						lstGrantDatabase.remove(speLeaverData);
						speLeaverData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain((double) 0));
						lstGrantDatabase.add(speLeaverData);
					} else {
						lstGrantDatabase.remove(speLeaverData);
						speLeaverData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(-unDisgesteDays));
						lstGrantDatabase.add(speLeaverData);
						break;
					}
				}
			}
		}
		List<SpecialLeaveGrantRemainingData> lstOuput = new ArrayList<>(lstGrantDatabase);
		if(!lstGrantDataMemory.isEmpty()) {
			lstOuput.addAll(lstGrantDataMemory);
		}
		lstOuput = lstOuput.stream().sorted((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()))
				.collect(Collectors.toList());
		
		return new DataMngOfDeleteExpired(unDisgesteDays, lstOuput, limitDays);
	}

	@Override
	public InPeriodOfSpecialLeave sumRemainData(InPeriodOfSpecialLeave inPeriodData) {
		//付与前明細．残数と付与数=0（初期化）
		SpecialHolidayRemainInfor grantDetailBefore = inPeriodData.getRemainDays().getGrantDetailBefore();
		grantDetailBefore.setGrantDays(0);
		grantDetailBefore.setRemainDays(0);
		//付与後明細．残数と付与数=0（初期化）
		Optional<SpecialHolidayRemainInfor> optGrantDetailAfter = inPeriodData.getRemainDays().getGrantDetailAfter();
		SpecialHolidayRemainInfor grantDetailAfter = new SpecialHolidayRemainInfor();
		if(optGrantDetailAfter.isPresent()) {
			grantDetailAfter = optGrantDetailAfter.get();
		} else {
			grantDetailAfter.setGrantDays(0);
			grantDetailAfter.setRemainDays(0);
			grantDetailAfter.setUseDays(0);
		}
		//パラメータ．特別休暇の付与明細をループする
		for (SpecialLeaveGrantDetails grantDetail : inPeriodData.getLstSpeLeaveGrantDetails()) {
			//ループ中のパラメータ．特別休暇の付与明細．データ区分をチェックする
			if(grantDetail.getDataAtr() == DataAtr.GRANTED) {
				//付与前明細．残数 += ループ中のパラメータ．特別休暇の付与明細．明細．残数
				grantDetailBefore.setRemainDays(grantDetailBefore.getRemainDays() + grantDetail.getDetails().getRemainDays());
				//付与前明細．付与数 += ループ中のパラメータ．特別休暇の付与明細．明細．付与数
				grantDetailBefore.setGrantDays(grantDetailBefore.getGrantDays() + grantDetail.getDetails().getGrantDays());
			} else {
				//付与後明細．残数 += ループ中のパラメータ．特別休暇の付与明細．明細．残数
				grantDetailAfter.setRemainDays(grantDetailAfter.getRemainDays() + grantDetail.getDetails().getRemainDays());
				//付与後明細．付与数 += ループ中のパラメータ．特別休暇の付与明細．明細．付与数
				grantDetailAfter.setGrantDays(grantDetailAfter.getGrantDays() + grantDetail.getDetails().getGrantDays());
			}
		}
		//付与前明細．残数と付与数を特別休暇の残数に入れる
		inPeriodData.getRemainDays().setGrantDetailBefore(grantDetailBefore);
		//付与後明細．付与数をチェックする
		if(grantDetailAfter.getGrantDays() > 0) {
			inPeriodData.getRemainDays().setGrantDetailAfter(Optional.of(grantDetailAfter));
		}
		return inPeriodData;
	}
}
