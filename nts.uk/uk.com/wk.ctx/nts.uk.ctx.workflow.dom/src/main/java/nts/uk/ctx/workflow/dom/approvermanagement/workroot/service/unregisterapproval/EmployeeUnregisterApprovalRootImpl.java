package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.AppTypeName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.service.ApprovalSettingService;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeUnregisterApprovalRootImpl implements EmployeeUnregisterApprovalRoot {
	@Inject
	private EmployeeOfApprovalRoot employeeOfApprovalRoot;
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Inject
	private EmployeeAdapter empInfor;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApprovalSettingService approvalSettingService;
	@Inject
	private ManagedParallelWithContext parallel;
	
	@Override
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate, int sysAtr,
			List<Integer> lstNotice, List<String> lstEvent, List<AppTypeName> lstName) {
		// Imported(??????)???????????????????????????
		List<EmployeeImport> lstEmps = empInfor.getEmployeesAtWorkByBaseDate(companyId, baseDate);
		// ??????????????????????????????????????????????????????
		List<EmployeeUnregisterOutput> lstUnRegister = new ArrayList<>();
		// ??????????????????(data = 0)
		if (CollectionUtil.isEmpty(lstEmps)) {
			return lstUnRegister;
		}

		// ??????????????????????????????????????????
		ApproverRegisterSet approverRegisterSet = approvalSettingService.getSettingUseUnit(companyId, sysAtr);

		// ????????????????????????????????????????????????
		List<CompanyApprovalRoot> lstComs = new ArrayList<>();
		if (approverRegisterSet.getCompanyUnit() == UseClassification.DO_USE) {
			lstComs.addAll(comRootRepository.findByBaseDate(companyId, baseDate, sysAtr)
					.stream().filter(i -> sysAtr == SystemAtr.WORK.value
							|| (i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON
							|| i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION))
					.collect(Collectors.toList()));
		}

		// ??????????????????????????????????????????????????????
		List<WorkplaceApprovalRoot> lstWps = new ArrayList<>();
		if (approverRegisterSet.getWorkplaceUnit() == UseClassification.DO_USE) {
			lstWps.addAll(wpRootRepository.findAllByBaseDate(companyId, baseDate, sysAtr)
					.stream().filter(i -> sysAtr == SystemAtr.WORK.value
							|| (i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON
							|| i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION))
					.collect(Collectors.toList()));
		}

		// ????????????????????????????????????????????????
		List<PersonApprovalRoot> lstPss = new ArrayList<>();
		if (approverRegisterSet.getEmployeeUnit() == UseClassification.DO_USE) {
			lstPss.addAll(psRootRepository.findAllByBaseDate(companyId, baseDate, sysAtr)
					.stream().filter(i -> sysAtr == SystemAtr.WORK.value
							|| (i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON
							|| i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION))
					.collect(Collectors.toList()));
		}

		parallel.forEach(lstEmps, employee -> {
			if(sysAtr == SystemAtr.WORK.value) { // ?????????????????????????????????
				// <<Enum>>?????????????????????????????????????????????
				for (ApplicationType appType : ApplicationType.values()) {
					UnregisteredApprovalCheckResult result = employeeOfApprovalRoot.lstEmpApprovalRoot(
							companyId,
							lstComs, lstWps, lstPss,
							employee,
							baseDate,
							sysAtr,
							EmploymentRootAtr.APPLICATION,
							appType.value.toString()
					);
					if (result != null) {
						lstUnRegister.add(new EmployeeUnregisterOutput(
								employee.getSId(),
								appType.nameId,
								result.getRoute() == null ? 0 : 1,
								result.getRoute(),
								result.getCommon(),
								result.getWorkplaceId(),
								result.getErrorContents()
						));
					}
				}
				// <<Enum>>?????????????????????????????????????????????
				for (ConfirmationRootType conf : ConfirmationRootType.values()) {
					UnregisteredApprovalCheckResult result = employeeOfApprovalRoot.lstEmpApprovalRoot(
							companyId,
							lstComs, lstWps, lstPss,
							employee,
							baseDate,
							sysAtr,
							EmploymentRootAtr.CONFIRMATION,
							conf.value.toString()
					);
					if (result != null) {
						lstUnRegister.add(new EmployeeUnregisterOutput(
								employee.getSId(),
								conf.nameId,
								result.getRoute() == null ? 0 : 1,
								result.getRoute(),
								result.getCommon(),
								result.getWorkplaceId(),
								result.getErrorContents()
						));
					}
				}
			} else { // ?????????????????????????????????
				// List?????????ID?????????????????????????????????????????????
				for (Integer notice : lstNotice) {
					UnregisteredApprovalCheckResult result = employeeOfApprovalRoot.lstEmpApprovalRoot(
							companyId,
							lstComs, lstWps, lstPss,
							employee,
							baseDate,
							sysAtr,
							EmploymentRootAtr.NOTICE,
							notice.toString()
					);
					if (result != null) {
						lstUnRegister.add(new EmployeeUnregisterOutput(
								employee.getSId(),
								findName(lstName, notice, null, EmploymentRootAtr.NOTICE.value),
								result.getRoute() == null ? 0 : 1,
								result.getRoute(),
								result.getCommon(),
								result.getWorkplaceId(),
								result.getErrorContents()
						));
					}
				}
				// List????????????????????????ID??????????????????????????????
				for (String event : lstEvent) {
					UnregisteredApprovalCheckResult result = employeeOfApprovalRoot.lstEmpApprovalRoot(
							companyId,
							lstComs, lstWps, lstPss,
							employee,
							baseDate,
							sysAtr,
							EmploymentRootAtr.BUS_EVENT,
							event
					);
					if (result != null) {
						lstUnRegister.add(new EmployeeUnregisterOutput(
								employee.getSId(),
								findName(lstName, null, event, EmploymentRootAtr.BUS_EVENT.value),
								result.getRoute() == null ? 0 : 1,
								result.getRoute(),
								result.getCommon(),
								result.getWorkplaceId(),
								result.getErrorContents()
						));
					}
				}
			}
		});

		return lstUnRegister;
	}

	private String findName(List<AppTypeName> lstName, Integer notice, String event, int empR) {
		if(empR == EmploymentRootAtr.NOTICE.value) {
			for(AppTypeName app :  lstName) {
				if(app.getEmpRAtr() == empR && Integer.valueOf(app.getValue()).equals(notice)) {
					return app.getName();
				}
			}
			return "??????????????????";
		}
		for(AppTypeName app :  lstName) {
			if(app.getEmpRAtr() == empR && app.getValue().equals(event)) {
				return app.getName();
			}
		}
		return "??????????????????";
	}

	@Override
	public Map<String, List<String>> lstEmplUnregister(String cid, DatePeriod period, List<String> lstSid) {
		Map<String, List<String>> mapResult = new HashMap<String, List<String>>();
		if(lstSid.isEmpty()) return mapResult;
		// ????????????????????????????????????????????????????????????????????????(l???y th??ng tin domain????????????????????????????????????)
		List<Integer> lstRootAtr = new ArrayList<>();
		lstRootAtr.add(EmploymentRootAtr.COMMON.value);
		lstRootAtr.add(EmploymentRootAtr.APPLICATION.value);
		List<CompanyApprovalRoot> lstComs = comRootRepository.findByDatePeriod(cid,
				period, SystemAtr.WORK, lstRootAtr);

		List<CompanyApprovalRoot> comInfoCommon = lstComs.stream()
				.filter(x -> x.getApprRoot().getEmploymentRootAtr().value == EmploymentRootAtr.COMMON.value)
				.collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(comInfoCommon)) {
			for (CompanyApprovalRoot companyApprovalRoot : comInfoCommon) {
				List<ApprovalPhase> lstAppPhase = repoAppPhase.getAllApprovalPhasebyCode(companyApprovalRoot.getApprovalId());
				if(!lstAppPhase.isEmpty()){
					return mapResult;
				}
			}
		}
		// ????????????????????????????????????????????????????????????????????????????????????(kh??ng c?? th??ng tin ???????????????????????????????????? c???a ?????????????????????l?? common)
		// ????????????????????????????????????????????????????????????????????????(l???y th??ng tin domain ????????????????????????????????????)
		List<WorkplaceApprovalRoot> lstWps = wpRootRepository.getAppRootByDatePeriod(cid,
				period, SystemAtr.WORK, lstRootAtr);
		// ????????????????????????????????????????????????????????????????????????(l???y th??ng tin domain ????????????????????????????????????)
		List<PersonApprovalRoot> lstPss = psRootRepository.getAppRootByDatePeriod(cid,
							period, SystemAtr.WORK, lstRootAtr);
		for(String sid: lstSid) {
			List<String> appTypes = new ArrayList<>();
			EmployeeImport empInfor = new EmployeeImport();
			empInfor.setCompanyId(cid);
			empInfor.setSId(sid);
			for(GeneralDate date : period.datesBetween()) {
				List<CompanyApprovalRoot> lstComsDate = lstComs.stream()
						.filter(com -> com.getApprRoot().getHistoryItems()
								.stream()
								.filter(his -> his.getDatePeriod().start().beforeOrEquals(date)
										&& his.getDatePeriod().end().afterOrEquals(date)).collect(Collectors.toList()).size() > 0)
						.collect(Collectors.toList());
				List<WorkplaceApprovalRoot> lstWpsDate = lstWps.stream()
						.filter(com -> com.getApprRoot().getHistoryItems()
								.stream()
								.filter(his -> his.getDatePeriod().start().beforeOrEquals(date)
										&& his.getDatePeriod().end().afterOrEquals(date)).collect(Collectors.toList()).size() > 0)
						.collect(Collectors.toList());
				List<PersonApprovalRoot> lstPssDate = lstPss.stream()
						.filter(com -> com.getEmployeeId().equals(sid) && com.getApprRoot().getHistoryItems()
								.stream()
								.filter(his -> his.getDatePeriod().start().beforeOrEquals(date)
										&& his.getDatePeriod().end().afterOrEquals(date)).collect(Collectors.toList()).size() > 0)
						.collect(Collectors.toList());
				//APPLICATION
				for (ApplicationType appType : ApplicationType.values()) {
					// ??????????????????????????????????????????????????????(l???y d??? li???u approve route c???a ?????i t?????ng ????n xin c???a nh??n vi??n)
					UnregisteredApprovalCheckResult result = employeeOfApprovalRoot.lstEmpApprovalRoot(
							cid,
							lstComsDate, lstWpsDate, lstPssDate,
							empInfor,
							date, SystemAtr.WORK.value,
							EmploymentRootAtr.APPLICATION,
							appType.value.toString()
					);
					// ?????????????????????????????????????????????????????????(th??m v??o ?????i t?????ng ch??a c??i ?????t approve route ????? output)
					if (result != null && result.getRoute() == null) {
						appTypes.add(appType.nameId);
					}
				}
			}
			if(!appTypes.isEmpty()) {
				mapResult.put(sid, appTypes.stream().distinct().collect(Collectors.toList()));
			}
		}
		return mapResult;
	}
}
