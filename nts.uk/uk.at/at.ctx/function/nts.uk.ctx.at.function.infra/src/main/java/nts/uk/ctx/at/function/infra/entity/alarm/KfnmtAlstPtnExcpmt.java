package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;
import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_PTN_EXCPMT")
public class KfnmtAlstPtnExcpmt extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlstPtnExcpmtPK pk;
	
	@Column(name = "AUTH_SET")
	public int authSetting;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false)
	})
	public KfnmtAlstPtn alarmPatternSet;
	
	@OneToMany(mappedBy="alarmPerSet", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALST_PTN_EXCPMTITM")
	public List<KfnmtAlstPtnExcpmtitm> alarmPerSetItems;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KfnmtAlstPtnExcpmt(KfnmtAlstPtnExcpmtPK pk, int authSetting, List<KfnmtAlstPtnExcpmtitm> alarmPerSetItems) {
		super();
		this.pk = pk;
		this.authSetting = authSetting;
		this.alarmPerSetItems = alarmPerSetItems;
	}
	
	public AlarmPermissionSetting toDomain() {
		return new AlarmPermissionSetting(this.pk.alarmPatternCD, this.pk.companyID, authSetting==1,
				this.alarmPerSetItems.stream().map(c -> c.pk.roleID).collect(Collectors.toList()));
	}
	
	public static KfnmtAlstPtnExcpmt toEntity(AlarmPermissionSetting domain, String companyId, String alarmPatternCode) {
		List<KfnmtAlstPtnExcpmtitm> alarmPerSetItems = domain.getRoleIds().stream()
				.map(r -> new KfnmtAlstPtnExcpmtitm(
						new KfnmtAlstPtnExcpmtitmPK(companyId, alarmPatternCode, r)))
				.collect(Collectors.toList());
		
		return new KfnmtAlstPtnExcpmt(new KfnmtAlstPtnExcpmtPK(companyId, alarmPatternCode),
				domain.isAuthSetting() ? 1 : 0, alarmPerSetItems);
	}

	public void fromEntity(KfnmtAlstPtnExcpmt  newEntity) {
//		this.alarmPerSetItems = newEntity.alarmPerSetItems;
		this.alarmPerSetItems.removeIf(c->!newEntity.alarmPerSetItems.contains(c));	
		newEntity.alarmPerSetItems.forEach( item ->{
			if(!this.alarmPerSetItems.contains(item)) this.alarmPerSetItems.add(item);		
		});
		this.authSetting = newEntity.authSetting;
	}
}
