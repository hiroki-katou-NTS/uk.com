package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**個人情報と紐付け*/
@AllArgsConstructor
@Getter
public class MasterMapping {

	private Integer_1_5 displayNumber;
	
	private String type;
	
	private List<Item> itemList;
	
	public static MasterMapping createFromJavaType(int displayNumber, String type, List<Item> itemList) {
		return new MasterMapping(
				new Integer_1_5(displayNumber), 
				type,
				itemList
				);
	}
}
