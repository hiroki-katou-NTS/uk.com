package nts.uk.ctx.bs.person.pub.contact;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OtherContact {
	/**
	 * NO
	 */
	private int no;

	/**
	 * 連絡先利用設定
	 */
	private boolean isDisplay;

	/**
	 * 連絡先名
	 */
	private String contactName;
}
