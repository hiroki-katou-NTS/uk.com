package nts.uk.ctx.sys.portal.infra.repository.standardmenu;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.standardmenu.CcgstStandardMenu;
import nts.uk.ctx.sys.portal.infra.entity.standardmenu.CcgstStandardMenuPK;

/**
 * The Class JpaStandardMenuRepository.
 */
@Stateless
public class JpaStandardMenuRepository extends JpaRepository implements StandardMenuRepository {

	private final String GET_ALL_STANDARD_MENU = "SELECT s FROM CcgstStandardMenu s WHERE s.ccgmtStandardMenuPK.companyId = :companyId";
	private final String GET_ALL_STANDARD_MENU_AFTER_LOGIN_DISPLAY_INDICATOR_IS_TRUE = "SELECT s FROM CcgstStandardMenu s WHERE s.ccgmtStandardMenuPK.companyId = :companyId AND s.afterLoginDisplay = :afterLoginDisplay";
	private final int afterLoginDisplay = 1;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository#findAll(java.lang.
	 * String)
	 */
	@Override
	public List<StandardMenu> findAll(String companyId) {
		return this.queryProxy().query(GET_ALL_STANDARD_MENU, CcgstStandardMenu.class)
				 .setParameter("companyId", companyId)
				 .getList(t -> toDomain(t));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository#findAllWithAfterLoginDisplayIndicatorIsTrue(java.lang.
	 * String)
	 */
	@Override
	public List<StandardMenu> findAllWithAfterLoginDisplayIndicatorIsTrue(String companyId) {
		return this.queryProxy().query(GET_ALL_STANDARD_MENU_AFTER_LOGIN_DISPLAY_INDICATOR_IS_TRUE, CcgstStandardMenu.class)
				 .setParameter("companyId", companyId)
				 .setParameter("afterLoginDisplay", afterLoginDisplay)
				 .getList(t -> toDomain(t));
	}

	/**
	 * To domain.
	 *
	 * @param s the s
	 * @return the top page
	 */
	private StandardMenu toDomain(CcgstStandardMenu s) {
		return StandardMenu.createFromJavaType(s.ccgmtStandardMenuPK.companyId, s.ccgmtStandardMenuPK.code, s.targetItems,
				s.displayName, s.displayOrder, s.menuAtr, s.url, s.system, s.classification, s.webMenuSetting, 
				s.afterLoginDisplay, s.logSettingDisplay);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the ccgmt standard menu
	 */
	public CcgstStandardMenu toEntity(StandardMenu domain) {
		CcgstStandardMenuPK key = new CcgstStandardMenuPK(domain.getCompanyId(), domain.getCode().v());
		return new CcgstStandardMenu(key, domain.getTargetItems(), domain.getDisplayName().v(), domain.getDisplayOrder(),
				domain.getMenuAtr(), domain.getUrl(), domain.getSystem(), domain.getClassification(), domain.getWebMenuSetting(),
				domain.getAfterLoginDisplay(), domain.getLogSettingDisplay());
	}
}
