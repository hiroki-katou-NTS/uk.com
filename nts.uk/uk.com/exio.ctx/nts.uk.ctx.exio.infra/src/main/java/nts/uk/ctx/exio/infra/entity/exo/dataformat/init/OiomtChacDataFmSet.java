package nts.uk.ctx.exio.infra.entity.exo.dataformat.init;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 外部出力文字型データ形式設定（初期値）
*/

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_FM_CHAC_INIT")
public class OiomtChacDataFmSet extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtChacDataFmSetPk chacDataFmSetPk;

	/**
	 * 有効桁数
	 */
	@Basic(optional = false)
	@Column(name = "EFFECT_DIGIT_LENGTH")
	public int effectDigitLength;

	/**
	 * 有効桁数開始桁
	 */
	@Basic(optional = true)
	@Column(name = "START_DIGIT")
	public Integer startDigit;

	/**
	 * 有効桁数終了桁
	 */
	@Basic(optional = true)
	@Column(name = "END_DIGIT")
	public Integer endDigit;

	/**
	 * コード編集
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDIT")
	public int cdEditting;

	/**
	 * コード編集桁
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDIT_DIGIT")
	public Integer cdEditDigit;

	/**
	 * コード編集方法
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDIT_METHOD")
	public int cdEdittingMethod;

	/**
	 * スペース編集
	 */
	@Basic(optional = false)
	@Column(name = "SPACE_EDIT")
	public int spaceEditting;

	/**
	 * コード変換コード
	 */
	@Basic(optional = true)
	@Column(name = "CONVERT_CD")
	public String cdConvertCd;
    
    /**
    * NULL値置換
    */
    @Basic(optional = false)
    @Column(name = "NULL_REPLACE_VAL_ATR")
    public int nullValueReplace;
    
    /**
    * NULL値置換の値
    */
    @Basic(optional = true)
    @Column(name = "NULL_REPLACE_VAL")
    public String valueOfNullValueReplace;

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
        return chacDataFmSetPk;
    }

    public ChacDataFmSet toDomain() {
        return new ChacDataFmSet(ItemType.CHARACTER.value,
        		this.chacDataFmSetPk.cid,
        		this.nullValueReplace,
        		this.valueOfNullValueReplace,
        		this.cdEditting,
        		this.fixedValue,
        		this.cdEdittingMethod,
        		this.cdEditDigit,
        		this.cdConvertCd,
        		this.spaceEditting,
        		this.effectDigitLength,
        		this.startDigit,
        		this.endDigit,
        		this.valueOfFixedValue);
    }
    public static OiomtChacDataFmSet toEntity(ChacDataFmSet domain) {
        return new OiomtChacDataFmSet(
        		new OiomtChacDataFmSetPk(domain.getCid()),
        		domain.getNullValueReplace().value,
        		domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null, 
        		domain.getCdEditting().value,
        		domain.getFixedValue().value,
        		domain.getCdEdittingMethod().value, 
        		domain.getCdEditDigit().isPresent() ? domain.getCdEditDigit().get().v() : null,
        		domain.getConvertCode().isPresent() ? domain.getConvertCode().get().v() : null,
        		domain.getSpaceEditting().value,
        		domain.getEffectDigitLength().value, 
        		domain.getStartDigit().isPresent() ? domain.getStartDigit().get().v() : null,
        		domain.getEndDigit().isPresent() ? domain.getEndDigit().get().v() : null,
        		domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null);
    }

	public OiomtChacDataFmSet(OiomtChacDataFmSetPk chacDataFmSetPk, int nullValueReplace,
			String valueOfNullValueReplace, int cdEditting, int fixedValue, int cdEdittingMethod, Integer cdEditDigit,
			String cdConvertCd, int spaceEditting, int effectDigitLength, Integer startDigit, Integer endDigit,
			String valueOfFixedValue) {
		super();
		this.chacDataFmSetPk = chacDataFmSetPk;
		this.nullValueReplace = nullValueReplace;
		this.valueOfNullValueReplace = valueOfNullValueReplace;
		this.cdEditting = cdEditting;
		this.fixedValue = fixedValue;
		this.cdEdittingMethod = cdEdittingMethod;
		this.cdEditDigit = cdEditDigit;
		this.cdConvertCd = cdConvertCd;
		this.spaceEditting = spaceEditting;
		this.effectDigitLength = effectDigitLength;
		this.startDigit = startDigit;
		this.endDigit = endDigit;
		this.valueOfFixedValue = valueOfFixedValue;
	}

}
