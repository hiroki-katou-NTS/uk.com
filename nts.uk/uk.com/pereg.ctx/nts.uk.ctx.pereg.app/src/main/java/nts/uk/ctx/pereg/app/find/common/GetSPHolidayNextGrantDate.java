package nts.uk.ctx.pereg.app.find.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.export.GetCalcStartForNextLeaveGrant;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveAppSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.GrantDaysInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveGrantNextDateInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveGrantNextDateService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetSPHolidayNextGrantDate {
	
	
	@Inject
	private GetCalcStartForNextLeaveGrant getCalcStartForNextLeaveGrant;
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepository;
	
	@Inject
	private SpecialLeaveGrantNextDateService SpecialLeaveGrantNextDateService;
	
	/**
	 * 次回特休情報を取得する
	 * @param 社員ID sid
	 * @param 特別休暇コード specialCD
	 * @param 特休付与基準日 referDate
	 * @param 適用設定 appSet
	 * @param 特休付与テーブルコード grantTableCD
	 * @param 付与日数 grantedDays
	 * @param 入社年月日 entryDate NULL
	 * @param 退職年月日 retireDate NULL
	 * @param 年休付与基準日 year NULL
	 * @return
	 */
	public GeneralDate getSPHolidayGrantDate(SpecialleaveInformation param){
		
		GeneralDate entryDate = null;
		
		GeneralDate yearRefDate = null;
		
		// 次回休暇付与を計算する開始日を取得する
		GeneralDate baseDate = getCalcStartForNextLeaveGrant.algorithm(param.getSid());
		if (baseDate == null){
			return null;
		}
		
		// Set entry date
		if (param.getEntryDate() != null && baseDate.afterOrEquals(param.getEntryDate())) {
			entryDate = param.getEntryDate();
		} else {
			AffCompanyHistSharedImport affComHist = empEmployeeAdapter.GetAffComHisBySidAndBaseDate(param.getSid(),
					baseDate);
			entryDate = affComHist.getEntryDate().orElse(null);
			
			// ドメインモデル「所属会社履歴（社員別）」を取得し直し、入社年月日を取得する
			if (entryDate == null){
				AffCompanyHistSharedImport defaultValue = empEmployeeAdapter.GetAffComHisBySid(AppContexts.user().companyId(),param.getSid());
				entryDate = defaultValue.getEntryDate().orElse(null);
			}
		}
		
		if (entryDate == null){
			return null;
		}
		
		// Set 年休付与基準日
		if (param.getYearRefDate() == null && param.getSid() != null){
			// アルゴリズム「年休社員基本情報を取得する」を実行し、年休付与基準日を取得する
			Optional<AnnualLeaveEmpBasicInfo> annualBasicInfo = annLeaEmpBasicInfoRepository.get(param.getSid());
			
			if (annualBasicInfo.isPresent()){
				yearRefDate = annualBasicInfo.get().getGrantRule().getGrantStandardDate();
			}
		} else {
			yearRefDate = param.getYearRefDate();
		}
		
		if (yearRefDate == null){
			return null;
		}
		
		SpecialLeaveGrantNextDateInput inputParam = new SpecialLeaveGrantNextDateInput(AppContexts.user().companyId(),
				param.getSpLeaveCD(), param.getGrantDate(),
				EnumAdaptor.valueOf(param.getAppSet(), SpecialLeaveAppSetting.class),
				Optional.ofNullable(param.getGrantTable()), Optional.ofNullable(param.getGrantDays()), baseDate,
				entryDate, yearRefDate);
		Optional<GrantDaysInfor> grantDaysInfo = SpecialLeaveGrantNextDateService.getNumberOfGrantDays(inputParam);

		return grantDaysInfo.map(i->i.getYmd()).orElse(null);
	}
}