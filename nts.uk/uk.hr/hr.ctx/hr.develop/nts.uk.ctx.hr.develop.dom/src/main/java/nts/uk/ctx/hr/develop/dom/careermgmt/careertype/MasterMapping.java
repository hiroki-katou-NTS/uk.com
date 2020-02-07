package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_1_5;

/**個人情報と紐付け*/
@AllArgsConstructor
@Getter
public class MasterMapping {

	private Integer_1_5 displayNumber;
	
	private String type;
	
	private List<Item> itemList;
	
	public static MasterMapping createFromJavaType(int displayNumber, String type, List<String> itemList) {
		return new MasterMapping(
				new Integer_1_5(displayNumber), 
				type,
				itemList.stream().map(c->new Item(c)).collect(Collectors.toList())
				);
	}
}
