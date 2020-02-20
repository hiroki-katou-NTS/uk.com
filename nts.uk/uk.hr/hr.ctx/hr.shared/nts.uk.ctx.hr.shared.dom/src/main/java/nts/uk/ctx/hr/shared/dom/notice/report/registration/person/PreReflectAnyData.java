package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreReflectAnyData extends DomainObject{
	
	// 新規GUIDをセット
	private String histId;
	
	// 会社ID
	private String companyId;
	
	// 会社コード
	private String companyCd;
	
	// 業務ID
	private Integer workId;
	
	//届出区分
	private Integer requestFlg;
	
	//入力日
	private GeneralDateTime regisDate;
	
	// ステータス
	private int status;
	
	//申請者個人ID
	private String pid;
	
	// 申請者社員ID
	private String sid;
	
	// 申請者社員コード
	private String scd;
	
	// 申請者社員名
	private String personName;
	
	// 届出ID
	private Integer reportId;
	
	// 届出コード
	private String reportCode;
	
	// 届出名
	private String reportName;
	
	// 入力日
	private GeneralDateTime inputDate;

}
