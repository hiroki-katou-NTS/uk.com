package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerclass;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerclass.CareerClass;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_CLASS")
public class JhcmtCareerClass extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerClassPK PK_JHCMT_CAREER_CLASS;

	@Column(name = "CID")
	public String cId;

	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate startDate;

	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endDate;

	@Column(name = "CAREER_CLASS_CD")
	public String careerClassCd;
	
	@Column(name = "CAREER_CLASS_NAME")
	public String careerClassName;
	
	@Column(name = "DISABLE_FLG")
	public Integer disableFlg;

	@OneToMany(mappedBy = "masterMappingList", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JHCMT_CAREER_TYPE_MP")
	public List<JhcmtCareerClassMp> masterMappingList;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_CLASS;
	}

	public CareerClass toDomain() {

		return CareerClass.createFromJavaType(
				this.cId, 
				this.PK_JHCMT_CAREER_CLASS.histId, 
				this.PK_JHCMT_CAREER_CLASS.careerClassId,
				this.careerClassCd,
				this.careerClassName,
				this.disableFlg == 1,
				this.masterMappingList.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}
	
}
