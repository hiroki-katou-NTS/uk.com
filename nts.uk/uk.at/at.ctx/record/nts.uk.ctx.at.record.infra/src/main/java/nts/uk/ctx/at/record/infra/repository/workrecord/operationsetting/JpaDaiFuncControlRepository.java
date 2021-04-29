package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiFuncControlRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControl;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControlPk;

@Stateless
public class JpaDaiFuncControlRepository extends JpaRepository implements DaiFuncControlRepository{

	@Override
	public void add(DaiPerformanceFun daiPerformanceFun,
			IdentityProcess identityProcess, ApprovalProcess approvalProcess) {
		this.commandProxy().insert(KrcmtDaiFuncControl.toEntity(daiPerformanceFun, identityProcess, approvalProcess));
	}

	@Override
	public void update(DaiPerformanceFun daiPerformanceFun,
			IdentityProcess identityProcess, ApprovalProcess approvalProcess) {
		KrcmtDaiFuncControl newEntity = KrcmtDaiFuncControl.toEntity(daiPerformanceFun, identityProcess, approvalProcess);
		KrcmtDaiFuncControl updateEntity = this.queryProxy().find(newEntity.daiFuncControlPk, KrcmtDaiFuncControl.class).orElse(null);
        if (null == updateEntity) {
        	this.add(daiPerformanceFun, identityProcess, approvalProcess);
            return;
        }
        updateEntity.comment = newEntity.comment;
        updateEntity.disp36Atr = newEntity.disp36Atr;
        updateEntity.flexDispAtr = newEntity.flexDispAtr;
        updateEntity.checkErrRefDisp = newEntity.checkErrRefDisp;
        updateEntity.daySelfChk = newEntity.daySelfChk;
        updateEntity.monSelfChk = newEntity.monSelfChk;
        updateEntity.daySelfChkError = newEntity.daySelfChkError;
        updateEntity.dayBossChk = newEntity.dayBossChk;
        updateEntity.monBossChk = newEntity.monBossChk;
        updateEntity.dayBossChkError = newEntity.dayBossChkError;
        this.commandProxy().update(updateEntity);
	}

	@Override
	public void remove(String cid) {
		this.commandProxy().remove(KrcmtDaiFuncControl.class, new KrcmtDaiFuncControlPk(cid)); 
	}
}
