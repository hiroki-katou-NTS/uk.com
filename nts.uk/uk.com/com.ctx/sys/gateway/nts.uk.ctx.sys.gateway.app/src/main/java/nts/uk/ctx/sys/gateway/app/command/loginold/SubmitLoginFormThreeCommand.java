/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

/**
 * The Class SubmitLoginFormThreeCommand.
 */
public class SubmitLoginFormThreeCommand extends BasicLoginCommand {

	/** The is sign on. */
	private boolean signOn;
	
	/**
	 * Checks if is sign on.
	 *
	 * @return true, if is sign on
	 */
	public boolean isSignOn() {
        return signOn;
    }

    /**
     * Sets the sign on.
     *
     * @param signOn the new sign on
     */
    public void setSignOn(boolean signOn) {
        this.signOn = signOn;
    }
}
