package nts.uk.ctx.at.record.infra.entity.monthly.remarks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemain;
import nts.uk.ctx.at.record.dom.monthly.remarks.RecordRemarks;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author phongtq
 *
 */
@Entity
@Table(name = "KRCDT_REMARK_MONTHRECORD")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KrcdtRemarksMonthlyRecord extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** プライマリキー */
	@EmbeddedId
	public KrcdtRemarksMonthlyRecordPK recordPK;
	
	
	/** 期間 - start */
	@Column(name = "STR_YMD")
	public GeneralDate startYmd;
	
	/** 備考 */
	@Column(name = "RECORD_REMARKS")
	public String recordRemarks;
	
	/** 期間 - end */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;
	
	
	@Override
	protected Object getKey() {
		return recordPK;
	}
	
public RemarksMonthlyRecord toDomain(){
		
		return new RemarksMonthlyRecord(
				this.recordPK.employeeId,
				EnumAdaptor.valueOf(this.recordPK.closureId, ClosureId.class),
				this.recordPK.remarksNo,
				new YearMonth(this.recordPK.yearMonth),
				new ClosureDate(this.recordPK.closureDay, (true)),
				new DatePeriod(this.startYmd, 
						       this.endYmd),
				new RecordRemarks(this.recordRemarks)
				);
	}

public void toEntityCareRemainData(RemarksMonthlyRecord domain){
	if (domain == null) return;
	this.startYmd = domain.getRemarksPeriod().start();
	this.recordRemarks = domain.getRecordRemarks().v();
	this.endYmd = domain.getRemarksPeriod().end();
}

public void deleteRemarksMonthlyRecord(){
	this.recordRemarks = null;
}

}
