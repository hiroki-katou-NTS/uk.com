package nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 * 本人確認
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_CONFIRMATION_DAY")
public class KrcdtIdentificationStatus extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtIdentificationStatusPK krcdtIdentificationStatusPK;
	
	@Column(name = "INDENTIFICATION_YMD")
	public GeneralDate indentificationYmd;

	@Override
	protected Object getKey() {
		return this.krcdtIdentificationStatusPK;
	}
	
	public Identification toDomain() {
		return new Identification(
				this.krcdtIdentificationStatusPK.companyID,
				this.krcdtIdentificationStatusPK.employeeId,
				this.krcdtIdentificationStatusPK.processingYmd,
				this.indentificationYmd
				);
	}
	
	public static KrcdtIdentificationStatus toEntity(Identification entity) {
		return new KrcdtIdentificationStatus(
				new KrcdtIdentificationStatusPK(
						entity.getCompanyID(),
						entity.getEmployeeId(),
						entity.getProcessingYmd()),
				entity.getIndentificationYmd()
				);
	}
}
