package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careertype;

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
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.CareerType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_TYPE")
public class JhcmtCareerType extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerTypePK PK_JHCMT_CAREER_TYPE;

	@Column(name = "CID")
	public String cId;

	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate startDate;

	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endDate;

	@Column(name = "CAREER_TYPE_CD")
	public String careerTypeCd;
	
	@Column(name = "CAREER_TYPE_NAME")
	public String careerTypeName;
	
	@Column(name = "COMMON_FLG")
	public Integer commonFlg;
	
	@Column(name = "DISABLE_FLG")
	public Integer disableFlg;

	@OneToMany(mappedBy = "masterMappingList", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JHCMT_CAREER_TYPE_MP")
	public List<JhcmtCareerTypeMp> masterMappingList;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_TYPE;
	}

	public CareerType toDomain() {

		return CareerType.createFromJavaType(
				this.cId, 
				this.PK_JHCMT_CAREER_TYPE.careerTypeId, 
				this.PK_JHCMT_CAREER_TYPE.histId,
				this.careerTypeCd,
				this.careerTypeName,
				this.commonFlg == 1,
				this.disableFlg == 1,
				this.masterMappingList.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}
	
}
