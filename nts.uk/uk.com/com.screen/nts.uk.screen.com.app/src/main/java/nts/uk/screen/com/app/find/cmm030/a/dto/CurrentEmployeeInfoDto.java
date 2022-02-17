package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.RoleDto;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;

@Data
@AllArgsConstructor
public class CurrentEmployeeInfoDto {

	/**
	 * 社員ID（List）
	 */
	private List<ResultRequest600Export> employees;
	
	/**
	 * 職場情報一覧
	 */
	private List<WorkplaceInforParam> workplaceInfos;
	
	/**
	 * ロール
	 */
	private RoleDto roleDto;
}
