package nts.uk.ctx.at.shared.dom.yearholidaygrant;

/**
 * 
 * @author TanLV
 *
 */
public enum GrantSimultaneity {
	NOT_USE(0),
	USE(1);
	
	public final int value;
	
	GrantSimultaneity(int value) {
		this.value = value;
	}
}
