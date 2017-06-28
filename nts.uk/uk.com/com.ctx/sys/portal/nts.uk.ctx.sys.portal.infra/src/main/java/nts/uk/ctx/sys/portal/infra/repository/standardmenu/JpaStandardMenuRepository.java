package nts.uk.ctx.sys.portal.infra.repository.standardmenu;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.standardmenu.CcgmtStandardMenu;
import nts.uk.ctx.sys.portal.infra.entity.standardmenu.CcgmtStandardMenuPK;

/**
 * The Class JpaStandardMenuRepository.
 */
@Stateless
public class JpaStandardMenuRepository extends JpaRepository implements StandardMenuRepository {

	private final String GET_ALL_STANDARD_MENU = "SELECT s FROM CcgmtStandardMenu s WHERE s.ccgmtStandardMenuPK.companyId = :companyId";
	private final String GET_ALL_STANDARD_MENU_AFTER_LOGIN_DISPLAY_INDICATOR_IS_TRUE = "SELECT s FROM CcgmtStandardMenu s WHERE s.ccgmtStandardMenuPK.companyId = :companyId AND s.afterLoginDisplayIndicator = :afterLoginDisplayIndicatorValue";
	private final int AFTER_LOGIN_DISPLAY_INDICATOR_VALUE = 1;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository#findAll(java.lang.
	 * String)
	 */
	@Override
	public List<StandardMenu> findAll(String companyId) {
		return this.queryProxy().query(GET_ALL_STANDARD_MENU, CcgmtStandardMenu.class)
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
		return this.queryProxy().query(GET_ALL_STANDARD_MENU_AFTER_LOGIN_DISPLAY_INDICATOR_IS_TRUE, CcgmtStandardMenu.class)
				 .setParameter("companyId", companyId).setParameter("afterLoginDisplayIndicatorValue", AFTER_LOGIN_DISPLAY_INDICATOR_VALUE)
				 .getList(t -> toDomain(t));
	}

	/**
	 * To domain.
	 *
	 * @param s the s
	 * @return the top page
	 */
	private StandardMenu toDomain(CcgmtStandardMenu s) {
		return StandardMenu.createFromJavaType(s.ccgmtStandardMenuPK.companyId, s.url, s.webMenuSettingDisplayIndicator, s.code, s.system,
				s.classification, s.afterLoginDisplayIndicator, s.logSettingDisplayIndicator, s.targetItems, s.displayName);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the ccgmt standard menu
	 */
	public CcgmtStandardMenu toEntity(StandardMenu domain) {
		CcgmtStandardMenuPK key = new CcgmtStandardMenuPK(domain.getCompanyId());
		return new CcgmtStandardMenu(key, domain.getUrl(), domain.getWebMenuSettingDisplayIndicator(), domain.getCode(), domain.getSystem(),
				domain.getClassification(), domain.getAfterLoginDisplayIndicator(), domain.getLogSettingDisplayIndicator(), domain.getTargetItems(), 
				domain.getDisplayName());
	}
}
