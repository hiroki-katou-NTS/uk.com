package nts.uk.ctx.at.shared.infra.repository.workrule.shiftmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.Getter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMaterOrg;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaShiftMaterOrgImpl extends JpaRepository implements ShiftMasterOrgRepository {
	private static final String SELECT_ALL = "SELECT c FROM KshmtShiftMaterOrg c ";
	private static final String SELECT_BY_CID = SELECT_ALL + " WHERE c.kshmtShiftMaterOrgPK.companyId = :companyId ";
	private static final String SELECT_BY_CID_AND_TARGET = SELECT_ALL
			+ " WHERE c.kshmtShiftMaterOrgPK.companyId = :companyId "
			+ " AND c.kshmtShiftMaterOrgPK.targetUnit = :targetUnit "
			+ " AND c.kshmtShiftMaterOrgPK.targetId = :targetId";
	private static final String DELETE_BY_CID_AND_TARGET = "DELETE from KshmtShiftMaterOrg c "
			+ " WHERE c.kshmtShiftMaterOrgPK.companyId = :companyId"
			+ " AND c.kshmtShiftMaterOrgPK.targetUnit = :targetUnit"
			+ " AND c.kshmtShiftMaterOrgPK.targetId = :targetId";
	private static final String SELECT_ALREADY_SETTING_WORKPLACE = SELECT_ALL
			+ " WHERE c.kshmtShiftMaterOrgPK.companyId = :companyId "
			+ " AND c.kshmtShiftMaterOrgPK.targetUnit = :targetUnit ";
	@Override
	public boolean exists(String companyId, TargetOrgIdenInfor info) {
		Optional<ShiftMasterOrganization> exist = getByTargetOrg(companyId, info);
		return exist.isPresent();
	}

	@Override
	public void insert(ShiftMasterOrganization shiftMaterOrg) {
		List<String> shiftMasterCodes = shiftMaterOrg.getListShiftMaterCode();
		
		shiftMasterCodes.forEach(code -> {
			this.commandProxy().insert(KshmtShiftMaterOrg.toEntity(shiftMaterOrg, code));
		});
		
	}

	@Override
	public void update(ShiftMasterOrganization shiftMater) {
		delete(shiftMater.getCompanyId(), shiftMater.getTargetOrg());
		insert(shiftMater);
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg) {
		TargetOrg target = new TargetOrg(targetOrg);
		this.getEntityManager().createQuery(DELETE_BY_CID_AND_TARGET)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", target.getTargetUnit())
				.setParameter("targetId", target.getTargetId())
				.executeUpdate();

	}

	@Override
	public Optional<ShiftMasterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg) {
		TargetOrg target = new TargetOrg(targetOrg);
		List<KshmtShiftMaterOrg> datas = this.queryProxy()
				.query(SELECT_BY_CID_AND_TARGET, KshmtShiftMaterOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", target.getTargetUnit())
				.setParameter("targetId", target.getTargetId())
				.getList();
		
		if (CollectionUtil.isEmpty(datas)) {
			return Optional.ofNullable(null);
		}
		
		KshmtShiftMaterOrg first = datas.get(0);
		List<String> shiftMasterCodes = datas.stream().map(e -> e.kshmtShiftMaterOrgPK.getShiftMaterCode()).collect(Collectors.toList());
		ShiftMasterOrganization domain = first.toDomain(shiftMasterCodes);
		
		return Optional.ofNullable(domain);
	}
	
	@Override
	public List<ShiftMasterOrganization> getByListTargetOrg(String companyId, List<TargetOrgIdenInfor> targetOrgs) {
		List<ShiftMasterOrganization> results = new ArrayList<>();
		
		for (TargetOrgIdenInfor target : targetOrgs) {
			Optional<ShiftMasterOrganization> oResult = getByTargetOrg(companyId, target);
			if(oResult.isPresent()) {
				results.add(oResult.get());
			}
		}
		
		return results;
	}

	@Override
	public List<ShiftMasterOrganization> getByCid(String companyId) {
		List<ShiftMasterOrganization> datas = this.queryProxy().query(SELECT_BY_CID, KshmtShiftMaterOrg.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
		return datas;
	}
	
	@Getter
	class TargetOrg {
		private Integer targetUnit;
		private String targetId;
		public TargetOrg(TargetOrgIdenInfor targetOrg) {
			Integer targetUnit = targetOrg.getUnit().value;
			this.targetUnit = targetUnit;
			if (targetUnit == TargetOrganizationUnit.WORKPLACE.value) {
				this.targetId = targetOrg.getWorkplaceId().get();
			} else {
				this.targetId = targetOrg.getWorkplaceGroupId().get();
			}
		}
	}

	@Override
	public List<String> getAlreadySettingWorkplace(String companyId) {
		List<KshmtShiftMaterOrg> datas = this.queryProxy()
				.query(SELECT_ALREADY_SETTING_WORKPLACE, KshmtShiftMaterOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", TargetOrganizationUnit.WORKPLACE.value)
				.getList();
		return datas.stream().map(d -> d.kshmtShiftMaterOrgPK.targetId).distinct().collect(Collectors.toList());
	}
}
