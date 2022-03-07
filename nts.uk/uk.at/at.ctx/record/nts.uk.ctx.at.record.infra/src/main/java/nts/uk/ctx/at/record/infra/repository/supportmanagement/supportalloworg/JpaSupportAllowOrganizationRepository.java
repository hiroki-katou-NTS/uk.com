package nts.uk.ctx.at.record.infra.repository.supportmanagement.supportalloworg;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.supportmanagement.supportalloworg.KshmtSupportPermittedOrg;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaSupportAllowOrganizationRepository extends JpaRepository implements SupportAllowOrganizationRepository {
    public static final String DELETE_BY_TARGET_ORG;
    public static final String SELECT_BY_TARGET_ORG;
    public static final String SELECT_BY_SUPPORT_ORG;
    public static final String SELECT_TARGET_BYID;
    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append( " DELETE ");
        builderString .append(" FROM KshmtSupportPermittedOrg a ");
        builderString .append(" WHERE a.permittedOrgPk.targetUnit =:targetUnit ");
        builderString .append(" AND a.permittedOrgPk.targetId =:targetId ");
        builderString .append(" AND a.companyId =:cid ");
        DELETE_BY_TARGET_ORG = builderString.toString();

        builderString = new StringBuilder();
        builderString.append( " SELECT a");
        builderString .append(" FROM KshmtSupportPermittedOrg a ");
        builderString .append(" WHERE a.permittedOrgPk.targetUnit =:targetUnit ");
        builderString .append(" AND a.permittedOrgPk.targetId =:targetId ");
        builderString .append(" AND a.companyId =:cid ");
        SELECT_BY_TARGET_ORG = builderString.toString();

        builderString = new StringBuilder();
        builderString.append( " SELECT a");
        builderString .append(" FROM KshmtSupportPermittedOrg a ");
        builderString .append(" WHERE a.permittedOrgPk.permittedTargetUnit =:permittedTargetUnit ");
        builderString .append(" AND a.permittedOrgPk.permittedTargetId =:permittedTargetId ");
        builderString .append(" AND a.companyId =:cid ");
        SELECT_BY_SUPPORT_ORG = builderString.toString();

        builderString = new StringBuilder();
        builderString.append( " SELECT a");
        builderString .append(" FROM KshmtSupportPermittedOrg a ");
        builderString .append(" WHERE a.companyId =:cid ");
        SELECT_TARGET_BYID = builderString.toString();
    }

    @Override
    public void insertAll(String cid, List<SupportAllowOrganization> supportAllowOrgs) {
        val listEntity = supportAllowOrgs.stream().map(e->KshmtSupportPermittedOrg.toEntity(e,cid)).collect(Collectors.toList());
        if(!listEntity.isEmpty()){
            this.commandProxy().insertAll(listEntity);
        }
    }

    @Override
    public void update(String cid, SupportAllowOrganization supportAllowOrg) {
        val entity = KshmtSupportPermittedOrg.toEntity(supportAllowOrg,cid);
        this.commandProxy().update(entity);
    }

    @Override
    public void delete(String cid, TargetOrgIdenInfor targetOrg) {
        this.getEntityManager().createQuery(DELETE_BY_TARGET_ORG)
                .setParameter("targetUnit",targetOrg.getUnit().value)
                .setParameter("targetId",targetOrg.getTargetId())
                .setParameter("cid",cid)
                .executeUpdate();
        this.getEntityManager().flush();

    }

    @Override
    public List<SupportAllowOrganization> getListByTargetOrg(String cid, TargetOrgIdenInfor targetOrg) {
        return    this.queryProxy().query(SELECT_BY_TARGET_ORG,KshmtSupportPermittedOrg.class)
                .setParameter("targetUnit",targetOrg.getUnit().value)
                .setParameter("targetId",targetOrg.getTargetId())
                .setParameter("cid",cid)
                .getList(this::toDomain);
    }

    @Override
    public List<SupportAllowOrganization> getListBySupportableOrg(String cid, TargetOrgIdenInfor supportableOrg) {
        return    this.queryProxy().query(SELECT_BY_SUPPORT_ORG,KshmtSupportPermittedOrg.class)
                .setParameter("permittedTargetUnit",supportableOrg.getUnit().value)
                .setParameter("permittedTargetId",supportableOrg.getTargetId())
                .setParameter("cid",cid)
                .getList(this::toDomain);
    }

    @Override
    public boolean exists(String cid, TargetOrgIdenInfor targetOrg) {
        return !this.getListByTargetOrg(cid,targetOrg).isEmpty();
    }

    @Override
    public List<SupportAllowOrganization> getByCid(String cid){
        return this.queryProxy().query(SELECT_TARGET_BYID,KshmtSupportPermittedOrg.class)
                .setParameter("cid",cid)
                .getList(this::toDomain);
    }

    private SupportAllowOrganization toDomain(KshmtSupportPermittedOrg entity){
        val unitTargetOrg = EnumAdaptor.valueOf(entity.getPermittedOrgPk().getTargetUnit(), TargetOrganizationUnit.class);
        val unitSupportable = EnumAdaptor.valueOf(entity.getPermittedOrgPk().getPermittedTargetUnit(), TargetOrganizationUnit.class);
        String targetOrgId = entity.permittedOrgPk.getTargetId();
        String targetSupportableId = entity.permittedOrgPk.getPermittedTargetId();
        TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.createFromTargetUnit(unitTargetOrg,targetOrgId);
        TargetOrgIdenInfor supportableOrganization = TargetOrgIdenInfor.createFromTargetUnit(unitSupportable,targetSupportableId);
        return SupportAllowOrganization.create(
                targetOrg,
                supportableOrganization
        );
    }
}
