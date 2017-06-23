package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtSpecBPTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtSpecBPTimesheetPK;

@Stateless
public class JpaSpecBPTimesheetRepository extends JpaRepository implements SpecBPTimesheetRepository {
	private final String SELECT_BY_COMPANYID_AND_BPCODE = "SELECT c FROM KbpmtSpecBPTimesheet c JOIN KbpstBonusPayTimeItem k  ON c.KbpmtSpecBPTimesheetPK.timeItemId = k.KbpmtSpecBPTimesheetPK.timeItemId WHERE c.KbpmtSpecBPTimesheetPK.companyId = :companyId AND c.bonusPaySettingCode = :bonusPaySettingCode AND k.timeItemTypeAtr = 1 AND AND K.useAtr=0  ORDER BY k.timeItemNo";

	@Override
	public List<SpecBonusPayTimesheet> getListTimesheet(String companyId, String bonusPaySettingCode) {
		return this.queryProxy().query(SELECT_BY_COMPANYID_AND_BPCODE, KbpmtSpecBPTimesheet.class)
				.setParameter("companyId", companyId).setParameter("bonusPaySettingCode", bonusPaySettingCode)
				.getList(x -> this.toSpecBPTimesheetDomain(x));
	}

	@Override
	public void addListTimesheet(String companyId, String bonusPaySettingCode,
			List<SpecBonusPayTimesheet> lstTimesheet) {
		List<KbpmtSpecBPTimesheet> lstKbpmtSpecBPTimesheet = lstTimesheet.stream()
				.map(c -> toSpecBPTimesheetEntity(companyId, bonusPaySettingCode, c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpmtSpecBPTimesheet);
	}

	@Override
	public void updateListTimesheet(String companyId, String bonusPaySettingCode,
			List<SpecBonusPayTimesheet> lstTimesheet) {
		List<KbpmtSpecBPTimesheet> lstKbpmtSpecBPTimesheet = lstTimesheet.stream()
				.map(c -> toSpecBPTimesheetEntity(companyId, bonusPaySettingCode, c)).collect(Collectors.toList());
		this.commandProxy().updateAll(lstKbpmtSpecBPTimesheet);
	}

	@Override
	public void removeListTimesheet(String companyId, String bonusPaySettingCode,
			List<SpecBonusPayTimesheet> lstTimesheet) {
		List<KbpmtSpecBPTimesheet> lstKbpmtSpecBPTimesheet = lstTimesheet.stream()
				.map(c -> toSpecBPTimesheetEntity(companyId, bonusPaySettingCode, c)).collect(Collectors.toList());
		this.commandProxy().removeAll(lstKbpmtSpecBPTimesheet);
	}

	private SpecBonusPayTimesheet toSpecBPTimesheetDomain(KbpmtSpecBPTimesheet KbpmtSpecBPTimesheet) {
		return SpecBonusPayTimesheet.createFromJavaType(KbpmtSpecBPTimesheet.kbpstSpecBPTimesheetPK.getTimeSheetNO(),
				KbpmtSpecBPTimesheet.useAtr.intValue(), KbpmtSpecBPTimesheet.timeItemId,
				KbpmtSpecBPTimesheet.startTime.longValue(), KbpmtSpecBPTimesheet.endTime.longValue(),
				KbpmtSpecBPTimesheet.roundingTimeAtr.intValue(), KbpmtSpecBPTimesheet.roundingAtr.intValue(),
				KbpmtSpecBPTimesheet.specialDateItemNO.intValue());

	}

	private KbpmtSpecBPTimesheet toSpecBPTimesheetEntity(String companyId, String bonusPaySettingCode,
			SpecBonusPayTimesheet specBonusPayTimesheet) {
		return new KbpmtSpecBPTimesheet(
				new KbpmtSpecBPTimesheetPK(companyId, specBonusPayTimesheet.getTimeSheetId(), bonusPaySettingCode),
				new BigDecimal(specBonusPayTimesheet.getUseAtr().value),
				specBonusPayTimesheet.getTimeItemId().toString(),
				new BigDecimal(specBonusPayTimesheet.getStartTime().minute()),
				new BigDecimal(specBonusPayTimesheet.getEndTime().minute()),
				new BigDecimal(specBonusPayTimesheet.getRoundingTimeAtr().value),
				new BigDecimal(specBonusPayTimesheet.getRoundingAtr().value),
				new BigDecimal(specBonusPayTimesheet.getDateCode()));
	}

}
