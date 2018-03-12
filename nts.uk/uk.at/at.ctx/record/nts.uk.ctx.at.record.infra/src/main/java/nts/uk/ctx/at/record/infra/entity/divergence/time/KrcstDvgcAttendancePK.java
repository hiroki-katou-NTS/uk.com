package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KRCST_DVGC_ATTENDANCE database table.
 * 
 */
@Embeddable
public class KrcstDvgcAttendancePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="[NO]")
	private long no;

	@Column(name="CID")
	private String cid;

	@Column(name="ATTENDANCE_ID")
	private long attendanceId;

	public KrcstDvgcAttendancePK() {
	}
	public long getNo() {
		return this.no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getCid() {
		return this.cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public long getAttendanceId() {
		return this.attendanceId;
	}
	public void setAttendanceId(long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcstDvgcAttendancePK)) {
			return false;
		}
		KrcstDvgcAttendancePK castOther = (KrcstDvgcAttendancePK)other;
		return 
			(this.no == castOther.no)
			&& this.cid.equals(castOther.cid)
			&& (this.attendanceId == castOther.attendanceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.no ^ (this.no >>> 32)));
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.attendanceId ^ (this.attendanceId >>> 32)));
		
		return hash;
	}
}