package nts.uk.cnv.dom.td.devstatus;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

/**
 * 開発状況
 */
@RequiredArgsConstructor
public enum DevelopmentStatus {

	/** 未発注 */
	NOT_ORDER(1),

	/** 発注済み */
	ORDERED(2),

	/** 納品済み */
	DELIVERED(3),

	/** 検収済み */
	ACCEPTED(4);

	public final int order;

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
}
