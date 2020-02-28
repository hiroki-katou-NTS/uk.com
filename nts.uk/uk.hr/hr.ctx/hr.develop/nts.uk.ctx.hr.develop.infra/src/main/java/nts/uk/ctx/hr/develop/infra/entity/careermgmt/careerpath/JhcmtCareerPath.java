package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_PATH")
public class JhcmtCareerPath extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerPathPK PK_JHCMT_CAREER_PATH;

	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate strD;

	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endD;
	
	@Column(name = "MAX_CLASS_LEVEL")
	public Integer level;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_PATH;
	}

	public static List<JhcmtCareerPath> toEntity(CareerPathHistory domain) {
		return domain.getCareerPathHistory().stream().map(c -> new JhcmtCareerPath(new JhcmtCareerPathPK(domain.getCompanyId(), c.identifier()), c.start(), c.end(), null)).collect(Collectors.toList());
	}
	
}
