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
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.SpecialVacationCD;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.DayNumberOfGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class SpecialLeaveManagementServiceImpl implements SpecialLeaveManagementService{
	@Inject
	private SpecialLeaveGrantRepository speLeaveRepo;
	@Inject
	private InforSpecialLeaveOfEmployeeSevice inforSpeLeaveEmpService;
	@Inject
	private InterimRemainRepository interimMngRepo;
	@Inject
	private InterimRemainOffMonthProcess interimMonthProcess;
	@Inject
	private InterimSpecialHolidayMngRepository interimSpecialRepos;
	
	@Override
	public InPeriodOfSpecialLeave complileInPeriodOfSpecialLeave(String cid, String sid, DatePeriod complileDate,
			boolean mode, GeneralDate baseDate, int specialLeaveCode, boolean mngAtr) {
		//管理データを取得する
		ManagaData grantRemainData = this.getMngData(cid, sid, specialLeaveCode, complileDate);		
		//特別休暇暫定データを取得する
		SpecialHolidayInterimMngData specialHolidayInterimDataMng = this.specialHolidayData(cid, sid, complileDate, mode);
		//管理データと暫定データの相殺
		InPeriodOfSpecialLeave output = this.getOffsetDay(cid,
				sid,
				complileDate,
				baseDate,
				grantRemainData.getRemainDatas(),
				specialHolidayInterimDataMng,
				grantRemainData.getLimitDays().isPresent() ? grantRemainData.getLimitDays().get() : 0);		
		//残数情報をまとめる
		output = this.sumRemainData(output);
		if(mngAtr) {
			//社員の特別休暇情報を取得する
			InforSpecialLeaveOfEmployee getSpecialHolidayOfEmp = inforSpeLeaveEmpService.getInforSpecialLeaveOfEmployee(cid, sid, specialLeaveCode, complileDate);
			if(getSpecialHolidayOfEmp.getStatus() != InforStatus.GRANTED) {
				List<SpecialHolidayInfor> lstInfor = getSpecialHolidayOfEmp.getSpeHolidayInfor();
				if(lstInfor.isEmpty()) {
					return output;
				}
				SpecialHolidayInfor speInfor = lstInfor.get(0);
				SpecialLeaveNumberInfoService numberInfor = new SpecialLeaveNumberInfoService(speInfor.getGrantDaysInfor().getGrantDays(),
						0,
						speInfor.getGrantDaysInfor().getGrantDays(),
						Optional.empty(),
						Optional.empty(),
						Optional.empty(),
						Optional.empty());
				SpecialLeaveGrantDetails details = new SpecialLeaveGrantDetails(specialLeaveCode, 
						DataAtr.GRANTSCHE, 
						LeaveExpirationStatus.AVAILABLE,
						speInfor.getDeadlineDate().isPresent() ?
								speInfor.getDeadlineDate().get() : GeneralDate.max(),
						sid,
						speInfor.getGrantDaysInfor().getYmd(),
						numberInfor);
				output.getLstSpeLeaveGrantDetails().add(details);
			}
			
		}
		return output;
	}

	@Override
	public ManagaData getMngData(String cid, String sid, int specialLeaveCode,
			DatePeriod complileDate) {
		//ドメインモデル「特別休暇付与残数データ」を取得する
		List<SpecialLeaveGrantRemainingData> lstDataSpeDataBase = speLeaveRepo.getByPeriodStatus(sid, specialLeaveCode, LeaveExpirationStatus.AVAILABLE,
				complileDate.end(), complileDate.start());
		//社員の特別休暇情報を取得する
		InforSpecialLeaveOfEmployee getSpecialHolidayOfEmp = inforSpeLeaveEmpService.getInforSpecialLeaveOfEmployee(cid, sid, specialLeaveCode, complileDate);
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
	public InPeriodOfSpecialLeave subtractUseDaysFromMngData(List<SpecialLeaveGrantRemainingData> specialLeaverData,
			SpecialHolidayInterimMngData interimDataMng, OffsetDaysFromInterimDataMng offsetDays, InPeriodOfSpecialLeave inPeriodData, Map<GeneralDate, Double> limitDays) {
		List<InterimSpecialHolidayMng> interimSpeHolidayData = interimDataMng.getLstSpecialInterimMng();
		List<InterimRemain> lstInterimMng = interimDataMng.getLstInterimMng();
		List<UseDaysOfPeriodSpeHoliday> lstUseDays = new ArrayList<>();
		//INPUT．特別休暇暫定データ一覧をループする
		double beforeUseDays = offsetDays.getBeforeUseDays();
		double afterUseDays = offsetDays.getAfterUseDays();
		double beforeUseDaysRemain = beforeUseDays;
		double afterUseDaysRemain = afterUseDays;
		List<SpecialLeaveGrantRemainingData> specialLeaverDataTmp = new ArrayList<>(specialLeaverData);
		List<InterimSpecialHolidayMng> tmpInterimSpeData = new ArrayList<>(interimSpeHolidayData);
		int count = 0;
		for (InterimSpecialHolidayMng speHolidayData : tmpInterimSpeData) {
			List<InterimRemain> lstInterimMngTmp = lstInterimMng.stream().filter(x -> x.getRemainManaID() == speHolidayData.getSpecialHolidayId())
					.collect(Collectors.toList());
			if(lstInterimMng.isEmpty()) {
				continue;
			}
			InterimRemain interimMng = lstInterimMngTmp.get(0);
			//相殺できるINPUT．特別休暇付与残数データを取得する
			//・「特別休暇付与残数データ」．付与日<= ループ中の「特別休暇暫定データ」．年月日 <= 「特別休暇付与残数データ」．期限日
			List<SpecialLeaveGrantRemainingData> tmpGrantRemainingData = specialLeaverDataTmp
					.stream()
					.filter(x -> x.getGrantDate().beforeOrEquals(interimMng.getYmd())
							&& interimMng.getYmd().beforeOrEquals(x.getDeadlineDate()))
					.collect(Collectors.toList());
			if(tmpGrantRemainingData.isEmpty()) {
				//ループ中の「特別休暇暫定データ」を「特別休暇期間外の使用」に追加する
				UseDaysOfPeriodSpeHoliday useDaysOPeriod = new UseDaysOfPeriodSpeHoliday(interimMng.getYmd(), 
						speHolidayData.getUseDays().isPresent() ? Optional.of(speHolidayData.getUseDays().get().v()) : Optional.empty(), 
								speHolidayData.getUseTimes() != null && speHolidayData.getUseTimes().isPresent() ? Optional.of(speHolidayData.getUseTimes().get().v()) : Optional.empty());
				lstUseDays.add(useDaysOPeriod);
			}
			if(!specialLeaverData.isEmpty()) {
				specialLeaverDataTmp = new ArrayList<>(specialLeaverData.subList(count, specialLeaverData.size() - 1));
				for (SpecialLeaveGrantRemainingData grantData : specialLeaverDataTmp) {
					count += 1;
					Optional<SpecialLeaveGrantRemainingData> grantDataById = speLeaveRepo.getBySpecialId(grantData.getSpecialId());
					if(grantDataById.isPresent()) {
						DayNumberOfRemain remainDaysInfor = grantData.getDetails().getRemainingNumber().getDayNumberOfRemain();
						double useDays = speHolidayData.getUseDays().isPresent() ? speHolidayData.getUseDays().get().v() : (double)0;
						//特別休暇暫定データ．特休使用をDBから取得した付与日の古い特別休暇付与残数データから引く			
						double remainDays = remainDaysInfor.v() - useDays;
						if(remainDays < 0) {
							break;
						}
						specialLeaverData.remove(grantData);
						interimSpeHolidayData.remove(speHolidayData);
						grantData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain(remainDays));
						//特別休暇暫定データ．特休使用を該当特別休暇付与残数データに特休使用に計上する
						double remainUsedays = speHolidayData.getUseDays().isPresent() ? speHolidayData.getUseDays().get().v() : 0;
						speHolidayData.setUseDays(Optional.of(new UseDay(remainUsedays + grantData.getDetails().getUsedNumber().getDayNumberOfUse().v())));
						specialLeaverData.add(grantData);
						interimSpeHolidayData.add(speHolidayData);
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
			
			inforSevice.setRemainDays(x.getDetails().getRemainingNumber() != null ? x.getDetails().getRemainingNumber().getDayNumberOfRemain().v() : 0);
			inforSevice.setUseDays(x.getDetails().getUsedNumber() == null ? 0 : x.getDetails().getUsedNumber().getDayNumberOfUse().v());
			inforSevice.setGrantDays(x.getDetails().getGrantNumber().getDayNumberOfGrant().v());
			inforSevice.setRemainTimes(x.getDetails().getRemainingNumber() != null && x.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
					? Optional.of( x.getDetails().getRemainingNumber().getTimeOfRemain().get().v()) : Optional.empty());
			inforSevice.setUseTimes(x.getDetails().getUsedNumber() != null && x.getDetails().getUsedNumber().getTimeOfUse().isPresent() 
					? Optional.of(x.getDetails().getUsedNumber().getTimeOfUse().get().v()) : Optional.empty());
			if(limitDays.containsKey(x.getGrantDate())) {
				LimitTimeAndDays limitInfor = new LimitTimeAndDays(limitDays.get(x.getGrantDate()), Optional.empty());
				inforSevice.setLimitDays(Optional.of(limitInfor));
			} else {
				inforSevice.setLimitDays(Optional.empty());	
			}			
			inforSevice.setGrantTimes(x.getDetails().getGrantNumber() != null && x.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
					? Optional.of(x.getDetails().getGrantNumber().getTimeOfGrant().get().v()) : Optional.empty());
			grantDetail.setDetails(inforSevice);
			lstSpecialLeaveGrantDetails.add(grantDetail);
		});
		inPeriodData.setLstSpeLeaveGrantDetails(lstSpecialLeaveGrantDetails);
		//付与前明細．使用数=INPUT．使用数付与前、付与後明細．使用数=INPUT．使用数付与後
		inPeriodData.getRemainDays().getGrantDetailBefore().setUseDays(beforeUseDaysRemain);
		SpecialHolidayRemainInfor afterInfor = new SpecialHolidayRemainInfor(0.0, afterUseDaysRemain, 0.0);
		inPeriodData.getRemainDays().setGrantDetailAfter(Optional.of(afterInfor));
		inPeriodData.setUseOutPeriod(lstUseDays);
		return inPeriodData;
	}

	@Override
	public SpecialHolidayInterimMngData specialHolidayData(String cid, String sid, DatePeriod dateData,
			boolean mode) {
		List<InterimSpecialHolidayMng> lstOutput = new ArrayList<>();
		List<InterimRemain> lstInterimMng = new ArrayList<>();
		//INPUT．モードをチェックする
		if(mode) {
			//暫定残数管理データを作成する
			Map<GeneralDate, DailyInterimRemainMngData> interimMngData = interimMonthProcess.monthInterimRemainData(cid, sid, dateData);
			List<DailyInterimRemainMngData> lstDailyInterimRemainMngData = interimMngData.values()
					.stream().collect(Collectors.toList());
			for(DailyInterimRemainMngData y : lstDailyInterimRemainMngData) {
				List<InterimSpecialHolidayMng> specialHolidayData = y.getSpecialHolidayData();
				for(InterimSpecialHolidayMng specialData : specialHolidayData) {
					lstOutput.add(specialData);
					List<InterimRemain> mngData = y.getRecAbsData().stream()
							.filter(a -> a.getRemainManaID() == specialData.getSpecialHolidayId())
							.collect(Collectors.toList());
					if(!mngData.isEmpty()) {
						lstInterimMng.add(mngData.get(0));
					}
				}
			}
		} else {
			lstInterimMng = interimMngRepo.getRemainBySidPriod(sid, dateData, RemainType.SPECIAL);
			lstInterimMng.stream().forEach(a -> {
				List<InterimSpecialHolidayMng> lstSpecialData = interimSpecialRepos.findById(a.getRemainManaID());
				if(!lstSpecialData.isEmpty()) {
					lstOutput.addAll(lstSpecialData);
				}
			});
		}
		return new SpecialHolidayInterimMngData(lstOutput, lstInterimMng);
	}

	@Override
	public InPeriodOfSpecialLeave getOffsetDay(String cid, String sid, DatePeriod dateData, GeneralDate baseDate,
			List<SpecialLeaveGrantRemainingData> lstGrantData, SpecialHolidayInterimMngData interimDataMng, double accumulationMaxDays) {
		List<InterimSpecialHolidayMng> lstInterimData = interimDataMng.getLstSpecialInterimMng();
		//使用数付与前=0, 使用数付与後=0, 未消化数=0(初期化)
		OffsetDaysFromInterimDataMng outputData = new OffsetDaysFromInterimDataMng(0, 0, 0);
		//INPUT．特別休暇付与残数データ一覧に付与予定のデータがあるかチェックする
		if(lstGrantData.isEmpty()) {
			double useDays = 0;
			//使用数を求める
			for (InterimSpecialHolidayMng interimData : lstInterimData) {
				useDays += interimData.getUseDays().isPresent() ? interimData.getUseDays().get().v() : 0;
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
		DataMngOfDeleteExpired adjustCarryForward = this.adjustCarryForward(expiredData.getLstGrantData(), outputData.getBeforeUseDays(), accumulationMaxDays);
		//未消化数+=未消化数 //パラメータ．特別休暇の残数．未消化数=未消化数
		outputData.setUndigested(outputData.getUndigested() + adjustCarryForward.getUnDigestedDay());
		SpecialHolidayRemainInfor infor = new SpecialHolidayRemainInfor(0, 0, 0);
		RemainDaysOfSpecialHoliday remainDaysOfSpecialHoliday = new RemainDaysOfSpecialHoliday(infor, outputData.getUndigested(), Optional.empty());
		InPeriodOfSpecialLeave inPeriodData = new InPeriodOfSpecialLeave(new ArrayList<>(), remainDaysOfSpecialHoliday, new ArrayList<>());
		//使用数を管理データから引く
		inPeriodData = this.subtractUseDaysFromMngData(adjustCarryForward.getLstGrantData(), interimDataMng, outputData, inPeriodData, adjustCarryForward.getLimitDays());
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
		for (InterimSpecialHolidayMng interimMng : lstInterimData) {
			Optional<InterimRemain> optInterimMng = interimMngRepo.getById(interimMng.getSpecialHolidayId());
			if(optInterimMng.isPresent()) {
				InterimRemain interimMngData = optInterimMng.get();
				//ループ中のドメインモデル「特別休暇暫定データ」．年月日とINPUT．開始日、終了日を比較する
				if(interimMngData.getYmd().beforeOrEquals(dateData.start())
						&& interimMngData.getYmd().afterOrEquals(dateData.end())) {
					//使用数 += ループ中のドメインモデル「特別休暇暫定データ」．特休使用
					outputData += interimMng.getUseDays().isPresent() ? interimMng.getUseDays().get().v() : 0;
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
				//未消化数+=「特別休暇数情報」．残数
				unDisgesteDays += grantData.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
				//ループ中の特別休暇付与残数データをINPUT．特別休暇付与残数データ一覧から削除する
				lstTmp.remove(grantData);
			}
		}
		return new DataMngOfDeleteExpired(unDisgesteDays, lstTmp, null);
	}
	/**
	 * 繰越上限日数まで調整する
	 * @param lstGrantData 特別休暇付与残数データ一覧(output)
	 *  ※アルゴリズム「期限切れの管理データを削除する」の返した結果を利用
	 * @param beforeUseDays 使用数付与前
	 * @param accumulationMaxDays: 蓄積上限日数
	 * @return
	 */
	private DataMngOfDeleteExpired adjustCarryForward(List<SpecialLeaveGrantRemainingData> lstGrantData, double beforeUseDays, double accumulationMaxDays) {
		double unDisgesteDays = 0;
		Map<GeneralDate, Double> limitDays = new HashMap<>();
		//INPUT．蓄積上限日数をチェックする
		if(accumulationMaxDays <= 0) {
			return new DataMngOfDeleteExpired(unDisgesteDays, lstGrantData, limitDays);
		}
		List<SpecialLeaveGrantRemainingData> lstGrantDataMemory = new ArrayList<>();
		List<SpecialLeaveGrantRemainingData> lstGrantDatabase = new ArrayList<>();
		for (SpecialLeaveGrantRemainingData grantData : lstGrantData) {
			Optional<SpecialLeaveGrantRemainingData> grantDataById = speLeaveRepo.getBySpecialId(grantData.getSpecialId());
			if(grantDataById.isPresent()) {
				//DB上取得した「特別休暇付与残数データ」は付与済
				lstGrantDatabase.add(grantData);
			} else {
				//メモリ上の「特別休暇付与残数データ」は付与予定	
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
					//古い日付順から、付与済の「特別休暇付与残数データ」．「明細」．残数から繰越超えた値を引く
					unDisgesteDays -= remainLeaverDays;
					//特別休暇の利用情報 set 上限超過消滅日数．日数					
					limitDays.put(speLeaverData.getGrantDate(), unDisgesteDays > 0 ? unDisgesteDays : 0);					
					if(unDisgesteDays > 0) {
						lstGrantDatabase.remove(speLeaverData);
						speLeaverData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain((double) unDisgesteDays));
						lstGrantDatabase.add(speLeaverData);
						break;
					} else {
						lstGrantDatabase.remove(speLeaverData);
						speLeaverData.getDetails().getRemainingNumber().setDayNumberOfRemain(new DayNumberOfRemain((double) 0));
						lstGrantDatabase.add(speLeaverData);
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
