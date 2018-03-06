package nts.uk.ctx.at.function.dom.adapter;

import java.util.Optional;


public interface ErAlApplicationAdapter {
	Optional<ErAlApplicationAdapterDto> getAllErAlAppByEralCode(String companyID,String errorAlarmCode);
}
