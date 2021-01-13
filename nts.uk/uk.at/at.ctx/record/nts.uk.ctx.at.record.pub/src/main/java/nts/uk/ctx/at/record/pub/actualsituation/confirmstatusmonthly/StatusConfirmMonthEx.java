package nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 月の実績の確認状況
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class StatusConfirmMonthEx {
	
	/**確認状況*/
	private List<ConfirmStatusResultEx> listConfirmStatus = new ArrayList<>();

	
	public StatusConfirmMonthEx(List<ConfirmStatusResultEx> listConfirmStatus) {
		super();
		this.listConfirmStatus = listConfirmStatus;
	}

	
}
