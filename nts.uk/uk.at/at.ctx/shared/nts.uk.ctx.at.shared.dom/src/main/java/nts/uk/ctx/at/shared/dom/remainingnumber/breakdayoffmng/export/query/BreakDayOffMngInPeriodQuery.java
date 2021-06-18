package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate.GetNumberOfVacaLeavObtainBaseDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate.GetNumberOfVacaLeavObtainBaseDate.Require;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate.NumberConsecutiveVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BreakDayOffMngInPeriodQuery {

	/**
	 * 1.未相殺の代休(確定)を取得する
	 * @param sid
	 * @return
	 */
	public static List<BreakDayOffDetail> getConfirmDayOffDetail(RequireM8 require, String cid, String sid, GeneralDate startDate) {
		List<BreakDayOffDetail> lstOutputData = new ArrayList<>();
		//アルゴリズム「確定代休から未相殺の代休を取得する」を実行する
		List<CompensatoryDayOffManaData> lstDayOffConfirmData = require.compensatoryDayOffManaData(cid, sid, startDate);
		if(lstDayOffConfirmData.isEmpty()) {
			return lstOutputData;
		}
		for (CompensatoryDayOffManaData dayOffConfirmData : lstDayOffConfirmData) {
			//1-3.暫定休出と紐付けをしない確定代休を取得する
			BreakDayOffDetail dataDetail = getConfirmDayOffData(dayOffConfirmData, sid);
			if(dataDetail != null) {
				lstOutputData.add(dataDetail);
			}
		}
		return lstOutputData;
	}

	/**
	 * 1-1.確定代休から未相殺の代休を取得する
	 * @param sid
	 * @return
	 */
	//List<CompensatoryDayOffManaData> lstConfirmDayOffData(String cid, String sid, GeneralDate startDate);
	/**
	 * 1-3.暫定休出と紐付けをしない確定代休を取得する
	 * @param dayoffConfirmData
	 * @return
	 */
	public static BreakDayOffDetail getConfirmDayOffData(CompensatoryDayOffManaData dayoffConfirmData, String sid) {
		BreakDayOffDetail detail = new BreakDayOffDetail();
		//ドメインモデル「暫定休出代休紐付け管理」を取得する REPONSE 対応
		//List<InterimBreakDayOffMng> interimTyingData = breakDayOffInterimRepo.getDayOffByIdAndDataAtr(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM, dayoffConfirmData.getComDayOffID());
		//未相殺日数と未相殺時間を設定する
		double unOffsetDays = dayoffConfirmData.getRemainDays().v();
		int unOffsetTimes = dayoffConfirmData.getRemainTimes().v();
		/*for (InterimBreakDayOffMng tyingData : interimTyingData) {
			unOffsetDays -= tyingData.getUseDays().v();
			unOffsetTimes -= tyingData.getUseTimes().v();
		}*/
		//未相殺日数と未相殺時間をチェックする
		if(unOffsetDays <= 0 && unOffsetTimes <= 0) {
			return null;
		}
		//「代休の未相殺」．未相殺日数=未相殺日数,  「代休の未相殺」．未相殺時間=未相殺時間
		UnOffSetOfDayOff dayOffData = new UnOffSetOfDayOff(dayoffConfirmData.getComDayOffID(),
				dayoffConfirmData.getRequiredTimes().v(),
				dayoffConfirmData.getRequireDays().v(),
				unOffsetTimes,
				unOffsetDays);
		detail.setDataAtr(MngDataStatus.CONFIRMED);
		detail.setSid(sid);
		detail.setYmdData(dayoffConfirmData.getDayOffDate());
		detail.setOccurrentClass(OccurrenceDigClass.DIGESTION);
		detail.setUnOffsetOfDayoff(Optional.of(dayOffData));
		return detail;
	}

	/**
	 * 2.未使用の休出(確定)を取得する
	 * @param sid
	 * @return
	 */
	public static List<BreakDayOffDetail> getConfirmBreakDetail(RequireM9 require, String sid, GeneralDate startDate) {
		String cid = AppContexts.user().companyId();
		List<BreakDayOffDetail> lstData = new ArrayList<>();
		//アルゴリズム「確定休出から未使用の休出を取得する」を実行する
		List<LeaveManagementData> lstConfirmBreakData = require.leaveManagementData(cid, sid, startDate, DigestionAtr.UNUSED);
		if(lstConfirmBreakData.isEmpty()) {
			return lstData;
		}
		for (LeaveManagementData confirmData : lstConfirmBreakData) {
			//アルゴリズム「暫定代休と紐付けをしない確定休出を取得する」を実行する
			BreakDayOffDetail ouputData = getConfirmBreakData(confirmData, sid, startDate);
			if(ouputData != null) {
				lstData.add(ouputData);
			}
		}
		return lstData;
	}

	/**
	 * 2-1.確定休出から未使用の休出を取得する
	 * @param sid
	 * @return
	 */
	//List<LeaveManagementData> lstConfirmBreakData(String sid, GeneralDate startDate);
	/**
	 * 2-3.暫定代休と紐付けをしない確定休出を取得する
	 * @param breakConfirm
	 * @param sid
	 * @return
	 */
	public static BreakDayOffDetail getConfirmBreakData(LeaveManagementData breakConfirm, String sid, GeneralDate aggStartDate) {
		//ドメインモデル「暫定休出代休紐付け管理」を取得する　REPONSE 対応
		//List<InterimBreakDayOffMng> interimTyingData = breakDayOffInterimRepo.getBreakByIdAndDataAtr(DataManagementAtr.CONFIRM, DataManagementAtr.INTERIM, breakConfirm.getID());
		//未使用日数：INPUT.未使用日数
		double unUseDays = breakConfirm.getUnUsedDays().v();
		Integer unUseTimes = breakConfirm.getUnUsedTimes().v();
		/*for (InterimBreakDayOffMng breakData : interimTyingData) {
			//未使用日数と未使用時間を設定する
			unUseDays -= breakData.getUseDays().v();
			unUseTimes -= breakData.getUseTimes().v();
		}*/
		//未使用日数と未使用時間をチェックする
		if(unUseDays <= 0 && unUseTimes <= 0) {
			return null;
		}
		//「休出の未使用」．未使用日数 = 未使用日数、「休出の未使用」．未使用時間 = 未使用時間
		UnUserOfBreak breakData = new UnUserOfBreak(breakConfirm.getID(),
				breakConfirm.getFullDayTime().v(),
				breakConfirm.getExpiredDate(),
				breakConfirm.getOccurredTimes().v(),
				breakConfirm.getOccurredDays().v(),
				breakConfirm.getHalfDayTime().v(),
				unUseTimes,
				unUseDays,
				DigestionAtr.USED,
				Optional.empty(),
				Optional.of(aggStartDate));
		BreakDayOffDetail outputData = new BreakDayOffDetail(sid,
				MngDataStatus.CONFIRMED,
				breakConfirm.getComDayOffDate(),
				OccurrenceDigClass.OCCURRENCE,
				Optional.of(breakData),
				Optional.empty());
		return outputData;
	}

	/**
	 * 繰越数を計算する
	 * @param baseDate
	 * @param lstDetailData
	 * @return
	 */
	public static CarryForwardDayTimes calcCarryForwardDays(RequireM3 require, CacheCarrier cacheCarrier,
			GeneralDate baseDate, List<BreakDayOffDetail> lstDetailData, String sid, boolean isMode) {
		CarryForwardDayTimes outputData = new CarryForwardDayTimes(0, 0);
		//アルゴリズム「6.残数と未消化数を集計する」を実行
		RemainUnDigestedDayTimes dayTimes = getRemainUnDigestedDayTimes(require, cacheCarrier, baseDate, lstDetailData, sid, isMode);
		//取得した「残日数」「残時間数」を返す
		outputData.setCarryForwardDays(dayTimes.getRemainDays());
		outputData.setCarryForwardTime(dayTimes.getRemainTimes());
		return outputData;
	}

	/**
	 * 6.残数と未消化数を集計する
	 * @param baseDate
	 * @param lstDetailData
	 * @return
	 */
	public static RemainUnDigestedDayTimes getRemainUnDigestedDayTimes(RequireM3 require, CacheCarrier cacheCarrier,
			GeneralDate baseDate,List<BreakDayOffDetail> lstDetailData, String sid, boolean isMode) {
		//残日数 = 0、残時間数 = 0、未消化日数 = 0、未消化時間 = 0（初期化）
		RemainUnDigestedDayTimes outputData = new RemainUnDigestedDayTimes(0, 0, 0, 0, false);
		//アルゴリズム「代休の設定を取得する」を実行する
		String cid = AppContexts.user().companyId();
		SubstitutionHolidayOutput dayOffSetting = AbsenceTenProcess.getSettingForSubstituteHoliday(require, cacheCarrier, cid, sid, baseDate);
		double unDigestedDays = 0;
		int unDigestedTimes = 0;
		//「休出代休明細」をループする
		for (BreakDayOffDetail detailData : lstDetailData) {
			//ループ中の「休出代休明細」．発生消化区分をチェックする
			if(detailData.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE) {
				UnUserOfBreak breakData = detailData.getUnUserOfBreak().get();
				//期限切れかをチェックする
				if((isMode && breakData.getExpirationDate().beforeOrEquals(baseDate))
						|| (!isMode && breakData.getExpirationDate().before(baseDate))) {
					if(dayOffSetting == null) {
						continue;
					}
					//時間代休管理区分をチェックする
					if(dayOffSetting.isTimeOfPeriodFlg()) {
						//未消化時間 += ループ中の「休出の未使用」．未使用時間
						outputData.setUnDigestedTimes(outputData.getUnDigestedTimes() + breakData.getUnUsedTimes());
					} else {
						//未消化日数 += ループ中の「休出の未使用」．未使用日数
						outputData.setUnDigestedDays(outputData.getUnDigestedDays() + breakData.getUnUsedDays());
						//ループ中の「休出の未使用」．未使用日数をチェックする
						if(breakData.getUnUsedDays() == 1) {
							//未消化時間 += ループ中の「休出の未使用」．１日相当時間
							outputData.setUnDigestedTimes(outputData.getUnDigestedTimes() + breakData.getOnedayTime());
						} else if (detailData.getUnUserOfBreak().get().getUnUsedDays() == 0.5) {
							//未消化時間 += ループ中の「休出の未使用」．半日相当時間
							outputData.setUnDigestedTimes(outputData.getUnDigestedTimes() + breakData.getHaftDayTime());
						}
					}
				} else {
					//残日数 += ループ中の「休出の未使用」．未使用日数、残時間 += ループ中の「休出の未使用」．未使用時間
					outputData.setRemainDays(outputData.getRemainDays() + breakData.getUnUsedDays());
					outputData.setRemainTimes(outputData.getRemainTimes() + breakData.getUnUsedTimes());
				}
			} else {
				//ループ中の「代休の未相殺」．未相殺日数、「代休の集計結果」．エラー情報をチェックする
				if(detailData.getUnOffsetOfDayoff().get().getUnOffsetDay() > 0) {
					outputData.setErrors(true);
				}

				//残日数 -= ループ中の「代休の未相殺」．未相殺日数、残時間 -= ループ中の「代休の未相殺」．未相殺時間
				UnOffSetOfDayOff dayOffData = detailData.getUnOffsetOfDayoff().get();
				unDigestedDays += dayOffData.getUnOffsetDay();
				unDigestedTimes += dayOffData.getUnOffsetTimes();
			}
		}
		if(unDigestedDays != 0 || unDigestedTimes != 0) {
			outputData.setRemainDays(outputData.getRemainDays() - unDigestedDays);
			outputData.setRemainTimes(outputData.getRemainTimes() - unDigestedTimes);
		}
		return outputData;
	}

	/**
	 * 3.未相殺の代休(暫定)を取得する
	 * @param inputParam
	 * @return
	 */
	public static List<BreakDayOffDetail> lstInterimDayOffDetail(RequireM1 require, BreakDayOffRemainMngParam inputParam) {
		List<BreakDayOffDetail> lstOutputDayoff = new ArrayList<>();
		List<InterimRemain> lstInterimDayoff = new ArrayList<>();
		List<InterimDayOffMng> lstDayoffMng = new ArrayList<>();
		//INPUT．モードをチェックする
		if(inputParam.isMode()) {
			//暫定残数管理データを作成する
			Map<GeneralDate, DailyInterimRemainMngData> interimData = require.monthInterimRemainData(inputParam.getCid(),
					inputParam.getSid(), inputParam.getDateData());
			//メモリ上からドメインモデル「暫定振休管理データ」を取得する
			if(!interimData.isEmpty()) {
				List<DailyInterimRemainMngData> lstRemainMngData = interimData.values().stream().collect(Collectors.toList());
				for (DailyInterimRemainMngData x : lstRemainMngData) {
						lstInterimDayoff.addAll(x.getDayOffData());
				}
			}

		} else {
			//ドメインモデル「暫定代休管理データ」を取得する
			lstDayoffMng.addAll(require.interimDayOffMng(inputParam.getSid(), inputParam.getDateData()));
		}

		if(inputParam.isReplaceChk()
				&& !inputParam.getInterimMng().isEmpty()
				&& !inputParam.getDayOffMng().isEmpty()) {
			List<InterimDayOffMng> lstDayOffTmp = new ArrayList<>(lstDayoffMng);
			List<InterimRemain> lstInterimDataTmp = new ArrayList<>(lstInterimDayoff);
			//INPUT．上書き用の暫定管理データをドメインモデル「暫定代休管理データ」に追加する
			for (InterimDayOffMng dayOffInput : inputParam.getDayOffMng()) {
				List<InterimRemain> lstInterimInput = inputParam.getInterimMng()
						.stream()
						.filter(z -> z.getRemainManaID().equals(dayOffInput.getRemainManaID()))
						.collect(Collectors.toList());
				if(!lstInterimInput.isEmpty()) {
					InterimRemain interimData = lstInterimInput.get(0);
					List<InterimRemain> lstTmp = lstInterimDataTmp.stream().filter(a -> a.getYmd().equals(interimData.getYmd()))
					.collect(Collectors.toList());
					if(!lstTmp.isEmpty()) {
						InterimRemain tmpData = lstTmp.get(0);
						lstInterimDayoff.remove(tmpData);
						List<InterimDayOffMng> lstTmpDayoff = lstDayOffTmp.stream()
								.filter(b -> b.getRemainManaID().equals(tmpData.getRemainManaID())).collect(Collectors.toList());
						if(!lstTmpDayoff.isEmpty()) {
							InterimDayOffMng tmpDayoff = lstTmpDayoff.get(0);
							lstDayoffMng.remove(tmpDayoff);
						}
					}
					lstInterimDayoff.add(interimData);
				}
				lstDayoffMng.add(dayOffInput);
			}

		}
		//取得した件数をチェックする
		for (InterimDayOffMng interimDayOffMng : lstDayoffMng) {
			//アルゴリズム「休出と紐付けをしない代休を取得する」を実行する
			InterimRemain interimData = lstInterimDayoff.stream()
					.filter(x -> x.getRemainManaID().equals(interimDayOffMng.getRemainManaID()))
					.collect(Collectors.toList())
					.get(0);
			BreakDayOffDetail outData = getNotTypeBreak(require, interimDayOffMng, interimData);
			lstOutputDayoff.add(outData);
		}
		return lstOutputDayoff;
	}

	/**
	 * 3-1.休出と紐付けをしない代休を取得する
	 * @param detailData
	 * @param remainData
	 * @return
	 */
	public static BreakDayOffDetail getNotTypeBreak(RequireM7 require, InterimDayOffMng detailData, InterimRemain interimData) {
		//ドメインモデル「暫定休出代休紐付け管理」を取得する
		List<InterimBreakDayOffMng> lstDayOff = require.interimBreakDayOffMng(detailData.getRemainManaID(), false, DataManagementAtr.INTERIM);
		//未相殺日数と未相殺時間を設定する
		double unOffsetDays = detailData.getRequiredDay().v();
		Integer unOffsetTimes = detailData.getRequiredTime().v();
		for (InterimBreakDayOffMng interimMng : lstDayOff) {
			unOffsetDays -= interimMng.getUseDays().v();
			unOffsetTimes -= interimMng.getUseTimes().v();
		}

		//「代休の未相殺」．未相殺日数=未相殺日数,  「代休の未相殺」．未相殺時間=未相殺時間
		UnOffSetOfDayOff dayOffDataMng = new UnOffSetOfDayOff(detailData.getRemainManaID(),
				detailData.getRequiredTime().v(),
				detailData.getRequiredDay().v(),
				unOffsetTimes,
				unOffsetDays);
		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if(interimData.getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (interimData.getCreatorAtr() == CreateAtr.RECORD){
			dataAtr = MngDataStatus.RECORD;
		}
		CompensatoryDayoffDate date = new CompensatoryDayoffDate(false, Optional.of(interimData.getYmd()));
		BreakDayOffDetail dataOutput = new BreakDayOffDetail(interimData.getSID(),
				dataAtr,
				date,
				OccurrenceDigClass.DIGESTION,
				Optional.empty(),
				Optional.of(dayOffDataMng));
		return dataOutput;
	}

	/**
	 * 4.未使用の休出(暫定)を取得する
	 * @param inputParam
	 * @return
	 */
	//List<BreakDayOffDetail> lstInterimBreakDetail(BreakDayOffRemainMngParam inputParam);
	/**
	 * 4-1.代休と紐付けをしない休出を取得する
	 * @param breakMng
	 * @param remainData
	 * @return
	 */
	public static BreakDayOffDetail getNotTypeDayOff(RequireM6 require, CacheCarrier cacheCarrier,
			InterimBreakMng breakMng, InterimRemain remainData, GeneralDate aggStartDate,
			GeneralDate baseDate, SubstitutionHolidayOutput subsHolidaySetting, String cid, String sid) {
		//ドメインモデル「暫定休出代休紐付け管理」を取得する
		//ドメインモデル「暫定休出代休紐付け管理」を取得する
		List<InterimBreakDayOffMng> lstDayOff = require.interimBreakDayOffMng(remainData.getRemainManaID(),
				true, DataManagementAtr.INTERIM);
		Integer unUseTimes = breakMng.getUnUsedTimes().v();
		double unUseDays = breakMng.getUnUsedDays().v();
		for (InterimBreakDayOffMng interimData : lstDayOff) {
			unUseTimes -= interimData.getUseTimes().v();
			unUseDays -= interimData.getUseDays().v();
		}

		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if(remainData.getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (remainData.getCreatorAtr() == CreateAtr.RECORD){
			dataAtr = MngDataStatus.RECORD;
		}
		UnUserOfBreak unUseBreak = new UnUserOfBreak(breakMng.getRemainManaID(),
				breakMng.getOnedayTime().v(),
				breakMng.getExpirationDate(),
				breakMng.getOccurrenceTimes().v(),
				breakMng.getOccurrenceDays().v(),
				breakMng.getHaftDayTime().v(),
				unUseTimes,
				unUseDays,
				DigestionAtr.USED,
				Optional.empty(),
				Optional.empty());
		//代休使用期限をチェックする //ExpirationTime: 0: 当月, 2: 年度末クリア
		if(subsHolidaySetting != null && (subsHolidaySetting.getExpirationOfsubstiHoliday() == 0
				|| subsHolidaySetting.getExpirationOfsubstiHoliday() == 2)) {
			//社員に対応する処理締めを取得する
			//社員に対応する処理締めを取得する
			Closure period = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, baseDate);
			//指定した年月日時点の締め期間を取得する
			Optional<ClosurePeriod> optClosurePeriod = period.getClosurePeriodByYmd(remainData.getYmd());
			if(subsHolidaySetting.getExpirationOfsubstiHoliday() == 0) {
				unUseBreak.setUseStartDate(optClosurePeriod.isPresent() ? Optional.ofNullable(optClosurePeriod.get().getPeriod().start()) : Optional.empty());
			} else {
				//会社の期首月を取得する
				CompanyDto compInfor = require.firstMonth(cacheCarrier, cid);

				//「締め期間」．年月の月と期首月を比較する
				if(optClosurePeriod.isPresent()
						&& optClosurePeriod.get().getYearMonth().v() >= compInfor.getStartMonth()) {
					//使用開始日=「締め期間」．期間．開始日.AddMonths(「締め期間」．年月の月 - 期首月)
					int months = optClosurePeriod.get().getYearMonth().v() - compInfor.getStartMonth();
					unUseBreak.setUseStartDate(Optional.of(optClosurePeriod.get().getPeriod().start().addMonths(months)));
				} else {
					//使用開始日=「締め期間」．期間．開始日.AddMonths(12 + 「締め期間」．年月の月 - 期首月)
					int months = optClosurePeriod.get().getYearMonth().v() - compInfor.getStartMonth();
					unUseBreak.setUseStartDate(Optional.of(optClosurePeriod.get().getPeriod().start().addMonths(12 + months)));
				}
			}
		}
		CompensatoryDayoffDate date = new CompensatoryDayoffDate(false, Optional.of(remainData.getYmd()));

		BreakDayOffDetail outData = new BreakDayOffDetail(remainData.getSID(),
				dataAtr,
				date,
				OccurrenceDigClass.OCCURRENCE,
				Optional.of(unUseBreak),
				Optional.empty());
		return outData;
	}

	/**
	 * 5.時系列順で相殺する
	 * @param lstDataDetail
	 * @return
	 */
	public static List<BreakDayOffDetail> lstSortForTime(List<BreakDayOffDetail> lstDataDetail) {
		//INPUT．「休出代休明細」．発生消化区分により、休出と代休を別ける
		List<BreakDayOffDetail> lstBreak = lstDataDetail.stream().filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.collect(Collectors.toList());
		List<BreakDayOffDetail> lstDayoff = lstDataDetail.stream().filter(y -> y.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				.collect(Collectors.toList());
		List<BreakDayOffDetail> lstBreackTmp = new ArrayList<>(lstBreak);
		List<BreakDayOffDetail> lstDayoffTmp = new ArrayList<>(lstDayoff);
		//「休出代休明細」(代休)をループする
		for (BreakDayOffDetail dayOffData : lstDayoff) {
			UnOffSetOfDayOff dayOffMng = dayOffData.getUnOffsetOfDayoff().get();
			//ループ中の「代休の未相殺」．未相殺日数と未相殺時間をチェックする
			if(dayOffMng.getUnOffsetDay() <= 0
					&& dayOffMng.getUnOffsetTimes() <= 0) {
				continue;
			}
			//「休出代休明細」(休出)をループする
			for (BreakDayOffDetail breakData : lstBreak) {
				UnUserOfBreak breakMng = breakData.getUnUserOfBreak().get();
				if(breakMng.getExpirationDate().before(dayOffData.getYmdData().getDayoffDate().isPresent() ? dayOffData.getYmdData().getDayoffDate().get() : GeneralDate.min()) //期限切れかをチェックする
						|| (breakMng.getUseStartDate().isPresent() && !breakMng.getUseStartDate().get().beforeOrEquals(dayOffData.getYmdData().getDayoffDate().get())) //使用可能かチェックする
						|| (breakMng.getUnUsedDays() <= 0 && breakMng.getUnUsedTimes() <= 0)//ループ中の「休出未使用」．未使用日数と未使用時間をチェックする
						) {
					continue;
				}
				lstDayoffTmp.remove(dayOffData);
				lstBreackTmp.remove(breakData);
				//ループ中の「代休の未相殺」．未相殺日数 > ループ中の「休出の未使用」．未使用日数
				//OR ループ中の「代休の未相殺」．未相殺時間> ループ中の「休出の未使用」．未使用時間
				if(dayOffMng.getUnOffsetDay() > breakMng.getUnUsedDays()
						|| dayOffMng.getUnOffsetTimes() > breakMng.getUnUsedTimes()) {
					//ループ中の「代休の未相殺」．未相殺日数  -= ループ中の「休出の未使用」．未使用日数 、ループ中の「代休の未相殺」．未相殺時間 -= ループ中の「休出の未使用」．未使用時間
					dayOffMng.setUnOffsetDay(dayOffMng.getUnOffsetDay() - breakMng.getUnUsedDays());
					dayOffMng.setUnOffsetTimes(dayOffMng.getUnOffsetTimes() - breakMng.getUnUsedTimes());
					dayOffData.setUnOffsetOfDayoff(Optional.of(dayOffMng));
					lstDayoffTmp.add(dayOffData);
					//ループ中の「休出の未使用」．未使用日数 = 0、ループ中の「休出の未使用」．未使用時間 = 0
					breakMng.setUnUsedDays(0);
					breakMng.setUnUsedTimes(0);
					breakData.setUnUserOfBreak(Optional.of(breakMng));
					lstBreackTmp.add(breakData);
				} else {
					//ループ中の「休出の未使用」．未使用日数 -= ループ中の「代休の未相殺」．未相殺日数、ループ中の「休出の未使用」．未使用時間 -= ループ中の「代休の未相殺」．未相殺時間
					breakMng.setUnUsedDays(breakMng.getUnUsedDays() - dayOffMng.getUnOffsetDay());
					breakMng.setUnUsedTimes(breakMng.getUnUsedTimes() - dayOffMng.getUnOffsetTimes());
					//ループ中の「代休の未相殺」．未相殺日数   = 0、ループ中の「代休の未相殺」．未相殺時間  = 0
					dayOffMng.setUnOffsetDay(0);
					dayOffMng.setUnOffsetTimes(0);
					dayOffData.setUnOffsetOfDayoff(Optional.of(dayOffMng));
					lstDayoffTmp.add(dayOffData);
					breakData.setUnUserOfBreak(Optional.of(breakMng));
					lstBreackTmp.add(breakData);
					break;
				}
			}
			//相殺できる休出があるかチェックする: 全ての「休出代休明細」(休出)．「休出の未使用」．未使用日数と未使用時間が0の場合、相殺できる休出がないとする。
			List<BreakDayOffDetail> lstBreakChk = lstBreackTmp.stream()
					.filter(x -> x.getUnUserOfBreak().get().getUnUsedDays() == 0 && x.getUnUserOfBreak().get().getUnUsedTimes() == 0)
					.collect(Collectors.toList());
			if(lstBreakChk.size() == lstBreackTmp.size()) {
				break;
			}
		}
		lstBreackTmp.addAll(lstDayoffTmp);

		return lstBreackTmp;
	}

	/**
	 * 7.発生数・使用数を計算する
	 * @param lstDataDetail
	 * @param dateData
	 * @return
	 */
	public static RemainUnDigestedDayTimes getRemainOccurrenceUseDayTimes(List<BreakDayOffDetail> lstDataDetail,
			DatePeriod dateData) {
		RemainUnDigestedDayTimes outputData = new RemainUnDigestedDayTimes(0, 0, 0, 0, false);
		//「List<代休出明細>」を取得
		for (BreakDayOffDetail detailData : lstDataDetail) {
			if(!detailData.getYmdData().isUnknownDate()
					&& detailData.getYmdData().getDayoffDate().isPresent()
					&& detailData.getYmdData().getDayoffDate().get().afterOrEquals(dateData.start())
					&& detailData.getYmdData().getDayoffDate().get().beforeOrEquals(dateData.end())) {
				//処理中の休出代休明細．発生消化区分をチェック
				if(detailData.getOccurrentClass() == OccurrenceDigClass.DIGESTION) {
					//代休使用日数 += 代休の未相殺．必要日数
					outputData.setUnDigestedDays(outputData.getUnDigestedDays() + detailData.getUnOffsetOfDayoff().get().getRequiredDay());
					//代休使用時間 += 代休の未相殺．必要時間数
					outputData.setUnDigestedTimes(outputData.getUnDigestedTimes() + detailData.getUnOffsetOfDayoff().get().getRequiredTime());
				} else {
					//代休発生日数 += 休出の未使用．発生日数
					outputData.setRemainDays(outputData.getRemainDays() + detailData.getUnUserOfBreak().get().getOccurrenceDays());
					//代休発生時間 += 休出の未使用．発生時間数
					outputData.setRemainTimes(outputData.getRemainTimes() + detailData.getUnUserOfBreak().get().getOccurrenceTimes());
				}
			}

		}
		return outputData;
	}

	/**
	 * 消化区分と消滅日を計算する
	 * @param lstDetail
	 * @return
	 */
	public static List<BreakDayOffDetail> calDigestionAtr(List<BreakDayOffDetail> lstDetail, GeneralDate baseDate) {
		lstDetail.stream().forEach(x -> {
			//ループ中の「休出代休明細．発生消化区分」をチェック
			if(x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE) {
				UnUserOfBreak breakData = x.getUnUserOfBreak().get();
				//ループ中の「休出の未使用」の「未使用日数」と「未使用時間」をチェックする
				if(breakData.getUnUsedDays() == 0
						&& breakData.getUnUsedTimes() == 0) {
					//休出の未使用．代休消化区分　←　"消化済"
					x.getUnUserOfBreak().get().setDigestionAtr(DigestionAtr.USED);
				} else {
					//ループ中の「休出の未使用．使用期限日」と「INPUT．基準日」を比較
					if(breakData.getExpirationDate().after(baseDate)) {
						//休出の未使用．代休消化区分　←　"未消化"
						x.getUnUserOfBreak().get().setDigestionAtr(DigestionAtr.UNUSED);
					} else {
						//休出の未使用．代休消化区分　←　"消滅"
						x.getUnUserOfBreak().get().setDigestionAtr(DigestionAtr.EXPIRED);
						//代出の未使用．消滅日　←　代出の未使用．使用期限日
						x.getUnUserOfBreak().get().setDisappearanceDate(Optional.of(breakData.getExpirationDate()));
					}
				}
			}
		});
		return lstDetail;
	}

	/**
	 *  3.未相殺の代休(暫定)を取得する
	 * 4.未使用の休出(暫定)を取得する
	 * @param inputParam
	 * @param lstDetailData
	 * @return
	 */
	public static List<BreakDayOffDetail> lstInterimData(RequireM5 require, CacheCarrier cacheCarrier,
			BreakDayOffRemainMngParam inputParam,List<BreakDayOffDetail> lstDetailData) {

		List<InterimRemain> lstInterimDayoff = new ArrayList<>();
		List<InterimDayOffMng> lstDayoffMng = new ArrayList<>();
		List<InterimRemain> lstInterimBreak = new ArrayList<>();
		List<InterimBreakMng> lstBreakMng = new ArrayList<>();

		//INPUT．モードをチェックする
		if(inputParam.isMode()) {
			//暫定残数管理データを作成する
			//代休
			lstInterimDayoff = inputParam.getInterimMng().stream()
					.filter(x -> x.getYmd().afterOrEquals(inputParam.getDateData().start())
							&& x.getYmd().beforeOrEquals(inputParam.getDateData().end())
							&& x.getRemainType() == RemainType.SUBHOLIDAY)
					.collect(Collectors.toList());
			lstInterimDayoff.stream().forEach(a -> {
				List<InterimDayOffMng> temp = inputParam.getDayOffMng().stream()
						.filter(y -> y.getRemainManaID().equals(a.getRemainManaID()))
						.collect(Collectors.toList());
				lstDayoffMng.addAll(temp);
			});

			//休出
			lstInterimBreak = inputParam.getInterimMng().stream()
					.filter(x -> x.getYmd().afterOrEquals(inputParam.getDateData().start())
							&& x.getYmd().beforeOrEquals(inputParam.getDateData().end())
							&& x.getRemainType() == RemainType.BREAK)
					.collect(Collectors.toList());
			lstInterimBreak.stream().forEach(b -> {
				List<InterimBreakMng> temp = inputParam.getBreakMng().stream()
						.filter(c -> c.getRemainManaID().equals(b.getRemainManaID()))
						.collect(Collectors.toList());
				lstBreakMng.addAll(temp);
			});
		} else {
			//ドメインモデル「暫定代休管理データ」を取得する
			 lstDayoffMng.addAll(require.interimDayOffMng(inputParam.getSid(), inputParam.getDateData()));

			//ドメインモデル「暫定休出管理データ」を取得する
			lstBreakMng.addAll(require.interimBreakMng(inputParam.getSid(), inputParam.getDateData()));
		}
		//20181003 DuDT fix bug 101491 ↓
		List<InterimRemain> lstTmpDayoff = new ArrayList<>(lstInterimDayoff);
		List<InterimDayOffMng> lstDayoffUsen = new ArrayList<>(lstDayoffMng);
		List<InterimRemain> lstTmpBreak = new ArrayList<>(lstInterimBreak);
		List<InterimBreakMng> lstBreakMngUsen = new ArrayList<>(lstBreakMng);
		if(inputParam.isReplaceChk()
				&& !inputParam.getInterimMng().isEmpty()
				&& !inputParam.isMode()) {
			for (InterimRemain interimRemain : inputParam.getInterimMng()) {

				List<InterimRemain> lstInterimDayoffUsen = lstTmpDayoff.stream()
						.filter(a -> a.getYmd().equals(interimRemain.getYmd())).collect(Collectors.toList());
				if(!lstInterimDayoffUsen.isEmpty()) {
					InterimRemain temp = lstInterimDayoffUsen.get(0);
					lstInterimDayoff.remove(temp);
					List<InterimDayOffMng> tmpDayoffUsen = lstDayoffUsen.stream().filter(b -> b.getRemainManaID().equals(temp.getRemainManaID()))
							.collect(Collectors.toList());
					if(!tmpDayoffUsen.isEmpty()) {
						lstDayoffMng.remove(tmpDayoffUsen.get(0));
					}
				}
				List<InterimRemain> lstBreakUsen = lstTmpBreak.stream()
						.filter(b -> b.getYmd().equals(interimRemain.getYmd())).collect(Collectors.toList());
				if(!lstBreakUsen.isEmpty()) {
					lstInterimBreak.remove(lstBreakUsen.get(0));
					List<InterimBreakMng> tempLstBreak = lstBreakMngUsen.stream().filter(b -> b.getRemainManaID().equals(lstBreakUsen.get(0).getRemainManaID()))
							.collect(Collectors.toList());
					if(!tempLstBreak.isEmpty()) {
						lstBreakMng.remove(tempLstBreak.get(0));
					}
				}
			}
		}
		//20181003 DuDT fix bug 101491 ↑
		if(inputParam.isReplaceChk()
				&& inputParam.getCreatorAtr().isPresent()
				&& inputParam.getProcessDate().isPresent()) {
			List<InterimRemain> lstDayoffRemove = lstInterimDayoff.stream().filter(x -> x.getCreatorAtr() == inputParam.getCreatorAtr().get()
					&& x.getYmd().afterOrEquals(inputParam.getProcessDate().get().start())
					&& x.getYmd().beforeOrEquals(inputParam.getProcessDate().get().end())).collect(Collectors.toList());
			lstDayoffRemove.stream().forEach(x -> {
				List<InterimDayOffMng> lstOffMng = lstDayoffMng.stream().filter(a -> a.getRemainManaID().equals(x.getRemainManaID())).collect(Collectors.toList());
				lstDayoffMng.removeAll(lstOffMng);
			});
			lstInterimDayoff.removeAll(lstDayoffRemove);
			List<InterimRemain>  lstBreakRemove = lstInterimBreak.stream().filter(x -> x.getCreatorAtr() == inputParam.getCreatorAtr().get()
					&& x.getYmd().afterOrEquals(inputParam.getProcessDate().get().start())
					&& x.getYmd().beforeOrEquals(inputParam.getProcessDate().get().end())).collect(Collectors.toList());
			lstBreakRemove.stream().forEach(x -> {
				List<InterimBreakMng> lstBre = lstBreakMng.stream().filter(a -> a.getRemainManaID().equals(x.getRemainManaID())).collect(Collectors.toList());
				lstBreakMng.removeAll(lstBre);
			});
			lstInterimBreak.removeAll(lstBreakRemove);
		}
		List<BreakDayOffDetail> lstOutputDayoff = lstOutputDayoff(require, inputParam, lstDayoffMng, lstInterimDayoff);
		lstDetailData.addAll(lstOutputDayoff);
		List<BreakDayOffDetail> lstOutputBreak = lstOutputBreak(require, cacheCarrier,inputParam, lstBreakMng, lstInterimBreak);
		lstDetailData.addAll(lstOutputBreak);
		return lstDetailData;

	}

	/**
	 * [No.505]代休残数を取得する
	 * @param employeeID
	 * @param date
	 * @return
	 */
	public static NumberConsecutiveVacation getBreakDayOffMngRemain(RequireM11 require, CacheCarrier cacheCarrier,
			String employeeID, GeneralDate date) {
		String companyID = AppContexts.user().companyId();
		// 基準日時点で取得可能な代休日数を取得する
		NumberConsecutiveVacation numberConsecutiveVacation = GetNumberOfVacaLeavObtainBaseDate.process(require, companyID, employeeID, date);
		return numberConsecutiveVacation;
	}


	private static List<BreakDayOffDetail> lstOutputDayoff(RequireM4 require, BreakDayOffRemainMngParam inputParam,
			List<InterimDayOffMng> lstDayoffMng, List<InterimRemain> lstInterimDayoff) {
		List<BreakDayOffDetail> lstOutputDayoff = new ArrayList<>();
		if(inputParam.isReplaceChk()
				&& !inputParam.getInterimMng().isEmpty()
				&& !inputParam.getDayOffMng().isEmpty()) {
			List<InterimDayOffMng> lstDayOffTmp = new ArrayList<>(lstDayoffMng);
			List<InterimRemain> lstInterimDataTmp = new ArrayList<>(lstInterimDayoff);
			//INPUT．上書き用の暫定管理データをドメインモデル「暫定代休管理データ」に追加する
			for (InterimDayOffMng dayOffInput : inputParam.getDayOffMng()) {
				List<InterimRemain> lstInterimInput = inputParam.getInterimMng()
						.stream()
						.filter(z -> z.getRemainManaID().equals(dayOffInput.getRemainManaID()))
						.collect(Collectors.toList());
				if(!lstInterimInput.isEmpty()) {
					InterimRemain interimData = lstInterimInput.get(0);
					List<InterimRemain> lstTmp = lstInterimDataTmp.stream().filter(a -> a.getYmd().equals(interimData.getYmd()))
					.collect(Collectors.toList());
					if(!lstTmp.isEmpty()) {
						InterimRemain tmpData = lstTmp.get(0);
						lstInterimDayoff.remove(tmpData);
						List<InterimDayOffMng> lstTmpDayoff = lstDayOffTmp.stream()
								.filter(b -> b.getRemainManaID().equals(tmpData.getRemainManaID())).collect(Collectors.toList());
						if(!lstTmpDayoff.isEmpty()) {
							InterimDayOffMng tmpDayoff = lstTmpDayoff.get(0);
							lstDayoffMng.remove(tmpDayoff);
						}
					}
					lstInterimDayoff.add(interimData);
				}
				lstDayoffMng.add(dayOffInput);
			}

		}
		//取得した件数をチェックする
		for (InterimDayOffMng interimDayOffMng : lstDayoffMng) {
			//アルゴリズム「休出と紐付けをしない代休を取得する」を実行する
			InterimRemain interimData = lstInterimDayoff.stream()
					.filter(x -> x.getRemainManaID().equals(interimDayOffMng.getRemainManaID()))
					.collect(Collectors.toList())
					.get(0);
			BreakDayOffDetail outData = getNotTypeBreak(require, interimDayOffMng, interimData);
			lstOutputDayoff.add(outData);
		}
		return lstOutputDayoff;
	}

	private static List<BreakDayOffDetail> lstOutputBreak(RequireM3 require, CacheCarrier cacheCarrier,
			BreakDayOffRemainMngParam inputParam,List<InterimBreakMng> lstBreakMng,List<InterimRemain> lstInterimBreak){
		List<BreakDayOffDetail> lstOutputBreak = new ArrayList<>();
		//INPUT．上書きフラグをチェックする
		if(inputParam.isReplaceChk()
				&& !inputParam.getInterimMng().isEmpty()
				&& !inputParam.getBreakMng().isEmpty()) {
			List<InterimRemain> lstInterimDataTmp = new ArrayList<>(lstInterimBreak);
			List<InterimBreakMng> lstBreakMngTmp =  new ArrayList<>(lstBreakMng);
			//INPUT．上書き用の暫定管理データをドメインモデル「暫定休出管理データ」に追加する
			for (InterimBreakMng breakReplace : inputParam.getBreakMng()) {
				List<InterimRemain> lstRemainReplace = inputParam.getInterimMng().stream()
						.filter(x -> x.getRemainManaID().equals(breakReplace.getRemainManaID()))
						.collect(Collectors.toList());
				if(!lstRemainReplace.isEmpty()) {
					InterimRemain remainReplace = lstRemainReplace.get(0);
					List<InterimRemain> lstRemainData = lstInterimDataTmp.stream()
							.filter(z -> z.getYmd().equals(remainReplace.getYmd()))
							.collect(Collectors.toList());
					if(!lstRemainData.isEmpty()) {
						InterimRemain remainData = lstRemainData.get(0);
						List<InterimBreakMng> lstBreakData = lstBreakMngTmp.stream()
								.filter(a -> a.getRemainManaID().equals(remainData.getRemainManaID()))
								.collect(Collectors.toList());
						if(!lstBreakData.isEmpty()) {
							InterimBreakMng breakData = lstBreakData.get(0);

							lstBreakMng.remove(breakData);
						}
						lstInterimBreak.remove(remainData);
					}
					lstInterimBreak.add(remainReplace);
				}
				lstBreakMng.add(breakReplace);
			}

		}
		//代休の設定を取得する
		SubstitutionHolidayOutput subsHolidaySetting = AbsenceTenProcess.getSettingForSubstituteHoliday(
				require, cacheCarrier,inputParam.getCid(), inputParam.getSid(), inputParam.getBaseDate());

		for (InterimBreakMng breakMng : lstBreakMng) {
			//アルゴリズム「代休と紐付けをしない休出を取得する」を実行する
			InterimRemain remainData = lstInterimBreak.stream()
					.filter(a -> a.getRemainManaID().equals(breakMng.getRemainManaID()))
					.collect(Collectors.toList())
					.get(0);
			BreakDayOffDetail dataDetail = getNotTypeDayOff(require, cacheCarrier,
					breakMng, remainData, inputParam.getDateData().start(), inputParam.getBaseDate(),
					subsHolidaySetting, inputParam.getCid(), inputParam.getSid());
			if(dataDetail != null) {
				lstOutputBreak.add(dataDetail);
			}

		}
		return lstOutputBreak;
	}

	public static interface RequireM10 extends RequireM9, RequireM8, RequireM5 {

	}

	public static interface RequireM7 {

		List<InterimBreakDayOffMng> interimBreakDayOffMng(String mngId, boolean breakDay, DataManagementAtr mngAtr);

	}

	public static interface RequireM6 extends ClosureService.RequireM3, RequireM7 {

		CompanyDto firstMonth(CacheCarrier cacheCarrier, String companyId);

	}

	public static interface RequireM5 extends RequireM3, RequireM4, RequireM0 {

		List<InterimBreakMng> interimBreakMng(String breakManaId, DatePeriod datePeriod);
	}

	public static interface RequireM4 extends RequireM7 {

	}

	public static interface RequireM9 extends RequireM3 {

		List<LeaveManagementData> leaveManagementData(String cid, String sid, GeneralDate ymd, DigestionAtr state);

	}

	public static interface RequireM3 extends AbsenceTenProcess.RequireM4, RequireM6 {

	}

	public static interface RequireM2 extends NumberRemainVacationLeaveRangeQuery.Require, ClosureService.RequireM3 {

	}

	public static interface RequireM0 {

		List<InterimDayOffMng> interimDayOffMng(String dayOffManaId, DatePeriod datePeriod);
	}

	public static interface RequireM8 {

		List<CompensatoryDayOffManaData> compensatoryDayOffManaData(String cid, String sid, GeneralDate ymd);
	}

	public static interface RequireM1 extends RequireM7, RequireM0 {

		Map<GeneralDate, DailyInterimRemainMngData> monthInterimRemainData(String cid, String sid, DatePeriod dateData);
	}

	public static interface RequireM11 extends GetNumberOfVacaLeavObtainBaseDate.Require{
	    
	}
}