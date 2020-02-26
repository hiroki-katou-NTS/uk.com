package nts.uk.ctx.hr.shared.dom.wageprovision.processdatecls;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClosureDateImport {
	/**
	 * 雇用コード
	 */
	private List<String> employmentCodes;

	/**
	 * 参照日
	 */

	private List<Integer> referenceDates;
}
