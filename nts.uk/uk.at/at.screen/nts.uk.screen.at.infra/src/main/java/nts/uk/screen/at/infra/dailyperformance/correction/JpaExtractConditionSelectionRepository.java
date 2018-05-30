package nts.uk.screen.at.infra.dailyperformance.correction;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.screen.at.app.dailyperformance.correction.ExtractConditionSelectionRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorAlarmWorkRecordDto;

@Stateless
public class JpaExtractConditionSelectionRepository extends JpaRepository implements ExtractConditionSelectionRepository {

	private final String FIND_ERROR_ALARM_BY_COMPANY =
			"SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId AND a.useAtr = :useAtr";

	@Override
	public List<ErrorAlarmWorkRecordDto> getErrorAlarmWorkRecordList(String companyId) {
		List<KwrmtErAlWorkRecord> lstData = this.queryProxy().query(FIND_ERROR_ALARM_BY_COMPANY, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId).setParameter("useAtr", 1).getList();
		return lstData.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
	}

	private static ErrorAlarmWorkRecordDto toDto(KwrmtErAlWorkRecord entity) {
		return new ErrorAlarmWorkRecordDto(entity.kwrmtErAlWorkRecordPK.companyId,
										entity.kwrmtErAlWorkRecordPK.errorAlarmCode,
										entity.errorAlarmName, entity.fixedAtr,
										entity.useAtr, entity.typeAtr,
										entity.eralCheckId, entity.boldAtr,
										entity.messageColor, entity.cancelableAtr,
										entity.errorDisplayItem);
	}
}
