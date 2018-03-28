package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
public class ApprovalStatusMailTempFinder {

	@Inject
	private ApprovalStatusMailTempRepository finder;

	public ApprovalStatusMailTempDto findByType(int mailType) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		Optional<ApprovalStatusMailTemp> domain = finder.getApprovalStatusMailTempById(cid, mailType);
		return domain.isPresent() ? ApprovalStatusMailTempDto.fromDomain(domain.get()) : null;
	}

	public List<ApprovalStatusMailTempDto> findBySetting() {
		// 会社ID
		String cid = AppContexts.user().companyId();
		List<ApprovalStatusMailTempDto> listMail = new ArrayList<ApprovalStatusMailTempDto>();

		listMail.add(this.getApprovalStatusMailTemp(cid, 0));
		listMail.add(this.getApprovalStatusMailTemp(cid, 1));
		//listMail.add(this.getApprovalStatusMailTemp(cid, 2));
		listMail.add(this.getApprovalStatusMailTemp(cid, 3));
		listMail.add(this.getApprovalStatusMailTemp(cid, 4));
		
		return listMail;
	}

	private ApprovalStatusMailTempDto getApprovalStatusMailTemp(String cid, int mailType) {
		Optional<ApprovalStatusMailTemp> domain = finder.getApprovalStatusMailTempById(cid, mailType);
		return domain.isPresent() ? ApprovalStatusMailTempDto.fromDomain(domain.get())
				: new ApprovalStatusMailTempDto(mailType, 1, 1, 1, "", "", 0);
	}
}
