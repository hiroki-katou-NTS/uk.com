/**
 * 5:11:24 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecordPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaErrorAlarmWorkRecordRepository extends JpaRepository implements ErrorAlarmWorkRecordRepository {

	private final String FIND_BY_COMPANY = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId ";
	private final String FIND_BY_ERROR_ALARM_CHECK_ID = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId AND a.eralCheckId = :eralCheckId ";

	@Override
	public Optional<ErrorAlarmWorkRecord> findByCode(String code) {
		Optional<KwrmtErAlWorkRecord> entity = this.queryProxy()
				.find(new KwrmtErAlWorkRecordPK(AppContexts.user().companyId(), code), KwrmtErAlWorkRecord.class);
		return Optional.ofNullable(entity.isPresent() ? KwrmtErAlWorkRecord.toDomain(entity.get()) : null);
	}

	@Override
	public List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId) {
		List<KwrmtErAlWorkRecord> lstData = this.queryProxy().query(FIND_BY_COMPANY, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId).getList();
		return lstData.stream().map(entity -> KwrmtErAlWorkRecord.toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public void addErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain) {
		this.commandProxy().insert(KwrmtErAlWorkRecord.fromDomain(domain));
	}

	@Override
	public void updateErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain) {
		KwrmtErAlWorkRecord targetEntity = this.queryProxy()
				.find(new KwrmtErAlWorkRecordPK(domain.getCompanyId(), domain.getCode().v()), KwrmtErAlWorkRecord.class)
				.get();
		domain.setCheckId(targetEntity.eralCheckId);
		if (!domain.getFixedAtr()) {
			domain.setGroupId1(targetEntity.krcmtErAlCondition.atdItemConditionGroup1);
			domain.setGroupId2(targetEntity.krcmtErAlCondition.atdItemConditionGroup2);
		}
		KwrmtErAlWorkRecord domainAfterConvert = KwrmtErAlWorkRecord.fromDomain(domain);
		targetEntity.eralCheckId = domainAfterConvert.eralCheckId;
		targetEntity.boldAtr = domainAfterConvert.boldAtr;
		targetEntity.cancelableAtr = domainAfterConvert.cancelableAtr;
		targetEntity.cancelRoleId = domainAfterConvert.cancelRoleId;
		targetEntity.errorAlarmName = domainAfterConvert.errorAlarmName;
		targetEntity.errorDisplayItem = domainAfterConvert.errorDisplayItem;
		targetEntity.fixedAtr = domainAfterConvert.fixedAtr;
		targetEntity.krcmtErAlCondition = domainAfterConvert.krcmtErAlCondition;
		targetEntity.krcstErAlApplication = domainAfterConvert.krcstErAlApplication;
		targetEntity.kwrmtErAlWorkRecordPK = domainAfterConvert.kwrmtErAlWorkRecordPK;
		targetEntity.messageColor = domainAfterConvert.messageColor;
		targetEntity.typeAtr = domainAfterConvert.typeAtr;
		targetEntity.useAtr = domainAfterConvert.useAtr;
		this.commandProxy().update(targetEntity);
	}

	@Override
	public void removeErrorAlarmWorkRecord(String code) {
		this.commandProxy().remove(KwrmtErAlWorkRecord.class,
				new KwrmtErAlWorkRecordPK(AppContexts.user().companyId(), code));
	}

    @Override
    public Optional<ErrorAlarmWorkRecord> findByErrorAlamCheckId(String eralCheckId) {
        Optional<KwrmtErAlWorkRecord> entity = this.queryProxy().query(FIND_BY_ERROR_ALARM_CHECK_ID, KwrmtErAlWorkRecord.class)
                .setParameter("companyId", AppContexts.user().companyId())
                .setParameter("eralCheckId", eralCheckId).getSingle();
                return Optional.ofNullable(entity.isPresent() ? KwrmtErAlWorkRecord.toDomain(entity.get()) : null);
    }

}
