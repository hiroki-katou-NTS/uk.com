package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;



/**
 * 勤務予定の基本情報
 * @author HieuLt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_SCH_BASIC_INFO")
public class KscdtSchBasicInfo extends ContractUkJpaEntity {
	@EmbeddedId
	public KscdtSchBasicInfoPK pk;
	
	/** "予定確定区分 ---true:確定済み---false:未確定" **/								
	@Column(name = "DECISION_STATUS")
	public boolean decisionStatus;
	/** 雇用コード **/
	@Column(name = "EMP_CD")
	public String empCd;
	/** 職位ID **/
	@Column(name = "JOB_ID")
	public String jobId;
	/** 職場ID **/
	@Column(name = "WKP_ID")
	public String wkpId;
	/** 分類コード**/ 
	@Column(name = "CLS_CD ")
	public boolean clsCd;
	/** 勤務種別コード **/
	@Column(name = "BUSTYPE_CD")
	public String busTypeCd;
	/** 看護区分 **/
	@Column(name = "NURSE_LICENSE")
	public String nurseLicense;
	/** 勤務種類コード **/
	@Column(name = "WKTP_CD")
	public String wktpCd;
	/**就業時間帯コード**/
	@Column(name = "WKTM_CD")
	public String wktmCd;
	/** "直行区分---true:直行する---false:直行しない"**/
	@Column(name = "GO_STRAIGHT_ATR")
	public boolean goStraightAtr;
	/** "直帰区分---true:直帰する---false:直帰しない"**/
	@Column(name = "BACK_STRAIGHT_ATR")
	public String backStraightAtr;

	@Override
	protected Object getKey() {
		
		return this.pk;
	}
}
