package nts.uk.screen.com.app.command.sys.auth.role;

import java.util.List;

import lombok.Value;

@Value
public class RoleCas005Command {
	/**
	 * class Role : ロール
	 */
	
	// Id
	private String roleId;

	// コード RoleCode
	private String roleCode;

	// ロール種類 RoleType
	private int roleType;

	// 参照範囲 EmployeeReferenceRange
	private int employeeReferenceRange;

	// ロール名称 RoleName
	private String name;

	// 契約コード ContractCode
	private String contractCode;

	// 担当区分 RoleAtr
	private int assignAtr;

	// 会社ID
	private String companyId;
	
	/**
	 * Class RoleByRoleTies : 担当ロール別紐付け
	 */
	
	private String webMenuCd;
	
	/**
	 * Class : 就業ロール
	 */
	//スケジュール画面社員参照
	private int scheduleEmployeeRef;
	//予約画面社員参照
	private int bookEmployeeRef; 
	//代行者指定時社員参照 
	private  int employeeRefSpecAgent;
	//在席照会社員参照
	private int presentInqEmployeeRef; 
	//未来日参照許可 
	private int futureDateRefPermit;
	/**
	 * class WorkPlaceAuthority : 所属職場権限
	 */
	private List<WorkPlaceAuthorityCommand> listWorkPlaceAuthority;
}
