package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.Year36OverMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 36年間超過月
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_YEAR36_OVER_MONTH")
public class KrqdtYear36OverMonth extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrqdtYear36OverMonthPk year36OverMonthPk;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtAppOvertimeDetail appOvertimeDetail;

	@Override
	protected Object getKey() {
		return year36OverMonthPk;
	}

	public Year36OverMonth toDomain() {
		return new Year36OverMonth(this.year36OverMonthPk.cid, this.year36OverMonthPk.appId,
				this.year36OverMonthPk.overMonth);
	}

	public static KrqdtYear36OverMonth toEntity(Year36OverMonth domain) {
		return new KrqdtYear36OverMonth(
				new KrqdtYear36OverMonthPk(domain.getCid(), domain.getAppId(), domain.getOverMonth().v()));
	}

	public KrqdtYear36OverMonth(KrqdtYear36OverMonthPk year36OverMonthPk) {
		super();
		this.year36OverMonthPk = year36OverMonthPk;
	}

}
