package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**キャリアパス*/
@AllArgsConstructor
@Getter
public class CareerPath extends AggregateRoot{

	private String companyId;
	
	private String historyId;
	
	private Integer_1_10 maxClassLevel;
	
	private List<Career> careerList;
	
	public static CareerPath createFromJavaType(String companyId, String historyId, int maxClassLevel, List<Career> careerList) {
		return new CareerPath(
				companyId,
				historyId, 
				new Integer_1_10(maxClassLevel),
				careerList
				);
	}
}
