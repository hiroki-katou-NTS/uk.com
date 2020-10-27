package nts.uk.ctx.at.schedule.infra.entity.displaysetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayRangeType;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDate;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCompany;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayStartTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_DISPSET_BYDATE_CMP")
public class KscmtDispsetBydateCmp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<KscmtDispsetBydateCmp> MAPPER = new JpaEntityMapper<>(KscmtDispsetBydateCmp.class);
	
	@Override
	protected Object getKey() {
		return this.companyId;
	}
	
	/**
	 *  会社ID
	 */
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 表示の範囲
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
	
	
	/**
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtDispsetBydateCmp of (String companyId, DisplaySettingByDateForCompany domain) {
		return new KscmtDispsetBydateCmp(
				companyId,
				domain.getDispSetting().getDispRange().value,
				domain.getDispSetting().getDispStart().v(),
				domain.getDispSetting().getInitDispStart().v());
	}
	
	/**
	 * convert to domain
	 * @return domain
	 */
	public DisplaySettingByDateForCompany toDomain () {
		val dipSet = new DisplaySettingByDate (
				  DisplayRangeType.of(this.rangeAtr)
				, new DisplayStartTime(this.startClock)
				, new DisplayStartTime(this.initStartClock));
		
		return new DisplaySettingByDateForCompany (dipSet);
	}
}
