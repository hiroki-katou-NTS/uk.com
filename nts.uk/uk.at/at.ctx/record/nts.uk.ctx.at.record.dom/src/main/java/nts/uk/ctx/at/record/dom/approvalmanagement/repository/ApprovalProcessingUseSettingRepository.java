/**
 * 5:03:10 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;

/**
 * @author hungnm
 *
 */
public interface ApprovalProcessingUseSettingRepository {

	Optional<ApprovalProcessingUseSetting> findByCompanyId(String companyId);
	
}