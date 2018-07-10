package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.refcompany;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;

@Getter
public class SelectionAndOrder {

	List<Selection> selectionsOfCompany;
	
	List<SelectionItemOrder> selectionOrderOfCompany;
	
	public SelectionAndOrder() {
		this.selectionsOfCompany = new ArrayList<>();
		this.selectionOrderOfCompany = new ArrayList<>();
	}

	public SelectionAndOrder(List<Selection> selectionsOfCompany, List<SelectionItemOrder> selectionOrderOfCompany) {
		super();
		this.selectionsOfCompany = selectionsOfCompany;
		this.selectionOrderOfCompany = selectionOrderOfCompany;
	}
	
}
