package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurPreRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurRateId;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 雇用保険料率
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_INSUR_PRE_RATE")
public class QpbmtEmpInsurPreRate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtEmpInsurPreRatePk empInsurPreRatePk;
    
    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    
    /**
    * 個人負担率
    */
    @Basic(optional = false)
    @Column(name = "IND_BD_RATIO_GENERAL")
    public BigDecimal indBdRatioGeneral;
    
    /**
    * 事業主負担率
    */
    @Basic(optional = false)
    @Column(name = "EMP_CONTR_RATIO_GENERAL")
    public BigDecimal empContrRatioGeneral;
    
    /**
    * 個人端数区分
    */
    @Basic(optional = false)
    @Column(name = "PER_FRAC_CLASS_GENERAL")
    public int perFracClassGeneral;
    
    /**
    * 事業主端数区分
    */
    @Basic(optional = false)
    @Column(name = "BUSI_OW_FRAC_CLASS_GENERAL")
    public int busiOwFracClassGeneral;
    
    /**
    * 個人負担率
    */
    @Basic(optional = false)
    @Column(name = "IND_BD_RATIO_AGRI_FORES_FISH")
    public BigDecimal indBdRatioAgriForesFish;
    
    /**
    * 事業主負担率
    */
    @Basic(optional = false)
    @Column(name = "EMP_CONTR_RATIO_AGRI_FORES_FISH")
    public BigDecimal empContrRatioAgriForesFish;
    
    /**
    * 個人端数区分
    */
    @Basic(optional = false)
    @Column(name = "PER_FRAC_CLASS_AGRI_FORES_FISH")
    public int perFracClassAgriForesFish;
    
    /**
    * 事業主端数区分
    */
    @Basic(optional = false)
    @Column(name = "BUSI_OW_FRAC_CLASS_AGRI_FORES_FISH")
    public int busiOwFracClassAgriForesFish;
    
    /**
    * 個人負担率
    */
    @Basic(optional = false)
    @Column(name = "IND_BD_RATIO_CONSTRUCTION")
    public BigDecimal indBdRatioConstruction;
    
    /**
    * 事業主負担率
    */
    @Basic(optional = false)
    @Column(name = "EMP_CONTR_RATIO_CONSTRUCTION")
    public BigDecimal empContrRatioConstruction;
    
    /**
    * 個人端数区分
    */
    @Basic(optional = false)
    @Column(name = "PER_FRAC_CLASS_CONSTRUCTION")
    public int perFracClassConstruction;
    
    /**
    * 事業主端数区分
    */
    @Basic(optional = false)
    @Column(name = "BUSI_OW_FRAC_CLASS_CONSTRUCTION")
    public int busiOwFracClassConstruction;
    
    @Override
    protected Object getKey() {
        return empInsurPreRatePk;
    }
    
    public List<EmpInsurBusBurRatio> toDomain(){
    	EmpInsurBusBurRatio general = new EmpInsurBusBurRatio(
    			this.empInsurPreRatePk.historyId, 
    			EmpInsurRateId.GEN_BUS_BURDEN_RATIO.value,
    			this.indBdRatioGeneral,
    			this.empContrRatioGeneral,
    			this.perFracClassGeneral,
    			this.busiOwFracClassGeneral);
    	EmpInsurBusBurRatio agriForesFish = new EmpInsurBusBurRatio(this.empInsurPreRatePk.historyId, 
    			EmpInsurRateId.BUS_RATIO_OF_AGRI_FOREST_FISH.value,
    			this.indBdRatioAgriForesFish,
    			this.empContrRatioAgriForesFish,
    			this.perFracClassAgriForesFish,
    			this.busiOwFracClassAgriForesFish);
    	EmpInsurBusBurRatio construction = new EmpInsurBusBurRatio(this.empInsurPreRatePk.historyId, 
    			EmpInsurRateId.BUS_BUR_RATIO_OF_CONSTRUCTION.value,
    			this.indBdRatioConstruction,
    			this.empContrRatioConstruction,
    			this.perFracClassConstruction,
    			this.busiOwFracClassConstruction);
    	List<EmpInsurBusBurRatio> empInsurBusBurRatio = new ArrayList<EmpInsurBusBurRatio>();
    	empInsurBusBurRatio.add(general);
    	empInsurBusBurRatio.add(agriForesFish);
    	empInsurBusBurRatio.add(construction);
    	return empInsurBusBurRatio;
    }
    
    public YearMonthHistoryItem toDomainYearMonth(){
    	return new YearMonthHistoryItem(
    			this.empInsurPreRatePk.historyId,
    			new YearMonthPeriod(new YearMonth(this.startYearMonth),new YearMonth(this.endYearMonth)));
    	
    }
    
    public static QpbmtEmpInsurPreRate toEntity(EmpInsurPreRate domain, String cId, YearMonthHistoryItem item){
    	
    	return new QpbmtEmpInsurPreRate(new QpbmtEmpInsurPreRatePk(cId, domain.getHisId()),
    			item.start().v(),
    			item.end().v(),
    			domain.getGenBusBurdenRatio().getIndBdRatio().v(),
    			domain.getGenBusBurdenRatio().getEmpContrRatio().v(),
    			domain.getGenBusBurdenRatio().getPerFracClass().value,
    			domain.getGenBusBurdenRatio().getBusiOwFracClass().value,
    			domain.getBusRatioOfAgriForestFish().getIndBdRatio().v(),
    			domain.getBusRatioOfAgriForestFish().getEmpContrRatio().v(),
    			domain.getBusRatioOfAgriForestFish().getPerFracClass().value,
    			domain.getBusRatioOfAgriForestFish().getBusiOwFracClass().value,
    			domain.getBusBurRatioOfConstruction().getIndBdRatio().v(),
    			domain.getBusBurRatioOfConstruction().getEmpContrRatio().v(),
    			domain.getBusBurRatioOfConstruction().getPerFracClass().value,
    			domain.getBusBurRatioOfConstruction().getBusiOwFracClass().value);
    }
}
