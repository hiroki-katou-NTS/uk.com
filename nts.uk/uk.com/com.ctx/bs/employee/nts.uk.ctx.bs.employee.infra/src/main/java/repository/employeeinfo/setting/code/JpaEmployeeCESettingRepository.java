package repository.employeeinfo.setting.code;

import java.util.Optional;

import javax.ejb.Stateless;

import entity.employeeinfo.setting.code.BsydtEmployeeCESetting;
import entity.employeeinfo.setting.code.BsydtEmployeeCESettingPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.setting.code.EmployeeCESetting;
import nts.uk.ctx.bs.employee.dom.setting.code.IEmployeeCESettingRepository;

@Stateless
public class JpaEmployeeCESettingRepository extends JpaRepository implements IEmployeeCESettingRepository {
	public static final String SELECT_ALL = "SELECT s FROM BsydtEmployeeCESetting s";
	public static final String SELECT_BY_COM_ID = SELECT_ALL + " WHERE s.bsydtEmployeeCESettingPk.cId = :companyId";

	@Override
	public Optional<EmployeeCESetting> getByComId(String companyId) {
		Optional<BsydtEmployeeCESetting> entity = this.queryProxy()
				.query(SELECT_BY_COM_ID, BsydtEmployeeCESetting.class).setParameter("companyId", companyId).getSingle();

		if (!entity.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(toDomain(entity.get()));
	}

	@Override
	public void saveSetting(EmployeeCESetting domain) {
		Optional<BsydtEmployeeCESetting> entity = this.queryProxy()
				.query(SELECT_BY_COM_ID, BsydtEmployeeCESetting.class).setParameter("companyId", domain.getCompanyId())
				.getSingle();

		if (!entity.isPresent()) {
			commandProxy().insert(toEntity(domain));
		} else {
			BsydtEmployeeCESetting _update = entity.get();

			// update value from domain to entity
			_update.setDigitNumb(domain.getDigitNumb().v());
			_update.setCeMethodAtr(domain.getCeMethodAtr().value);

			// commit data to db
			commandProxy().update(_update);
		}
	}

	@Override
	public void removeSetting(String companyId) {
		BsydtEmployeeCESettingPk pkey = new BsydtEmployeeCESettingPk(companyId);

		Optional<BsydtEmployeeCESetting> entity = this.queryProxy()
				.query(SELECT_BY_COM_ID, BsydtEmployeeCESetting.class).setParameter("companyId", companyId).getSingle();

		if (entity.isPresent()) {
			this.commandProxy().remove(BsydtEmployeeCESetting.class, pkey);
		}
	}

	@Override
	public void removeSetting(EmployeeCESetting domain) {
		this.removeSetting(domain.getCompanyId());
	}

	private EmployeeCESetting toDomain(BsydtEmployeeCESetting entity) {
		return EmployeeCESetting.createFromJavaType(entity.getBsydtEmployeeCESettingPk().getCId(),
				entity.getCeMethodAtr(), entity.getDigitNumb());
	}

	private BsydtEmployeeCESetting toEntity(EmployeeCESetting domain) {
		BsydtEmployeeCESettingPk pkey = new BsydtEmployeeCESettingPk(domain.getCompanyId());

		return new BsydtEmployeeCESetting(pkey, domain.getCeMethodAtr().value, domain.getDigitNumb().v());
	}
}
