package nts.uk.ctx.at.record.dom.workrecord.monthcal.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;

/**
 * 実装：集計設定の取得（フレックス）
 * @author shuichu_ishida
 */
@Stateless
public class GetFlexAggrSetImpl implements GetFlexAggrSet {

	/** 労働時間と日数の設定の利用単位の設定 */
	@Inject
	private UsageUnitSettingRepository usageUnitSetRepo;
	/** 会社別 */
	@Inject
	private ComFlexMonthActCalSetRepository comSetRepo;
	/** 職場別 */
	@Inject
	private WkpFlexMonthActCalSetRepository wkpSetRepo;
	/** 雇用別 */
	@Inject
	private EmpFlexMonthActCalSetRepository empSetRepo;
	/** 社員別 */
	@Inject
	private ShaFlexMonthActCalSetRepository shaSetRepo;
	/** 職場情報の取得 */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	
	/** 取得 */
	@Override
	public Optional<FlexMonthWorkTimeAggrSet> get(String companyId, String employmentCd,
			String employeeId, GeneralDate criteriaDate) {
		
		// 利用単位　確認
		val usagaUnitSetOpt = this.usageUnitSetRepo.findByCompany(companyId);
		UsageUnitSetting usageUnitSet = new UsageUnitSetting(new CompanyId(companyId), false, false, false);
		if (usagaUnitSetOpt.isPresent()) usageUnitSet = usagaUnitSetOpt.get();
		
		// 社員別設定　確認
		if (usageUnitSet.isEmployee()){
			val shaSetOpt = this.shaSetRepo.find(companyId, employeeId);
			if (shaSetOpt.isPresent()) return Optional.of(shaSetOpt.get().getAggrSetting());
		}
		
		// 職場別設定　確認
		if (usageUnitSet.isWorkPlace()){
			
			// 所属職場を含む上位階層の職場IDを取得
			val workplaceIds = this.affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(
					companyId, employeeId, criteriaDate);
			
			for (val workplaceId : workplaceIds){
				val wkpSetOpt = this.wkpSetRepo.find(companyId, workplaceId);
				if (wkpSetOpt.isPresent()) return Optional.of(wkpSetOpt.get().getAggrSetting());
			}
		}
		
		// 雇用別設定　確認
		if (usageUnitSet.isEmployment()){
			val empSetOpt = this.empSetRepo.find(companyId, employmentCd);
			if (empSetOpt.isPresent()) return Optional.of(empSetOpt.get().getAggrSetting());
		}
		
		// 会社別設定　確認
		val comSetOpt = this.comSetRepo.find(companyId);
		if (comSetOpt.isPresent()) return Optional.of(comSetOpt.get().getAggrSetting());
		
		return Optional.empty();
	}
}
