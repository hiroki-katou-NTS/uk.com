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
import nts.uk.ctx.at.shared.infra.entity.outsideot.KshmtOutsideSet;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshmtOutsideDetail;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrdPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrdPK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrd_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshmtOutsideAtd;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance.KshstOutsideOtBrdAtenPK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshmtOutside;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshmtHd60hPremiumRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRatePK;

/**
 * The Class JpaOutsideOTSettingRepository.
 */
@Stateless
public class JpaOutsideOTSettingRepository extends JpaRepository
		implements OutsideOTSettingRepository {
	
	public static final String FIND_BY_COMPANY_ID_AND_USE_CLS = "SELECT ost FROM KshmtOutsideDetail ost"
			+ "	WHERE ost.useAtr = :useAtr"
			+ "		AND ost.kshstOutsideOtBrdPK.cid = :cid";
	

	public static final String FIND_OVER_TIME_BY_COMPANY_ID_AND_USE_CLS = "SELECT ot FROM KshmtOutside ot"
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
		
		Optional<KshmtOutsideSet> kshstOutsideOtSet = getOutsireOtSet(companyId);
		
		List<KshmtOutside> entityOvertime = getOverTime(companyId, true);
		
		List<KshmtOutsideDetail> entityOvertimeBRDItem = getOutsideOtBrd(companyId, true);
		
		// check exist data
		if (kshstOutsideOtSet.isPresent()) {
			return Optional
					.ofNullable(this.toDomain(kshstOutsideOtSet.get(), entityOvertimeBRDItem, entityOvertime));
		}
		// default data
		return Optional.ofNullable(
				this.toDomain(new KshmtOutsideSet(), entityOvertimeBRDItem, entityOvertime));
		
	}
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@SneakyThrows
	@Override
	public Optional<OutsideOTSetting> findByIdV2(String companyId) {
		
		Optional<KshmtOutsideSet> kshstOutsideOtSet = getOutsireOtSet(companyId);
		
		List<KshmtOutside> entityOvertime = getOverTime(companyId, false);
		
		List<KshmtOutsideDetail> entityOvertimeBRDItem = getOutsideOtBrd(companyId, false);
		
		// check exist data
		if (kshstOutsideOtSet.isPresent()) {
			return Optional
					.ofNullable(this.toDomain(kshstOutsideOtSet.get(), entityOvertimeBRDItem, entityOvertime));
		}
		// default data
		return Optional.ofNullable(
				this.toDomain(new KshmtOutsideSet(), entityOvertimeBRDItem, entityOvertime));
		
	}

	@SneakyThrows
	private Optional<KshmtOutsideSet> getOutsireOtSet(String companyId) {
		String sqlJdbc = "SELECT * FROM KSHMT_OUTSIDE_SET KOOS "
				+ "WHERE KOOS.CID = ?";
		
		try (PreparedStatement stmt = this.connection().prepareStatement(sqlJdbc)) {

			stmt.setString(1, companyId);

			return new NtsResultSet(stmt.executeQuery())
					.getSingle(rec -> {
						KshmtOutsideSet entity = new KshmtOutsideSet();
						entity.setCid(rec.getString("CID"));
						entity.setNote(rec.getString("NOTE"));
						entity.setCalculationMethod(rec.getInt("CALCULATION_METHOD"));
						return entity;
					});
		}
	}
	
	@SneakyThrows
	private List<KshmtOutsideDetail> getOutsideOtBrd(String companyId, boolean isUse) {

		String sqlJdbc = "SELECT * FROM KSHMT_OUTSIDE_ATD KOOBA "
				+ "WHERE KOOBA.CID = ?";
		List<KshmtOutsideAtd> lstOutsideOtBrdAten = new ArrayList<>();
		try (PreparedStatement stmt3 = this.connection().prepareStatement(sqlJdbc)) {

			stmt3.setString(1, companyId);

			lstOutsideOtBrdAten.addAll(new NtsResultSet(stmt3.executeQuery())
					.getList(rec3 -> {
						KshstOutsideOtBrdAtenPK kshstOutsideOtBrdAtenPK = new KshstOutsideOtBrdAtenPK();
						kshstOutsideOtBrdAtenPK.setCid(rec3.getString("CID"));
						kshstOutsideOtBrdAtenPK.setBrdItemNo(rec3.getInt("BRD_ITEM_NO"));
						kshstOutsideOtBrdAtenPK.setAttendanceItemId(rec3.getInt("ATTENDANCE_ITEM_ID"));

						KshmtOutsideAtd entity = new KshmtOutsideAtd();
						entity.setKshstOutsideOtBrdAtenPK(kshstOutsideOtBrdAtenPK);

						return entity;
					}));
		}
		

		
		sqlJdbc = "SELECT * FROM KSHMT_HD60H_PREMIUM_RATE KOOBA "
				+ "WHERE KOOBA.CID = ?";
		List<KshmtHd60hPremiumRate> lstPremiumExtRate = new ArrayList<>();
		try (PreparedStatement stmt3 = this.connection().prepareStatement(sqlJdbc)) {

			stmt3.setString(1, companyId);

			lstPremiumExtRate.addAll(new NtsResultSet(stmt3.executeQuery())
					.getList(rec3 -> {
						KshstPremiumExt60hRatePK kshstOutsideOtBrdAtenPK = new KshstPremiumExt60hRatePK();
						kshstOutsideOtBrdAtenPK.setCid(rec3.getString("CID"));
						kshstOutsideOtBrdAtenPK.setBrdItemNo(rec3.getInt("BRD_ITEM_NO"));
						kshstOutsideOtBrdAtenPK.setOverTimeNo(rec3.getInt("OVER_TIME_NO"));

						KshmtHd60hPremiumRate entity = new KshmtHd60hPremiumRate();
						entity.setPk(kshstOutsideOtBrdAtenPK);
						entity.setPremiumRate(rec3.getInt("PREMIUM_RATE"));

						return entity;
					}));
		}
		
		sqlJdbc = "SELECT * FROM KSHMT_OUTSIDE_DETAIL KOOB "
				+ " WHERE KOOB.CID = ?"
				+ (isUse ? " AND KOOB.USE_ATR = ? " : "")
				+ " ORDER BY KOOB.BRD_ITEM_NO ASC";
		List<KshmtOutsideDetail> entityOvertimeBRDItem = new ArrayList<>();
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

						KshmtOutsideDetail entity = new KshmtOutsideDetail();
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
	private List<KshmtOutside> getOverTime(String companyId, boolean isUse)  {
		String sqlJdbc = "SELECT * FROM KSHMT_OUTSIDE KOT "
				+ " WHERE KOT.CID = ? "
				+ (isUse ? " AND KOT.USE_ATR = ? " : "")
				+ " ORDER BY KOT.OVER_TIME_NO ASC";
		List<KshmtOutside> entityOvertime = new ArrayList<>();
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

						KshmtOutside entity = new KshmtOutside();
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
		Optional<KshmtOutsideSet> entity = this.queryProxy().find(companyId,
				KshmtOutsideSet.class);

		// call repository find all domain overtime
		List<Overtime> domainOvertime = this.findAllOvertime(companyId);

		// call repository find all domain overtime break down item
		List<OutsideOTBRDItem> domainOvertimeBrdItem = this.findAllBRDItem(companyId);

		// check exist data
		if (entity.isPresent()) {
			return Optional.ofNullable(this.toDomain2(entity.get(), domainOvertimeBrdItem, domainOvertime));
		}
		// default data
		return Optional.ofNullable(this.toDomain2(new KshmtOutsideSet(), domainOvertimeBrdItem, domainOvertime));
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
		KshmtOutsideSet entity = new KshmtOutsideSet();
		// call repository find entity setting
		Optional<KshmtOutsideSet> opEntity = this.queryProxy().find(domain.getCompanyId(), KshmtOutsideSet.class);

		// check exist data
		if (opEntity.isPresent()) {
			entity = opEntity.get();
			entity.update(domain);
			this.commandProxy().update(entity);
		} // insert data
		else {
			entity = new KshmtOutsideSet(domain.getCompanyId());
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
	private OutsideOTSetting toDomain2(KshmtOutsideSet entity, List<OutsideOTBRDItem> overtimeBRDItems, 
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
	private OutsideOTSetting toDomain(KshmtOutsideSet entity, List<KshmtOutsideDetail> entityOvertimeBRDItems, 
			List<KshmtOutside> entityOvertime) {
		
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
		Map<Integer, KshmtOutsideDetail> mapOvertimeBRDItem = this.internalFindAllBRDItem(companyId)
				.stream().collect(Collectors.toMap((overtime) -> overtime.getKshstOutsideOtBrdPK().getBrdItemNo(), 
													Function.identity()));

		// entity add all
		List<KshmtOutsideDetail> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshmtOutsideDetail> entityUpdateAll = new ArrayList<>();

		// for each data overtime
		overtimeBreakdownItems.forEach(overtimeBRDItem -> {
			KshmtOutsideDetail e = mapOvertimeBRDItem.get(overtimeBRDItem.getBreakdownItemNo().value);
			if (e != null) {
				e.update(overtimeBRDItem, companyId);
				entityUpdateAll.add(e);
			} else {
				e = new KshmtOutsideDetail(new KshstOutsideOtBrdPK(companyId, overtimeBRDItem.getBreakdownItemNo().value));
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
	
	private List<KshmtOutsideDetail> internalFindAllBRDItem(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshmtOutsideDetail> cq = criteriaBuilder.createQuery(KshmtOutsideDetail.class);

		// root data
		Root<KshmtOutsideDetail> root = cq.from(KshmtOutsideDetail.class);

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
		TypedQuery<KshmtOutsideDetail> query = em.createQuery(cq);

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
		CriteriaQuery<KshmtOutsideDetail> cq = criteriaBuilder.createQuery(KshmtOutsideDetail.class);

		// root data
		Root<KshmtOutsideDetail> root = cq.from(KshmtOutsideDetail.class);

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
		TypedQuery<KshmtOutsideDetail> query = em.createQuery(cq);

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
		Map<Integer, KshmtOutside> mapOvertime = this.findAllEntity(companyId).stream()
				.collect(Collectors.toMap((overtime) -> overtime.getKshstOverTimePK().getOverTimeNo(), Function.identity()));
		
		// entity add all
		List<KshmtOutside> entityAddAll = new ArrayList<>();
		
		// entity update all
		List<KshmtOutside> entityUpdateAll = new ArrayList<>();
		
		
		// for each data overtime
		overtimes.forEach(overtime->{
			KshmtOutside e = mapOvertime.get(overtime.getOvertimeNo().value);
			if (e != null) {
				e.update(overtime);
				entityUpdateAll.add(e);
			} else {
				e = new KshmtOutside(new KshstOverTimePK(companyId, overtime.getOvertimeNo().value));
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
	private List<KshmtOutside> findAllEntity(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_OUTSIDE (KshmtOutside SQL)
		CriteriaQuery<KshmtOutside> cq = criteriaBuilder.createQuery(KshmtOutside.class);

		// root data
		Root<KshmtOutside> root = cq.from(KshmtOutside.class);

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
		TypedQuery<KshmtOutside> query = em.createQuery(cq);

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

		// call KSHMT_OUTSIDE (KshmtOutside SQL)
		CriteriaQuery<KshmtOutside> cq = criteriaBuilder.createQuery(KshmtOutside.class);

		// root data
		Root<KshmtOutside> root = cq.from(KshmtOutside.class);

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
		TypedQuery<KshmtOutside> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> entity.domain())
				.collect(Collectors.toList());
	}

	@Override
	public List<Overtime> getOverTimeByCompanyIdAndUseClassification(String companyId, int useClassification) {
		return this.queryProxy().query(FIND_OVER_TIME_BY_COMPANY_ID_AND_USE_CLS, KshmtOutside.class)
				.setParameter("useAtr", useClassification)
				.setParameter("cid", companyId)
				.getList().stream()
				.map(t -> t.domain())
				.collect(Collectors.toList());
	}

	@Override
	public List<OutsideOTBRDItem> getByCompanyIdAndUseClassification(String companyId, int useClassification) {
		return this.queryProxy().query(FIND_BY_COMPANY_ID_AND_USE_CLS, KshmtOutsideDetail.class)
				.setParameter("useAtr", useClassification)
				.setParameter("cid", companyId)
				.getList().stream()
				.map(t -> t.domain())
				.collect(Collectors.toList());
	}

	@Override
	public List<PremiumExtra60HRate> findAllPremiumExtraRate(String cid) {

		String queryString = "SELECT r FROM KshmtHd60hPremiumRate r WHERE r.pk.cid = :cid";
		
		return this.queryProxy().query(queryString, KshmtHd60hPremiumRate.class)
				.setParameter("cid", cid)
				.getList(c -> new PremiumExtra60HRate(new PremiumRate(c.getPremiumRate()),
													  EnumAdaptor.valueOf(c.getPk().getOverTimeNo(), OvertimeNo.class)));
	}

}
