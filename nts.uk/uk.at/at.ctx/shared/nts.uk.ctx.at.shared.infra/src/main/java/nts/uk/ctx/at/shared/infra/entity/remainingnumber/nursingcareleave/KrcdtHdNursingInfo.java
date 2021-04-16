package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 子の看護介護休暇基本情報
 */
@Entity
@Table(name="KRCDT_HDNURSING_INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KrcdtHdNursingInfo extends ContractUkJpaEntity{

	/** プライマリキー */
	@EmbeddedId
	public KrcdtHdNursingInfoPK pk;

	@Column(name="CID")
	private String cId;

	//使用区分
	@Column(name="USE_ATR")
	private int useAtr;

	//上限設定
	@Column(name="UPPER_LIM_SET_ART")
	private int upperLimSetAtr;

	//本年度上限日数
	@Column(name="MAX_DAY_THIS_FISCAL_YEAR")
	private Integer maxDayThisFiscalYear;

	//次年度上限日数
	@Column(name="MAX_DAY_NEXT_FISCAL_YEAR")
	private Integer maxDayNextFiscalYear;

	@Override
	protected Object getKey() {
		return pk;
	}

}
