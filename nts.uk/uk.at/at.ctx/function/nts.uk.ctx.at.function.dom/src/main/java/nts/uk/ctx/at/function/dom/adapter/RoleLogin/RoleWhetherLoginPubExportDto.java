package nts.uk.ctx.at.function.dom.adapter.RoleLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class RoleWhetherLoginPubExportDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleWhetherLoginPubExportDto {

	/** 就業担当者か. */
	boolean isEmployeeCharge = false;

	/** 給与担当者か. */
	boolean isSalaryProfessional = false;

	/** 人事担当者か. */
	boolean isHumanResOfficer = false;

	/** オフィスヘルパー担当者か. */
	boolean isOfficeHelperPersonne = false;

	/** 個人情報担当者か. */
	boolean isPersonalInformation = false;
}
