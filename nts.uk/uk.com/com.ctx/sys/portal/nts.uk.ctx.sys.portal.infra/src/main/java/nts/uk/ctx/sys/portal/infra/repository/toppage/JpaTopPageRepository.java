/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.infra.repository.toppage;

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

	private static  final String GET_ALL_TOP_PAGE = "SELECT t FROM CcgmtTopPage t "
			+ "WHERE t.ccgmtTopPagePK.cid = :companyId "
			+ " ORDER BY t.ccgmtTopPagePK.topPageCode";
	private static final String GET_BY_CODE = "SELECT b FROM CcgmtTopPage b WHERE b.ccgmtTopPagePK.cid = :companyId AND b.ccgmtTopPagePK.topPageCode = :topPageCode";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository#findAll(java.lang.
	 * String)
	 */
	@Override
	public List<TopPage> findAll(String companyId) {
		 return this.queryProxy().query(GET_ALL_TOP_PAGE, CcgmtTopPage.class)
		 .setParameter("companyId", companyId)
		 .getList(t -> toDomain(t));
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
		 return this.queryProxy().query(GET_BY_CODE, CcgmtTopPage.class)
		 .setParameter("companyId", companyId)
		 .setParameter("topPageCode", topPageCode)
		 .getSingle(t -> toDomain(t));
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
		CcgmtTopPage entity = toEntity(topPage);
		this.commandProxy().insert(entity);
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
		CcgmtTopPage entity = toEntity(topPage);
		this.commandProxy().update(entity);
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
	 * @param t the t
	 * @return the top page
	 */
	private TopPage toDomain(CcgmtTopPage t) {
		return TopPage.createFromJavaType(t.ccgmtTopPagePK.cid, t.ccgmtTopPagePK.topPageCode, t.layoutId, t.topPageName,
				t.languageNumber);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the ccgmt top page
	 */
	public CcgmtTopPage toEntity(TopPage domain) {
		CcgmtTopPage entity = this.findEntity(domain.getCompanyId(), domain.getTopPageCode().v());
		if (entity != null) {
			entity.setLanguageNumber(domain.getLanguageNumber());
			entity.setLayoutId(domain.getLayoutId());
			entity.setTopPageName(domain.getTopPageName().v());
			return entity;
		} else {
			CcgmtTopPagePK key = new CcgmtTopPagePK(domain.getCompanyId(), domain.getTopPageCode().v());
			return new CcgmtTopPage(key, domain.getTopPageName().v(), domain.getLanguageNumber(), domain.getLayoutId());
		}
	}
	
	public CcgmtTopPage findEntity(String companyId, String topPageCode) {
		 return this.queryProxy().query(GET_BY_CODE, CcgmtTopPage.class)
		 .setParameter("companyId", companyId)
		 .setParameter("topPageCode", topPageCode)
		 .getSingleOrNull();
	}

}
