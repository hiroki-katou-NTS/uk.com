package nts.uk.cnv.dom.td.event;

import lombok.RequiredArgsConstructor;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

@RequiredArgsConstructor
public enum EventType {

	/** 発注 */
	ORDER(1, DevelopmentStatus.ORDERED),

	/** 納品 */
	DELIVER(2, DevelopmentStatus.DELIVERED),

	/** 検収 */
	ACCEPT(3, DevelopmentStatus.ACCEPTED);

	public final int order;
	
	// イベントに対応する開発状況
	public final DevelopmentStatus relationStatus;
	
	/**
	 * 到達している必要がある開発状況
	 * @return
	 */
	public DevelopmentStatus necessary() {
		return this.relationStatus.previous();
	}
}
