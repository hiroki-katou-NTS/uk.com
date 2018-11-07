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
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplay;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplayPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplayPK_;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplay_;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK_;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedule_;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtPrintRemarkCont;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtPrintRemarkContPK;

/**
 * The Class JpaOutputItemDailyWorkScheduleRepository.
 * author: HoangDD
 */
@Stateless
public class JpaOutputItemDailyWorkScheduleRepository extends JpaRepository implements OutputItemDailyWorkScheduleRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#add(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void add(OutputItemDailyWorkSchedule domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#update(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void update(OutputItemDailyWorkSchedule domain) {
		EntityManager em = this.getEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

        // create delete
        CriteriaDelete<KfnmtAttendanceDisplay> delete = cb.createCriteriaDelete(KfnmtAttendanceDisplay.class);

        // set the root class
        Root<KfnmtAttendanceDisplay> root = delete.from(KfnmtAttendanceDisplay.class);

        // set where clause
        delete.where(cb.equal(root.get(KfnmtAttendanceDisplay_.id).get(KfnmtAttendanceDisplayPK_.cid), domain.getCompanyID()),
        				cb.equal(root.get(KfnmtAttendanceDisplay_.id).get(KfnmtAttendanceDisplayPK_.itemCode), domain.getItemCode().v()));

        // perform update
        em.createQuery(delete).executeUpdate();
		
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#delete(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void delete(OutputItemDailyWorkSchedule domain) {
		this.commandProxy().remove(this.toEntity(domain));
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
		CriteriaQuery<KfnmtItemWorkSchedule> cq = builder.createQuery(KfnmtItemWorkSchedule.class);

		// From table
		Root<KfnmtItemWorkSchedule> root = cq.from(KfnmtItemWorkSchedule.class);

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
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#deleteByCidAndCode(java.lang.String, int)
	 */
	@Override
	public void deleteByCidAndCode(String companyId, String code) {
        KfnmtItemWorkSchedulePK primaryKey = new KfnmtItemWorkSchedulePK();
        primaryKey.setCid(companyId);
        primaryKey.setItemCode(code);
        this.commandProxy().remove(KfnmtItemWorkSchedule.class, primaryKey);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#findByCidAndCode(java.lang.String, int)
	 */
	@Override
	@SneakyThrows
	public Optional<OutputItemDailyWorkSchedule> findByCidAndCode(String companyId, String code) {
		List<KfnmtAttendanceDisplay> lstKfnmtAttendanceDisplay = new ArrayList<>();
		String sqlJDBC1 = "select * from KFNMT_ATTENDANCE_DISPLAY where CD = ? and ITEM_CD = ? ORDER BY ORDER_NO";
		try (PreparedStatement statement1 = this.connection().prepareStatement(sqlJDBC1)) {
			statement1.setString(1, companyId);
			statement1.setString(2, code);
			lstKfnmtAttendanceDisplay
					.addAll(new NtsResultSet(statement1.executeQuery()).getList(rec -> {
						KfnmtAttendanceDisplayPK pk = new KfnmtAttendanceDisplayPK();
						pk.setCid(rec.getString("CID"));
						pk.setItemCode(rec.getString("ITEM_CD"));
						pk.setOrderNo(rec.getLong("ORDER_NO"));
						KfnmtAttendanceDisplay entity = new KfnmtAttendanceDisplay();
						entity.setId(pk);
						entity.setAtdDisplay(rec.getBigDecimal("ATD_DISPLAY"));
						return entity;
					}));
		}

		List<KfnmtPrintRemarkCont> lstKfnmtPrintRemarkCont = new ArrayList<>();
		String sqlJDBC2 = "select * from KFNMT_PRINT_REMARK_CONT where CD = ? and ITEM_CD = ?";
		try (PreparedStatement statement2 = this.connection().prepareStatement(sqlJDBC2)) {
			statement2.setString(1, companyId);
			statement2.setString(2, code);
			lstKfnmtPrintRemarkCont
					.addAll(new NtsResultSet(statement2.executeQuery()).getList(rec -> {
						KfnmtPrintRemarkContPK pk = new KfnmtPrintRemarkContPK();
						pk.setCid(rec.getString("CID"));
						pk.setItemCode(rec.getString("ITEM_CD"));
						pk.setPrintItem(rec.getLong("PRINT_ITEM"));
						KfnmtPrintRemarkCont entity = new KfnmtPrintRemarkCont();
						entity.setId(pk);
						entity.setUseCls(rec.getBigDecimal("USE_CLS"));
						return entity;
					}));
		}

		Map<String, List<KfnmtAttendanceDisplay>> mapKfnmtAttendanceDisplay = lstKfnmtAttendanceDisplay
				.stream().collect(Collectors.groupingBy(item -> item.getId().getItemCode()));
		Map<String, List<KfnmtPrintRemarkCont>> mapKfnmtPrintRemarkCont = lstKfnmtPrintRemarkCont
				.stream().collect(Collectors.groupingBy(item -> item.getId().getItemCode()));
		String sqlJDBC3 = "select * from KFNMT_ITEM_WORK_SCHEDULE where CD = ? and ITEM_CD = ?";
		try (PreparedStatement statement3 = this.connection().prepareStatement(sqlJDBC3)) {
			statement3.setString(1, companyId);
			statement3.setString(2, code);
			return new NtsResultSet(statement3.executeQuery()).getSingle(rec -> {
				KfnmtItemWorkSchedulePK pk = new KfnmtItemWorkSchedulePK();
				pk.setCid(rec.getString("CID"));
				pk.setItemCode(rec.getString("ITEM_CD"));
				KfnmtItemWorkSchedule entity = new KfnmtItemWorkSchedule();
				entity.setId(pk);
				entity.setItemName(rec.getString("ITEM_NAME"));
				entity.setWorkTypeNameDisplay(rec.getBigDecimal("WORKTYPE_NAME_DISPLAY"));
				entity.setRemarkInputNo(rec.getBigDecimal("REMARK_INPUT_NO"));
				entity.setLstKfnmtAttendanceDisplay(mapKfnmtAttendanceDisplay
						.getOrDefault(pk.getItemCode(), Collections.emptyList()));
				entity.setLstKfnmtPrintRemarkCont(mapKfnmtPrintRemarkCont
						.getOrDefault(pk.getItemCode(), Collections.emptyList()));
				return this.toDomain(entity);
			});
		}
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the output item daily work schedule
	 */
	private OutputItemDailyWorkSchedule toDomain(KfnmtItemWorkSchedule entity) {
		return new OutputItemDailyWorkSchedule(new JpaOutputItemDailyWorkScheduleGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmt item work schedule
	 */
	private KfnmtItemWorkSchedule toEntity(OutputItemDailyWorkSchedule domain) {
		KfnmtItemWorkSchedule entity = new KfnmtItemWorkSchedule();
		domain.saveToMemento(new JpaOutputItemDailyWorkScheduleSetMemento(entity));
		return entity;
	}
	
}
