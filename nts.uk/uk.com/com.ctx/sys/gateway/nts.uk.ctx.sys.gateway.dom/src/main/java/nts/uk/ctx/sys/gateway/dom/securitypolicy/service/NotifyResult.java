package nts.uk.ctx.sys.gateway.dom.securitypolicy.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotifyResult {

	//１．終了状態
	private boolean notifyFlg;
	//２．③【残り何日】
	private int spanDays;
}
