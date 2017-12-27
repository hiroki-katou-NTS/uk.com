package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// 利用単位の設定
public class PublicHolidayManagementUsageUnit extends DomainObject{
	// 社員の公休管理をする
	private boolean isManageEmployeePublicHd ;
	// 職場の公休管理をする
	private boolean isManageWkpPublicHd ;
	// 雇用の公休管理をする
	private boolean isManageEmpPublicHd ;
	
	
}
