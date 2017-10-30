package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.app.find.person.item.EmpPersonInfoItemDefDto;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

/**
 * Return object when starting cps 001 a (category)
 * 
 * @author xuan vinh
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class EmpPerCtgInfoDto {
	private String categoryName;
	private int categoryType;
	private boolean isAbolition;
	private int systemRequired;
	private int isExistedItemLst;
	private List<EmpPersonInfoItemDefDto> itemLst;
	private CtgItemFixDto ctgItemDto;
	private CtgItemOptionalDto ctgItemOptionalDto;	
	
	private EmpPerCtgInfoDto(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition, 
			CtgItemFixDto ctgItemDto, CtgItemOptionalDto ctgItemOptionalDto){
		this.categoryName = personInfoCategory.getCategoryName().v();
		this.categoryType = personInfoCategory.getCategoryType().value;
		this.isAbolition = personInfoCategory.getIsAbolition().value > 0;
		if(lstPersonInfoItemDefinition != null)
			this.itemLst = lstPersonInfoItemDefinition.stream()
				.map(x -> new EmpPersonInfoItemDefDto(x.getPerInfoItemDefId(),
						x.getPerInfoCategoryId(), x.getItemName().v(), x.getIsAbolition().value,
						x.getSystemRequired().value)).collect(Collectors.toList());
		this.ctgItemDto = ctgItemDto;
		this.ctgItemOptionalDto = ctgItemOptionalDto;
	}
	
	public static EmpPerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition, CtgItemFixDto ctgItemDto, CtgItemOptionalDto ctgItemOptionalDto){
		return new EmpPerCtgInfoDto(personInfoCategory, lstPersonInfoItemDefinition, ctgItemDto, ctgItemOptionalDto);
	}
	
	public static EmpPerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition, CtgItemFixDto ctgItemDto){
		return new EmpPerCtgInfoDto(personInfoCategory, lstPersonInfoItemDefinition, ctgItemDto, null);
	}
	
	public static EmpPerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory,
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition){
		return new EmpPerCtgInfoDto(personInfoCategory, lstPersonInfoItemDefinition, null, null);
	}
	
	public static EmpPerCtgInfoDto createObjectFromDomain(PersonInfoCategory personInfoCategory){
		return new EmpPerCtgInfoDto(personInfoCategory, null, null, null);
	}
	
}
