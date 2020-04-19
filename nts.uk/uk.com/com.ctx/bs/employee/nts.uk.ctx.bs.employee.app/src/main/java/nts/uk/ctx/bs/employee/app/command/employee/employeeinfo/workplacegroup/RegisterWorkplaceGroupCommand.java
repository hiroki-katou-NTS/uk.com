package nts.uk.ctx.bs.employee.app.command.employee.employeeinfo.workplacegroup;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupCode;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class RegisterWorkplaceGroupCommand {
	/** 職場グループID */
	private String WKPGRPID;
	
	/** 職場グループコード */
	private String WKPGRPCode;
	
	/** 職場グループ名称 */
	private String WKPGRPName;
	
	/** 職場グループ種別 */
	private int WKPGRPType;
	
	public WorkplaceGroup toDomain() {
		String CID = AppContexts.user().companyId();
		return new WorkplaceGroup(
				CID, 
				WKPGRPID, 
				new WorkplaceGroupCode(WKPGRPCode), 
				new WorkplaceGroupName(WKPGRPName), 
				EnumAdaptor.valueOf(WKPGRPType, WorkplaceGroupType.class));
	}
}
