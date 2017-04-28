package nts.uk.ctx.sys.portal.infra.repository.toppage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppage.CcgmtTopPage;
import nts.uk.ctx.sys.portal.infra.entity.toppage.CcgmtTopPagePK;

/**
 * The Class JpaTopPageRepository.
 */
@Stateless
public class JpaTopPageRepository extends JpaRepository implements TopPageRepository {

	private final String GET_ALL_TOP_PAGE = "SELECT t FROM CcgmtTopPage t WHERE t.CcgmtTopPagePK.cid = :companyId";
	private final String GET_BY_CODE = "SELECT b FROM CcgmtTopPage b WHERE b.CcgmtTopPagePK.cid = :companyId AND b.CcgmtTopPagePK.topPageCode = :topPageCode";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository#findAll(java.lang.
	 * String)
	 */
	@Override
	public List<TopPage> findAll(String companyId) {
		// Mock data
		List<TopPage> lstTopPage = new ArrayList<TopPage>();
		lstTopPage.add(TopPage.createFromJavaType("1", "001", "id", "no", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "002", "id", "2", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "003", "id", "4", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "004", "id", "5", 0));
		return lstTopPage;
		// return this.queryProxy().query(GET_ALL_TOP_PAGE, CcgmtTopPage.class)
		// .setParameter("companyId", companyId)
		// .getList(t -> toDomain(t));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository#findByCode(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public Optional<TopPage> findByCode(String companyId, String topPageCode) {
		List<TopPage> lstTopPage = new ArrayList<TopPage>();
		lstTopPage.add(TopPage.createFromJavaType("1", "001", "id", "no", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "002", "id", "2", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "003", "id", "4", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "004", "id", "5", 0));
		TopPage tp = lstTopPage.stream().filter(item -> {
			return item.getTopPageCode().v().equals(topPageCode);
		}).findAny().orElse(null);
		;
		return Optional.of(tp);
		// return this.queryProxy().query(GET_BY_CODE, CcgmtTopPage.class)
		// .setParameter("companyId", companyId)
		// .setParameter("topPageCode", topPageCode)
		// .getSingle(t -> toDomain(t));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository#add(nts.uk.ctx.sys.
	 * portal.dom.toppage.TopPage)
	 */
	@Override
	public void add(TopPage topPage) {
		this.commandProxy().insert(toEntity(topPage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository#update(nts.uk.ctx.sys
	 * .portal.dom.toppage.TopPage)
	 */
	@Override
	public void update(TopPage topPage) {
		this.commandProxy().update(toEntity(topPage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository#remove(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void remove(String CompanyId, String topPageCode) {
		CcgmtTopPagePK key = new CcgmtTopPagePK(CompanyId, topPageCode);
		this.commandProxy().remove(CcgmtTopPage.class, key);
	}

	/**
	 * To domain.
	 *
	 * @param t
	 *            the t
	 * @return the top page
	 */
	private TopPage toDomain(CcgmtTopPage t) {
		return TopPage.createFromJavaType(t.ccgmtTopPagePK.cid, t.ccgmtTopPagePK.topPageCode, t.layoutId, t.topPageName,
				t.languageNumber);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the ccgmt top page
	 */
	public CcgmtTopPage toEntity(TopPage domain) {
		CcgmtTopPagePK key = new CcgmtTopPagePK(domain.getCompanyId().v(), domain.getTopPageCode().v());
		return new CcgmtTopPage(key, domain.getTopPageName().v(), domain.getLanguageNumber(), domain.getLayoutId());
	}
}
