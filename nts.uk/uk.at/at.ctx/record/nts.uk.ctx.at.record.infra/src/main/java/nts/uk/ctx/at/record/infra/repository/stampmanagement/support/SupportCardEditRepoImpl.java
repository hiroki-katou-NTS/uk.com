package nts.uk.ctx.at.record.infra.repository.stampmanagement.support;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEdit;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEditRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support.KrcmtSupportCardEdit;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support.KrcmtSupportCardEditPk;

/**
 * @author nws_namnv2
 *
 */
@Stateless
public class SupportCardEditRepoImpl extends JpaRepository implements SupportCardEditRepository {

	@Override
	public Optional<SupportCardEdit> get(String cid) {
		return this.queryProxy()
				.find(new KrcmtSupportCardEditPk(cid), KrcmtSupportCardEdit.class)
				.map(entity -> new SupportCardEdit(EnumAdaptor
						.valueOf(entity.editMethod, StampCardEditMethod.class)));
	}

	@Override
	public void insert(SupportCardEdit domain) {
		this.commandProxy().insert(KrcmtSupportCardEdit.toEntity(domain));
	}

	@Override
	public void update(SupportCardEdit domain) {
		this.commandProxy().update(KrcmtSupportCardEdit.toEntity(domain));
	}

	@Override
	public void delete(SupportCardEdit domain) {
		this.commandProxy().remove(KrcmtSupportCardEdit.toEntity(domain));
	}

}
