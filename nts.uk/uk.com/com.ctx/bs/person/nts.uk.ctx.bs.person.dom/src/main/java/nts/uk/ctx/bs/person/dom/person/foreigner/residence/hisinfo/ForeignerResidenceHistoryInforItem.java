/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.foreigner.residence.hisinfo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/**
 * domain 外国人在留履歴情報
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Stateless
public class ForeignerResidenceHistoryInforItem extends AggregateRoot {

	@Inject
	private ForeignerResidenceRepository repo;
	
	
	// 個人ID
	String pid;

	// 交付年月日
	GeneralDateTime issueDate;

	// 履歴ID
	String hisId;

	// 在留資格_ID
	Long statusOfResidenceID;

	// 在留資格
	String statusOfResidenceCode;

	// 在留資格_名称
	String statusOfResidenceName;

	// 在留期間_ID
	Integer periodOfStayID;

	// 在留期間_コード
	String periodOfStayCode;

	// 在留期間_名称
	String periodOfStayName;

	// 在留許可番号
	String numberResidencePermit;

	// 資格外活動許可
	PerUnqualifiedActivity perUnqualifiedActivity;

	// 届出事業所外就労区分
	ReportWorkOutside reportWorkOutside;

	//国籍_ID
	Integer nationalityID;

	// 国籍_コード
	String nationality_Code;

	// 国籍_名称
	String nationality_Name;
	
	ListForeignerResidence listForeignerResidence;
	
	// 外国人在留履歴情報をロードする
	public ListForeignerResidence getListForeignerResidenceHistoryInforItem(List<String> listPID, GeneralDateTime baseDate) {
		// ドメイン [外国人在留履歴情報] を取得する(lấy domain[thông tin lịch sử cư trú của người
		// nước ngoài])
		List<ForeignerResidenceHistoryInforItem> listDomain = repo.getListForeignerResidenceHistoryInforItem(listPID,
				baseDate);

		this.listForeignerResidence = new ListForeignerResidence(listDomain, listPID);

		return this.listForeignerResidence;
	}
	
	// 外国人在留履歴情報の取得
	public ForeignerResidenceHistoryInforItem getForeignerResidenceHistoryInforItem(String pId, GeneralDateTime baseDate) {
		Optional<ForeignerResidenceHistoryInforItem> domainOpt = this.listForeignerResidence.listForeignerResidenceHistoryInforItem
																	   .stream().filter(e -> e.pid.equals(pId)).findFirst();
		if (domainOpt.isPresent()) {
			return domainOpt.get();
		}else {
			
			List<ForeignerResidenceHistoryInforItem> domain = repo.getListForeignerResidenceHistoryInforItem(Arrays.asList(pId),baseDate);
			if (domain.isEmpty()) {
				return null;
			}
			return domain.get(0);
		}
	}

	// 社員IDリストより外国人在留履歴情報をロードする
	public List<ForeignerResidenceHistoryInforItem> getListForeignerResidenceHistoryInforItem(List<String> listSID) {
		
		// 社員IDを個人IDへ変換する(chuyen employeID thanh PersonID)
		//input
		//・＜List＞社員ID // employeeID
		//output
		//・list<個人社員基本情報＞// PersonEmployeeBasicInfo
		
		
		// Đang chờ QA6
		
		
		return null;
	}

	// 社員IDより外国人在留履歴情報を取得する
	public Optional<ForeignerResidenceHistoryInforItem> getListForeignerResidenceHistoryInforItem(String sid) {
		// Đang chờ QA6
		return null;
	}

	public ForeignerResidenceHistoryInforItem(String pid, GeneralDateTime issueDate, String hisId,
			Long statusOfResidenceID, String statusOfResidenceCode, String statusOfResidenceName,
			Integer periodOfStayID, String periodOfStayCode, String periodOfStayName, String numberResidencePermit,
			PerUnqualifiedActivity perUnqualifiedActivity, ReportWorkOutside reportWorkOutside, Integer nationalityID,
			String nationality_Code, String nationality_Name) {
		super();
		this.pid = pid;
		this.issueDate = issueDate;
		this.hisId = hisId;
		this.statusOfResidenceID = statusOfResidenceID;
		this.statusOfResidenceCode = statusOfResidenceCode;
		this.statusOfResidenceName = statusOfResidenceName;
		this.periodOfStayID = periodOfStayID;
		this.periodOfStayCode = periodOfStayCode;
		this.periodOfStayName = periodOfStayName;
		this.numberResidencePermit = numberResidencePermit;
		this.perUnqualifiedActivity = perUnqualifiedActivity;
		this.reportWorkOutside = reportWorkOutside;
		this.nationalityID = nationalityID;
		this.nationality_Code = nationality_Code;
		this.nationality_Name = nationality_Name;
	}
	
	
}
