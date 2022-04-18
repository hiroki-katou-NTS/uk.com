package nts.uk.ctx.workflow.pub.service.export;
/**
 * システム区分
 * @author hoatt
 *
 */
public enum SystemAtrExport {
	/**就業*/
	WORK(0,"就業"),
	/**人事*/
	HUMAN_RESOURCES(1,"人事");
	
	public Integer value;
	public String nameId;

	private SystemAtrExport(Integer value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
