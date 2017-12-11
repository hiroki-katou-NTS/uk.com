package nts.uk.ctx.pereg.app.find.roles.auth.category;

import lombok.Value;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryDetail;

@Value
public class PersonInfoCategoryDetailDto {

	private String categoryId;

	private String categoryCode;

	private String categoryName;

	private int categoryType;

	private int allowPersonRef;

	private int allowOtherRef;

	private int personEmployeeType;

	private boolean isSetting;

	public static PersonInfoCategoryDetailDto fromDomain(PersonInfoCategoryDetail domain) {
		return new PersonInfoCategoryDetailDto(domain.getCategoryId(), domain.getCategoryCode(),
				domain.getCategoryName(), domain.getCategoryType(), domain.getAllowPersonRef(),
				domain.getAllowOtherRef(), domain.getPersonEmployeeType(), domain.isSetting());

	}

}
