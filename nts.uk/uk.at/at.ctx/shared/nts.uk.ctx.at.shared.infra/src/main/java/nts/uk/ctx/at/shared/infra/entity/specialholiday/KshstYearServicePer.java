package nts.uk.ctx.at.shared.infra.entity.specialholiday;

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
@Table(name = "KSHST_YEAR_SERVICE_PER")
public class KshstYearServicePer extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstYearServicePerPK kshstYearServicePerPK;
		
		/* 勤続年数基名称 */
		@Column(name = "YEAR_SERVICE_NAME")
		public String yearServiceName;
		
		/* 勤続年数基準日 */
		@Column(name = "YEAR_SERVICE_CLS")
		public int yearServiceCls;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}