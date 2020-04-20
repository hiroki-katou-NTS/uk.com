package nts.uk.ctx.bs.employee.app.command.employee.employeeinfo.workplacegroup;

import java.util.List;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupCode;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class RegisterWorkplaceGroupCommand {
	/** 職場グループコード */
	private String WKPGRPCode;
	
	/** 職場グループ名称 */
	private String WKPGRPName;
	
	/** 職場グループ種別 */
	private int WKPGRPType;
	
	/** 職場IDリスト */
	private List<String> lstWKPID;
	
	public WorkplaceGroup toDomain(String CID, String WKPGRPID) {
		return new WorkplaceGroup(
				CID, 
				WKPGRPID, 
				new WorkplaceGroupCode(WKPGRPCode), 
				new WorkplaceGroupName(WKPGRPName), 
				EnumAdaptor.valueOf(WKPGRPType, WorkplaceGroupType.class));
	}
}
