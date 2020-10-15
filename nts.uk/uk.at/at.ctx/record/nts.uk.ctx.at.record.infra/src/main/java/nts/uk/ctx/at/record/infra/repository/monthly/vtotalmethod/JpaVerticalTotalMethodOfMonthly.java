package nts.uk.ctx.at.record.infra.repository.monthly.vtotalmethod;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
//import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.WorkTypeClassification;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcstVertMonMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;

/**
 * The Class JpaVerticalTotalMethodOfMonthly.
 */
@Stateless
public class JpaVerticalTotalMethodOfMonthly extends JpaRepository implements VerticalTotalMethodOfMonthlyRepository {

	/** The Constant FIND_BY_CID. */
	private static final String FIND_BY_CID =
			"SELECT a FROM KrcstVertMonMethod a "
			+ "WHERE a.cid = :companyId ";
	
	private static final String REMOVE_BY_CID =
			"DELETE FROM KrcstVertMonMethod a "
			+ "WHERE a.cid = :companyId ";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<VerticalTotalMethodOfMonthly> findByCid(String companyId) {
		
		val vertDaysList = this.queryProxy()
				.query(FIND_BY_CID, KrcstVertMonMethod.class)
				.setParameter("companyId", companyId)
				.getList();
		
		if (vertDaysList == null || vertDaysList.size() == 0) {
			return Optional.empty();
		}
		
		return Optional.of(toDomain(vertDaysList));
	}
	
	
	/**
	 * To domain.
	 *
	 * @param lstVertical the lst vertical
	 * @return the vertical total method of monthly
	 */
	public VerticalTotalMethodOfMonthly toDomain(List<KrcstVertMonMethod> lstVertical) {
		KrcstVertMonMethod entity = lstVertical.get(0);
		VerticalTotalMethodOfMonthly setting = new VerticalTotalMethodOfMonthly(entity.getCid());
		setting.setTransferAttendanceDays(TADaysCountOfMonthlyAggr.of(
				EnumAdaptor.valueOf(entity.getTransAttendDay(), TADaysCountCondOfMonthlyAggr.class)));
		return setting;
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst vert mon method
	 */
	public KrcstVertMonMethod toDbType(VerticalTotalMethodOfMonthly setting) {
		KrcstVertMonMethod entity = new KrcstVertMonMethod();
		entity.setCid(setting.getCompanyId());
		entity.setTransAttendDay(setting.getTransferAttendanceDays().getTADaysCountCondition().value);
		return entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#insert(nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly)
	 */
	@Override
	public void insert(VerticalTotalMethodOfMonthly setting) {
		//List<KrcstVertMonMethod> lstEntity = toDbType(setting);
		this.commandProxy().insert(toDbType(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#update(nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly)
	 */
	@Override
	public void update(VerticalTotalMethodOfMonthly setting) {
		// Remove all old records
		this.getEntityManager().createQuery(REMOVE_BY_CID)
			.setParameter("companyId", setting.getCompanyId())
			.executeUpdate();
		
		// Then add all new records
		this.insert(setting);
	}

}
