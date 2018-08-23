package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.Year36OverMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 時間外時間の詳細
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_OVERTIME_DETAIL")
public class KrqdtAppOvertimeDetail extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrqdtAppOvertimeDetailPk appOvertimeDetailPk;

	/**
	 * 申請時間
	 */
	@Basic(optional = false)
	@Column(name = "APPLICATION_TIME")
	public int applicationTime;

	/**
	 * 年月
	 */
	@Basic(optional = false)
	@Column(name = "YEAR_MONTH")
	public int yearMonth;

	/**
	 * 実績時間
	 */
	@Basic(optional = false)
	@Column(name = "ACTUAL_TIME")
	public int actualTime;

	/**
	 * 36時間
	 */
	@Basic(optional = false)
	@Column(name = "TIME36")
	public int time36;

	@OneToMany(targetEntity = KrqdtYear36OverMonth.class, mappedBy = "appOvertimeDetail", cascade = CascadeType.ALL)
	@JoinTable(name = "KRQDT_YEAR36_OVER_MONTH")
	private List<KrqdtYear36OverMonth> year36OverMonth;
	
	/**
	 * 36年間超過回数
	 */
	@Basic(optional = false)
	@Column(name = "NUM_OF_YEAR36_OVER")
	public int numOfYear36Over;

	@OneToOne
	@PrimaryKeyJoinColumns({ 
		@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
		@PrimaryKeyJoinColumn(name = "APP_ID", referencedColumnName = "APP_ID") 
	})
	public KrqdtAppOvertime appOvertime;

	@Override
	protected Object getKey() {
		return appOvertimeDetailPk;
	}

	public AppOvertimeDetail toDomain() {
		return new AppOvertimeDetail(this.appOvertimeDetailPk.cid, this.appOvertimeDetailPk.appId, this.applicationTime,
				this.yearMonth, this.actualTime, this.time36,
				this.year36OverMonth.stream().map(x -> x.toDomain().getOverMonth().v()).collect(Collectors.toList()),
				this.numOfYear36Over);
	}

	public static KrqdtAppOvertimeDetail toEntity(Optional<AppOvertimeDetail> domainOtp) {
		if (!domainOtp.isPresent()) {
			return null;
		}
		AppOvertimeDetail domain = domainOtp.get();
		return new KrqdtAppOvertimeDetail(new KrqdtAppOvertimeDetailPk(domain.getCid(), domain.getAppId()),
				domain.getApplicationTime().v(), domain.getYearMonth().v(), domain.getActualTime().v(),
				domain.getTime36().v(), domain.getYear36OverMonth(), domain.getNumOfYear36Over().v());
	}

	public KrqdtAppOvertimeDetail(KrqdtAppOvertimeDetailPk appOvertimeDetailPk, int applicationTime, int yearMonth,
			int actualTime, int time36, List<Year36OverMonth> year36OverMonth, int numOfYear36Over) {
		super();
		this.appOvertimeDetailPk = appOvertimeDetailPk;
		this.applicationTime = applicationTime;
		this.yearMonth = yearMonth;
		this.actualTime = actualTime;
		this.time36 = time36;
		this.year36OverMonth = year36OverMonth.stream().map(x -> KrqdtYear36OverMonth.toEntity(x))
				.collect(Collectors.toList());
		this.numOfYear36Over = numOfYear36Over;
	}

}
