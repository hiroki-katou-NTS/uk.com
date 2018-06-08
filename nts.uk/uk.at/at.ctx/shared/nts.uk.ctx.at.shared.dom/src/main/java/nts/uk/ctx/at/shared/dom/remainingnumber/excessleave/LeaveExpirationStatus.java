package nts.uk.ctx.at.shared.dom.remainingnumber.excessleave;

import lombok.AllArgsConstructor;
/**
 * 休暇期限切れ状態
 * @author HopNT
 *
 */
@AllArgsConstructor
public enum LeaveExpirationStatus {
		
	// 使用可能	
	AVAILABLE(1),
	
	// 期限切れ
	EXPIRED(0);
	
	public final int value;
}
