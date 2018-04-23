package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 項目の算出式
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNRT_CALC_FORMULA_ITEM")
public class KfnrtCalcFormulaItem extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KfnrtCalcFormulaItemPk calcFormulaItemPk;
    
    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "SET_OUT_CD")
    public int setOutCd;
    
    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "ITEM_OUT_CD")
    public int itemOutCd;
    
    /**
    * 勤怠項目ID
    */
    @Basic(optional = false)
    @Column(name = "ATTENDANCE_ITEM_ID")
    public String attendanceItemId;
    
    /**
    * 加, 減
    */
    @Basic(optional = false)
    @Column(name = "OPERATION")
    public int operation;
    
    @Override
    protected Object getKey()
    {
        return calcFormulaItemPk;
    }

    public CalcFormulaItem toDomain() {
        return new CalcFormulaItem(this.calcFormulaItemPk.cid, this.setOutCd, this.itemOutCd, this.attendanceItemId, this.operation);
    }
    public static KfnrtCalcFormulaItem toEntity(CalcFormulaItem domain) {
        return new KfnrtCalcFormulaItem(new KfnrtCalcFormulaItemPk(domain.getCid()), domain.getSetOutCd(), domain.getItemOutCd(), domain.getAttendanceItemId(), domain.getOperation());
    }

}
