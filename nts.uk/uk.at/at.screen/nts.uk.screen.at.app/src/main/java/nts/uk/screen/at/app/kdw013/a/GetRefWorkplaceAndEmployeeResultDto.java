package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;

/**
 * 
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
public class GetRefWorkplaceAndEmployeeResultDto {
	
	/** 社員の所属情報(Map<社員ID,職場ID>)*/
	private Map<String, String> employeeInfos;
	
	/** List＜社員ID（List）から社員コードと表示名を取得＞*/
	private List<EmployeeBasicInfoImport> lstEmployeeInfo;
	 
	/** List＜職場情報一覧＞ */
	private List<WorkplaceInfoDto> workplaceInfos;
}
