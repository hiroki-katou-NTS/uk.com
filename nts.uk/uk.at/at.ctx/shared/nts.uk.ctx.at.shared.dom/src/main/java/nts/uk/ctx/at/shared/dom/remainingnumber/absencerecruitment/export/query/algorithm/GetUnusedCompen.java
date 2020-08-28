package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;

/**
 * @author ThanhNX
 *
 *         2.未使用の振出(確定)を取得する
 */
public class GetUnusedCompen {
	
	private GetUnusedCompen() {};

	// 2.未使用の振出(確定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, String cid, String employeeId,
			GeneralDate ymd) {

		List<AccumulationAbsenceDetail> result = new ArrayList<>();
		// アルゴリズム「確定振出から未使用の振出を取得する」を実行する
		List<PayoutManagementData> lstPayout = unbalanceHolConfirme(require, cid, employeeId, ymd);

		// アルゴリズム「暫定振休と紐付けをしない確定振出を取得する」を実行する
		lstPayout.stream().forEach(x -> acquireFixedSuspension(employeeId, ymd, x).ifPresent(data -> result.add(data)));
		return result;

	}

	// 2-1.確定振出から未使用の振出を取得する
	public static List<PayoutManagementData> unbalanceHolConfirme(Require require, String cid, String employeeId,
			GeneralDate ymd) {
		return require.getByUnUseState(cid, employeeId, ymd, 0, DigestionAtr.UNUSED);
	}

	// 2-3.暫定振休と紐付けをしない確定振出を取得する
	public static Optional<AccumulationAbsenceDetail> acquireFixedSuspension(String employeeId, GeneralDate ymd,
			PayoutManagementData data) {

		if (data.getPayoutDate().getDayoffDate().isPresent()
				&& data.getPayoutDate().getDayoffDate().get().afterOrEquals(ymd)) {
			return Optional.empty();
		}
		// アルゴリズム「暫定振休と紐付けをしない確定振出を取得する」を実行する
		// ドメインモデル「暫定振出振休紐付け管理」を取得する REPONSE 対応
		// List<InterimRecAbsMng> lstInterim =
		// recAbsRepo.getRecBySidMngAtr(DataManagementAtr.CONFIRM,
		// DataManagementAtr.INTERIM, confirmRecData.getPayoutId());
		double unUseDays = data.getUnUsedDays().v();
		/*
		 * for (InterimRecAbsMng interimData : lstInterim) { unUseDays -=
		 * interimData.getUseDays().v(); }
		 */
		// 未使用日数をチェックする
		if (unUseDays <= 0) {
			return Optional.empty();
		}

		// 「逐次発生の休暇明細」．未相殺数=未相殺数

		AccumulationAbsenceDetail result = new AccuVacationBuilder(data.getSID(), data.getPayoutDate(),
				OccurrenceDigClass.OCCURRENCE, MngDataStatus.CONFIRMED, data.getPayoutId())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(data.getOccurredDays().v()), Optional.empty()))
						.unbalanceNumber(
								new NumberConsecuVacation(new ManagementDataRemainUnit(unUseDays), Optional.empty()))
						.unbalanceCompensation(
								new UnbalanceCompensation(data.getExpiredDate(), data.getStateAtr(), data.getDisapearDate(),
										EnumAdaptor.valueOf(data.getLawAtr().value, StatutoryAtr.class)))
						.build();
		return Optional.of(result);

	}

	public static interface Require {

		// PayoutManagementDataRepository
		List<PayoutManagementData> getByUnUseState(String cid, String sid, GeneralDate ymd, double unUse,
				DigestionAtr state);

	}

}
