/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatdPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnotePK;

/**
 * The Class JpaOutputItemDailyWorkScheduleRepository.
 * author: HoangDD
 */
@Stateless
public class JpaOutputItemDailyWorkScheduleRepository extends JpaRepository implements OutputItemDailyWorkScheduleRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#update(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void update(OutputItemDailyWorkSchedule domain) {
		EntityManager em = this.getEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

        // create delete
        CriteriaDelete<KfnmtRptWkDaiOutatd> delete = cb.createCriteriaDelete(KfnmtRptWkDaiOutatd.class);

        // set the root class
        Root<KfnmtRptWkDaiOutatd> root = delete.from(KfnmtRptWkDaiOutatd.class);

        // set where clause
        delete.where(cb.equal(root.get(KfnmtAttendanceDisplay_.id).get(KfnmtAttendanceDisplayPK_.cid), domain.getCompanyID()),
        				cb.equal(root.get(KfnmtAttendanceDisplay_.id).get(KfnmtAttendanceDisplayPK_.itemCode), domain.getItemCode().v()));

        // perform update
        em.createQuery(delete).executeUpdate();
		
		this.commandProxy().update(this.toEntity(domain));
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#findByCid(java.lang.String)
	 */
	@Override
	public List<OutputItemDailyWorkSchedule> findByCid(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create builder
		CriteriaBuilder builder = em.getCriteriaBuilder();

		// Create query
		CriteriaQuery<KfnmtRptWkDaiOutItem> cq = builder.createQuery(KfnmtRptWkDaiOutItem.class);

		// From table
		Root<KfnmtRptWkDaiOutItem> root = cq.from(KfnmtRptWkDaiOutItem.class);

		// Add where condition
		cq.where(builder.equal(root.get(KfnmtItemWorkSchedule_.id).get(KfnmtItemWorkSchedulePK_.cid),companyId));
		cq.orderBy(builder.asc(root.get(KfnmtItemWorkSchedule_.id).get(KfnmtItemWorkSchedulePK_.cid)));
		// Get results
		List<KfnmtItemWorkSchedule> results = em.createQuery(cq).getResultList();

		// Check empty
		if (CollectionUtil.isEmpty(results)) {
			return Collections.emptyList();
		}

		// Return
		return results.stream().map(item -> new OutputItemDailyWorkSchedule(new JpaOutputItemDailyWorkScheduleGetMemento(item)))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#findByCidAndCode(java.lang.String, int)
	 */
	@Override
	@SneakyThrows
	public Optional<OutputItemDailyWorkSchedule> findByLayoutId(String layoutId) {
		List<KfnmtRptWkDaiOutatd> lstKfnmtRptWkDaiOutatds = new ArrayList<>();
		String sqlJDBC1 = "select * from KFNMT_RPT_WK_DAI_OUTATD where LAYOUT_ID = ? ORDER BY ORDER_NO";
		try (PreparedStatement statement1 = this.connection().prepareStatement(sqlJDBC1)) {
			statement1.setString(1, layoutId);
			lstKfnmtRptWkDaiOutatds
					.addAll(new NtsResultSet(statement1.executeQuery()).getList(rec -> {
						KfnmtRptWkDaiOutatdPK pk = new KfnmtRptWkDaiOutatdPK();
						pk.setLayoutId(rec.getString("LAYOUT_ID"));
						pk.setOrderNo(rec.getLong("ORDER_NO"));
						KfnmtRptWkDaiOutatd entity = new KfnmtRptWkDaiOutatd();
						entity.setId(pk);
						entity.setAtdDisplay(rec.getBigDecimal("ATD_DISPLAY"));
						entity.setCid(rec.getString("CID"));
						return entity;
					}));
		}

		List<KfnmtRptWkDaiOutnote> lstKfnmtRptWkDaiOutnotes = new ArrayList<>();
		String sqlJDBC2 = "select * from KFNMT_RPT_WK_DAI_OUTNOTE where LAYOUT_ID = ?";
		try (PreparedStatement statement2 = this.connection().prepareStatement(sqlJDBC2)) {
			statement2.setString(1, layoutId);
			lstKfnmtRptWkDaiOutnotes
					.addAll(new NtsResultSet(statement2.executeQuery()).getList(rec -> {
						KfnmtRptWkDaiOutnotePK pk = new KfnmtRptWkDaiOutnotePK();
						pk.setLayoutId(rec.getString("LAYOUT_ID"));
						pk.setPrintItem(rec.getLong("PRINT_ITEM"));
						KfnmtRptWkDaiOutnote entity = new KfnmtRptWkDaiOutnote();
						entity.setId(pk);
						entity.setUseCls(rec.getBigDecimal("USE_CLS"));
						entity.setCid(rec.getString("CID"));
						return entity;
					}));
		}

		Map<String, List<KfnmtRptWkDaiOutatd>> mapKfnmtAttendanceDisplay = lstKfnmtRptWkDaiOutatds
				.stream().collect(Collectors.groupingBy(item -> item.getId().getLayoutId()));
		Map<String, List<KfnmtRptWkDaiOutnote>> mapKfnmtPrintRemarkCont = lstKfnmtRptWkDaiOutnotes
				.stream().collect(Collectors.groupingBy(item -> item.getId().getLayoutId()));
		String sqlJDBC3 = "select * from KFNMT_RPT_WK_DAI_OUT_ITEM where LAYOUT_ID = ?";
		try (PreparedStatement statement3 = this.connection().prepareStatement(sqlJDBC3)) {
			statement3.setString(1, layoutId);
			return new NtsResultSet(statement3.executeQuery()).getSingle(rec -> {
				KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
				entity.setLayoutId(rec.getString("LAYOUT_ID"));
				entity.setSid(rec.getString("SID"));
				entity.setCid(rec.getString("CID"));
				entity.setItemSelType(rec.getInt("ITEM_SEL_TYPE"));
				entity.setItemName(rec.getString("ITEM_NAME"));
				entity.setItemCode(rec.getString("ITEM_CD"));
				entity.setWorkTypeNameDisplay(rec.getBigDecimal("WORKTYPE_NAME_DISPLAY"));
				entity.setNoteInputNo(rec.getBigDecimal("NOTE_INPUT_NO"));
				entity.setCharSizeType(rec.getBigDecimal("CHAR_SIZE_TYPE"));
				return this.toDomain(entity, mapKfnmtAttendanceDisplay.get(entity.getLayoutId()), mapKfnmtPrintRemarkCont.get(entity.getLayoutId()));
			});
		}
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the output item daily work schedule
	 */
	private OutputItemDailyWorkSchedule toDomain(KfnmtRptWkDaiOutItem entity
			, List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds
			, List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes) {
		return new OutputItemDailyWorkSchedule(
				new JpaOutputItemDailyWorkScheduleGetMemento(entity, kfnmtRptWkDaiOutatds, kfnmtRptWkDaiOutnotes));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmt item work schedule
	 */
	private KfnmtRptWkDaiOutItem toEntity(OutputItemDailyWorkSchedule domain) {
		KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
		domain.saveToMemento(new JpaOutputItemDailyWorkScheduleSetMemento(entity));
		return entity;
	}
	
}
