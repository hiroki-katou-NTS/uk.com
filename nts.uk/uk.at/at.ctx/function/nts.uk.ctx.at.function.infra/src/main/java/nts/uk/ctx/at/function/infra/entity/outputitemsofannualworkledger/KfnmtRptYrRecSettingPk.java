package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : chinh.hm
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KfnmtRptYrRecSettingPk implements Serializable {
    private static long serialVersionUID = 1l;
    // 	設定ID
    @Column(name = "ID")
    private String iD;


}
