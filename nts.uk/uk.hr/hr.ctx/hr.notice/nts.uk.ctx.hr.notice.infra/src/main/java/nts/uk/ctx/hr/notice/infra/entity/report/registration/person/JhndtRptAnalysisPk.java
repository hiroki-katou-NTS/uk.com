package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JhndtRptAnalysisPk implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "RPT_LAYOUT_ID")
	public int reportLayoutID; // 届出ID
	
	@NotNull
	@Column(name = "RPT_YYYYMM")
	public int reportYearMonth; // 届出年月
	
	@NotNull
	@Column(name = "COUNT_CLASS")
	public int countClass;//カウント大区分
	
	@NotNull
	@Column(name = "COUNT_CLASS_S")
	public int countClassSmall;//カウント小区分
	
	@NotNull
	@Column(name = "CID")
	public String cid; // 会社ID

}
