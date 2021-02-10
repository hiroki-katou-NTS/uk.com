package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_ERAL_OTK_ATD")
public class KrcdtErOtkAtd extends KrcdtErAttendanceItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public KrcdtErOtkAtd(String id, int attendanceItemId, String cid, 
			String sid, String ccd, GeneralDate processDate) {
		super(id, attendanceItemId, cid, sid, ccd, processDate);
	}
}
