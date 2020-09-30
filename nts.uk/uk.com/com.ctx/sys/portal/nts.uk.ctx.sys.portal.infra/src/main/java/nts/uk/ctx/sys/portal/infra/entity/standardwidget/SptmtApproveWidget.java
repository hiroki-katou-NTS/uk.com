package nts.uk.ctx.sys.portal.infra.entity.standardwidget;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPTMT_APPROVE_WIDGET")
public class SptmtApproveWidget extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Id
	@Column(name = "CID")
	public String companyId;

	/**
	 * 名称
	 */
	@Column(name = "TOPPAGE_PART_NAME")
	public String topPagePartName;

	/**
	 * 承認すべき申請データの表示区分
	 */
	@Column(name = "APP_DISPLAY_ATR")
	public Integer appDisplayAtr;

	/**
	 * 日別実績承認すべきデータの表示区分
	 */
	@Column(name = "DAY_DISPLAY_ATR")
	public Integer dayDisplayAtr;

	/**
	 * 月別実績承認すべきデータの表示区分
	 */
	@Column(name = "MON_DISPLAY_ATR")
	public Integer monDisplayAtr;

	/**
	 * ３６協定承認すべき申請データの表示区分
	 */
	@Column(name = "AGR_DISPLAY_ATR")
	public Integer agrDisplayAtr;

	@Override
	protected Object getKey() {
		return this.companyId;
	}
}
