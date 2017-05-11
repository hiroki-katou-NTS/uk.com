package nts.uk.ctx.at.record.infra.entity.divergencetime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Setter
@Getter
@Entity
@Table(name="KMNMT_ATTENDANCE_ITEM")
@AllArgsConstructor
@NoArgsConstructor
public class KmnmtDivergenceItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
    public KmnmtDivergenceItemPK kmnmtDivergenceItemPK;
	/*名称*/
	@Column(name = "NAME")
	public String divName;
	/*表示番号*/
	@Column(name = "DISPLAY_NUMBER")
	public int displayNumber;
	/*使用区分*/
	@Column(name = "USE_ATR")
	public int useAtr;
	/*属性*/
	@Column(name = "ATTENDANCE_ATR")
	public int attendanceAtr;
	@Override
	protected Object getKey() {
		return null;
	}
}
