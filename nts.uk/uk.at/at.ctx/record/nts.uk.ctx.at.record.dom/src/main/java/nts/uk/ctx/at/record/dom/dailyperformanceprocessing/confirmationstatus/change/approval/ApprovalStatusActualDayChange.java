package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.approval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.company.StatusOfEmployeeExport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalStatus;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalActionByEmpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ReleasedProprietyDivision;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ModeData;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmInfoResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.InformationMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.finddata.IFindDataDCRecord;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author thanhnx
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApprovalStatusActualDayChange {

	@Inject
	private IFindDataDCRecord iFindDataDCRecord;

	@Inject
	private ApprovalInfoAcqProcess approvalInfoAcqProcess;

	// [No.585]?????????????????????????????????????????????NEW???
	public List<ApprovalStatusActualResult> processApprovalStatus(String companyId, String empTarget,
			List<String> employeeIds, Optional<DatePeriod> periodOpt, Optional<YearMonth> yearMonthOpt, int mode) {
		return processApprovalStatus(companyId, empTarget, employeeIds, periodOpt, yearMonthOpt, mode, true);
	}
	
	public List<ApprovalStatusActualResult> processApprovalStatus(String companyId, String empTarget,
			List<String> employeeIds, Optional<DatePeriod> periodOpt, Optional<YearMonth> yearMonthOpt, int mode,
			boolean clearState) {
		if(clearState) {
			iFindDataDCRecord.clearAllStateless();
		}
		// ?????????????????????????????????????????????????????????????????????
		Optional<ApprovalProcessingUseSetting> optApprovalUse = iFindDataDCRecord.findApprovalByCompanyId(companyId);
		if (!optApprovalUse.isPresent() || !optApprovalUse.get().getUseDayApproverConfirm())
			return Collections.emptyList();

		List<ConfirmInfoResult> confirmInfoResults = approvalInfoAcqProcess.getApprovalInfoAcp(companyId, empTarget,
				employeeIds, periodOpt, yearMonthOpt);
		if(clearState) {
			iFindDataDCRecord.clearAllStateless();
		}
		if (confirmInfoResults.isEmpty())
			return Collections.emptyList();
		// ???????????????????????????????????????????????????????????????????????????
		Optional<IdentityProcessUseSet> optIndentity = iFindDataDCRecord.findIdentityByKey(companyId);
		return processApprovalStatusCommon(companyId, empTarget, employeeIds, confirmInfoResults, optIndentity,
				optApprovalUse, mode);
	}

	public List<ApprovalStatusActualResult> processApprovalStatusCommon(String companyId, String tartgetEmp,
			List<String> employeeIds, List<ConfirmInfoResult> approvalInfoResults,
			Optional<IdentityProcessUseSet> optIndentity, Optional<ApprovalProcessingUseSetting> approvalUseSettingOpt,
			int mode) {
		List<ApprovalStatusActualResult> resultResult = new ArrayList<>();
		Optional<ApprovalProcessingUseSetting> optApprovalUse = approvalUseSettingOpt.isPresent()
				? approvalUseSettingOpt
				: iFindDataDCRecord.findApprovalByCompanyId(companyId);
		// ??????????????????????????????????????????????????????
		// List<StatusOfEmployeeExport> statusOfEmps =
		// approvalInfoResultAll.getStatusOfEmps();
		approvalInfoResults.stream().forEach(approvalInfoResult -> {
			// statusOfEmps.stream().forEach(statusOfEmp -> {
			StatusOfEmployeeExport statusOfEmp = approvalInfoResult.getStatusOfEmp();
			List<ApprovalStatusActualResult> resultPart = new ArrayList<>(), resultPart2 = new ArrayList<>();
			List<DatePeriod> lstDatePeriod = statusOfEmp.getListPeriod();
			String employeeId = statusOfEmp.getEmployeeId();
			Map<Pair<String, GeneralDate>, Pair<ApprovalStatus, ApproverEmployeeState>> mapApprovalRootBySId = new HashMap<>();
			lstDatePeriod.stream().forEach(datePeriod -> {

				// ????????????Imported???????????????????????????????????????????????????????????????????????????????????????
				// List<ApproveRootStatusForEmpImport> lstApprovalStatus = approvalStatusAdapter
				// .getApprovalByListEmplAndListApprovalRecordDateNew(datePeriod.datesBetween(),
				// Arrays.asList(employeeId), 1);
				List<ApproveRootStatusForEmpImport> lstApprovalStatus = approvalInfoResult.getInformationDay().getLstApprovalDayStatus();
				// ????????????Imported???????????????????????????????????????????????????
				List<ApprovalRootOfEmployeeImport> lstApprovalRootDay = approvalInfoResult.getInformationDay().getLstApprovalRootDaily();
				Map<Pair<String, GeneralDate>, Pair<ApprovalStatus, ApproverEmployeeState>> mapApprovalRoot = lstApprovalRootDay
						.stream().flatMap(x -> x.getApprovalRootSituations().stream())
						.collect(Collectors.toMap(x -> Pair.of(x.getTargetID(), x.getAppDate()),
								x -> Pair.of(x.getApprovalStatus(), x.getApprovalAtr()), (x, y) -> x));
				mapApprovalRootBySId.putAll(mapApprovalRoot);

				if (mode == ModeData.NORMAL.value) {
					// set ??????????????? ???????????????????????????????????????????????????????????????
					lstApprovalStatus.stream().forEach(x -> {
						if (x.getApprovalStatus() == ApprovalStatusForEmployee.UNAPPROVED) {
							resultPart2.add(new ApprovalStatusActualResult(employeeId, x.getAppDate(), false, false));
						} else {
							resultPart2.add(new ApprovalStatusActualResult(employeeId, x.getAppDate(), false, true));
						}
						;
					});
					// ApprovalActionByEmpl
					resultPart2.stream().forEach(x -> {
						val temp = mapApprovalRoot.get(Pair.of(x.getEmployeeId(), x.getDate()));
						if (temp != null && temp.getLeft().getApprovalActionByEmpl() == ApprovalActionByEmpl.APPROVALED)
							x.updateApprovalStatus(true);
					});

				} else {
					Map<Pair<String, GeneralDate>, ApproveRootStatusForEmpImport> mapApprovalStatus = lstApprovalStatus
							.stream().collect(Collectors.toMap(x -> Pair.of(x.getEmployeeID(), x.getAppDate()), x -> x,
									(x, y) -> x));
					if (!lstApprovalRootDay.isEmpty()) {
						lstApprovalRootDay.stream().flatMap(x -> x.getApprovalRootSituations().stream())
								.filter(x -> x.getTargetID().equals(employeeId)).forEach(x -> {
									if (x.getApprovalStatus()
											.getApprovalActionByEmpl() == ApprovalActionByEmpl.APPROVALED) {
										resultPart2.add(new ApprovalStatusActualResult(employeeId, x.getAppDate(), true,
												false));
									} else {
										resultPart2.add(new ApprovalStatusActualResult(employeeId, x.getAppDate(),
												false, false));
									}
								});
					}

					resultPart2.stream().forEach(x -> {
						val temp = mapApprovalStatus.get(Pair.of(x.getEmployeeId(), x.getDate()));
						if (temp != null && temp.getApprovalStatus() != ApprovalStatusForEmployee.UNAPPROVED)
							x.setStatusNormal(true);
					});

				}

				Optional<IdentityProcessUseSet> identitySetOpt = optIndentity.isPresent() ? optIndentity
						: iFindDataDCRecord.findIdentityByKey(companyId);
				if (!identitySetOpt.isPresent() || !identitySetOpt.get().isUseConfirmByYourself()) {
					resultPart.addAll(updatePermissionCheckWithCondition(resultPart2, true, mapApprovalRoot));
				} else {
					// Acquire the domain model "identification of the day"
					List<Identification> lstIdentity = approvalInfoResult.getInformationDay().getIndentities();
					List<GeneralDate> dateTemps = lstIdentity.stream().map(x -> x.getProcessingYmd())
							.collect(Collectors.toList());

					val temp1 = updatePermissionCheckWithCondition(resultPart2.stream()
							.filter(x -> !dateTemps.contains(x.getDate())).collect(Collectors.toList()), false,
							mapApprovalRoot);
					val temp2 = updatePermissionCheckWithCondition(resultPart2.stream()
							.filter(x -> dateTemps.contains(x.getDate())).collect(Collectors.toList()), true,
							mapApprovalRoot);
					resultPart.addAll(temp1);
					resultPart.addAll(temp2);
				}

			});

			if (!optApprovalUse.isPresent() || !optApprovalUse.get().getUseMonthApproverConfirm()) {
				updatePermissionRelease(resultPart, true);
			} else {
				List<InformationMonth> informationMonths = approvalInfoResult.getInformationMonths();
				List<ApproveRootStatusForEmpImport> lstApprovalStatus = approvalInfoResult.getInformationMonths()
						.stream().flatMap(x -> x.getLstApprovalMonthStatus().stream()).collect(Collectors.toList());
				for(InformationMonth informationMonth : informationMonths) {
//				for (AggrPeriodEachActualClosure mergePeriodClr : approvalInfoResult.getAggrPeriods()) {
					AggrPeriodEachActualClosure mergePeriodClr = informationMonth.getActualClosure();
					DatePeriod mergePeriod = mergePeriodClr.getPeriod();
					// ????????????Imported???????????????????????????????????????????????????????????????????????????????????????
//					List<ApproveRootStatusForEmpImport> lstApprovalStatus = approvalInfoResult
//							.getLstApprovalMonthStatus();
					ApproveRootStatusForEmpImport mapApprovalStatus = lstApprovalStatus.isEmpty() ? null
							: lstApprovalStatus.get(0);
					// mapApprovalRootBySId
					resultPart.stream().forEach(x -> {
						val mapApprovalRoot = mapApprovalRootBySId.get(Pair.of(x.getEmployeeId(), x.getDate()));
						val tempApprovalStatus = mapApprovalStatus == null ? null
								: mapApprovalStatus.getApprovalStatus();
						if (mapApprovalRoot != null && tempApprovalStatus != null
								&& (x.getDate().afterOrEquals(mergePeriod.start())
										&& x.getDate().beforeOrEquals(mergePeriod.end()))) {
							if (mapApprovalRoot.getLeft().getReleaseDivision() == ReleasedProprietyDivision.RELEASE
									&& tempApprovalStatus == ApprovalStatusForEmployee.UNAPPROVED)
								x.setPermissionRelease(true);
							else
								x.setPermissionRelease(false);
						}
					});
//				}
			}
			}
			resultResult.addAll(resultPart);
			// });
		});
		return resultResult;
	}

	public <T extends ConfirmStatusActualResult> List<T> updatePermissionCheckWithCondition(List<T> datas,
			boolean checked,
			Map<Pair<String, GeneralDate>, Pair<ApprovalStatus, ApproverEmployeeState>> mapApprovalRoot) {
		return datas.stream().map(x -> {
			val data = mapApprovalRoot.get(Pair.of(x.getEmployeeId(), x.getDate()));
			val dataCompleteApproval = (data == null) ? false : data.getRight() != ApproverEmployeeState.PHASE_DURING;
			x.setPermissionChecked(checked && !dataCompleteApproval);
			return x;
		}).collect(Collectors.toList());
	}

	public <T extends ConfirmStatusActualResult> List<T> updatePermissionRelease(List<T> datas, boolean release) {
		return datas.stream().map(x -> {
			x.setPermissionRelease(release);
			return x;
		}).collect(Collectors.toList());
	}

}
