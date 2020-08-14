package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

/**
 * @author ThanhNX
 *
 *         1.未相殺の振休(確定)を取得する
 * 
 */
public class GetUnbalanceSuspension {

	private GetUnbalanceSuspension() {
	};

	// 1.未相殺の振休(確定)を取得する
	public static List<AccumulationAbsenceDetail> process(Require require, String cid, String employeeId,
			GeneralDate ymd) {

		List<AccumulationAbsenceDetail> result = new ArrayList<>();
		// アルゴリズム「確定振休から未相殺の振休を取得する」を実行する
		List<SubstitutionOfHDManagementData> lstSub = unbalanceHolConfirme(require, cid, employeeId, ymd);

		// アルゴリズム「暫定振出と紐付けをしない確定振休を取得する」を実行する
		for (SubstitutionOfHDManagementData data : lstSub) {
			acquireFixedSuspension(employeeId, data).ifPresent(x -> result.add(x));
		}

		return result;
	}

	// 1-1.確定振休から未相殺の振休を取得する
	public static List<SubstitutionOfHDManagementData> unbalanceHolConfirme(Require require, String cid,
			String employeeId, GeneralDate ymd) {
		return require.getByYmdUnOffset(cid, employeeId, ymd, 0);
	}

	// 1-3.暫定振出と紐付けをしない確定振休を取得する
	public static Optional<AccumulationAbsenceDetail> acquireFixedSuspension(String employeeId,
			SubstitutionOfHDManagementData data) {

		// ドメインモデル「暫定振出振休紐付け管理」を取得する REPONSE 対応
		// List<InterimRecAbsMng> lstInterim =
		// recAbsRepo.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
		// confirmAbsData.getSubOfHDID());
		double unUseDays = data.getRemainDays().v();
		/*
		 * for (InterimRecAbsMng interimRecAbsMng : lstInterim) { unUseDays -=
		 * interimRecAbsMng.getUseDays().v(); }
		 */
		if (unUseDays <= 0) {
			return Optional.empty();
		}

		// 「逐次発生の休暇明細」．未相殺数=未相殺数

		AccumulationAbsenceDetail result = new AccuVacationBuilder(data.getSID(), data.getHolidayDate(),
				OccurrenceDigClass.DIGESTION, MngDataStatus.CONFIRMED, data.getSubOfHDID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(data.getRequiredDays().v()), Optional.empty()))
						.unbalanceNumber(
								new NumberConsecuVacation(new ManagementDataRemainUnit(unUseDays), Optional.empty()))
						.build();
		return Optional.of(result);

	}

	public static interface Require {

		// SubstitutionOfHDManaDataRepository
		List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd,
				double unOffseDays);

	}
}
