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
public class ApprSttSendMailInfoParam {
	private int mailType;
	private int closureId;
	private int processingYm;
	private int closureDay;
	private boolean lastDayOfMonth;
	private String startDate;
	private String endDate;
	private List<DisplayWorkplace> wkpInfoLst;
	private List<String> employmentCDLst;
}
