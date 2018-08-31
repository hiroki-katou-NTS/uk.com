package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PermissionOfEmploymentFinder {
	@Inject
	private PermissionOfEmploymentFormRepository permissionOfEmploymentFormRepository;

	/** 機能NO　=　4(年間勤務表) */
	private static final int FUNCTION_NO = 4;

	/**
	 * ドメインモデル「就業帳票の権限」を取得する
	 * 
	 * @return
	 */
	public PermissionOfEmploymentFormDto getPermissionOfEmploymentForm() {
		String companyId = AppContexts.user().companyId();
		String employeeRoleId = AppContexts.user().roles().forAttendance();

		Optional<PermissionOfEmploymentForm> permission = this.permissionOfEmploymentFormRepository.find(companyId,
				employeeRoleId, FUNCTION_NO);
		if (permission.isPresent()) {
			return new PermissionOfEmploymentFormDto(permission.get().getCompanyId(), permission.get().getRoleId(),
					permission.get().getFunctionNo(), permission.get().isAvailable());
		}

		return new PermissionOfEmploymentFormDto(companyId, employeeRoleId, FUNCTION_NO, false);
	}
}
