package nts.uk.ctx.sys.auth.dom.role.personrole;

import lombok.Value;

@Value
public class PersonInfoCategoryExportDetailDto {
	private String categoryId;

	private String categoryCode;

	private String categoryName;

	private int categoryType;

	private int allowPersonRef;

	private int allowOtherRef;

	private int personEmployeeType;

	private boolean isSetting;

	public static PersonInfoCategoryExportDetailDto fromDomain(PersonInfoCategoryExportDetail domain) {
		return new PersonInfoCategoryExportDetailDto(domain.getCategoryId(), domain.getCategoryCode(),
				domain.getCategoryName(), domain.getCategoryType(), domain.getAllowPersonRef(),
				domain.getAllowOtherRef(), domain.getPersonEmployeeType(), domain.isSetting());

	}

}
