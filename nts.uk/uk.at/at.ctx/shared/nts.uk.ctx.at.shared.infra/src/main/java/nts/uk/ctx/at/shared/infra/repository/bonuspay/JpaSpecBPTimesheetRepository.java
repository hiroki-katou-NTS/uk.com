package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtSpecBPTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtSpecBPTimesheetPK;

@Stateless
public class JpaSpecBPTimesheetRepository extends JpaRepository implements SpecBPTimesheetRepository {
	private static final String SELECT_BY_COMPANYID_AND_BPCODE = "SELECT c FROM KbpmtSpecBPTimesheet c WHERE c.kbpstSpecBPTimesheetPK.companyId = :companyId AND c.kbpstSpecBPTimesheetPK.bonusPaySettingCode = :bonusPaySettingCode ORDER BY c.kbpstSpecBPTimesheetPK.timeSheetNO";

	@Override
	public List<SpecBonusPayTimesheet> getListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		return this.queryProxy().query(SELECT_BY_COMPANYID_AND_BPCODE, KbpmtSpecBPTimesheet.class)
				.setParameter("companyId", companyId).setParameter("bonusPaySettingCode", bonusPaySettingCode, BonusPaySettingCode.class)
				.getList(x -> this.toSpecBPTimesheetDomain(x));
	}

	@Override
	public void addListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode,
			List<SpecBonusPayTimesheet> lstTimesheet) {
		List<KbpmtSpecBPTimesheet> lstKbpmtSpecBPTimesheet = lstTimesheet.stream()
				.map(c -> toSpecBPTimesheetEntity(companyId, bonusPaySettingCode.v(), c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpmtSpecBPTimesheet);
	}

	@Override
	public void updateListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode,
			List<SpecBonusPayTimesheet> lstTimesheet) {
		lstTimesheet.forEach(c -> {
			Optional<KbpmtSpecBPTimesheet> kbpmtSpecBPTimesheetOptinal = this.queryProxy().find(
					new KbpmtSpecBPTimesheetPK(companyId, c.getTimeSheetId(), bonusPaySettingCode.v()),
					KbpmtSpecBPTimesheet.class);
			if (kbpmtSpecBPTimesheetOptinal.isPresent()) {
				KbpmtSpecBPTimesheet kbpmtSpecBPTimesheet = kbpmtSpecBPTimesheetOptinal.get();
				kbpmtSpecBPTimesheet.endTime = new BigDecimal(c.getEndTime().v());
				kbpmtSpecBPTimesheet.roundingAtr = new BigDecimal(c.getRoundingAtr().value);
				kbpmtSpecBPTimesheet.roundingTimeAtr = new BigDecimal(c.getRoundingTimeAtr().value);
				kbpmtSpecBPTimesheet.specialDateItemNO = new BigDecimal(c.getDateCode());
				kbpmtSpecBPTimesheet.startTime = new BigDecimal(c.getStartTime().v());
				kbpmtSpecBPTimesheet.timeItemId = c.getTimeItemId();
				kbpmtSpecBPTimesheet.useAtr = new BigDecimal(c.getUseAtr().value);
				this.commandProxy().update(kbpmtSpecBPTimesheet);
			} else {
				this.commandProxy().insert(toSpecBPTimesheetEntity(companyId, bonusPaySettingCode.v(), c));
			}
		});
	}

	@Override
	public void removeListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode,
			List<SpecBonusPayTimesheet> lstTimesheet) {
		lstTimesheet.forEach(c->{
			Optional<KbpmtSpecBPTimesheet> kbpmtSpecBPTimesheet = this.queryProxy().find(new KbpmtSpecBPTimesheetPK(companyId,c.getTimeSheetId(),bonusPaySettingCode.v()), KbpmtSpecBPTimesheet.class);
			if (kbpmtSpecBPTimesheet.isPresent()) {
				this.commandProxy().remove(kbpmtSpecBPTimesheet.get());
			}
		});
	}

	private SpecBonusPayTimesheet toSpecBPTimesheetDomain(KbpmtSpecBPTimesheet KbpmtSpecBPTimesheet) {
		return SpecBonusPayTimesheet.createFromJavaType(KbpmtSpecBPTimesheet.kbpstSpecBPTimesheetPK.getTimeSheetNO(),
				KbpmtSpecBPTimesheet.useAtr.intValue(), KbpmtSpecBPTimesheet.timeItemId,
				KbpmtSpecBPTimesheet.startTime.intValue(), KbpmtSpecBPTimesheet.endTime.intValue(),
				KbpmtSpecBPTimesheet.roundingTimeAtr.intValue(), KbpmtSpecBPTimesheet.roundingAtr.intValue(),
				KbpmtSpecBPTimesheet.specialDateItemNO.intValue());

	}

	private KbpmtSpecBPTimesheet toSpecBPTimesheetEntity(String companyId, String bonusPaySettingCode,
			SpecBonusPayTimesheet specBonusPayTimesheet) {
		return new KbpmtSpecBPTimesheet(
				new KbpmtSpecBPTimesheetPK(companyId, specBonusPayTimesheet.getTimeSheetId(), bonusPaySettingCode),
				new BigDecimal(specBonusPayTimesheet.getUseAtr().value), specBonusPayTimesheet.getTimeItemId(),
				new BigDecimal(specBonusPayTimesheet.getStartTime().v()),
				new BigDecimal(specBonusPayTimesheet.getEndTime().v()),
				new BigDecimal(specBonusPayTimesheet.getRoundingTimeAtr().value),
				new BigDecimal(specBonusPayTimesheet.getRoundingAtr().value),
				new BigDecimal(specBonusPayTimesheet.getDateCode()));
	}

}
