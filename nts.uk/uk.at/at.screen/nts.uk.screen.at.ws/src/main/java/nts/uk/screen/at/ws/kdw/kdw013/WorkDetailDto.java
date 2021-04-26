package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.workrecord.workrecord.WorkDetail;

/**
 * 
 * @author tutt
 *
 */
public class WorkDetailDto {
	/** 年月日 */
	private GeneralDate date;

	/** List<作業詳細> */
	private List<WorkDetailsParamDto> lstWorkDetailsParam;

	public WorkDetail toDomain() {

		return new WorkDetail(this.date, this.lstWorkDetailsParam.stream()
				.map(m -> new WorkDetailsParamDto().toDomain()).collect(Collectors.toList()));
	}
}
