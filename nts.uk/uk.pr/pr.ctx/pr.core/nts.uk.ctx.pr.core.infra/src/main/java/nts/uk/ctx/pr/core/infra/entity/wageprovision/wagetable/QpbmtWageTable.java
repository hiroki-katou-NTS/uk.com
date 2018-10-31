package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 賃金テーブル
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TABLE")
public class QpbmtWageTable extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtWageTablePk wageTablePk;

    /**
     * 賃金テーブル名
     */
    @Basic(optional = false)
    @Column(name = "WAGE_TABLE_NAME")
    public String wageTableName;

    /**
     * 固定の要素
     */
    @Basic(optional = true)
    @Column(name = "FIRST_FIXED_ELEMENT")
    public Integer firstFixedElement;

    /**
     * 任意追加の要素
     */
    @Basic(optional = true)
    @Column(name = "FIRST_ADDITIONAL_ELEMENT")
    public String firstAdditionalElement;

    /**
     * マスタ数値区分
     */
    @Basic(optional = true)
    @Column(name = "FIRST_MASTER_NUMERIC_CLS")
    public Integer firstMasterNumericCls;

    /**
     * 固定の要素
     */
    @Basic(optional = true)
    @Column(name = "SECOND_FIXED_ELEMENT")
    public Integer secondFixedElement;

    /**
     * 任意追加の要素
     */
    @Basic(optional = true)
    @Column(name = "SECOND_ADDITIONAL_ELEMENT")
    public String secondAdditionalElement;

    /**
     * マスタ数値区分
     */
    @Basic(optional = true)
    @Column(name = "SECOND_MASTER_NUMERIC_CLS")
    public Integer secondMasterNumericCls;

    /**
     * 固定の要素
     */
    @Basic(optional = true)
    @Column(name = "THIRD_FIXED_ELEMENT")
    public Integer thirdFixedElement;

    /**
     * 任意追加の要素
     */
    @Basic(optional = true)
    @Column(name = "THIRD_ADDITIONAL_ELEMENT")
    public String thirdAdditionalElement;

    /**
     * マスタ数値区分
     */
    @Basic(optional = true)
    @Column(name = "THIRD_MASTER_NUMERIC_CLS")
    public Integer thirdMasterNumericCls;

    /**
     * 要素設定
     */
    @Basic(optional = false)
    @Column(name = "ELEMENT_SETTING")
    public int elementSetting;

    /**
     * 備考情報
     */
    @Basic(optional = true)
    @Column(name = "REMARK_INFORMATION")
    public String remarkInformation;

    @Override
    protected Object getKey() {
        return wageTablePk;
    }

    public WageTable toDomain() {
        return new WageTable(this.wageTablePk.cid, this.wageTablePk.wageTableCode, this.wageTableName, this.firstFixedElement, this.firstAdditionalElement, this.firstMasterNumericCls, this.secondFixedElement, this.secondAdditionalElement, this.secondMasterNumericCls, this.thirdFixedElement, this.thirdAdditionalElement, this.thirdMasterNumericCls, this.elementSetting, this.remarkInformation);
    }

    public static QpbmtWageTable toEntity(WageTable domain) {
        val firstFixedElement = domain.getElementInformation().getOneDimensionalElement().getFixedElement().map(v -> v.value).orElse(null);
        val firstOptionalAddElement = domain.getElementInformation().getOneDimensionalElement().getOptionalAdditionalElement().map(i -> i.v()).orElse(null);
        val firstMasterNumeric = domain.getElementInformation().getOneDimensionalElement().getMasterNumericClassification().map(i -> i.value).orElse(null);


        val secondElement = domain.getElementInformation().getTwoDimensionalElement().map(i -> i.getFixedElement().map(v -> v.value).orElse(null)).orElse(null);
        val secondOptionalAddElement = domain.getElementInformation().getTwoDimensionalElement().map(i -> i.getOptionalAdditionalElement().map(v -> v.v()).orElse(null)).orElse(null);
        val secondMasterNumeric = domain.getElementInformation().getTwoDimensionalElement().map(i -> i.getMasterNumericClassification().map(v -> v.value).orElse(null)).orElse(null);


        val thirdElement = domain.getElementInformation().getThreeDimensionalElement().map(i -> i.getFixedElement().map(v -> v.value).orElse(null)).orElse(null);
        val thirdOptionalAddElement = domain.getElementInformation().getThreeDimensionalElement().map(i -> i.getOptionalAdditionalElement().map(v -> v.v()).orElse(null)).orElse(null);
        val thirdMasterNumeric = domain.getElementInformation().getThreeDimensionalElement().map(i -> i.getMasterNumericClassification().map(v -> v.value).orElse(null)).orElse(null);

        return new QpbmtWageTable(new QpbmtWageTablePk(domain.getCID(), domain.getWageTableCode().v()), domain.getWageTableName().v(),
                firstFixedElement, firstOptionalAddElement, firstMasterNumeric,
                secondElement, secondOptionalAddElement, secondMasterNumeric,
                thirdElement, thirdOptionalAddElement, thirdMasterNumeric,
                domain.getElementSetting().value, domain.getRemarkInformation().map(i -> i.v()).orElse(null));

    }
}