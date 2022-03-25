package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 応援の同一打刻の判断基準 entity
 * @author laitv
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_SUPPORT_STAMP_SET")
public class KrcmtSupportStampSet extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/// 会社ID 
	@Id
	@Column(name = "CID")
	public String cid;

	// 同一打刻とみなす範囲  
	@Column(name = "SAME_STAMP_RANGE_IN_MINUTES")
	public int sameStampRanceInMinutes;

	@Override
	protected Object getKey() {
		return cid;
	}
	
	public static KrcmtSupportStampSet convert(JudgmentCriteriaSameStampOfSupport domain) {
		
		KrcmtSupportStampSet entity = new KrcmtSupportStampSet();
		
		entity.cid = domain.getCid().toString();
		entity.sameStampRanceInMinutes = domain.getSameStampRanceInMinutes().v();		
		return entity;
	}
	
}
