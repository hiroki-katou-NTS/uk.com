package repository.person.setting.copysetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.info.setting.copysetting.BsystEmployeeCopySetting;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySetting;
import nts.uk.ctx.bs.person.dom.person.setting.copysetting.EmpCopySettingRepository;

@Stateless
public class JpaEmpCopySettingRepository extends JpaRepository implements EmpCopySettingRepository {

	private static final String SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING = "SELECT cs "
			+ "FROM BsystEmployeeCopySetting cs " + "WHERE cs.companyId =:companyId";

	@Override
	public List<EmpCopySetting> find(String companyId) {
		return this.queryProxy().query(SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING, BsystEmployeeCopySetting.class)
				.setParameter("companyId", companyId).getList().stream().map(x -> toDomain(x))
				.collect(Collectors.toList());

	}

	private EmpCopySetting toDomain(BsystEmployeeCopySetting entity) {

		return new EmpCopySetting(entity.BsystEmployeeCopySettingPk.categoryId, entity.companyId);
	}
}
