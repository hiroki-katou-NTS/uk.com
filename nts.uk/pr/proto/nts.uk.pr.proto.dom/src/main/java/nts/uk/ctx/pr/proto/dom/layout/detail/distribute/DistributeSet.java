package nts.uk.ctx.pr.proto.dom.layout.detail.distribute;

public enum DistributeSet {
	// 0:按分しない
	NOT_PROPORTIONAL(0),
	// 1:按分する
	PROPORTIONAL(1),
	// 2:月1回支給
	MONTHLY_PAYMENT(2);

	public final int value;

	private DistributeSet(int value) {
		this.value = value;
	}

}
