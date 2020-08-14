/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import lombok.AllArgsConstructor;

/**
 * @author laitv 応援区分
 */
@AllArgsConstructor
public enum SupportCategory {

	NotCheering(1), // 応援ではない
	TimeSupporter(2), // 時間帯応援元
	TimeSupport(3),   // 時間帯応援先
	SupportFrom(4),  // 終日応援元
	SupportTo(5);     // 終日応援先
	public int value;
}
