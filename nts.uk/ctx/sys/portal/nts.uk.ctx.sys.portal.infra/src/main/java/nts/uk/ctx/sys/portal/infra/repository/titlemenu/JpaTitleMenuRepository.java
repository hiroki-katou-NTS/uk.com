/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.infra.repository.titlemenu;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.titlemenu.CcgmtTitleMenu;

@Stateless
public class JpaTitleMenuRepository extends JpaRepository implements TitleMenuRepository{
	
	private final String SELECT= "SELECT c FROM CcgmtTitleMenu c";
	private final String SELECT_SINGLE = "SELECT c FROM CcgmtTitleMenu c WHERE c.ccgmtTitleMenuPK.companyID = :companyID AND c.ccgmtTitleMenuPK.titlemenuCD = :titlemenuCD";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.ccgmtTitleMenuPK.companyID = :companyID";
	
	/**
     * Get list of TitleMenu
     * @param companyID
     * @return List of TitleMenu
     */
	@Override
	public List<TitleMenu> findAll(String companyID) {
		return this.queryProxy()
				.query(SELECT_ALL_BY_COMPANY, CcgmtTitleMenu.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}
	/**
	 * Get Optional TitleMenu
	 * @param companyID
	 * @param titleMenuCD
	 * @return Optional of TitleMenu
	 */
	@Override
	public Optional<TitleMenu> findByCode(String companyID, String titleMenuCD) {
		return this.queryProxy()
				.query(SELECT_SINGLE, CcgmtTitleMenu.class)
				.setParameter("companyID", companyID)
				.setParameter("titlemenuCD", titleMenuCD)
				.getSingle(c -> toDomain(c));
	}
	/**
	 * Add
	 * @param companyID
	 * @param titleMenuCD
	 * @return 
	 */
	@Override
	public void add(TitleMenu title) {
		this.commandProxy().insert(CcgmtTitleMenu.class);
		}
	/**
	 * Update
	 * @param companyID
	 * @param titleMenuCD
	 * @return 
	 */	
	@Override
	public void update(TitleMenu title) {
				this.commandProxy().update(CcgmtTitleMenu.class);
	}
	/**
	 * Remove
	 * @param companyID
	 * @param titleMenuCD
	 * @return 
	 */
	@Override
	public void remove(String companyID, String titleMenuCD) {
		this.commandProxy().remove(CcgmtTitleMenu.class);
	}

	private TitleMenu toDomain(CcgmtTitleMenu entity) {
		return TitleMenu.createFromJavaType(entity.ccgmtTitleMenuPK.companyID, entity.ccgmtTitleMenuPK.titleMenuCD, entity.layoutID, entity.name);
	}

}

