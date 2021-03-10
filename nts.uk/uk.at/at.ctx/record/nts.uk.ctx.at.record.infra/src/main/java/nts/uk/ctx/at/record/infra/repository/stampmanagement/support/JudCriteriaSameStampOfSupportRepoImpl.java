/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.stampmanagement.support;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudCriteriaSameStampOfSupportRepo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support.KrcmtSupportStampSet;

/**
 * 応援の同一打刻の判断基準Repository 
 * @author laitv
 */
@Stateless
public class JudCriteriaSameStampOfSupportRepoImpl extends JpaRepository implements JudCriteriaSameStampOfSupportRepo{

	@Override
	public JudgmentCriteriaSameStampOfSupport find(String cid) {
		
		KrcmtSupportStampSet entity = queryProxy().query("SELECT o FROM KrcmtSupportStampSet o WHERE o.cid = :cid", KrcmtSupportStampSet.class)
				.getSingleOrNull();
		if(entity == null)
			return null;
		
		JudgmentCriteriaSameStampOfSupport rs = toDomain(entity);
		return rs;
	}

	@Override
	public void update(List<JudgmentCriteriaSameStampOfSupport> domains) {
		domains.stream().map(c -> KrcmtSupportStampSet.convert(c)).forEach(e -> {
			commandProxy().update(e);
		});
	}

	@Override
	public void insert(List<JudgmentCriteriaSameStampOfSupport> domains) {
		domains.stream().map(c -> KrcmtSupportStampSet.convert(c)).forEach(e -> {
			commandProxy().insert(e);
		});
	}

	@Override
	public void delete(List<JudgmentCriteriaSameStampOfSupport> domains) {
		domains.stream().map(c -> KrcmtSupportStampSet.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}
	
	private JudgmentCriteriaSameStampOfSupport toDomain(KrcmtSupportStampSet entity) {
		JudgmentCriteriaSameStampOfSupport dm = JudgmentCriteriaSameStampOfSupport.create(entity.cid, entity.sameStampRanceInMinutes, entity.supportMaxFrame);
		return dm;
	}

}
