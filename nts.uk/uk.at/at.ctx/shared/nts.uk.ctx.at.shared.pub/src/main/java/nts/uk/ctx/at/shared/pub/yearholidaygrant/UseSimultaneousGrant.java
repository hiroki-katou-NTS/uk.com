package nts.uk.ctx.at.shared.pub.yearholidaygrant;

/**
 * 
 * @author TanLV
 *
 */
public enum UseSimultaneousGrant {
	NOT_USE(0),
	USE(1);
	
	public final int value;
	
	UseSimultaneousGrant(int value) {
		this.value = value;
	}
}
