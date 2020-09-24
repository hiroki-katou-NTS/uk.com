package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.workmethodrelationship;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrg;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipOrgRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkMethodRelationshipOrg extends JpaRepository implements WorkMethodRelationshipOrgRepo{

	@Override
	public void insert(WorkMethodRelationshipOrg domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkMethodRelationshipOrg domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(String companyId, TargetOrgIdenInfor targetOrg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWorkMethod(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod workMethod) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<WorkMethodRelationshipOrg> getAll(String companyId, TargetOrgIdenInfor targetOrg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkMethodRelationshipOrg> getWithWorkMethod(String companyId, TargetOrgIdenInfor targetOrg,
			WorkMethod workMethod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkMethodRelationshipOrg> getWithWorkMethodList(String companyId, TargetOrgIdenInfor targetOrg,
			List<WorkMethod> workMethodList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, WorkMethod workMethod) {
		// TODO Auto-generated method stub
		return false;
	}

}
