package nts.uk.ctx.at.schedule.infra.entity.displaysetting;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayRangeType;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDate;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForOrg;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayStartTime;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "KSCMT_DISPSET_BYDATE_ORG")
public class KscmtDispsetBydateOrg extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscmtDispsetBydateOrgPk pk;
	
	/**
	 *  表示の範囲
	 */
	@Column(name = "RANGE_ATR")
	public int rangeAtr;
	
	/**
	 * 表示する範囲の開始時刻
	 */
	@Column(name = "START_CLOCK")
	public int startClock;
	
	/**
	 * 初期表示の開始時刻(スクロールの位置)
	 */
	@Column(name = "INIT_START_CLOCK")
	public int initStartClock;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	
	/**
	 * convert to domain
	 * @param entity
	 * @return domain
	 */
	public static DisplaySettingByDateForOrg of (KscmtDispsetBydateOrg entity) {
		
		TargetOrgIdenInfor targetOrg = new TargetOrgIdenInfor (
				TargetOrganizationUnit.valueOf(entity.pk.targetUnit),
				Optional.ofNullable(entity.pk.targetId),
				Optional.ofNullable(entity.pk.targetId));
		
		DisplaySettingByDate dispDomain = new DisplaySettingByDate(
				DisplayRangeType.of(entity.rangeAtr), 
				new DisplayStartTime(entity.startClock), 
				new DisplayStartTime(entity.initStartClock));
		
		return new DisplaySettingByDateForOrg(targetOrg, dispDomain);
	}
	
	/**
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtDispsetBydateOrg toEntity (String companyId, DisplaySettingByDateForOrg domain) {

		KscmtDispsetBydateOrgPk entPk = new KscmtDispsetBydateOrgPk(
				companyId,
				domain.getTargetOrg().getUnit().value,
				domain.getTargetOrg().getTargetId());
		
		return new KscmtDispsetBydateOrg (
				entPk,
				domain.getDispSetting().getDispRange().value,
				domain.getDispSetting().getDispStart().v(),
				domain.getDispSetting().getInitDispStart().v());
	}
}
