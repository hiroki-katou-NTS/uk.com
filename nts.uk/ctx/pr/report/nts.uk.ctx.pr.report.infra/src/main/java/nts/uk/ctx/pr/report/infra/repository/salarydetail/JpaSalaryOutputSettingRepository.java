package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHeadPK;
import nts.uk.ctx.pr.report.infra.repository.salarydetail.memento.JpaSalaryOutputSettingGetMemento;
import nts.uk.ctx.pr.report.infra.repository.salarydetail.memento.JpaSalaryOutputSettingSetMemento;

/**
 * The Class JpaSalaryOutputSettingRepository.
 */
@Stateless
public class JpaSalaryOutputSettingRepository extends JpaRepository implements SalaryOutputSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository#create(nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSetting)
	 */
	@Override
	public void create(SalaryOutputSetting outputSetting) {
		QlsptPaylstFormHead entity = new QlsptPaylstFormHead();
		outputSetting.saveToMemento(new JpaSalaryOutputSettingSetMemento(entity));
		this.commandProxy().insert(entity);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository#update(nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSetting)
	 */
	@Override
	public void update(SalaryOutputSetting outputSetting) {
		QlsptPaylstFormHead entity = new QlsptPaylstFormHead();
		outputSetting.saveToMemento(new JpaSalaryOutputSettingSetMemento(entity));
		this.commandProxy().update(entity);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyCode, String salaryOutputSettingCode) {
		Optional<QlsptPaylstFormHead> entity = this.queryProxy()
				.find(new QlsptPaylstFormHeadPK(companyCode, salaryOutputSettingCode), QlsptPaylstFormHead.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(entity.get());
		}

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository#findByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public SalaryOutputSetting findByCode(String companyCode, String salaryOutputSettingCode) {
		Optional<QlsptPaylstFormHead> entity = this.queryProxy()
				.find(new QlsptPaylstFormHeadPK(companyCode, salaryOutputSettingCode), QlsptPaylstFormHead.class);

		if (entity.isPresent()) {
			return new SalaryOutputSetting(new JpaSalaryOutputSettingGetMemento(entity.get()));
		}

		throw new RuntimeException("Entity not found.");
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository#isExist(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean isExist(String companyCode, String salaryOutputSettingCode) {
		Optional<QlsptPaylstFormHead> entity = this.queryProxy()
				.find(new QlsptPaylstFormHeadPK(companyCode, salaryOutputSettingCode), QlsptPaylstFormHead.class);

		if (entity.isPresent()) {
			return true;
		}
		return false;
	}
}
