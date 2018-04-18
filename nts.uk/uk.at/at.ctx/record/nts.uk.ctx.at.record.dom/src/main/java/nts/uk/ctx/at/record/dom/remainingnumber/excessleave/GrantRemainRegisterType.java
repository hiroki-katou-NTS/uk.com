package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import lombok.AllArgsConstructor;

/**
 * 付与残数登録種別
 * @author HopNT
 *
 */
@AllArgsConstructor
public enum GrantRemainRegisterType {

	// 手動
	MANUAL(0),
	
	// 月締め
	MONTH_CLOSE(1);
	
	public final int value;
}
