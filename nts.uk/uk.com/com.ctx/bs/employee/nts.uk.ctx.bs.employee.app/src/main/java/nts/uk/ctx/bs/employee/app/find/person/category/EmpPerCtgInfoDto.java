package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.app.find.person.item.EmpPersonInfoItemDefDto;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;


@Setter
@Getter
@NoArgsConstructor
public class EmpPerCtgInfoDto {
	private String categoryNameDefault;
	private String categoryName;
	private int categoryType;
	private boolean isAbolition;
	private int systemRequired;
	private int isExistedItemLst;
	private List<EmpPersonInfoItemDefDto> itemLst;
	
	private EmpPerCtgInfoDto(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition){
		this.categoryName = personInfoCategory.getCategoryName().v();
		this.categoryType = personInfoCategory.getCategoryType().value;
		this.isAbolition = personInfoCategory.getIsAbolition().value > 0;
		if(lstPersonInfoItemDefinition != null)
			this.itemLst = lstPersonInfoItemDefinition.stream()
				.map(x -> new EmpPersonInfoItemDefDto(x.getPerInfoItemDefId(),
						x.getPerInfoCategoryId(), x.getItemName().v(), x.getIsAbolition().value,
						x.getSystemRequired().value)).collect(Collectors.toList());
	}
	
	public static EmpPerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition){
		return new EmpPerCtgInfoDto(personInfoCategory, lstPersonInfoItemDefinition);
	}
	public static EmpPerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory){
		return new EmpPerCtgInfoDto(personInfoCategory, null);
	}
}
