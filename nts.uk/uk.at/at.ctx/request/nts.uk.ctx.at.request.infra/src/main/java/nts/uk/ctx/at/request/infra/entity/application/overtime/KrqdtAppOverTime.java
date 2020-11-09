package nts.uk.ctx.at.request.infra.entity.application.overtime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author hoangnd
 *
 */
@Entity
@Table(name = "KRQDT_APP_OVERTIME")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KrqdtAppOverTime extends ContractUkJpaEntity{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected KrqdtAppOvertimePK krqdtAppOvertimePK;
	
	@Version
	@Column(name = "EXCLUS_VER")
	private Long version;

	@Column(name = "OVERTIME_ATR")
	private int overtimeAtr;
	
	@Column(name = "WORK_TYPE_CD")
	private String workTypeCode;
	
	@Column(name = "WORK_TIME_CD")
	private String workTimeCode;
	
	@Column(name = "WORK_TIME_START1")
	private Integer workTimeStart1;
	
	@Column(name = "WORK_TIME_END1")
	private Integer workTimeEnd1;
	
	@Column(name = "WORK_TIME_START2")
	private Integer workTimeStart2;
	
	@Column(name = "WORK_TIME_END2")
	private Integer workTimeEnd2;
	
	@Column(name = "DIVERGENCE_NO")
	private Integer divergenceNo;
	
	@Column(name = "DIVERGENCE_CD")
	private String divergenceCD;
	
	@Column(name = "DIVERGENCE_REASON")
	private String divergenceReason;
	
	@Column(name = "FLEX_EXCESS_TIME")
	private Integer flexExcessTime;
	
	@Column(name = "OVERTIME_NIGHT")
	private Integer overTimeNight;
	
	@Column(name = "TOTAL_NIGHT")
	private Integer totalNight;
	
	@Column(name = "LEGAL_HD_NIGHT")
	private Integer legalHdNight;
	
	@Column(name = "NON_LEGAL_HD_NIGHT")
	private Integer nonLegalHdNight;
	
	@Column(name = "NON_LEGAL_PUBLIC_HD_NIGHT")
	private Integer nonLegalPublicHdNight;
	
	@Column(name = "BREAK_TIME_START1")
	private Integer breakTimeStart1;
	
	@Column(name = "BREAK_TIME_END1")
	private Integer breakTimeEnd1;
	
	@Column(name = "BREAK_TIME_START2")
	private Integer breakTimeStart2;
	
	@Column(name = "BREAK_TIME_END2")
	private Integer breakTimeEnd2;
	
	@Column(name = "BREAK_TIME_START3")
	private Integer breakTimeStart3;
	
	@Column(name = "BREAK_TIME_END3")
	private Integer breakTimeEnd3;
	
	@Column(name = "BREAK_TIME_START4")
	private Integer breakTimeStart4;
	
	@Column(name = "BREAK_TIME_END4")
	private Integer breakTimeEnd4;
	
	@Column(name = "BREAK_TIME_START5")
	private Integer breakTimeStart5;
	
	@Column(name = "BREAK_TIME_END5")
	private Integer breakTimeEnd5;
	
	@Column(name = "BREAK_TIME_START6")
	private Integer breakTimeStart6;
	
	@Column(name = "BREAK_TIME_END6")
	private Integer breakTimeEnd6;
	
	@Column(name = "BREAK_TIME_START7")
	private Integer breakTimeStart7;
	
	@Column(name = "BREAK_TIME_END7")
	private Integer breakTimeEnd7;
	
	@Column(name = "BREAK_TIME_START8")
	private Integer breakTimeStart8;
	
	@Column(name = "BREAK_TIME_END8")
	private Integer breakTimeEnd8;
	
	@Column(name = "BREAK_TIME_START9")
	private Integer breakTimeStart9;
	
	@Column(name = "BREAK_TIME_END9")
	private Integer breakTimeEnd9;
	
	@Column(name = "BREAK_TIME_START10")
	private Integer breakTimeStart10;
	
	@Column(name = "BREAK_TIME_END10")
	private Integer breakTimeEnd10;

	@Override
	protected Object getKey() {
		return null;
	}
	
	
}
