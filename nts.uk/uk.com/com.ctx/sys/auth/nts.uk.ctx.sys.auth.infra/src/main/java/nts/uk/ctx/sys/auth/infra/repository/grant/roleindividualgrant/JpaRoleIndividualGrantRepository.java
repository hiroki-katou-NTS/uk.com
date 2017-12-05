/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.grant.roleindividualgrant;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.infra.entity.grant.roleindividualgrant.SacmtRoleIndiviGrant;
import nts.uk.ctx.sys.auth.infra.entity.grant.roleindividualgrant.SacmtRoleIndiviGrantPK;

@Stateless
public class JpaRoleIndividualGrantRepository extends JpaRepository implements RoleIndividualGrantRepository {
	
	private final String SELECT_BY_ROLE = "SELECT c FROM SacmtRoleIndiviGrant c WHERE c.SacmtRoleIndiviGrantPK.cid = :cid AND c.SacmtRoleIndiviGrantPK.roleType = roleType";
	
	private final String SELECT_BY_KEY = "SELECT c FROM SacmtRoleIndiviGrant c WHERE c.SacmtRoleIndiviGrantPK.userId = :userId AND c.SacmtRoleIndiviGrantPK.cid = :cid AND c.SacmtRoleIndiviGrantPK.roleId = :roleId";

	private final String SELECT_BY_DATE = "SELECT c FROM SacmtRoleIndiviGrant c WHERE c.SacmtRoleIndiviGrantPK.cid = :cid AND c.SacmtRoleIndiviGrant.      ";
	
	private final String SELECT_BY_ROLE_ID ="SELECT c FROM SacmtRoleIndiviGrant c WHERE c.roleId = :roleId ";
	

	@Override
	public Optional<RoleIndividualGrant> findByUser(String userId, GeneralDate date) {
		// TODO Auto-generated method stub
		return this.queryProxy()
				.query(SELECT_BY_DATE, RoleIndividualGrant.class)
				.setParameter("userId", userId)
				.setParameter("date", date)
				.getSingle();
	}

	public void add(RoleIndividualGrant roleIndividualGrant) {
			this.commandProxy().insert(toEntity(roleIndividualGrant));		
	}

	@Override
	public void update(RoleIndividualGrant roleIndividualGrant) {
		SacmtRoleIndiviGrant newEntity = toEntity(roleIndividualGrant);
		SacmtRoleIndiviGrant updateEntity = this.queryProxy().find(newEntity.sacmtRoleIndiviGrantPK, SacmtRoleIndiviGrant.class).get();
		updateEntity.roleId = newEntity.roleId;
		updateEntity.strD = newEntity.strD;
		updateEntity.endD = newEntity.endD;
		
		
	}

	@Override
	public void remove(String userId, String companyId, RoleType roleType) {
		this.commandProxy().remove(SacmtRoleIndiviGrant.class, new SacmtRoleIndiviGrantPK(companyId , userId , roleType.value ));
		this.getEntityManager().flush();
	}
	
	private SacmtRoleIndiviGrant toEntity(RoleIndividualGrant domain){
		
		return new SacmtRoleIndiviGrant(
				new SacmtRoleIndiviGrantPK(domain.getCompanyId(), domain.getUserId(), domain.getRoleType().value),
				domain.getRoleId(),
				domain.getValidPeriod().start(),
				domain.getValidPeriod().end()
				);
	}
	
	private RoleIndividualGrant toDomain(SacmtRoleIndiviGrant entity){
		return RoleIndividualGrant.createFromJavaType(	
				entity.sacmtRoleIndiviGrantPK.userID,
				entity.roleId,
				entity.sacmtRoleIndiviGrantPK.companyID,
				entity.sacmtRoleIndiviGrantPK.roleType.intValue(),
				entity.strD, 
				entity.endD);
	}

	@Override
	public List<RoleIndividualGrant> findByRoleId(String roleId) {		
		List<RoleIndividualGrant>  result = new ArrayList<RoleIndividualGrant>();		
		List<SacmtRoleIndiviGrant> entities =  this.queryProxy().query(SELECT_BY_ROLE_ID, SacmtRoleIndiviGrant.class).setParameter("roleId", roleId).getList();
		if(entities != null && !entities.isEmpty()){
			result =entities.stream().map( e -> toDomain(e)).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public Optional<RoleIndividualGrant> findByUserAndRole(String userId, RoleType roleType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleIndividualGrant> findUser(String userId, GeneralDate startDate, GeneralDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleIndividualGrant> findByRoleType(int roleType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<RoleIndividualGrant> findRoleIndividualGrant(String userID, String companyID, RoleType roleType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleIndividualGrant findByKey(String userId, String companyId, String roleId) {
		RoleIndividualGrant  result = new RoleIndividualGrant();		
		SacmtRoleIndiviGrant entities =  this.queryProxy().query(SELECT_BY_KEY, SacmtRoleIndiviGrant.class)
			.setParameter("userId", userId)
			.setParameter("cid", companyId)
			.setParameter("roleId", roleId).getSingleOrNull();
		if(entities != null){
			result = this.toDomain(entities);
		}
		return result;
	}

	@Override
	public List<RoleIndividualGrant> findByCompanyIdAndRoleType(String companyID, int roleType) {
		// TODO Auto-generated method stub
		return null;
	}


}
