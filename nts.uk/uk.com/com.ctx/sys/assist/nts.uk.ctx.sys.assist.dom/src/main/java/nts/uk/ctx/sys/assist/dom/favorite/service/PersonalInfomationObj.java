package nts.uk.ctx.sys.assist.dom.favorite.service;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonalInfomationObj {
	// 社員ID
	private String sid;
	// 階層コード
	private String hierarchyCode;
	// Optional<並び順>
	private Optional<String> order;
	// 職位コード
	private String positionCode;
	// 社員コード
	private String employeeCode;
}
