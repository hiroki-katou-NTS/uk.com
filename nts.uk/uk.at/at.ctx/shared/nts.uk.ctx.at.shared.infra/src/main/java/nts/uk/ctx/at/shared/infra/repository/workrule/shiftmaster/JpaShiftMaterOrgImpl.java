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
	
	private static final String DELETE_BY_SHIFTMASTER_CD = "DELETE from KshmtShiftMaterOrg c "
			+ " WHERE c.kshmtShiftMaterOrgPK.shiftMaterCode = :shiftMaterCode";
	
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
		List<String> shiftMasterCodes = shiftMater.getListShiftMaterCode();
		String get = "Select a.kshmtShiftMaterOrgPK.shiftMaterCode From KshmtShiftMaterOrg a "
				+ " Where a.kshmtShiftMaterOrgPK.companyId = :companyId "
				+ " And a.kshmtShiftMaterOrgPK.targetUnit = :targetUnit "
				+ " And a.kshmtShiftMaterOrgPK.targetId = :targetId";
		List<String> result = this.queryProxy().query(get, String.class)
				.setParameter("companyId", shiftMater.getCompanyId())
				.setParameter("targetUnit", shiftMater.getTargetOrg().getUnit().value)
				.setParameter("targetId", shiftMater.getTargetOrg().getUnit().value == 0 ? 
						shiftMater.getTargetOrg().getWorkplaceId().get()
						: shiftMater.getTargetOrg().getWorkplaceGroupId().get())
				.getList(e -> e);
		List<String> listToDel = result.stream().filter(e-> !shiftMasterCodes.contains(e)).collect(Collectors.toList());
		String delete = "delete from KshmtShiftMaterOrg o "
				+ " where o.kshmtShiftMaterOrgPK.companyId = :companyId "
				+ " and o.kshmtShiftMaterOrgPK.targetUnit = :targetUnit "
				+ " and o.kshmtShiftMaterOrgPK.targetId = :targetId "
				+ "  and o.kshmtShiftMaterOrgPK.shiftMaterCode IN :shiftMaterCode";
		if (!listToDel.isEmpty()) {			
			this.getEntityManager().createQuery(delete).setParameter("companyId", shiftMater.getCompanyId())
			.setParameter("targetUnit", shiftMater.getTargetOrg().getUnit().value)
			.setParameter("targetId", shiftMater.getTargetOrg().getUnit().value == 0 ? 
					shiftMater.getTargetOrg().getWorkplaceId().get()
					: shiftMater.getTargetOrg().getWorkplaceGroupId().get())
			.setParameter("shiftMaterCode", listToDel).executeUpdate();
		}
		
		List<String> listToInsert = shiftMasterCodes.stream().filter(e-> !result.contains(e)).collect(Collectors.toList());
		listToInsert.forEach(code -> {
			this.commandProxy().insert(KshmtShiftMaterOrg.toEntity(shiftMater, code));
		});
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
	public List<ShiftMasterOrganization> getAlreadySettingWorkplace(String companyId, int unit) {
		List<KshmtShiftMaterOrg> datas = this.queryProxy()
				.query(SELECT_ALREADY_SETTING_WORKPLACE, KshmtShiftMaterOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", TargetOrganizationUnit.WORKPLACE.value)
				.getList();
		return datas.stream().map(mapper-> mapper.toDomain()).collect(Collectors.toList());
	}
	
	@Override
	public List<ShiftMasterOrganization> getAlreadySettingWorkplaceGrp(String companyId, int unit) {
		List<KshmtShiftMaterOrg> datas = this.queryProxy()
				.query(SELECT_ALREADY_SETTING_WORKPLACE, KshmtShiftMaterOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", TargetOrganizationUnit.WORKPLACE_GROUP.value)
				.getList();
		return datas.stream().map(mapper-> mapper.toDomain()).collect(Collectors.toList());
	}
	
	@Override
	public void deleteByShiftMasterCd(String shiftMasterCd) {
		this.getEntityManager().createQuery(DELETE_BY_SHIFTMASTER_CD)
				.setParameter("shiftMaterCode", shiftMasterCd)
				.executeUpdate();
	}
}
