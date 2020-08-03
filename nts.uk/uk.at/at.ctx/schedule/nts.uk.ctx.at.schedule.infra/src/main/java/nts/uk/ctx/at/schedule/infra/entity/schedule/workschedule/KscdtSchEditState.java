package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の編集状態
 * @author Hieult
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_EDIT_STATE")
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
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.pk;
	}
	

}
