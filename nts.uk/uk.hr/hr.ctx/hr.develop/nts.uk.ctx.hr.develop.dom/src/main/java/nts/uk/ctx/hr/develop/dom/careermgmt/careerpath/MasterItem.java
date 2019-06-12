package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MasterItem {
	
	private Optional<String> masterItemId;
	
	private String masterItemCd;
	
	private String masterItemName;

}
