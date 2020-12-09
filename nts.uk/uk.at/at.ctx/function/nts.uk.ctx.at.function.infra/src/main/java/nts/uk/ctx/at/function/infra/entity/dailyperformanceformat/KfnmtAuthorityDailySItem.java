package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author anhdt
 * 
 * 日次表示項目シート一覧
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_FORM_S_DAY_ITEM")
public class KfnmtAuthorityDailySItem extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAuthorityDailySItemPK kfnmtAuthorityDailyMobileItemPK;

	/**
	 * 並び順
	 */
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Override
	protected Object getKey() {
		return this.kfnmtAuthorityDailyMobileItemPK;
	}
}
