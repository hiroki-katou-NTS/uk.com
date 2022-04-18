package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * @author thanh_nx
 *
 *         申請のデータ
 */
@Getter
public abstract class NRQueryAppImport {
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
	
	public NRQueryAppImport(GeneralDate appDate, GeneralDateTime inputDate, String appName, int appType,
			int beforeAfterType, String approvalStatus) {
		this.appDate = appDate;
		this.inputDate = inputDate;
		this.appName = appName;
		this.appType = appType;
		this.beforeAfterType = beforeAfterType;
		this.approvalStatus = approvalStatus;
	}

	// [1] XMLを作る
	public abstract String createXml();

	// [2] HTMLを作る
	public abstract String createHtml();
}
