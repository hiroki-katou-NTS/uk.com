package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.SettingTypeUsed;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 利用する種類設定DTO
 */
@Data
@Builder
public class SettingTypeUsedDto {
	/** 承認ルート区分 */
	private Integer employmentRootAtr;

	/** 申請種類 */
	private Integer applicationType;

	/** 確認ルート種類 */
	private Integer confirmRootType;

	/** 利用する */
	private Integer notUseAtr;

	public static SettingTypeUsedDto fromValueObject(SettingTypeUsed domain) {
		return SettingTypeUsedDto.builder().applicationType(domain.getApplicationType().map(x -> x.value).orElse(null))
				.confirmRootType(domain.getConfirmRootType().map(x -> x.value).orElse(null))
				.employmentRootAtr(domain.getEmploymentRootAtr().value).notUseAtr(domain.getNotUseAtr().value).build();
	}

	public SettingTypeUsed toValueObject() {
		return new SettingTypeUsed(EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
				Optional.ofNullable(
						applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class)),
				Optional.ofNullable(confirmRootType == null ? null
						: EnumAdaptor.valueOf(confirmRootType, ConfirmationRootType.class)),
				EnumAdaptor.valueOf(notUseAtr, NotUseAtr.class));
	}
}
