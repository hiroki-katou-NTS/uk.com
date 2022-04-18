package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * @author thanh_nx
 *
 *         申請のデータ
 */
@AllArgsConstructor
@Getter
public class NRQueryAppExport {
	// 年月日
	private GeneralDate appDate;

	// 入力日
	private GeneralDateTime inputDate;

	// 申請名
	private String appName;

	// 申請種類
	private int appType;

	// 事前事後区分
	private int beforeAfterType;

	// 承認状況
	private String approvalStatus;

}
