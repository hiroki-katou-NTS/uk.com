package nts.uk.ctx.at.shared.dom.workmanagementmultiple;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 *  
 * @author hieult
 *
 */
@Getter
@AllArgsConstructor
public class WorkManagementMultiple {
			
	String companyID;
	
	UseATR useATR;

	public static WorkManagementMultiple createFromJavaType( String companyID, int useATR) {
		
		 return new WorkManagementMultiple( 
				 companyID,
				 EnumAdaptor.valueOf(useATR,UseATR.class)
				 );
	}
}
