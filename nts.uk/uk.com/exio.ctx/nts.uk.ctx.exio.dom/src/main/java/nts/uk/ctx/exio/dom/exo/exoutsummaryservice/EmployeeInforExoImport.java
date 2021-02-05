package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeInforExoImport {
	/**
	 * 会社ID
	 */
	private String cid;
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 個人ID
	 */
	private String pid;
	/**
	 * 社員CD
	 */
	private String scd;
}
