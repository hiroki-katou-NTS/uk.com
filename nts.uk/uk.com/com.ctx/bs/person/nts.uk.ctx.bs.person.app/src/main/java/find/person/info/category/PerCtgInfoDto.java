package find.person.info.category;

import java.util.List;

import find.person.info.item.PersonInfoItemDefDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PerCtgInfoDto {
	private String categoryNameDefault;
	private String categoryName;
	private int categoryType;
	private boolean isAbolition;
	private int systemRequired;
	private int isExistedItemLst;
	private int personEmployeeType;
	private List<PersonInfoItemDefDto> itemLst;
}
