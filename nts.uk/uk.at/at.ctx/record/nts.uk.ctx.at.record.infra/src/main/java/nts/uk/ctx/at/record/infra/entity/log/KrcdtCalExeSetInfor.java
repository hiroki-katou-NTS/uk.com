package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.CalExeSettingInfor;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.SetInforReflAprResult;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author hieult
 *
 */

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_EXEC_CASE_DETAIL")
public class KrcdtCalExeSetInfor extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrcdtCalExeSetInforPK krcdtCalExeSetInforPK;
	
	/**実行内容*/
	@Column(name = "EXECUTION_CONTENT")
	public int executionContent;

	/** 実行種別 */
	@Column(name = "EXECUTION_TYPE")
	public int executionType;
		
	/** 確定済みの場合にも強制的に反映する */
	@Column(name = "REF_EVEN_CONFIRM", nullable = true)
	public Boolean alsoForciblyReflectEvenIfItIsConfirmed;
	
	@Override
	protected Object getKey() {
		return this.krcdtCalExeSetInforPK;
	}
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="CAL_EXECUTION_SET_INFO_ID", referencedColumnName="CAL_EXECUTION_SET_INFO_ID", insertable = false, updatable = false),
		@JoinColumn(name="EXECUTION_CONTENT", referencedColumnName="EXECUTION_CONTENT", insertable = false, updatable = false)
	})
	public KrcdtExecLog executionlog;
	
	@OneToOne
	@JoinColumn(name="CAL_EXECUTION_SET_INFO_ID", referencedColumnName="MONTHLY_AGG_SET_INFOR_ID", insertable = false, updatable = false, nullable=true)
	public KrcstCaseSpecExeContent caseSpecExeContentMonthly;
	
	@OneToOne
	@JoinColumn(name="CAL_EXECUTION_SET_INFO_ID", referencedColumnName="REF_APPROVAL_SET_INFO_ID", insertable = false, updatable = false, nullable=true)
	public KrcstCaseSpecExeContent caseSpecExeContentRef;
	
	@OneToOne
	@JoinColumn(name="CAL_EXECUTION_SET_INFO_ID", referencedColumnName="DAILY_CAL_SET_INFO_ID", insertable = false, updatable = false, nullable=true)
	public KrcstCaseSpecExeContent caseSpecExeContentCal;
	
	@OneToOne
	@JoinColumn(name="CAL_EXECUTION_SET_INFO_ID", referencedColumnName="DAILY_CREAT_SET_INFO_ID", insertable = false, updatable = false, nullable=true)
	public KrcstCaseSpecExeContent caseSpecExeContentCreat;

	
	public KrcdtCalExeSetInfor(KrcdtCalExeSetInforPK krcdtCalExeSetInforPK,int executionContent, int executionType,
			Boolean alsoForciblyReflectEvenIfItIsConfirmed) {
		super();
		this.krcdtCalExeSetInforPK = krcdtCalExeSetInforPK;
		this.executionContent = executionContent;
		this.executionType = executionType;
		this.alsoForciblyReflectEvenIfItIsConfirmed = alsoForciblyReflectEvenIfItIsConfirmed;
	}

	@SuppressWarnings("unchecked")
	public <T> T toDomain() {
		if (this.executionContent == ExecutionContent.DAILY_CREATION.value) {
			CalExeSettingInfor calExeSettingInfor = new CalExeSettingInfor(
					EnumAdaptor.valueOf(this.executionContent, ExecutionContent.class),
					EnumAdaptor.valueOf(this.executionType, ExecutionType.class),
					this.krcdtCalExeSetInforPK.calExecutionSetInfoID);
			return (T) calExeSettingInfor;
		} else if(this.executionContent == ExecutionContent.DAILY_CALCULATION.value ) {
			//calculation
			CalExeSettingInfor calExeSettingInfor = new CalExeSettingInfor(
					EnumAdaptor.valueOf(this.executionContent, ExecutionContent.class),
					EnumAdaptor.valueOf(this.executionType, ExecutionType.class),
					this.krcdtCalExeSetInforPK.calExecutionSetInfoID
					);
			return (T) calExeSettingInfor;
		}else if(this.executionContent == ExecutionContent.REFLRCT_APPROVAL_RESULT.value) {
			//Reflect
			SetInforReflAprResult setInforReflAprResult = new SetInforReflAprResult(
					EnumAdaptor.valueOf(this.executionContent, ExecutionContent.class),
					EnumAdaptor.valueOf(this.executionType, ExecutionType.class),
					this.krcdtCalExeSetInforPK.calExecutionSetInfoID,
					this.alsoForciblyReflectEvenIfItIsConfirmed
					);
					
			return (T) setInforReflAprResult;
			
		}else {
			//aggregation
			CalExeSettingInfor calExeSettingInfor = new CalExeSettingInfor(
					EnumAdaptor.valueOf(this.executionContent, ExecutionContent.class),
					EnumAdaptor.valueOf(this.executionType, ExecutionType.class),
					this.krcdtCalExeSetInforPK.calExecutionSetInfoID
					);
			return (T) calExeSettingInfor;
		}
	}

	//create, calculation,aggregation
	public static KrcdtCalExeSetInfor toEntity(CalExeSettingInfor domain) {
		return new KrcdtCalExeSetInfor(
					new KrcdtCalExeSetInforPK(
						domain.getCalExecutionSetInfoID()
						),
					domain.getExecutionContent().value,
					domain.getExecutionType().value,
					null
				);
	}
	//Reflect 
	public static KrcdtCalExeSetInfor toEntity(SetInforReflAprResult domain) {
		return new KrcdtCalExeSetInfor(
					new KrcdtCalExeSetInforPK(
						domain.getCalExecutionSetInfoID()
						),
					domain.getExecutionContent().value,
					domain.getExecutionType().value,
					domain.isForciblyReflect()
				);
	}
	
	
}
