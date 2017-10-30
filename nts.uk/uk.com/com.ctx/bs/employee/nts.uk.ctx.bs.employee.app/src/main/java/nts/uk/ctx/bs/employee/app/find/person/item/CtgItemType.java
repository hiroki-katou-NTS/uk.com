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
	EMPLOYEE(5),
	//休職休業
	TEMPORARY_ABSENCE(6),
	//職務職位
	JOB_POS_MAIN(7),
	//所属職場
	ASSIGNED_WORKPLACE(8),
	//所属部門
	AFFI_DEPARMENT(9),
	//所属部門（兼務）
	CURRENT_AFF_DEPARTMENT(10);
	
	public final int value;
}
