package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHeadPK;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHeadPK_;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead_;
import nts.uk.ctx.pr.report.infra.repository.salarydetail.memento.JpaSalaryOutputSettingGetMemento;
import nts.uk.ctx.pr.report.infra.repository.salarydetail.memento.JpaSalaryOutputSettingSetMemento;

/**
 * The Class JpaSalaryOutputSettingRepository.
 */
@Stateless
public class JpaSalaryOutputSettingRepository extends JpaRepository implements SalaryOutputSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryOutputSettingRepository#create(nts.uk.ctx.pr.report.dom.
	 * salarydetail.outputsetting.SalaryOutputSetting)
	 */
	@Override
	public void create(SalaryOutputSetting outputSetting) {
		QlsptPaylstFormHead entity = new QlsptPaylstFormHead();
		outputSetting.saveToMemento(new JpaSalaryOutputSettingSetMemento(entity));
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryOutputSettingRepository#update(nts.uk.ctx.pr.report.dom.
	 * salarydetail.outputsetting.SalaryOutputSetting)
	 */
	@Override
	public void update(SalaryOutputSetting outputSetting) {
		QlsptPaylstFormHead entity = new QlsptPaylstFormHead();
		outputSetting.saveToMemento(new JpaSalaryOutputSettingSetMemento(entity));
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryOutputSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyCode, String salaryOutputSettingCode) {
		Optional<QlsptPaylstFormHead> entity = this.queryProxy()
				.find(new QlsptPaylstFormHeadPK(companyCode, salaryOutputSettingCode), QlsptPaylstFormHead.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(entity.get());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryOutputSettingRepository#findByCode(java.lang.String,
	 * java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryOutputSettingRepository#isExist(java.lang.String, java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryOutputSettingRepository#findAll(java.lang.String)
	 */
	@Override
	public List<SalaryOutputSetting> findAll(String companyCode) {
		EntityManager em = this.getEntityManager();

		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QlsptPaylstFormHead> cq = cb.createQuery(QlsptPaylstFormHead.class);
		Root<QlsptPaylstFormHead> root = cq.from(QlsptPaylstFormHead.class);

		// Create query.
		cq.where(cb.equal(root.get(QlsptPaylstFormHead_.qlsptPaylstFormHeadPK).get(QlsptPaylstFormHeadPK_.ccd),
				companyCode))
				.orderBy(cb
						.asc(root.get(QlsptPaylstFormHead_.qlsptPaylstFormHeadPK).get(QlsptPaylstFormHeadPK_.formCd)));

		// Select.
		return em.createQuery(cq).getResultList().stream().map(entity -> {
			return new SalaryOutputSetting(new JpaSalaryOutputSettingGetMemento(entity));
		}).collect(Collectors.toList());

	}
}
