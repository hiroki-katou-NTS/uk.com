package nts.uk.ctx.at.record.infra.repository.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodinfor.ErrorMess;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodInfor;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcmtAggrPeriodInforPK;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaAggrPeriodInforRepository extends JpaRepository implements AggrPeriodInforRepository {

	@Override
	public List<AggrPeriodInfor> findAll(String anyPeriodAggrLogId) {
		String sql = "SELECT a FROM KrcmtAggrPeriodInfor a WHERE a.krcmtAggrPeriodInforPK.periodArrgLogId = :periodArrgLogId";
		return this.queryProxy().query(sql, KrcmtAggrPeriodInfor.class)
				.setParameter("periodArrgLogId", anyPeriodAggrLogId).getList(c -> convertEntityToDomain(c));
	}

	private AggrPeriodInfor convertEntityToDomain(KrcmtAggrPeriodInfor entity) {
		AggrPeriodInfor domain = new AggrPeriodInfor(entity.krcmtAggrPeriodInforPK.memberId,
				entity.krcmtAggrPeriodInforPK.periodArrgLogId, entity.krcmtAggrPeriodInforPK.resourceId,
				entity.processDay, new ErrorMess(entity.errorMess));
		return domain;
	}
	
	@Override
	public void addPeriodInfor(AggrPeriodInfor periodInfor) {
		this.commandProxy().insert(convertToDbTypeApi(periodInfor));

	}
	
	private KrcmtAggrPeriodInfor convertToDbTypeApi(AggrPeriodInfor periodInfor) {
		KrcmtAggrPeriodInfor entity = new KrcmtAggrPeriodInfor();
		entity.krcmtAggrPeriodInforPK = new KrcmtAggrPeriodInforPK(periodInfor.getMemberId(), periodInfor.getPeriodArrgLogId(), periodInfor.getResourceId());
		entity.processDay = periodInfor.getProcessDay();
		entity.errorMess = periodInfor.getErrorMess().v();
		return entity;
	}

}
