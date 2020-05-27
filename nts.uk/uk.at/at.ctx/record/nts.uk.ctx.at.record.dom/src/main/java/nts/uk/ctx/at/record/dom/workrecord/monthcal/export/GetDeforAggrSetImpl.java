package nts.uk.ctx.at.record.dom.workrecord.monthcal.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;

/**
 * 実装：集計設定の取得（変形労働）
 * @author shuichu_ishida
 */
@Stateless
public class GetDeforAggrSetImpl implements GetDeforAggrSet {

	/** 労働時間と日数の設定の利用単位の設定 */
	@Inject
	private UsageUnitSettingRepository usageUnitSetRepo;
	/** 会社別 */
	@Inject
	private ComDeforLaborMonthActCalSetRepository comSetRepo;
	/** 職場別 */
	@Inject
	private WkpDeforLaborMonthActCalSetRepository wkpSetRepo;
	/** 雇用別 */
	@Inject
	private EmpDeforLaborMonthActCalSetRepository empSetRepo;
	/** 社員別 */
	@Inject
	private ShaDeforLaborMonthActCalSetRepository shaSetRepo;
	/** 職場情報の取得 */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	
//	/** 取得 */
//	@Override
//	public Optional<DeforWorkTimeAggrSet> get(String companyId, String employmentCd,
//			String employeeId, GeneralDate criteriaDate) {
//
//		// 利用単位　確認
//		UsageUnitSetting usageUnitSet = new UsageUnitSetting(new CompanyId(companyId), false, false, false);
//		val usagaUnitSetOpt = this.usageUnitSetRepo.findByCompany(companyId);
//		if (usagaUnitSetOpt.isPresent()) usageUnitSet = usagaUnitSetOpt.get();
//		
//		// 社員別設定　確認
//		val shaSetOpt = this.shaSetRepo.find(companyId, employeeId);
//		
//		// 会社別設定　確認
//		val comSetOpt = this.comSetRepo.find(companyId);
//		
//		return this.getCommon(companyId, employmentCd, employeeId, criteriaDate,
//				usageUnitSet, shaSetOpt, comSetOpt);
//	}

	/** 取得 */
	@Override
	public Optional<DeforWorkTimeAggrSet> get(String companyId, String employmentCd, String employeeId,
			GeneralDate criteriaDate, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		val require = new Require() {
			@Override
			public Optional<WkpDeforLaborMonthActCalSet> findWkpDeforLaborMonthActCalSet(String cid, String wkpId) {
				return wkpSetRepo.find(companyId, wkpId);
			}
			@Override
			public Optional<EmpDeforLaborMonthActCalSet> findEmpDeforLaborMonthActCalSet(String cid, String empCode) {
				return empSetRepo.find(companyId, employmentCd);
			}
		};
		
		val cacheCarrier = new CacheCarrier();
		
		return this.getRequire(require, cacheCarrier, companyId, employmentCd, employeeId, criteriaDate, companySets, employeeSets);
	}
	
	/** 取得 */
	@Override
	public Optional<DeforWorkTimeAggrSet> getRequire(Require require, CacheCarrier cacheCarrier, 
			String companyId, String employmentCd, String employeeId,
			GeneralDate criteriaDate, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {

		return this.getCommon(require, cacheCarrier, companyId, employmentCd, employeeId, criteriaDate,
				companySets.getUsageUnitSet(), employeeSets.getShaIrgSetOpt(), companySets.getComIrgSetOpt());
	}
	
	/**
	 * 取得共通処理
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param usageUnitSet 労働時間と日数の設定の利用単位の設定
	 * @param shaIrgSetOpt 変形労働社員別月別実績集計設定
	 * @param comIrgSetOpt 変形労働会社別月別実績集計設定
	 * @return 変形労働の法定内集計設定
	 */
	private Optional<DeforWorkTimeAggrSet> getCommon(
			Require require, CacheCarrier cachecarrier, 
			String companyId, String employmentCd, String employeeId, GeneralDate criteriaDate,
			UsageUnitSetting usageUnitSet, Optional<ShaDeforLaborMonthActCalSet> shaIrgSetOpt,
			Optional<ComDeforLaborMonthActCalSet> comIrgSetOpt){

		// 社員別設定　確認
		if (usageUnitSet.isEmployee()){
			if (shaIrgSetOpt.isPresent()) return Optional.of(shaIrgSetOpt.get().getAggrSetting());
		}
		
		// 職場別設定　確認
		if (usageUnitSet.isWorkPlace()){
			
			// 所属職場を含む上位階層の職場IDを取得
			val workplaceIds = this.affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(
					cachecarrier, companyId, employeeId, criteriaDate);
			
			for (val workplaceId : workplaceIds){
				val wkpSetOpt = require.findWkpDeforLaborMonthActCalSet(companyId, workplaceId);
				if (wkpSetOpt.isPresent()) return Optional.of(wkpSetOpt.get().getAggrSetting());
			}
		}
		
		// 雇用別設定　確認
		if (usageUnitSet.isEmployment()){
			val empSetOpt = require.findEmpDeforLaborMonthActCalSet(companyId, employmentCd);
			if (empSetOpt.isPresent()) return Optional.of(empSetOpt.get().getAggrSetting());
		}
		
		// 会社別設定　確認
		if (comIrgSetOpt.isPresent()) return Optional.of(comIrgSetOpt.get().getAggrSetting());
		
		return Optional.empty();
	}
	
	public static interface Require{
		//wkpSetRepo.find(companyId, workplaceId);
		Optional<WkpDeforLaborMonthActCalSet> findWkpDeforLaborMonthActCalSet(String cid, String wkpId);
//		empSetRepo.find(companyId, employmentCd);
		Optional<EmpDeforLaborMonthActCalSet> findEmpDeforLaborMonthActCalSet(String cid, String empCode);
	}
}
