package entity.person.bs.info;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

public class BpsdtPerson  extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BpsdtPersonPk bpsdtPersonPk;
		
	@Basic(optional = false)
	@Column(name = "PERSON_NAME_GROUP")
	public String PersonNameGroup;

	@Basic(optional = false)
	@Column(name = "BIRTHDAY")
	public String Birthday;

	@Basic(optional = false)
	@Column(name = "BLOOD_TYPE")
	public String BloodType;

	@Basic(optional = false)
	@Column(name = "GENDER")
	public String Gender;
	
	@Basic(optional = false)
	@Column(name = "PERSON_MOBILE")
	public String PersonMobile;
	
	@Basic(optional = false)
	@Column(name = "PERSON_MAIL_ADDERSS")
	public String PersonMailAddress;
	
	@Basic(optional = true)
	@Column(name = "HOBBY")
	public String Hobby;
	
	@Basic(optional = true)
	@Column(name = "TASTE")
	public String Taste;
	
	@Basic(optional = true)
	@Column(name = "NATIONALITY")
	public String Nationality;
	
	@Override
	protected Object getKey() {
		return this.bpsdtPersonPk;
	}

}
