/**
 * 5:11:24 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecordPK;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaErrorAlarmWorkRecordRepository extends JpaRepository implements ErrorAlarmWorkRecordRepository {

	private final String FIND_BY_COMPANY = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId ";

	@Override
	public List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId) {
		List<KwrmtErAlWorkRecord> lstData = this.queryProxy().query(FIND_BY_COMPANY, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId).getList();
		return lstData.stream().map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public void updateErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain) {
		Optional<KwrmtErAlWorkRecord> targetEntity = this.queryProxy().find(
				new KwrmtErAlWorkRecordPK(domain.getCompanyId(), domain.getCode().v()), KwrmtErAlWorkRecord.class);
		if (targetEntity.isPresent()) {
			targetEntity.get().boldAtr = domain.getMessage().getBoldAtr() ? new BigDecimal(1) : new BigDecimal(0);
			targetEntity.get().cancelableAtr = domain.getCancelableAtr() ? new BigDecimal(1) : new BigDecimal(0);
			targetEntity.get().errorAlarmName = domain.getName().v();
			targetEntity.get().errorDisplayItem = domain.getErrorDisplayItem();
			targetEntity.get().fixedAtr = domain.getFixedAtr() ? new BigDecimal(1) : new BigDecimal(2);
			targetEntity.get().messageColor = domain.getMessage().getMessageColor().v();
			targetEntity.get().messageDisplay = domain.getMessage().getDisplayMessage().v();
			targetEntity.get().typeAtr = new BigDecimal(domain.getTypeAtr().value);
			targetEntity.get().useAtr = domain.getUseAtr() ? new BigDecimal(1) : new BigDecimal(2);
			this.commandProxy().update(targetEntity.get());
		}
	}

	private ErrorAlarmWorkRecord toDomain(KwrmtErAlWorkRecord entity) {
		return ErrorAlarmWorkRecord.createFromJavaType(entity.kwrmtErAlWorkRecordPK.companyId,
				entity.kwrmtErAlWorkRecordPK.errorAlarmCode, entity.errorAlarmName,
				entity.fixedAtr.intValue() == 1 ? true : false, entity.useAtr.intValue() == 1 ? true : false,
				entity.typeAtr.intValue(), entity.messageDisplay, entity.boldAtr.intValue() == 1 ? true : false,
				entity.messageColor, entity.cancelableAtr.intValue() == 1 ? true : false, entity.errorDisplayItem);
	}
}
