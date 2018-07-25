package nts.uk.ctx.at.record.dom.workrecord.monthcal.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;

/**
 * 実装：集計設定の取得（通常勤務）
 * @author shuichu_ishida
 */
@Stateless
public class GetRegularAggrSetImpl implements GetRegularAggrSet {

	/** 労働時間と日数の設定の利用単位の設定 */
	@Inject
	private UsageUnitSettingRepository usageUnitSetRepo;
	/** 会社別 */
	@Inject
	private ComRegulaMonthActCalSetRepository comSetRepo;
	/** 職場別 */
	@Inject
	private WkpRegulaMonthActCalSetRepository wkpSetRepo;
	/** 雇用別 */
	@Inject
	private EmpRegulaMonthActCalSetRepository empSetRepo;
	/** 社員別 */
	@Inject
	private ShaRegulaMonthActCalSetRepository shaSetRepo;
	/** 職場情報の取得 */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	
	/** 取得 */
	@Override
	public Optional<RegularWorkTimeAggrSet> get(String companyId, String employmentCd,
			String employeeId, GeneralDate criteriaDate) {
		
		// 利用単位　確認
		UsageUnitSetting usageUnitSet = new UsageUnitSetting(new CompanyId(companyId), false, false, false);
		val usagaUnitSetOpt = this.usageUnitSetRepo.findByCompany(companyId);
		if (usagaUnitSetOpt.isPresent()) usageUnitSet = usagaUnitSetOpt.get();
		
		// 社員別設定　確認
		val shaSetOpt = this.shaSetRepo.find(companyId, employeeId);
		
		// 会社別設定　確認
		val comSetOpt = this.comSetRepo.find(companyId);
		
		return this.getCommon(companyId, employmentCd, employeeId, criteriaDate,
				usageUnitSet, shaSetOpt, comSetOpt);
	}
	
	/** 取得 */
	@Override
	public Optional<RegularWorkTimeAggrSet> get(String companyId, String employmentCd, String employeeId,
			GeneralDate criteriaDate, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		
		return this.getCommon(companyId, employmentCd, employeeId, criteriaDate,
				companySets.getUsageUnitSet(), employeeSets.getShaRegSetOpt(), companySets.getComRegSetOpt());
	}
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param usageUnitSet 労働時間と日数の設定の利用単位の設定
	 * @param shaRegSetOpt 通常勤務社員別月別実績集計設定
	 * @param comRegSetOpt 通常勤務会社別月別実績集計設定
	 * @return 通常勤務の法定内集計設定
	 */
	private Optional<RegularWorkTimeAggrSet> getCommon(
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate,
			UsageUnitSetting usageUnitSet, Optional<ShaRegulaMonthActCalSet> shaRegSetOpt,
			Optional<ComRegulaMonthActCalSet> comRegSetOpt){
		
		// 社員別設定　確認
		if (usageUnitSet.isEmployee()){
			if (shaRegSetOpt.isPresent()) return Optional.of(shaRegSetOpt.get().getRegulaAggrSetting());
		}
		
		// 職場別設定　確認
		if (usageUnitSet.isWorkPlace()){
			
			// 所属職場を含む上位階層の職場IDを取得
			val workplaceIds = this.affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(
					companyId, employeeId, criteriaDate);
			
			for (val workplaceId : workplaceIds){
				val wkpSetOpt = this.wkpSetRepo.find(companyId, workplaceId);
				if (wkpSetOpt.isPresent()) return Optional.of(wkpSetOpt.get().getRegulaAggrSetting());
			}
		}
		
		// 雇用別設定　確認
		if (usageUnitSet.isEmployment()){
			val empSetOpt = this.empSetRepo.find(companyId, employmentCd);
			if (empSetOpt.isPresent()) return Optional.of(empSetOpt.get().getRegulaAggrSetting());
		}
		
		// 会社別設定　確認
		if (comRegSetOpt.isPresent()) return Optional.of(comRegSetOpt.get().getRegulaAggrSetting());
		
		return Optional.empty();
	}
}
