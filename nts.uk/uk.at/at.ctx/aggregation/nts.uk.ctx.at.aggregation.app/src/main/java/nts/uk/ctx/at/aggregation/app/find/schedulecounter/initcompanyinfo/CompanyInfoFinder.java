package nts.uk.ctx.at.aggregation.app.find.schedulecounter.initcompanyinfo;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;

import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoangnd
 *
 */
@Stateless
// 会社の初期情報を取得する
public class CompanyInfoFinder {

	@Inject
	private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;
	
	@Inject
	private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository;
	
	@Inject
	private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
	
	/**
	 * 初期情報を取得する
	 */
	public InitInfoDto getInitInfo() {
		String cid = AppContexts.user().companyId();
		// 1: call
		Optional<CriterionAmountUsageSetting> criOptional = criterionAmountUsageSettingRepository.get(cid);
		
		// 2: call
		EsimatedInfoDto esimatedInfoDto = this.getEstimatedInfo();
		
		return new InitInfoDto(
				esimatedInfoDto,
				criOptional.map(x -> BooleanUtils.toBoolean(x.getEmploymentUse().value)).orElse(null));
	}
	
	
	
	/**
	 * 目安金額情報を取得する
	 */
	public EsimatedInfoDto getEstimatedInfo() {
		String cid = AppContexts.user().companyId();
		// 会社の目安金額を取得する
		Optional<CriterionAmountForCompany> criOptional = criterionAmountForCompanyRepository.get(cid);
		
		// 目安金額の扱いを取得する
		Optional<HandlingOfCriterionAmount> hanOptional = handlingOfCriterionAmountRepository.get(cid);
		
		return new EsimatedInfoDto(
				criOptional.get()
					.getCriterionAmount()
					.getMonthly()
					.getList()
					.stream()
					.map(CriterionAmountByNoDto::setData)
					.collect(Collectors.toList()),
				criOptional.get()
					.getCriterionAmount()
					.getYearly()
					.getList()
					.stream()
					.map(CriterionAmountByNoDto::setData)
					.collect(Collectors.toList()),
				hanOptional.get()
					.getList()
					.stream()
					.map(x -> x.getFrameNo().v())
					.collect(Collectors.toList()));
	}
	
	
}
