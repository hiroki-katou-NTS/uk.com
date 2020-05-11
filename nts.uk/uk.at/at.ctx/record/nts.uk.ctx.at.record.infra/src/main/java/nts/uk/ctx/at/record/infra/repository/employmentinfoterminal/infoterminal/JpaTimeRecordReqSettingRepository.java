package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingRepository implements TimeRecordReqSettingRepository {

	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
		// TODO Auto-generated method stub
		return Optional.of(new TimeRecordReqSetting(new ReqSettingBuilder(empInfoTerCode, contractCode, new CompanyId("1"), "1", null, null, null)));
	}

}
