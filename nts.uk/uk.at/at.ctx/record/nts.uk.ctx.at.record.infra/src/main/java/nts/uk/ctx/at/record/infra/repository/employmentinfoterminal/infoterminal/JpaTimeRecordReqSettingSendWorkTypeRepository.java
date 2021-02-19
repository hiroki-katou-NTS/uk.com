package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendWorkTypeRepository;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendWorkType;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendWorkTypePK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingSendWorkTypeRepository extends JpaRepository
	   implements TimeRecordReqSettingSendWorkTypeRepository{

	@Override
	public void insert(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendWorkType> workTypeList = reqSetting.getWorkTypeCodes().stream()
				.map(e -> toEntity(reqSetting, e.v())).collect(Collectors.toList());

		this.commandProxy().insertAll(workTypeList);
	}

	@Override
	public void update(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendWorkType> workTypeList = reqSetting.getWorkTypeCodes().stream()
				.map(e -> toEntity(reqSetting, e.v())).collect(Collectors.toList());

		this.commandProxy().updateAll(workTypeList);
	}

	@Override
	public void delete(TimeRecordReqSetting reqSetting) {
		String contractCode = reqSetting.getContractCode().v();
		String timeRecordCode = reqSetting.getTerminalCode().v();

		List<KrcmtTrSendWorkTypePK> workTypeList = reqSetting.getWorkTypeCodes().stream()
				.map(e -> new KrcmtTrSendWorkTypePK(contractCode, timeRecordCode, e.v())).collect(Collectors.toList());

		this.commandProxy().removeAll(KrcmtTrSendWorkType.class, workTypeList);
		this.getEntityManager().flush();
	}
	
	private KrcmtTrSendWorkType toEntity(TimeRecordReqSetting setting, String workTypeCode) {
		return new KrcmtTrSendWorkType(
				new KrcmtTrSendWorkTypePK(
						setting.getContractCode().v(),
						setting.getTerminalCode().v(),
						workTypeCode));
	}

}
