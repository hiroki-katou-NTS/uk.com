package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendWorkTimeRepository;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendWorkTime;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendWorkTimePK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingSendWorkTimeRepository extends JpaRepository
	   implements TimeRecordReqSettingSendWorkTimeRepository{

	@Override
	public void insert(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendWorkTime> workTimeList = reqSetting.getWorkTimeCodes().stream()
				.map(e -> toEntity(reqSetting, e.v())).collect(Collectors.toList());

		this.commandProxy().insertAll(workTimeList);
	}

	@Override
	public void update(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendWorkTime> workTimeList = reqSetting.getWorkTimeCodes().stream()
				.map(e -> toEntity(reqSetting, e.v())).collect(Collectors.toList());

		this.commandProxy().updateAll(workTimeList);
	}

	@Override
	public void delete(TimeRecordReqSetting reqSetting) {
		String contractCode = reqSetting.getContractCode().v();
		String timeRecordCode = reqSetting.getTerminalCode().v();

		List<KrcmtTrSendWorkTimePK> workTimeList = reqSetting.getWorkTimeCodes().stream()
				.map(e -> new KrcmtTrSendWorkTimePK(contractCode, timeRecordCode, e.v())).collect(Collectors.toList());

		this.commandProxy().removeAll(KrcmtTrSendWorkTime.class, workTimeList);
		this.getEntityManager().flush();
	}
	
	private KrcmtTrSendWorkTime toEntity(TimeRecordReqSetting setting, String workTimeCode) {
		return new KrcmtTrSendWorkTime(
				new KrcmtTrSendWorkTimePK(
						setting.getContractCode().v(),
						setting.getTerminalCode().v(),
						workTimeCode));
	}

}
