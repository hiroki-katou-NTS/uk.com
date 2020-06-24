package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.GetTightSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.GetTightSetting.GetTightSettingResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ProcessDataTemporary;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.SettingExpirationDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SettingSubstituteHolidayProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;

/**
 * @author ThanhNX
 *
 *         4.未使用の休出(暫定)を取得する
 */
public class GetUnusedLeaveTemporary {

	// 4.未使用の休出(暫定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, BreakDayOffRemainMngRefactParam param) {

		List<AccumulationAbsenceDetail> result = new ArrayList<>();
		List<InterimRemain> lstInterimBreak = new ArrayList<>();
		List<InterimBreakMng> lstBreakMng = new ArrayList<>();

		if (param.isModeMonth()) {
			// INPUT．上書き用の暫定管理データを受け取る
			// 代休
			lstInterimBreak = param.getInterimMng().stream()
					.filter(x -> x.getYmd().afterOrEquals(param.getDateData().start())
							&& x.getYmd().beforeOrEquals(param.getDateData().end())
							&& x.getRemainType() == RemainType.BREAK)
					.collect(Collectors.toList());
			lstInterimBreak.stream().forEach(a -> {
				List<InterimBreakMng> temp = param.getBreakMng().stream()
						.filter(y -> y.getBreakMngId().equals(a.getRemainManaID())).collect(Collectors.toList());
				lstBreakMng.addAll(temp);
			});

		} else {
			// ドメインモデル「暫定休出管理データ」を取得する
			lstInterimBreak.addAll(require.getRemainBySidPriod(param.getSid(), param.getDateData(), RemainType.BREAK));
			lstBreakMng.addAll(require.getBySidPeriod(param.getSid(), param.getDateData()));
		}

		// 対象期間のドメインモデル「暫定休出管理データ」を上書き用の暫定管理データに置き換える 
		ProcessDataTemporary.processOverride(param, param.getBreakMng(), lstInterimBreak, lstBreakMng);

		// 代休の設定を取得する
		SubstitutionHolidayOutput subHolidayOut = SettingSubstituteHolidayProcess
				.getSettingForSubstituteHoliday(require, param.getCid(), param.getSid(), param.getScreenDisplayDate());

		// 取得した件数をチェックする

		// アルゴリズム「代休と紐付けをしない休出を取得する」を実行する
		for (InterimBreakMng breakMng : lstBreakMng) {
			// アルゴリズム「代休と紐付けをしない休出を取得する」を実行する
			InterimRemain remainData = lstInterimBreak.stream()
					.filter(a -> a.getRemainManaID().equals(breakMng.getBreakMngId())).collect(Collectors.toList())
					.get(0);
			AccumulationAbsenceDetail dataDetail = getNotTypeDayOff(require, Pair.of(remainData, breakMng),
					param.getDateData().start(), param.getScreenDisplayDate(), subHolidayOut, param.getCid(),
					param.getSid());
			if (dataDetail != null) {
				result.add(dataDetail);
			}
		}
		return result;
	}

	// 4-1.代休と紐付けをしない休出を取得する
	public static AccumulationAbsenceDetail getNotTypeDayOff(Require require,
			Pair<InterimRemain, InterimBreakMng> interimData, GeneralDate aggStartDate, GeneralDate baseDate,
			SubstitutionHolidayOutput subsHolidaySetting, String cid, String sid) {

		// ドメインモデル「暫定休出代休紐付け管理」を取得する
		List<InterimBreakDayOffMng> lstInterimBreakDay = require
				.getBreakDayOffMng(interimData.getLeft().getRemainManaID(), true, DataManagementAtr.INTERIM);

		Integer unUseTimes = interimData.getRight().getUnUsedTimes().v();
		double unUseDays = interimData.getRight().getUnUsedDays().v();
		for (InterimBreakDayOffMng interimBreakData : lstInterimBreakDay) {
			unUseTimes -= interimBreakData.getUseTimes().v();
			unUseDays -= interimBreakData.getUseDays().v();
		}

		// 締め設定を取得する
		Optional<GetTightSettingResult> tightSettingResult = GetTightSetting.getTightSetting(require, cid, sid,
				baseDate, ExpirationTime.valueOf(subsHolidaySetting.getExpirationOfsubstiHoliday()), aggStartDate);

		// 使用期限を設定
		GeneralDate dateSettingExp = SettingExpirationDate.settingExp(
				ExpirationTime.valueOf(subsHolidaySetting.getExpirationOfsubstiHoliday()), tightSettingResult,
				interimData.getRight().getExpirationDate());

		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if (interimData.getLeft().getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (interimData.getLeft().getCreatorAtr() == CreateAtr.RECORD) {
			dataAtr = MngDataStatus.RECORD;
		}

		new AccuVacationBuilder(interimData.getLeft().getSID(),
				new CompensatoryDayoffDate(false, Optional.of(interimData.getLeft().getYmd())),
				OccurrenceDigClass.OCCURRENCE, dataAtr, interimData.getLeft().getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(interimData.getRight().getOccurrenceDays().v()),
								Optional.of(new AttendanceTime(interimData.getRight().getOccurrenceTimes().v()))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unUseDays),
								Optional.of(new AttendanceTime(unUseTimes))))
						.unbalanceVacation(new UnbalanceVacation(dateSettingExp, DigestionAtr.USED, Optional.empty(),
								interimData.getRight().getOnedayTime(), interimData.getRight().getHaftDayTime()))
						.build();

		return new AccuVacationBuilder(interimData.getLeft().getSID(),
				new CompensatoryDayoffDate(false, Optional.of(interimData.getLeft().getYmd())),
				OccurrenceDigClass.OCCURRENCE, dataAtr, interimData.getLeft().getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(interimData.getRight().getOccurrenceDays().v()),
								Optional.of(new AttendanceTime(interimData.getRight().getOccurrenceTimes().v()))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unUseDays),
								Optional.of(new AttendanceTime(unUseTimes))))
						.unbalanceVacation(new UnbalanceVacation(dateSettingExp, DigestionAtr.USED, Optional.empty(),
								interimData.getRight().getOnedayTime(), interimData.getRight().getHaftDayTime()))
						.build();
	}

	public static interface Require extends SettingSubstituteHolidayProcess.Require, GetTightSetting.Require {

		// 暫定残数管理データ
		// InterimRemainRepository
		List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType);

		// InterimBreakDayOffMngRepository
		List<InterimBreakMng> getBySidPeriod(String sid, DatePeriod period);

		// InterimBreakDayOffMngRepository
		List<InterimBreakDayOffMng> getBreakDayOffMng(String mngId, boolean breakDay, DataManagementAtr mngAtr);

	}

}
