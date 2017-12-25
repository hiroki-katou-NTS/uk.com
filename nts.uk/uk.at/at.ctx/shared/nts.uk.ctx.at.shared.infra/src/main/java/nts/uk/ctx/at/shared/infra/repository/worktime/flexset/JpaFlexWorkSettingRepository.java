///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import javax.ejb.Stateless;
//
//import nts.arc.layer.infra.data.JpaRepository;
//import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
//import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexRestSet;
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexRestSetPK;
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;
//
///**
// * The Class JpaFlexWorkSettingRepository.
// */
//@Stateless
//public class JpaFlexWorkSettingRepository extends JpaRepository
//		implements FlexWorkSettingRepository {
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
//	 * findById(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public Optional<FlexWorkSetting> find(String companyId, String worktimeCode) {
//		return this.findFlexSettingGroup(companyId, worktimeCode)
//				.map(entity -> this.toDomain(entity));
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
//	 * saveFlexWorkSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
//	 * FlexWorkSetting)
//	 */
//	@Override
//	public void save(FlexWorkSetting domain) {
//		Optional<KshmtFlexWorkSet> optionalEntityWorkSet = this
//				.findWorkSetting(domain.getCompanyId(), domain.getWorkTimeCode().v());
//		if (optionalEntityWorkSet.isPresent()) {
//			KshmtFlexSetGroup entityGroup = new KshmtFlexSetGroup(
//					this.findFlexODRestTime(domain.getCompanyId(), domain.getWorkTimeCode().v()),
//					optionalEntityWorkSet.get(),
//					this.findFlexRestSetting(domain.getCompanyId(), domain.getWorkTimeCode().v()));
//			domain.saveToMemento(new JpaFlexWorkSettingSetMemento(entityGroup, new ArrayList<>(),
//					new KshmtFlexArrayGroup(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
//							new ArrayList<>())));
//			this.updateFlexSettingGroup(entityGroup);
//		} else {
//			KshmtFlexSetGroup entityGroup = new KshmtFlexSetGroup(new KshmtFlexOdRestTime(),
//					new KshmtFlexWorkSet(), new KshmtFlexRestSet());
//			domain.saveToMemento(new JpaFlexWorkSettingSetMemento(entityGroup, new ArrayList<>(),
//					new KshmtFlexArrayGroup(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
//							new ArrayList<>())));
//			this.addFlexSettingGroup(entityGroup);
//		}
//	}
//
//	/**
//	 * Adds the flex setting group.
//	 *
//	 * @param entity
//	 *            the entity
//	 */
//	private void addFlexSettingGroup(KshmtFlexSetGroup entity) {
//		this.commandProxy().insert(entity.getEntitySetting());
//		this.commandProxy().insert(entity.getEntityOffday());
//		this.commandProxy().insert(entity.getEntityRest());
//	}
//
//	/**
//	 * Update flex setting group.
//	 *
//	 * @param entity
//	 *            the entity
//	 */
//	private void updateFlexSettingGroup(KshmtFlexSetGroup entity) {
//		this.commandProxy().update(entity.getEntitySetting());
//		this.commandProxy().update(entity.getEntityOffday());
//		this.commandProxy().update(entity.getEntityRest());
//	}
//
//	/**
//	 * To domain.
//	 *
//	 * @param entity
//	 *            the entity
//	 * @return the flex work setting
//	 */
//	private FlexWorkSetting toDomain(KshmtFlexSetGroup entitySetting) {
//		return new FlexWorkSetting(new JpaFlexWorkSettingGetMemento(entitySetting,
//				new ArrayList<>(), new KshmtFlexArrayGroup(new ArrayList<>(), new ArrayList<>(),
//						new ArrayList<>(), new ArrayList<>())));
//	}
//
//	/**
//	 * Find flex setting group.
//	 *
//	 * @param companyId
//	 *            the company id
//	 * @param worktimeCode
//	 *            the worktime code
//	 * @return the optional
//	 */
//	private Optional<KshmtFlexSetGroup> findFlexSettingGroup(String companyId,
//			String worktimeCode) {
//		Optional<KshmtFlexWorkSet> optionalEntityWorkSet = this.findWorkSetting(companyId,
//				worktimeCode);
//		if (optionalEntityWorkSet.isPresent()) {
//			KshmtFlexSetGroup entityGroup = new KshmtFlexSetGroup(
//					this.findFlexODRestTime(companyId, worktimeCode), optionalEntityWorkSet.get(),
//					this.findFlexRestSetting(companyId, worktimeCode));
//			return Optional.ofNullable(entityGroup);
//		}
//		return Optional.empty();
//	}
//
//	/**
//	 * Find flex rest setting.
//	 *
//	 * @param companyId
//	 *            the company id
//	 * @param worktimeCode
//	 *            the worktime code
//	 * @return the kshmt flex rest set
//	 */
//	private KshmtFlexRestSet findFlexRestSetting(String companyId, String worktimeCode) {
//		return this.queryProxy()
//				.find(new KshmtFlexRestSetPK(companyId, worktimeCode), KshmtFlexRestSet.class)
//				.get();
//	}
//
//	/**
//	 * Find flex OD rest time.
//	 *
//	 * @param companyId
//	 *            the company id
//	 * @param worktimeCode
//	 *            the worktime code
//	 * @return the kshmt flex od rest time
//	 */
//	private KshmtFlexOdRestTime findFlexODRestTime(String companyId, String worktimeCode) {
//		return this.queryProxy()
//				.find(new KshmtFlexOdRestTimePK(companyId, worktimeCode), KshmtFlexOdRestTime.class)
//				.get();
//	}
//
//	/**
//	 * Find work setting.
//	 *
//	 * @param companyId
//	 *            the company id
//	 * @param worktimeCode
//	 *            the worktime code
//	 * @return the optional
//	 */
//	private Optional<KshmtFlexWorkSet> findWorkSetting(String companyId, String worktimeCode) {
//		return this.queryProxy().find(new KshmtFlexWorkSetPK(companyId, worktimeCode),
//				KshmtFlexWorkSet.class);
//	}
//
//}
