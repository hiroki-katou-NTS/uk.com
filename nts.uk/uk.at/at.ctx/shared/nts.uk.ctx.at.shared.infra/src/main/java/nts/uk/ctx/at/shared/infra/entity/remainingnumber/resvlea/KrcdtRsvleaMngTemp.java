package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.TempReserveLeaveManagement;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：暫定積立年休管理データ
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_RSVLEA_MNG_TEMP")
@NoArgsConstructor
public class KrcdtRsvleaMngTemp extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtRsvleaMngTempPK PK;
	
	/** 積立年休使用日数 */
	@Column(name = "RSVLEA_USE_DAYS")
	public double reserveLeaveUseDays;
	
	/** 予定実績区分 */
	@Column(name = "SCHE_RECD_ATR")
	public int scheduleRecordAtr;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 暫定積立年休管理データ
	 */
	public TempReserveLeaveManagement toDomain(){
		
		return TempReserveLeaveManagement.of(
				this.PK.employeeId,
				this.PK.ymd,
				new ManagementDays(this.reserveLeaveUseDays),
				EnumAdaptor.valueOf(this.scheduleRecordAtr, ScheduleRecordAtr.class));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 暫定積立年休管理データ
	 */
	public void fromDomainForPersist(TempReserveLeaveManagement domain){
		
		this.PK = new KrcdtRsvleaMngTempPK(
				domain.getEmployeeId(),
				domain.getYmd());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 暫定積立年休管理データ
	 */
	public void fromDomainForUpdate(TempReserveLeaveManagement domain){
		
		this.reserveLeaveUseDays = domain.getReserveLeaveUseDays().v();
		this.scheduleRecordAtr = domain.getScheduleRecordAtr().value;
	}
}
