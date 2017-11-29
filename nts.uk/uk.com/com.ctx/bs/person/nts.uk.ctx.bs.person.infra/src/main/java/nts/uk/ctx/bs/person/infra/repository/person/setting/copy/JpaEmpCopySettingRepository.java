package nts.uk.ctx.bs.person.infra.repository.person.setting.copy;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySetting;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingRepository;
import nts.uk.ctx.bs.person.infra.entity.person.info.setting.copy.PpestEmployeeCopySetting;

@Stateless
public class JpaEmpCopySettingRepository extends JpaRepository implements EmpCopySettingRepository {

	private static final String SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING = "SELECT cs "
			+ "FROM PpestEmployeeCopySetting cs " + "WHERE cs.companyId =:companyId";

	@Override
	public List<EmpCopySetting> find(String companyId) {
		return this.queryProxy().query(SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING, PpestEmployeeCopySetting.class)
				.setParameter("companyId", companyId).getList().stream().map(x -> toDomain(x))
				.collect(Collectors.toList());

	}

	private EmpCopySetting toDomain(PpestEmployeeCopySetting entity) {

		return EmpCopySetting.createFromJavaType(entity.ppestEmployeeCopySettingPk.categoryId, entity.companyId);
	}
}
