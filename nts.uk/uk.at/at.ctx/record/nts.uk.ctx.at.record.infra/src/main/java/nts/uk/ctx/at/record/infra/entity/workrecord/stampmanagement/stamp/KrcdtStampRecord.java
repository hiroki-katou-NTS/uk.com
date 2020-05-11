package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author ThanhNX
 *
 *         打刻記録
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_STAMP_RECORD")
public class KrcdtStampRecord extends ContractUkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtStampRecordPk pk;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
	
	/**
	 * 打刻区分 0:False(通常打刻しない) 1:True(通常打刻する)
	 */
	@Basic(optional = false)
	@Column(name = "STAMP_ART")
	public boolean stampArt;

	/**
	 * 予約区分 0:なし 1:予約 2:予約取消
	 */
	@Basic(optional = false)
	@Column(name = "RESERVATON_ART")
	public int reservationArt;

	/**
	 * 就業情報端末コード
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TERMINAL_INFO_CD")
	public Integer workTerminalInfoCd;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcdtStampRecord toUpdateEntity(StampRecord domain) {
		this.stampArt = domain.isStampArt();
		this.reservationArt = domain.getRevervationAtr().value;
		this.workTerminalInfoCd = domain.getEmpInfoTerCode().isPresent() ? domain.getEmpInfoTerCode().get().v() : null;
		return this;
	}
}
