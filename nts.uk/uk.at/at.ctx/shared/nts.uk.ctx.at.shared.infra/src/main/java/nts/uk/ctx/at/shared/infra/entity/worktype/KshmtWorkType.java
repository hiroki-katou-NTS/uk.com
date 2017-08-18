package nts.uk.ctx.at.shared.infra.entity.worktype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KMNMT_WORK_TYPE")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkType extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
    public KshmtWorkTypePK kmnmtWorkTypePK;
	
	/*勤務種類名称*/
	@Column(name = "NAME")
	public String name;
	/*勤務種類略名*/
	@Column(name = "ABNAME")
	public String abbreviationName;
	/*勤務種類略名*/
	@Column(name = "SYNAME")
	public String symbolicName;
	/*廃止区分*/
	@Column(name = "ABOLISH_ATR")
	public int abolishAtr;
	/*勤務種類備考*/
	@Column(name = "MEMO")
	public String memo;
	/*並び順*/
	@Column(name = "DISPLAYORDER")
	public int displayOrder;
	/*勤務の単位*/
	@Column(name = "WORK_ATR")
	public int workAtr;
	/*  */
	@Column(name = "ONE_DAY_CLS")
	public int oneDayCls;
	/*   */
	@Column(name = "AFTERNOON_CLS")
	public int afternoonCls;
	/*   */
	@Column(name = "MORNING_CLS")
	public int morningCls;
	
	@Override
	protected Object getKey() {
		return kmnmtWorkTypePK;
	}

}
