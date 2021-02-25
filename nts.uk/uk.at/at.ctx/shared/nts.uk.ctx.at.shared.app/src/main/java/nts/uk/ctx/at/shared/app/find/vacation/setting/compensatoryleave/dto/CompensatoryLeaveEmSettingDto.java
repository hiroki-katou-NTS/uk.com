/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 雇用の代休管理設定 Dto
public class CompensatoryLeaveEmSettingDto {

	// 会社ID
	/** The company id. */
	public String companyId;

	// 雇用区分コード
	/** The employment code. */
	public String employmentCode;
    //管理区分
	/** The is managed. */
	public int isManaged;

	public static CompensatoryLeaveEmSettingDto toDto(CompensatoryLeaveEmSetting domain) {
		return new CompensatoryLeaveEmSettingDto (
				domain.getCompanyId(), 
				domain.getEmploymentCode().v(), 
				domain.getIsManaged().value);
	}
}

