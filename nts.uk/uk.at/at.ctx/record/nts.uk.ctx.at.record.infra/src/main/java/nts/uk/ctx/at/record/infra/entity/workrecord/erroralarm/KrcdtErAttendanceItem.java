package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_ER_ATTENDANCE_ITEM")
public class KrcdtErAttendanceItem extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtErAttendanceItemPK krcdtErAttendanceItemPK;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.krcdtErAttendanceItemPK;
	}
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)})
	public KrcdtSyainDpErList erAttendanceItem;
	
	public static KrcdtErAttendanceItem toEntity(String id, int attendanceItemId){
		KrcdtErAttendanceItem krcdtErAttendanceItem = new KrcdtErAttendanceItem();
		KrcdtErAttendanceItemPK krcdtErAttendanceItemPK = new KrcdtErAttendanceItemPK(id, attendanceItemId);
		krcdtErAttendanceItem.krcdtErAttendanceItemPK = krcdtErAttendanceItemPK;
		return krcdtErAttendanceItem;
	}
}
