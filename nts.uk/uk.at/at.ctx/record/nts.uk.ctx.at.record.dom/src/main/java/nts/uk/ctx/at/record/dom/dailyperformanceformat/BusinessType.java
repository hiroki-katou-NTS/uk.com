package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeName;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class BusinessType extends AggregateRoot {

	private String companyId;
	
	/**
	 * padding left "0"
	 */
	private BusinessTypeCode workTypeCode;
	
	private BusinessTypeName workTypeName;

	public BusinessType(String companyId, BusinessTypeCode workTypeCode, BusinessTypeName workTypeName) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.workTypeName = workTypeName;
	}
	
	public static BusinessType createFromJavaType(String companyId, String workTypeCode, String workTypeName){
		return new BusinessType(companyId, new BusinessTypeCode(workTypeCode), new BusinessTypeName(workTypeName));
	}	
}
