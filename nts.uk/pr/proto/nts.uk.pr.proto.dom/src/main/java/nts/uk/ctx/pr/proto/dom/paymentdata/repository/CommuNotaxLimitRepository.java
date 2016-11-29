package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.paymentdata.CommuNotaxLimit;

public interface CommuNotaxLimitRepository {
	Optional<CommuNotaxLimit> find(String ccd, String commuNotaxLimitCode);
}
