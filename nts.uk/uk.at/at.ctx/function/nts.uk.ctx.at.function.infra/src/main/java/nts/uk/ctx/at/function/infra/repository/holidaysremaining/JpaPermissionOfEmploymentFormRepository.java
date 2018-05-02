package nts.uk.ctx.at.function.infra.repository.holidaysremaining;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtPerEmpForm;
import nts.uk.shr.infra.permit.data.AvaiablityPermissionRepositoryBase;

@Stateless
public class JpaPermissionOfEmploymentFormRepository
		extends AvaiablityPermissionRepositoryBase<PermissionOfEmploymentForm, KfnmtPerEmpForm>
		implements PermissionOfEmploymentFormRepository {

	@Override
	protected Class<KfnmtPerEmpForm> getEntityClass() {
		return KfnmtPerEmpForm.class;
	}

	@Override
	protected KfnmtPerEmpForm createEmptyEntity() {
		return new KfnmtPerEmpForm();
	}

}
