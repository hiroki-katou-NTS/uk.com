package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoCategoryDetail {
	@Setter
	@Getter
	private String personInfoCategoryAuthId;
	@Setter
	@Getter
	private String categoryId;
	@Setter
	@Getter
	private String categoryName;
	@Setter
	@Getter
	private boolean isSetting;
}
