package nts.uk.cnv.dom.td.devstatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 開発状況
 */
public enum DevelopmentStatus {

	/** 未発注 */
	NOT_ORDER,
	
	/** 発注済み */
	ORDERED,
	
	/** 納品済み */
	DELIVERED,
	
	/** 検収済み */
	ACCEPTED,
	
	;
	
	/**
	 * 未検収のステータス
	 * @return
	 */
	public static Set<DevelopmentStatus> notAccepted() {
		return new HashSet<>(Arrays.asList(NOT_ORDER, ORDERED, DELIVERED));
	}
	
	/**
	 * 全てのステータス
	 * @return
	 */
	public static Set<DevelopmentStatus> all() {
		return new HashSet<>(Arrays.asList(DevelopmentStatus.values()));
	}
}
