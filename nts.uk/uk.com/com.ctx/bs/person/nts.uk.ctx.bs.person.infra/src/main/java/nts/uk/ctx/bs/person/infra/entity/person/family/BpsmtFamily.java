package nts.uk.ctx.bs.person.infra.entity.person.family;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BPSMT_FAMILY")
public class BpsmtFamily extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public BpsmtFamilyPk ppsmtFamilyPk;

	@Basic(optional = false)
	@Column(name = "WORK_STUDENT_TYPE")
	public int workStudentType;

	@Basic(optional = false)
	@Column(name = "TOG_SEP_DIV_TYPE")
	public int TogSepDivType;

	@Basic(optional = false)
	@Column(name = "TODUKEDE_NAME")
	public String todukedeName;

	@Basic(optional = false)
	@Column(name = "SUP_CARE_ATR")
	public int SupportCareType;

	@Basic(optional = false)
	@Column(name = "RELATIONSHIP")
	public String relationShip;

	@Basic(optional = false)
	@Column(name = "PERSON_ID")
	public String pid;

	@Basic(optional = false)
	@Column(name = "OCCUPATION_NAME")
	public String occupationName;

	@Basic(optional = false)
	@Column(name = "NATIONALITY")
	public String nationality;

	@Basic(optional = true)
	@Column(name = "NAME_ROMAJI")
	public String nameRomaji;

	@Basic(optional = false)
	@Column(name = "KN_NAME_ROMAJI")
	public String nameRomajiKana;

	@Basic(optional = true)
	@Column(name = "NAME_MULTI_LANG")
	public String nameMultiLang;

	@Basic(optional = false)
	@Column(name = "KN_NAME_MULTI_LANG")
	public String nameMultiLangKana;

	@Basic(optional = true)
	@Column(name = "NAME")
	public String name;

	@Basic(optional = false)
	@Column(name = "KN_NAME")
	public String NameKana;

	@Basic(optional = false)
	@Column(name = "EXP_DATE")
	public GeneralDate expDate;

	@Basic(optional = false)
	@Column(name = "ENTRY_DATE")
	public GeneralDate entryDate;

	@Basic(optional = false)
	@Column(name = "DATE_OF_DEATH")
	public GeneralDate deathDate;

	@Basic(optional = false)
	@Column(name = "BIRTHDAY")
	public GeneralDate birthday;

	@Override
	protected Object getKey() {
		return ppsmtFamilyPk;
	}

}
