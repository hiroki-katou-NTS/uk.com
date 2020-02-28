package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_1_10;

/**キャリアパス*/
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

	public CareerPath(String companyId, String historyId, Integer_1_10 maxClassLevel, List<Career> careerList) {
		super();
		this.companyId = companyId;
		this.historyId = historyId;
		this.maxClassLevel = maxClassLevel;
		this.careerList = careerList;
		this.validateExt();
	}
	
	private void validateExt() {
		if(this.careerList.isEmpty()) {
			throw new BusinessException("MsgJ_50");
		}
	}
	
}
