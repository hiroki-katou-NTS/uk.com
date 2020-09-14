package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@Getter
@Setter
@NoArgsConstructor
public class EmploymentManageDistinctDto {
	// 管理区分
	private String employmentCode;
	// 雇用コード
	private ManageDistinct isManage;
}
