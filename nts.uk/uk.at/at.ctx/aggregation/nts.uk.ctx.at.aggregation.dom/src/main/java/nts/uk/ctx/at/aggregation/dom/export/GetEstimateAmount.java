package nts.uk.ctx.at.aggregation.dom.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountByNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountByNo;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *
 *         目安金額を取得
 */
public class GetEstimateAmount {

	public static List<EstimateAmountSetting> getData(Require require, String cid, String sid, GeneralDate date) {

		Optional<CriterionAmountUsageSetting> setting = require.getAmountUsageSet(cid);
		if (!setting.isPresent()) {
			return new ArrayList<>();
		}

		Optional<String> empCode = Optional.empty();
		if (setting.get().getEmploymentUse() == NotUseAtr.USE) {
			empCode = require.employmentHistory(new CacheCarrier(), cid, sid, date).map(x -> x.getEmploymentCode());
		}

		Optional<CriterionAmount> criterionAmount = empCode.map(x -> {
			return require.getAmountForEmployment(cid, new EmploymentCode(x)).map(y -> y.getCriterionAmount());
		}).orElse(require.getAmountForCompany(cid).map(y -> y.getCriterionAmount()));

		return require.getHandlingOfCriterionAmount(cid).map(handling -> {
			return handling.getList().stream().map(x -> {
				return createAmountSetting(
						criterionAmount.map(y -> y.getMonthly().getCriterionAmountByNo(x.getFrameNo()))
								.orElse(Optional.empty()),
						criterionAmount.map(y -> y.getYearly().getCriterionAmountByNo(x.getFrameNo()))
								.orElse(Optional.empty()),
						x);
			}).collect(Collectors.toList());
		}).orElse(new ArrayList<EstimateAmountSetting>());
	}

	private static EstimateAmountSetting createAmountSetting(Optional<CriterionAmountByNo> monthOpt,
			Optional<CriterionAmountByNo> yearOpt, HandlingOfCriterionAmountByNo handlingAmountByNo) {
		return new EstimateAmountSetting(
				yearOpt.map(year -> new EstimateAmountDetail(year.getFrameNo().v(), year.getAmount().v(),
						handlingAmountByNo.getBackgroundColor().v())),
				monthOpt.map(month -> new EstimateAmountDetail(month.getFrameNo().v(), month.getAmount().v(),
						handlingAmountByNo.getBackgroundColor().v())));
	}

	public static interface Require {

		// ShareEmploymentAdapter.findEmploymentHistory
		// [RQ31]社員所属雇用履歴を取得
		Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate ymd);

		// CriterionAmountUsageSettingRepository.get
		// 目安利用区分を取得する
		Optional<CriterionAmountUsageSetting> getAmountUsageSet(String cid);

		// CriterionAmountForCompanyRepository
		Optional<CriterionAmountForCompany> getAmountForCompany(String cid);

		// CriterionAmountForEmploymentRepository
		Optional<CriterionAmountForEmployment> getAmountForEmployment(String cid, EmploymentCode employmentCd);

		// HandlingOfCriterionAmountRepository
		Optional<HandlingOfCriterionAmount> getHandlingOfCriterionAmount(String cid);
	}
}
