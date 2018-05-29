package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum SelectionItemClassification {
	
	PersonalInformation(0), // 個人情報
	
	EmployeeInformation(1);// 社員情報
	
	public final int value;
}
