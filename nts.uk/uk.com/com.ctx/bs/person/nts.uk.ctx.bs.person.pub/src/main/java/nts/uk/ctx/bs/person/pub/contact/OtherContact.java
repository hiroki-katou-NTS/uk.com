package nts.uk.ctx.bs.person.pub.contact;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OtherContact {
	/**
	 * NO
	 */
	private Integer no;

	/**
	 * 連絡先利用設定
	 */
	private Optional<Boolean> isDisplay;

	/**
	 * 連絡先名
	 */
	private String address;
}
