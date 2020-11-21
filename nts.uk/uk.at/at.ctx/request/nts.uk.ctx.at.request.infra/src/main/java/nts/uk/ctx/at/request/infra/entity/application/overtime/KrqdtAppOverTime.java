package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@Entity
@Table(name = "KRQDT_APP_OVERTIME")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppOverTime extends ContractUkJpaEntity implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtAppOvertimePK krqdtAppOvertimePK;
	
	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	@Column(name = "OVERTIME_ATR")
	public Integer overtimeAtr;
	
	@Column(name = "WORK_TYPE_CD")
	public String workTypeCode;
	
	@Column(name = "WORK_TIME_CD")
	public String workTimeCode;
	
	@Column(name = "WORK_TIME_START1")
	public Integer workTimeStart1;
	
	@Column(name = "WORK_TIME_END1")
	public Integer workTimeEnd1;
	
	@Column(name = "WORK_TIME_START2")
	public Integer workTimeStart2;
	
	@Column(name = "WORK_TIME_END2")
	public Integer workTimeEnd2;
	
	@Column(name = "DIVERGENCE_NO1")
	public Integer divergenceNo1;
	
	@Column(name = "DIVERGENCE_CD1")
	public String divergenceCD1;
	
	@Column(name = "DIVERGENCE_REASON1")
	public String divergenceReason1;
	
	@Column(name = "DIVERGENCE_NO2")
	public Integer divergenceNo2;
	
	@Column(name = "DIVERGENCE_CD2")
	public String divergenceCD2;
	
	@Column(name = "DIVERGENCE_REASON2")
	public String divergenceReason2;
	
	@Column(name = "FLEX_EXCESS_TIME")
	public Integer flexExcessTime;
	
	@Column(name = "OVERTIME_NIGHT")
	public Integer overTimeNight;
	
	@Column(name = "TOTAL_NIGHT")
	public Integer totalNight;
	
	@Column(name = "LEGAL_HD_NIGHT")
	public Integer legalHdNight;
	
	@Column(name = "NON_LEGAL_HD_NIGHT")
	public Integer nonLegalHdNight;
	
	@Column(name = "NON_LEGAL_PUBLIC_HD_NIGHT")
	public Integer nonLegalPublicHdNight;
	
	@Column(name = "BREAK_TIME_START1")
	public Integer breakTimeStart1;
	
	@Column(name = "BREAK_TIME_END1")
	public Integer breakTimeEnd1;
	
	@Column(name = "BREAK_TIME_START2")
	public Integer breakTimeStart2;
	
	@Column(name = "BREAK_TIME_END2")
	public Integer breakTimeEnd2;
	
	@Column(name = "BREAK_TIME_START3")
	public Integer breakTimeStart3;
	
	@Column(name = "BREAK_TIME_END3")
	public Integer breakTimeEnd3;
	
	@Column(name = "BREAK_TIME_START4")
	public Integer breakTimeStart4;
	
	@Column(name = "BREAK_TIME_END4")
	public Integer breakTimeEnd4;
	
	@Column(name = "BREAK_TIME_START5")
	public Integer breakTimeStart5;
	
	@Column(name = "BREAK_TIME_END5")
	public Integer breakTimeEnd5;
	
	@Column(name = "BREAK_TIME_START6")
	public Integer breakTimeStart6;
	
	@Column(name = "BREAK_TIME_END6")
	public Integer breakTimeEnd6;
	
	@Column(name = "BREAK_TIME_START7")
	public Integer breakTimeStart7;
	
	@Column(name = "BREAK_TIME_END7")
	public Integer breakTimeEnd7;
	
	@Column(name = "BREAK_TIME_START8")
	public Integer breakTimeStart8;
	
	@Column(name = "BREAK_TIME_END8")
	public Integer breakTimeEnd8;
	
	@Column(name = "BREAK_TIME_START9")
	public Integer breakTimeStart9;
	
	@Column(name = "BREAK_TIME_END9")
	public Integer breakTimeEnd9;
	
	@Column(name = "BREAK_TIME_START10")
	public Integer breakTimeStart10;
	
	@Column(name = "BREAK_TIME_END10")
	public Integer breakTimeEnd10;
	
	@OneToMany(targetEntity = KrqdtOvertimeInput.class, mappedBy = "appOvertime", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KRQDT_APP_OVERTIME_INPUT")
	public List<KrqdtOvertimeInput> overtimeInputs;
	
	@OneToOne(targetEntity = KrqdtAppOvertimeDetail.class, mappedBy = "appOvertime", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KRQDT_APP_OVERTIME_DETAIL")
	public KrqdtAppOvertimeDetail appOvertimeDetail;

	@Override
	protected Object getKey() {
		return krqdtAppOvertimePK;
	}
	
	
}
