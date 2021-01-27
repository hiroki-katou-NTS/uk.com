package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmpConfirmAfterParam {
	private int closureId;
	private int processingYm;
	private List<DisplayWorkplace> wkpInfoLst;
}
