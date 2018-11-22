package nts.uk.ctx.at.record.app.find.application.realitystatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.SendMailResultImport;
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
				wkpInfoDto.getEndDate(), wkpInfoDto.getListEmpCd(), wkpInfoDto.isConfirmData(), wkpInfoDto.getClosureID());
	}

	public void checkSendUnconfirmedMail(List<WkpIdMailCheckParam> listWkp) {
		// アルゴリズム「承認状況未確認メール送信」を実行する
		realityStatusService.checkSendUnconfirmedMail(this.getWkpIdMailCheck(listWkp));
	}

	public SendMailResultDto exeSendUnconfirmMail(ExeSendUnconfirmMailParam dto) {
		List<WkpIdMailCheckOutput> listWkp = this.getWkpIdMailCheck(dto.getListWkp());
		// アルゴリズム「承認状況未確認メール送信実行」を実行する
		SendMailResultImport result = realityStatusService.exeSendUnconfirmMail(
				EnumAdaptor.valueOf(dto.getType(), ApprovalStatusMailType.class), listWkp, dto.getStartDate(),
				dto.getEndDate(), dto.getListEmpCd(), dto.getClosureID());
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
		List<EmpPerformanceOutput> listEmpPerformance = realityStatusService.getAcquisitionWkpEmpPerformance(
				dto.getWkpId(), dto.getStartDate(), dto.getEndDate(), dto.getListEmpCd(), dto.getClosureID());
		return this.convertEmpPerformanceDto(listEmpPerformance);
	}

	private List<EmpPerformanceDto> convertEmpPerformanceDto(List<EmpPerformanceOutput> listEmpPerformance) {
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
}
