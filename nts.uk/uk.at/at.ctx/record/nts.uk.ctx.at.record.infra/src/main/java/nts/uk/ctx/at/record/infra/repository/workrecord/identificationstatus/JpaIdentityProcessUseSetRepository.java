package nts.uk.ctx.at.record.infra.repository.workrecord.identificationstatus;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.KrcmtIdentityProceSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.KrcmtIdentityProceSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Stateless
public class JpaIdentityProcessUseSetRepository extends JpaRepository implements IdentityProcessUseSetRepository {

	@Override
	public Optional<IdentityProcessUseSet> findByKey(String companyId) {
		return this.queryProxy().find(new KrcmtIdentityProceSetPK(companyId), KrcmtIdentityProceSet.class)
				.map(x -> toDomain(x));
	}

	@Override
	public void insertIdentity(IdentityProcessUseSet identity) {
		this.commandProxy().insert(toEntity(identity));
	}

	@Override
	public void updateIdentity(IdentityProcessUseSet identity) {
		this.commandProxy().update(toEntity(identity));
	}

	private IdentityProcessUseSet toDomain(KrcmtIdentityProceSet entity) {
		return new IdentityProcessUseSet(new CompanyId(entity.krcmtIdentityProceSetPK.cid),
				entity.useConfirmByYourself == 1 ? true : false, entity.useIdentityOfMonth == 1 ? true : false,
				entity.yourSelfConfirmError == null ? Optional.empty()
						: Optional.of(EnumAdaptor.valueOf(entity.yourSelfConfirmError, SelfConfirmError.class)));
	}

	private KrcmtIdentityProceSet toEntity(IdentityProcessUseSet domain) {
		return new KrcmtIdentityProceSet(new KrcmtIdentityProceSetPK(domain.getCompanyId().v()),
				domain.isUseConfirmByYourself() ? 1 : 0, domain.isUseIdentityOfMonth() ? 1 : 0,
				domain.getYourSelfConfirmError().isPresent() ? domain.getYourSelfConfirmError().get().value : null);
	}
}
