package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.mastercheck;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.ErrorAlarmAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.ErrorAlarmMessageMSTCHK;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_MSTCHK_FIXEDEXTRIT")
@NoArgsConstructor
@Getter @Setter
public class KrcmtMasterCheckFixedExtractItem extends UkJpaEntity {

	@Id
	@Column(name = "NO")
	private int no;
		
	@Column(name = "FIRST_MESSAGE_DIS")
	private String initMessage;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ERALARM_ATR")
	private int elAlAtr;

	@Override
	protected Object getKey() {
		return this.no;
	}
	
	public MasterCheckFixedExtractItem toDomain() {
		return new MasterCheckFixedExtractItem(
				EnumAdaptor.valueOf(this.no, MasterCheckFixedCheckItem.class),
				new ErrorAlarmMessageMSTCHK(this.initMessage),
				EnumAdaptor.valueOf(this.elAlAtr, ErrorAlarmAtr.class),
				new FixConWorkRecordName(this.name));
		
	}
}
