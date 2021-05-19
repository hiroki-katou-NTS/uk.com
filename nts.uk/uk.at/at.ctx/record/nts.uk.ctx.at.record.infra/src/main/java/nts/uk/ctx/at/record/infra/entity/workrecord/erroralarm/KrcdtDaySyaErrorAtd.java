package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class KrcdtDaySyaErrorAtd extends UkJpaEntity {

	@EmbeddedId
	public KrcdtErAttendanceItemPK krcdtErAttendanceItemPK;
	
	@Column(nullable = false, name = "CID")
	public String cid;
	
	@Column(nullable = false, name = "SID")
	public String sid;
	
	@Column(nullable = false, name = "CONTRACT_CD")
	public String ccd;
	
	@Column(nullable = false, name = "PROCESSING_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate processDate;
	
	@Override
	protected Object getKey() {
		return this.krcdtErAttendanceItemPK;
	}
	
	public KrcdtDaySyaErrorAtd(String id, int attendanceItemId, String cid, 
			String sid, String ccd, GeneralDate processDate) {
		
		this.krcdtErAttendanceItemPK = new KrcdtErAttendanceItemPK(id, attendanceItemId);
		this.ccd = ccd;
		this.cid = cid;
		this.sid = cid;
		this.processDate = processDate;
	}
}
