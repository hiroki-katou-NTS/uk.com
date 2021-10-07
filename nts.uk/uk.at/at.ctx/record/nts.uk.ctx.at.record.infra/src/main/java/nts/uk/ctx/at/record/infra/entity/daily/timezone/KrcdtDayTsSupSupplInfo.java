package nts.uk.ctx.at.record.infra.entity.daily.timezone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * @name 日別時間帯別実績
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TS_SUP_SUPPL_INFO")
public class KrcdtDayTsSupSupplInfo extends ContractCompanyUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayTsSupSupplInfoPk pk;

	@Column(name = "SUPPL_INFO_TIME1")
	public int supplInfoTime1;
	
	@Column(name = "SUPPL_INFO_TIME2")
	public int supplInfoTime2;
	
	@Column(name = "SUPPL_INFO_TIME3")
	public int supplInfoTime3;
	
	@Column(name = "SUPPL_INFO_TIME4")
	public int supplInfoTime4;
	
	@Column(name = "SUPPL_INFO_TIME5")
	public int supplInfoTime5;
	
	@Column(name = "SUPPL_INFO_NUMBER1")
	public int supplInfoNumber1;
	
	@Column(name = "SUPPL_INFO_NUMBER2")
	public int supplInfoNumber2;
	
	@Column(name = "SUPPL_INFO_NUMBER3")
	public int supplInfoNumber3;
	
	@Column(name = "SUPPL_INFO_NUMBER4")
	public int supplInfoNumber4;
	
	@Column(name = "SUPPL_INFO_NUMBER5")
	public int supplInfoNumber5;
	
	@Column(name = "SUPPL_INFO_COMMENT1")
	public String supplInfoComment1;
	
	@Column(name = "SUPPL_INFO_COMMENT2")
	public String supplInfoComment2;
	
	@Column(name = "SUPPL_INFO_COMMENT3")
	public String supplInfoComment3;
	
	@Column(name = "SUPPL_INFO_COMMENT4")
	public String supplInfoComment4;
	
	@Column(name = "SUPPL_INFO_COMMENT5")
	public String supplInfoComment5;
	
	@Column(name = "SUPPL_INFO_CODE1")
	public String supplInfoCode1;
	
	@Column(name = "SUPPL_INFO_CODE2")
	public String supplInfoCode2;
	
	@Column(name = "SUPPL_INFO_CODE3")
	public String supplInfoCode3;
	
	@Column(name = "SUPPL_INFO_CODE4")
	public String supplInfoCode4;
	
	@Column(name = "SUPPL_INFO_CODE5")
	public String supplInfoCode5;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
