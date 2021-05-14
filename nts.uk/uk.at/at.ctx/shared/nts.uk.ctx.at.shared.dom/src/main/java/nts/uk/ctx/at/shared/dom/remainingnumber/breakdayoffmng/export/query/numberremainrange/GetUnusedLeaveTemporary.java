package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SettingSubstituteHolidayProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;

/**
 * @author ThanhNX
 *
 *         4.未使用の休出(暫定)を取得する
 */
public class GetUnusedLeaveTemporary {

	private GetUnusedLeaveTemporary() {
	};

	// 4.未使用の休出(暫定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, BreakDayOffRemainMngRefactParam param) {

		List<AccumulationAbsenceDetail> result = new ArrayList<>();
		List<InterimBreakMng> lstBreakMng = param.getBreakMng();

		if (!param.isModeMonth()) {
			lstBreakMng = require.getBySidPeriod(param.getSid(), param.getDateData());

		} 

		// 対象期間のドメインモデル「暫定休出管理データ」を上書き用の暫定管理データに置き換える

		// 代休の設定を取得する
		SubstitutionHolidayOutput subHolidayOut = SettingSubstituteHolidayProcess
				.getSettingForSubstituteHoliday(require, param.getCid(), param.getSid(), param.getDateData().end());

		// 取得した件数をチェックする

		// アルゴリズム「代休と紐付けをしない休出を取得する」を実行する
		for (InterimBreakMng breakMng : lstBreakMng) {
			
			AccumulationAbsenceDetail dataDetail = getNotTypeDayOff(require, breakMng,
					param.getDateData().end(), subHolidayOut, param.getCid(), param.getSid());
			result.add(dataDetail);
		}
		return result;
	}

	// 4-1.代休と紐付けをしない休出を取得する
	public static AccumulationAbsenceDetail getNotTypeDayOff(Require require,
			InterimBreakMng interimData, GeneralDate aggEndDate,
			SubstitutionHolidayOutput subsHolidaySetting, String cid, String sid) {

		// ドメインモデル「休出代休紐付け管理」を取得する(get domain model 「休出代休紐付け管理」)
		List<LeaveComDayOffManagement> lstInterimBreakDay = require
				.getByLeaveID(interimData.getSID(), interimData.getYmd());

		Integer unUseTimes = interimData.getUnUsedTimes().v();
		double unUseDays = interimData.getUnUsedDays().v();
		for (LeaveComDayOffManagement interimBreakData : lstInterimBreakDay) {
			unUseDays -= interimBreakData.getAssocialInfo().getDayNumberUsed().v();
		}

//		// 締め設定を取得する
//		Optional<GetTightSettingResult> tightSettingResult = GetTightSetting.getTightSetting(require, cid, sid,
//				aggEndDate, ExpirationTime.valueOf(subsHolidaySetting.getExpirationOfsubstiHoliday()),
//				interimData.getLeft().getYmd());
//
//		// 使用期限を設定
//		GeneralDate dateSettingExp = SettingExpirationDate.settingExp(
//				ExpirationTime.valueOf(subsHolidaySetting.getExpirationOfsubstiHoliday()), tightSettingResult,
//				interimData.getLeft().getYmd());

		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if (interimData.getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (interimData.getCreatorAtr() == CreateAtr.RECORD) {
			dataAtr = MngDataStatus.RECORD;
		}

		AccumulationAbsenceDetail detail = new AccuVacationBuilder(interimData.getSID(),
				new CompensatoryDayoffDate(false, Optional.of(interimData.getYmd())),
				OccurrenceDigClass.OCCURRENCE, dataAtr, interimData.getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(interimData.getOccurrenceDays().v()),
								Optional.of(new AttendanceTime(interimData.getOccurrenceTimes().v()))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unUseDays),
								Optional.of(new AttendanceTime(unUseTimes))))
						.build();
		return new UnbalanceVacation(interimData.getExpirationDate(), DigestionAtr.USED, Optional.empty(), detail,
				interimData.getOnedayTime(), interimData.getHaftDayTime());

	}

	public static interface Require extends SettingSubstituteHolidayProcess.Require {

		// InterimBreakDayOffMngRepository
		List<InterimBreakMng> getBySidPeriod(String sid, DatePeriod period);

		//ドメインモデル「休出代休紐付け管理」を取得する
		 //LeaveComDayOffManaRepository
		List<LeaveComDayOffManagement> getByLeaveID(String sid, GeneralDate occDate);

	}

}
