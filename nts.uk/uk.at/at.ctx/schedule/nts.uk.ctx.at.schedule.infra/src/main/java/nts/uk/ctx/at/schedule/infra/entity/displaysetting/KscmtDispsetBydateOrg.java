package nts.uk.ctx.at.schedule.infra.entity.displaysetting;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayRangeType;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDate;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForOrganization;
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
@Table(name = "KSCMT_DISPSET_BYDATE_ORG")
public class KscmtDispsetBydateOrg extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtDispsetBydateOrg> MAPPER = new JpaEntityMapper<>(KscmtDispsetBydateOrg.class);
	
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
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtDispsetBydateOrg of (String companyId, DisplaySettingByDateForOrganization domain) {

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
	
	/**
	 * convert to domain
	 * @return domain
	 */
	public DisplaySettingByDateForOrganization toDomain () {
		val targetOrg = new TargetOrgIdenInfor (
				TargetOrganizationUnit.valueOf(this.pk.targetUnit),
				Optional.ofNullable(this.pk.targetId),
				Optional.ofNullable(this.pk.targetId));
		
		val dispDomain = new DisplaySettingByDate(
				DisplayRangeType.of(this.rangeAtr), 
				new DisplayStartTime(this.startClock), 
				new DisplayStartTime(this.initStartClock));
		
		return new DisplaySettingByDateForOrganization(targetOrg, dispDomain);
	}
}
