package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Anh.BD
 *
 */
@Value
@AllArgsConstructor
public class UnAppMailTransmisOutput {
	List<String> listWkpId;
	GeneralDate closureStart;
	GeneralDate closureEnd;
	List<String> listEmpCd;
}
