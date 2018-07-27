package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import lombok.AllArgsConstructor;

/**
 * 暫定年休管理モード
 * @author shuichu_ishida
 */
@AllArgsConstructor
public enum TempAnnualLeaveMngMode {
	/** 月次モード */
	MONTHLY(0),
	/** その他モード */
	OTHER(1);

	public final int value;
}
