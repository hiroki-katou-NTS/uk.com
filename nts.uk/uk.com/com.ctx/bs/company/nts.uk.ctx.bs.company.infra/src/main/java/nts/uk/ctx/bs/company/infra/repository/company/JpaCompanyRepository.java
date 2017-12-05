/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.infra.repository.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.company.dom.company.AddInfor;
import nts.uk.ctx.bs.company.dom.company.CompanyInforNew;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.infra.entity.company.BcmmtAddInfor;
import nts.uk.ctx.bs.company.infra.entity.company.BcmmtAddInforPK;
import nts.uk.ctx.bs.company.infra.entity.company.BcmmtCompanyInfor;
import nts.uk.ctx.bs.company.infra.entity.company.BcmmtCompanyInforPK;

/**
 * The Class JpaCompanyRepository.
 */
@Stateless
public class JpaCompanyRepository extends JpaRepository implements CompanyRepository {

	private static final String GETALLCOMPANY;

	public final String SELECT_BY_CID = "SELECT c FROM BcmmtCompany c WHERE c.cid = :cid" + " AND c.abolitionAtr = 0 "
			+ " AND c.employmentSystem = 1 ";
	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM BcmmtCompanyInfor e");
		builderString.append(" WHERE e.isAbolition = 0 ");
		GETALLCOMPANY = builderString.toString();
	}
	/**
	 * Bcmmt Company Infor author: Hoang Yen
	 */
	private final String SELECT_NO_WHERE = "SELECT c FROM BcmmtCompanyInfor c ";
	// bcmmt add infor
	private final String COUNT_ALL = "SELECT COUNT(c.bcmmtCompanyInforPK.companyId) FROM BcmmtCompanyInfor c ";
	private final String COUNT_ABOLISH = "SELECT COUNT(c.bcmmtCompanyInforPK.companyId) FROM BcmmtCompanyInfor c WHERE c.isAbolition = 1 AND c.bcmmtCompanyInforPK.companyId != :companyId ";
	
	@Override
	public List<CompanyInforNew> getAllCompany() {
		return this.queryProxy().query(GETALLCOMPANY, BcmmtCompanyInfor.class).getList(c -> toDomainCom(c));
	}

	/**
	 * for RequestList 108
	 */
	@Override
	public Optional<CompanyInforNew> getComanyInfoByCid(String cid) {
		BcmmtCompanyInfor entity = this.queryProxy().query(SELECT_BY_CID, BcmmtCompanyInfor.class).setParameter("cid", cid)
				.getSingleOrNull();
		CompanyInforNew company = new CompanyInforNew();
		if (entity != null) {
			company = toDomainCom(entity);
		}
		return Optional.of(company);
	}

	/**
	 * convert from entity to domain
	 * 
	 * @param entity
	 * @return author Hoang Yen
	 */
	private CompanyInforNew toDomainCom(BcmmtCompanyInfor entity) {
		AddInfor add = entity.bcmmtAddInfor == null ? null : toDomainAdd(entity.bcmmtAddInfor);
		CompanyInforNew domain = CompanyInforNew.createFromJavaType(entity.companyCode,
				entity.companyName, entity.startMonth, entity.isAbolition,
				entity.repname, entity.repost, entity.comNameKana, entity.shortComName,
				entity.contractCd, entity.taxNo, add);
		return domain;
	}

	/**
	 * convert to domain address
	 * 
	 * @param entity  
	 * @return author Hoang Yen
	 */
	private static AddInfor toDomainAdd(BcmmtAddInfor entity) {
		AddInfor domain = AddInfor.createFromJavaType(entity.bcmmtAddInforPK.companyId, entity.faxNum, entity.add_1,
				entity.add_2, entity.addKana_1, entity.addKana_2, entity.postCd, entity.phoneNum);
		return domain;
	}

	/**
	 * change from company domain to company entity
	 * 
	 * @param domain
	 * @return author: Hoang Yen
	 */
	private static BcmmtCompanyInfor toEntityCom(CompanyInforNew domain) {
		val entity = new BcmmtCompanyInfor();
		entity.bcmmtCompanyInforPK = new BcmmtCompanyInforPK(domain.getCompanyId());
		entity.companyCode = domain.getCompanyCode().v();
		entity.contractCd = domain.getContractCd().v();
		entity.repname = domain.getRepname().v();
		entity.repost = domain.getRepjob().v();
		entity.companyName = domain.getCompanyName().v();
		entity.comNameKana = domain.getComNameKana().v();
		entity.shortComName = domain.getShortComName().v();
		entity.isAbolition = domain.getIsAbolition().value;
		entity.startMonth = domain.getStartMonth().value;
		entity.taxNo = domain.getTaxNo().v();
		if (domain.getAddInfor() != null) {
			entity.bcmmtAddInfor = toEntityAdd(domain.getAddInfor());
		}
		return entity;
	}

	private static BcmmtAddInfor toEntityAdd(AddInfor domain) {
		val entity = new BcmmtAddInfor();
		entity.bcmmtAddInforPK = new BcmmtAddInforPK(domain.getCompanyId());
		entity.faxNum = domain.getFaxNum().v();
		entity.add_1 = domain.getAdd_1().v();
		entity.add_2 = domain.getAdd_2().v();
		entity.addKana_1 = domain.getAddKana_1().v();
		entity.addKana_2 = domain.getAddKana_2().v();
		entity.postCd = domain.getPostCd().v();
		entity.phoneNum = domain.getPhoneNum().v();
		return entity;
	}

	/**
	 * get all company in database 
	 * author: Hoang Yen
	 */
	@Override
	public List<CompanyInforNew> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, BcmmtCompanyInfor.class)
				.getList(c -> toDomainCom(c));
	}

	/**
	 * find company information by code 
	 * author: Hoang Yen
	 */
	@Override
	public Optional<CompanyInforNew> find(String companyId) {
		val pk = new BcmmtCompanyInforPK(companyId);
		return this.queryProxy().find(pk, BcmmtCompanyInfor.class).map(x -> toDomainCom(x));
	}
	
	/**
	 * update a company author: Hoang Yen
	 */
	@Override
	public void updateCom(CompanyInforNew company) {
		BcmmtCompanyInfor entity = toEntityCom(company);
		BcmmtCompanyInfor oldEntity = this.queryProxy().find(entity.bcmmtCompanyInforPK, BcmmtCompanyInfor.class).get();
		oldEntity.repname = entity.repname;
		oldEntity.repost = entity.repost;
		oldEntity.companyName = entity.companyName;
		oldEntity.comNameKana = entity.comNameKana;
		oldEntity.shortComName = entity.shortComName;
		oldEntity.isAbolition = entity.isAbolition;
		oldEntity.startMonth = entity.startMonth;
		oldEntity.taxNo = entity.taxNo;
		oldEntity.bcmmtAddInfor = entity.bcmmtAddInfor;
		this.commandProxy().update(oldEntity);
	}

	/**
	 * insert a company author: Hoang Yen
	 */
	@Override
	public void insertCom(CompanyInforNew company) {
		BcmmtCompanyInfor entity = toEntityCom(company);
		this.commandProxy().insert(entity);
	}

	/**
	 * delete a company item author: Hoang Yen
	 */
	@Override
	public void deleteCom(String companyId, String contractCd, String companyCode) {
		BcmmtCompanyInforPK bcmmtCompanyInforPK = new BcmmtCompanyInforPK(companyId);
		this.commandProxy().remove(BcmmtCompanyInfor.class, bcmmtCompanyInforPK);
	}

	/**
	 * find Address author: Hoang Yen
	 */
	@Override
	public Optional<AddInfor> findAdd(String companyId) {
		val pk = new BcmmtAddInforPK(companyId);
		return this.queryProxy().find(pk, BcmmtAddInfor.class).map(c -> toDomainAdd(c));
	}

	/**
	 * update address item author: Hoang Yen
	 */
	@Override
	public void updateAdd(AddInfor addInfor) {
		BcmmtAddInfor entity = toEntityAdd(addInfor);
		BcmmtAddInfor oldEntity = this.queryProxy().find(entity.bcmmtAddInforPK, BcmmtAddInfor.class).get();
		oldEntity.faxNum = entity.faxNum;
		oldEntity.add_1 = entity.add_1;
		oldEntity.add_2 = entity.add_2;
		oldEntity.addKana_1 = entity.addKana_1;
		oldEntity.addKana_2 = entity.addKana_2;
		oldEntity.postCd = entity.postCd;
		oldEntity.phoneNum = entity.phoneNum;
	}

	/**
	 * insert address item author: Hoang Yen
	 */
	@Override
	public void insertAdd(AddInfor addInfor) {
		BcmmtAddInfor entity = toEntityAdd(addInfor);
		this.commandProxy().insert(entity);
	}

	@Override
	public boolean checkAbolish(String currentCompanyId) {
		// get all company
		long totalCompany = this.queryProxy().query(COUNT_ALL, Long.class).getSingle().get();
		// filter by current company and abolish=true -> size 
		long listTrueSize = this.queryProxy().query(COUNT_ABOLISH, Long.class)
				.setParameter("companyId", currentCompanyId).getSingle().get();
		return listTrueSize == totalCompany - 1;
	}
}
