package nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess;

/**
 * 
 * @author thuongtv
 *
 */

public interface IdentityConfirmProcessAdapter {
	/**
	 * Get Usage setting of identity confirmation processing by cid
	 * @param cid
	 * @return
	 */
	public IdentityConfirmProcessImport getIdentityConfirmProcess(String cid);
	
}
