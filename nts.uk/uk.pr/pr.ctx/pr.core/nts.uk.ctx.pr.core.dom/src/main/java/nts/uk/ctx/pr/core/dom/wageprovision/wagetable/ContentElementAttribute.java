package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
 * 要素属性
 */
@AllArgsConstructor
@Getter
public class ContentElementAttribute extends DomainObject {

    /**
     * 第一要素項目
     */
    private ElementItem firstElementItem;

    /**
     * 第二要素項目
     */
    private Optional<ElementItem> secondElementItem;

    /**
     * 第三要素項目
     */
    private Optional<ElementItem> thirdElementItem;

    public ContentElementAttribute(String firstMasterCode, Integer firstFrameNumber, Integer firstFrameLowerLimit, Integer firstFrameUpperLimit,
                                   String secondMasterCode, Integer secondFrameNumber, Integer secondFrameLowerLimit, Integer secondFrameUpperLimit,
                                   String thirdMasterCode, Integer thirdFrameNumber, Integer thirdFrameLowerLimit, Integer thirdFrameUpperLimit) {
        this.firstElementItem = new ElementItem(firstMasterCode, firstFrameNumber, firstFrameLowerLimit, firstFrameUpperLimit);
        if (secondMasterCode == null && secondFrameNumber == null && secondFrameLowerLimit == null && secondFrameUpperLimit == null)
            this.thirdElementItem = Optional.empty();
        else
            this.secondElementItem = Optional.of(new ElementItem(secondMasterCode, secondFrameNumber, secondFrameLowerLimit, secondFrameUpperLimit));
        if (thirdMasterCode == null && thirdFrameNumber == null && thirdFrameLowerLimit == null && thirdFrameUpperLimit == null)
            this.thirdElementItem = Optional.empty();
        else
            this.secondElementItem = Optional.of(new ElementItem(thirdMasterCode, thirdFrameNumber, thirdFrameLowerLimit, thirdFrameUpperLimit));
    }

}
