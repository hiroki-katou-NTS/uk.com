package nts.uk.ctx.at.function.infra.repository.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthlyRepository;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KfnmtInitialDisplayMonthly;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KfnmtInitialDisplayMonthlyPK;
@Stateless
public class JpaInitialDisplayMonthlyRepo extends JpaRepository implements InitialDisplayMonthlyRepository  {

	private static final String Get_ALL_DIS_MON = "SELECT c FROM KfnmtInitialDisplayMonthly c "
			+ " WHERE c.kfnmtInitialDisplayMonthlyPK.companyID = :companyID";
	private static final String Get_DIS_MON_BY_CODE = Get_ALL_DIS_MON
			+ " AND c.kfnmtInitialDisplayMonthlyPK.monthlyPfmFormatCode = :monthlyPfmFormatCode";
	
	
	@Override
	public List<InitialDisplayMonthly> getAllInitialDisMon(String companyID) {
		List<InitialDisplayMonthly> data = this.queryProxy().query(Get_ALL_DIS_MON,KfnmtInitialDisplayMonthly.class)
				.setParameter("companyID", companyID)
				.getList(c->c.toDomain());
		return data;
	}

	@Override
	public Optional<InitialDisplayMonthly> getInitialDisplayMon(String companyID, String monthlyPfmFormatCode) {
		Optional<InitialDisplayMonthly> data = this.queryProxy().query(Get_DIS_MON_BY_CODE,KfnmtInitialDisplayMonthly.class)
				.setParameter("companyID", companyID)
				.setParameter("monthlyPfmFormatCode", monthlyPfmFormatCode)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void deleteInitialDisplayMonthly(String companyID, String monthlyPfmFormatCode) {
		KfnmtInitialDisplayMonthly newEntity = this.queryProxy().find(new KfnmtInitialDisplayMonthlyPK(companyID,monthlyPfmFormatCode), KfnmtInitialDisplayMonthly.class).get();
		this.commandProxy().remove(newEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void updateInitialDisplayMonthly(InitialDisplayMonthly initialDisplayMonthly) {
		KfnmtInitialDisplayMonthly newEntity = KfnmtInitialDisplayMonthly.toEntity(initialDisplayMonthly);
		this.commandProxy().update(newEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void addInitialDisplayMonthly(InitialDisplayMonthly initialDisplayMonthly) {
		KfnmtInitialDisplayMonthly newEntity = KfnmtInitialDisplayMonthly.toEntity(initialDisplayMonthly);
		this.commandProxy().insert(newEntity);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByCid(String companyID) {
		List<KfnmtInitialDisplayMonthly> data = this.queryProxy().query(Get_ALL_DIS_MON,KfnmtInitialDisplayMonthly.class)
				.setParameter("companyID", companyID)
				.getList();
		this.commandProxy().removeAll(data);
		this.getEntityManager().flush();
	}

}
