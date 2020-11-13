/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;

/**
 * The Class JpaOutputItemDailyWorkScheduleRepository.
 * author: HoangDD
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaOutputItemDailyWorkScheduleRepository extends JpaRepository implements OutputItemDailyWorkScheduleRepository {
	
	private static final String SELECT_BY_LAYOUT_ID = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.layoutId = :layoutId";
	
	private static final String GET_ATD_BY_LAYOUT_ID = "SELECT atd FROM KfnmtRptWkDaiOutatd atd"
			+ " WHERE atd.id.layoutId = :layoutId ORDER BY atd.id.orderNo";

	private static final String GET_NOTE_BY_LAYOUT_ID = "SELECT note FROM KfnmtRptWkDaiOutnote note"
			+ " WHERE note.id.layoutId = :layoutId";

	@Override
	public Optional<OutputItemDailyWorkSchedule> findByLayoutId(String layoutId) {
		// get all attendance display item by layoutId
		List<KfnmtRptWkDaiOutatd> lstKfnmtRptWkDaiOutatds = this.queryProxy()
				.query(GET_ATD_BY_LAYOUT_ID, KfnmtRptWkDaiOutatd.class)
				.setParameter("layoutId", layoutId)
				.getList();
		
		// get lst KfnmtRptWkDaiOutnote by layoutId
		List<KfnmtRptWkDaiOutnote> lstKfnmtRptWkDaiOutnotes = this.queryProxy()
				.query(GET_NOTE_BY_LAYOUT_ID, KfnmtRptWkDaiOutnote.class)
				.setParameter("layoutId", layoutId)
				.getList();

		// map to domain
		return this.queryProxy()
				.query(SELECT_BY_LAYOUT_ID, KfnmtRptWkDaiOutItem.class)
				.setParameter("layoutId", layoutId)
				.getSingle().map(t -> {
					t.setLstKfnmtRptWkDaiOutatds(lstKfnmtRptWkDaiOutatds);
					t.setLstKfnmtRptWkDaiOutnotes(lstKfnmtRptWkDaiOutnotes);
					return new OutputItemDailyWorkSchedule(t);
				});
	}

	@Override
	public void update(OutputItemDailyWorkSchedule domain, int selectionType, String companyId, String employeeId) {
		// get all attendance display item by layoutId
		List<KfnmtRptWkDaiOutatd> lstKfnmtRptWkDaiOutatds = this.queryProxy()
				.query(GET_ATD_BY_LAYOUT_ID, KfnmtRptWkDaiOutatd.class)
				.setParameter("layoutId", domain.getLayoutId())
				.getList();
		this.commandProxy().removeAll(lstKfnmtRptWkDaiOutatds);
		this.getEntityManager().flush();

		Optional<KfnmtRptWkDaiOutItem> oEntity = this.getOutItemByLayoutId(domain.getLayoutId());
		if (oEntity.isPresent()) {
			KfnmtRptWkDaiOutItem entity = oEntity.get();
			domain.saveToMemento(entity);
			this.commandProxy().update(entity);
		}
	}
	
	private Optional<KfnmtRptWkDaiOutItem> getOutItemByLayoutId(String layoutId) {
		return this.queryProxy()
				.query(SELECT_BY_LAYOUT_ID, KfnmtRptWkDaiOutItem.class)
				.setParameter("layoutId", layoutId)
				.getSingle();
	}
}
