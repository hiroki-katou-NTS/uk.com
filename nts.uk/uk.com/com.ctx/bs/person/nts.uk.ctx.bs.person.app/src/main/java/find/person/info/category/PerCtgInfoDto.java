package find.person.info.category;

import java.util.List;
import java.util.stream.Collectors;

import find.person.info.item.PersonInfoItemDefDto;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;


@Setter
@Getter
public class PerCtgInfoDto {
	private String categoryNameDefault;
	private String categoryName;
	private int categoryType;
	private boolean isAbolition;
	private int systemRequired;
	private int isExistedItemLst;
	private List<PersonInfoItemDefDto> itemLst;
	
	private PerCtgInfoDto(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition){
		this.categoryName = personInfoCategory.getCategoryName().v();
		this.categoryType = personInfoCategory.getCategoryType().value;
		this.isAbolition = personInfoCategory.getIsAbolition().value > 0;
		if(lstPersonInfoItemDefinition != null)
			this.itemLst = lstPersonInfoItemDefinition.stream()
				.map(x -> new PersonInfoItemDefDto(x.getPerInfoItemDefId(),
						x.getPerInfoCategoryId(), x.getItemName().v(), x.getIsAbolition().value,
						x.getSystemRequired().value)).collect(Collectors.toList());
	}
	
	public static PerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition){
		return new PerCtgInfoDto(personInfoCategory, lstPersonInfoItemDefinition);
	}
	public static PerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory){
		return new PerCtgInfoDto(personInfoCategory, null);
	}
}
