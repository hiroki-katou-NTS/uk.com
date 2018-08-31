package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.otkcustomize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.repo.ContinuousHolCheckSetRepo;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize.KrcctOtkVacationCk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize.KrcctOtkWtNonTarget;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize.KrcctOtkWtTarget;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class ContinuousHolCheckSetRepoImpl extends JpaRepository implements ContinuousHolCheckSetRepo {
	
	public List<ContinuousHolCheckSet> find(List<String> companyIds){
		if(companyIds.isEmpty()){
			return new ArrayList<>();
		}
		return this.queryProxy().query("SELECT v FROM KrcctOtkVacationCk v WHERE v.cid IN :cid", KrcctOtkVacationCk.class)
				.setParameter("cid", companyIds).getList(c -> c.toDomain());
	}
	
	public Optional<ContinuousHolCheckSet> find(String companyId){
		return find(Arrays.asList(companyId)).stream()
					.filter(c -> c.getCompanyId().equals(companyId)).findFirst();
	}
	
	public void insert(ContinuousHolCheckSet setting){
		this.commandProxy().insert(KrcctOtkVacationCk.fromDomain(setting));
		this.commandProxy().insertAll(toNonTarget(setting.getIgnoreWorkType(), setting.getCompanyId()));
		this.commandProxy().insertAll(toTarget(setting.getTargetWorkType(), setting.getCompanyId()));
	}
	
	public void update(ContinuousHolCheckSet setting){
		KrcctOtkVacationCk entity = findEntity(setting.getCompanyId());
		if(entity != null){
			if(!entity.krcctOtkWtNonTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtNonTarget);
			}
			if(!entity.krcctOtkWtTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtTarget);
			}
			this.getEntityManager().flush();
			entity.continuousDays = setting.getMaxContinuousDays().v();
			entity.messageDisplay = setting.getDisplayMessege().v();
			entity.useAtr = setting.isUseAtr() ? 1 : 0;
			this.commandProxy().insertAll(toNonTarget(setting.getIgnoreWorkType(), setting.getCompanyId()));
			this.commandProxy().insertAll(toTarget(setting.getTargetWorkType(), setting.getCompanyId()));
			this.getEntityManager().flush();
		}
	}
	
	public void remove(String cid){
		KrcctOtkVacationCk entity = findEntity(cid);
		if(entity != null){
			if(!entity.krcctOtkWtNonTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtNonTarget);
			}
			if(!entity.krcctOtkWtTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtTarget);
			}
			this.commandProxy().remove(entity);
		}
	}

	private KrcctOtkVacationCk findEntity(String cid) {
		KrcctOtkVacationCk entity = this.queryProxy().query("SELECT v FROM KrcctOtkVacationCk v WHERE v.cid = :cid", KrcctOtkVacationCk.class)
											.setParameter("cid", cid).getSingleOrNull();
		return entity;
	}
	
	private List<KrcctOtkWtNonTarget> toNonTarget(List<WorkTypeCode> typeCode, String cid){
		return typeCode.stream().map(t -> new KrcctOtkWtNonTarget(cid, t.v())).collect(Collectors.toList());
	}
	
	private List<KrcctOtkWtTarget> toTarget(List<WorkTypeCode> typeCode, String cid){
		return typeCode.stream().map(t -> new KrcctOtkWtTarget(cid, t.v())).collect(Collectors.toList());
	}
}