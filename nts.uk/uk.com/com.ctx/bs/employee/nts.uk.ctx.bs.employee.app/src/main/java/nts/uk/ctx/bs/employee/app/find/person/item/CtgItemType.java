package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CtgItemType {
	//家族所属税 IncomeTax
	INCOME_TAX(1),
	//家族社会保険 FamilySocialInsurance
	FAMILY_SOCIAL_INSURANCE(2),
	//家族介護 FamilyCare
	FAMILY_CARE(3),
	//所属部門ID（兼務） CurrJobPos
	CURR_JOB_POS(4),
	//社員 Employee
	EMPLOYEE(5);
	//休職休業
	
	
	public final int value;
}
