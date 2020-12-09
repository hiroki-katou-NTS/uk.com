package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KFNMT_ALST_MAILSET")
public class KfnmtMailSettingList extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		return generateId;
	}
	
	@Id
	@Column(name = "GENERATE_ID")
	public String generateId;

	@Column(name = "LIST_MAIL_ID")
	public String listMailId;

	@Column(name = "MAIL_ADDRESS")
	public String mailAddress;

	// Map mail setting auto
	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "CC", insertable = false, updatable = false)
	public KfnmtMailSettingAutomatic mailAutomaticCC;

	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "BCC", insertable = false, updatable = false)
	public KfnmtMailSettingAutomatic mailAutomaticBCC;

	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "ADMIN_CC", insertable = false, updatable = false)
	public KfnmtMailSettingAutomatic mailAutomaticAdminCC;

	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "ADMIN_BCC", insertable = false, updatable = false)
	public KfnmtMailSettingAutomatic mailAutomaticAdminBCC;

	// Map mail setting normal
	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "CC", insertable = false, updatable = false)
	public KfnmtMailSettingNormal mailNormalCC;

	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "BCC", insertable = false, updatable = false)
	public KfnmtMailSettingNormal mailNormalBCC;

	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "ADMIN_CC", insertable = false, updatable = false)
	public KfnmtMailSettingNormal mailNormalAdminCC;

	@ManyToOne
	@JoinColumn(name = "LIST_MAIL_ID", referencedColumnName = "ADMIN_BCC", insertable = false, updatable = false)
	public KfnmtMailSettingNormal mailNormalAdminBCC;

	public KfnmtMailSettingList(String listMailId, String mailAddress) {
		super();
		this.generateId = IdentifierUtil.randomUniqueId();
		this.listMailId = listMailId;
		this.mailAddress = mailAddress;
	}
}
