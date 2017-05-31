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
public class KmnmtWorkType extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
    public KmnmtWorkTypePK kmnmtWorkTypePK;
	
	/*ソート順*/
	@Column(name = "SORT_ORDER")
	public String sortOrder;
	/*勤務種類記号名*/
	@Column(name = "SYMBOLIC_NAME")
	public String symbolicName;
	/*勤務種類名称*/
	@Column(name = "NAME")
	public String name;
	/*勤務種類略名*/
	@Column(name = "ABBREVIATION_NAME")
	public String abbreviationName;
	/*勤務種類備考*/
	@Column(name = "MEMO")
	public String memo;
	/*使用区分*/
	@Column(name = "USE_ATR")
	public String useAtr;
	
	@Override
	protected Object getKey() {
		return kmnmtWorkTypePK;
	}

}
