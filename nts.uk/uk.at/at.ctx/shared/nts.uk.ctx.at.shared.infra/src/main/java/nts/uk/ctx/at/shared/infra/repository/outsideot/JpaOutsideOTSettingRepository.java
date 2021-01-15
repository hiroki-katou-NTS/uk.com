/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.infra.entity.outsideot.KshstOutsideOtSet;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrd;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrdPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrdPK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrd_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOutsideOtBrdAten;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOutsideOtBrdAtenPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRatePK;

/**
 * The Class JpaOutsideOTSettingRepository.
 */
@Stateless
public class JpaOutsideOTSettingRepository extends JpaRepository
		implements OutsideOTSettingRepository {
	
	public static final String FIND_BY_COMPANY_ID_AND_USE_CLS = "SELECT ost FROM KshstOutsideOtBrd ost"
			+ "	WHERE ost.useAtr = :useAtr"
			+ "		AND ost.kshstOutsideOtBrdPK.cid = :cid";
	

	public static final String FIND_OVER_TIME_BY_COMPANY_ID_AND_USE_CLS = "SELECT ot FROM KshstOverTime ot"
			+ "	WHERE ot.useAtr = :useAtr"
			+ "		AND ot.kshstOverTimePK.cid = :cid";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingRepository#findById(java
	 * .lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@SneakyThrows
	@Override
	public Optional<OutsideOTSetting> findById(String companyId) {
		
		Optional<KshstOutsideOtSet> kshstOutsideOtSet = getOutsireOtSet(companyId);
		
		List<KshstOverTime> entityOvertime = getOverTime(companyId, true);
		
		List<KshstOutsideOtBrd> entityOvertimeBRDItem = getOutsideOtBrd(companyId, true);
		
		// check exist data
		if (kshstOutsideOtSet.isPresent()) {
			return Optional
					.ofNullable(this.toDomain(kshstOutsideOtSet.get(), entityOvertimeBRDItem, entityOvertime));
		}
		// default data
		return Optional.ofNullable(
				this.toDomain(new KshstOutsideOtSet(), entityOvertimeBRDItem, entityOvertime));
		
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@SneakyThrows
	@Override
	public Optional<OutsideOTSetting> findByIdV2(String companyId) {
		
		Optional<KshstOutsideOtSet> kshstOutsideOtSet = getOutsireOtSet(companyId);
		
		List<KshstOverTime> entityOvertime = getOverTime(companyId, false);
		
		List<KshstOutsideOtBrd> entityOvertimeBRDItem = getOutsideOtBrd(companyId, false);
		
		// check exist data
		if (kshstOutsideOtSet.isPresent()) {
			return Optional
					.ofNullable(this.toDomain(kshstOutsideOtSet.get(), entityOvertimeBRDItem, entityOvertime));
		}
		// default data
		return Optional.ofNullable(
				this.toDomain(new KshstOutsideOtSet(), entityOvertimeBRDItem, entityOvertime));
		
	}

	@SneakyThrows
	private Optional<KshstOutsideOtSet> getOutsireOtSet(String companyId) {
		String sqlJdbc = "SELECT * FROM KSHST_OUTSIDE_OT_SET KOOS "
				+ "WHERE KOOS.CID = ?";
		
		try (PreparedStatement stmt = this.connection().prepareStatement(sqlJdbc)) {

			stmt.setString(1, companyId);

			return new NtsResultSet(stmt.executeQuery())
					.getSingle(rec -> {
						KshstOutsideOtSet entity = new KshstOutsideOtSet();
						entity.setCid(rec.getString("CID"));
						entity.setNote(rec.getString("NOTE"));
						entity.setCalculationMethod(rec.getInt("CALCULATION_METHOD"));
						return entity;
					});
		}
	}
	
	@SneakyThrows
	private List<KshstOutsideOtBrd> getOutsideOtBrd(String companyId, boolean isUse) {

		String sqlJdbc = "SELECT * FROM KSHST_OUTSIDE_OT_BRD_ATEN KOOBA "
				+ "WHERE KOOBA.CID = ?";
		List<KshstOutsideOtBrdAten> lstOutsideOtBrdAten = new ArrayList<>();
		try (PreparedStatement stmt3 = this.connection().prepareStatement(sqlJdbc)) {

			stmt3.setString(1, companyId);

			lstOutsideOtBrdAten.addAll(new NtsResultSet(stmt3.executeQuery())
					.getList(rec3 -> {
						KshstOutsideOtBrdAtenPK kshstOutsideOtBrdAtenPK = new KshstOutsideOtBrdAtenPK();
						kshstOutsideOtBrdAtenPK.setCid(rec3.getString("CID"));
						kshstOutsideOtBrdAtenPK.setBrdItemNo(rec3.getInt("BRD_ITEM_NO"));
						kshstOutsideOtBrdAtenPK.setAttendanceItemId(rec3.getInt("ATTENDANCE_ITEM_ID"));

						KshstOutsideOtBrdAten entity = new KshstOutsideOtBrdAten();
						entity.setKshstOutsideOtBrdAtenPK(kshstOutsideOtBrdAtenPK);

						return entity;
					}));
		}
		

		
		sqlJdbc = "SELECT * FROM KSHST_PREMIUM_EXT60H_RATE KOOBA "
				+ "WHERE KOOBA.CID = ?";
		List<KshstPremiumExt60hRate> lstPremiumExtRate = new ArrayList<>();
		try (PreparedStatement stmt3 = this.connection().prepareStatement(sqlJdbc)) {

			stmt3.setString(1, companyId);

			lstPremiumExtRate.addAll(new NtsResultSet(stmt3.executeQuery())
					.getList(rec3 -> {
						KshstPremiumExt60hRatePK kshstOutsideOtBrdAtenPK = new KshstPremiumExt60hRatePK();
						kshstOutsideOtBrdAtenPK.setCid(rec3.getString("CID"));
						kshstOutsideOtBrdAtenPK.setBrdItemNo(rec3.getInt("BRD_ITEM_NO"));
						kshstOutsideOtBrdAtenPK.setOverTimeNo(rec3.getInt("OVER_TIME_NO"));

						KshstPremiumExt60hRate entity = new KshstPremiumExt60hRate();
						entity.setPk(kshstOutsideOtBrdAtenPK);
						entity.setPremiumRate(rec3.getInt("PREMIUM_RATE"));

						return entity;
					}));
		}
		
		sqlJdbc = "SELECT * FROM KSHST_OUTSIDE_OT_BRD KOOB "
				+ " WHERE KOOB.CID = ?"
				+ (isUse ? " AND KOOB.USE_ATR = ? " : "")
				+ " ORDER BY KOOB.BRD_ITEM_NO ASC";
		List<KshstOutsideOtBrd> entityOvertimeBRDItem = new ArrayList<>();
		try (PreparedStatement stmt2 = this.connection().prepareStatement(sqlJdbc)) {

			stmt2.setString(1, companyId);
			if(isUse) {
				stmt2.setInt(2, UseClassification.UseClass_Use.value);
			}

			entityOvertimeBRDItem = new NtsResultSet(stmt2.executeQuery())
					.getList(rec2 -> {
						KshstOutsideOtBrdPK kshstOutsideOtBrdPK = new KshstOutsideOtBrdPK();
						kshstOutsideOtBrdPK.setCid(rec2.getString("CID"));
						kshstOutsideOtBrdPK.setBrdItemNo(rec2.getInt("BRD_ITEM_NO"));

						KshstOutsideOtBrd entity = new KshstOutsideOtBrd();
						entity.setKshstOutsideOtBrdPK(kshstOutsideOtBrdPK);
						entity.setName(rec2.getString("NAME"));
						entity.setUseAtr(rec2.getInt("USE_ATR"));
						entity.setProductNumber(rec2.getInt("PRODUCT_NUMBER"));
						
						val brdAten = lstOutsideOtBrdAten.stream()
								.filter(c -> c.getKshstOutsideOtBrdAtenPK().getBrdItemNo() == entity.getKshstOutsideOtBrdPK().getBrdItemNo())
								.collect(Collectors.toList());
						
						entity.setLstOutsideOtBrdAten(brdAten);
						
						val premiumExtRate = lstPremiumExtRate.stream()
								.filter(c -> c.getPk().getBrdItemNo() == entity.getKshstOutsideOtBrdPK().getBrdItemNo())
								.collect(Collectors.toList());
						
						entity.setLstPremiumExtRates(premiumExtRate);

						return entity;
					});
		}
		return entityOvertimeBRDItem;
	}
	
	@SneakyThrows
	private List<KshstOverTime> getOverTime(String companyId, boolean isUse)  {
		String sqlJdbc = "SELECT * FROM KSHST_OVER_TIME KOT "
				+ " WHERE KOT.CID = ? "
				+ (isUse ? " AND KOT.USE_ATR = ? " : "")
				+ " ORDER BY KOT.OVER_TIME_NO ASC";
		List<KshstOverTime> entityOvertime = new ArrayList<>();
		try (PreparedStatement stmt1 = this.connection().prepareStatement(sqlJdbc)) {

			stmt1.setString(1, companyId);
			if (isUse) {
				stmt1.setInt(2, UseClassification.UseClass_Use.value);
			}

			entityOvertime = new NtsResultSet(stmt1.executeQuery())
					.getList(rec1 -> {
						KshstOverTimePK kshstOverTimePK = new KshstOverTimePK();
						kshstOverTimePK.setCid(rec1.getString("CID"));
						kshstOverTimePK.setOverTimeNo(rec1.getInt("OVER_TIME_NO"));

						KshstOverTime entity = new KshstOverTime();
						entity.setKshstOverTimePK(kshstOverTimePK);
						entity.setIs60hSuperHd(rec1.getInt("IS_60H_SUPER_HD"));
						entity.setUseAtr(rec1.getInt("USE_ATR"));
						entity.setName(rec1.getString("NAME"));
						entity.setOverTime(rec1.getInt("OVER_TIME"));

						return entity;
					});
		}
		return entityOvertime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingRepository#findById(java
	 * .lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<OutsideOTSetting> reportById(String companyId) {

		// call repository find entity setting
		Optional<KshstOutsideOtSet> entity = this.queryProxy().find(companyId,
				KshstOutsideOtSet.class);

		// call repository find all domain overtime
		List<Overtime> domainOvertime = this.findAllOvertime(companyId);

		// call repository find all domain overtime break down item
		List<OutsideOTBRDItem> domainOvertimeBrdItem = this.findAllBRDItem(companyId);

		// check exist data
		if (entity.isPresent()) {
			return Optional.ofNullable(this.toDomain2(entity.get(), domainOvertimeBrdItem, domainOvertime));
		}
		// default data
		return Optional.ofNullable(this.toDomain2(new KshstOutsideOtSet(), domainOvertimeBrdItem, domainOvertime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingRepository#save(
	 * nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSetting)
	 */
	@Override
	public void save(OutsideOTSetting domain) {
		KshstOutsideOtSet entity = new KshstOutsideOtSet();
		// call repository find entity setting
		Optional<KshstOutsideOtSet> opEntity = this.queryProxy().find(domain.getCompanyId(), KshstOutsideOtSet.class);

		// check exist data
		if (opEntity.isPresent()) {
			entity = opEntity.get();
			entity.update(domain);
			this.commandProxy().update(entity);
		} // insert data
		else {
			entity = new KshstOutsideOtSet(domain.getCompanyId());
			entity.update(domain);
			this.commandProxy().insert(entity);
		}
		// save all overtime
		this.saveAllOvertime(domain.getOvertimes(), domain.getCompanyId());

		// save all over time breakdown item
		this.saveAllBRDItem(domain.getBreakdownItems(), domain.getCompanyId());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @param entityOvertimeBRDItems the entity overtime BRD items
	 * @param entityOvertime the entity overtime
	 * @return the outside OT setting
	 */
	private OutsideOTSetting toDomain2(KshstOutsideOtSet entity, List<OutsideOTBRDItem> overtimeBRDItems, 
			List<Overtime> overtime) {
		
		return new OutsideOTSetting(entity.getCid(), new OvertimeNote(entity.getNote()), 
				overtimeBRDItems, 
				EnumAdaptor.valueOf(entity.getCalculationMethod(), OutsideOTCalMed.class), 
				overtime);
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @param entityOvertimeBRDItems the entity overtime BRD items
	 * @param entityOvertime the entity overtime
	 * @return the outside OT setting
	 */
	private OutsideOTSetting toDomain(KshstOutsideOtSet entity, List<KshstOutsideOtBrd> entityOvertimeBRDItems, 
			List<KshstOverTime> entityOvertime) {
		
		return new OutsideOTSetting(entity.getCid(), new OvertimeNote(entity.getNote()), 
				entityOvertimeBRDItems.stream().map(c -> c.domain()).collect(Collectors.toList()), 
				EnumAdaptor.valueOf(entity.getCalculationMethod(), OutsideOTCalMed.class), 
				entityOvertime.stream().map(c -> c.domain()).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * saveAll(java.util.List)
	 */
	@Override
	public void saveAllBRDItem(List<OutsideOTBRDItem> overtimeBreakdownItems, String companyId) {

		// to map over time break down item
		Map<Integer, KshstOutsideOtBrd> mapOvertimeBRDItem = this.internalFindAllBRDItem(companyId)
				.stream().collect(Collectors.toMap((overtime) -> overtime.getKshstOutsideOtBrdPK().getBrdItemNo(), 
													Function.identity()));

		// entity add all
		List<KshstOutsideOtBrd> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshstOutsideOtBrd> entityUpdateAll = new ArrayList<>();

		// for each data overtime
		overtimeBreakdownItems.forEach(overtimeBRDItem -> {
			KshstOutsideOtBrd e = mapOvertimeBRDItem.get(overtimeBRDItem.getBreakdownItemNo().value);
			if (e != null) {
				e.update(overtimeBRDItem, companyId);
				entityUpdateAll.add(e);
			} else {
				e = new KshstOutsideOtBrd(new KshstOutsideOtBrdPK(companyId, overtimeBRDItem.getBreakdownItemNo().value));
				e.update(overtimeBRDItem, companyId);
				entityAddAll.add(e);
			}
		});

		// insert all
		this.commandProxy().insertAll(entityAddAll);

		// update all
		this.commandProxy().updateAll(entityUpdateAll);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * findAll(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<OutsideOTBRDItem> findAllBRDItem(String companyId) {

		// exclude select
		return internalFindAllBRDItem(companyId).stream().map(entity -> entity.domain()).collect(Collectors.toList());
	}
	
	private List<KshstOutsideOtBrd> internalFindAllBRDItem(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshstOutsideOtBrd> cq = criteriaBuilder.createQuery(KshstOutsideOtBrd.class);

		// root data
		Root<KshstOutsideOtBrd> root = cq.from(KshstOutsideOtBrd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK).get(KshstOutsideOtBrdPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time break down item no asc
		cq.orderBy(criteriaBuilder.asc(root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK)
				.get(KshstOutsideOtBrdPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOutsideOtBrd> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * findAllUse(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<OutsideOTBRDItem> findAllUseBRDItem(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshstOutsideOtBrd> cq = criteriaBuilder.createQuery(KshstOutsideOtBrd.class);

		// root data
		Root<KshstOutsideOtBrd> root = cq.from(KshstOutsideOtBrd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK).get(KshstOutsideOtBrdPK_.cid),
				companyId));

		// equal use classification
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshstOutsideOtBrd_.useAtr),
				UseClassification.UseClass_Use.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time break down item no asc
		cq.orderBy(criteriaBuilder.asc(root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK)
				.get(KshstOutsideOtBrdPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOutsideOtBrd> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> entity.domain()).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeRepository#findAll(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<Overtime> findAllOvertime(String companyId) {
		return this.findAllEntity(companyId).stream().map(item -> item.domain())
				.collect(Collectors.toList());
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeRepository#saveAll(java.util.
	 * List, java.lang.String)
	 */
	@Override
	public void saveAllOvertime(List<Overtime> overtimes, String companyId) {
		
		// to map over time
		Map<Integer, KshstOverTime> mapOvertime = this.findAllEntity(companyId).stream()
				.collect(Collectors.toMap((overtime) -> overtime.getKshstOverTimePK().getOverTimeNo(), Function.identity()));
		
		// entity add all
		List<KshstOverTime> entityAddAll = new ArrayList<>();
		
		// entity update all
		List<KshstOverTime> entityUpdateAll = new ArrayList<>();
		
		
		// for each data overtime
		overtimes.forEach(overtime->{
			KshstOverTime e = mapOvertime.get(overtime.getOvertimeNo().value);
			if (e != null) {
				e.update(overtime);
				entityUpdateAll.add(e);
			} else {
				e = new KshstOverTime(new KshstOverTimePK(companyId, overtime.getOvertimeNo().value));
				e.update(overtime);
				entityAddAll.add(e);
			}
		});
		
		// insert all
		this.commandProxy().insertAll(entityAddAll);
		
		// update all
		this.commandProxy().updateAll(entityUpdateAll);
	}
	
	/**
	 * Find all entity.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private List<KshstOverTime> findAllEntity(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME (KshstOverTime SQL)
		CriteriaQuery<KshstOverTime> cq = criteriaBuilder.createQuery(KshstOverTime.class);

		// root data
		Root<KshstOverTime> root = cq.from(KshstOverTime.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.cid), companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time no asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.overTimeNo)));

		// create query
		TypedQuery<KshstOverTime> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository#
	 * findAllUseOvertime(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<Overtime> findAllUseOvertime(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME (KshstOverTime SQL)
		CriteriaQuery<KshstOverTime> cq = criteriaBuilder.createQuery(KshstOverTime.class);

		// root data
		Root<KshstOverTime> root = cq.from(KshstOverTime.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.cid), companyId));
		
		// equal Use Classification use
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshstOverTime_.useAtr),
				UseClassification.UseClass_Use.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time no asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.overTimeNo)));

		// create query
		TypedQuery<KshstOverTime> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> entity.domain())
				.collect(Collectors.toList());
	}

	@Override
	public List<Overtime> getOverTimeByCompanyIdAndUseClassification(String companyId, int useClassification) {
		return this.queryProxy().query(FIND_OVER_TIME_BY_COMPANY_ID_AND_USE_CLS, KshstOverTime.class)
				.setParameter("useAtr", useClassification)
				.setParameter("cid", companyId)
				.getList().stream()
				.map(t -> t.domain())
				.collect(Collectors.toList());
	}

	@Override
	public List<OutsideOTBRDItem> getByCompanyIdAndUseClassification(String companyId, int useClassification) {
		return this.queryProxy().query(FIND_BY_COMPANY_ID_AND_USE_CLS, KshstOutsideOtBrd.class)
				.setParameter("useAtr", useClassification)
				.setParameter("cid", companyId)
				.getList().stream()
				.map(t -> t.domain())
				.collect(Collectors.toList());
	}

	@Override
	public List<PremiumExtra60HRate> findAllPremiumExtraRate(String cid) {

		String queryString = "SELECT r FROM KshstPremiumExt60hRate r WHERE r.pk.cid = :cid";
		
		return this.queryProxy().query(queryString, KshstPremiumExt60hRate.class)
				.setParameter("cid", cid)
				.getList(c -> new PremiumExtra60HRate(new PremiumRate(c.getPremiumRate()),
													  EnumAdaptor.valueOf(c.getPk().getOverTimeNo(), OvertimeNo.class)));
	}

}
