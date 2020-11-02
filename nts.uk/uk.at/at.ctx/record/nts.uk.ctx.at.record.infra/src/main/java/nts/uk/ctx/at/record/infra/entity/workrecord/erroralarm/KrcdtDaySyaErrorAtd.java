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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class KrcdtDaySyaErrorAtd extends ContractUkJpaEntity {

	@EmbeddedId
	public KrcdtDaySyaErrorAtdPK krcdtDaySyaErrorAtdPK;
	
	@Column(nullable = false, name = "CID")
	public String cid;
	
	@Column(nullable = false, name = "SID")
	public String sid;
	
	@Column(nullable = false, name = "PROCESSING_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate processDate;
	
	@Override
	protected Object getKey() {
		return this.krcdtDaySyaErrorAtdPK;
	}
	
//	@ManyToOne
//	@JoinColumns({ @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)})
//	public KrcdtOtkErAl erOtk;
//	
//	@ManyToOne
//	@JoinColumns({ @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)})
//	public KrcdtEmpDivErAl erDiv;
//	
//	@ManyToOne
//	@JoinColumns({ @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)})
//	public KrcdtDaySyaError erOth;
	
	public static KrcdtDaySyaErrorAtd toEntity(String id, int attendanceItemId, String cid, 
			String sid, String ccd, GeneralDate processDate){
		KrcdtDaySyaErrorAtd krcdtDaySyaErrorAtd = new KrcdtDaySyaErrorAtd();
		KrcdtDaySyaErrorAtdPK krcdtDaySyaErrorAtdPK = new KrcdtDaySyaErrorAtdPK(id, attendanceItemId);
		krcdtDaySyaErrorAtd.krcdtDaySyaErrorAtdPK = krcdtDaySyaErrorAtdPK;
		krcdtDaySyaErrorAtd.cid = cid;
		krcdtDaySyaErrorAtd.sid = cid;
		krcdtDaySyaErrorAtd.processDate = processDate;
		return krcdtDaySyaErrorAtd;
	}
}
