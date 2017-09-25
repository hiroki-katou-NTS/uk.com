package entity.person.info;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BPSMT_PERSON")
public class BpsmtPerson extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BpsmtPersonPk bpsmtPersonPk;

	@Basic(optional = false)
	@Column(name = "BIRTHDAY")
	public GeneralDate birthday;

	@Basic(optional = false)
	@Column(name = "BLOOD_TYPE")
	public int bloodType;

	@Basic(optional = false)
	@Column(name = "GENDER")
	public int gender;

	@Basic(optional = false)
	@Column(name = "PERSON_MOBILE")
	public String personMobile;

	@Basic(optional = false)
	@Column(name = "PERSON_MAIL_ADDERSS")
	public String personMailAddress;

	@Basic(optional = true)
	@Column(name = "HOBBY")
	public String hobby;

	@Basic(optional = true)
	@Column(name = "TASTE")
	public String taste;

	@Basic(optional = true)
	@Column(name = "NATIONALITY")
	public String nationality;

	@Basic(optional = false)
	@Column(name = "PERSON_NAME")
	public String personName;

	@Basic(optional = true)
	@Column(name = "PERSON_NAME_KANA")
	public String personNameKana;

	@Basic(optional = false)
	@Column(name = "BUSINESS_NAME")
	public String businessName;

	@Basic(optional = true)
	@Column(name = "BUSINESS_ENGLISH_NAME")
	public String businessEnglishName;

	@Basic(optional = true)
	@Column(name = "BUSINESS_OTHER_NAME")
	public String businessOtherName;

	@Basic(optional = true)
	@Column(name = "PERSON_ROMANJI")
	public String personRomanji;

	@Basic(optional = true)
	@Column(name = "OLD_NAME")
	public String oldName;

	@Basic(optional = true)
	@Column(name = "TODOKEDE_FULL_NAME")
	public String todokedeFullName;

	@Basic(optional = true)
	@Column(name = "TODOKEDE_OLD_FULLNAME")
	public String todokedeOldFullName;

	@Override
	protected Object getKey() {
		return this.bpsmtPersonPk;
	}

}
