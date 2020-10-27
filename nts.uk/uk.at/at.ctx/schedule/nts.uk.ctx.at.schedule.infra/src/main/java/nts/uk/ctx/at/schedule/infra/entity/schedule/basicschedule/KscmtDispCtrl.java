package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の表示制御
 * 
 * @author trungtran
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_DISP_CTRL")
public class KscmtDispCtrl extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtDispCtrlPK kscmtDispCtrlPK;

	/** 資格表示記号 B1_14 */
	@Column(name = "PERSON_SYQUALIFY")
	public String personSyQualify;

	/** 取得不足表示区分 B3_8 */
	@Column(name = "PUB_HD_SHORTAGE_ATR")
	public boolean pubHolidayShortageAtr;

	/** 取得超過表示区分 B3_4 */
	@Column(name = "PUB_HD_EXCESS_ATR")
	public boolean pubHolidayExcessAtr;

	/** 勤務就業記号表示区分 B2_8 */
	@Column(name = "SYMBOL_ATR")
	public boolean symbolAtr;

	/** 半日表示区分 B2_4*/
	@Column(name = "SYMBOL_HALF_DAY_ATR")
	public boolean symbolHalfDayAtr;

	/** 半日記号 */
	@Column(name = "SYMBOL_HALF_DAY_NAME")
	public String symbolHalfDayName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="scheDispControl", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KscmtSchePerInfoAtr> schePerInfoAtr;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="scheDispControl", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KscstScheQualifySet> scheQualifySet;

	@Override
	protected Object getKey() {
		return this.kscmtDispCtrlPK;
	}
}
