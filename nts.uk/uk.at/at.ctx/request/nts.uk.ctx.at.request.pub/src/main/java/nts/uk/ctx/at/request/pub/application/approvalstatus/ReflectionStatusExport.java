package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReflectionStatusExport {
	
	/**
	 * 対象日の反映状態
	 */
	private List<ReflectionStatusOfDayExport> listReflectionStatusOfDayExport;
}
