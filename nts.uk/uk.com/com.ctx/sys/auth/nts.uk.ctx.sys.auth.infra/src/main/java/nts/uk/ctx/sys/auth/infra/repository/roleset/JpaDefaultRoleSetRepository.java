/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.roleset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtDefaultRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtDefaultRoleSetPK;

/**
 * Class JpaDefaultRoleSetRepository implement of DefaultRoleSetRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaDefaultRoleSetRepository extends JpaRepository implements DefaultRoleSetRepository {
    
    private static final String SELECT_DEFAULT_ROLE_SET_BY_COMPANY_ID_ROLE_SET_CD = "SELECT drs FROM SacmtDefaultRoleSet drs"
            + " WHERE drs.defaultRoleSetPK.companyId = :companyId "
            + "       AND drs.roleSetCd = :roleSetCd ";

    /**
     * Build Domain from Entity
     * @param entity
     * @return
     */
    private DefaultRoleSet toDomain(SacmtDefaultRoleSet entity) {
        return new DefaultRoleSet(entity.defaultRoleSetPK.companyId, entity.roleSetCd);
    }

    /**
     * Build Entity from Domain
     * @param domain
     * @return
     */
    private SacmtDefaultRoleSet toEntity(DefaultRoleSet domain) {
        return new SacmtDefaultRoleSet(new SacmtDefaultRoleSetPK(domain.getCompanyId()), domain.getRoleSetCd().v());
    }

    /**
     * Build Entity from Domain for updating (keep common fields)
     * @param domain
     * @param upEntity
     * @return
     */
    private SacmtDefaultRoleSet toEntityForUpdate(DefaultRoleSet domain, SacmtDefaultRoleSet upEntity) {
        upEntity.buildEntity(upEntity.defaultRoleSetPK, domain.getRoleSetCd().v());
        return upEntity;
    }    
    
    @Override
    public Optional<DefaultRoleSet> find(String companyId, String roleSetCd) {
        return this.queryProxy().query(SELECT_DEFAULT_ROLE_SET_BY_COMPANY_ID_ROLE_SET_CD, SacmtDefaultRoleSet.class)
                .setParameter("companyId", companyId)
                .setParameter("roleSetCd", roleSetCd)
                .getSingle(c -> toDomain(c));
    }
    
    @Override
    public Optional<DefaultRoleSet> findByCompanyId(String companyId) {
        SacmtDefaultRoleSetPK pk = new SacmtDefaultRoleSetPK(companyId);
        return this.queryProxy().find(pk, SacmtDefaultRoleSet.class).map(c -> toDomain(c));
    }

    @Override
    public void insert(DefaultRoleSet domain) {
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(DefaultRoleSet domain) {
         Optional<SacmtDefaultRoleSet> upEntity = this.queryProxy().find(new SacmtDefaultRoleSetPK(domain.getCompanyId()),
                 SacmtDefaultRoleSet.class);
        if (upEntity.isPresent()) {
            this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
        }
    }

    @Override
    public void delete(String companyId) {
        this.commandProxy().remove(SacmtDefaultRoleSet.class, new SacmtDefaultRoleSetPK(companyId));
    }

    @Override
    public void addOrUpdate(DefaultRoleSet domain) {
        Optional<SacmtDefaultRoleSet> upEntity = this.queryProxy().find(new SacmtDefaultRoleSetPK(domain.getCompanyId()),
                 SacmtDefaultRoleSet.class);
        if (upEntity.isPresent()) {
            this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
        } else {
            this.commandProxy().insert(toEntity(domain));
        }
    }
}
