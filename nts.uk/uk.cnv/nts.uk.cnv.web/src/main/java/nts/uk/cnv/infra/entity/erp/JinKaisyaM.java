package nts.uk.cnv.infra.entity.erp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jinkaisya_m")
public class JinKaisyaM extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "会社CD")
	private int companyCode;

	@Column(name = "j_kaophotentry")
	private String profilePhotePath;

	@Column(name = "j_mapentry")
	private String mapPhotePath;

	@Column(name = "j_densientry")
	private String documentPath;

	@Column(name = "j_uploadentry")
	private String profilePhote;

	@Override
	protected Object getKey() {
		return companyCode;
	}

}
