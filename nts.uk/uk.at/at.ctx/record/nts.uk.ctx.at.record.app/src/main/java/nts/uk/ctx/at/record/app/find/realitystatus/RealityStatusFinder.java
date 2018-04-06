package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.application.realitystatus.RealityStatusService;
import nts.uk.ctx.at.record.dom.application.realitystatus.enums.TransmissionAttr;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.StatusWkpActivityOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.WkpIdMailCheckOutput;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@Stateless
public class RealityStatusFinder {
	@Inject
	RealityStatusService realityStatusService;

	public List<StatusWkpActivityOutput> getStatusWkpActivity(RealityStatusActivityParam wkpInfoDto) {
		GeneralDate startDate = GeneralDate.fromString(wkpInfoDto.getStartDate(), "yyyy/MM/dd");
		GeneralDate endDate = GeneralDate.fromString(wkpInfoDto.getEndDate(), "yyyy/MM/dd");
		// アルゴリズム「承認状況職場実績起動」を実行する
		return realityStatusService.getStatusWkpActivity(wkpInfoDto.getListWorkplaceId(), startDate, endDate,
				wkpInfoDto.getListEmpCd(), wkpInfoDto.isConfirmData());
	}

	public void checkSendUnconfirmedMail(List<WkpIdMailCheckParam> listWkp) {
		// アルゴリズム「承認状況未確認メール送信」を実行する
		realityStatusService.checkSendUnconfirmedMail(this.getWkpIdMailCheck(listWkp));
	}

	public void exeSendUnconfirmMail(ExeSendUnconfirmMailParam dto) {
		List<WkpIdMailCheckOutput> listWkp = this.getWkpIdMailCheck(dto.getListWkp());
		GeneralDate startDate = GeneralDate.fromString(dto.getStartDate(), "yyyy/MM/dd");
		GeneralDate endDate = GeneralDate.fromString(dto.getEndDate(), "yyyy/MM/dd");
		// アルゴリズム「承認状況未確認メール送信実行」を実行する
		realityStatusService.exeSendUnconfirmMail(EnumAdaptor.valueOf(dto.getType(), TransmissionAttr.class), listWkp,
				startDate, endDate, dto.getListEmpCd());
	}

	private List<WkpIdMailCheckOutput> getWkpIdMailCheck(List<WkpIdMailCheckParam> listWkpParam) {
		List<WkpIdMailCheckOutput> listWkp = new ArrayList<>();
		for (WkpIdMailCheckParam item : listWkpParam) {
			WkpIdMailCheckOutput wkp = new WkpIdMailCheckOutput(item.getWkpId(), item.isCheckOn());
			listWkp.add(wkp);
		}
		return listWkp;
	}
}
