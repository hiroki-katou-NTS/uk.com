package nts.uk.ctx.at.function.infra.entity.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author : chinh.hm
 */
@Embeddable
@AllArgsConstructor
@Setter
@Getter
public class KfnmtRptYrRecSettingPk implements Serializable {
    private static long serialVersionUID = 1l;
    // 	設定ID
    @Column(name = "ID")
    private String iD;


}
