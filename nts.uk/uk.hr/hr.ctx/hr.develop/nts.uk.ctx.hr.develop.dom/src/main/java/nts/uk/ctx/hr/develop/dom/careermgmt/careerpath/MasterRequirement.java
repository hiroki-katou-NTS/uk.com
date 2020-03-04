package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**マスタ条件*/
@AllArgsConstructor
@Getter
public class MasterRequirement {

	private String masterType;
	
	private List<MasterItem> masterItemList;
}
