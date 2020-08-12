package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.displaysetting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * 個人条件の表示制御 表示する資格 entity
 *
 */
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "KSCMT_SYACND_DISPCTL_QUA")
public class KscmtSyacndDispCtlQua extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtSyacndDispCtlQuaPk pk;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @PrimaryKeyJoinColumns({
	// @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID")
	// })
	// public KscmtSyacndDispCtl kscmtSyacndDispCtl;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtSyacndDispCtlQua> toEntities(DisplayControlPersonalCondition dom) {
		if (dom.getOtpWorkscheQualifi().isPresent()) {
			return dom.getOtpWorkscheQualifi().get().getListQualificationCD().stream().map(i -> {
				return new KscmtSyacndDispCtlQua(new KscmtSyacndDispCtlQuaPk(dom.getCompanyID(), i.v()));
			}).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public static KscmtSyacndDispCtlQua toEntity(DisplayControlPersonalCondition dom, String code) {
		return new KscmtSyacndDispCtlQua(new KscmtSyacndDispCtlQuaPk(dom.getCompanyID(), code));
	}

}
