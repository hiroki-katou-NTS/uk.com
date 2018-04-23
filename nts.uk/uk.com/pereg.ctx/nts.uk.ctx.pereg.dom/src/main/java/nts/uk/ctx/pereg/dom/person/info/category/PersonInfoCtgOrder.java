package nts.uk.ctx.pereg.dom.person.info.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonInfoCtgOrder {

	private String companyId;

	private String categoryId;

	private int disorder;

	public static PersonInfoCtgOrder createCategoryOrder(String companyId, String categoryId, int disOrder) {
		return new PersonInfoCtgOrder(companyId, categoryId, disOrder);
	}
	
	public void updateDisOrder(int disorder) {
		this.disorder = disorder;
	}

}
