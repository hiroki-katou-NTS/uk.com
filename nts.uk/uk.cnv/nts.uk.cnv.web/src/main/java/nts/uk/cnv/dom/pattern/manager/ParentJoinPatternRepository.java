package nts.uk.cnv.dom.pattern.manager;

import java.util.List;

import nts.uk.cnv.dom.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.service.ConversionInfo;

public interface ParentJoinPatternRepository {

	List<ParentJoinPattern> get(ConversionInfo info, String category, String name);

}
