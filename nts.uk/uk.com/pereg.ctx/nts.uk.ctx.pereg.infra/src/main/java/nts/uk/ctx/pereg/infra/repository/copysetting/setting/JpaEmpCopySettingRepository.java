package nts.uk.ctx.pereg.infra.repository.copysetting.setting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySetting;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.ctx.pereg.infra.entity.copysetting.setting.PpestEmployeeCopySetting;
import nts.uk.ctx.pereg.infra.entity.copysetting.setting.PpestEmployeeCopySettingPk;

@Stateless
public class JpaEmpCopySettingRepository extends JpaRepository implements EmpCopySettingRepository {

	private static final String SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING = "SELECT cs "
			+ "FROM PpestEmployeeCopySetting cs " + "WHERE cs.companyId =:companyId";

	private final static String COUNT_PERINFOCTGIN_COPYSETING = "SELECT COUNT(i) FROM PpestEmployeeCopySetting i "
			+ "WHERE i.ppestEmployeeCopySettingPk.categoryId = :categoryId AND i.companyId = :companyId";

	@Override
	public List<EmpCopySetting> find(String companyId) {
		return this.queryProxy().query(SELECT_EMP_COPY_SETTING_BY_COMID_QUERY_STRING, PpestEmployeeCopySetting.class)
				.setParameter("companyId", companyId).getList().stream().map(x -> toDomain(x))
				.collect(Collectors.toList());

	}

	private EmpCopySetting toDomain(PpestEmployeeCopySetting entity) {

		return EmpCopySetting.createFromJavaType(entity.ppestEmployeeCopySettingPk.categoryId, entity.companyId);
	}

	@Override
	public void updatePerInfoCtgInCopySetting(String perInfoCtgId, String companyId) {
		boolean alreadyExist = checkPerInfoCtgAlreadyCopy(perInfoCtgId, companyId);
		if (!alreadyExist) {

			PpestEmployeeCopySettingPk key = new PpestEmployeeCopySettingPk(perInfoCtgId);
			PpestEmployeeCopySetting entity = new PpestEmployeeCopySetting(key, companyId);
			this.commandProxy().insert(entity);
		}

	}

	@Override
	public boolean checkPerInfoCtgAlreadyCopy(String perInfoCtgId, String companyId) {
		Optional<Long> a = this.queryProxy().query(COUNT_PERINFOCTGIN_COPYSETING, Long.class)
				.setParameter("categoryId", perInfoCtgId).setParameter("companyId", companyId).getSingle();
		return a.isPresent() ? (a.get().intValue() > 0 ? true : false) : false;
	}

	private PpestEmployeeCopySetting toEntity(EmpCopySetting domain) {
		PpestEmployeeCopySettingPk key = new PpestEmployeeCopySettingPk(domain.getCategoryId());

		return new PpestEmployeeCopySetting(key, domain.getCompanyId());
	}

	@Override
	public void addCtgCopySetting(EmpCopySetting newCtg) {
		this.commandProxy().insert(toEntity(newCtg));

	}

	@Override
	public void removeCtgCopySetting(String perInfoCtgId) {
		if (this.queryProxy().find(new PpestEmployeeCopySettingPk(perInfoCtgId), PpestEmployeeCopySetting.class)
				.isPresent()) {
			this.commandProxy().remove(PpestEmployeeCopySetting.class, new PpestEmployeeCopySettingPk(perInfoCtgId));

		}

	}
}
