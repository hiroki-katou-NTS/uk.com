package nts.uk.ctx.pereg.infra.repository.roles.functionauth;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc.PerInfoAuthDescRepository;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc.PersonInfoAuthDescription;
import nts.uk.ctx.pereg.infra.entity.roles.functionauth.PpemtPersonInfoFunction;
import nts.uk.shr.infra.permit.data.JpaDescriptionOfAvaiablityPermissionRepositoryBase;

@Stateless
public class PerInfoAuthDescRepoImpl
		extends JpaDescriptionOfAvaiablityPermissionRepositoryBase<PersonInfoAuthDescription, PpemtPersonInfoFunction>
		implements PerInfoAuthDescRepository {

	@Override
	protected Class<PpemtPersonInfoFunction> getEntityClass() {
		return PpemtPersonInfoFunction.class;
	}

	@Override
	protected PpemtPersonInfoFunction createEmptyEntity() {
		return new PpemtPersonInfoFunction();
	}

	@Override
	public List<PersonInfoAuthDescription> getListDesc() {
		return this.findAll();
	}

}
