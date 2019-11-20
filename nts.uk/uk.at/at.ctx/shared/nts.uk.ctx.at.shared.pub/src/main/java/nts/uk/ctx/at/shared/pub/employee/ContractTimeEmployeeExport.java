/**
 * 
 */
package nts.uk.ctx.at.shared.pub.employee;

import lombok.Data;

/**
 * @author hieult
 *
 */
@Data
public class ContractTimeEmployeeExport {

		private String employeeID;
		
		private int contractTime;

		/**
		 * @param employeeID
		 * @param contractTime
		 */
		public ContractTimeEmployeeExport(String employeeID, int contractTime) {
			super();
			this.employeeID = employeeID;
			this.contractTime = contractTime;
		}

		
		
		
}
