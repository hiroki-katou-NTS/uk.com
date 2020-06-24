package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtSrampPortal;

/**
 * 
 * @author chungnt	
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPortalStampSettingsRepository extends JpaRepository implements PortalStampSettingsRepository {

	/**
	 *  [1]  insert(ポータルの打刻設定)
	 */
	@Override
	public void insert(PortalStampSettings domain) {
		this.commandProxy().insert(KrcmtSrampPortal.toEntity(domain));
	}
	
	/**
	 * 	[2]  update(ポータルの打刻設定)
	 */
	@Override
	public void update(PortalStampSettings domain) {
		Optional<KrcmtSrampPortal> entityOpt = this.queryProxy().find(domain.getCid(), KrcmtSrampPortal.class);
		if (entityOpt.isPresent()) {
			this.commandProxy().update(KrcmtSrampPortal.toEntity(domain));
		}
	}

	/**
	 * 	[3]  取得する
	 */
	@Override
	public Optional<PortalStampSettings> get(String comppanyID) {
		Optional<KrcmtSrampPortal> entity = this.queryProxy().find(comppanyID, KrcmtSrampPortal.class);
		if (entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
	}
}
