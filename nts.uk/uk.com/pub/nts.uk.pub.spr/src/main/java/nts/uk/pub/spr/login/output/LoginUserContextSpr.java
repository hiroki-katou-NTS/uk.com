package nts.uk.pub.spr.login.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ログインユーザコンテキスト
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class LoginUserContextSpr {
	
	/** ユーザID */
	private String userID;
	
	/** 契約コード */
	private String contractCD;
	
	/** 会社ID */
	private String companyID;
	
	/** 会社コード */
	private String companyCD;
	
	/** 個人ID */
	private String personID;
	
	/** ログイン社員ID */
	private String loginEmployeeID;
	
	/** 社員コード */
	private String employeeCD;
	
	/** 個人情報のロール */
	private List<RoleInfoSpr> roleList;
	
	/** 社員ID */
	private String employeeID;
	
}
