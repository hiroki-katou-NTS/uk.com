package nts.uk.ctx.sys.portal.infra.repository.toppagepart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePartPK;

/**
 * @author LamDT
 */
@Stateless
public class JpaTopPagePartRepository extends JpaRepository implements TopPagePartRepository {

	private static final String SELECT_SINGLE = "SELECT c FROM CcgmtTopPagePart AS c WHERE c.ccgmtTopPagePartPK.topPagePartID = :topPagePartID";
	private static final String SELECT_SINGLE_BY_KEY = "SELECT c FROM CcgmtTopPagePart AS c WHERE c.ccgmtTopPagePartPK.topPagePartID = :topPagePartID AND c.ccgmtTopPagePartPK.companyID = :cID";
	private static final String SELECT_BY_COMPANY = "SELECT c FROM CcgmtTopPagePart AS c WHERE c.ccgmtTopPagePartPK.companyID = :companyID";
	private static final String SELECT_BY_TYPE = "SELECT c FROM CcgmtTopPagePart AS c WHERE c.ccgmtTopPagePartPK.companyID = :companyID AND c.topPagePartType = :topPagePartType";
	private static final String SELECT_BY_TYPES = "SELECT c FROM CcgmtTopPagePart AS c WHERE c.ccgmtTopPagePartPK.companyID = :companyID "
										+ "AND c.topPagePartType IN :topPagePartTypes";
	private static final String SELECT_BY_TYPE_AND_IDS = "SELECT c FROM CcgmtTopPagePart AS c WHERE c.ccgmtTopPagePartPK.companyID = :companyID "
												+ "AND c.ccgmtTopPagePartPK.topPagePartID IN :topPagePartIDs "
												+ "AND c.topPagePartType IN :topPagePartTypes";
	private static final String SELECT_BY_CODE_AND_TYPE = SELECT_BY_TYPE + " AND c.code = :code";

	@Override
	public List<TopPagePart> findAll(String companyID) {
		 return this.queryProxy().query(SELECT_BY_COMPANY, CcgmtTopPagePart.class)
					.setParameter("companyID", companyID)
					.getList(c -> toDomain(c));
	}

	@Override
	public List<TopPagePart> findByType(String companyID, int type) {
		 return this.queryProxy().query(SELECT_BY_TYPE, CcgmtTopPagePart.class)
				.setParameter("companyID", companyID)
				.setParameter("topPagePartType", type)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<TopPagePart> findByCodeAndType(String companyId, String code, int type) {
		return this.queryProxy().query(SELECT_BY_CODE_AND_TYPE, CcgmtTopPagePart.class)
				.setParameter("companyID", companyId)
				.setParameter("topPagePartType", type)
				.setParameter("code", code)
				.getSingle(c -> toDomain(c));
	}


	@Override
	public List<TopPagePart> findByTypes(String companyID, List<Integer> topPagePartTypes) {
		 return this.queryProxy().query(SELECT_BY_TYPES, CcgmtTopPagePart.class)
					.setParameter("companyID", companyID)
					.setParameter("topPagePartTypes", topPagePartTypes)
					.getList(c -> toDomain(c));
	}
	
	@Override
	public List<TopPagePart> findByTypesAndIDs(String companyID, List<Integer> topPagePartTypes, List<String> topPagePartIDs) {
		//hoatt
		if(topPagePartTypes.size()==0 || topPagePartIDs.size()==0){
			return new ArrayList<>();
		}
		 return this.queryProxy().query(SELECT_BY_TYPE_AND_IDS, CcgmtTopPagePart.class)
					.setParameter("companyID", companyID)
					.setParameter("topPagePartIDs", topPagePartIDs)
					.setParameter("topPagePartTypes", topPagePartTypes)
					.getList(c -> toDomain(c));
	}
	
	@Override
	public void remove(String companyID, String topPagePartID) {
		this.commandProxy().remove(CcgmtTopPagePart.class, new CcgmtTopPagePartPK(companyID, topPagePartID));
		this.getEntityManager().flush();
	}

	@Override
	public void add(TopPagePart topPagePart) {
		this.commandProxy().insert(toEntity(topPagePart));
	}

	@Override
	public void update(TopPagePart topPagePart) {
		CcgmtTopPagePart newEntity = toEntity(topPagePart);
		CcgmtTopPagePart updateEntity = this.queryProxy().find(newEntity.ccgmtTopPagePartPK, CcgmtTopPagePart.class).get();
		updateEntity.name = newEntity.name;
		updateEntity.width = newEntity.width;
		updateEntity.height = newEntity.height;
		this.commandProxy().update(updateEntity);
	}

	/**
	 * Convert entity to domain
	 * 
	 * @param entity CcgmtPlacement
	 * @return TopPagePart instance
	 */
	private TopPagePart toDomain(CcgmtTopPagePart entity) {
		return TopPagePart.createFromJavaType(
			entity.ccgmtTopPagePartPK.companyID, entity.ccgmtTopPagePartPK.topPagePartID,
			entity.code, entity.name,
			entity.topPagePartType,
			entity.width, entity.height);
	}

	/**
	 * Convert domain to entity
	 * 
	 * @param domain TopPagePart
	 * @return CcgmtTopPagePart instance
	 */
	private CcgmtTopPagePart toEntity(TopPagePart domain) {
		return new CcgmtTopPagePart(
			new CcgmtTopPagePartPK(domain.getCompanyID(), domain.getToppagePartID()),
			domain.getCode().v(), domain.getName().v(),
			domain.getType().value,
			domain.getSize().getWidth().v(), domain.getSize().getHeight().v()
		);
	}

	@Override
	public Optional<TopPagePart> findByKey(String cID, String topPagePartID) {
		return this.queryProxy().query(SELECT_SINGLE_BY_KEY, CcgmtTopPagePart.class)
				.setParameter("topPagePartID", topPagePartID)
				.setParameter("cID", cID)
				.getSingle(c -> toDomain(c));
	}
}