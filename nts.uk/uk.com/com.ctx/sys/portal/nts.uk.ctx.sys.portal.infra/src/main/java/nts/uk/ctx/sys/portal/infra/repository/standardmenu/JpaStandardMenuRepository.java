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
	private final String SEL = "SELECT s FROM CcgstStandardMenu s ";
	private final String GET_ALL_STANDARD_MENU = "SELECT s FROM CcgstStandardMenu s WHERE s.ccgmtStandardMenuPK.companyId = :companyId";
	private final String FIND_BY_AFTER_LOGIN_DISPLAY = SEL + "WHERE s.ccgmtStandardMenuPK.companyId = :companyId "
			+ "AND s.afterLoginDisplay = :afterLoginDisplay ";
	private final String FIND_BY_SYSTEM_MENUCLASSIFICATION = SEL + "WHERE s.ccgmtStandardMenuPK.companyId = :companyId "
			+ "AND s.ccgmtStandardMenuPK.system = :system "
			+ "AND s.ccgmtStandardMenuPK.classification = :menu_classification ORDER BY s.ccgmtStandardMenuPK.code ASC";
	private final String FIND_BY_AFTERLOGINDISPLAY_SYSTEM_MENUCLASSIFICATION = SEL
			+ "WHERE s.ccgmtStandardMenuPK.companyId = :companyId " + "AND s.ccgmtStandardMenuPK.system = :system "
			+ "AND s.ccgmtStandardMenuPK.classification = :menu_classification AND s.afterLoginDisplay = :afterLoginDisplay "
			+ "ORDER BY s.ccgmtStandardMenuPK.code ASC";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository#findAll(
	 * java.lang. String)
	 */
	@Override
	public List<StandardMenu> findAll(String companyId) {
		return this.queryProxy().query(GET_ALL_STANDARD_MENU, CcgstStandardMenu.class)
				.setParameter("companyId", companyId).getList(t -> toDomain(t));
	}

	@Override
	public List<StandardMenu> findByAfterLoginDisplay(String companyId, int afterLoginDisplay) {
		return this.queryProxy().query(FIND_BY_AFTER_LOGIN_DISPLAY, CcgstStandardMenu.class)
				.setParameter("companyId", companyId).setParameter("afterLoginDisplay", afterLoginDisplay)
				.getList(t -> toDomain(t));
	}

	/**
	 * added by sonnh1
	 * 
	 * find by COMPANYID and SYSTEM and MENU_CLASSIFICATION
	 */
	@Override
	public List<StandardMenu> findBySystemMenuClassification(String companyId, int system, int menu_classification) {
		return this.queryProxy().query(FIND_BY_SYSTEM_MENUCLASSIFICATION, CcgstStandardMenu.class)
				.setParameter("companyId", companyId).setParameter("system", system)
				.setParameter("menu_classification", menu_classification).getList(t -> toDomain(t));
	}

	/**
	 * added by sonnh1
	 * 
	 * find by COMPANYID and AFTER_LOGIN_DISPLAY
	 */
	@Override
	public List<StandardMenu> findByAfterLgDisSysMenuCls(String companyId, int afterLoginDisplay, int system,
			int menu_classification) {
		return this.queryProxy().query(FIND_BY_AFTERLOGINDISPLAY_SYSTEM_MENUCLASSIFICATION, CcgstStandardMenu.class)
				.setParameter("companyId", companyId).setParameter("afterLoginDisplay", afterLoginDisplay)
				.setParameter("system", system).setParameter("menu_classification", menu_classification)
				.getList(t -> toDomain(t));
	}

	/**
	 * To domain.
	 *
	 * @param s
	 *            the s
	 * @return the top page
	 */
	private StandardMenu toDomain(CcgstStandardMenu s) {
		return StandardMenu.createFromJavaType(s.ccgmtStandardMenuPK.companyId, s.ccgmtStandardMenuPK.code,
				s.targetItems, s.displayName, s.displayOrder, s.menuAtr, s.url, s.ccgmtStandardMenuPK.system,
				s.ccgmtStandardMenuPK.classification, s.webMenuSetting, s.afterLoginDisplay, s.logSettingDisplay);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the ccgmt standard menu
	 */
	public CcgstStandardMenu toEntity(StandardMenu domain) {
		CcgstStandardMenuPK key = new CcgstStandardMenuPK(domain.getCompanyId(), domain.getCode().v(),
				domain.getSystem().value, domain.getClassification());
		return new CcgstStandardMenu(key, domain.getTargetItems(), domain.getDisplayName().v(),
				domain.getDisplayOrder(), domain.getMenuAtr(), domain.getUrl(), domain.getWebMenuSetting(),
				domain.getAfterLoginDisplay(), domain.getLogSettingDisplay());
	}
}
