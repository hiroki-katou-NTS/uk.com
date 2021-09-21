/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.infra.repository.mypage.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.CcgmtPartItemSetPK;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.SptmtMyPageSet;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.SptmtPartItem;

/**
 * The Class JpaMyPageSettingRepository.
 */
@Stateless
public class JpaMyPageSettingRepository extends JpaRepository implements MyPageSettingRepository {

	private static final String GET_ONE_MPS = "SELECT m FROM SptmtMyPageSet m WHERE m.cid = :companyId";
	private static final String GET_ONE_PIS = "SELECT p FROM SptmtPartItem p WHERE p.ccgmtPartItemSetPK.cid = :companyId";
	private static final String GET_ONE_PIS_BY_TPP_ID = "SELECT p FROM SptmtPartItem p WHERE p.ccgmtPartItemSetPK.cid = :companyId AND p.ccgmtPartItemSetPK.topPagePartId = :topPagePartId";
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository#findByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<MyPageSetting> findByCompanyId(String companyId) {
		return this.queryProxy().query(GET_ONE_MPS, SptmtMyPageSet.class).setParameter("companyId", companyId)
				.getSingle(c -> mpsToDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository#update(
	 * nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting)
	 */
	@Override
	public void update(MyPageSetting myPageSetting) {
		// update myPageSet
		this.commandProxy().update(myPageSetToEntity(myPageSetting));
		// update PartItemSet
		this.commandProxy().updateAll(partItemSetToEntity(myPageSetting.getTopPagePartUseSetting()));
	}

	/**
	 * Adds the.
	 *
	 * @param topPagePartUseSetting the top page part use setting
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository#add(nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting)
	 */
	@Override
	public void addTopPagePartUseSetting(TopPagePartUseSetting topPagePartUseSetting) {
		CcgmtPartItemSetPK key = new CcgmtPartItemSetPK(topPagePartUseSetting.getCompanyId(),
				topPagePartUseSetting.getTopPagePartId());
		SptmtPartItem entity = new SptmtPartItem(key, topPagePartUseSetting.getUseDivision().value);
		this.commandProxy().update(entity);
	}

	/**
	 * Pus to domain.
	 *
	 * @param c the c
	 * @return the top page part use setting
	 */
	private TopPagePartUseSetting pusToDomain(SptmtPartItem c) {
		TopPagePartUseSetting pus = TopPagePartUseSetting.createFromJavaType(c.ccgmtPartItemSetPK.cid,c.ccgmtPartItemSetPK.topPagePartId,"",
				"", c.useAtr, null);
		return pus;
	}

	/**
	 * Mps to domain.
	 *
	 * @param c the c
	 * @return the my page setting
	 */
	private MyPageSetting mpsToDomain(SptmtMyPageSet c) {
		// get list item setting
		List<SptmtPartItem> lstCcgmtPartItemSet = this.queryProxy().query(GET_ONE_PIS, SptmtPartItem.class)
				.setParameter("companyId", c.cid)
				.getList();
		MyPageSetting mps = MyPageSetting.createFromJavaType(c.cid, c.useMyPageAtr, c.useStandarWidgetAtr, c.useOptionalWidgetAtr, c.useDashBoardAtr,
				c.useFolowMenuAtr, c.externalUrlPermissionAtr, this.pusToDomain2(lstCcgmtPartItemSet));
		return mps;
	}
	
	/**fix performance get List TopPagePartUseSetting from one company*/
	private List<TopPagePartUseSetting> pusToDomain2(List<SptmtPartItem> c) {
		return new ArrayList<TopPagePartUseSetting>();
	}

	/**
	 * My page set to entity.
	 *
	 * @param domain the domain
	 * @return the ccgmt my page set
	 */
	private SptmtMyPageSet myPageSetToEntity(MyPageSetting domain) {
		// Find Entity
		SptmtMyPageSet entity = this.queryProxy().query(GET_ONE_MPS, SptmtMyPageSet.class)
				.setParameter("companyId", domain.getCompanyId()).getSingleOrNull();
		if (entity != null) {
			entity.setUseMyPageAtr(domain.getUseMyPage().value);
			entity.setUseStandarWidgetAtr(domain.getUseStandarWidget().value);
			entity.setUseOptionalWidgetAtr(domain.getUseOptionalWidget().value);
			entity.setUseDashBoardAtr(domain.getUseDashboard().value);
			entity.setUseFolowMenuAtr(domain.getUseFlowMenu().value);
			entity.setExternalUrlPermissionAtr(domain.getExternalUrlPermission().value);
			return entity;
		} else {
			SptmtMyPageSet newEntity = new SptmtMyPageSet(domain.getCompanyId(), domain.getUseMyPage().value,
					domain.getUseStandarWidget().value, domain.getUseOptionalWidget().value, domain.getUseDashboard().value, domain.getUseFlowMenu().value,
					domain.getExternalUrlPermission().value);
			return newEntity;
		}
	}

	/**
	 * Part item set to entity.
	 *
	 * @param lstTopPagePartUseSetting the lst top page part use setting
	 * @return the list
	 */
	private List<SptmtPartItem> partItemSetToEntity(List<TopPagePartUseSetting> lstTopPagePartUseSetting) {
		List<SptmtPartItem> lstEntity = lstTopPagePartUseSetting.stream().map(item -> {
			// find entity
			SptmtPartItem entity = this.queryProxy().query(GET_ONE_PIS_BY_TPP_ID, SptmtPartItem.class)
					.setParameter("companyId", item.getCompanyId())
					.setParameter("topPagePartId", item.getTopPagePartId()).getSingleOrNull();
			if (entity != null) {
				entity.setUseAtr(item.getUseDivision().value);
				return entity;
			} else {
				CcgmtPartItemSetPK key = new CcgmtPartItemSetPK(item.getCompanyId(), item.getTopPagePartId());
				SptmtPartItem newEntity = new SptmtPartItem(key, item.getUseDivision().value);
				return newEntity;
			}
		}).collect(Collectors.toList());
		return lstEntity;
	}

	@Override
	public Optional<TopPagePartUseSetting> findTopPagePartUseSettingById(String companyId, String topPagePartId) {
		SptmtPartItem c = this.queryProxy().query(GET_ONE_PIS_BY_TPP_ID, SptmtPartItem.class)
				.setParameter("companyId", companyId).setParameter("topPagePartId", topPagePartId).getSingle().get();
		TopPagePartUseSetting pus = TopPagePartUseSetting.createFromJavaType(c.ccgmtPartItemSetPK.cid,
				c.ccgmtPartItemSetPK.topPagePartId, "", "", c.useAtr, null);
		return Optional.of(pus);
	}

	@Override
	public void removeTopPagePartUseSettingById(String companyId, String topPagePartId) {
		this.commandProxy().remove(SptmtPartItem.class, new CcgmtPartItemSetPK(companyId, topPagePartId));
		this.getEntityManager().flush();
	}

	@Override
	public List<TopPagePartUseSetting> findTopPagePartUseSettingByCompanyId(String companyId) {
		// get list item setting
		List<TopPagePartUseSetting> lstTopPagePartUseSetting = this.queryProxy()
				.query(GET_ONE_PIS, SptmtPartItem.class).setParameter("companyId", companyId)
				.getList(p -> pusToDomain(p));
		return lstTopPagePartUseSetting;
	}

	/**
	 * hoatt
	 * find my page setting
	 * @param companyId
	 * @return
	 */
	@Override
	public Optional<MyPageSetting> findMyPageSet(String companyId) {
		
		return this.queryProxy().find(companyId, SptmtMyPageSet.class).map(c->toDomainMyPageSet(c));
	}
	/**
	 * hoatt
	 * convert entity SptmtMyPageSet to domain MyPageSetting
	 * @param entity
	 * @return
	 */
	private MyPageSetting toDomainMyPageSet(SptmtMyPageSet entity) {
		 List<TopPagePartUseSetting> lstTopPart = null;
		val domain = MyPageSetting.createFromJavaType(entity.getCid(),
				Integer.valueOf(entity.getUseMyPageAtr()),
				Integer.valueOf(entity.getUseStandarWidgetAtr()),
				Integer.valueOf(entity.getUseOptionalWidgetAtr()),
				Integer.valueOf(entity.getUseDashBoardAtr()),
				Integer.valueOf(entity.getUseFolowMenuAtr()),
				Integer.valueOf(entity.getExternalUrlPermissionAtr()),
						lstTopPart);
		return domain;
	}
}
