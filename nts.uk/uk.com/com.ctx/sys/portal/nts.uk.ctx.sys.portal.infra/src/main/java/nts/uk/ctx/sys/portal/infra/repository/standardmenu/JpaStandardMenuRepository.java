package nts.uk.ctx.sys.portal.infra.repository.standardmenu;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.standardmenu.CcgstStandardMenu;
import nts.uk.ctx.sys.portal.infra.entity.standardmenu.CcgstStandardMenuPK;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageJobSet;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageJobSetPK;

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
	private final String GET_ALL_STANDARD_MENU_BY_ATR = "SELECT s FROM CcgstStandardMenu s WHERE s.ccgmtStandardMenuPK.companyId = :companyId "
			+ "AND s.webMenuSetting = :webMenuSetting "
			+ "AND s.menuAtr = :menuAtr"; 
	//hoatt
	private final String SELECT_STANDARD_MENU_BY_CODE = "SELECT c FROM CcgstStandardMenu c WHERE c.ccgmtStandardMenuPK.companyId = :companyId "
			+ " AND c.ccgmtStandardMenuPK.code = :code"
			+ " AND c.ccgmtStandardMenuPK.system = :system"
			+ " AND c.ccgmtStandardMenuPK.classification = :menu_classification";
	
	private CcgstStandardMenu toEntity(StandardMenu domain) {
		val entity = new CcgstStandardMenu();

		entity.ccgmtStandardMenuPK = new CcgstStandardMenuPK();
		entity.ccgmtStandardMenuPK.companyId = domain.getCompanyId();
		entity.ccgmtStandardMenuPK.code = domain.getCode().v();
		entity.ccgmtStandardMenuPK.system = domain.getSystem().value;
		entity.ccgmtStandardMenuPK.classification = domain.getClassification();
		entity.afterLoginDisplay = domain.getAfterLoginDisplay();
		entity.displayName = domain.getDisplayName().v();
		entity.displayOrder = domain.getDisplayOrder();
		entity.logSettingDisplay = domain.getLogSettingDisplay();
		entity.menuAtr = domain.getMenuAtr().value;
		entity.targetItems = domain.getTargetItems();
		entity.url = domain.getUrl();
		entity.webMenuSetting = domain.getWebMenuSetting().value;
		return entity;
	}

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
	
	@Override
	public List<StandardMenu> findByAtr(String companyId, int webMenuSetting , int menuAtr) {
		return this.queryProxy().query(GET_ALL_STANDARD_MENU_BY_ATR, CcgstStandardMenu.class)
				 .setParameter("companyId", companyId)
				 .setParameter("webMenuSetting", webMenuSetting)
				 .setParameter("menuAtr", menuAtr)
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
	 * hoatt
	 * get standard menu
	 * @param companyId
	 * @param code
	 * @param system
	 * @param classification
	 * @return
	 */
	@Override
	public Optional<StandardMenu> getStandardMenubyCode(String companyId, String code, int system, int classification) {
		return this.queryProxy().query(SELECT_STANDARD_MENU_BY_CODE, CcgstStandardMenu.class)
				.setParameter("companyId", companyId)
				.setParameter("code", code)
				.setParameter("system", system)
				.setParameter("classification", classification)
				.getSingle(c->toDomain(c));
	}
}
