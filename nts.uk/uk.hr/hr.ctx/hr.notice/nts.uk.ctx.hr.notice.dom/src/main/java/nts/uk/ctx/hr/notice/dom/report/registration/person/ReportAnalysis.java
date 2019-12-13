/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.CountClass;

/**
 * @author laitv
 * Domain 届出分析
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportAnalysis {

	String cid; //会社ID
	int reportYearMonth; //届出年月
	int  reportLayoutID; // 個別届出種類ID 
	CountClass countClass; //カウント大区分
	int countClass_s; //カウント小区分
	int reportCount;//カウント数量

}
