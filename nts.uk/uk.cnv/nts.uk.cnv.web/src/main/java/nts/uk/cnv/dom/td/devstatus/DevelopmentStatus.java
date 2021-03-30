package nts.uk.cnv.dom.td.devstatus;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

/**
 * 開発状況
 */
@RequiredArgsConstructor
public enum DevelopmentStatus {

	/** 未発注 */
	NOT_ORDER(1, "未発注"),

	/** 発注済み */
	ORDERED(2, "発注済み"),

	/** 納品済み */
	DELIVERED(3, "納品済み"),

	/** 検収済み */
	ACCEPTED(4, "検収済み");

	public final int order;
	public final String strStatus;

	public static DevelopmentStatus byOrder(int order) {
		return Arrays.asList(DevelopmentStatus.values()).stream()
				.filter(s -> s.order == order)
				.findFirst()
				.orElse(null);
	}

	public DevelopmentStatus next() {
		return byOrder(this.order + 1);
	}

	public DevelopmentStatus previous() {
		return byOrder(this.order - 1);
	}

	public boolean isLast() {
		return next() == null;
	}

	public boolean isFirst() {
		return previous() == null;
	}
	
	/**
	 * 到達している必要がある開発状況
	 * @return
	 */
	public DevelopmentStatus necessary() {
		return this.previous();
	}
}
