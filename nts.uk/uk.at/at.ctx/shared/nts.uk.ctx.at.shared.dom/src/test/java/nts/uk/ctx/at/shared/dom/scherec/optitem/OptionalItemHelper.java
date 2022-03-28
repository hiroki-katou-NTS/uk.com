package nts.uk.ctx.at.shared.dom.scherec.optitem;

public class OptionalItemHelper {
	
	public static OptionalItem createOptionalItemByNoAndUseAtr(int optionalItemNo, OptionalItemUsageAtr usageAtr) {
		return new OptionalItem(new OptionalItemNo(optionalItemNo), usageAtr);
	}

}
