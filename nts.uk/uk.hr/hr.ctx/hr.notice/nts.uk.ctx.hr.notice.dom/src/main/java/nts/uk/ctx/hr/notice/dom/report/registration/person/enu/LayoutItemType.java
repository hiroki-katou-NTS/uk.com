package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum 項目区分
 *
 */
public enum LayoutItemType {
	
	/** 項目 */
	Item(0),
	/** 一覧表 */
	List(1),
	/** 区切り線  */
	Separator(2);
	public final int value;

	private LayoutItemType(int value) {
		this.value = value;
	}
}
