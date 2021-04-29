package nts.uk.ctx.at.record.infra.repository.workrecord.identificationstatus;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControl;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControlPk;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Stateless
public class JpaIdentityProcessUseSetRepository extends JpaRepository implements IdentityProcessUseSetRepository {

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<IdentityProcessUseSet> findByKey(String companyId) {
		return this.queryProxy().find(new KrcmtDaiFuncControlPk(companyId), KrcmtDaiFuncControl.class)
				.map(x -> toDomain(x));
	}

	private IdentityProcessUseSet toDomain(KrcmtDaiFuncControl entity) {
		return new IdentityProcessUseSet(new CompanyId(entity.daiFuncControlPk.cid),
				entity.daySelfChk == 1 ? true : false, entity.monSelfChk == 1 ? true : false,
				entity.daySelfChkError == null ? Optional.empty()
						: Optional.of(EnumAdaptor.valueOf(entity.daySelfChkError, SelfConfirmError.class)));
	}
}
