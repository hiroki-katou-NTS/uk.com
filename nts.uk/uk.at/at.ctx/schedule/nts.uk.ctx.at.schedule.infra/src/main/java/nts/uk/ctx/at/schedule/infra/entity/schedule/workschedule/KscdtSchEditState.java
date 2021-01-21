package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の編集状態
 * @author Hieult
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_EDIT_STATE")
@Getter
public class KscdtSchEditState extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchEditStatePK pk;
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	/**"編集状態---1:手修正(本人)---2:手修正(他人)---3:申請反映"**/
	@Column(name = "EDIT_STATE")
	public int sditState;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;
	
	// 勤務予定.編集状態
	public static KscdtSchEditState toEntity(EditStateOfDailyAttd dailyAttd, String sID, GeneralDate yMD, String cID) {
		KscdtSchEditStatePK pk = new KscdtSchEditStatePK(sID, yMD, dailyAttd.getAttendanceItemId());
		return new KscdtSchEditState(pk, cID, dailyAttd.getEditStateSetting().value);
	}
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscdtSchEditState(KscdtSchEditStatePK pk, String cid, int sditState) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.sditState = sditState;
	}
}
