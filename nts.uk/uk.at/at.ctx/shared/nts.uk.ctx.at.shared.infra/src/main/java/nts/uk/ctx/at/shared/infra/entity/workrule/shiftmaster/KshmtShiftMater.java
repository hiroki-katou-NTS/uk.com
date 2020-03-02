package nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KSHMT_SHIFT_MASTER")
public class KshmtShiftMater extends UkJpaEntity {

	@EmbeddedId
	public KshmtShiftMaterPK kshmtShiftMaterPK;

	@Column(name = "NAME")
	public String name;

	@Column(name = "COLOR")
	public String color;

	@Column(name = "NOTE")
	public String remarks;

	@Column(name = "WORKTYPE_CD")
	public String workTypeCd;

	@Column(name = "WORKTIME_CD")
	public String workTimeCd;

	@Override
	protected Object getKey() {
		return kshmtShiftMaterPK;
	}

	public KshmtShiftMater(KshmtShiftMaterPK kshmtShiftMaterPK, String name, String color, String remarks,
			String workTypeCd, String workTimeCd) {
		super();
		this.kshmtShiftMaterPK = kshmtShiftMaterPK;
		this.name = name;
		this.color = color;
		this.remarks = remarks;
		this.workTypeCd = workTypeCd;
		this.workTimeCd = workTimeCd;
	}

	public ShiftMaster toDomain() {
		return new ShiftMaster(kshmtShiftMaterPK.companyId, new ShiftMasterCode(kshmtShiftMaterPK.shiftMaterCode),
				new ShiftMasterDisInfor(new ShiftMasterName(this.name), new ColorCodeChar6(this.color),
						this.remarks == null ? null : new Remarks(this.remarks)),
				this.workTypeCd, this.workTimeCd);
	}

	public static KshmtShiftMater toEntity(ShiftMaster domain) {
		return new KshmtShiftMater(new KshmtShiftMaterPK(domain.getCompanyId(), domain.getShiftMasterCode().v()),
				domain.getDisplayInfor().getName().v(), domain.getDisplayInfor().getColor().v(),
				domain.getDisplayInfor().getRemarks().isPresent() ? domain.getDisplayInfor().getRemarks().get().v()
						: null,
						domain.getWorkTypeCode().v(), domain.getWorkTimeCode()==null ? null:domain.getWorkTimeCode().v());
	}
}
