package nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReflectionStatusImport {
	
	/**
	 * 対象日の反映状態
	 */
	private List<ReflectionStatusOfDayImport> listReflectionStatusOfDayExport;
}
