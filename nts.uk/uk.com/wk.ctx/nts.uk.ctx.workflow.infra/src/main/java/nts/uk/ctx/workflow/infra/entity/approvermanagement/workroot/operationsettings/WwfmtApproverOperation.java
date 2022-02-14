package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.operationsettings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "WWFMT_APPROVER_OPERATION")
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtApproverOperation extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 契約コード */
	@Column(name = "CONTRACT_CD")
	public String contractCd;
	
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	/** 運用モード */
	@Column(name = "OPE_MODE")
	public int opeMode;
	
	/** 利用するレベル */
	@Column(name = "LEVEL_USE_NUMBER")
	public int levelUseNumber;
	
	/** 項目1の名称 */
	@Column(name = "ITEM_1_NAME")
	public String item1Name;
	
	/** 項目2の名称 */
	@Column(name = "ITEM_2_NAME")
	public String item2Name;
	
	/** 項目3の名称 */
	@Column(name = "ITEM_3_NAME")
	public String item3Name;
	
	/** 項目4の名称 */
	@Column(name = "ITEM_4_NAME")
	public String item4Name;
	
	/** 項目5の名称 */
	@Column(name = "ITEM_5_NAME")
	public String item5Name;
	
	/** 日次確認を利用する */
	@Column(name = "CONF_DAY_USE")
	public int confDayUse;
	
	/** 月次確認を利用する */
	@Column(name = "CONF_MONTH_USE")
	public int confMonthUse;
	
	/** 手順の説明 */
	@Column(name = "PROCESS_MEMO")
	public String processMemo;
	
	/** 表示する注意内容 */
	@Column(name = "ATTENTION_MEMO")
	public String attentionMemo;
	
	@Override
	protected Object getKey() {
		return this.cid;
	}

}
