package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TR_REMOTE_UPDATE")
public class KrcdtTrRemoteUpdate extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTrRemoteUpdatePK pk;

	/**
	 * 機種名
	 */
	@Column(name = "TR_NAME")
	public String empInfoTerName;

	/**
	 * ROMバージョン
	 */
	@Column(name = "ROM_VERSION")
	public String romVersion;

	/**
	 * 機種区分
	 */
	@Column(name = "RECORD_TYPE")
	public int modelEmpInfoTer;

	/**
	 * 更新の値
	 */
	@Column(name = "UPDATE_VALUE")
	public String updateValue;

	@Override
	protected Object getKey() {
		return pk;
	}

	public String getGroupByString() {
		return this.pk.timeRecordCode + this.empInfoTerName + this.romVersion + this.modelEmpInfoTer;
	}
}
