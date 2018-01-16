package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;

/**
 * 月別実績集計設定を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetAggrSettingMonthlyImpl implements GetAggrSettingMonthly {
	
	/** 会社月別実績集計設定 */
	@Inject
	private AggrSettingMonthlyForCompanyRepository aggrSettingMonthlyForCompanyRepository;
	/** 職場月別実績集計設定 */
	@Inject
	private AggrSettingMonthlyForWorkplaceRepository aggrSettingMonthlyForWorkplaceRepository;
	/** 雇用月別実績集計設定 */
	@Inject
	private AggrSettingMonthlyForEmploymentRepository aggrSettingMonthlyForEmploymentRepository;
	/** 社員月別実績集計設定 */
	@Inject
	private AggrSettingMonthlyForSyainRepository aggrSettingMonthlyForSyainRepository;
	
	/** 取得 */
	@Override
	public Optional<AggrSettingMonthly> get(String companyId, String workplaceId, String employmentCd, String employeeId){

		// 社員別設定　確認
		val aggrSettingMonthlyForSyain = this.aggrSettingMonthlyForSyainRepository.find(companyId, employeeId);
		if (aggrSettingMonthlyForSyain.isPresent()) {
			return Optional.of(AggrSettingMonthly.of(
					aggrSettingMonthlyForSyain.get().getRegularWork(),
					aggrSettingMonthlyForSyain.get().getIrregularWork(),
					aggrSettingMonthlyForSyain.get().getFlexWork()));
		}
		
		// 雇用別設定　確認
		val aggrSettingMonthlyForEmployment = this.aggrSettingMonthlyForEmploymentRepository.find(companyId, employmentCd);
		if (aggrSettingMonthlyForEmployment.isPresent()) {
			return Optional.of(AggrSettingMonthly.of(
					aggrSettingMonthlyForEmployment.get().getRegularWork(),
					aggrSettingMonthlyForEmployment.get().getIrregularWork(),
					aggrSettingMonthlyForEmployment.get().getFlexWork()));
		}
		
		// 職場別設定　確認　（所属上位含む）
		//*****（未）　上位の組織一覧を取るsharedのアルゴリズムが必要。コードをどう対応するか、確認要。
		val aggrSettingMonthlyForWorkplace = this.aggrSettingMonthlyForWorkplaceRepository.find(companyId, workplaceId);
		if (aggrSettingMonthlyForWorkplace.isPresent()) {
			return Optional.of(AggrSettingMonthly.of(
					aggrSettingMonthlyForWorkplace.get().getRegularWork(),
					aggrSettingMonthlyForWorkplace.get().getIrregularWork(),
					aggrSettingMonthlyForWorkplace.get().getFlexWork()));
		}
		
		// 会社別設定　確認
		val aggrSettingMonthlyForCompany = this.aggrSettingMonthlyForCompanyRepository.find(companyId);
		if (aggrSettingMonthlyForCompany.isPresent()) {
			return Optional.of(AggrSettingMonthly.of(
					aggrSettingMonthlyForCompany.get().getRegularWork(),
					aggrSettingMonthlyForCompany.get().getIrregularWork(),
					aggrSettingMonthlyForCompany.get().getFlexWork()));
		}
		
		// いずれも該当がなかった時、設定値を空にする
		return Optional.empty();
	}
}
