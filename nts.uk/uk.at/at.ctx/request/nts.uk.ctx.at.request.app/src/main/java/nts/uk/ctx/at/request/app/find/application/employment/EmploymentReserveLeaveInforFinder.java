package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaveInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.TmpRsvLeaveMngImport;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentReserveLeaveInforFinder {

	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;

	@Inject
	private ReserveLeaveManagerApdater rsvLeaManaApdater;

	@Inject
	private HolidayApplicationSettingRepository repoHdAppSet;

	@Inject
	private RemainNumberTempRequireService requireService;

	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL029_積休ダイアログ.アルゴリズム.1.積休ダイアログ作成（起動用）.1.積休ダイアログ作成（起動用）
	 * @param param
	 * @return
	 */
	public EmpRsvLeaveInforDto getEmploymentReserveLeaveInfor(ParamEmpRsvLeave param) {
		//2.社員リスト情報作成 - RequestList228
		List<EmployeeInfoImport> lstEmpInfor = this.atEmployeeAdapter.getByListSID(param.getListSID());
		String employeeCode = "";
		String employeeName = "";
		String yearResigName = "";
		// 積立年休管理区分
		boolean isRetentionManage = true;
		EmployeeInfoImport firstEmp = lstEmpInfor.get(0);
		RsvLeaManagerImportDto rsvLeaManaImp = null;
		if (!CollectionUtil.isEmpty(lstEmpInfor)) {
			employeeCode = firstEmp.getScd();
			employeeName = firstEmp.getBussinessName();
		}
		if (param.getInputDate() != null) {
			GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成 - RequestList201
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(firstEmp.getSid(), referDate);
			if (!rsvLeaManaImport.isPresent()) {
				return new EmpRsvLeaveInforDto(lstEmpInfor, rsvLeaManaImp, employeeCode, employeeName, yearResigName, isRetentionManage);
			}
			RsvLeaveInfoImport reserveLeaveInfo = rsvLeaManaImport.get().getReserveLeaveInfo();
			List<RsvLeaGrantRemainingImportDto> grantRemainingList = rsvLeaManaImport.get().getGrantRemainingList().stream()
				.map(item-> new RsvLeaGrantRemainingImportDto(
					item.getGrantDate(),
					item.getDeadline(),
					item.getGrantNumber(),
					item.getUsedNumber(),
					item.getRemainingNumber(),
					false
				))
				.collect(Collectors.toList());
			List<TmpRsvLeaveMngImport> tmpManageList = rsvLeaManaImport.get().getTmpManageList();
			rsvLeaManaImp = new RsvLeaManagerImportDto(reserveLeaveInfo, grantRemainingList, tmpManageList);
		}
		//ドメインモデル「休暇申請設定」を取得する (Vacation application setting)
		Optional<HolidayApplicationSetting> hdAppSet = repoHdAppSet.findSettingByCompanyId(AppContexts.user().companyId());
		if (hdAppSet.isPresent()) {
			yearResigName = hdAppSet.get().getHolidayApplicationTypeDisplayName()
					.stream()
					.filter(i -> i.getHolidayApplicationType() == HolidayAppType.YEARLY_RESERVE)
					.findFirst()
					.map(i -> i.getDisplayName().v())
					.orElse("");
		}
		return new EmpRsvLeaveInforDto(lstEmpInfor, rsvLeaManaImp, employeeCode, employeeName, yearResigName, isRetentionManage);
	}
	
	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL029_積休ダイアログ.アルゴリズム.5.積休ダイアログ作成（社員選択用）.5.積休ダイアログ作成（社員選択用）
	 * @param param
	 * @return
	 */
	public EmpRsvLeaveInforDto getByEmloyee(ParamEmpRsvLeave param) {
		RsvLeaManagerImportDto rsvLeaManaImp = null;
		val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
		GeneralDate referDate = GeneralDate.fromString(param.getInputDate(), "yyyy/MM/dd");
		String cId = AppContexts.user().companyId();
		String sId = param.getListSID().get(0);
		//10-4.積立年休の設定を取得する
		boolean isRetentionManage = AbsenceTenProcess.getSetForYearlyReserved(require, cacheCarrier, cId, sId, referDate);
		if (isRetentionManage) {
			//3.積立年休取得日一覧の作成, 4.当月以降積休使用状況作成
			Optional<RsvLeaManagerImport> rsvLeaManaImport = rsvLeaManaApdater.getRsvLeaveManager(param.getListSID().get(0), referDate);
			if (!rsvLeaManaImport.isPresent()) {
				return new EmpRsvLeaveInforDto(new ArrayList<>(), rsvLeaManaImp, "", "", "", isRetentionManage);
			}

			// 社員に対応する締め期間を取得する
			DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, sId, referDate);
			RsvLeaveInfoImport reserveLeaveInfo = rsvLeaManaImport.get().getReserveLeaveInfo();
			List<RsvLeaGrantRemainingImportDto> grantRemainingList = rsvLeaManaImport.get().getGrantRemainingList().stream()
				.map(item-> new RsvLeaGrantRemainingImportDto(
					item.getGrantDate(),
					item.getDeadline(),
					item.getGrantNumber(),
					item.getUsedNumber(),
					item.getRemainingNumber(),
					(GeneralDate.fromString(item.getDeadline(), "yyyy/MM/dd")).beforeOrEquals(closingPeriod.end())
				))
				.collect(Collectors.toList());
			List<TmpRsvLeaveMngImport> tmpManageList = rsvLeaManaImport.get().getTmpManageList();
			rsvLeaManaImp = new RsvLeaManagerImportDto(reserveLeaveInfo, grantRemainingList, tmpManageList);
		}
		return new EmpRsvLeaveInforDto(new ArrayList<>(), rsvLeaManaImp, "", "", "", isRetentionManage);
	}
}
