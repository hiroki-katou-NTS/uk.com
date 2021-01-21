package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 社員の勤務種別
 * @author Trung Tran
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUS_TYPE_HIST_ITEM")
public class KrcmtBusinessTypeOfEmployee extends ContractUkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtBusinessTypeOfEmployeePK krcmtBusinessTypeOfEmployeePK;
	
	/** 社員ID */
	@Column(name = "SID")
	public String sId;
	
	/** 勤務種別コード */
	@Column(name = "BUSINESS_TYPE_CD")
	public String businessTypeCode;
	
	@Override
	protected Object getKey() {
		return krcmtBusinessTypeOfEmployeePK;
	}

}
