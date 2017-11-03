package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.app.find.employee.item.PerInfoItemDefDto;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemOptionalDto;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoCtgFullDto {
	private String id;
	private String categoryCode;
	private String categoryName;
	private int personEmployeeType;
	private int isAbolition;
	private int categoryType;
	private int isFixed;
	private List<PerInfoItemDefDto> itemLst;
	private List<CategoryItemFixDto> listCtgItemFixedDto;
	private List<CategoryItemOptionalDto> listCtgItemOptionalDto;

	private PersonInfoCtgFullDto(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition, List<CategoryItemFixDto> listCtgItemFixedDto,
			List<CategoryItemOptionalDto> listCtgItemOptionalDto) {
		this.id = personInfoCategory.getPersonInfoCategoryId();
		this.categoryCode = personInfoCategory.getCategoryCode().v();
		this.categoryName = personInfoCategory.getCategoryName().v();
		this.personEmployeeType = personInfoCategory.getPersonEmployeeType().value;
		this.isAbolition = personInfoCategory.getIsAbolition().value;
		this.categoryType = personInfoCategory.getCategoryType().value;
		this.isFixed = personInfoCategory.getIsFixed().value;
		this.listCtgItemFixedDto = listCtgItemFixedDto;
		this.listCtgItemOptionalDto = listCtgItemOptionalDto;
		if (lstPersonInfoItemDefinition != null)
			this.itemLst = lstPersonInfoItemDefinition.stream()
					.map(x -> new PerInfoItemDefDto(x.getPerInfoItemDefId(), x.getPerInfoCategoryId(),
							x.getItemName().v(), x.getIsAbolition().value, x.getSystemRequired().value))
					.collect(Collectors.toList());
	}

	public static PersonInfoCtgFullDto createObjectFromDomain(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition) {
		return new PersonInfoCtgFullDto(personInfoCategory, lstPersonInfoItemDefinition, null, null);
	}

	public static PersonInfoCtgFullDto createObjectFromDomain(PersonInfoCategory personInfoCategory) {
		return new PersonInfoCtgFullDto(personInfoCategory, null, null, null);
	}
}
