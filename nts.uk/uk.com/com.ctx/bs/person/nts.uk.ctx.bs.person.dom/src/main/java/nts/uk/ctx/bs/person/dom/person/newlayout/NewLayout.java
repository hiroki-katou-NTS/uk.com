package nts.uk.ctx.bs.person.dom.person.newlayout;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

public class NewLayout extends AggregateRoot {
	@Getter
	private String companyId;
	@Getter
	private String newLayoutID;
	@Getter
	private LayoutCode layoutCode;
	@Getter
	private LayoutName layoutName;

	/**
	 * 
	 * @param newLayoutID
	 * @param layoutCode
	 * @param layoutName
	 */
	public NewLayout(String companyId, String newLayoutID, LayoutCode layoutCode, LayoutName layoutName) {
		super();
		this.companyId = companyId;
		this.newLayoutID = newLayoutID;
		this.layoutCode = layoutCode;
		this.layoutName = layoutName;
	}

	public static NewLayout createFromJavaType(String companyId, String newLayoutID, String layoutCode,
			String layoutName) {
		return new NewLayout(companyId, newLayoutID, new LayoutCode(layoutCode),
				new LayoutName(layoutName));
	}
}
