package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.SendMailResultImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.application.realitystatus.RealityStatusService;
import nts.uk.ctx.at.record.dom.application.realitystatus.enums.ApprovalStatusMailType;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.DailyConfirmOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.EmpPerformanceOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.ErrorStatusOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.StatusWkpActivityOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.UseSetingOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.WkpIdMailCheckOutput;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author dat.lh
 */
@Stateless
public class RealityStatusFinder {
	@Inject
	RealityStatusService realityStatusService;

	public List<StatusWkpActivityOutput> getStatusWkpActivity(RealityStatusActivityParam wkpInfoDto) {
		// アルゴリズム「承認状況職場実績起動」を実行する
		return realityStatusService.getStatusWkpActivity(wkpInfoDto.getListWorkplaceId(), wkpInfoDto.getStartDate(),
				wkpInfoDto.getEndDate(), wkpInfoDto.getListEmpCd(), wkpInfoDto.isConfirmData());
	}

	public String checkSendUnconfirmedMail(List<WkpIdMailCheckParam> listWkp) {
		// アルゴリズム「承認状況未確認メール送信」を実行する
		return realityStatusService.checkSendUnconfirmedMail(this.getWkpIdMailCheck(listWkp));
	}

	public SendMailResultDto exeSendUnconfirmMail(ExeSendUnconfirmMailParam dto) {
		List<WkpIdMailCheckOutput> listWkp = this.getWkpIdMailCheck(dto.getListWkp());
		// アルゴリズム「承認状況未確認メール送信実行」を実行する
		SendMailResultImport result = realityStatusService.exeSendUnconfirmMail(
				EnumAdaptor.valueOf(dto.getType(), ApprovalStatusMailType.class), listWkp, dto.getStartDate(),
				dto.getEndDate(), dto.getListEmpCd());
		return new SendMailResultDto(result.isOK(), result.getListError());
	}

	private List<WkpIdMailCheckOutput> getWkpIdMailCheck(List<WkpIdMailCheckParam> listWkpParam) {
		List<WkpIdMailCheckOutput> listWkp = new ArrayList<>();
		for (WkpIdMailCheckParam item : listWkpParam) {
			WkpIdMailCheckOutput wkp = new WkpIdMailCheckOutput(item.getWkpId(), item.isCheckOn());
			listWkp.add(wkp);
		}
		return listWkp;
	}

	public UseSetingDto getUseSetting() {
		String cid = AppContexts.user().companyId();
		UseSetingOutput setting = realityStatusService.getUseSetting(cid);
		return new UseSetingDto(setting.isMonthlyConfirm(), setting.isUseBossConfirm(), setting.isUsePersonConfirm());
	}

	public List<EmpPerformanceDto> getEmpPerformance(EmpPerformanceParam dto) {
		List<EmpPerformanceDto> listData = this.convertToDto(this.getSampleDate(dto));
		return listData;
	}

	private List<EmpPerformanceDto> convertToDto(List<EmpPerformanceOutput> listEmpPerformance) {
		List<EmpPerformanceDto> listEmp = new ArrayList<>();
		for (EmpPerformanceOutput emp : listEmpPerformance) {
			Integer approvalStatus;
			List<DailyConfirmDto> listDailyConfirm = new ArrayList<>();
			List<GeneralDate> listErrorStatus = new ArrayList<>();

			if (Objects.isNull(emp.getRouteStatus())) {
				approvalStatus = null;
			} else {
				approvalStatus = emp.getRouteStatus().getApprovalStatus().value;
			}
			for (DailyConfirmOutput daily : emp.getListDailyConfirm()) {
				listDailyConfirm.add(
						new DailyConfirmDto(daily.getTargetDate(), daily.isPersonConfirm(), daily.isBossConfirm()));
			}
			for (ErrorStatusOutput error : emp.getListErrorStatus()) {
				listErrorStatus.add(error.getTargetDate());
			}
			listEmp.add(new EmpPerformanceDto(emp.getSId(), emp.getSName(), emp.getStartDate(), emp.getEndDate(),
					approvalStatus, listDailyConfirm, listErrorStatus));
		}
		return listEmp;
	}

	private List<EmpPerformanceOutput> getSampleDate(EmpPerformanceParam dto) {
		List<EmpPerformanceOutput> listEmp = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			String sId = "sid" + i;
			String sName = "sName - " + i;
			GeneralDate startDate = dto.getStartDate();
			GeneralDate endDate = dto.getEndDate();
			ApproveRootStatusForEmpImport routeStatus = null;
			List<DailyConfirmOutput> listDailyConfirm = new ArrayList<>();
			List<ErrorStatusOutput> listErrorStatus = new ArrayList<>();

			if (i % 5 == 0) {
				routeStatus = new ApproveRootStatusForEmpImport();
				routeStatus.setApprovalStatus(EnumAdaptor.valueOf(2, ApprovalStatusForEmployee.class));
			}

			if (i == 2) {
				startDate = startDate.addDays(5);
				endDate = endDate.addDays(-7);
			}

			if (i == 4) {
				startDate = startDate.addDays(4);
			}

			if (i == 7) {
				startDate = startDate.addDays(5);
				endDate = endDate.addDays(-10);
			}

			GeneralDate currentDate = startDate;

			while (currentDate.beforeOrEquals(endDate)) {
				String wkpId = dto.getWkpId();
				boolean personConfirm = false;
				boolean bossConfirm = false;

				if (i == 4 || i == 5 || i == 9) {
					personConfirm = true;
					bossConfirm = true;
				} else if (i % 3 == 0) {
					if (currentDate.day() % 3 == 0) {
						personConfirm = true;
					}
					if (currentDate.day() % 4 == 0) {
						bossConfirm = true;
					}
				}

				if (i % 5 == 0) {
					if (currentDate.day() % 2 == 0) {
						personConfirm = true;
					}
					if (currentDate.day() % 8 == 0) {
						bossConfirm = true;
					}
				}

				if (i % 7 == 0) {
					if (currentDate.day() % 6 == 0) {
						personConfirm = true;
					}
					if (currentDate.day() % 5 == 0) {
						bossConfirm = true;
					}
				}

				DailyConfirmOutput daily = new DailyConfirmOutput(wkpId, sId, currentDate, personConfirm, bossConfirm);
				listDailyConfirm.add(daily);

				if (currentDate.day() % 5 == 0) {
					ErrorStatusOutput error = new ErrorStatusOutput(wkpId, sId, currentDate);
					listErrorStatus.add(error);
				}
				currentDate = currentDate.addDays(1);
			}
			listEmp.add(new EmpPerformanceOutput(sId, sName, startDate, endDate, routeStatus, listDailyConfirm,
					listErrorStatus));
		}
		return listEmp;
	}
}
