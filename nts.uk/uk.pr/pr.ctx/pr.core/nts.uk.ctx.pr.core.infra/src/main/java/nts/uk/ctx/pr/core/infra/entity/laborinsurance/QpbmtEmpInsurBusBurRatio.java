package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public static List<QpbmtEmpInsurBusBurRatio> toEntity(List<EmpInsurBusBurRatio> domain){
    	List<QpbmtEmpInsurBusBurRatio> listEmpInsurBusBurRatio = domain.stream().map(item -> {return new QpbmtEmpInsurBusBurRatio(
    			new QpbmtEmpInsurBusBurRatioPk(item.getHisId(), item.getEmpPreRateId().value),
    			item.getIndBdRatio().v(),
    			item.getEmpContrRatio().v(),
    			item.getPerFracClass().value,
    			item.getBusiOwFracClass().value);}).collect(Collectors.toList());
    	return listEmpInsurBusBurRatio;
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
