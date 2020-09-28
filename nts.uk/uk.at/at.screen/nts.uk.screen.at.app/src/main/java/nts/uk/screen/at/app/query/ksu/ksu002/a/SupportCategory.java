package nts.uk.screen.at.app.query.ksu.ksu002.a;

import lombok.AllArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@AllArgsConstructor
public enum SupportCategory {

	NOT_CHEERING(1), // 応援ではない
	TIME_SUPPORTER(2), // 時間帯応援元
	TIME_SUPPORT(3),   // 時間帯応援先
	SUPPORT_FROM(4),  // 終日応援元
	SUPPORT_TO(5);     // 終日応援先
	public int value;
}
