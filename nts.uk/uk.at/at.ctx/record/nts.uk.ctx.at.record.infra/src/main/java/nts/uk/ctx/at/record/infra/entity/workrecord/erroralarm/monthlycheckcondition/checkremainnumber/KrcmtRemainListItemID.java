package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
//import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndexpiptchk;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity      
@Table(name = "KRCMT_ALST_CHKMON_UDREMSP")
public class KrcmtRemainListItemID extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtRemainListItemIDPK krcmtRemainListItemIDPK;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtCheckRemainNumberMon krcmtCheckRemainNumberMon;
	
	@Override
	protected Object getKey() {
		return krcmtRemainListItemIDPK;
	}

	public KrcmtRemainListItemID(KrcmtRemainListItemIDPK krcmtRemainListItemIDPK) {
		super();
		this.krcmtRemainListItemIDPK = krcmtRemainListItemIDPK;
	}
	
	public static KrcmtRemainListItemID toEntity(String errorAlarmCheckID,int itemID) {
		return new KrcmtRemainListItemID(
					new KrcmtRemainListItemIDPK(errorAlarmCheckID,itemID));
	}
	
	public static List<KrcmtRemainListItemID> toEntity(String errorAlarmCheckID,List<Integer> listItemID) {
		List<KrcmtRemainListItemID> listKrcmtRemainListItemID = new ArrayList<>();
		for(int itemID : listItemID) {
			listKrcmtRemainListItemID.add(KrcmtRemainListItemID.toEntity(errorAlarmCheckID, itemID));
		}
		return listKrcmtRemainListItemID;
	}
	
	
	

}
