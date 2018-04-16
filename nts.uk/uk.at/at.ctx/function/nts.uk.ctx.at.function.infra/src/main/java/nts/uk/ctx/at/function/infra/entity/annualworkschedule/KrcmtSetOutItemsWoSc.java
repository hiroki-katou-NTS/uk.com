package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_SET_OUT_ITEMS_WO_SC")
public class KrcmtSetOutItemsWoSc extends UkJpaEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	* ID
	*/
	@EmbeddedId
	public KrcmtSetOutItemsWoScPk setOutItemsWoScPk;

	/**
	* 36協定の表示設定
	*/
	@Basic(optional = false)
	@Column(name = "DISP_SETT_AGR_36")
	public int dispSettAgr36;

	/**
	* 名称
	*/
	@Basic(optional = false)
	@Column(name = "NAME")
	public String name;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_NUM_EXCEED_TIME_36_AGR")
	public int outNumExceedTime36Agr;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "DISPLAY_FORMAT")
	public int displayFormat;

	@Override
	protected Object getKey() {
		return setOutItemsWoScPk;
	}

	public SetOutItemsWoSc toDomain() {
		return new SetOutItemsWoSc(this.setOutItemsWoScPk.cid, this.setOutItemsWoScPk.cd, this.dispSettAgr36, this.name, this.outNumExceedTime36Agr, this.displayFormat);
	}
	public static KrcmtSetOutItemsWoSc toEntity(SetOutItemsWoSc domain) {
		return new KrcmtSetOutItemsWoSc(new KrcmtSetOutItemsWoScPk(domain.getCid(), domain.getCd()), domain.getDispSettAgr36(), domain.getName(), domain.getOutNumExceedTime36Agr(), domain.getDisplayFormat());
	}

}
