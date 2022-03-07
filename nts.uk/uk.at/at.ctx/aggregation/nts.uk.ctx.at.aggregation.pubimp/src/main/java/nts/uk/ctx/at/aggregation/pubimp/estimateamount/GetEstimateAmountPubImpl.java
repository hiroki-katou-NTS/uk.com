package nts.uk.ctx.at.aggregation.pubimp.estimateamount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.export.GetEstimateAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;
import nts.uk.ctx.at.aggregation.pub.estimateamount.GetEstimateAmountPub;
import nts.uk.ctx.at.aggregation.pub.estimateamount.export.EstimateAmountDetailExport;
import nts.uk.ctx.at.aggregation.pub.estimateamount.export.EstimateAmountSettingExport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Stateless
public class GetEstimateAmountPubImpl implements GetEstimateAmountPub {

	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;

	@Inject
	private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;

	@Inject
	private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository;

	@Inject
	private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository;

	@Override
	public List<EstimateAmountSettingExport> getData(String cid, String sid, GeneralDate date) {
		RequireImpl impl = new RequireImpl();
		return GetEstimateAmount
				.getData(impl, cid, sid,
						date)
				.stream().map(
						x -> new EstimateAmountSettingExport(x.getAnnualAmountDetail()
								.map(y -> new EstimateAmountDetailExport(y.getAmountFrameNo(), y.getAmount(),
										y.getTreatmentByFrameColor())),
								x.getMonthlyAmountDetail().map(y -> new EstimateAmountDetailExport(y.getAmountFrameNo(),
										y.getAmount(), y.getTreatmentByFrameColor()))))
				.collect(Collectors.toList());
	}

	@AllArgsConstructor
	public class RequireImpl implements GetEstimateAmount.Require {

		@Override
		public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate ymd) {
			return shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, ymd);
		}

		@Override
		public Optional<CriterionAmountUsageSetting> getAmountUsageSet(String cid) {
			return criterionAmountUsageSettingRepository.get(cid);
		}

		@Override
		public Optional<CriterionAmountForCompany> getAmountForCompany(String cid) {
			return criterionAmountForCompanyRepository.get(cid);
		}

		@Override
		public Optional<CriterionAmountForEmployment> getAmountForEmployment(String cid, EmploymentCode employmentCd) {
			return criterionAmountForEmploymentRepository.get(cid, employmentCd);
		}

		@Override
		public Optional<HandlingOfCriterionAmount> getHandlingOfCriterionAmount(String cid) {
			return handlingOfCriterionAmountRepository.get(cid);
		}
	}
}
