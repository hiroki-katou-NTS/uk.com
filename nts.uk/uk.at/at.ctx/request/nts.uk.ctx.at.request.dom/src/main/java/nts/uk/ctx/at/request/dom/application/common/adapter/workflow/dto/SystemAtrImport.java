package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;
/**
 * システム区分
 * @author hoatt
 *
 */
public enum SystemAtrImport {
	/**就業*/
	WORK(0,"就業"),
	/**人事*/
	HUMAN_RESOURCES(1,"人事");
	
	public Integer value;
	public String nameId;

	private SystemAtrImport(Integer value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
