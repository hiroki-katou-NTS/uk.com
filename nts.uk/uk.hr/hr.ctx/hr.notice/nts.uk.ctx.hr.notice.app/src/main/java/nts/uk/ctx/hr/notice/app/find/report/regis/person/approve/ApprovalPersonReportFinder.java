/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person.approve;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class ApprovalPersonReportFinder {

	@Inject
	private ApprovalPersonReportRepository repo;

	/**
	 * @param reportId,
	 * @param cid
	 * @return
	 */
	public List<ApprovalPersonReportDto> getListDomain(int reportId) {
		String cid = AppContexts.user().companyId();
		List<ApprovalPersonReportDto> result = new ArrayList<>();
		List<ApprovalPersonReport> listDomain = repo.getListDomainByReportId(cid, String.valueOf(reportId)).stream()
				.filter(apr -> apr.getAprStatus().value == ApprovalStatus.Approved.value).collect(Collectors.toList());
		if (listDomain.isEmpty()) {
			return new ArrayList<>();
		}
		
		ApprovalPersonReportDto firstItemCombobox = ApprovalPersonReportDto.builder()
				.idCombobox(1)
				.infoToDisplay("申請者： "  + "BussinessName ngnd").build();
		result.add(firstItemCombobox);
		
		for (int i = 0; i < listDomain.size(); i++) {
			ApprovalPersonReportDto itemCombobox = ApprovalPersonReportDto.builder()
					  .idCombobox(i+2)
					  .cid(listDomain.get(i).getCid()) //会社ID
					  .rootSatteId(listDomain.get(i).getRootSatteId()) //ルートインスタンスID
					  .reportID(listDomain.get(i).getReportID()) //届出ID
					  .reportName(listDomain.get(i).getReportName()) //届出名
					  .refDate(listDomain.get(i).getRefDate().toDate()) //基準日
					  .inputDate(listDomain.get(i).getInputDate().toDate()) //入力日
					  .appDate(listDomain.get(i).getAppDate().toDate()) //申請日
					  .aprDate(listDomain.get(i).getAprDate().toDate()) //承認日
					  .aprSid(listDomain.get(i).getAprSid())//承認者社員ID
					  .aprBussinessName(listDomain.get(i).getAprBussinessName()) //承認者社員名
					  .emailAddress(listDomain.get(i).getEmailAddress()) //メールアドレス
					  .phaseNum(listDomain.get(i).getPhaseNum())// フェーズ通番
					  .aprStatus(listDomain.get(i).getAprStatus().value) //承認状況
					  .aprNum(listDomain.get(i).getAprNum())   //承認者通番
					  .arpAgency(listDomain.get(i).isArpAgency())   //代行承認
					  .comment(listDomain.get(i).getComment() == null ? "" : listDomain.get(i).getComment().toString()) //コメント
					  .aprActivity(listDomain.get(i).getAprActivity().value)//承認活性
					  .emailTransmissionClass(listDomain.get(i).getEmailTransmissionClass().value)//メール送信区分
					  .appSid(listDomain.get(i).getAppSid()) //申請者社員ID
					  .inputSid(listDomain.get(i).getInputSid()) //入力者社員ID
					  .reportLayoutID(listDomain.get(i).getReportLayoutID())//個別届出種類ID
					  .sendBackSID(listDomain.get(i).getSendBackSID().isPresent() ? listDomain.get(i).getSendBackSID().get() : null) //差し戻し先社員ID
					  .sendBackClass(listDomain.get(i).getSendBackClass().isPresent() ? listDomain.get(i).getSendBackClass().get().value : null) //差し戻し区分
					  .infoToDisplay("フェーズ" + listDomain.get(i).getPhaseNum() + "の承認者" + listDomain.get(i).getAprNum()
							+ ":  " + listDomain.get(i).getAprBussinessName())
					.build();
					result.add(itemCombobox);	
		}
		
		return result;

	}
}
