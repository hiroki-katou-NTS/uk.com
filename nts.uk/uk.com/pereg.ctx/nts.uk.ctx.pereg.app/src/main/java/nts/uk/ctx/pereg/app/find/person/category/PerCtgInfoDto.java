package nts.uk.ctx.pereg.app.find.person.category;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.person.info.item.PersonInfoItemDefDto;
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
	private boolean initValMasterObjCls;
	private boolean addItemObjCls;
	private boolean canAbolition;
	private List<PersonInfoItemDefDto> itemLst;
}
