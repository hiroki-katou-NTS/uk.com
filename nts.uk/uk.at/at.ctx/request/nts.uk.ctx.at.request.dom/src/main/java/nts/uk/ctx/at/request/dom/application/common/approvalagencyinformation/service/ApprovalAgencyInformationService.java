package nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.ApprovalAgencyInformationOutput;

/**
 * 3-1.承認代行情報の取得処理
 * @author tutk
 *
 */
public interface ApprovalAgencyInformationService {
	public ApprovalAgencyInformationOutput getApprovalAgencyInformation(String companyID,List<String> approver);

}
