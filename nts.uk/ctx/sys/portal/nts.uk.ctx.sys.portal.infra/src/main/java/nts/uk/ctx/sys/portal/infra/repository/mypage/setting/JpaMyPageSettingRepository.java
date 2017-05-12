package nts.uk.ctx.sys.portal.infra.repository.mypage.setting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.CcgmtMyPageSet;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.CcgmtPartItemSet;
import nts.uk.ctx.sys.portal.infra.entity.mypage.setting.CcgmtPartItemSetPK;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;

@Stateless
public class JpaMyPageSettingRepository extends JpaRepository implements MyPageSettingRepository {

	private final String GET_ONE_MPS = "SELECT m FROM CcgmtMyPageSet m WHERE m.cid = :companyId";
	private final String GET_ONE_PIS = "SELECT p FROM CcgmtPartItemSet p WHERE p.ccgmtPartItemSetPK.cid = :companyId";
	private final String GET_ONE_TPP = "SELECT t FROM CcgmtTopPagePart t WHERE t.ccgmtTopPagePartPK.companyID = :companyId AND  t.code = :topPagePartCode AND t.topPagePartType = :topPagePartType";

	@Override
	public Optional<MyPageSetting> findByCompanyId(String companyId) {
		return this.queryProxy().query(GET_ONE_MPS, CcgmtMyPageSet.class).setParameter("companyId", companyId)
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

	@Override
	public void add(MyPageSetting myPageSetting) {
		// TODO Auto-generated method stub
	}

	/**
	 * Pus to domain.
	 *
	 * @param c
	 *            the c
	 * @return the top page part use setting
	 */
	private TopPagePartUseSetting pusToDomain(CcgmtPartItemSet c) {
		CcgmtTopPagePart tpp = this.queryProxy().query(GET_ONE_TPP, CcgmtTopPagePart.class)
				.setParameter("companyId", c.ccgmtPartItemSetPK.cid)
				.setParameter("topPagePartCode", c.ccgmtPartItemSetPK.partItemCode)
				.setParameter("topPagePartType", c.ccgmtPartItemSetPK.partType).getSingle().get();
		TopPagePartUseSetting pus = TopPagePartUseSetting.createFromJavaType(c.ccgmtPartItemSetPK.cid, tpp.code,
				tpp.name, c.useAtr, tpp.topPagePartType);
		// if update DB (no remove)
		// TopPagePartUseSetting pus =
		// TopPagePartUseSetting.createFromJavaType(c.ccgmtPartItemSetPK.cid,
		// c.ccgmtPartItemSetPK.partItemCode, c.partItemName, c.useAtr,
		// c.ccgmtPartItemSetPK.partType);
		return pus;
	}

	/**
	 * Mps to domain.
	 *
	 * @param c
	 *            the c
	 * @return the my page setting
	 */
	private MyPageSetting mpsToDomain(CcgmtMyPageSet c) {
		// get list item setting
		List<TopPagePartUseSetting> lstTopPagePartUseSetting = this.queryProxy()
				.query(GET_ONE_PIS, CcgmtPartItemSet.class).setParameter("companyId", c.cid)
				.getList(p -> pusToDomain(p));
		MyPageSetting mps = MyPageSetting.createFromJavaType(c.cid, c.useMyPageAtr, c.useWidgetAtr, c.useDashBoardAtr,
				c.useFolowMenuAtr, c.externalUrlPermissionAtr, lstTopPagePartUseSetting);
		return mps;
	}

	/**
	 * My page set to entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the ccgmt my page set
	 */
	private CcgmtMyPageSet myPageSetToEntity(MyPageSetting domain) {
		CcgmtMyPageSet entity = new CcgmtMyPageSet(domain.getCompanyId(), domain.getUseMyPage().value,
				domain.getUseWidget().value, domain.getUseDashboard().value, domain.getUseFlowMenu().value,
				domain.getExternalUrlPermission().value);
		return entity;
	}

	/**
	 * Part item set to entity.
	 *
	 * @param lstTopPagePartUseSetting
	 *            the lst top page part use setting
	 * @return the list
	 */
	private List<CcgmtPartItemSet> partItemSetToEntity(List<TopPagePartUseSetting> lstTopPagePartUseSetting) {
		List<CcgmtPartItemSet> lstEntity = lstTopPagePartUseSetting.stream().map(item -> {
			CcgmtPartItemSetPK key = new CcgmtPartItemSetPK(item.getCompanyId(), item.getTopPagePartCode().v(),
					item.getTopPagePartType().value);
			CcgmtPartItemSet entity = new CcgmtPartItemSet(key,item.getUseDivision().value);
			return entity;
		}).collect(Collectors.toList());
		return lstEntity;
	}
}
