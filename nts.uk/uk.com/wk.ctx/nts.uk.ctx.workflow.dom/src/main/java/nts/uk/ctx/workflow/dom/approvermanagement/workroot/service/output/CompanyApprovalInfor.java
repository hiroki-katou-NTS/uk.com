package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.company.CompanyInfor;
@Data
@AllArgsConstructor
public class CompanyApprovalInfor {
	/**
	 * 会社情報
	 */
	Optional<CompanyInfor> comInfo;
	/**
	 * 会社別就業承認ルート情報
	 */
	List<ApprovalForApplication> lstComs;
}
