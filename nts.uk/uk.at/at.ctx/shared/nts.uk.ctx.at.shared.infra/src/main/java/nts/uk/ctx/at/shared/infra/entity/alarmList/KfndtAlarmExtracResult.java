package nts.uk.ctx.at.shared.infra.entity.alarmList;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * Table : アラームリストパターンの抽出結果 
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNDT_ALARM_EXTRAC_RESULT")
public class KfndtAlarmExtracResult extends UkJpaEntity implements Serializable {
	/**主キー	 */
	@EmbeddedId
	public KfndtAlarmExtracResultPK pk;
	/**アラーム値日付：終了日	 */
	@Column (name = "END_DATE")
	public GeneralDate endDate;
	/**アラームリストパターン名称	 */
	@Column (name = "PATTERN_NAME")
	public String patternName;

	/**	アラーム項目 */
	@Column (name = "ALARM_ITEM_NAME")
	public String alarmItemName;
	/**	アラーム内容 */
	@Column (name = "ALARM_CONTENT")
	public String alarmContent;
	/**	発生日時 */
	@Column (name = "RUN_TIME")
	public GeneralDateTime runTime;
	/**	職場ID */
	@Column (name = "WORKPLACE_ID")
	public String wpId;
	/**	コメント */
	@Column (name = "COMMENT")
	public String comment;
	/**	チェック対象値 */
	@Column (name = "CHECK_VALUE")
	public String checkValue;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

}
