package nts.uk.ctx.hr.shared.infra.entity.report.registration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEDT_PRE_REFLECT_ANY_DATA")
public class PpedtPreRefectAnyData extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "HIST_ID")
	public String histId; // 新規GUIDをセット
	
	@NotNull
	@Column(name = "CONTRACT_CD")
	public String contractCd; 
	
	@NotNull
	@Column(name = "CID")
	public String companyId; // 会社ID
	
	@NotNull
	@Column(name = "CCD")
	public String companyCd; // 会社コード
	
	@Column(name = "WORK_ID")
	public Integer workId; // 業務ID
	
	@Column(name = "WORK_NAME")
	public String workName; // 業務name
	
	@Column(name = "REQUEST_FLG")
	public Integer requestFlg; //届出区分
	
	@Column(name = "REGIST_DATE")
	public GeneralDateTime regisDate; //入力日
	
	@Column(name = "ON_HOLD_FLG")
	public Integer holdFlag; //??
	
	@Column(name = "STATUS")
	public Integer status; // ステータス
	
	@Column(name = "DST_HIST_ID")
	public String dstHistId; //???
	
	@Column(name = "RELEASE_DATE")
	public GeneralDateTime releaseDate; //???
	
	@Column(name = "PID")
	public String pid; //申請者個人ID
	
	@Column(name = "SID")
	public String sid; // 申請者社員ID
	
	@Column(name = "SCD")
	public String scd; // 申請者社員コード
	
	@Column(name = "PERSON_NAME")
	public String personName; // 申請者社員名
	
	@Column(name = "RPTID")
	public int reportId; // 届出ID
	
	@Column(name = "RPTCD")
	public String reportCode; // 届出コード
	
	@Column(name = "RPT_NAME")
	public String reportName; // 届出名
	
	@Column(name = "INPUT_DATE")
	public GeneralDateTime inputDate; // 入力日

	@Override
	protected Object getKey() {
		return this.histId;
	}
	

}
