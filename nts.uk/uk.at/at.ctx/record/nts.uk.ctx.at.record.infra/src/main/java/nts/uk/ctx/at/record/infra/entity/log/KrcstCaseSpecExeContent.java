package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseSpecExeContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_EXECUTION_LOG")
public class KrcstCaseSpecExeContent extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstCaseSpecExeContentPK krcstCaseSpecExeContentPK;

	@Column(name = "ORDER_NUMBER")
	public int orderNumber;

	@Column(name = "USE_CASE_NAME")
	public String useCaseName;

	@Column(name = "CAL_EXECUTION_SET_INFO_ID")
	public String calExecutionSetInfoID;

	@OneToMany(mappedBy = "executionlog", cascade = CascadeType.ALL)
	@JoinTable(name = "KRCDT_CAL_EXE_SET_INFOR")
	public List<KrcdtCalExeSetInfor> lstCalExeSetInfor;

	public KrcstCaseSpecExeContent(KrcstCaseSpecExeContentPK krcstCaseSpecExeContentPK, int orderNumber,
			String useCaseName, String calExecutionSetInfoID) {
		super();
		this.krcstCaseSpecExeContentPK = krcstCaseSpecExeContentPK;
		this.orderNumber = orderNumber;
		this.useCaseName = useCaseName;
		this.calExecutionSetInfoID = calExecutionSetInfoID;
	}

	public CaseSpecExeContent toDomain() {
		val domain = CaseSpecExeContent.createFromJavaType(this.krcstCaseSpecExeContentPK.caseSpecExeContentID,
				this.orderNumber, this.useCaseName, this.calExecutionSetInfoID);
		for (KrcdtCalExeSetInfor calExeSetInfor : lstCalExeSetInfor) {
			if (calExeSetInfor.krcdtCalExeSetInforPK.executionContent == ExecutionContent.DAILY_CREATION.value) {
				domain.setDailyCreationSetInfo(Optional.of(calExeSetInfor.toDomain()));
			} else if (calExeSetInfor.krcdtCalExeSetInforPK.executionContent == ExecutionContent.DAILY_CALCULATION.value) {
				domain.setDailyCalSetInfo(Optional.of(calExeSetInfor.toDomain()));
			} else if (calExeSetInfor.krcdtCalExeSetInforPK.executionContent == ExecutionContent.REFLRCT_APPROVAL_RESULT.value) {
				domain.setReflectApprovalSetInfo(Optional.of(calExeSetInfor.toDomain()));
			} else {
				domain.setMonlyAggregationSetInfo(Optional.of(calExeSetInfor.toDomain()));
			}
		}
		return domain;
	}

	@Override
	protected Object getKey() {
		return this.krcstCaseSpecExeContentPK;
	}

}
