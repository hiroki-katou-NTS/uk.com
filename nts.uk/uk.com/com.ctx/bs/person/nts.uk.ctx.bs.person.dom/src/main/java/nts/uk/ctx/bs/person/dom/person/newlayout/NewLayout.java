package nts.uk.ctx.bs.person.dom.person.newlayout;

import nts.arc.layer.dom.AggregateRoot;

public class NewLayout extends AggregateRoot{
	
	String companyId;
	LayoutID newLayoutID;
	LayoutCode layoutCode;
	LayoutName layoutName;
	
	public NewLayout(String companyId, LayoutID newLayoutID, LayoutCode layoutCode, LayoutName layoutName) {
		super();
		this.companyId = companyId;
		this.newLayoutID = newLayoutID;
		this.layoutCode = layoutCode;
		this.layoutName = layoutName;
	}
}
