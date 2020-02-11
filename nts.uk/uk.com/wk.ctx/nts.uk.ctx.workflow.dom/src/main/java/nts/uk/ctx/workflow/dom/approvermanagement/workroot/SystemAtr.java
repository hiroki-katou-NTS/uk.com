package nts.uk.ctx.workflow.dom.approvermanagement.workroot;
/**
 * システム区分
 * @author hoatt
 *
 */
public enum SystemAtr {
	/**就業*/
	WORK(0,"就業"),
	/**人事*/
	HUMAN_RESOURCES(1,"人事");
	
	public Integer value;
	public String nameId;

	private SystemAtr(Integer value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
