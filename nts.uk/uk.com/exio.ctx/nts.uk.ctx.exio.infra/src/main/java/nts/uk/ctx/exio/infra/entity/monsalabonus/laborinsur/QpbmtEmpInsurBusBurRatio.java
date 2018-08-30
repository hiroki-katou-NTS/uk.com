package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatio;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* 雇用保険料率
*/
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_INSUR_PRE_RATE")
public class QpbmtEmpInsurBusBurRatio extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpInsurBusBurRatioPk empInsurBusBurRatioPk;

    /**
     * 個人負担率
     */
    @Basic(optional = false)
    @Column(name = "IND_BD_RATIO")
    public BigDecimal indBdRatio;

    /**
     * 事業主負担率
     */
    @Basic(optional = false)
    @Column(name = "EMP_CONTR_RATIO")
    public BigDecimal empContrRatio;

    /**
     * 個人端数区分
     */
    @Basic(optional = false)
    @Column(name = "PER_FRAC_CLASS")
    public int perFracClass;

    /**
     * 事業主端数区分
     */
    @Basic(optional = false)
    @Column(name = "BUSI_OW_FRAC_CLASS")
    public int busiOwFracClass;

    @Override
    protected Object getKey()
    {
        return empInsurBusBurRatioPk;
    }

    public EmpInsurBusBurRatio toDomain(){
    	return new EmpInsurBusBurRatio(
    			this.empInsurBusBurRatioPk.hisId,
    			this.empInsurBusBurRatioPk.empPreRateId,
    			this.indBdRatio,
    			this.empContrRatio,
    			this.perFracClass,
    			this.busiOwFracClass);
    }
    
    public static QpbmtEmpInsurBusBurRatio toEntity(EmpInsurBusBurRatio domain){
    	return new QpbmtEmpInsurBusBurRatio(
    			new QpbmtEmpInsurBusBurRatioPk(domain.getHisId(), domain.getEmpPreRateId().value),
    			domain.getIndBdRatio().v(),
    			domain.getEmpContrRatio().v(),
    			domain.getPerFracClass().value,
    			domain.getBusiOwFracClass().value);
    }

	public QpbmtEmpInsurBusBurRatio(QpbmtEmpInsurBusBurRatioPk empInsurBusBurRatioPk, BigDecimal indBdRatio,
			BigDecimal empContrRatio, int perFracClass, int busiOwFracClass) {
		super();
		this.empInsurBusBurRatioPk = empInsurBusBurRatioPk;
		this.indBdRatio = indBdRatio;
		this.empContrRatio = empContrRatio;
		this.perFracClass = perFracClass;
		this.busiOwFracClass = busiOwFracClass;
	}
    
    
}
