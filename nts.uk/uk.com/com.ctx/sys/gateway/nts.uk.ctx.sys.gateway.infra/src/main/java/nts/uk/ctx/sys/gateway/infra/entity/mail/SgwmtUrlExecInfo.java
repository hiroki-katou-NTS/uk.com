package nts.uk.ctx.sys.gateway.infra.entity.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.url.UrlExecInfo;
import nts.uk.shr.com.url.UrlTaskIncre;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SGWMT_URL_EXEC_INFO")
public class SgwmtUrlExecInfo extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SgwmtUrlExecInfoPk urlExecInfoPk;

	/**
	 * プログラムID
	 */
	@Basic(optional = false)
	@Column(name = "PROGRAM_ID")
	public String programId;

	/**
	 * ログインID
	 */
	@Basic(optional = false)
	@Column(name = "LOGIN_ID")
	public String loginId;

	/**
	 * 契約コード
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCd;

	/**
	 * 有効期限
	 */
	@Basic(optional = false)
	@Column(name = "EXPIRED_DATE")
	public GeneralDateTime expiredDate;

	/**
	 * 発行日時
	 */
	@Basic(optional = false)
	@Column(name = "ISSUE_DATE")
	public GeneralDateTime issueDate;

	/**
	 * 遷移先の画面ID
	 */
	@Basic(optional = false)
	@Column(name = "SCREEN_ID")
	public String screenId;

	/**
	 * 社員ID
	 */
	@Basic(optional = true)
	@Column(name = "SID")
	public String sid;

	/**
	 * 社員コード
	 */
	@Basic(optional = true)
	@Column(name = "SCD")
	public String scd;

	@Override
	protected Object getKey() {
		return urlExecInfoPk;
	}

//	@OneToMany(targetEntity = SgwmtUrlTaskIncre.class, cascade = CascadeType.ALL, mappedBy = "sgwmtUrlExecInfo", orphanRemoval = true)
//	@JoinTable(name = "SGWMT_URL_TASK_INCRE")
//	public List<SgwmtUrlTaskIncre> taskIncre;

	public UrlExecInfo toDomain() {
        return UrlExecInfo.createFromJavaType(
        		this.urlExecInfoPk.embeddedId, this.urlExecInfoPk.cid, this.programId, this.loginId, this.contractCd, 
        		this.expiredDate, this.issueDate, this.screenId, this.sid, this.scd, null
//        		, this.taskIncre.stream().map(x -> {return new UrlTaskIncre(x.urlTaskIncrePk.cid, "", "", "", "");}).collect(Collectors.toList())
        		);
    }
	
	
	public static SgwmtUrlExecInfo toEntity(UrlExecInfo domain) {
		return new SgwmtUrlExecInfo(new SgwmtUrlExecInfoPk(domain.getEmbeddedId(), domain.getCid()),
				domain.getProgramId(), domain.getLoginId(), domain.getContractCd(), domain.getExpiredDate(),
				domain.getIssueDate(), domain.getScreenId(), domain.getSid(), domain.getScd());
//		, 
//		domain.getTaskIncre().stream().map(x -> {
//			return SgwmtUrlTaskIncre.toEntity(x);
//		}).collect(Collectors.toList())
	}
}
