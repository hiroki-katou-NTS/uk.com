package nts.uk.ctx.at.shared.dom.workmanagementmultiple;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 *  複数回勤務管理
 * @author hieult
 *
 */
@Getter
@AllArgsConstructor
public class WorkManagementMultiple {
			
	/** 会社ID */
	String companyID;
	/** 使用区分 */
	UseATR useATR;

	/**
	 * Create domain from java data
	 * @param companyID
	 * @param useATR
	 * @return
	 */
	public static WorkManagementMultiple createFromJavaType( String companyID, int useATR) {
		
		 return new WorkManagementMultiple( 
				 companyID,
				 EnumAdaptor.valueOf(useATR,UseATR.class)
				 );
	}
}
