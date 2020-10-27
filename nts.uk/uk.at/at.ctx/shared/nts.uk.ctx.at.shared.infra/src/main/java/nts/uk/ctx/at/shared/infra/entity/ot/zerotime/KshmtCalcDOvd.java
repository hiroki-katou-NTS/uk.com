package nts.uk.ctx.at.shared.infra.entity.ot.zerotime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * @author phongtq
 * 0時跨ぎ計算設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_CALC_D_OVD")
public class KshmtCalcDOvd extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshmtCalcDOvdPK kshstOverDayCalcSetPK;
	
	/** 0時跨ぎ計算を行なう*/
	@Column(name = "CALC_FROM_ZERO_TIME")
	public int calcFromZeroTime;
	
	/** 法定内休日 */
	@Column(name = "WKD_LEGAL_HD")
	public int legalHd;
	
	/** 法定外休日 */
	@Column(name = "WKD_NON_LEGAL_HD")
	public int nonLegalHd;
	
	/** 法定外祝日*/
	@Column(name = "WKD_NON_LEGAL_PUBLIC_HD")
	public int nonLegalPublicHd;
	
	/** 平日 */
	@Column(name = "LHD_WEEKDAY")
	public int weekday1;
	
	/** 法定外休日*/
	@Column(name = "LHD_NON_LEGAL_HD")
	public int nonLegalHd1;
	
	/** 法定外祝日 */
	@Column(name = "LHD_NON_LEGAL_PUBLIC_HD")
	public int nonLegalPublicHd1;
	
	/** 平日*/
	@Column(name = "SHD_WEEKDAY")
	public int weekday2;

	/** 法定内休日 */
	@Column(name = "SHD_LEGAL_HD")
	public int legalHd2;
	
	/** 法定外祝日 */
	@Column(name = "SHD_NON_LEGAL_HD")
	public int nonLegalHd2;
	
	/** 平日 */
	@Column(name = "SFT_WEEKDAY")
	public int weekday3;
	
	/** 法定内休日 */
	@Column(name = "SFT_LEGAL_HD")
	public int legalHd3;
	
	/** 法定外休日 */
	@Column(name = "SFT_NON_LEGAL_PUBLIC_HD")
	public int nonLegalPublicHd3;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
	public List<KshmtCalcDOvdWtoh> weekdayHd;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
	public List<KshmtCalcDOvdHtow> overdayHdAttSet;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
	public List<KshmtCalcDOvdHtoh> overDayHdSet;
	
	@Override
	protected Object getKey() {
		return kshstOverDayCalcSetPK;
	}
}
