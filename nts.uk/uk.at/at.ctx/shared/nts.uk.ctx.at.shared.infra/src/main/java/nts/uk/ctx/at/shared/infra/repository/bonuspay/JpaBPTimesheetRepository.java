package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBPTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBPTimesheetPK;

@Stateless
public class JpaBPTimesheetRepository extends JpaRepository implements BPTimesheetRepository {
	private static final String SELECT_BY_COMPANYID_AND_BPCODE = "SELECT c FROM KbpmtBPTimesheet c WHERE c.kbpmtBPTimesheetPK.companyId = :companyId AND c.kbpmtBPTimesheetPK.bonusPaySettingCode = :bonusPaySettingCode  ORDER BY c.kbpmtBPTimesheetPK.timeSheetNO";

	@Override
	public List<BonusPayTimesheet> getListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		return this.queryProxy().query(SELECT_BY_COMPANYID_AND_BPCODE, KbpmtBPTimesheet.class)
				.setParameter("companyId", companyId).setParameter("bonusPaySettingCode", bonusPaySettingCode.v(),BonusPaySettingCode.class)
				.getList(x -> this.toBonusPayTimesheetDomain(x));
	}

	@Override
	public void addListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet) {
		List<KbpmtBPTimesheet> lstKbpmtBPTimesheet = lstTimesheet.stream()
				.map(c -> toBonusPayTimesheetEntity(companyId, bonusPaySettingCode.v(), c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpmtBPTimesheet);
	}

	@Override
	public void updateListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode,
			List<BonusPayTimesheet> lstTimesheet) {
		lstTimesheet.forEach(c -> {
			Optional<KbpmtBPTimesheet> kbpmtBPTimesheetOptional = this.queryProxy().find(
					new KbpmtBPTimesheetPK(companyId, c.getTimeSheetId(), bonusPaySettingCode.v()),
					KbpmtBPTimesheet.class);
			if (kbpmtBPTimesheetOptional.isPresent()) {
				KbpmtBPTimesheet kbpmtBPTimesheet = kbpmtBPTimesheetOptional.get();
				kbpmtBPTimesheet.endTime = new BigDecimal(c.getEndTime().v());
				kbpmtBPTimesheet.roundingAtr = new BigDecimal(c.getRoundingAtr().value);
				kbpmtBPTimesheet.roundingTimeAtr = new BigDecimal(c.getRoundingTimeAtr().value);
				kbpmtBPTimesheet.startTime = new BigDecimal(c.getStartTime().v());
				kbpmtBPTimesheet.timeItemId = c.getTimeItemId();
				kbpmtBPTimesheet.useAtr = new BigDecimal(c.getUseAtr().value);
				this.commandProxy().update(kbpmtBPTimesheet);
			} else {
				this.commandProxy().insert(toBonusPayTimesheetEntity(companyId, bonusPaySettingCode.v(), c));
			}

		});
	}

	@Override
	public void removeListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode,
			List<BonusPayTimesheet> lstTimesheet) {
		lstTimesheet.forEach(c->{
			Optional<KbpmtBPTimesheet> kbpmtBPTimesheet = this.queryProxy().find(new KbpmtBPTimesheetPK(companyId, c.getTimeSheetId(), bonusPaySettingCode.v()), KbpmtBPTimesheet.class);
			if(kbpmtBPTimesheet.isPresent()){
				this.commandProxy().remove(kbpmtBPTimesheet.get());
			}
		});
	}

	private KbpmtBPTimesheet toBonusPayTimesheetEntity(String companyId, String bonusPaySettingCode,
			BonusPayTimesheet bonusPayTimesheet) {
		return new KbpmtBPTimesheet(new KbpmtBPTimesheetPK(companyId, bonusPayTimesheet.getTimeSheetId(),bonusPaySettingCode),
				new BigDecimal(bonusPayTimesheet.getUseAtr().value),
				bonusPayTimesheet.getTimeItemId(), new BigDecimal(bonusPayTimesheet.getStartTime().v()),
				new BigDecimal(bonusPayTimesheet.getEndTime().v()),
				new BigDecimal(bonusPayTimesheet.getRoundingTimeAtr().value),
				new BigDecimal(bonusPayTimesheet.getRoundingAtr().value));

	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(KbpmtBPTimesheet kbpmtBPTimesheet) {
		return BonusPayTimesheet.createFromJavaType(kbpmtBPTimesheet.kbpmtBPTimesheetPK.timeSheetNO,
				kbpmtBPTimesheet.useAtr.intValue(), kbpmtBPTimesheet.timeItemId, kbpmtBPTimesheet.startTime.intValue(),
				kbpmtBPTimesheet.endTime.intValue(), kbpmtBPTimesheet.roundingTimeAtr.intValue(),
				kbpmtBPTimesheet.roundingAtr.intValue());
	}

}
