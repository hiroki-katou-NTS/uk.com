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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
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
			GeneralDate ymd, FixedManagementDataMonth fixManaDataMonth) {

		List<AccumulationAbsenceDetail> result = new ArrayList<>();
		// アルゴリズム「確定振休から未相殺の振休を取得する」を実行する
		List<SubstitutionOfHDManagementData> lstSubTem = unbalanceHolConfirme(require, cid, employeeId, ymd);
		List<SubstitutionOfHDManagementData> lstSub = new ArrayList<SubstitutionOfHDManagementData>();
		lstSub.addAll(lstSubTem);
		//追加用確定管理データをリストに追加する
		addDataFixManaMonth(fixManaDataMonth, lstSub);
		
		//追加用確定管理データをリストに追加する
		addDataFixManaMonth(fixManaDataMonth, lstSub);
		
		// アルゴリズム「暫定振出と紐付けをしない確定振休を取得する」を実行する
		for (SubstitutionOfHDManagementData data : lstSub) {
			acquireFixedSuspension(require, employeeId, data).ifPresent(x -> result.add(x));
		}

		return result;
	}

	// 1-1.確定振休から未相殺の振休を取得する
	public static List<SubstitutionOfHDManagementData> unbalanceHolConfirme(Require require, String cid,
			String employeeId, GeneralDate ymd) {
		return require.getByYmdUnOffset(cid, employeeId, ymd, 0);
	}

	// 1-3.暫定振出と紐付けをしない確定振休を取得する
	public static Optional<AccumulationAbsenceDetail> acquireFixedSuspension(Require require, String employeeId,
			SubstitutionOfHDManagementData data) {

		// ドメインモデル「暫定振出振休紐付け管理」を取得する REPONSE 対応
		List<PayoutSubofHDManagement> lstInterim  = new ArrayList<>();
		if (!data.getHolidayDate().isUnknownDate() && data.getHolidayDate().getDayoffDate().isPresent()) {
			lstInterim.addAll(require.getBySubId(data.getSID(), data.getHolidayDate().getDayoffDate().get()));
		}
		 
		double unUseDays = data.getRemainDays().v();

		for (PayoutSubofHDManagement interimRecAbsMng : lstInterim) {
			unUseDays -= interimRecAbsMng.getAssocialInfo().getDayNumberUsed().v();
		}

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

	// 追加用確定管理データの振休管理データをリストに追加する
	public static void addDataFixManaMonth(FixedManagementDataMonth fixManaDataMonth,
			List<SubstitutionOfHDManagementData> lstData) {

		lstData.addAll(fixManaDataMonth.getSubstitutionHDMagData());

	}

	public static interface Require {

		// SubstitutionOfHDManaDataRepository
		List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd,
				double unOffseDays);

		//PayoutSubofHDManaRepository
		List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate);

	}
}
