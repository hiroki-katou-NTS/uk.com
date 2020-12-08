package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.GetAllNRWebQueryAppDetail;

/**
 * @author thanh_nx
 *
 *         申請のデータ
 */
@AllArgsConstructor
@Getter
public class NRQueryApp {
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

	// [S-1] 申請のデータを作る
	public static NRQueryApp create(Application app, ReflectionStatusOfDay state) {
		return new NRQueryApp(state.getTargetDate(), app.getInputDate(),
				GetAllNRWebQueryAppDetail.createAppName(app.getAppType()), app.getAppType().value,
				app.getPrePostAtr().value, GetAllNRWebQueryAppDetail.createAppStatus(state));
	}

}
