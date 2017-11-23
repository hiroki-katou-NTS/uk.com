package nts.uk.ctx.sys.auth.infra.entity.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "SACMT_USER")
@NoArgsConstructor
@AllArgsConstructor
public class SacmtUser extends UkJpaEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The user id. */
    /** ユーザID */ 
    @Column(name = "USER_ID")
    private String userID;
    
    /** The default User */
    /** デフォルトユーザ*/
    @Column(name = "DEFAULT_USER")
    private boolean defaultUser;
    
    /** The password. */
    /**パスワード*/
    @Column(name = "PASSWORD")
    private String password;
    
    /** The login ID. */
    /** ログインID */
    @Column(name = "LOGIN_ID")
    private String loginID;
    
    /** The contract CD. */
    /**契約コード */
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    /** The expiration date. */
    /** 有効期限 */
    @Column(name = "EXPIRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    
    /** The special user. */
    /** 特別利用者 */
    @Column(name = "SPECIAL_USER")
    private boolean specialUser;
    
    /** The multi com. */
    /**複数会社を兼務する */
    @Column(name = "MULTI_COM")
    private boolean multiCompanyConcurrent;
    
    /** The mail add. */
    /** メールアドレス*/
    @Column(name = "MAIL_ADD")
    private String mailAdd;
    
    /**ユーザ名 */
    /** The user name. */
    @Column(name = "USER_NAME")
    private String userName;
    
    /** 紐付け先個人ID*/
    /** The asso sid. */
    @Column(name = "ASSO_PID")
    private String associatedPersonID;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
