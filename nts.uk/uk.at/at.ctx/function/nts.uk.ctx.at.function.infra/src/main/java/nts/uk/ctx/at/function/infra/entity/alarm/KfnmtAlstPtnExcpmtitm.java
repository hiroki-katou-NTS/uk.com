package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_PTN_EXCPMTITM")
public class KfnmtAlstPtnExcpmtitm extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlstPtnExcpmtitmPK pk;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false)})
	public KfnmtAlstPtnExcpmt alarmPerSet;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KfnmtAlstPtnExcpmtitm(KfnmtAlstPtnExcpmtitmPK pk) {
		super();
		this.pk = pk;
	}
	
	public void fromEnity(KfnmtAlstPtnExcpmtitm newEntity) {
		this.pk.roleID = newEntity.pk.roleID;
	}
}
