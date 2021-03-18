package nts.uk.ctx.office.dom.favorite.service;

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
	private Optional<Integer> order;
	// 職位コード
	private String positionCode;
	// 社員コード
	private String employeeCode;
	
	public Integer getOrderOptional() {
		return this.order.orElse(null);
	}
}
