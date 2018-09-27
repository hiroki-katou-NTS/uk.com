package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

@AllArgsConstructor
public class Time36UpperLimitCheckResult {
	/**
	 * 上限エラーフラグ
	 */
	private boolean errorFlg;

	/**
	 * 時間外時間の詳細
	 */
	@Getter
	private Optional<AppOvertimeDetail> appOvertimeDetail;

	public boolean getErrorFlg() {
		return errorFlg;
	}
}
