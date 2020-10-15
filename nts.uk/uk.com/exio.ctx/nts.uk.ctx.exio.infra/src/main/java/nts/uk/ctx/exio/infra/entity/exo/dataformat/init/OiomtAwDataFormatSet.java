package nts.uk.ctx.exio.infra.entity.exo.dataformat.init;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 外部出力在職区分型データ形式設定（初期値）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_FM_STAT_INIT")
public class OiomtAwDataFormatSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtAwDataFormatSetPk awDataFormatSetPk;
    
	/**
	 * 在職時出力
	 */
	@Basic(optional = true)
	@Column(name = "AT_WORK_OUTPUT")
	public String atWorkOutput;

	/**
	 * 休職時出力
	 */
	@Basic(optional = true)
	@Column(name = "ABSENCE_OUTPUT")
	public String absenceOutput;

	/**
	 * 休業時出力
	 */
	@Basic(optional = true)
	@Column(name = "CLOSED_OUTPUT")
	public String closedOutput;

	/**
	 * 退職時出力
	 */
	@Basic(optional = true)
	@Column(name = "RETIREMENT_OUTPUT")
	public String retirementOutput;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VAL_ATR")
	public int fixedValue;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_VAL")
	public String valueOfFixedValue;

    @Override
    protected Object getKey() {
        return awDataFormatSetPk;
    }
    
    public AwDataFormatSet toDomain() {
        return new AwDataFormatSet(ItemType.AT_WORK_CLS.value,this.awDataFormatSetPk.cid, this.closedOutput, this.absenceOutput, this.fixedValue, this.valueOfFixedValue, this.atWorkOutput, this.retirementOutput);
    }
    
    public static OiomtAwDataFormatSet toEntity(AwDataFormatSet domain) {
        return new OiomtAwDataFormatSet(
        		new OiomtAwDataFormatSetPk(domain.getCid()),
                domain.getAtWorkOutput().isPresent() ? domain.getAtWorkOutput().get().v() : null,
        		domain.getAbsenceOutput().isPresent() ? domain.getAbsenceOutput().get().v() : null,
        		domain.getClosedOutput().isPresent() ? domain.getClosedOutput().get().v() : null,
                domain.getRetirementOutput().isPresent() ? domain.getRetirementOutput().get().v() : null,
        		domain.getFixedValue().value,
        		domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null);
    }

}
