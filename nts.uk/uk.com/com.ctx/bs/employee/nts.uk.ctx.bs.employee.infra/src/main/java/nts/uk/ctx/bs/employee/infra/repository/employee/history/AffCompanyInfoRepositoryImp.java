package nts.uk.ctx.bs.employee.infra.repository.employee.history;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyInfo;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyInfoPk;

@Stateless
public class AffCompanyInfoRepositoryImp extends JpaRepository implements AffCompanyInfoRepository {

	private static final String SELECT_NO_PARAM = String.join(" ", "SELECT c FROM BsymtAffCompanyInfo c");

	private static final String SELECT_BY_HISTID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.historyId = : histId");

	@Override
	public void add(AffCompanyInfo domain) {
	}

	@Override
	public void update(AffCompanyInfo domain) {
	}

	@Override
	public void remove(AffCompanyInfo domain) {
		this.remove(domain.getHistoryId());
	}

	@Override
	public void remove(String histId) {
		this.commandProxy().remove(BsymtAffCompanyInfo.class, new BsymtAffCompanyInfoPk(histId));
	}

	@Override
	public AffCompanyInfo getAffCompanyInfoByHistId(String histId) {

		return this.queryProxy().query(SELECT_BY_HISTID, BsymtAffCompanyInfo.class).setParameter("histId", histId)
				.getSingle(m -> AffCompanyInfo.createFromJavaType(m.bsymtAffCompanyInfoPk.historyId,
						m.recruitmentCategoryCode, m.adoptionDate, m.retirementAllowanceCalcStartDate))
				.orElse(null);
	}
}
