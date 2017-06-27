package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBPTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBPTimesheetPK;

@Stateless
public class JpaBPTimesheetRepository extends JpaRepository implements BPTimesheetRepository {
	private final String SELECT_BY_COMPANYID_AND_BPCODE = "SELECT c FROM KbpmtBPTimesheet JOIN  k KbpstBonusPayTimeItem  ON c.kbpmtBPTimesheetPK.timeItemId = k.kbpstBonusPayTimeItemPK.timeItemId c WHERE c.kbpmtBPTimesheetPK.companyId = :companyId AND c.bonusPaySettingCode = :bonusPaySettingCode AND k.timeItemTypeAtr = 0 AND K.useAtr=0  ORDER BY k.timeItemNo";

	@Override
	public List<BonusPayTimesheet> getListTimesheet(String companyId, String bonusPaySettingCode) {
		return this.queryProxy().query(SELECT_BY_COMPANYID_AND_BPCODE, KbpmtBPTimesheet.class)
				.setParameter("companyId", companyId).setParameter("bonusPaySettingCode", bonusPaySettingCode)
				.getList(x -> this.toBonusPayTimesheetDomain(x));
	}

	@Override
	public void addListTimesheet(String companyId, String bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet) {
		List<KbpmtBPTimesheet> lstKbpmtBPTimesheet = lstTimesheet.stream()
				.map(c -> toBonusPayTimesheetEntity(companyId, bonusPaySettingCode, c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpmtBPTimesheet);
	}

	@Override
	public void updateListTimesheet(String companyId, String bonusPaySettingCode,
			List<BonusPayTimesheet> lstTimesheet) {
		List<KbpmtBPTimesheet> lstKbpmtBPTimesheet = lstTimesheet.stream()
				.map(c -> toBonusPayTimesheetEntity(companyId, bonusPaySettingCode, c)).collect(Collectors.toList());
		this.commandProxy().updateAll(lstKbpmtBPTimesheet);

	}

	@Override
	public void removeListTimesheet(String companyId, String bonusPaySettingCode,
			List<BonusPayTimesheet> lstTimesheet) {
		List<KbpmtBPTimesheet> lstKbpmtBPTimesheet = lstTimesheet.stream()
				.map(c -> toBonusPayTimesheetEntity(companyId, bonusPaySettingCode, c)).collect(Collectors.toList());
		this.commandProxy().removeAll(lstKbpmtBPTimesheet);
	}

	private KbpmtBPTimesheet toBonusPayTimesheetEntity(String companyId, String bonusPaySettingCode,
			BonusPayTimesheet bonusPayTimesheet) {
		return new KbpmtBPTimesheet(new KbpmtBPTimesheetPK(companyId, bonusPayTimesheet.getTimeSheetId(),bonusPaySettingCode),
				new BigDecimal(bonusPayTimesheet.getUseAtr().value),
				bonusPayTimesheet.getTimeItemId().toString(), new BigDecimal(bonusPayTimesheet.getStartTime().minute()),
				new BigDecimal(bonusPayTimesheet.getEndTime().minute()),
				new BigDecimal(bonusPayTimesheet.getRoundingTimeAtr().value),
				new BigDecimal(bonusPayTimesheet.getRoundingAtr().value));

	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(KbpmtBPTimesheet kbpmtBPTimesheet) {
		return BonusPayTimesheet.createFromJavaType(kbpmtBPTimesheet.kbpmtBPTimesheetPK.timeSheetNO,
				kbpmtBPTimesheet.useAtr.intValue(), kbpmtBPTimesheet.timeItemId, kbpmtBPTimesheet.startTime.longValue(),
				kbpmtBPTimesheet.endTime.longValue(), kbpmtBPTimesheet.roundingTimeAtr.intValue(),
				kbpmtBPTimesheet.roundingAtr.intValue());
	}

}
