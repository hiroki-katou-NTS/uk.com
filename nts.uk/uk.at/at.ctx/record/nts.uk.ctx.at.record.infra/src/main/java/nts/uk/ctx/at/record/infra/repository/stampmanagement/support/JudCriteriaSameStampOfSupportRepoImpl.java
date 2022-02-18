/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.stampmanagement.support;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudCriteriaSameStampOfSupportRepo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.RangeRegardedSupportStamp;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support.KrcmtSupportStampSet;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;

/**
 * 応援の同一打刻の判断基準Repository 
 * @author laitv
 */
@Stateless
public class JudCriteriaSameStampOfSupportRepoImpl extends JpaRepository implements JudCriteriaSameStampOfSupportRepo{

	@Override
	public JudgmentCriteriaSameStampOfSupport get(String cid) {
		
		KrcmtSupportStampSet entity = queryProxy().query("SELECT o FROM KrcmtSupportStampSet o WHERE o.cid = :cid", KrcmtSupportStampSet.class)
				.setParameter("cid", cid)
				.getSingleOrNull();
		if(entity == null)
			return null;
		
		JudgmentCriteriaSameStampOfSupport rs = toDomain(entity);
		return rs;
	}

	@Override
	public void update(List<JudgmentCriteriaSameStampOfSupport> domains) {
		domains.forEach(dm -> {
			KrcmtSupportStampSet entiti = this.queryProxy().find(dm.getCid().toString(),KrcmtSupportStampSet.class).orElse(null);
			if(entiti != null){
				entiti.sameStampRanceInMinutes = dm.getSameStampRanceInMinutes().v();
				commandProxy().update(entiti);
			}
		});
	}

	@Override
	public void insert(List<JudgmentCriteriaSameStampOfSupport> domains) {
		domains.forEach(dm -> {
			KrcmtSupportStampSet entiti = this.queryProxy().find(dm.getCid().toString(),KrcmtSupportStampSet.class).orElse(null);
			if(entiti == null){
				KrcmtSupportStampSet e = KrcmtSupportStampSet.convert(dm);
				commandProxy().insert(e);
			}
		});
	}

	@Override
	public void delete(List<JudgmentCriteriaSameStampOfSupport> domains) {
		domains.stream().map(c -> KrcmtSupportStampSet.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}
	
	private JudgmentCriteriaSameStampOfSupport toDomain(KrcmtSupportStampSet entity) {
		return new JudgmentCriteriaSameStampOfSupport(entity.cid, new RangeRegardedSupportStamp(entity.sameStampRanceInMinutes));
	}

}
