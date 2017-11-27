package nts.uk.ctx.bs.employee.infra.repository.employee.history;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyHist;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AffCompanyHistRepositoryImp extends JpaRepository implements AffCompanyHistRepository {

	private static final String DELETE_NO_PARAM = String.join(" ", "DELETE FROM BsymtAffCompanyHist c");

	private static final String DELETE_BY_PERSON_ID = String.join(" ", DELETE_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId");

	private static final String DELETE_BY_PID_EMPID = String.join(" ", DELETE_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId", "AND c.bsymtAffCompanyHistPk.sId = :sId");

	private static final String DELETE_BY_PRIMARY_KEY = String.join(" ", DELETE_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId", "AND c.bsymtAffCompanyHistPk.sId = :sId",
			"AND c.bsymtAffCompanyHistPk.historyId = :histId");

	private static final String SELECT_NO_PARAM = String.join(" ", "SELECT c FROM BsymtAffCompanyHist c");

	private static final String SELECT_BY_PERSON_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.pId = :pId");

	private static final String SELECT_BY_EMPLOYEE_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyHistPk.sId = :sId");

	@Override
	public void add(AffCompanyHist domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(AffCompanyHist domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(AffCompanyHist domain) {
		remove(domain.getPId());
	}

	@Override
	public void remove(String pId, String employeeId, String hisId) {
		this.getEntityManager().createQuery(DELETE_BY_PRIMARY_KEY, BsymtAffCompanyHist.class).setParameter("pId", pId)
				.setParameter("sId", employeeId).setParameter("histId", hisId).executeUpdate();
	}

	@Override
	public void remove(String pId, String employeeId) {
		this.getEntityManager().createQuery(DELETE_BY_PID_EMPID, BsymtAffCompanyHist.class).setParameter("pId", pId)
				.setParameter("sId", employeeId).executeUpdate();
	}

	@Override
	public void remove(String pId) {
		this.getEntityManager().createQuery(DELETE_BY_PERSON_ID, BsymtAffCompanyHist.class).setParameter("pId", pId)
				.executeUpdate();
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfPerson(String personId) {
		List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
				.query(SELECT_BY_PERSON_ID, BsymtAffCompanyHist.class).setParameter("pId", personId).getList();

		return toDomain(lstBsymtAffCompanyHist);
	}

	@Override
	public AffCompanyHist getAffCompanyHistoryOfEmployee(String employeeId) {
		List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
				.query(SELECT_BY_EMPLOYEE_ID, BsymtAffCompanyHist.class).setParameter("sId", employeeId).getList();

		return toDomain(lstBsymtAffCompanyHist);
	}

	private AffCompanyHist toDomain(List<BsymtAffCompanyHist> filter) {

		AffCompanyHist affCompanyHist = new AffCompanyHist();

		for (BsymtAffCompanyHist item : filter) {
			if (affCompanyHist.getPId() == null) {
				affCompanyHist.setPId(item.bsymtAffCompanyHistPk.pId);
			}

			AffCompanyHistByEmployee affCompanyHistByEmployee = affCompanyHist
					.getAffCompanyHistByEmployee(item.bsymtAffCompanyHistPk.sId);

			if (affCompanyHistByEmployee == null) {
				affCompanyHistByEmployee = new AffCompanyHistByEmployee();
				affCompanyHistByEmployee.setSId(item.bsymtAffCompanyHistPk.sId);

				affCompanyHist.addAffCompanyHistByEmployee(affCompanyHistByEmployee);
			}

			AffCompanyHistItem affCompanyHistItem = affCompanyHistByEmployee
					.getAffCompanyHistItem(item.bsymtAffCompanyHistPk.historyId);

			if (affCompanyHistItem == null) {
				affCompanyHistItem = new AffCompanyHistItem();
				affCompanyHistItem.setDestinationData(item.destinationData == 1);
				affCompanyHistItem.setDatePeriod(new DatePeriod(item.startDate, item.endDate));

				affCompanyHistByEmployee.addAffCompanyHistItem(affCompanyHistItem);
			}
		}

		return affCompanyHist;
	}

	private List<BsymtAffCompanyHist> toEntities() {
		return null;
	}
}
