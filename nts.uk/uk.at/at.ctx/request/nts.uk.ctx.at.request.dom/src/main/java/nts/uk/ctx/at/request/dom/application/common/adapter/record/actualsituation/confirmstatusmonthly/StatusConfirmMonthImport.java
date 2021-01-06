package nts.uk.ctx.at.request.dom.application.common.adapter.record.actualsituation.confirmstatusmonthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
public class StatusConfirmMonthImport {
	/**確認状況*/
	private List<ConfirmStatusResultImport> listConfirmStatus = new ArrayList<>();

	
	public StatusConfirmMonthImport(List<ConfirmStatusResultImport> listConfirmStatus) {
		super();
		this.listConfirmStatus = listConfirmStatus;
	}
}
