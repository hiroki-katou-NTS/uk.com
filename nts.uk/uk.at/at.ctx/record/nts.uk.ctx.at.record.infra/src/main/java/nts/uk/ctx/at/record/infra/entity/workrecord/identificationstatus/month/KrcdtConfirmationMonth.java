package nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.month;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * @author thanhnx
 * 月の本人確認
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_CONFIRMATION_MONTH")
public class KrcdtConfirmationMonth extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtConfirmationMonthPK krcdtConfirmationMonthPK;

	@Column(name = "IDENTIFY_DATE")
	public GeneralDate indentifyYmd;
	
	public ConfirmationMonth toDomain(){
		return new ConfirmationMonth(new CompanyId(this.krcdtConfirmationMonthPK.companyID),
				this.krcdtConfirmationMonthPK.employeeId, ClosureId.valueOf(this.krcdtConfirmationMonthPK.closureId),
				new ClosureDate(this.krcdtConfirmationMonthPK.closureDay, (this.krcdtConfirmationMonthPK.isLastDay == 1)), new YearMonth(this.krcdtConfirmationMonthPK.processYM), this.indentifyYmd);
	}
	
	@Override
	protected Object getKey() {
		return this.krcdtConfirmationMonthPK;
	}

}
