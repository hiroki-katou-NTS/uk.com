package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.GetTightSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ProcessDataTemporary;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.GetSettingCompensaLeave;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;

/**
 * @author ThanhNX
 *
 *         4.未使用の振出(暫定)を取得する
 */
public class GetUnusedCompenTemporary {

	private GetUnusedCompenTemporary() {
	};

	// 4.未使用の振出(暫定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, AbsRecMngInPeriodRefactParamInput input) {

		List<InterimRecMng> lstRecMng = new ArrayList<>();
		List<InterimRemain> lstInterimMngOfRec = new ArrayList<>();
		List<AccumulationAbsenceDetail> lstOutput = new ArrayList<>();

		// INPUT．モードをチェックする
		if (input.isMode()) {
			// INPUT．上書き用の暫定管理データを受け取る
			// INPUT．上書き用の暫定管理データから「暫定振出管理データ」を取得する
			lstInterimMngOfRec.addAll(input.getInterimMng().stream()
					.filter(x -> x.getSID().equals(input.getSid())
							&& x.getYmd().afterOrEquals(input.getDateData().start())
							&& x.getYmd().beforeOrEquals(input.getDateData().end())
							&& x.getRemainType() == RemainType.PICKINGUP)
					.collect(Collectors.toList()));
			Map<String, String> mapId = lstInterimMngOfRec.stream()
					.collect(Collectors.toMap(x -> x.getRemainManaID(), x -> x.getRemainManaID()));
			lstRecMng.addAll(input.getUseRecMng().stream().filter(x -> mapId.containsKey(x.getRecruitmentMngId()))
					.collect(Collectors.toList()));

		} else {
			// ドメインモデル「暫定振出管理データ」を取得する
			lstInterimMngOfRec
					.addAll(require.getRemainBySidPriod(input.getSid(), input.getDateData(), RemainType.PICKINGUP));
			lstRecMng.addAll(require.getRecBySidDatePeriod(input.getSid(), input.getDateData()));

		}

		// 対象期間のドメインモデル「暫定振出管理データ」を上書き用の暫定管理データに置き換える
		ProcessDataTemporary.processOverride(input, input.getUseRecMng(), lstInterimMngOfRec, lstRecMng);

		// 振休の設定を取得する
		LeaveSetOutput leaveSetOut = GetSettingCompensaLeave.process(require, input.getCid(), input.getSid(),
				input.getDateData().end());

		// 取得した件数をチェックする
		for (InterimRecMng interimRecMng : lstRecMng) {
			InterimRemain remainData = lstInterimMngOfRec.stream()
					.filter(x -> x.getRemainManaID().equals(interimRecMng.getRecruitmentMngId()))
					.collect(Collectors.toList()).get(0);
			// アルゴリズム「振休と紐付けをしない振出を取得する」を実行する
			lstOutput.add(getNotTypeRec(require, interimRecMng, remainData, input.getCid(), input.getSid(),
					input.getDateData().end(), leaveSetOut));
		}
		return lstOutput;

	}

	// 4-1.振休と紐付けをしない振出を取得する
	public static AccumulationAbsenceDetail getNotTypeRec(Require require, InterimRecMng recMng,
			InterimRemain remainData, String cid, String sid, GeneralDate aggEndDate, LeaveSetOutput leaveSetOut) {
		// ドメインモデル「暫定振出振休紐付け管理」を取得する
		List<PayoutSubofHDManagement> lstInterimMng = require.getByPayoutId(remainData.getSID(), remainData.getYmd());

		// 未使用日数←SELF.発生日数
		double unUseDays = recMng.getOccurrenceDays().v();
		if (!lstInterimMng.isEmpty()) {
			for (PayoutSubofHDManagement interimMng : lstInterimMng) {
				// 未使用日数：INPUT.暫定振出管理データ.発生日数－合計(暫定振出振休紐付け管理.使用日数)
				unUseDays -= interimMng.getAssocialInfo().getDayNumberUsed().v();
			}
		}

		// 「逐次発生の休暇明細」．未使用数=未使用数
		// INPUT．暫定振出管理データを「逐次発生の休暇明細」に追加する

//		// 締め設定を取得する
//		Optional<GetTightSettingResult> tightSettingResult = GetTightSetting.getTightSetting(require, cid, sid,
//				aggEndDate, ExpirationTime.valueOf(leaveSetOut.getExpirationOfLeave()), remainData.getYmd());
//
//		// 使用期限日を設定
//		GeneralDate dateSettingExp = SettingExpirationDate.settingExp(
//				ExpirationTime.valueOf(leaveSetOut.getExpirationOfLeave()), tightSettingResult,
//				remainData.getYmd());

		CompensatoryDayoffDate date = new CompensatoryDayoffDate(false, Optional.of(remainData.getYmd()));
		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if (remainData.getCreatorAtr() == CreateAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (remainData.getCreatorAtr() == CreateAtr.RECORD) {
			dataAtr = MngDataStatus.RECORD;
		}

		AccumulationAbsenceDetail detail = new AccuVacationBuilder(remainData.getSID(), date,
				OccurrenceDigClass.OCCURRENCE, dataAtr, recMng.getRecruitmentMngId())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(recMng.getOccurrenceDays().v()), Optional.empty()))
						.unbalanceNumber(
								new NumberConsecuVacation(new ManagementDataRemainUnit(unUseDays), Optional.empty()))
						.build();
		return new UnbalanceCompensation(detail, recMng.getExpirationDate(), DigestionAtr.USED, Optional.empty(),
				recMng.getStatutoryAtr());
	}

	public static interface Require extends GetSettingCompensaLeave.Require, GetTightSetting.Require {

		// InterimRemainRepository
		List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType);

		// InterimRecAbasMngRepository
		List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period);

		// PayoutSubofHDManaRepository
		List<PayoutSubofHDManagement> getByPayoutId(String sid, GeneralDate occDate);
	}

}
