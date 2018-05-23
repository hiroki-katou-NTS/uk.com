package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnApprovalSendMail;

/**
 * 
 * @author Anh.BD
 *
 */
@Value
@AllArgsConstructor
public class UnAppMailTransmisDto {
	List<String> listWkpId;
	GeneralDate closureStart;
	GeneralDate closureEnd;
	List<String> listEmpCd;
}
