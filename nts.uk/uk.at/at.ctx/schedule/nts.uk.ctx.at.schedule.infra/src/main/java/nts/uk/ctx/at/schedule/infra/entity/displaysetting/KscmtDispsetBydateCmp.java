package nts.uk.ctx.at.schedule.infra.entity.displaysetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayRangeType;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDate;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCmp;
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
@Setter
@Table(name = "KSCMT_DISPSET_BYDATE_CMP")
public class KscmtDispsetBydateCmp extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	 * convert to domain
	 * @param entity
	 * @return domain
	 */
	public static DisplaySettingByDateForCmp of(KscmtDispsetBydateCmp entity) {
		DisplaySettingByDate dispDomain = new DisplaySettingByDate(
				DisplayRangeType.of(entity.rangeAtr), 
				new DisplayStartTime(entity.startClock), 
				new DisplayStartTime(entity.initStartClock));
		
		return new DisplaySettingByDateForCmp(dispDomain);
	}
	
	/**
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtDispsetBydateCmp toEntity (String companyId, DisplaySettingByDateForCmp domain) {
		return new KscmtDispsetBydateCmp(
				companyId,
				domain.getDispSetting().getDispRange().value,
				domain.getDispSetting().getDispStart().v(),
				domain.getDispSetting().getInitDispStart().v());
	}
}
