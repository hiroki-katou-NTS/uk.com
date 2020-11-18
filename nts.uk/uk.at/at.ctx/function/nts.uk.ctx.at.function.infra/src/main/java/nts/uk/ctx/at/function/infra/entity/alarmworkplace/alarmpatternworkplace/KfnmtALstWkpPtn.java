package nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPermissionSetting;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmlstrole.KfnmtALstWkpPms;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * entity : アラームリストパターン設定(職場別)
 */
@AllArgsConstructor
@Entity
@Table(name = "KFNMT_ALSTWKP_PTN")
public class KfnmtALstWkpPtn extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtALstWkpPtnPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "ALARM_PATTERN_NAME")
    public String alarmPatternName;

    @Column(name = "PERMISSION_SET")
    public boolean authSetting;

    @OneToMany(mappedBy = "kfnmtALstWkpPtn", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
    @JoinTable(name = "KFNMT_ALSTWKP_PMS")
    public List<KfnmtALstWkpPms> alarmPerSet;

    @OneToOne(mappedBy = "kfnmtALstWkpPtn", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
    @JoinTable(name = "KFNMT_PTN_MAP_CAT")
    public List<KfnmtWkpCheckCondition> checkConList;

    @Override
    protected Object getKey() {
        return this.pk;
    }


    public AlarmPatternSettingWorkPlace toDomain() {
        return new AlarmPatternSettingWorkPlace(this.checkConList.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
            pk.alarmPatternCD, pk.companyID, toDomainItem(), alarmPatternName);
    }

    public static KfnmtALstWkpPtn toEntity(AlarmPatternSettingWorkPlace domain) {
        List<KfnmtALstWkpPms> alarmPerSet = domain.getAlarmPerSet().getRoleIds().stream().map(x ->
            KfnmtALstWkpPms.toEntity(x, domain.getAlarmPatternCD().v())).collect(Collectors.toList());

        List<KfnmtWkpCheckCondition> checkConList = domain.getCheckConList().stream()
            .map(c -> KfnmtWkpCheckCondition.toEntity(c, domain.getAlarmPatternCD().v())).collect(Collectors.toList());
        return new KfnmtALstWkpPtn(
            new KfnmtALstWkpPtnPk(domain.getCompanyID(), domain.getAlarmPatternCD().v()),
            AppContexts.user().contractCode(),
            domain.getAlarmPatternName().v(),
            domain.getAlarmPerSet().isAuthSetting(),
            alarmPerSet, checkConList);
    }

    public AlarmPermissionSetting toDomainItem() {
        return new AlarmPermissionSetting(authSetting,this.alarmPerSet.stream().map(c -> c.pk.roleId).collect(Collectors.toList()));
    }

    public void fromEntity(KfnmtALstWkpPtn entity) {
        this.alarmPatternName = entity.alarmPatternName;
        this.checkConList.removeIf(c -> !entity.checkConList.contains(c));
        entity.checkConList.forEach(e -> {
            Optional<KfnmtWkpCheckCondition> checkCon = this.checkConList.stream().filter(c -> c.pk.equals(e.pk))
                .findFirst();
            if (checkCon.isPresent()) {
                checkCon.get().fromEntity(e);
            } else {
                this.checkConList.add(e);
            }
        });
        this.alarmPerSet.removeIf(x -> !entity.alarmPerSet.contains(x));

        entity.alarmPerSet.forEach( item ->{
            if(!this.alarmPerSet.contains(item)) this.alarmPerSet.add(item);
        });
        this.authSetting = entity.authSetting;

    }

}
