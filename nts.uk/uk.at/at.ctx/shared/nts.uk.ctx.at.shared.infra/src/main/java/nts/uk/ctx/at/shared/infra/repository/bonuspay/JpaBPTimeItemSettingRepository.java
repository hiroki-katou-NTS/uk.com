package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BPTimeItemSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBPTimeItemSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBPTimeItemSettingPK;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItemPK;
@Stateless
public class JpaBPTimeItemSettingRepository extends JpaRepository implements BPTimeItemSettingRepository {
	private final String SELECT_BPTIMEITEMSET_BY_COMPANYID = "SELECT c FROM KbpstBPTimeItemSetting c  JOIN  KbpstBonusPayTimeItem k  ON c.kbpstBPTimeItemSettingPK.timeItemId = k.kbpstBonusPayTimeItemPK.timeItemId"
			+ "WHERE c.kbpstBPTimeItemSettingPK.companyId = :companyId  AND  k.timeItemTypeAtr = 0 AND K.useAtr = 0 ORDER BY k.timeItemNo";
	private final String SELECT_SPEC_BPTIMEITEMSET_BY_COMPANYID = "SELECT FROM KbpstBPTimeItemSetting c JOIN  KbpstBonusPayTimeItem k ON c.kbpstBPTimeItemSettingPK.timeItemId = k.kbpstBonusPayTimeItemPK.timeItemId"
			+ "WHERE c.kbpstBPTimeItemSettingPK.companyId = :companyId  AND  k.timeItemTypeAtr = 1 AND K.useAtr = 0 ORDER BY k.timeItemNo";

	@Override
	public List<BPTimeItemSetting> getListSetting(String companyId) {
		return this.queryProxy().query(SELECT_BPTIMEITEMSET_BY_COMPANYID, KbpstBPTimeItemSetting.class)
				.setParameter("companyId", companyId).getList(x -> this.toBPTimeItemSettingDomain(x));
	}

	@Override
	public List<BPTimeItemSetting> getListSpecialSetting(String companyId) {
		return this.queryProxy().query(SELECT_SPEC_BPTIMEITEMSET_BY_COMPANYID, KbpstBPTimeItemSetting.class)
				.setParameter("companyId", companyId).getList(x -> this.toBPTimeItemSettingDomain(x));
	}

	@Override
	public void addListSetting(List<BPTimeItemSetting> lstSetting) {
		List<KbpstBPTimeItemSetting> lstKbpstBPTimeItemSetting = lstSetting.stream().map(c -> toBPTimeItemSettingEntity(c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpstBPTimeItemSetting);
	}

	@Override
	public void updateListSetting(List<BPTimeItemSetting> lstSetting) {
		lstSetting.forEach(c->{
			Optional<KbpstBPTimeItemSetting> kbpstBPTimeItemSettingOptional = this.queryProxy().find(new KbpstBPTimeItemSettingPK(c.getCompanyId().toString(), c.getTiemItemId().v()), KbpstBPTimeItemSetting.class);
			if (kbpstBPTimeItemSettingOptional.isPresent()) {
				KbpstBPTimeItemSetting kbpstBPTimeItemSetting = kbpstBPTimeItemSettingOptional.get();
				kbpstBPTimeItemSetting.holidayCalSettingAtr= new BigDecimal(c.getHolidayCalSettingAtr().value);
				kbpstBPTimeItemSetting.overtimeCalSettingAtr= new BigDecimal(c.getOvertimeCalSettingAtr().value);
				kbpstBPTimeItemSetting.worktimeCalSettingAtr= new BigDecimal(c.getOvertimeCalSettingAtr().value);
				this.commandProxy().update(kbpstBPTimeItemSetting);
			}
		});
	}

	private BPTimeItemSetting toBPTimeItemSettingDomain(KbpstBPTimeItemSetting kbpstBPTimeItemSetting) {
		return BPTimeItemSetting.createFromJavaType(kbpstBPTimeItemSetting.kbpstBPTimeItemSettingPK.companyId,
				kbpstBPTimeItemSetting.kbpstBPTimeItemSettingPK.timeItemId,
				kbpstBPTimeItemSetting.holidayCalSettingAtr.intValue(),
				kbpstBPTimeItemSetting.overtimeCalSettingAtr.intValue(),
				kbpstBPTimeItemSetting.worktimeCalSettingAtr.intValue());

	}

	private KbpstBPTimeItemSetting toBPTimeItemSettingEntity(BPTimeItemSetting bpTimeItemSetting) {

		return new KbpstBPTimeItemSetting(
				new KbpstBPTimeItemSettingPK(bpTimeItemSetting.getCompanyId().toString(),
						bpTimeItemSetting.getTiemItemId().toString()),
				new BigDecimal(bpTimeItemSetting.getHolidayCalSettingAtr().value),
				new BigDecimal(bpTimeItemSetting.getOvertimeCalSettingAtr().value),
				new BigDecimal(bpTimeItemSetting.getWorktimeCalSettingAtr().value));
	}

}
