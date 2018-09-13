package nts.uk.ctx.at.record.infra.entity.monthly.remarks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
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
public class KrcdtRemarksMonthlyRecord extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** プライマリキー */
	@EmbeddedId
	public KrcdtRemarksMonthlyRecordPK krcdtRemarksMonthlyRecordPK;
	
	/** 締め日 */
	@Column(name = "CLOSURE_DAY")
	public GeneralDate closeDay;
	
	/** 年月 */
	@Column(name = "REMARKS_YM")
	public int remarksYM;
	
	/** 期間 - start */
	@Column(name = "STR_YMD")
	public GeneralDate startYmd;
	
	/** 期間 - end */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;

	/** 備考 */
	@Column(name = "RECORD_REMARKS")
	public String recordRemarks;
	
	@Override
	protected Object getKey() {
		return krcdtRemarksMonthlyRecordPK;
	}

}
