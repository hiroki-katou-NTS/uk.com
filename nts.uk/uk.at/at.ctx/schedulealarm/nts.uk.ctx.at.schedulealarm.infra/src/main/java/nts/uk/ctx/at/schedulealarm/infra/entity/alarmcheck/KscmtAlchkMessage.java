package nts.uk.ctx.at.schedulealarm.infra.entity.alarmcheck;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckMessage;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.SubCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 勤務予定のアラームチェック条件・任意のメッセージ
 * @author lan_lt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_MESSAGE")
public class KscmtAlchkMessage extends ContractUkJpaEntity  {
	
	public static final JpaEntityMapper<KscmtAlchkMessage> MAPPER = new JpaEntityMapper<>(KscmtAlchkMessage.class);
	
	@EmbeddedId
	public KscmtAlchkMessagePk pk;
	
	/** 任意のメッセージ */
	@Column(name = "MESSAGE")
	public String message;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static KscmtAlchkMessage toEntity(String cid, 
			AlarmCheckConditionScheduleCode code, SubCode subCode, AlarmCheckMessage message) {
		val pk = new KscmtAlchkMessagePk(cid, code.v(), subCode.v());
		return new KscmtAlchkMessage(pk,  message.v());
	}

}
